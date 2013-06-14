package com.mpx.birjan.core;

import java.util.concurrent.Future;

import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Async;

import com.mpx.birjan.common.Lottery;

public interface PriceBoardWebService {

	@Async
	Future<String[]> retrieve(Lottery lottery);
	
	@Async
	Future<String[]> retrieve(Lottery lottery, DateTime date);

}
