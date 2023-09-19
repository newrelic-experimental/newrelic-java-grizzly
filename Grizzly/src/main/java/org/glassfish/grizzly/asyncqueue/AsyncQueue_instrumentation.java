package org.glassfish.grizzly.asyncqueue;

import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.asyncqueue.AsyncQueue.AsyncResult;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface,originalName="org.glassfish.grizzly.asyncqueue.AsyncQueue")
public abstract class AsyncQueue_instrumentation {

	@Trace
	public AsyncResult processAsync(Context context) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","AsyncQueue",getClass().getSimpleName(),"processAsync"});
		return Weaver.callOriginal();
	}
}
