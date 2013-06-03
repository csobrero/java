package com.mpx.birjan.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

import com.mpx.birjan.common.Lottery;
import com.mpx.birjan.common.Rule;

public class BirjanUtils {

	public static String decode(long l) {
		return null;
	}

	public static String hashFor(String userName, Date created) {
		DateTime dt = new DateTime(created);

		int[] n = new int[3];
		n[0] = dt.getMonthOfYear();
		n[1] = dt.getDayOfMonth();
		n[2] = dt.getHourOfDay();
		String hex = Integer.toHexString(
				dt.getSecondOfMinute() * dt.getMinuteOfHour()).toUpperCase();

		String hash = userName.substring(0, 2).toUpperCase();
		for (int i : n) {
			hash += (char) ((i < 10) ? i + 48 : i + 55);
		}

		String millis = String.valueOf(dt.getMillis());
		return hash + hex + millis.substring(millis.length()-3,millis.length()-1);
	}

	public static DateTime getDate(String day) {
		DateTime dt = new DateTime().minusDays(20);//no more than 4 days modify this.
		if(day!=null){
			for (int i = 0; i < 28; i++) {
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
			DateTime today = new DateMidnight().toDateTime();
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
		DateTime today = new DateMidnight().toDateTime();
		DateTime tomorrow = today.plusDays(1);
		DateTime to = lottery.getRule().getTo(today);
		if (date.isAfter(today) && (date.isBefore(to)) || date.isAfter(tomorrow))
			return true;
		return false;
	}

	public static List<Object> retrieveVariantAvailability(Lottery[] lottery, String day) {
		List<Object> results = new ArrayList<Object>();
		DateTime date = BirjanUtils.getDate(day);
		DateTime today = new DateMidnight().toDateTime();
		DateTime tomorrow = today.plusDays(1);
		for (Lottery lott : lottery) {
			DateTime to = lott.getRule().getTo(today);
			if (date.isAfter(today)
					&& (date.isBefore(to) || date.isAfter(tomorrow)))
				results.add(true);
			else
				results.add(false);
		}
		return results;

	}

	public static Object[][] toArray(List<List<Object>> list) {
		if (list != null && !list.isEmpty()) {
			Object[][] results = new Object[list.size()][];
			for (int i = 0; i < list.size(); i++) {
				results[i] = list.get(i).toArray(new Object[] {});
			}
			return results;
		}
		return null;
	}

}
