package org.glassfish.grizzly.filterchain;

import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.ProcessorResult;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.WriteResult;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.grizzly.NRCompletionListener;

@SuppressWarnings("rawtypes")
@Weave(type=MatchType.Interface)
public abstract class FilterChain {
	
	@Trace(async=true)
    public ProcessorResult execute(FilterChainContext_instrumentation context) {
		if(!context.hasNRListener) {
			context.addCompletionListener(new NRCompletionListener());
		}
		String op = context.getOperation() != null ? context.getOperation().name() : null;
		if(op != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","FilterChain",getClass().getSimpleName(),"execute",op});
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","FilterChain",getClass().getSimpleName(),"execute"});
		}
    	return Weaver.callOriginal();
    }

	@Trace(dispatcher=true)
	public void flush(Connection connection,CompletionHandler<WriteResult> completionHandler) {
		if(completionHandler.handlerFor == null) {
			completionHandler.handlerFor = "FilterChain-"+getClass().getSimpleName()+"-flush";
		}
    	Weaver.callOriginal();
    }

	@Trace(dispatcher=true)
    public void fireEventUpstream(Connection connection,FilterChainEvent event,CompletionHandler<FilterChainContext_instrumentation> completionHandler) {
		if(completionHandler.handlerFor == null) {
			completionHandler.handlerFor = "FilterChain-"+getClass().getSimpleName()+"-fireEventUpstream";
		}
		String name = "UnknownEventType";
		if(event != null) {
			Object type = event.type();
			if(type != null) {
				name = type.getClass().getSimpleName();
			}
		}
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","FilterChain",getClass().getSimpleName(),"fireEventUpstream",name});
    	Weaver.callOriginal();
    }
    
	@Trace(dispatcher=true)
    public void fireEventDownstream(Connection connection,FilterChainEvent event,CompletionHandler<FilterChainContext> completionHandler) {
		if(completionHandler.handlerFor == null) {
			completionHandler.handlerFor = "FilterChain-"+getClass().getSimpleName()+"-fireEventDownstream";
		}
		String name = "UnknownEventType";
		if(event != null) {
			Object type = event.type();
			if(type != null) {
				name = type.getClass().getSimpleName();
			}
		}
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","FilterChain",getClass().getSimpleName(),"fireEventDownstream",name});
    	Weaver.callOriginal();
    }

	@Trace(async=true)
    public ReadResult read(FilterChainContext context) {
		String op = context.getOperation() != null ? context.getOperation().name() : null;
		if(op != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","FilterChain",getClass().getSimpleName(),"read",op});
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","FilterChain",getClass().getSimpleName(),"read"});
		}
    	return Weaver.callOriginal();
    }

	@Trace(async=true)
    public void fail(FilterChainContext context, Throwable failure) {
		NewRelic.noticeError(failure);
    	Weaver.callOriginal();
    }

}
