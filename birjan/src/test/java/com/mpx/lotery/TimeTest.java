package com.mpx.lotery;

import org.joda.time.TimeOfDay;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


public class TimeTest {

	@Test
	public void time() {
		TimeOfDay tod = new TimeOfDay(23, 59, 59, 999);
		
		System.out.println(tod);
	}

}
