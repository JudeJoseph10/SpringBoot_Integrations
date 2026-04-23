package com.abhishekvermaa10.service.impl;

import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.abhishekvermaa10.dto.AttachmentDTO;
import com.abhishekvermaa10.dto.MailDTO;
import com.abhishekvermaa10.service.AsyncMailService;

import freemarker.template.Configuration;
import jakarta.activation.DataSource;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

/**
 * @author abhishekvermaa10
 */
@Service
public class AsyncMailServiceImpl implements AsyncMailService {
	
	private final JavaMailSender mailSender;
	private final Configuration templateConfig;
	@Value("${spring.mail.username}")
	private String senderEmail;
	@Value("${success.message}")
	private String successMessage;

	public AsyncMailServiceImpl(JavaMailSender mailSender, Configuration templateConfig) {
		this.mailSender = mailSender;
		this.templateConfig = templateConfig;
	}

	@Async
	@Override
	public CompletableFuture<String> sendEmail(MailDTO mailDTO) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(senderEmail);
			message.setTo(mailDTO.to());
			message.setSubject(mailDTO.subject());
			message.setText(mailDTO.body());
			mailSender.send(message);
			return CompletableFuture.completedFuture(String.format(successMessage, LocalDateTime.now()));
		} catch (Exception e) {
			e.printStackTrace();
			return CompletableFuture.failedFuture(e);
		}
	}

	@Async
	@Override
	public CompletableFuture<String> sendEmailWithAttachment(MailDTO mailDTO, Resource attachment) {
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
			return CompletableFuture.completedFuture(String.format(successMessage, LocalDateTime.now()));
		} catch (Exception e) {
			return CompletableFuture.failedFuture(e);
		}
	}

	@Async
	@Override
	public CompletableFuture<String> sendEmailWithAttachment(MailDTO mailDTO, List<AttachmentDTO> attachments) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
			mimeMessageHelper.setFrom(senderEmail);
			mimeMessageHelper.setTo(mailDTO.to());
			mimeMessageHelper.setSubject(mailDTO.subject());
			mimeMessageHelper.setText(buildMailBodyWithTemplate(mailDTO.body()), true);
			if (Objects.nonNull(attachments) && !attachments.isEmpty()) {
				for (AttachmentDTO attachment : attachments) {
					if (Objects.nonNull(attachment)) {
						DataSource dataSource = new ByteArrayDataSource(attachment.data(), attachment.contentType());
						mimeMessageHelper.addAttachment(attachment.fileName(), dataSource);
					}
				}
			}
			mailSender.send(message);
			return CompletableFuture.completedFuture(String.format(successMessage, LocalDateTime.now()));
		} catch (Exception e) {
			return CompletableFuture.failedFuture(e);
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
