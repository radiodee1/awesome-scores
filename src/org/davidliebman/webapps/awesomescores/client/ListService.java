package org.davidliebman.webapps.awesomescores.client;

import java.util.ArrayList;

import org.davidliebman.webapps.awesomescores.shared.Record;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface ListService extends RemoteService {
	//String greetServer(String name) throws IllegalArgumentException;
	ArrayList<Record> listServer( String input) throws IllegalArgumentException;
}
