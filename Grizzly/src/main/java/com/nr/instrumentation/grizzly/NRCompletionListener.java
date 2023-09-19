package com.nr.instrumentation.grizzly;

import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.FilterChainContext.CompletionListener;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

public class NRCompletionListener implements CompletionListener {
	
	private static boolean isTransformed = false;
	
	public NRCompletionListener() {
		if(!isTransformed) {
			isTransformed = true;
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
		}
	}

	@Override
	@Trace(dispatcher=true)
	public void onComplete(FilterChainContext context) {
		int hashCode = context.hashCode();
		
		Token token = GrizzlyUtils.removeToken(hashCode);
		if(token != null) {
			token.linkAndExpire();
		}
	}

}
