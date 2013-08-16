package com.mpx.birjan.util;

import java.util.Map.Entry;
import java.util.concurrent.Future;

import org.joda.time.DateTime;

import com.google.common.base.Throwables;
import com.mpx.birjan.common.Lottery;

public final class Messages {

	public static final String CONTROL_GAMES_DEFAULT = "Control de Jugadas";
	
	public static String build(Entry<Lottery, Future<String[]>> result, DateTime now) {
		try {
			return "Premio: " + result.getKey().getLotteryName() + " " + 
					result.getKey().getVariantName() + " : " + now.getDayOfMonth() + "/" + now.getMonthOfYear() +
					": a la cabeza: " + result.getValue().get()[0];
		} catch (Throwable t) {
			t.printStackTrace();
			Throwables.propagate(t);
		}
		return "";
	}
	
}
