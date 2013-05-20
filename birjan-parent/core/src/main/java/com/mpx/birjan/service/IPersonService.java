package com.mpx.birjan.service;

import java.util.List;

import javax.jws.WebService;

import com.mpx.birjan.bean.Person;

@WebService
public interface IPersonService {

	long saveOrUpdatePerson(Long id, String name, String surname, String movile);

	List<Person> findByFilter(String name, String surname, String movile);

}
