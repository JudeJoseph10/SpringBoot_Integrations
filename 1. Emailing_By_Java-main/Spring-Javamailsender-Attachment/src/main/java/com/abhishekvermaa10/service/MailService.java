package com.abhishekvermaa10.service;

import org.springframework.core.io.Resource;

import com.abhishekvermaa10.dto.MailDTO;

/**
 * @author abhishekvermaa10
 */
public interface MailService {
	
	String sendBasicMail(MailDTO mailDTO);

	String sendAdvancedMail(MailDTO mailDTO, Resource attachment);

}
