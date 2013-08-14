package com.mpx.birjan.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

public abstract class Rule {

	public abstract DateTime getTo();
	public abstract DateTime getTo(DateTime date);
	public static final Rule NAP = new Nacional(VARIANT.PRIMERA);
	public static final Rule NAM = new Nacional(VARIANT.MATUTINA);
	public static final Rule NAV = new Nacional(VARIANT.VESPERTINA);
	public static final Rule NAN = new Nacional(VARIANT.NOCTURNA);
	public static final Rule PRP = new Provincia(VARIANT.PRIMERA);
	public static final Rule PRM = new Provincia(VARIANT.MATUTINA);
	public static final Rule PRV = new Provincia(VARIANT.VESPERTINA);
	public static final Rule PRN = new Provincia(VARIANT.NOCTURNA);
	public static final Rule[] National = new Rule[] { NAP, NAM, NAV, NAN };
	public static final Rule[] Provincia = new Rule[] { PRP, PRM, PRV, PRN };
	
	public static final int[] defaultWinRatios = {7,70,650,3500};

	public static List<Rule> check(Rule[] rules, DateTime date) {
		List<Rule> list = new ArrayList<Rule>();
		if (date.isAfterNow())
			list = Arrays.asList(rules);
		else
			for (Rule rule : rules) {
				DateTime to = rule.getTo(date);
				if (date.isBefore(to))
					list.add(rule);
			}
		return list;
	}
	
	public enum VARIANT {
		PRIMERA(11, 30), MATUTINA(14, 00), VESPERTINA(17, 30), NOCTURNA(21, 0);

		VARIANT(int hourTo, int minutesTo) {
			this.hourTo = hourTo;
			this.minutesTo = minutesTo;
		}
		private final int hourTo;
		private final int minutesTo;
	}

	public Rule(VARIANT variant) {
		this.variant = variant;
	}

	protected VARIANT variant;

	public final VARIANT getVariant() {
		return variant;
	}
	
	
	/**
	 * Rules for NATIONAL lottery.
	 */
 	public static class Nacional extends Rule {

		public Nacional(VARIANT variant) {
			super(variant);
		}
		
		@Override
		public DateTime getTo() {
			return getTo(new DateTime());
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
					date.dayOfMonth().get(), hourTo, variant.minutesTo, 0 ,0);
		}
	}

	public static class Provincia extends Nacional{

		public Provincia(VARIANT variant) {
			super(variant);
		}
		
	}
}
