package com.mpx.birjan.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.mpx.birjan.bean.Balance;
import com.mpx.birjan.bean.Draw;
import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.User;
import com.mpx.birjan.common.BalanceDTO;
import com.mpx.birjan.common.Item;
import com.mpx.birjan.common.Lottery;
import com.mpx.birjan.common.Status;
import com.mpx.birjan.common.Ticket;
import com.mpx.birjan.common.Wrapper;
import com.mpx.birjan.core.TransactionalManager;
import com.mpx.birjan.service.BirjanWebService;
import com.mpx.birjan.util.BirjanUtils;

@Service
@WebService(serviceName = "birjanws", endpointInterface = "com.mpx.birjan.service.BirjanWebService")
@Secured("ROLE_USER")
public class BirjanServiceEndpoint implements BirjanWebService {

	@Autowired
	private TransactionalManager txManager;
	
	@Override
	public String[] populateCombo(String view, String combo, String day) {
		return txManager.getComboOptions(view, combo, day);
	}

	@Override
	public Ticket retrieveByHash(String hash) {
		Preconditions.checkNotNull(hash);
		Ticket jugada = txManager.processWinners(hash, false);
		return jugada;
	}

	@Override
	public Ticket pay(String hash) {
		Preconditions.checkNotNull(hash);
		Ticket jugada = txManager.processWinners(hash, true);
		return jugada;
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
		
		return (draw!=null)?draw.getNumbers():null;
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
	public Wrapper[] retriveGames(String lotteryName, String variant, String statusName, String day) {
		Preconditions.checkNotNull(lotteryName);
		Preconditions.checkNotNull(variant);
		Preconditions.checkNotNull(day);

		DateTime date = BirjanUtils.getDate(day);
		Lottery lottery = Lottery.valueOf((lotteryName + "_" + variant)
				.toUpperCase());
		
		Status status = (statusName!=null)?Status.valueOf(statusName):null;
		
		List<Game> games = txManager.retriveGames(status, lottery, date, null, null);
		
		Wrapper[] values = null;
		if (games != null && !games.isEmpty()) {
			values = new Wrapper[games.size()];
			for (int i = 0; i < values.length; i++) {
				Game game = games.get(i);
				values[i] = new Wrapper(game.getWager().getHash(),
						game.getData(), game.getStatus(), game.getPrize(), game.getWager()
								.getUser().getUsername(), game
								.getCreated());
			}
		}
		
		return values;
	}

	@Override
	public BalanceDTO performBalance(String day, String userName, Boolean close) {
		Preconditions.checkNotNull(day);
		Preconditions.checkNotNull(userName);
		
		DateTime date = BirjanUtils.getDate(day);
		User user = txManager.identify(userName);

		return txManager.performBalance(date, user, close);
		
	}
	
	@Override
	public boolean isDevelopment() {
		return txManager.isDevelopment();
	}
	
	@Override
	public Object[][] retrieveAvailability(String day){
		
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<Object> availables;
		
		for (Lottery[] lotteries : Lottery.ALL) {
			if(isDevelopment())
				availables = Arrays.asList(new Object[]{true,true,true,true});
			availables = BirjanUtils.retrieveVariantAvailability(lotteries, day);
			availables.add(0, lotteries[0].getName());
			list.add(availables);
		}
		
		return BirjanUtils.toArray(list);
		
	}

	@Override
	public String createGames(String day, String[] lotteryNames, Object[][] data) {
		Preconditions.checkNotNull(day);
		Preconditions.checkNotNull(lotteryNames);
		Preconditions.checkNotNull(data);
		

		DateTime date = BirjanUtils.getDate(day);
		List<Lottery> lotteries = new ArrayList<Lottery>();
		for (String str : lotteryNames) {
			Lottery lottery = Lottery.valueOf(str);
			Preconditions.checkArgument(
					isDevelopment() || BirjanUtils.isValid(lottery, date),
					"Invalid entry");
			
			lotteries.add(lottery);
		}
		
		String hash = txManager.createGames(lotteries, date, data);
		
		System.out.println(hash);

		return hash;
	}
	
}