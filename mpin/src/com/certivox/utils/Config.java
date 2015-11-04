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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Config {
	
	private static Config singleton = null;
	
	public static Config getInstance(String configFilePath) throws IllegalArgumentException, IOException {
		if (singleton == null) singleton = new Config (configFilePath);
		return singleton;
	}
	
	public static Config getInstance () throws IllegalArgumentException {
		if(singleton == null) throw new IllegalArgumentException("Configuration is not initialized!");
		return singleton;
	}
	
	private Properties properties = new Properties();
	
	private Config(String filePath) throws IllegalArgumentException ,IOException {
		if (StringUtils.isBlank(filePath)) throw new IllegalArgumentException("Invlalid Argument Exception! :: ConfigManifest at init Method");
		
		FileInputStream fiStream = null;
		try {
			fiStream = new FileInputStream(filePath);
			properties.load(fiStream);
			fiStream.close();	
		} catch (IOException e) {
			System.err.println("Error loading config: " + e.toString());
			throw e;
		} finally {
			if(fiStream != null)	fiStream.close();	
		}
		
	}
	
	public String get (String key) {
		return properties.getProperty(key);
	}

}
