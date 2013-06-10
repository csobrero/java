package com.mpx.birjan.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import twitter4j.DirectMessage;
import twitter4j.TwitterFactory;

import com.mpx.birjan.bean.Bet;
import com.mpx.birjan.service.impl.TwitterServiceEndpoint;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TwitterHandler implements Runnable{

	@Autowired
	private TwitterFactory twitterFactory;
	
	@Autowired
	private TwitterServiceEndpoint endpoint;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	private DirectMessage directMessage;
	
	@Override
	public void run() {
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				directMessage.getSenderId() + "", ""));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		Bet bet = endpoint.validate(directMessage);
		
//		endpoint.createGames(bet);
		
		System.out.println(bet);
		
//		long senderId = directMessage.getSenderId();
//		String senderScreenName = directMessage.getSenderScreenName();
//		String text = directMessage.getText();
//		System.out.println(senderId + " : " + senderScreenName + " : " + text);
//
//		Twitter twitterSender = twitterFactory.getInstance();
//		try {
//			DirectMessage dmRs = twitterSender.sendDirectMessage(directMessage.getSenderId(), directMessage.getText() + "_response");
//		} catch (TwitterException e) {
//			e.printStackTrace();
//		}
		
		
	}

	public DirectMessage getDirectMessage() {
		return directMessage;
	}

	public void setDirectMessage(DirectMessage directMessage) {
		this.directMessage = directMessage;
	}

}
