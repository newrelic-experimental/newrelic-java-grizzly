package org.glassfish.grizzly.threadpool;

import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass)
public abstract class FixedThreadPool extends AbstractThreadPool {
	
	public FixedThreadPool(ThreadPoolConfig config) {
		super(config);
	}

	@Override
	public void execute(Runnable command) {
		Weaver.callOriginal();
	}

}
