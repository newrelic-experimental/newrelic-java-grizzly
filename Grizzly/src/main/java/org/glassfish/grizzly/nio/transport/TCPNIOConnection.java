package org.glassfish.grizzly.nio.transport;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.nio.NIOConnection;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public class TCPNIOConnection extends NIOConnection {

	@Trace(dispatcher=true)
	protected void onRead(Buffer data, int size) {
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	protected void onWrite(Buffer data, long size) {
		Weaver.callOriginal();
	}
}
