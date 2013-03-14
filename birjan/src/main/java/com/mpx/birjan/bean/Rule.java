package com.mpx.birjan.bean;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import com.mpx.birjan.bean.Rule.Nacional.VARIANT;

public abstract class Rule {

	public abstract DateTime getFrom(DateTime date);

	public abstract DateTime getTo(DateTime date);
	
	public static final Rule NAP = new Nacional(VARIANT.PRIMERA);
	public static final Rule NAM = new Nacional(VARIANT.MATUTINA);
	public static final Rule NAV = new Nacional(VARIANT.VESPERTINA);
	public static final Rule NAN = new Nacional(VARIANT.NOCTURNA);

	/**
	 * Rules for NATIONAL lottery.
	 */
	public static class Nacional extends Rule {

		public enum VARIANT {
			PRIMERA(21, 0, 11, 30), MATUTINA(11, 30, 14, 00), VESPERTINA(14, 0,
					17, 30), NOCTURNA(17, 30, 21, 0);

			VARIANT(int hourFrom, int minutesFrom, int hourTo, int minutesTo) {
				this.hourFrom = hourFrom;
				this.minutesFrom = minutesFrom;
				this.hourTo = hourTo;
				this.minutesTo = minutesTo;
			}

			private final int hourFrom;
			private final int minutesFrom;
			private final int hourTo;
			private final int minutesTo;
		}

		private VARIANT variant;

		public Nacional(VARIANT variant) {
			this.variant = variant;
		}

		@Override
		public DateTime getFrom(DateTime date) {
			if (date.dayOfWeek().get() == DateTimeConstants.SUNDAY
					|| variant.equals(VARIANT.VESPERTINA)
					&& date.dayOfWeek().get() == DateTimeConstants.SATURDAY)
				return null;

			return new DateTime(date.year().get(), date.monthOfYear().get(),
					date.dayOfMonth().get(), variant.hourFrom,
					variant.minutesFrom, variant.hourTo, variant.minutesTo);
		}

		@Override
		public DateTime getTo(DateTime date) {
			if (date.dayOfWeek().get() == DateTimeConstants.SUNDAY
					|| variant.equals(VARIANT.VESPERTINA)
					&& date.dayOfWeek().get() == DateTimeConstants.SATURDAY)
				return null;

			int hourTo = variant.hourTo;
			if (variant.equals(VARIANT.MATUTINA)
					&& date.dayOfWeek().get() == DateTimeConstants.SATURDAY)
				hourTo = 15;

			return new DateTime(date.year().get(), date.monthOfYear().get(),
					date.dayOfMonth().get(), variant.hourFrom,
					variant.minutesFrom, hourTo, variant.minutesTo);
		}
	}

}
