package org.davidliebman.webapps.awesomescores.client;

import java.util.ArrayList;

import org.davidliebman.webapps.awesomescores.shared.Record;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	//String greetServer(String name) throws IllegalArgumentException;
	String greetServer( Record highScore) throws IllegalArgumentException;
	ArrayList<Record> listServer( String input) throws IllegalArgumentException;
	String deleteServer(Record highScore ) throws IllegalArgumentException;
}
