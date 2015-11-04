/*
 Copyright 2014 CertiVox UK Ltd, All Rights Reserved.
 
 The CertiVox M-Pin Client and Server Libraries are free software: you can
 redistribute it and/or modify it under the terms of the BSD 3-Clause
 License - http://opensource.org/licenses/BSD-3-Clause
 
 For full details regarding our CertiVox terms of service please refer to
 the following links:
 
 * Our Terms and Conditions -
 http://www.certivox.com/about-certivox/terms-and-conditions/
 
 * Our Security and Privacy -
 http://www.certivox.com/about-certivox/security-privacy/
 
 * Our Statement of Position and Our Promise on Software Patents -
 http://www.certivox.com/about-certivox/patents/
*/


package com.certivox.utils;


public class Constants {

	/*
	 * Configuration
	 */
	public static final String TOMCAT_BASE_DIR = System.getProperty("catalina.base");
	public static final String CONFIG_PROPERTIES = "WEB-INF/config.properties";
	
	public static final String RPS_SERVER = "RPS_SERVER";
	public static final String VERIFY_LINK_BASE_URL = "VERIFY_LINK_BASE_URL";
	public static final String FORCE_ACTIVATE = "FORCE_ACTIVATE";
	public static final String SMTP_HOST = "SMTP_HOST";
	public static final String SMTP_PORT = "SMTP_PORT";
	public static final String SMTP_USERNAME = "SMTP_USERNAME";
	public static final String SMTP_PASSWORD = "SMTP_PASSWORD";
	
	
	public final static String MPIN_COOKIE = "mpincookie";
	/// DATE TIME SETTINGS
	public final static String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public final static String TIME_ZONE  = "GMT";
	
	/*
	 * MPIN variables
	 */
	public static final String MPINID = "mpinId";
	public static final String ACTIVATION_KEY = "activateKey";
	public static final String USERID = "userId";
	public static final String MOBILE  = "mobile";
	public static final String EXPIRE_TIME	= "expireTime";
	public static final String RESEND = "resend";
	public static final String DEVICE_NAME = "deviceName";
	public static final String USER_DATA  = "userData";
	
	public static final String AUTH_OTT = "authOTT";
	public static final String MPIN_RESPONSE = "mpinResponse";
	public static final String LOGOUT_DATA = "logoutData";
	public static final String SESSION_TOKEN = "sessionToken";
	public static final String STATUS = "status";
	public static final String MESSAGE = "message";
	
}
