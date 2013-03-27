package com.mpx.birjan.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpx.birjan.bean.Person;
import com.mpx.birjan.core.Rule.Nacional;
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

	@Override
	public String[] getComboOptions(String combo) {
		List<String> list = new ArrayList<String>();
		if(combo.equalsIgnoreCase("loteria")){
			return new String[]{"NACIONAL","PROVINCIA"};
		}
		
			list = Nacional.VARIANT.toList();
			return list.toArray(new String[list.size()]);
		
	}

}
