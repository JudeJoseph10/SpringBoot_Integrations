package com.abhishekvermaa10.controller;

import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.abhishekvermaa10.dto.MailDTO;
import com.abhishekvermaa10.service.MailService;

/**
 * @author abhishekvermaa10
 */
@RequestMapping("/mail")
@RestController
public class MailController {
	
	private final MailService mailService;
	
	public MailController(MailService mailService) {
		this.mailService = mailService;
	}

	@PostMapping("/sendBasic")
	public ResponseEntity<String> sendSimpleMail(@RequestBody MailDTO mailDTO) {
		String response = mailService.sendBasicMail(mailDTO);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/sendAdvanced")
	public ResponseEntity<String> sendAdvancedMail(@RequestBody MailDTO mailDTO) {
		Resource attachment = new ClassPathResource("images/sample.jpg");
		String response = mailService.sendAdvancedMail(mailDTO, attachment);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/sendWithAttachment")
	public ResponseEntity<String> sendAdvancedMail(@RequestPart MailDTO mailDTO,
			@RequestPart(required = false) List<MultipartFile> attachments) {
		String response = mailService.sendAdvancedMail(mailDTO, attachments);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
