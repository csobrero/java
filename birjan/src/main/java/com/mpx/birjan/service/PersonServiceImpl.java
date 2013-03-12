package com.mpx.birjan.service;

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
	public void saveOrUpdatePerson(Long id, String name, String surname, String movile){
		Person person = new Person(id, name, surname, movile);
		dao.update(person);
	}
	
	public Person findPerson(long id){
		return dao.getById(id);
	}
	
}
