package com.mpx.birjan.service;

import java.util.Date;

import com.mpx.birjan.bean.Lottery;

public interface IBirjanService {

	void processWinners(final Lottery lottery, final Date date);

	void setWinnerGame(final Lottery lottery, final Date date,
			final Object[][] data);

	String createGame(final String lottery, final String variant,
			final String day, final Object[][] data);

}
