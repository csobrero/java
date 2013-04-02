package com.mpx.birjan.service.impl;

import java.util.Date;

import org.joda.time.DateTime;

public class BirjanUtils {

	public static String decode(long l) {
		return null;
	}

	public static String hashFor(String userId, Date date) {
		DateTime dt = new DateTime(date);

		int[] n = new int[3];
		n[0] = dt.getMonthOfYear();
		n[1] = dt.getDayOfMonth();
		n[2] = dt.getHourOfDay();
		String hex = Integer.toHexString(
				dt.getSecondOfMinute() * dt.getMinuteOfHour()).toUpperCase();

		String hash = userId.substring(0, 2);
		for (int i : n) {
			hash += (char) ((i < 10) ? i + 48 : i + 55);
		}

		return hash + hex;
	}

	public static Date getDate(String day) {
		DateTime dt = new DateTime(new Date()).minusDays(-4);
		if(day!=null){
			for (int i = 0; i < 11; i++) {
				if(day.equals(dt.getDayOfMonth())){
					return dt.toDate();
				}
				dt.plusDays(1);
			}
		}
		return null;
	}

}
