package com.mpx.birjan.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.mpx.birjan.common.Lottery;

@Service
public class PriceBoardImpl implements PriceBoardWebService {

	final Logger logger = LoggerFactory.getLogger(PriceBoardImpl.class);
	
	static Map<String, Integer> lookup;
	
	static{
		lookup = new HashMap<String, Integer>();
		lookup.put("NACIONAL", 25);
		lookup.put("PROVINCIA", 24);
		lookup.put("PRIMERA", 20);
		lookup.put("MATUTINA", 40);
		lookup.put("VESPERTINA", 60);
		lookup.put("NOCTURNA", 80);
	}
	
	@Override
	public Future<String[]> retrieve(Lottery lottery) {
		return retrieve(lottery, new DateTime());
	}

	@Async
	@Override
	public Future<String[]> retrieve(Lottery lottery, DateTime date) {
		String[] ajaxCall = ajaxCall(lottery, date);
		return new AsyncResult<String[]>(ajaxCall);
	}

	private String[] ajaxCall(Lottery lottery, DateTime date) {
		System.setProperty("http.proxyHost", "webproxy.wlb2.nam.nsroot.net");
		System.setProperty("http.proxyPort", "8080");
		final Integer lotteryNumber = lookup.get(lottery.getLotteryName());
		final Integer idx = lookup.get(lottery.getVariantName());
		String[] list = new String[]{};
		try {
			final URL url = new URL("http://www.vivitusuerte.com/datospizarra_loteria.php");
			final URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			connection.connect();
			final OutputStream outputStream = connection.getOutputStream();
			outputStream.write(("fecha=" + date.getYear() + "/" + date.getMonthOfYear() + "/"
					+ date.getDayOfMonth() + "&loteria=" + lotteryNumber).getBytes("UTF-8"));
			outputStream.flush();
			final InputStream in = connection.getInputStream();
			final Document doc = Jsoup.parse(in, connection.getContentEncoding(), "http://www.vivitusuerte.com");
			final Elements matches = doc.select(":matchesOwn(\\d{4})");
			if(matches!=null&&matches.size()<=idx){
				List<Element> elements = matches.subList(idx-20, idx);
				list = new String[elements.size()];
				for (int i = 0; i < list.length; i++) {
					list[i + ((i % 2 == 0) ? 0 : 9) - ((i - (i/20)*20)/ 2)] = elements.get(i).childNode(0)
							.outerHtml().replaceAll("\\s", "");
				}
//				Thread.currentThread().sleep(600000);
			}
		} catch (Exception e) {
			logger.error("Exception: " + e.getClass().getName() + " || Message:  " + e.getMessage());
		}
		return list;
	}

}
