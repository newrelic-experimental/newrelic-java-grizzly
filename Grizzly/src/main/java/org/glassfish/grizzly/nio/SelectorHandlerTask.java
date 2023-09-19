package org.glassfish.grizzly.nio;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class SelectorHandlerTask {

	@Trace(dispatcher=true)
	public boolean run(SelectorRunner selectorRunner) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","SelectorHandlerTask",getClass().getSimpleName(),"run"});
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
    public void cancel() {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","SelectorHandlerTask",getClass().getSimpleName(),"cancel"});
		Weaver.callOriginal();
	}
}
