package com.mpx.birjan.bean;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

public class Matutina extends Rule {

	public DateTime getFrom(DateTime date) {
		if (date.dayOfWeek().get() == DateTimeConstants.SUNDAY) 
			return null;
		
		date = date.minusDays(1); //yesterday
		return new DateTime(date.year().get(), date.monthOfYear().get(), date
				.dayOfMonth().get(), 21, 0, 0, 0); // yesterday 21h
	}

	public DateTime getTo(DateTime date) {
		if (date.dayOfWeek().get() == DateTimeConstants.SUNDAY) 
			return null;
		
		return new DateTime(date.year().get(), date.monthOfYear().get(), date
				.dayOfMonth().get(), 11, 30, 0, 0); // today 11.30h
	}

}
