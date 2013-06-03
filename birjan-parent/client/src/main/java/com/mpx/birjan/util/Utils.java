package com.mpx.birjan.util;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

public class Utils {

	public static final Locale locale = new Locale("es");

	public static class Combos {

		public static String[] buildDays(int daysQty, boolean up) {
			String[] days = new String[daysQty];
			DateTime dt = new DateTime();
			for (int i = 0; i < days.length; i++) {
				if (dt.getDayOfWeek() == DateTimeConstants.SUNDAY)
					dt = (up) ? dt.plusDays(1) : dt.minusDays(1);
				days[i] = dt.toString("EEEE", locale).toUpperCase() + "  " + dt.getDayOfMonth() + "/"
						+ dt.getMonthOfYear();
				dt = (up) ? dt.plusDays(1) : dt.minusDays(1);
			}
			return days;
		}

		public static String getDay(final String comboOption) {
			return comboOption.split("  ")[1].split("/")[0];
		}

		public static String getMonth(final String comboOption) {
			return comboOption.split("  ")[1].split("/")[1];
		}
	}

}
