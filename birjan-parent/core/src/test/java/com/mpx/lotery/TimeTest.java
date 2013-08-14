package com.mpx.lotery;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.mpx.birjan.bean.BirjanUtils;
import com.mpx.birjan.common.Rule;

public class TimeTest {

	@Test
	public void time() throws Exception {
//		TimeOfDay tod = new TimeOfDay(23, 59, 59, 999);
//
//		System.out.println(tod);
//
//		Days d = Days.SEVEN;
//
//		System.out.println(d);
//
//		DateTime dt = new DateTime();
//		
////		dt = dt.plusDays(-1);
//		
//
//		DateTime dateTime = new DateTime(dt.year().get(), dt.monthOfYear()
//				.get(), dt.dayOfMonth().get(), 23, 59, 59, 999);
//		
//		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		
		DateTime dt = new DateTime(2013,1,1,1,1,1,0);
		
		
		
		System.out.println(dt);
//		DateTimeUtils.setCurrentMillisFixed(dt.getMillis());
		
		DateTime dateTime = new DateTime();
		
		long l = dt.getMillis()-dateTime.getMillis();
		
		DateTimeUtils.setCurrentMillisOffset(l);
		
//		for (int i = 0; i < 100; i++) {
//			System.out.println(new DateMidnight().toDateTime());
//			
//			Thread.currentThread().sleep(1000);
//		}
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			@SuppressWarnings("static-access")
			public void run() {
				for (int i = 0; i < 100; i++) {
					System.out.println(new DateTime());
					
					try {
						Thread.currentThread().sleep(1000);
					} catch (InterruptedException e) {}
				}
				
			}
		});
		thread.run();
		
//		System.out.println(fmt.print(dateTime));
	}
	
	@Test
	public void arraycompare3() {
		
		DecimalFormat df = new DecimalFormat("0.##");
		float f = 0.799f;
//		Float g = null;
		System.out.println(df.format(f));
		
		DateTime dt = new DateTime();
		
		
		System.out.println(dt);
		System.out.println(dt.getZone());
		//-Duser.timezone="America/Argentina/Buenos_Aires"

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
	
	@Test
	public void sasas(){
		String[] s = new String[20];
		s[1] = "a";
		
		if(s[0]!=null)
			System.out.println("ola");
		
		System.out.println("chau");
	}
	
	@Test
	public void tweetTest() {
		
		String[] tws = {"22 1 N","22 1 N P","22 1 1 N","22 1 1 N P"};
		
		for (String string : tws) {
			
		}
		
		String tw = "22 11 1.5 NP PMVN";
		
		boolean b = tw.matches("\\d{1,4} \\d{1,2}? \\d(\\.\\d)? [NP]{1,2} [PMVN*]{1,4}?");
		
		assertTrue(b);
		
//		TweetParser parser = new TweetParser();
//		parser.parse(tw);
		
	}
	
	@Test
	public void tweetTest2() {
		
		String tw = "efdsef eff se5fswef sedfwsf";
		
		boolean b = tw.matches(".^(6).*");
		
		assertTrue(b);
		
//		TweetParser parser = new TweetParser();
//		parser.parse(tw);
		
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
//		List<Map<Integer, Integer>> l = new ArrayList<Map<Integer,Integer>>();
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
//		String str = "";
//		for (int i = 0; i < 20; i++) {
//			str += randomInteger(0, 9999, r);
//		}
//		return str;
		
		String[] win = new String[100];
		Integer[] s = {77, 34, 87, 00, 23, 77, 88, 29, 92, 57};
		for (int i = 0; i < s.length; i++) {
			win[s[i]] = i+"";
		}
		
		
		Integer[] t = {92, 11, 34};
		String w = "";
		for (Integer in : t) {
			w+=win[in];
		}
		
		System.out.println(w);
		
		
		
		return null;
	}
	
	@Test
	public void efrqw() {
		String[] win = new String[100];
		Integer[] s = { 77, 34, 87, 00, 23, 77, 88, 29, 92, 57 };
		for (int i = 0; i < s.length; i++) {
			win[s[i]] = i + "";
		}

		Integer[] t = { 92, 11, 34 };
		String w = "";
		for (Integer in : t) {
			w += win[in]!=null?win[in]:"";
		}

		System.out.println(w);
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
	
	@Test
	public void test12(){
//		for (int i = 0; i < 3600; i++) {
//			String hex = "00"+Integer.toHexString(i);
//			System.out.println(hex.substring(hex.length()-3, hex.length()-1) + " :"+i);
//		}
		float f = 2.0f;
		
		System.out.println(String.format("%.2f",f));
	}
	
	/**
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@Test
	public void test1() throws UnsupportedEncodingException, IOException {
		System.setProperty("http.proxyHost", "webproxy.wlb2.nam.nsroot.net");
		System.setProperty("http.proxyPort", "8080");
		
		DateTime dt = new DateTime();
		dt = dt.minusDays(1);
		
		final URL url = new URL("http://www.vivitusuerte.com/datospizarra_loteria.php");
		final URLConnection urlConnection = url.openConnection();
		urlConnection.setDoOutput(true);
		urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		urlConnection.connect();
		final OutputStream outputStream = urlConnection.getOutputStream();
		outputStream.write(("fecha="+dt.getYear()+"/"+dt.getMonthOfYear()+"/"+dt.getDayOfMonth()+"&loteria=25").getBytes("UTF-8"));
		outputStream.flush();
		final InputStream in = urlConnection.getInputStream();
//		String encoding = urlConnection.getContentEncoding();
//		encoding = encoding == null ? "UTF-8" : encoding;
//		String body = IOUtils.toString(in, encoding);
		Document doc = Jsoup.parse(in, urlConnection.getContentEncoding(), "http://www.vivitusuerte.com");
//		Elements newsHeadlines = doc.select("html body table tbody tr:eq(1) td table tbody tr td strong div");
//		Elements line1 = doc.select("html body table tbody tr:eq(1) td table tbody tr:eq(1) td:eq(1) div font");
//		Elements line2 = doc.select("html body table tbody tr:eq(1) td table tbody tr:eq(1) td:eq(3) div");
//		Elements line3 = doc.select("html body table tbody tr:eq(1) td table tbody tr:eq(2) td:eq(1) div font");
		Elements elements = doc.select(":matchesOwn(\\d{4})");
		
		String[] list = new String[elements.size()];
		for (int i = 0; i < list.length; i++) {
			list[i + ((i % 2 == 0) ? 0 : 9) - ((i - (i/20)*20)/ 2)] = elements.get(i).childNode(0)
					.outerHtml().replaceAll("\\s", "");
		}
		for (String string : list) {
			System.out.println(string);
		}
	}
	
}
