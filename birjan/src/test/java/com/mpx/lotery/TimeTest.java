package com.mpx.lotery;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.TimeOfDay;
import org.junit.Test;

public class TimeTest {

	@Test
	public void time() {
		TimeOfDay tod = new TimeOfDay(23, 59, 59, 999);

		System.out.println(tod);

		Days d = Days.SEVEN;

		System.out.println(d);

		DateTime dt = new DateTime(new Date());

		DateTime dateTime = new DateTime(dt.year().get(), dt.monthOfYear()
				.get(), dt.dayOfMonth().get(), 23, 59, 59, 999);
		System.out.println(dateTime);
	}

}
