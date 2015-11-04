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

import java.io.IOException;

public class HTTPErrorException extends IOException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3998816167241774286L;
	private int statusCode;

	public HTTPErrorException() {
		// TODO Auto-generated constructor stub
	}

	public HTTPErrorException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	public HTTPErrorException(String message, int statusCode) {
		super(message);
		setStatusCode(statusCode);
	}
	
	public HTTPErrorException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public HTTPErrorException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
