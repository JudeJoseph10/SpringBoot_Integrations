package com.abhishekvermaa10.service;

import java.io.File;

/**
 * @author abhishekvermaa10
 */
public interface MailService {
	
	String sendBasicMail(String recipientMail, String subject, String body);
	
	String sendAdvancedMail(String recipientMail, String subject, String body, File attachment);

}
