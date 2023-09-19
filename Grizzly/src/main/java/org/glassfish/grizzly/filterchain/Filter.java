package org.glassfish.grizzly.filterchain;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class Filter {

	@Trace
    public NextAction handleRead(FilterChainContext ctx) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Filter",getClass().getSimpleName(),"handleRead"});
    	return Weaver.callOriginal();
    }

	@Trace
    public NextAction handleWrite(FilterChainContext ctx) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Filter",getClass().getSimpleName(),"handleWrite"});
    	return Weaver.callOriginal();
    }

	@Trace
    public NextAction handleConnect(FilterChainContext ctx) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Filter",getClass().getSimpleName(),"handleConnect"});
    	return Weaver.callOriginal();
    }

	@Trace
    public NextAction handleAccept(FilterChainContext ctx) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Filter",getClass().getSimpleName(),"handleAccept"});
    	return Weaver.callOriginal();
    }

	@Trace
    public NextAction handleEvent(FilterChainContext ctx,FilterChainEvent event) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Filter",getClass().getSimpleName(),"handleEvent"});
    	return Weaver.callOriginal();
    }

	@Trace
    public NextAction handleClose(FilterChainContext ctx) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Filter",getClass().getSimpleName(),"handleClose"});
    	return Weaver.callOriginal();
    }
}
