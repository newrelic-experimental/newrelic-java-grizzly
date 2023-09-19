package org.glassfish.grizzly.http.server;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.grizzly.http.InboundWrapper;
import com.nr.instrumentation.grizzly.http.OutboundWrapper;

@Weave(type=MatchType.BaseClass)
public abstract class HttpHandler {

	public abstract String getName();
	
	@Trace(dispatcher=true)
	public void service(Request request, Response response) {
		String name = getName() != null ? getName() : getClass().getSimpleName();
		NewRelic.addCustomParameter("RequestURI", request.getRequestURI());
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HttpHandler",name,"service"});
		Transaction transaction = NewRelic.getAgent().getTransaction();
		if(!transaction.isWebTransaction()) {
			transaction.convertToWebTransaction();
		}
		InboundWrapper inWrapper = new InboundWrapper(request);
		OutboundWrapper outWrapper = new OutboundWrapper(response);
		transaction.setWebRequest(inWrapper);
		transaction.setWebResponse(outWrapper);
		transaction.addOutboundResponseHeaders();
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	boolean doHandle(Request request, Response response) {
		String name = getName() != null ? getName() : getClass().getSimpleName();
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HttpHandler",name,"doHandle"});
		Transaction transaction = NewRelic.getAgent().getTransaction();
		if(!transaction.isWebTransaction()) {
			transaction.convertToWebTransaction();
		}
		InboundWrapper inWrapper = new InboundWrapper(request);
		OutboundWrapper outWrapper = new OutboundWrapper(response);
		transaction.setWebRequest(inWrapper);
		transaction.setWebResponse(outWrapper);
		transaction.addOutboundResponseHeaders();
		return Weaver.callOriginal();
	}
	
	@Trace
	private boolean runService(Request request, Response response) {
		String name = getName() != null ? getName() : getClass().getSimpleName();
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HttpHandler",name,"runService"});
		return Weaver.callOriginal();
	}
}
