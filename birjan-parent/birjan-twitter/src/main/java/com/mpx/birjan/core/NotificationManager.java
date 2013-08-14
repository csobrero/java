package com.mpx.birjan.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
import org.springframework.security.util.InMemoryResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.mpx.birjan.bean.Agency;
import com.mpx.birjan.common.Lottery;
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
			
			Collection<Resource> resources = Collections2.transform(workbooks, new Function<WorkbookHolder, Resource>() {
				@Override
				public Resource apply(@Nullable WorkbookHolder workbookHolder) {
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					try {
						workbookHolder.getWorkbook().write(outputStream);
						outputStream.flush();
					} catch (IOException e) {
						e.printStackTrace();
						logger.error(e.getMessage());
					}
					Resource resource = new InMemoryResource(outputStream.toByteArray(), workbookHolder.getFileName());			
					return resource;
				}
			});
			
			send(agency.getEmail(), "Control de Jugadas", "Ver planillas de control adjuntas.", resources);
		}
	}
	
	@Async
	public void send(String email, String subject, String text, Collection<Resource> attaches) {
		
		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);
			helper.setFrom(senderName);
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText(text);
			for (Resource resource : attaches) {
				helper.addAttachment(resource.getDescription(), resource);
			}
		} catch (MessagingException e) {
			e.printStackTrace();
			logger.error("Exception email sending: " + e.getMessage());
		}

		mailSender.send(message);
	}
	
}
