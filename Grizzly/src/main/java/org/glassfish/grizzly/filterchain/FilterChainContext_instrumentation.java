package org.glassfish.grizzly.filterchain;

import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.filterchain.FilterChainContext.CompletionListener;
import org.glassfish.grizzly.filterchain.FilterChainContext.Operation;

import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.grizzly.NRCompletionListener;

@Weave(originalName="org.glassfish.grizzly.filterchain.FilterChainContext")
public abstract class FilterChainContext_instrumentation {

	@NewField
	protected boolean hasNRListener = false;


	public void addCompletionListener(CompletionListener listener) {
		if(listener instanceof NRCompletionListener) {
			hasNRListener = true;
		}
		Weaver.callOriginal();
	}

	@SuppressWarnings("rawtypes")
	public static FilterChainContext_instrumentation create(Connection connection,Closeable closeable) {
		FilterChainContext_instrumentation instance = Weaver.callOriginal();
		return instance;
	}
	
	abstract Operation getOperation();
	
	public abstract Context getInternalContext();
}
