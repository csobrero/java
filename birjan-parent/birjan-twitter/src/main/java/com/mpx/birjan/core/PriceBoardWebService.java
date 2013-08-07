package com.mpx.birjan.core;

import java.util.concurrent.Future;

import org.joda.time.DateTime;

import com.mpx.birjan.common.Lottery;

public interface PriceBoardWebService {

	Future<String[]> retrieve(Lottery lottery, DateTime date);

}
