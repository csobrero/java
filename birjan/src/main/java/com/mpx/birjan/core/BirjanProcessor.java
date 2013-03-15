package com.mpx.birjan.core;

import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.Lottery;
import com.mpx.birjan.bean.Status;
import com.mpx.birjan.bean.Wager;
import com.mpx.birjan.service.dao.GameDao;
import com.mpx.birjan.service.dao.IGenericDAO;
import com.mpx.birjan.service.dao.PersonDao;

@Controller
public class BirjanProcessor {

	private IGenericDAO<Wager> wagerDao;

	@Autowired
	private PersonDao personDao;

	@Autowired
	private GameDao gameDao;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int processWinners(Lottery lottery, DateTime date) {

		Game winGame = retrieveWinGame(lottery, date);
		
		if(winGame!=null){
			List<Game> list = retrieveCandidates(lottery, date);
			
			if(!list.isEmpty()){
				for (Game candidate : list) {
					
				}
			}
			
		}
		
		
		
		return 0;

	}

	private List<Game> retrieveCandidates(Lottery lottery, DateTime date) {
		return gameDao.findByFilter(lottery, Status.VALID,
				lottery.getRule().getFrom(date), lottery.getRule().getTo(date));
	}

	private Game retrieveWinGame(Lottery lottery, DateTime date) {
		List<Game> list = gameDao.findByFilter(lottery, Status.OPEN,
				lottery.getRule().getFrom(date), lottery.getRule().getTo(date));
		
		if(list.size()==1)
			return list.get(0);
		
		return null;
	}

	@Resource(name = "genericJpaDAO")
	public final void wagerDao(final IGenericDAO<Wager> daoToSet) {
		wagerDao = daoToSet;
		wagerDao.setClazz(Wager.class);
	}

}
