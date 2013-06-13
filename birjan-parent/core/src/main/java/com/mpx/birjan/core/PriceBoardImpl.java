package com.mpx.birjan.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.concurrent.Future;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import com.mpx.birjan.common.Lottery;


public class PriceBoardImpl implements PriceBoardWebService {

	final Logger logger = LoggerFactory.getLogger(PriceBoardImpl.class);
	
	@Override
	public Future<String[]> retrieve(Lottery lottery) {
		return retrieve(lottery, new DateTime());
	}

	@Async
	@Override
	public Future<String[]> retrieve(Lottery lottery, DateTime date) {
		return new AsyncResult<String[]>(ajaxCall(lottery, date));
	}

	private String[] ajaxCall(Lottery lottery, DateTime date) {
		System.setProperty("http.proxyHost", "webproxy.wlb2.nam.nsroot.net");
		System.setProperty("http.proxyPort", "8080");
		try {
			final URL url = new URL("http://www.vivitusuerte.com/datospizarra_loteria.php");
			final URLConnection urlConnection = url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			urlConnection.connect();
			final OutputStream outputStream = urlConnection.getOutputStream();
			outputStream.write(("fecha=2013/006/11&loteria=25").getBytes("UTF-8"));
			outputStream.flush();
			final InputStream in = urlConnection.getInputStream();
			Document doc = Jsoup.parse(in, urlConnection.getContentEncoding(), "http://www.vivitusuerte.com");
			Elements elements = doc.select(":matchesOwn(\\d{4})");
			@SuppressWarnings("unchecked")
			Collection<String> collect = CollectionUtils.collect(elements, new Transformer() {
				public Object transform(Object input) {
					return ((Element) input).childNode(0).outerHtml().replaceAll("\\s","");
				}
			});
			for (Element element : elements) {
				System.out.println(element.childNode(0).outerHtml().replaceAll("\\s",""));
			}
			return collect.toArray(new String[collect.size()]);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new String[]{};
	}

}
