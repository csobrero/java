package com.mpx.birjan.service;

import java.util.Date;

import com.mpx.birjan.bean.Lottery;

public interface IBirjanService {

	void processWinners(final Lottery lottery, final Date date);

	String createGame(final Lottery lottery, Float[] betAmount,
			final String[] numbers, final Long personId);

	void setWinnerGame(final Lottery lottery, final String[] numbers,
			final Date date);

}
