package com.mpx.birjan.service.impl;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.mpx.birjan.bean.Lottery;
import com.mpx.birjan.bean.Person;

@WebService
public interface BirjanWebService {
	
	String[] getComboOptions(String view, String combo, String day);

	String createGame(String lottery, String variant, String day,
			Object[][] data);

	Object[][]  retrieveByHash(String code);

	void createDraw(String lottery, String variant, String day,
			String[] data);

	String[] retrieveDraw(String lottery, String variant, String day);

	void validateDraw(String lottery, String variant, String day);

	String[] retriveBalance(String lottery, String variant, String day);
	
}
