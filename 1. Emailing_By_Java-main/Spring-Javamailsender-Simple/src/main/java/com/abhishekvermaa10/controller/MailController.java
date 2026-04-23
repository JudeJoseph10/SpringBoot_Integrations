package com.abhishekvermaa10.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
