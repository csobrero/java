package com.mpx.birjan.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.Lottery;
import com.mpx.birjan.bean.Status;
import com.mpx.birjan.service.dao.GameDao;

@Controller
public class BirjanController {

	@Autowired
	private GameDao gameDao;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void processWinners(Lottery lottery, DateTime date) {

		Game winGame = retrieveWinGame(lottery, date);

//		if (winGame != null) {
//			List<Game> list = retrieveCandidates(lottery, date);
//
//			if (!list.isEmpty()) {
//				for (Game candidate : list) {
//
//					Map<Integer, Integer> winPositions = matchWinNumbers(
//							winGame.getNumbers(), candidate.getNumbers());
//
//					if (winPositions != null) {
//
//						float betAmount = candidate.getWager().getBetAmount();
//						float winAmount = lottery.getRule().calculateWinAmount(betAmount, winPositions);
//
//						candidate.getWager().setWinAmount(winAmount);
//						candidate.setStatus(Status.WINNER);
//
//					} else {
//						candidate.setStatus(Status.LOSER);
//					}
//				}
//			}
//		}
	}

	private Map<Integer, Integer> matchWinNumbers(String patterns,
			String candidate) {
		Map<Integer, Integer> hits = null;
		int k = 0;
		for (int i = 3; i < 80; i += 4) {
			if (candidate.charAt(i) == patterns.charAt(i)) {
				k = 1;
				for (int j = (i - 1); j > (i - 4); j--) {
					if (candidate.charAt(j) == patterns.charAt(j))
						k++;
					else if (candidate.charAt(j) == 'x')
						break;
					else {
						k = 0;
						break;
					}
				}
				if (k > 0) {
					if (hits == null)
						hits = new HashMap<Integer, Integer>();
					hits.put((i + 1) / 4, k);
					k = 0;
				}
			}
		}
		return hits;
	}

	private List<Game> retrieveCandidates(Lottery lottery, DateTime date) {
		return gameDao.findByFilter(lottery, Status.VALID, lottery.getRule()
				.getFrom(date), lottery.getRule().getTo(date));
	}

	private Game retrieveWinGame(Lottery lottery, DateTime date) {
		List<Game> list = gameDao.findByFilter(lottery, Status.OPEN, lottery
				.getRule().getFrom(date), lottery.getRule().getTo(date));

		if (list.size() == 1)
			return list.get(0);

		return null;
	}

}
