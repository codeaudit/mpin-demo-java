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
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.Enumeration;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.certivox.utils.StringUtils;


public class HTTPConnector {
	public static final int MAX_CONNECTIONS = 200;
	public static final int MAX_CONNECTIONS_PER_ROUTE = 20;
	public static final int MAX_COONECTIONS_FOR_HOST = 50;
	
	private static class HttpRequestFactory {
		public static final String HTTP_GET = "GET";
		public static final String HTTP_POST = "POST";
		public static final String HTTP_PUT = "PUT";
		public static final String HTTP_DELETE = "DELETE";
		public static final String HTTP_OPTIONS = "OPTIONS";
		public static final String HTTP_PATCH = "PATCH";
		
		public static HttpRequestBase createRequest(String serviceURL,  String http_method, String requestBody, Hashtable<String, String> requestProperties) throws UnsupportedEncodingException {
			HttpRequestBase httpRequest = null;
			
			if(HTTP_GET.equals(http_method))	   		{	httpRequest = new HttpGet(serviceURL);		} 
			else if(HTTP_POST.equals(http_method)) 		{	httpRequest = new HttpPost(serviceURL);		((HttpPost)httpRequest).setEntity(new StringEntity(requestBody));	}
			else if (HTTP_PUT.equals(http_method)) 		{	httpRequest = new HttpPut(serviceURL);		((HttpPut)httpRequest).setEntity(new StringEntity(requestBody));	} 
			else if (HTTP_DELETE.equals(http_method)) 	{	httpRequest = new HttpDelete(serviceURL);	} 
			else if(HTTP_OPTIONS.equals(http_method)) 	{	httpRequest = new HttpOptions(serviceURL);	} 
			else if (HTTP_PATCH.equals(http_method)) 	{	httpRequest = new HttpPatch(serviceURL);	((HttpPatch)httpRequest).setEntity(new StringEntity(requestBody));	}
			else 										{	throw new UnsupportedOperationException();	}
			
			if( requestProperties == null ) return httpRequest;  
			if( requestProperties.isEmpty() ) return httpRequest; 
			
			Enumeration<String> keyEnum = requestProperties.keys();
			while(keyEnum.hasMoreElements()) {
				String key = keyEnum.nextElement();
				httpRequest.addHeader(key, requestProperties.get(key));
			}
			return httpRequest;
		}
		
		public static HttpHost createHost(URI uri){
			 HttpHost host;
			 int port=uri.getPort();
			 if (port == -1) {
				 port=uri.getScheme().equalsIgnoreCase("https") ? 443 : 80;
			 }
			 host=new HttpHost(uri.getHost(),port,uri.getScheme());
			 return host;
		 }
	};
	
	private CloseableHttpClient httpClient;
	
	public HTTPConnector(String url) throws URISyntaxException {
		super();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(MAX_CONNECTIONS);
		cm.setDefaultMaxPerRoute(MAX_CONNECTIONS_PER_ROUTE);
		cm.setMaxPerRoute(new HttpRoute(HttpRequestFactory.createHost(new URI(url))), MAX_COONECTIONS_FOR_HOST);
		httpClient = HttpClients.custom()
									.setConnectionManager(cm)
										.build();
	}
	
	public HTTPResponse sendRequest( String serviceURL,  String http_method, String requestBody, Hashtable<String, String> requestProperties) throws IOException {
		HttpRequestBase httpRequest = HttpRequestFactory.createRequest(serviceURL, http_method, requestBody, requestProperties);
		CloseableHttpResponse response = httpClient.execute( httpRequest,  HttpClientContext.create() );
        try {
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            try {
            	return new HTTPResponse(response.getStatusLine().getStatusCode(),StringUtils.convertStreamToString(is));
            } finally {	is.close();	}
        } finally { response.close(); }   
	}
	
	public HTTPResponse sendRequest( String serviceURL,  String http_method, String requestBody) throws IOException {
		return sendRequest(serviceURL, http_method, requestBody, null);
	}
	
	public HTTPResponse sendRequest( String serviceURL,  String http_method) throws IOException {
		return sendRequest( serviceURL,  http_method, null);
	}
}
