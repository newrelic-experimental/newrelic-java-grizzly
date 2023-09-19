package org.glassfish.grizzly.filterchain;

import org.glassfish.grizzly.filterchain.FilterChainContext.Operation;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class DefaultFilterChain {

	@Trace(dispatcher=true)
	protected FilterExecution executeChainPart(FilterChainContext ctx,FilterExecutor executor,int start,int end,FiltersState filtersState) {
		Operation op = ctx.getOperation();
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","DefaultFilterChain","executeChainPart",op != null ? op.name() : "UnknownOperation"});
		return Weaver.callOriginal();
	}
	
	@Trace
	protected NextAction executeFilter(FilterExecutor executor,Filter currentFilter, FilterChainContext ctx) {
		Operation op = ctx.getOperation();
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","DefaultFilterChain","executeFilter",op != null ? op.name() : "UnknownOperation"});
		return Weaver.callOriginal();
	}
	
	@Weave
	private static class FiltersState {
		
	}
	
	@Weave
	private static class FilterExecution {
		
	}
}
