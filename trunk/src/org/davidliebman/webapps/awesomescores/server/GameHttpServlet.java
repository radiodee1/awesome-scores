package org.davidliebman.webapps.awesomescores.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GameHttpServlet extends HttpServlet {

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		JSONDataCollector scores = new JSONDataCollector();
		scores.setJsonIn(request.getReader());
		scores.executeRequest();
		
		response.getOutputStream().println(scores.getJsonOut());
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		JSONDataCollector scores = new JSONDataCollector();
		scores.setJsonIn(request.getReader());
		scores.executeRequest();
		
		response.getOutputStream().println(scores.getJsonOut());
	}
	
}
