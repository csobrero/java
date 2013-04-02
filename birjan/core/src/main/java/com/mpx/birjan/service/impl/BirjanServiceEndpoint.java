package com.mpx.birjan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.mpx.birjan.bean.Draw;
import com.mpx.birjan.bean.Lottery;
import com.mpx.birjan.bean.Person;
import com.mpx.birjan.core.BirjanController;
import com.mpx.birjan.core.Rule.Nacional;
import com.mpx.birjan.service.IBirjanService;
import com.mpx.birjan.service.IPersonService;
import com.mpx.birjan.service.dao.FilterDao;
import com.mpx.birjan.service.dao.IGenericDAO;

@Service
@WebService(serviceName = "birjanws")
public class BirjanServiceEndpoint implements BirjanWebService {

	@Autowired
	private IPersonService personService;
	
	@Autowired
	private IBirjanService birjanService;
	
	@Autowired
	private BirjanController controller;
	
	@Autowired
	private FilterDao filterDao;
	
	private IGenericDAO<Draw> drawDao;

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
	public String createGame(String lottery, String variant, String day,
			Object[][] data) {
		String hash = birjanService.createGame(lottery, variant, day, data);
		return hash;
	}

	@Override
	public Object[][] retrieveByHash(String hash) {
		Object[][] dataVector = controller.retrieveByHash(hash);
		return dataVector;
	}
	
	@Override
	public void createDraw(String lotteryName, String variant, String day,
			String[] data) {
		Preconditions.checkNotNull(lotteryName);
		Preconditions.checkNotNull(variant);
		Preconditions.checkNotNull(day);
		Preconditions.checkNotNull(data);
		if (data.length == 20) {
			Date date = BirjanUtils.getDate(day);	
			Lottery lottery = Lottery.valueOf((lotteryName+"_"+variant).toUpperCase());
			
			drawDao.create(new Draw(lottery, date, data));
		}
	}

	@Override
	public String[] retrieveDraw(String lottery, String variant, String day) {
		Date date = BirjanUtils.getDate(day);
		Lottery lott = Lottery.valueOf((lottery + "_" + variant).toUpperCase());
		List<Draw> list = filterDao.findDrawByFilter(null, lott, date);
		if (list != null && list.size() == 1)
			return list.get(0).getNumbers();
		return null;
	}

	@Resource(name="genericJpaDAO")
	public final void setEmployeeDao(final IGenericDAO<Draw> daoToSet) {
		drawDao = daoToSet;
		drawDao.setClazz(Draw.class);
	}

}
