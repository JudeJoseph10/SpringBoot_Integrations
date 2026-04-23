package com.abhishekvermaa10.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.abhishekvermaa10.dto.MailDTO;
import com.abhishekvermaa10.service.MailService;

/**
 * @author abhishekvermaa10
 */
@Service
public class MailServiceImpl implements MailService {

	private final JavaMailSender mailSender;
	@Value("${spring.mail.username}")
	private String senderEmail;
	@Value("${success.message}")
	private String successMessage;

	public MailServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public String sendBasicMail(MailDTO mailDTO) {
		return sendEmail(mailDTO);
	}

	private String sendEmail(MailDTO mailDTO) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(senderEmail);
			message.setTo(mailDTO.to());
			message.setSubject(mailDTO.subject());
			message.setText(mailDTO.body());
			mailSender.send(message);
			return successMessage;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

}
