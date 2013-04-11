package com.mpx.birjan.service.impl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpx.birjan.bean.Wrapper;
import com.mpx.birjan.core.TransactionalManager;

@Service
@WebService(serviceName = "birjanws", endpointInterface="com.mpx.birjan.service.impl.BirjanWebService")
public class BirjanServiceEndpoint implements BirjanWebService {

	@Autowired
	private TransactionalManager txManager;

	@Override
	public String[] populateCombo(String view, String combo, String day) {
		return txManager.getComboOptions(view, combo, day);
	}

	@Override
	public String createGame(String lottery, String variant, String day,
			Object[][] data) {
		String hash = txManager.createGame(lottery, variant, day, data);
		return hash;
	}

	@Override
	public Object[][] retrieveByHash(String hash) {
		Object[][] dataVector = txManager.retrieveByHash(hash);
		return dataVector;
	}

	@Override
	public void createDraw(String lottery, String variant, String day,
			String[] data) {
		txManager.createDraw(lottery, variant, day, data);
	}

	@Override
	public String[] retrieveDraw(String lottery, String variant, String day) {
		return txManager.retrieveDraw(lottery, variant, day);
	}

	@Override
	public void validateDraw(String lottery, String variant, String day) {
		txManager.validateDraw(lottery, variant, day);
		
	}

	@Override
	public Wrapper[] retriveGames(String lottery, String variant, String day) {
		return txManager.retriveGames(lottery, variant, day);
	}

	@Override
	public boolean isDevelopment() {
		return txManager.isDevelopment();
	}

}
