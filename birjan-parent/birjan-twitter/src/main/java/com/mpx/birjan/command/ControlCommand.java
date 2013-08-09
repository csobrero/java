package com.mpx.birjan.command;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.util.InMemoryResource;
import org.springframework.stereotype.Repository;

import twitter4j.DirectMessage;

import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.TwitterBet;
import com.mpx.birjan.common.Lottery;
import com.mpx.birjan.common.Status;
import com.mpx.birjan.common.Wrapper;
import com.mpx.birjan.core.BirjanManager;
import com.mpx.birjan.core.TransactionalManager;
import com.mpx.birjan.tweeter.TwitterParser;
import com.mpx.birjan.util.WorkbookHandler;

@Repository
public class ControlCommand implements Command<String> {

	final Logger logger = LoggerFactory.getLogger(ControlCommand.class);
	
	private final static String senderName = "QuiniTwitter";

	@Autowired
	private BirjanManager birjanManager;
	
	@Autowired
	private TransactionalManager txManager;
	
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public String execute(DirectMessage directMessage) {

		TwitterBet twitterBet = validate(directMessage);
		String email = txManager.identifyMe().getAgency().getEmail();
		
		for (Lottery lottery : twitterBet.getLotteries()) {
			
			List<Game> games = txManager.retriveGames(null, lottery, twitterBet.getDate(), null, null);
			
			Wrapper[] values = null;
			if (games != null && !games.isEmpty()) {
				values = new Wrapper[games.size()];
				for (int i = 0; i < values.length; i++) {
					Game game = games.get(i);
					values[i] = new Wrapper(game.getWager().getHash(),
							game.getData(), game.getStatus(), game.getPrize(), game.getWager()
									.getUser().getUsername(), game
									.getCreated());
				}
			}
			
			String subject = "Control: " + lottery.getLotteryName() + " " + lottery.getVariantName() + " : " +
					twitterBet.getDate().getDayOfMonth()+"/"+twitterBet.getDate().getMonthOfYear();

			Workbook wb = WorkbookHandler.build(values);

			try {
				//OutputStream outputStream = new FileOutputStream(new File(lottery.name()+".xls"));
				OutputStream outputStream = new ByteArrayOutputStream();
				wb.write(outputStream);
				outputStream.flush();
				outputStream.close();	
				InMemoryResource resource = new InMemoryResource(((ByteArrayOutputStream) outputStream).toByteArray());
				
				MimeMessage message = mailSender.createMimeMessage();
				
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setFrom(senderName);
				helper.setTo(email);
				helper.setSubject(subject);
				//helper.setText("");
				helper.addAttachment(lottery.name()+".xls", resource);

				mailSender.send(message);
				
			} catch (Exception e) {
				logger.error("Exception email sending: " + e.getMessage());
			}
		}
		
		return "Control enviado al manager.";
	}
	
	private TwitterBet validate(DirectMessage directMessage) {
		TwitterBet bet = TwitterParser.unmarshalControl(directMessage.getText());

		for (String lotteryName : bet.getLotteryNames()) {
			for (String variantName : bet.getVariantNames()) {
				Lottery lottery = Lottery.valueOf(lotteryName + "_" + variantName);
				bet.add(lottery);
			}
		}
		logger.debug(bet.toString());

		return bet;
	}

}
