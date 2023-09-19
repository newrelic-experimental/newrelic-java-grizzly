package org.glassfish.grizzly.nio;

import java.net.SocketAddress;

import org.glassfish.grizzly.CloseReason;
import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.WriteResult;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass)
public abstract class NIOConnection {

	@Trace(dispatcher=true)
	public <M> void read(CompletionHandler<ReadResult<M, SocketAddress>> completionHandler) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","NIOConnection",getClass().getSimpleName(),"read"});
		completionHandler.handlerFor = "Connection-Read";
		Weaver.callOriginal();
	}
	
	@SuppressWarnings("deprecation")
	@Trace(dispatcher=true)
	public <M> void write(SocketAddress dstAddress,M message,CompletionHandler<WriteResult<M, SocketAddress>> completionHandler,org.glassfish.grizzly.asyncqueue.PushBackHandler pushbackHandler) {
		completionHandler.handlerFor = "Connection-Write";
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	protected void terminate0(CompletionHandler<Closeable> completionHandler,CloseReason closeReason) {
		completionHandler.handlerFor = "Connection-Terminate";
		Weaver.callOriginal();
	}
}
