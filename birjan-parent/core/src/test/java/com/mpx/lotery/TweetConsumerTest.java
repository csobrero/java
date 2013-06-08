package com.mpx.lotery;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.BeforeClass;
import org.junit.Test;

import twitter4j.DirectMessage;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.UserStreamAdapter;
import twitter4j.conf.ConfigurationBuilder;

public class TweetConsumerTest {

	private static TwitterFactory tf;
	private static TwitterStreamFactory tsf;

	@BeforeClass
	public static void init() {
		tf = new TwitterFactory(configuration().build());
		tsf = new TwitterStreamFactory(configuration().build());
	}

	private static ConfigurationBuilder configuration() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("piozU7GCQ2sSgBkvyNs6pw")
				.setOAuthConsumerSecret("iOqu91UxDEvQwC2fWSLeJjCJUynYKYx0MAO9uQNqQ")
				.setOAuthAccessToken("1491434672-NOJCS5heHjAX563VhMZy3ENA7AKvpzGOZmtbnea")
				.setOAuthAccessTokenSecret("3LVYeMxSTrUwp35TgySczMFenXmJk5BoZl3lsH34JPU");
		return cb;
	}

	@Test
	public void test1() throws IllegalStateException, TwitterException {
		Twitter twitter = tf.getInstance();
		String screenName = twitter.getScreenName();
		long id = twitter.getId();
		System.out.println("Successfully updated the status to [" + screenName + " with id: " + id + "].");
		assertTrue(twitter != null);
	}

	@Test
	public void test2() throws TwitterException {
		Twitter twitter = tf.getInstance();
		ResponseList<DirectMessage> directMessages = twitter.getDirectMessages();
		for (DirectMessage directMessage : directMessages) {
			long senderId = directMessage.getSenderId();
			String senderScreenName = directMessage.getSenderScreenName();
			String text = directMessage.getText();
			System.out.println(senderId + " : " + senderScreenName + " : " + text);
			assertTrue(twitter != null);
		}
	}

	@Test
	public void test3() throws TwitterException, InterruptedException {

		final BlockingQueue<DirectMessage> bq = new ArrayBlockingQueue<DirectMessage>(100);

		TwitterStream twiter = tsf.getInstance();
		twiter.addListener(new UserStreamAdapter() {
			@Override
			public void onDirectMessage(DirectMessage directMessage) {
				bq.add(directMessage);
			}
		});
		twiter.user();

		DirectMessage dmRq = bq.take();
		long senderId = dmRq.getSenderId();
		String senderScreenName = dmRq.getSenderScreenName();
		String text = dmRq.getText();
		System.out.println(senderId + " : " + senderScreenName + " : " + text);
		assertTrue(dmRq != null);

		Twitter twitterSender = tf.getInstance();
		DirectMessage dmRs = twitterSender.sendDirectMessage(dmRq.getSenderId(), dmRq.getText() + "_response");

		assertTrue(dmRs != null);

	}

}
