package com.mpx.birjan.service;

import com.mpx.birjan.bean.Lottery;

public interface ITicketService {

	long createGame(Lottery lottery, final Integer[] numbers);

	long updateGame(long gameId, Lottery lottery, final Integer[] numbers);

	void invalidGame(long gameId);

	long createWager(float betAmount, long gameId, Long personId);

}
