package org.glassfish.grizzly.nio;

import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.nio.SelectorHandler.Task;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class DefaultSelectorHandler {
	
	@Trace(dispatcher=true)
	public void enque(SelectorRunner selectorRunner,Task task,CompletionHandler<Task> completionHandler) {
		if(selectorRunner != null) {
			completionHandler.handlerFor = "SelectorTask-Enque";
			
			completionHandler.token = NewRelic.getAgent().getTransaction().getToken();
		}
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public void execute(SelectorRunner selectorRunner,Task task,CompletionHandler<Task> completionHandler) {
		if(selectorRunner != null) {
			completionHandler.handlerFor = "SelectorTask-Execute";
			
			completionHandler.token = NewRelic.getAgent().getTransaction().getToken();
		}
		Weaver.callOriginal();
	}
	
	@Weave
	protected static class RunnableTask {
		
		@NewField
		Token token = null;
		
		private RunnableTask(Task task,CompletionHandler<Task> completionHandler) {
			token = completionHandler.token;
		}
		
		@Trace(async=true)
		public boolean run(SelectorRunner selectorRunner) {
			if(token != null) {
				token.linkAndExpire();
				token = null;
			}
			return Weaver.callOriginal();
		}
		
		@Trace(async=true)
		public void cancel() {
			if(token != null) {
				token.linkAndExpire();
				token = null;
			}
			Weaver.callOriginal();
		}
	}
}
