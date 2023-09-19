package org.glassfish.grizzly.strategies;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.IOEvent;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class WorkerThreadIOStrategy {

	@SuppressWarnings("rawtypes")
	@Trace(dispatcher=true)
	public boolean executeIoEvent(Connection connection,IOEvent ioEvent, boolean isIoEventEnabled) {
		return Weaver.callOriginal();
	}
}
