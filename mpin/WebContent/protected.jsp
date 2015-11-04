<!--
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

   Certivox JavaScript M-Pin Authentication Functions

   Provides these functions:
   calculateMPinToken     Calculates the MPin Token 
   local_entropy          Gets an entropy value from the client machine
   randomX                Calculates a random 254 bit value
   addShares              Add two points on the curve that are originally in hex format
   pass1Request           Form the JSON request for pass one of the M-Pin protocol
   pass2Request           Form the JSON request for pass two of the M-Pin protocol
-->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.certivox.rpa.AppServletContextListener" %>
<%@ page import="com.certivox.managers.CookieManager" %>
<%@ page import="com.certivox.managers.SessionManager" %>
<%@ page import="javax.servlet.http.Cookie" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>M-Pin demo</title>
    <link href="css/certivox.css" rel="stylesheet" type="text/css" />
    <link rel="shortcut icon" href="images/favicon.ico">
    <link href='//fonts.googleapis.com/css?family=Roboto:400,400italic,700,700italic' rel='stylesheet' type='text/css'>

	  <% 
        	CookieManager cm  = AppServletContextListener.defaultCookieManager();
        	Cookie cookie = cm.getCookie(request.getCookies());
        	String sessionId = (cookie == null)? (null):(cookie.getValue());
        	SessionManager<String> sm = AppServletContextListener.defaultSessionManager();
        	String userId = (sessionId == null) ? (null):(sm.getSession(sessionId));        	
       %>
	
	
    <script type="text/javascript">
        function createAjax()
        {
            if (typeof XMLHttpRequest != "undefined")
            {
                return new XMLHttpRequest();
            }
            else if (window.ActiveXObject)
            {
                var aVersions = ["MSXML2.XMLHttp.5.0","MSXML2.XMLHttp.4.0","MSXML2.XMLHttp.3.0","MSXML2.XMLHttp","Microsoft.XMLHttp"];

                for (var i = 0; i < aVersions.length; i++)
                {
                    try
                    {
                        var oXmlHttp = new ActiveXObject(aVersions[i]);
                        return oXmlHttp;
                    }
                    catch(oError)
                    {
                        throw new Error("XMLHttp object could be created.");
                    }
                }
            }
            throw new Error("XMLHttp object could be created.");
        }

    </script>
</head>

<body>
    <div id="header">
        <div class="container">
            <a href="http://certivox.com" target="_blank" class="logo1"><img src="images/certivox-logo.png" alt="CertiVox Logo" width="179" height="57" title="CertiVox Logo" style="border-style: none"></a>
            <a href="http://www.certivox.com/m-pin/", target="_blank" class="logo2"><img alt="M-Pin strong authentication logo" src="images/m-pin-logo-strong.png" width="184" height="54" title="M-Pin strong authentication logo" style="border-style: none"></a>
        </div>
        <div class="clear"></div>
    </div>

    <%
  		if(userId != null)  {
    %>
        <div id="loggedInHolder"><div class="loggedInStatus">You are logged in as: <%= userId%>   |   <a href="logout"> Log Out </a></div></div>
    <%
    	}
    %>

    <div id="content">
        <div class="container">
        <div class="nav">
            <ul>
                <li><a href="http://www.certivox.com" target="_blank">CertiVox Web Site</a></li>
                <!-- <li><a href="/protected">Protected</a></li> -->
                <li><a href=" http://www.certivox.com/docs/m-pin-core/aws/" target="_blank">M-Pin Documentation</a></li>
                <!-- <li><a href="https://crypto.certivox.org" target="_blank">Crypto</a></li> -->
                <li><a href="http://discuss.certivox.com" target="_blank">Community Support</a></li>
            </ul>
            <div class="clear"></div>
        </div>
        <div class="content">
        <%
        	if(userId!=null) {
        %>
            <h1><%= userId %>, you are now logged in!</h1>
        <%
        	} 
        %>
          
         <%
        	if(userId==null) {
        %>
            <section>
                <div class="page-header section-header">
                    <h1>Usernames and Passwords are history</h1>
                    <div style="float:left;"><img width="501" height="344" style="float: right;" title="M-Pin authentication server, inherently more secure than usernames and passwords." src="images/landing-page2.jpg" alt="M-Pin authentication server, inherently more secure than usernames and passwords."></div>
                    <div class="clear"></div>
                    <p class="secondary-header"><span class="section-subheader" id="hs_cos_wrapper_subheader">Security has evolved; the Future of Strong Authentication is here.</span></p>
                    <div class="clear"></div>
                </div>
            </section>
         <%
        	} else { 
        %>
            <section class="center"><p>You see this page because you are logged in. <a href="logout">Log out</a></p></section>
            <section>
                <div class="page-header section-header">
                    <h1>Usernames and Passwords are history</h1>
                    <div style="float:left;"><img width="501" height="344" style="float: right;" title="M-Pin authentication server, inherently more secure than usernames and passwords." src="images/landing-page2.jpg" alt="M-Pin authentication server, inherently more secure than usernames and passwords."></div>
                    <div class="clear"></div>
                    <p class="secondary-header"><span class="section-subheader" id="hs_cos_wrapper_subheader">Security has evolved; the Future of Strong Authentication is here.</span></p>
                    <div class="clear"></div>
                </div>
            </section>
        <%
        	}
        %>

        <div class="clear"></div>
        <div id="footer">&copy; 201455 CertiVox UK Limited, All Rights Reserved.</div>
    </div>
    </div>
</body>
</html>
