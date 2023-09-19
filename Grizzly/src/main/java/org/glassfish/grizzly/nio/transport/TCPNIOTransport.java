package org.glassfish.grizzly.nio.transport;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class TCPNIOTransport {

	@SuppressWarnings("rawtypes")
	@Trace(dispatcher=true)
	public void fireIOEvent(IOEvent ioEvent,Connection connection,IOEventLifeCycleListener listener) {
		if (ioEvent == IOEvent.SERVER_ACCEPT || ioEvent == IOEvent.CLIENT_CONNECTED) {
			NewRelic.getAgent().getTransaction().ignore();
		} else {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "Grizzly", "TCPNIOTransport","FireIOEvent",ioEvent.name());
		}
		Weaver.callOriginal();
	}
}
