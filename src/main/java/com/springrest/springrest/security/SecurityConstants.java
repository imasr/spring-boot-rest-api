package com.springrest.springrest.security;

import com.springrest.springrest.SpringApplicationContext;

public class SecurityConstants {

	public static final String HEADER_STRING = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * 10;
	public static final String SIGN_UP_URL = "/users";
	public static final String SIGN_IN__URL = "/signin";

	public static String getTokenSecret() {
		AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
		return appProperties.getTokenSecret();
	}

}
