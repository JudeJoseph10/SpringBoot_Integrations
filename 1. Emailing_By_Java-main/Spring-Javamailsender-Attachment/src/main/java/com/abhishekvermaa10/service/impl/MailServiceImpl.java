package com.abhishekvermaa10.service.impl;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.abhishekvermaa10.dto.MailDTO;
import com.abhishekvermaa10.service.MailService;

import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;

/**
 * @author abhishekvermaa10
 */
@Service
public class MailServiceImpl implements MailService {

	private final JavaMailSender mailSender;
	private final Configuration templateConfig;
	@Value("${spring.mail.username}")
	private String senderEmail;
	@Value("${success.message}")
	private String successMessage;

	public MailServiceImpl(JavaMailSender mailSender, Configuration templateConfig) {
		this.mailSender = mailSender;
		this.templateConfig = templateConfig;
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

	@Override
	public String sendAdvancedMail(MailDTO mailDTO, Resource attachment) {
		return sendEmailWithAttachment(mailDTO, attachment);
	}
	
	private String sendEmailWithAttachment(MailDTO mailDTO, Resource attachment) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
			mimeMessageHelper.setFrom(senderEmail);
			mimeMessageHelper.setTo(mailDTO.to());
			mimeMessageHelper.setSubject(mailDTO.subject());
			mimeMessageHelper.setText(buildMailBodyWithTemplate(mailDTO.body()), true);
			if (Objects.nonNull(attachment)) {
				mimeMessageHelper.addAttachment(attachment.getFilename(), attachment);
			}
			mailSender.send(message);
			return successMessage;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	private String buildMailBodyWithTemplate(String body) {
		Map<String, String> dataModel = new HashMap<>();
		dataModel.put("subscriberName", body);
		Writer writer = new StringWriter();
		try {
			templateConfig.getTemplate("welcome.ftlh")
			.process(dataModel, writer);
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return body;
		}
	}

}
