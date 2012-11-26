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

	ScoreManagerJDO manageJDO;// = new ScoreManagerJDO();
	
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

		manageJDO = new ScoreManagerJDO();
		long retValue = manageJDO.saveRecord(highScore);
		
		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent
				+ "<br><br> Game Name:" + inputName.toString() 
				+ "<br> Score: " + inputScore 
				+ "<br> Lives: " + inputLives
				+ "<br> Level: " + inputLevel
				+ "<br> Game Speed " + inputSpeed
				+ "<br> [" + retValue + "]";
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
//		Record highScore = new Record();
//		highScore.setName(input);

		ArrayList<Record> list = new ArrayList<Record>();
		manageJDO = new ScoreManagerJDO();
		list = manageJDO.getList(input);
		
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
