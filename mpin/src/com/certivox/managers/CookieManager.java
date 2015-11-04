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


package com.certivox.managers;

import javax.servlet.http.Cookie;

import com.certivox.utils.StringUtils;

public class CookieManager {
	private String cookieIdentifier;
	private String domain;
	private String path;
	private int maxAge;
	
	/// TODO static set default cookie policy , max age and etc... 
	
	public CookieManager(String cookieIdentifier) throws IllegalArgumentException {
		if(StringUtils.isBlank(cookieIdentifier)) throw new IllegalArgumentException("Ivalid cookieIdentifier");
		this.cookieIdentifier = cookieIdentifier;
	}
	
	public CookieManager(String cookieIdentifier, String domain, String path , int maxAge) throws IllegalArgumentException {
		this(cookieIdentifier);
		this.domain = domain;
		this.path = path;
		this.maxAge = maxAge;
	}


	public Cookie getCookie(Cookie [] cookies) throws IllegalArgumentException {
		if(cookies == null) return null;
		
		for(Cookie cookie:cookies)
			if(cookieIdentifier.equals(cookie.getName()))
				return cookie;
		
		return null;
	}
		
	public Cookie createCookie(String sessionId) throws IllegalArgumentException {
		if(StringUtils.isBlank(sessionId)) throw new IllegalArgumentException("sessionId is null");
		
		Cookie cookie = new Cookie(cookieIdentifier, sessionId);
		if(domain != null)	cookie.setDomain(domain);
		if(path != null)	cookie.setPath(path);
		if(maxAge > 0)		cookie.setMaxAge(maxAge);
		return cookie;
	}

}
