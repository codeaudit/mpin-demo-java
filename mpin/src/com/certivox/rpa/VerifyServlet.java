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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.ParseException;

import com.certivox.utils.Config;
import com.certivox.utils.Constants;
import com.certivox.utils.EmailUtils;
import com.certivox.utils.StringUtils;

/**
 * VerifyServlet class
 * @author Georgi Georgiev
 *
 */


@WebServlet(name = "VerifyServlet", urlPatterns = {"/mpinVerify"})
public class VerifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VerifyServlet() {
		super();
	}
	
	/**
	 * MPIN verify METHOD
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();
		try {
			Config config = Config.getInstance();
			boolean forceActivate = false;
			String configForceActivate =  config.get(Constants.FORCE_ACTIVATE);

			if( !StringUtils.isBlank(configForceActivate) )
				forceActivate = configForceActivate.toLowerCase().equals("true");
			
			if ( forceActivate ) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("forceActivate", true);
				out.print(jsonObj.toJSONString());
				return;
			} 
				
			JSONObject jsonVerifyRequest = (JSONObject)JSONValue.parseWithException(request.getReader());
			String hexMpinId = (String) jsonVerifyRequest.get(Constants.MPINID);
			String userId =(String) jsonVerifyRequest.get(Constants.USERID);
			String activateKey = (String) jsonVerifyRequest.get(Constants.ACTIVATION_KEY);
			String expireTime = (String) jsonVerifyRequest.get(Constants.EXPIRE_TIME);
			
			if( StringUtils.isBlank(hexMpinId) || 
				StringUtils.isBlank(userId) || 
				StringUtils.isBlank(activateKey) || 
				StringUtils.isBlank(expireTime) ) 
					throw new IllegalArgumentException("POST data Validation Failed! Missing or empty parameter(s) :: mpinId, userId or activateKey");
			
			
			String activationUrl = config.get(Constants.VERIFY_LINK_BASE_URL) + request.getRequestURI().replace(request.getServletPath(), "/useractivate?") + 
		    					   Constants.MPINID + "=" + hexMpinId + "&" +
		    					   Constants.ACTIVATION_KEY + "=" + activateKey + "&" + 
		    					   Constants.EXPIRE_TIME + "=" + expireTime;
		    
		    EmailUtils.sendConfirmationEmail(userId, activationUrl);
		} catch (IllegalArgumentException e) {
				e.printStackTrace();
				response.setStatus(401);
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
}