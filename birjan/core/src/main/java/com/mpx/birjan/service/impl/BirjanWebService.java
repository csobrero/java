package com.mpx.birjan.service.impl;

import java.util.List;

import javax.jws.WebService;

import com.mpx.birjan.bean.Person;

@WebService
public interface BirjanWebService {

	long saveOrUpdatePerson(Long id, String name, String surname, String movile);

	List<Person> findByFilter(String name, String surname, String movile);

	String[] getComboOptions(String combo);

	String createGame(String lottery, Float[] betAmount, String[] numbers,
			Long personId);

}
