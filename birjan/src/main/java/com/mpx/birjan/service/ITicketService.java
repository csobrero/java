package com.mpx.birjan.service;

import com.mpx.birjan.bean.Lottery;

public interface ITicketService {

	long createGame(final Lottery lottery, final String numbers, float betAmount, Long personId);

//	long updateGame(long gameId, Lottery lottery, final String numbers);

//	void invalidGame(long gameId);
//
//	long createWager(float betAmount, long gameId, Long personId);

}
