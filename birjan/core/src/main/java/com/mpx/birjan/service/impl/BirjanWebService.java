package com.mpx.birjan.service.impl;

import javax.jws.WebService;

import com.mpx.birjan.bean.Jugada;
import com.mpx.birjan.bean.Wrapper;

@WebService
public interface BirjanWebService {

	String[] populateCombo(String view, String combo, String day);

	String createGames(String day, String[] lotteries, Object[][] data);

	Jugada retrieveByHash(String code);

	void createDraw(String lottery, String variant, String day, String[] data);

	String[] retrieveDraw(String lottery, String variant, String day);

	void validateDraw(String lottery, String variant, String day);

	Wrapper[] retriveGames(String lottery, String variant, String day);

	boolean isDevelopment();

	Object[][] retrieveAvailability(String day);

}
