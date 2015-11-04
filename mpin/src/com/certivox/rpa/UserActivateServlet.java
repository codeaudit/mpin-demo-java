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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;

import com.certivox.net.HTTPConnector;
import com.certivox.net.HTTPErrorException;
import com.certivox.utils.Config;
import com.certivox.utils.Constants;
import com.certivox.utils.StringUtils;

/**
 * UserActivateServlet class
 * @author Georgi Georgiev
 *
 */

@WebServlet(name = "UserActivateServlet", urlPatterns = {"/useractivate"})
public class UserActivateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String responseSuccess;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserActivateServlet() {
		super();
		responseSuccess = " <HTML>" +
				"<HEAD>" +
					"<TITLE> User activation request </TITLE> " +
				 "</HEAD>" + 
				 "<BODY>" +
				 "<H1>User activation is successful!</H1>" + 
				 "</BODY>" +
				 "</HTML>";
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			String hexMpinId = request.getParameter(Constants.MPINID);
			String activateKey = request.getParameter(Constants.ACTIVATION_KEY);
			String expireTime =  request.getParameter(Constants.EXPIRE_TIME);
			
			if(StringUtils.isBlank(hexMpinId) || StringUtils.isBlank(activateKey) || StringUtils.isBlank(expireTime))
				throw new IllegalArgumentException("Missing parameters from URL request :: mpinId or activate key or expire time");
		
			SimpleDateFormat dateTimeFormater = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
			dateTimeFormater.setTimeZone(TimeZone.getTimeZone(Constants.TIME_ZONE));
			Date expiresDate = dateTimeFormater.parse(expireTime);
			if ( expiresDate.before(new Date())) 
				throw new RequestExpiredException("Activation link for user with MPIN ID : " + hexMpinId + " has been expired!"); 
		
			Config config = Config.getInstance();
			String rpsEndPoint = config.get(Constants.RPS_SERVER);
			HTTPConnector connector = new HTTPConnector(rpsEndPoint);
			String userActivateURLMethod = rpsEndPoint + "/user/" + hexMpinId;
			
			JSONObject jobj = new JSONObject();
			jobj.put(Constants.ACTIVATION_KEY, activateKey);
			// TODO :: create method to return the HTTP RESPONDE 
			connector.sendRequest(userActivateURLMethod, "POST", jobj.toJSONString() );
			out.println(responseSuccess);
		} catch (IllegalArgumentException e) {
			response.setStatus(400);
			out.println(e.getLocalizedMessage());
		} catch (HTTPErrorException e) {
			((HttpServletResponse) response).setStatus(e.getStatusCode());
			out.println(e.getMessage());
		} catch (ParseException e) {
			((HttpServletResponse) response).setStatus(400);
			out.println(e.getLocalizedMessage());
		} catch (RequestExpiredException e) {
			((HttpServletResponse) response).setStatus(401);
			out.println(e.getLocalizedMessage());
		} catch (IOException e) {
			((HttpServletResponse) response).setStatus(500);
			out.println(e.getLocalizedMessage());
		} catch (Exception e) {
			((HttpServletResponse) response).setStatus(500);
			out.println(e.getLocalizedMessage());
		} finally {
			out.close();
		}
	}
	
	
	@SuppressWarnings("serial")
	public static class RequestExpiredException extends Exception {
		public RequestExpiredException() {
			super("Request Expired !");
		}
		public RequestExpiredException(String message) {
			super("Request Expired : " +  message);
		}
	}
}