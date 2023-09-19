package org.glassfish.grizzly.nio.transport;

import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.FilterChainContext.TransportContext;
import org.glassfish.grizzly.filterchain.NextAction;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.grizzly.GrizzlyUtils;

@Weave
public class TCPNIOTransportFilter {

	
	@Trace
    public NextAction handleRead(FilterChainContext ctx) {
		TransportContext transportCtx = ctx.getTransportContext();
		if(!transportCtx.isBlocking()) {
			int hash = hashCode();
			if(GrizzlyUtils.hasToken(hash)) {
				Token t = GrizzlyUtils.removeToken(hash);
				t.linkAndExpire();
			} else {
				Token t = NewRelic.getAgent().getTransaction().getToken();
				if(t != null && t.isActive()) {
					GrizzlyUtils.addToken(hash, t);
				}
			}
		}
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Filter",getClass().getSimpleName(),"handleRead"});
    	return Weaver.callOriginal();
    }

	@Trace
    public NextAction handleWrite(FilterChainContext ctx) {
		TransportContext transportCtx = ctx.getTransportContext();
		if(!transportCtx.isBlocking()) {
			int hash = hashCode();
			if(GrizzlyUtils.hasToken(hash)) {
				Token t = GrizzlyUtils.removeToken(hash);
				t.linkAndExpire();
			} else {
				Token t = NewRelic.getAgent().getTransaction().getToken();
				if(t != null && t.isActive()) {
					GrizzlyUtils.addToken(hash, t);
				}
			}
		}
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Filter",getClass().getSimpleName(),"handleWrite"});
    	return Weaver.callOriginal();
    }

}
