package com.mpx.birjan.core.manager;

import static com.mpx.birjan.common.Status.WINNER;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.mpx.birjan.bean.Agency;
import com.mpx.birjan.bean.Game;
import com.mpx.birjan.common.Lottery;
import com.mpx.birjan.util.Utils;
import com.mpx.birjan.util.WorkbookHandler.WorkbookHolder;

@Component
public class NotificationManager {
	
	final Logger logger = LoggerFactory.getLogger(NotificationManager.class);
	
	private final static String senderName = "QuiniTwitter";

	@Autowired
	private BirjanManager birjanManager;
	
	@Autowired
	private TransactionalManager txManager;
	
	@Autowired
	private TwitterFactory twitterFactory;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Transactional
	public void sendControlSheetToManagers() {
		
		Collection<Lottery> lotteries = Collections2.filter(Arrays.asList(Lottery.values()), new Predicate<Lottery>() {
			public boolean apply(@Nonnull Lottery lottery) {
				return lottery.getRule().getTo().minusMinutes(2).isBeforeNow() 
						&& lottery.getRule().getTo().isAfterNow();
			}
		});
		
		for (Agency agency : txManager.getAllAgencies()) {
			List<WorkbookHolder> workbooks = birjanManager.getWorkbooks(lotteries, new DateTime(), agency);		
			Collection<Resource> resources = Utils.transform(workbooks);
			
			send(agency.getEmail(), "Control de Jugadas", "Ver planillas de control adjuntas.", resources);
		}
	}

	@Transactional(readOnly = true)
	public void notifyWinners(Lottery lottery, DateTime date) {
		for (Agency agency : txManager.getAllAgencies()) {
			List<Game> winners = txManager.retriveGames(WINNER, lottery, date, null, null, agency);
			
			send(Long.parseLong(agency.getPrincipal().getUsername()), "Finalizado sorteo: " + 
				lottery.getLotteryName() + " " + lottery.getVariantName() + " : " + date.getDayOfMonth() + "/" + 
					date.getMonthOfYear() + (winners.size()==0?": sin ganadores." : 
					(winners.size()==1?": (1) ganador." : ": (" + winners.size() + ") ganadores.")));
		}
		
	}
	
	@Transactional(readOnly = true)
	public void notifyFirstPrize(String message) {
		for (Agency agency : txManager.getAllAgencies()) {
			send(Long.parseLong(agency.getPrincipal().getUsername()), message);
		}
		
	}

	@Async
	public void send(long senderId, String message) {
		send(null, senderId, message);
	}
	
	private void send(Twitter twitterSender, long senderId, String message) {
		if(twitterSender==null)
			twitterSender = twitterFactory.getInstance();
		try {
			twitterSender.sendDirectMessage(senderId, message);
		} catch (TwitterException e) {
			if (e.getErrorCode() == 151)
				send(twitterSender, senderId, message + ".");
			else
				logger.error("Exception sending: " + e.getMessage());
		}
		
	}
	
	@Async
	public void send(String email, String subject, String message, Collection<Resource> attaches) {
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(senderName);
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText(message);
			for (Resource resource : attaches) {
				helper.addAttachment(resource.getDescription(), resource);
			}
		} catch (MessagingException e) {
			e.printStackTrace();
			logger.error("Exception email sending: " + e.getMessage());
		}

		mailSender.send(mimeMessage);
	}
	
}
