package com.abhishekvermaa10.config;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

/**
 * @author abhishekvermaa10
 */
public class MailConfig {
	
	private static final Properties PROPERTIES = new Properties();
    private static final Session SESSION;

	private MailConfig() {
		//To avoid direct object creation
	}
	
	static {
        try (var input = MailConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                PROPERTIES.load(input);
                for (String key : PROPERTIES.stringPropertyNames()) {
                    String value = get(key);
                    if (value != null && value.startsWith("${") && value.endsWith("}")) {
                        String envVar = value.substring(2, value.length() - 1);
                        String envValue = System.getenv(envVar);
                        if (envValue != null) {
                            PROPERTIES.setProperty(key, envValue);
                        } else {
                            throw new RuntimeException("Environment variable '" + envVar + "' not set");
                        }
                    }
                }
            } else {
                throw new RuntimeException("application.properties not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load mail config", e);
        }

        Properties smtpProperties = getSmtpProperties();
        String username = get("mail.username");
        String password = get("mail.password");

        SESSION = Session.getInstance(smtpProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
	
	private static Properties getSmtpProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", get("mail.smtp.host"));
        properties.put("mail.smtp.port", get("mail.smtp.port"));
        properties.put("mail.smtp.auth", get("mail.smtp.auth"));
        properties.put("mail.smtp.starttls.enable", get("mail.smtp.starttls.enable"));
        return properties;
    }
	
	public static String get(String key) {
		return PROPERTIES.getProperty(key);
	}
	
	public static Session getSession() {
        return SESSION;
    }

}
