package com.mpx.birjan.service;

import java.util.Date;

import javax.jws.WebService;

import com.mpx.birjan.Exception.BusinessException;
import com.mpx.birjan.common.BalanceDTO;
import com.mpx.birjan.common.Ticket;
import com.mpx.birjan.common.Wrapper;

@WebService
public interface BirjanWebService {

	String[] populateCombo(String view, String combo, String day);

	String createGames(String day, String[] lotteries, Object[][] data);

	Ticket retrieveByHash(String code);

	void createDraw(String lottery, String variant, String day, String[] data);

	String[] retrieveDraw(String lottery, String variant, String day);

	BalanceDTO performBalance(String day, String user, Boolean close);

	void validateDraw(String lottery, String variant, String day);

	Wrapper[] retriveGames(String lottery, String variant, String state, String day);

	boolean isDevelopment();

	Object[][] retrieveAvailability(String day);

	Ticket pay(String hash);

	String[] getAuthorities() throws BusinessException;

	BalanceDTO[] closeBalance(String day, Boolean close);

	String[] getUsers();

	Date updateServerDateTime(Date date);

}
