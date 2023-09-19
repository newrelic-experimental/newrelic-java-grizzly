package org.glassfish.grizzly;

import org.glassfish.grizzly.asyncqueue.MessageCloner;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@SuppressWarnings("rawtypes")
@Weave(type=MatchType.Interface)
public abstract class Processor<E extends Context> {

	@Trace
	public ProcessorResult process(E context) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Processor",getClass().getSimpleName(),"process"});
		ProcessorResult processorResult = Weaver.callOriginal();
		return processorResult;
	}
	
	@Trace
	public void read(Connection connection,CompletionHandler<ReadResult> completionHandler) {
		if(completionHandler.handlerFor == null) {
			completionHandler.handlerFor = "Processor-"+getClass().getSimpleName()+"-read";
		}
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Processor",getClass().getSimpleName(),"read"});
		Weaver.callOriginal();
	}
	
	@Trace
	public void write(Connection connection,Object dstAddress, Object message,CompletionHandler<WriteResult> completionHandler) {
		if(completionHandler.handlerFor == null) {
			completionHandler.handlerFor = "Processor-"+getClass().getSimpleName()+"-write";
		}
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Processor",getClass().getSimpleName(),"write"});
		Weaver.callOriginal();
	}
    
	@Trace
    public void write(Connection connection,Object dstAddress, Object message,CompletionHandler<WriteResult> completionHandler,MessageCloner messageCloner) {
		if(completionHandler.handlerFor == null) {
			completionHandler.handlerFor = "Processor-"+getClass().getSimpleName()+"-write";
		}
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Processor",getClass().getSimpleName(),"write"});
		Weaver.callOriginal();
    }
    
}
