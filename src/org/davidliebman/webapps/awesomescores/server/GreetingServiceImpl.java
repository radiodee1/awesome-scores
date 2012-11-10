package org.davidliebman.webapps.awesomescores.server;

import org.davidliebman.webapps.awesomescores.client.GreetingService;
import org.davidliebman.webapps.awesomescores.shared.FieldVerifier;
import org.davidliebman.webapps.awesomescores.shared.Record;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	//public String greetServer(String input) throws IllegalArgumentException {
	public String greetServer(Record highScore) throws IllegalArgumentException {
	// Verify that the input is valid. 
		String input = new String( highScore.getName());
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}
//	public String greetServer(Record highscore) throws IllegalArgumentException {
//		return "Here goes list information <br>";
//		
//	}
	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
