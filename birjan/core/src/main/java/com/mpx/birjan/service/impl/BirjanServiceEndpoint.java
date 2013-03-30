package com.mpx.birjan.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.mpx.birjan.bean.Lottery;
import com.mpx.birjan.bean.Person;
import com.mpx.birjan.core.BirjanController;
import com.mpx.birjan.core.Rule.Nacional;
import com.mpx.birjan.service.IBirjanService;
import com.mpx.birjan.service.IPersonService;

@Service
@WebService(serviceName = "birjanws")
public class BirjanServiceEndpoint implements BirjanWebService {

	@Autowired
	private IPersonService personService;
	
	@Autowired
	private IBirjanService birjanService;

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
	
	@Override
	public String createGame(String lottery, Float[] betAmount, String[] numbers, Long personId){
		String hash = birjanService.createGame(Lottery.valueOf(lottery.toUpperCase()), betAmount, numbers, personId);
		return hash;
	}

}
