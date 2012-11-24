package org.davidliebman.webapps.awesomescores.server;

import java.util.ArrayList;

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
		String input =  highScore.getEmail();
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}
		String inputName =  highScore.getName();
		int inputScore =  highScore.getScore();
		int inputLives =  highScore.getLives();
		int inputLevel =  highScore.getLevel();
		int inputSpeed =  highScore.getGameSpeed();

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		inputName = escapeHtml(inputName);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent
				+ "<br><br> Game Name:" + inputName.toString() 
				+ "<br> Score: " + inputScore 
				+ "<br> Lives: " + inputLives
				+ "<br> Level: " + inputLevel
				+ "<br> Game Speed " + inputSpeed;
	}

	public ArrayList<Record> listServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		//String input =  highScore.getEmail();
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}
		Record highScore = new Record();
		highScore.setName(input);

		Record highScore2 = new Record();
		highScore2.setName("name 2");
		Record highScore3 = new Record();
		highScore3.setName("name 3");
		Record highScore4 = new Record();
		highScore4.setName("name 4");

		Record highScore5 = new Record();
		highScore5.setName("name 2");
		Record highScore6 = new Record();
		highScore6.setName("name 3");
		Record highScore7 = new Record();
		highScore7.setName("name 4");

		Record highScore8 = new Record();
		highScore8.setName("name 2");
		Record highScore9 = new Record();
		highScore9.setName("name 3");
		Record highScore10 = new Record();
		highScore10.setName("name 4");

		
		

		ArrayList<Record> list = new ArrayList<Record>();
		list.add(highScore);
		list.add(highScore2);
		list.add(highScore3);
		list.add(highScore4);
		list.add(highScore5);
		list.add(highScore6);
		list.add(highScore7);
		list.add(highScore8);
		list.add(highScore9);
		list.add(highScore10);
		//list.add(highScore3);
		//list.add(highScore4);
		return list;


	}
	
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
