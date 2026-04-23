package com.abhishekvermaa10.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.core.io.Resource;

import com.abhishekvermaa10.dto.AttachmentDTO;
import com.abhishekvermaa10.dto.MailDTO;

/**
 * @author abhishekvermaa10
 */
public interface AsyncMailService {
	
	CompletableFuture<String> sendEmail(MailDTO mailDTO);

	CompletableFuture<String> sendEmailWithAttachment(MailDTO mailDTO, Resource attachment);

	CompletableFuture<String> sendEmailWithAttachment(MailDTO mailDTO, List<AttachmentDTO> attachments);

}
