package org.davidliebman.webapps.awesomescores.client;

import java.util.ArrayList;

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
	void listServer (String input, AsyncCallback<ArrayList<Record>> asyncCallback)
			throws IllegalArgumentException;
	void deleteServer( Record highscore, AsyncCallback<String> asyncCallback)
			throws IllegalArgumentException;
	void checkUser (String input, AsyncCallback<Integer> asyncCallback )
			throws IllegalArgumentException;
}
