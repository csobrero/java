package com.mpx.birjan.tweeter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.joda.time.DateTime;

import com.google.common.base.Preconditions;
import com.mpx.birjan.bean.BirjanUtils;
import com.mpx.birjan.bean.TwitterBet;

public final class TwitterParser {

	public static String tweetBetPattern = "\\d{1,4}( \\d{1,2})? \\d([\\.,]\\d)? ([NP]{1,2}|T)( ([PMVN]{1,4}|T))?( \\d{1,2})?\\.*";
	public static String tweetDeletePattern = "BORRA [0-9|A-Z]{5}\\.*";
	public static String tweetShowPattern = "VER [0-9|A-Z]{5}\\.*";
	public static String tweetPayPattern = "PAGA [0-9|A-Z]{5}\\.*";

	public static final List<String> lotteryNames = Arrays.asList("NACIONAL", "PROVINCIA");
	public static final List<String> variantNames = Arrays.asList("PRIMERA", "MATUTINA", "VESPERTINA", "NOCTURNA");

	public static TwitterBet unmarshalBet(String tweet) {

		String tw=tweet.replace(",", ".").replaceAll(" +", " ").toUpperCase();
		
		Preconditions.checkArgument(tw.matches(tweetBetPattern), "TWEET INVALIDO");

		String str = tw.split(" ([NP]{1,2}|T)")[0];
		String[] data = str.split(" ");
		String[] lottery = tw.substring(str.length()+1).split(" ");

		String number = "xxx"+data[0];
		number = number.substring(number.length()-4, number.length());
		Integer position = data.length > 2 ? Integer.parseInt(data[1]) : 1;
		Float amount = data.length > 2 ? Float.parseFloat(data[2]) : Float.parseFloat(data[1]);
		DateTime date = lottery.length>2?BirjanUtils.getDate(lottery[2]):new DateTime();

		Collection<String> lotteries = select(lotteryNames, lottery[0]);
		Collection<String> variants = lottery.length > 1 ? select(variantNames, lottery[1]) : null;

		TwitterBet bet = new TwitterBet(number, position, amount, date, lotteries, variants);

		return bet;
	}
	
	public static String unmarshal(String tweet) {
		return tweet.replace(".", "").split(" ")[1];
	}
	
	

	@SuppressWarnings("unchecked")
	private static Collection<String> select(final Collection<String> collection, final String string) {
		if (string.contains("T")) {
			return collection;
		}
		return CollectionUtils.select(collection, new Predicate() {
			public boolean evaluate(Object object) {
				return string.contains(((String) object).charAt(0) + "");
			}
		});
	}

}
