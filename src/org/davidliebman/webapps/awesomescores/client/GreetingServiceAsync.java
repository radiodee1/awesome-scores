package org.davidliebman.webapps.awesomescores.client;

import org.davidliebman.webapps.awesomescores.shared.Record;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	//void greetServer(String input, AsyncCallback<String> callback)
	//		throws IllegalArgumentException;
	void greetServer(Record highscore, AsyncCallback<String> asyncCallback)
			throws IllegalArgumentException;
}
