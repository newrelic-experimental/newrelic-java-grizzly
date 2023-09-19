package org.glassfish.grizzly;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class CompletionHandler<E> {
	
	@NewField
	public Token token = null;
	
	@NewField
	public String handlerFor = null;

	@Trace(async=true)
    public void cancelled() {
		if(handlerFor != null && !handlerFor.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","CompletionHandler",getClass().getSimpleName(),"cancelled",handlerFor});
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","CompletionHandler",getClass().getSimpleName(),"cancelled"});
		}
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
    	Weaver.callOriginal();
    }

	@Trace(async=true)
    public void failed(Throwable throwable) {
		if(handlerFor != null && !handlerFor.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","CompletionHandler",getClass().getSimpleName(),"failed",handlerFor});
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","CompletionHandler",getClass().getSimpleName(),"failed"});
		}
		NewRelic.noticeError(throwable);
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
    	Weaver.callOriginal();
    }

	@Trace(async=true)
    public void completed(E result) {
		if(handlerFor != null && !handlerFor.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","CompletionHandler",getClass().getSimpleName(),"completed",handlerFor});
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","CompletionHandler",getClass().getSimpleName(),"completed"});
		}
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
    	Weaver.callOriginal();
    }

	@Trace(async=true)
    public void updated(E result) {
		if(handlerFor != null && !handlerFor.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","CompletionHandler",getClass().getSimpleName(),"updated",handlerFor});
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","CompletionHandler",getClass().getSimpleName(),"updated"});
		}
		if(token != null) {
			token.link();
		}
    	Weaver.callOriginal();
    }

}
