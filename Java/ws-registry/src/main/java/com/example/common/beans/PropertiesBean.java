package com.example.common.beans;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Bean responsible for Spring's dependency injection to use system messages.
 */
@Component
public class PropertiesBean {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private Environment environment;

	/**
	 * Get a message in file with PT-BR language.
	 * 
	 * @param key Message key in file.
	 * @param params Message optional parameters.
	 * @return Formatted message according the parameters.
	 */
	public String getMessage(String key, Object... params) {
		return messageSource.getMessage(key, params, new Locale("pt", "BR"));
	}

	/**
	 * Get a message in file with PT-BR language.
	 * 
	 * @param key Message key in file.
	 * 
	 * @return Message obtained by the key or null if doesn't exists.
	 */
	public String getMessage(String key) {
		return this.getMessage(key, new Object[] {});
	}

	/**
	 * Get a property in Spring's application.properties file.
	 * 
	 * @param key Property key in file
	 * 
	 * @return Property obtained by the key or null if doesn't exists.
	 */
	public String getApplicationProperty(String key) {
		return environment.getProperty(key);
	}
}
