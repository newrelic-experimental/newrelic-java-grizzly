package org.glassfish.grizzly.http.server;

import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class ErrorPageGenerator {

	public String generate(Request request, int status, String reasonPhrase,String description, Throwable exception) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		String requestURI = request.getRequestURI();
		if(requestURI != null && !requestURI.isEmpty()) {
			params.put("RequestURI", requestURI);
		}
		params.put("ResponseCode", status);
		
		if(reasonPhrase != null  && !reasonPhrase.isEmpty()) {
			params.put("ReasonPhrase", reasonPhrase);
		}
		
		if(description != null  && !description.isEmpty()) {
			params.put("Description", description);
		}
		
		if(exception != null) {
			NewRelic.noticeError(exception, params);
		} else {
			NewRelic.noticeError("Generated Error Page", params);
		}
		
		return Weaver.callOriginal();
	}
}
