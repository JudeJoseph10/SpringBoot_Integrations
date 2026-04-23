package com.abhishekvermaa10.config;

import java.util.TimeZone;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

/**
 * @author abhishekvermaa10
 */
public class TemplateConfig {
	
	private static final Configuration CONFIGUARTION;
	
	private TemplateConfig() {
		
	}
	
	static {
		CONFIGUARTION = new Configuration(Configuration.VERSION_2_3_34);
		CONFIGUARTION.setClassLoaderForTemplateLoading(TemplateConfig.class.getClassLoader(), "/templates");
		CONFIGUARTION.setDefaultEncoding("UTF-8");
		CONFIGUARTION.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		CONFIGUARTION.setLogTemplateExceptions(false);
		CONFIGUARTION.setWrapUncheckedExceptions(true);
		CONFIGUARTION.setFallbackOnNullLoopVariable(false);
	    CONFIGUARTION.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
	}
	
	public static Configuration getConfiguration() {
		return CONFIGUARTION;
	}
	
}
