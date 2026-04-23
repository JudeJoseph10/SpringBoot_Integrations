package com.abhishekvermaa10.service.impl;

import java.io.File;

import com.abhishekvermaa10.config.MailConfig;
import com.abhishekvermaa10.service.MailService;

import jakarta.mail.Message;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

/**
 * @author abhishekvermaa10
 */
public class MailServiceImpl implements MailService {
	
	private static final Session SESSION = MailConfig.getSession();
	private static final String SENDER_EMAIL = MailConfig.get("mail.username");
	private static final String SUCCESS_MESSAGE = "E-Mail sent successfully!";

	@Override
	public String sendBasicMail(String recipientMail, String subject, String body) {
        return sendEmail(recipientMail, subject, body);
	}
	
	@Override
	public String sendAdvancedMail(String recipientMail, String subject, String body, File attachment) {
		return sendEmailWithAttachment(recipientMail, subject, body, attachment);
	}

	private String sendEmail(String recipientMail, String subject, String body) {
		try {
        	Message message = new MimeMessage(SESSION);
        	message.setFrom(new InternetAddress(SENDER_EMAIL));
        	message.setRecipient(RecipientType.TO, new InternetAddress(recipientMail));
        	message.setSubject(subject);
        	message.setText(body);
			Transport.send(message);
			return SUCCESS_MESSAGE;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	private String sendEmailWithAttachment(String recipientMail, String subject, String body, File attachment) {
		try {
        	Message message = new MimeMessage(SESSION);
        	message.setFrom(new InternetAddress(SENDER_EMAIL));
        	message.setRecipient(RecipientType.TO, new InternetAddress(recipientMail));
        	message.setSubject(subject);
        	Multipart multipart = new MimeMultipart();
        	MimeBodyPart textPart = new MimeBodyPart();
        	textPart.setText(body);
        	multipart.addBodyPart(textPart);
        	MimeBodyPart attachmentPart = new MimeBodyPart();
        	attachmentPart.attachFile(attachment);
        	multipart.addBodyPart(attachmentPart);
        	message.setContent(multipart);
			Transport.send(message);
			return SUCCESS_MESSAGE;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

}
