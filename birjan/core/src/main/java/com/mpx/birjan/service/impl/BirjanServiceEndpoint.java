package com.mpx.birjan.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.mpx.birjan.bean.Draw;
import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.Lottery;
import com.mpx.birjan.bean.Status;
import com.mpx.birjan.bean.Wrapper;
import com.mpx.birjan.core.TransactionalManager;

@Service
@WebService(serviceName = "birjanws", endpointInterface = "com.mpx.birjan.service.impl.BirjanWebService")
@Secured("ROLE_USER")
public class BirjanServiceEndpoint implements BirjanWebService {

	@Autowired
	private TransactionalManager txManager;

	@Override
	public String[] populateCombo(String view, String combo, String day) {
		return txManager.getComboOptions(view, combo, day);
	}

	@Override
	public String createGame(String lotteryName, String variant, String day,
			Object[][] data) {
		Preconditions.checkNotNull(lotteryName);
		Preconditions.checkNotNull(variant);
		Preconditions.checkNotNull(day);
		Preconditions.checkNotNull(data);

		DateTime date = BirjanUtils.getDate(day);
		Lottery lottery = Lottery.valueOf((lotteryName + "_" + variant)
				.toUpperCase());

		Preconditions.checkArgument(
				isDevelopment() || BirjanUtils.isValid(lottery, date),
				"Invalid entry");

		String hash = txManager.createGame(lottery, date, data);

		System.out.println(hash);
		return hash;
	}

	@Override
	public Object[][] retrieveByHash(String hash) {
		Object[][] dataVector = txManager.retrieveByHash(hash);
		return dataVector;
	}

	@Override
	@Secured({ "ROLE_MANAGER" })
	public void createDraw(String lotteryName, String variant, String day,
			String[] data) {
		Preconditions.checkNotNull(lotteryName);
		Preconditions.checkNotNull(variant);
		Preconditions.checkNotNull(day);
		Preconditions.checkArgument(data.length == 20);

		DateTime date = BirjanUtils.getDate(day);
		Lottery lottery = Lottery.valueOf((lotteryName + "_" + variant)
				.toUpperCase());

		Preconditions.checkArgument(
				isDevelopment() || BirjanUtils.isValid(lottery, date),
				"Invalid entry");

		txManager.createDraw(lottery, date, data);
	}

	@Override
	@Secured({ "ROLE_MANAGER" })
	public String[] retrieveDraw(String lotteryName, String variant, String day) {
		Preconditions.checkNotNull(lotteryName);
		Preconditions.checkNotNull(variant);
		Preconditions.checkNotNull(day);
		
		DateTime date = BirjanUtils.getDate(day);
		Lottery lottery = Lottery.valueOf((lotteryName + "_" + variant)
				.toUpperCase());
		
		Draw draw = txManager.retrieveDraw(lottery, date);
		
		return draw.getNumbers();
	}

	@Override
	@Secured({ "ROLE_MANAGER" })
	public void validateDraw(String lotteryName, String variant, String day) {
		Preconditions.checkNotNull(lotteryName);
		Preconditions.checkNotNull(variant);
		Preconditions.checkNotNull(day);
		
		DateTime date = BirjanUtils.getDate(day);
		Lottery lottery = Lottery.valueOf((lotteryName + "_" + variant)
				.toUpperCase());
		
		txManager.validateDraw(lottery, date);

	}

	@Override
	public Wrapper[] retriveGames(String lotteryName, String variant, String day) {
		Preconditions.checkNotNull(lotteryName);
		Preconditions.checkNotNull(variant);
		Preconditions.checkNotNull(day);

		DateTime date = BirjanUtils.getDate(day);
		Lottery lottery = Lottery.valueOf((lotteryName + "_" + variant)
				.toUpperCase());
		
		List<Game> games = txManager.retriveGames(Status.VALID, lottery, date, null);
		
		Wrapper[] values = null;
		if (games != null && !games.isEmpty()) {
			values = new Wrapper[games.size()];
			for (int i = 0; i < values.length; i++) {
				values[i] = new Wrapper(games.get(i).getHash(), games.get(i)
						.getData());
			}
		}
		
		return values;
	}

	@Override
	public boolean isDevelopment() {
		return txManager.isDevelopment();
	}

}
