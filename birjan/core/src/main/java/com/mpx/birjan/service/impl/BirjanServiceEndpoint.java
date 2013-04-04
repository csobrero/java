package com.mpx.birjan.service.impl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpx.birjan.core.TransactionalManager;

@Service
@WebService(serviceName = "birjanws")
public class BirjanServiceEndpoint implements BirjanWebService {

	@Autowired
	private TransactionalManager txManager;

	@Override
	public String[] getComboOptions(String combo, String day) {
		return txManager.getComboOptions(combo, day);
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

}
