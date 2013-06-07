package com.mpx.lotery;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mpx.birjan.bean.TweetBet;
import com.mpx.birjan.tweeter.TweetParser;

public class TweetParserTest {

	@Test
	public void test1() {

		String tw = "22 1 1.5 N";

		boolean b = tw.matches(TweetParser.tweetPattern);	
		
		assertTrue(b);
	}
	
	
	@Test
	public void test2() {

		String[] tws = { "22 1 1 N P", "22 1 1 N", "22 1 N P", "22 1 N", 
				"22 1 1.5 N", "22 1.5 N", "22 1,5 N", "22 1 NP PMVN", "22 1 NP T" ,"22 1 T T"}; 

		boolean b = true;
		int i = 0;
		for (; b && i < tws.length; i++) {
			b = tws[i].matches(TweetParser.tweetPattern);
		}
		assertTrue("fail ["+(i-1)+"]: " + tws[i-1], b);
	}
	
	@Test
	public void test3() {

		String[] tws = { "22 1 1 N P", "22 1 1 N", "22 1 N P", "22 1 N", 
				"22 1 NP VN", "22 1 NP PMVN","22 1 NP T" ,"22 1 T T",
				"22 1 1.5 N", "22 1.5 NP", "22 1,5 N T"}; 

		TweetParser twParser = new TweetParser();
		
		for (String tw : tws) {
			TweetBet bet = twParser.decode(tw);
			System.out.println(bet);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
