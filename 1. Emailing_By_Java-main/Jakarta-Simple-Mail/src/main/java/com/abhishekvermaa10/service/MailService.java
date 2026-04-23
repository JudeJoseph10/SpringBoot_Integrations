package com.abhishekvermaa10.service;

/**
 * @author abhishekvermaa10
 */
public interface MailService {
	
	String sendBasicMail(String recipientMail, String subject, String body);

}
