package com.mpx.birjan.tweeter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import com.google.common.base.Preconditions;
import com.mpx.birjan.bean.TweetBet;

public class TweetParser {

	public static String tweetPattern = "\\d{1,4}( \\d{1,2})? \\d([\\.,]\\d)? ([NP]{1,2}|T)( ([PMVN]{1,4}|T))?";

	public static final List<String> lotteryNames = Arrays.asList("NACIONAL", "PROVINCIA");
	public static final List<String> variantNames = Arrays.asList("PRIMERA", "MATUTINA", "VESPERTINA", "NOCTURNA");

	public TweetBet decode(String tweet) {

		String tw=tweet.replace(",", ".").replaceAll(" +", " ").toUpperCase();
		
		Preconditions.checkArgument(tw.matches(tweetPattern), "TWEET INVALIDO");

		String str = tw.split(" ([NP]{1,2}|T)")[0];
		String[] data = str.split(" ");
		String[] lottery = tw.substring(str.length()+1).split(" ");

		int number = Integer.parseInt(data[0]);
		int position = data.length > 2 ? Integer.parseInt(data[1]) : 1;
		float amount = data.length > 2 ? Float.parseFloat(data[2]) : Float.parseFloat(data[1]);

		Collection<String> lotteries = select(lotteryNames, lottery[0]);
		Collection<String> variants = lottery.length > 1 ? select(variantNames, lottery[1]) : null;

		TweetBet bet = new TweetBet(number, position, amount, lotteries, variants);

		return bet;
	}

	@SuppressWarnings("unchecked")
	private Collection<String> select(final Collection<String> collection, final String string) {
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
