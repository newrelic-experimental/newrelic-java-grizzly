package org.glassfish.grizzly;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.grizzly.GrizzlyUtils;

@Weave
public class ProcessorExecutor {

	@Trace
	public static void execute(Context context) {
		String eventName = GrizzlyUtils.getEventName(context);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] { "Custom", "Grizzly", "ProcessorExecutor", "execute", eventName });
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Grizzly", new String[] { "Grizzly", "ProcessorExecutor", "execute", eventName });

		Weaver.callOriginal();
	}

	@Trace(async = true)
	static void complete(Context context, ProcessorResult result) {
		Weaver.callOriginal();
	}

	@Trace(async = true)
	private static void error(Context context, Object description) {
		if (description instanceof Throwable) {
			Throwable t = (Throwable)description;
			NewRelic.noticeError(t);
		} 
		Weaver.callOriginal();
	}	
}
