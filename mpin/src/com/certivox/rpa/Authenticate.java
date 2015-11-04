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

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.ParseException;

import com.certivox.managers.CookieManager;
import com.certivox.managers.SessionManager;
import com.certivox.net.HTTPConnector;
import com.certivox.net.HTTPResponse;
import com.certivox.utils.Config;
import com.certivox.utils.Constants;
import com.certivox.utils.StringUtils;

/**
 * Servlet implementation class Authenticate
 */
@WebServlet("/mpinAuthenticate")
public class Authenticate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public Authenticate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			JSONObject jsonRequest = (JSONObject)JSONValue.parseWithException(request.getReader());
			JSONObject mpinResponse = (JSONObject) jsonRequest.get(Constants.MPIN_RESPONSE);
			if(mpinResponse == null) throw new IllegalArgumentException("Missung mpinResponse parameter from request body");
			String authOTT = (String) mpinResponse.get(Constants.AUTH_OTT);
			if(StringUtils.isBlank(authOTT)) throw new IllegalArgumentException("Missing authOTT parameter from request body");
			
			CookieManager cm  = AppServletContextListener.defaultCookieManager();
			Cookie cookie = cm.getCookie(request.getCookies());
			if(cookie == null) cookie = cm.createCookie(request.getSession().getId());
			
			String sessionId = cookie.getValue();
			
			Config config = Config.getInstance();
			String rpsEndPoint = config.get(Constants.RPS_SERVER);
			HTTPConnector connector = new HTTPConnector(rpsEndPoint);
			String authenticateURL = rpsEndPoint + "/authenticate";
			
			JSONObject jreqObj = new JSONObject();
			jreqObj.put(Constants.AUTH_OTT, authOTT);
			JSONObject jlogoutData = new JSONObject();
			jlogoutData.put(Constants.SESSION_TOKEN, sessionId);
			jreqObj.put(Constants.LOGOUT_DATA, jlogoutData);
			
			HTTPResponse rpsResponse =  connector.sendRequest(authenticateURL, "POST", jreqObj.toJSONString() );
			
			JSONObject jsonResponse = (JSONObject)JSONValue.parseWithException(rpsResponse.getBody());
			Integer status = (Integer) jsonResponse.get(Constants.STATUS);
			String userId =(String) jsonResponse.get(Constants.USERID);
			String message = (String) jsonResponse.get(Constants.MESSAGE);
			
			if(status != 200) {
				response.setStatus(status);
				return;
			}
			
			/* TODO Enable this call later depending on config on RPS
			
			String loginResultURL  = rpsEndPoint + "/loginResult";
			
			jreqObj = new JSONObject();
			jreqObj.put(Constants.AUTH_OTT, authOTT);
			jreqObj.put(Constants.STATUS, status);
			jreqObj.put(Constants.MESSAGE, message);
			jlogoutData = new JSONObject();
			jlogoutData.put(Constants.SESSION_TOKEN, sessionId);
			jlogoutData.put(Constants.USERID, userId);
			jreqObj.put(Constants.LOGOUT_DATA, jlogoutData);
			
			
			
			rpsResponse =  connector.sendRequest(loginResultURL, "POST", jreqObj.toJSONString() );
			
			if(rpsResponse.getStatusCode() != 200) {
				response.setStatus(rpsResponse.getStatusCode());
				return;
			}
			*/
			
			SessionManager<String> sm = AppServletContextListener.defaultSessionManager();
			sm.put(sessionId, userId);
			/// System.out.println("Session Id = " + sessionId +  " userid= " + userId);
			response.addCookie(cookie);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			response.setStatus(400);
			out.println(e.getLocalizedMessage());
		} catch (ParseException e) { 
			response.setStatus(400);
			e.printStackTrace();
			out.println(e.getLocalizedMessage()); 
		 } catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500);
			out.println(e.getLocalizedMessage());
		} finally {
			out.close();
		}
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
