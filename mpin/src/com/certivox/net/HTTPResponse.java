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

package com.certivox.net;

import java.util.Hashtable;

public class HTTPResponse {
	
	private int statusCode;
	
	private  Hashtable<String, String> headers;
	private String body;
	
	HTTPResponse (int statusCode, Hashtable<String, String> headers, String body) {
		this.statusCode = statusCode;
		this.headers = headers;
		this.body = body;
	}
	
	HTTPResponse (int statusCode, String body) {
		this(statusCode, null, body);
	}

	public int getStatusCode() {
		return statusCode;
	}
	
	public Hashtable<String, String> getHeaders() {
		return headers;
	}

	public String getBody() {
		return body;
	}
}
