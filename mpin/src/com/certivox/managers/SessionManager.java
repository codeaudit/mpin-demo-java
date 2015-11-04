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

import java.util.Hashtable;

public class SessionManager <SessionObject> {
	// in memory Storage
	private Hashtable <String ,SessionObject > storage =  new Hashtable <String , SessionObject> ();
	
	/// TODO :: declare this as interface and remove dependances from the storage type
	public void removeSession(String sessionId) { storage.remove(sessionId); }
	public SessionObject getSession(String sessionId) { return storage.get(sessionId); }
	public void put(String key, SessionObject value) {	storage.put(key, value);	}
}
