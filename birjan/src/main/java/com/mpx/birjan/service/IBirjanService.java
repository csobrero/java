package com.mpx.birjan.service;

import java.util.Date;

import com.mpx.birjan.bean.Lottery;

public interface IBirjanService {

	void processWinners(final Lottery lottery, final Date date);

	public long createGame(final Lottery lottery, final String numbers,
			float betAmount, final Long personId);

	void setWinnerGame(final Lottery lottery, final String numbers,
			final Date date);

}
