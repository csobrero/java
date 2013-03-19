package com.mpx.lotery;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.ArrayUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.TimeOfDay;
import org.junit.Test;

import com.google.common.base.Preconditions;

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
	
	@Test
	public void arraycompare3() {
		
		String x = "1234222200014783474845897855";
		
		String c = "xxx5xx22xx01xx27xx5845896855";
		
		Map<Integer, Integer> hits = matchWinNumbers(x, c);
		
		System.out.println(hits);

	}

	private Map<Integer, Integer> matchWinNumbers(String patterns,
			String candidate) {
		Map<Integer, Integer> hits = null;
		int k = 0;
		for (int i = 3; i < 28; i += 4) {
			if (candidate.charAt(i) == patterns.charAt(i)) {
				k = 1;
				for (int j = (i - 1); j > (i - 4); j--) {
					if (candidate.charAt(j) == patterns.charAt(j))
						k++;
					else if (candidate.charAt(j) == 'x')
						break;
					else {
						k = 0;
						break;
					}
				}
				if (k > 0) {
					if (hits == null)
						hits = new HashMap<Integer, Integer>();
					hits.put((i + 1) / 4, k);
					k = 0;
				}
			}
		}
		return hits;
	}
	
	
	

	@Test
	public void arraycompare() {
		Random r = new Random();
		List<String> winList = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			winList.add(randomInteger(0, 9999, r));
		}

		Integer[] win = new Integer[] { 1223 };

	}

	private static String randomInteger(int aStart, int aEnd, Random aRandom) {
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		// get the range, casting to long to avoid overflow problems
		long range = (long) aEnd - (long) aStart + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * aRandom.nextDouble());
		return String.format("%04d", fraction + aStart);
	}
	
	@Test
	public void arraycompare2() {
		Random r = new Random();
		for (int i = 0; i < 100; i++) {
			System.out.println(randomInteger(0, 9, r));
			System.out.println();
		}
	}

}
