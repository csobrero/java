package com.mpx.birjan.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpx.birjan.bean.Person;
import com.mpx.birjan.service.IPersonService;
import com.mpx.birjan.service.dao.PersonDao;

@Service
public class PersonServiceImpl implements IPersonService {

	@Autowired
	private PersonDao dao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public long saveOrUpdatePerson(Long id, String name, String surname,
			String movile) {
		Person person;
		if (id == null) {
			person = new Person(id, name, surname, movile);
			dao.create(person);
		} else {
			person = dao.getById(id);
			person.setName(name);
			person.setSurname(surname);
			person.setMovile(movile);
			dao.update(person);
		}
		return person.getId();
	}

	public List<Person> findByFilter(String name, String surname, String movile) {
		if (StringUtils.isBlank(name) && StringUtils.isBlank(surname)
				&& StringUtils.isBlank(movile))
			return dao.getAll();
		
		return dao.findByFilter(name, surname, movile);
	}

}
