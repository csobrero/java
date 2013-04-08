package com.mpx.lotery;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.TimeOfDay;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import com.google.common.base.Preconditions;
import com.mpx.birjan.core.Rule;
import com.mpx.birjan.service.impl.BirjanUtils;

public class TimeTest {

	@Test
	public void time() {
		TimeOfDay tod = new TimeOfDay(23, 59, 59, 999);

		System.out.println(tod);

		Days d = Days.SEVEN;

		System.out.println(d);

		DateTime dt = new DateTime(new Date());
		
//		dt = dt.plusDays(-1);
		

		DateTime dateTime = new DateTime(dt.year().get(), dt.monthOfYear()
				.get(), dt.dayOfMonth().get(), 23, 59, 59, 999);
		
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		
		System.out.println(fmt.print(dateTime));
	}
	
//	@Test
	public void arraycompare3() {
		
		String x = "12342222000147834748123422220001478347481234222200014783474812342222000147834748";
		
		String c = "xxx5xx22xx01xx27xx58xxx5xx22xx01xx27xx58xxx5xx22xx01xx27xx58xxx5xx22xx01xx27xx58";
		
		Map<Integer, Integer> hits = matchWinNumbers(x, c);
		
		System.out.println(hits);

	}

	private Map<Integer, Integer> matchWinNumbers(String patterns,
			String candidate) {
		Map<Integer, Integer> hits = null;
		int k = 0;
		for (int i = 3; i < 80; i += 4) {
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
	
	
	

//	@Test
	public void arraycompare() {
		Random r = new Random();
		
		String x = getRandomInteger(r);
		
		List<String> list = new ArrayList<String>();
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			list.add(getRandomInteger(r));
		}
		System.out.println("time taken: " + (System.currentTimeMillis()-start) + "ms");
		
		
		start = System.currentTimeMillis();
		int winners = 0;
		List<Map<Integer, Integer>> l = new ArrayList<Map<Integer,Integer>>();
		for (String c : list) {
			Map<Integer, Integer> m = matchWinNumbers(x, c);
			if(m!=null){
				winners += m.size();
				
			}
		}
		System.out.println("winners: " + winners);
		
		
		//System.out.println(list.size());
		
		

	}

	private String getRandomInteger(Random r) {
		String str = "";
		for (int i = 0; i < 20; i++) {
			str += randomInteger(0, 9999, r);
		}
		return str;
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
	public void arraycompare22() {
		List<String> list = BirjanUtils.retrieveVariantAvailability("ticket", Rule.National, "6");
		
		System.out.println(list);
		

		list = BirjanUtils.retrieveVariantAvailability("draw", Rule.National, "6");
		
		System.out.println(list);
	}
	
	@Test
	public void arraycompare2() {
//		String random = RandomStringUtils.random(6, "QWERTY");
//		System.out.println(random);
//		
		String s = "A";
		char[] charArray = s.toCharArray();
		for (char d : charArray) {
			System.out.println((int)d);			
		}
//		
//		for (int i = 48; i < 91; i++) {
//			System.out.println((char)i);
//		}  XXXXXXXX  CC
//		
//		int x = 0;
		
		DateTime dt = new DateTime(2012, 12, 1, 23, 59, 59, 0);
		System.out.println(dt.toString());
		
		int[] n = new int[3];
//		n[0] = dt.getYear()-2000;
		n[0] = dt.getMonthOfYear();
		n[1] = dt.getDayOfMonth();
		n[2] = dt.getHourOfDay();
		String hex = Integer.toHexString(dt.getSecondOfMinute()*dt.getMinuteOfHour()).toUpperCase();
		
		String hash = "XX"; 
		for (int i : n) {
			hash+=(char)((i<10)?i+48:i+55);
		}
		
		System.out.println(hash+hex);
		
//		for (int i = 0; i < 36; i++) {
//			char c = (char)((i<10)?i+48:i+55);
//			System.out.println(c);
//		}
		
		
	}

}
