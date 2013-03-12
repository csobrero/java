package com.mpx.birjan.service;

import java.util.List;

import com.mpx.birjan.bean.Person;

public interface IPersonService {

	void saveOrUpdatePerson(Long id, String name, String surname, String movile);

	List<Person> findByFilter(String name, String surname, String movile);

	List<Person> getAll();
}
