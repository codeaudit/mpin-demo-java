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
import java.net.MalformedURLException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.certivox.net.HTTPConnector;
import com.certivox.net.HTTPErrorException;
import com.certivox.net.HTTPResponse;
import com.certivox.utils.Config;
import com.certivox.utils.Constants;
import com.certivox.utils.StringUtils;


@WebServlet(name = "RPSProxyServlet", urlPatterns = {"/rps/*"})
public class RPSProxy extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RPSProxy() {
		super();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			Config  config = Config.getInstance();
			String rpsEndPoint = config.get(Constants.RPS_SERVER) + "/rps";
			String path = request.getPathInfo();
			path = (path == null) ? ("") : (path);
			String queryString = request.getQueryString();
			queryString = (queryString == null) ? "" : ("?" + queryString);
			HTTPConnector connector = new HTTPConnector(rpsEndPoint);
			HTTPResponse rpsResponse = connector.sendRequest(  URLDecoder.decode(rpsEndPoint + path + queryString, "UTF-8"),
					((HttpServletRequest) request).getMethod(), 
					StringUtils.convertStreamToString(request.getReader()));
			
			response.setStatus(rpsResponse.getStatusCode());
			if(StringUtils.isNotBlank(rpsResponse.getBody()))
				out.println(rpsResponse.getBody());
			
		} catch (MalformedURLException e) {
			((HttpServletResponse) response).setStatus(400);
			out.println(e.toString());
		} catch (HTTPErrorException e) {
			((HttpServletResponse) response).setStatus(e.getStatusCode());
			out.println(e.getMessage());
		} catch (IOException e) {
			((HttpServletResponse) response).setStatus(500);
			out.println(e.toString());
		} catch (Exception e) {
			((HttpServletResponse) response).setStatus(500);
			out.println(e.toString());
		} finally {
			out.flush();
			out.close();
		}
	}
}