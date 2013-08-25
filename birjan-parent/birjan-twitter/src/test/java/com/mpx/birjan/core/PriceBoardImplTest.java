package com.mpx.birjan.core;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.mpx.birjan.common.Lottery;

public class PriceBoardImplTest {

	@Test
	public void test1() throws Exception {
		
		PriceBoardImpl tested = new PriceBoardImpl();
		
		String[] result = call(tested, Lottery.NACIONAL_PRIMERA, new DateTime());
		
		showArray(result);
		
		assertNotNull(result);
		assertTrue(result.length == 20);
	}

	@Test
	public void test2() throws Exception {
		
		PriceBoardImpl tested = new PriceBoardImpl();
		
		String[] result = call(tested, Lottery.NACIONAL_MATUTINA, new DateTime());
		
		showArray(result);
		
		assertNotNull(result);
		assertTrue(result.length == 20);
	}

	private String[] call(PriceBoardImpl tested, Lottery lottery, DateTime date) throws Exception {
		String[] result = Whitebox.<String[]>invokeMethod(tested, "ajaxCall", new Class[]{Lottery.class, DateTime.class}, 
				lottery, date);
		return result;
	}

	private void showArray(String[] result) {
		for (String string : result) {
			System.out.println(string);
		}
	}

}
