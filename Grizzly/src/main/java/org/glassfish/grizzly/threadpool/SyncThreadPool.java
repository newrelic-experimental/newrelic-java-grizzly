package org.glassfish.grizzly.threadpool;

import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public class SyncThreadPool extends AbstractThreadPool {
	
	public SyncThreadPool(ThreadPoolConfig config) {
        super(config);
	}

	@Override
	public void execute(Runnable command) {
		Weaver.callOriginal();
	}

}
