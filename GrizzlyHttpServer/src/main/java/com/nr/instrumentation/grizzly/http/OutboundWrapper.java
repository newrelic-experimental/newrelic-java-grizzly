package com.nr.instrumentation.grizzly.http;

import org.glassfish.grizzly.http.HttpResponsePacket;
import org.glassfish.grizzly.http.server.Response;

import com.newrelic.api.agent.ExtendedResponse;
import com.newrelic.api.agent.HeaderType;

public class OutboundWrapper extends ExtendedResponse {
	private Response response = null;
	
	public OutboundWrapper(Response resp) {
		response = resp;
	}

	@Override
	public int getStatus() throws Exception {
		return response.getStatus();
	}

	@Override
	public String getStatusMessage() throws Exception {
		HttpResponsePacket respPacket = response.getResponse();
		if(respPacket != null) {
			return respPacket.getReasonPhraseRawDC().toString();
		}
		return null;
	}

	@Override
	public String getContentType() {
		return response.getContentType();
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public void setHeader(String name, String value) {
		response.setHeader(name, value);
	}

	@Override
	public long getContentLength() {
		return response.getContentLengthLong();
	}

}
