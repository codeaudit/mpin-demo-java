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

package com.certivox.rpa;

import com.certivox.managers.CookieManager;
import com.certivox.managers.SessionManager;
import com.certivox.utils.Constants;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.certivox.utils.Config;

public class AppServletContextListener implements ServletContextListener {
	
	private static SessionManager<String> sm = null;
	private static CookieManager cm  =  null;
	
	public static SessionManager<String> defaultSessionManager() {
		if (sm == null) sm = new SessionManager<String>();
		return sm;
	}
	
	public static CookieManager defaultCookieManager () {
		if(cm == null) cm = new CookieManager(Constants.MPIN_COOKIE);
		return cm;
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvnt) {
		System.out.println("ServletContextListener started STARTED !!!!");
		ServletContext context = servletContextEvnt.getServletContext();	
		try {	
			Config.getInstance(context.getRealPath(Constants.CONFIG_PROPERTIES));
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		}	
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvnt) {
		System.out.println("Destroyed");
	}
}
