package com.mpx.birjan.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpx.birjan.bean.Person;
import com.mpx.birjan.service.IPersonService;

@Service
@WebService(serviceName = "birjanws")
public class BirjanServiceEndpoint implements BirjanWebService {

	@Autowired
	private IPersonService personService;

	public long saveOrUpdatePerson(Long id, String name, String surname,
			String movile) {
		return personService.saveOrUpdatePerson(id, name, surname, movile);
	}

	public List<Person> findByFilter(String name, String surname, String movile) {
		return personService.findByFilter(name, surname, movile);
	}

}
