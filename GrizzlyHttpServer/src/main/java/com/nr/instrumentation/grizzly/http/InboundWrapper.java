package com.nr.instrumentation.grizzly.http;

import java.util.Enumeration;
import java.util.Set;
import java.util.Vector;

import org.glassfish.grizzly.http.Cookie;
import org.glassfish.grizzly.http.server.Request;

import com.newrelic.api.agent.ExtendedRequest;
import com.newrelic.api.agent.HeaderType;

public class InboundWrapper extends ExtendedRequest {
	
	private Request request = null;
	
	public InboundWrapper(Request req) {
		request = req;
	}

	@Override
	public String getRequestURI() {
		return request.getRequestURI();
	}

	@Override
	public String getRemoteUser() {
		return request.getRemoteUser();
	}

	@Override
	public Enumeration getParameterNames() {
		Set<String> paramNames = request.getParameterNames();
		
		if(paramNames != null) {
			new Vector<String>(paramNames).elements();
		}
		return null;
	}

	@Override
	public String[] getParameterValues(String name) {
		return request.getParameterValues(name);
	}

	@Override
	public Object getAttribute(String name) {
		return request.getAttribute(name);
	}

	@Override
	public String getCookieValue(String name) {
		Cookie[] cookies = request.getCookies();
		for(int i=0;i<cookies.length;i++) {
			if(cookies[i].getName().equalsIgnoreCase(name)) {
				return cookies[i].getValue();
			}
		}
		return null;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public String getHeader(String name) {
		return request.getHeader(name);
	}

	@Override
	public String getMethod() {
		return request.getMethod().getMethodString();
	}

}
