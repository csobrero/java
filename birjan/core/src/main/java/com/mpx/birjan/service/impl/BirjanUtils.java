package com.mpx.birjan.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.joda.time.DateTime;

import com.mpx.birjan.bean.Lottery;
import com.mpx.birjan.core.Rule;

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

	public static DateTime getDate(String day) {
		DateTime dt = now().minusDays(4);
		if(day!=null){
			for (int i = 0; i < 11; i++) {
				if(day.equals(String.valueOf(dt.getDayOfMonth()))){
					return dt;
				}
				dt = dt.plusDays(1);
			}
		}
		return null;
	}

	public static void mergeDraw(String[] destination, String[] source) {
		if (destination.length == source.length) {
			for (int i = 0; i < destination.length; i++) {
				if (!source[i].equals(""))
					if (destination[i].equals(""))
						destination[i] = source[i];
					else if (!destination[i].equals(source[i]))
						destination[i] = "";
			}
		}
	}

	public static List<String> retrieveVariantAvailability(String view,
			Rule[] rules, String day) {
		List<String> values = new ArrayList<String>();
		DateTime date = BirjanUtils.getDate(day);
		if (date != null) {
			DateTime today = now().toDateMidnight().toDateTime();
			DateTime tomorrow = today.plusDays(1);
			for (Rule rule : rules) {
				DateTime to = rule.getTo(today);
				if (view.equals("ticket") && date.isAfter(today)
						&& (date.isBefore(to) || date.isAfter(tomorrow)))
					values.add(rule.getVariant().toString());
				else if (view.equals("draw") && date.isBefore(tomorrow)
						&& (date.isAfter(to) || date.isBefore(today)))
					values.add(rule.getVariant().toString());
			}
		}
		return values;
	}

	public static boolean isValid(Lottery lottery, DateTime date) {
		DateTime today = now().toDateMidnight().toDateTime();
		DateTime tomorrow = today.plusDays(1);
		DateTime to = lottery.getRule().getTo(today);
		if (date.isAfter(today) && (date.isBefore(to)) || date.isAfter(tomorrow))
			return true;
		return false;
	}

	public static DateTime now() {
		return new DateTime(new Date());
	}

}
