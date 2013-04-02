package com.mpx.birjan.service.impl;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.mpx.birjan.bean.Lottery;
import com.mpx.birjan.bean.Person;

@WebService
public interface BirjanWebService {

	long saveOrUpdatePerson(Long id, String name, String surname, String movile);

	List<Person> findByFilter(String name, String surname, String movile);

	String[] getComboOptions(String combo);

	String createGame(String lottery, String variant, String day,
			Object[][] data);

	Object[][]  retrieveByHash(String code);

	void createDraw(String lottery, String variant, String day,
			String[] data);

	String[] retrieveDraw(String lottery, String variant, String day);
	
}
