test 
<%@ page import = " java.util.* " %>

<% 

//import java.util;

//out.println("hello"); 
org.davidliebman.webapps.awesomescores.server.JSONDataCollector scores = new
org.davidliebman.webapps.awesomescores.server.JSONDataCollector();
scores.setJsonIn(request.getReader());
scores.executeRequest();
out.println(scores.getJsonOut());

out.println("<br>\n");

Enumeration enumeration = request.getHeaderNames();

while (enumeration.hasMoreElements()) {

String string = (String)enumeration.nextElement();

out.println("<font size = 3>" +string +": " + request.getHeader(string)+ "</font><br>");

}
out.println("----------------------<br>\n");

out.println(request.getMethod() + "xx");

Enumeration attributes = request.getParameterNames();

while (attributes.hasMoreElements()) {

String string = (String)attributes.nextElement();

out.println("<font size = 3>" +string +": " + request.getParameter(string)+ "</font><br>");

}
%>