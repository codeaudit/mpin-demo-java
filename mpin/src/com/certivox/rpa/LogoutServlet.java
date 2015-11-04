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
 * Servlet implementation class LogoutServlet
 */

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		try {
			CookieManager cm  = AppServletContextListener.defaultCookieManager();
	    	Cookie cookie = cm.getCookie(request.getCookies());
	    	if(cookie != null) {
	    		cookie.setMaxAge(0);
	    		SessionManager<String> sm = AppServletContextListener.defaultSessionManager();
	    		sm.removeSession(cookie.getValue());
	    	} 
	    	 response.sendRedirect("/mpin/index.html");	 
		} catch (Exception e) {
			response.setStatus(500);
			out.println("FAILED");
		} finally {
			out.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			JSONObject jsonRequest = (JSONObject)JSONValue.parseWithException(request.getReader());
			String sessionToken = (String) jsonRequest.get(Constants.SESSION_TOKEN);
			if(StringUtils.isBlank(sessionToken)) throw new IllegalArgumentException("Missung mpinResponse parameter from request body");
		
			System.out.println("Session token " + sessionToken);
			
			SessionManager<String> sm = AppServletContextListener.defaultSessionManager();
			String sessionValue = sm.getSession(sessionToken);
			if(sessionValue == null) {
				response.setStatus(400);
				return;
			}
			
			System.out.println("Session id = " + sessionValue);
			
			sm.removeSession(sessionToken);
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
	 * @see HttpServlet#doOptions(HttpServletRequest, HttpServletResponse)
	 */
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(200);
	}

}
