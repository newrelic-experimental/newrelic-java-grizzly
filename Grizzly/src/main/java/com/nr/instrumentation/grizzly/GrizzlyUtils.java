package com.nr.instrumentation.grizzly;

import java.util.HashMap;

import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.IOEvent;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;

public class GrizzlyUtils {

	private static final String NEWRELICAGENT = "com.newrelic.agent";


	public static String getEventName(Context ctx) {
		String name = "UnknownEvent";
		if(ctx != null) {
			IOEvent event = ctx.getIoEvent();
			return getEventName(event);
		}
		return name;
	}

	public static String getEventName(IOEvent event) {
		String name = "UnknownEvent";
		if(event == IOEvent.ACCEPTED) {
			return "Accepted";
		}
		if(event == IOEvent.CLIENT_CONNECTED ) {
			return "Client_Connected";
		}
		if(event == IOEvent.CLOSED) {
			return "Closed";
		}
		if(event == IOEvent.CONNECTED) {
			return "Connected";
		}
		if(event == IOEvent.NONE) {
			return "None";
		}
		if(event == IOEvent.READ) {
			return "Read";
		}
		if(event == IOEvent.SERVER_ACCEPT) {
			return "Server_Accept";
		}
		if(event == IOEvent.WRITE) {
			return "Write";
		}
		return name;
	}

	public static boolean hasToken(Integer hash) {
		return filterChainTokenCache.containsKey(hash);
	}
	
	private static HashMap<Integer, Token> filterChainTokenCache = new HashMap<Integer, Token>();
	
	public static void addToken(Integer hash, Token token) {
		filterChainTokenCache.put(hash, token);
	}
	
	public static Token removeToken(Integer hash) {
		return filterChainTokenCache.remove(hash);
	}
	
	public static void replaceToken(Integer oldHash, Integer newHash, Token newToken) {
		Token oldToken = filterChainTokenCache.remove(oldHash);
		if(oldToken != null) {
			oldToken.expire();
		}
		addToken(newHash,newToken);
	}
	
	public static Token getToken(Integer hash) {
		return filterChainTokenCache.get(hash);
	}
	
	public static NRRunnable getWrapper(Runnable runnable) {
		if(runnable == null || runnable instanceof NRRunnable) return null;
		
		Package runPackage = runnable.getClass().getPackage();
		if(runPackage.getName().startsWith(NEWRELICAGENT)) return null;
		
		Token token = NewRelic.getAgent().getTransaction().getToken();
		if(token == null) return null;
		if(!token.isActive()) {
			token.expire();
			token = null;
		}
		
		return new NRRunnable(runnable, token);
		
	}
}
