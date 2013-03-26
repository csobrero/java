package com.mpx.birjan.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import com.mpx.birjan.core.Rule.Nacional.VARIANT;

public abstract class Rule {

	public abstract DateTime getFrom(DateTime date);

	public abstract DateTime getTo(DateTime date);
	
	public abstract float calculateWinAmount(float betAmount, Map<Integer, Integer> winPositions);
	
	public static final Rule NAP = new Nacional(VARIANT.PRIMERA);
	public static final Rule NAM = new Nacional(VARIANT.MATUTINA);
	public static final Rule NAV = new Nacional(VARIANT.VESPERTINA);
	public static final Rule NAN = new Nacional(VARIANT.NOCTURNA);
	
	public static final int[] defaultWinRatios = {7,70,650,3500};

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

			public static List<String> toList() {
				List<String> list = new ArrayList<String>();
				for (VARIANT variant : Nacional.VARIANT.values())
					list.add(variant.toString());
				return list;
			}
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
	
		@Override
		public float calculateWinAmount(float betAmount, Map<Integer, Integer> winPositions){
			
			for (Entry<Integer, Integer> entry : winPositions.entrySet()) {
				betAmount=betAmount*defaultWinRatios[entry.getValue()];
			}
			
			return betAmount;
		}
	}

	public static class Provincia extends Nacional{

		public Provincia(VARIANT variant) {
			super(variant);
		}
		
	}
}
