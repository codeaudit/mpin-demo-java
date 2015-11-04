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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
	
	public static boolean isBlank(String str) {
		return str == null || str.length() == 0;
	}
	
	public static boolean isNotBlank(String str) {
		return str != null && str.length() > 0;
	}
	
	
	public static Date stringToDate(String strDate) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		 try {
			return (Date)formatter.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static String convertStreamToString(InputStream is) {
		if( is == null ) return "";

		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader (new InputStreamReader((is)));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}	
			return sb.toString();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch(Exception e) { 
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String convertStreamToString(BufferedReader br) {
		if(br == null ) return "";
		StringBuilder sb = new StringBuilder();
		try {
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}	
			return sb.toString();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}

