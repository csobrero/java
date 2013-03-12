package com.mpx.birjan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpx.birjan.bean.Person;
import com.mpx.birjan.service.dao.PersonDao;

@Service
public class PersonServiceImpl implements IPersonService {

	@Autowired
	private PersonDao dao;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveOrUpdatePerson(Long id, String name, String surname,
			String movile) {
		Person person = new Person(id, name, surname, movile);
		dao.update(person);
	}

	public List<Person> findByFilter(String name, String surname, String movile) {
		return dao.findByFilter(name, surname, movile);
	}
	
	public List<Person> getAll() {
		return dao.getAll();
	}

}
