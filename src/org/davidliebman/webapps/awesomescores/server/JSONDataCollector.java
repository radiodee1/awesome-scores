package org.davidliebman.webapps.awesomescores.server;

import java.io.BufferedReader;
import java.io.IOException;
//import java.util.logging.Logger;

import org.davidliebman.webapps.awesomescores.shared.Record;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.gson.Gson;

public class JSONDataCollector {

	
	public static final String ERROR = new String("ERROR");
	
	ScoreManagerJDO manageJDO;// = new ScoreManagerJDO();
	WebAuthServer auth;
	
	private String[] games;
	
	private String jsonIn = new String();
	private String jsonOut = new String();
	//private static final Logger log = Logger.getLogger(JSONDataCollector.class);
	private Record rec = new Record();
     
	public JSONDataCollector () {
		games = new String[2];
		games[0] = new String(WebAuthServer.AUTH_CLIENT_1);
		games[1] = new String(WebAuthServer.AUTH_CLIENT_2);
		auth = new WebAuthServer(games, WebAuthServer.AUTH_AUDIENCE);
	}
	
	
	public void executeRequest() {
		setJsonOut(executeRequest(getJsonIn()));
	}
	
     private String executeRequest(String jsonRequestString) {
             Gson gson = new Gson();
             try {
             
            	 rec = gson.fromJson(jsonRequestString, Record.class);
            	 if (rec == null) return new String(ERROR + " 0");
             }
             catch(NullPointerException e) {
            	 e.printStackTrace();
            	 return new String(ERROR+"1");
             }
             
             GoogleIdToken.Payload payload = auth.check(rec.getAuthToken());
             
             if (payload == null ) {
            	 System.err.println("detected problem: " + auth.problem());
            	 
            	 return null;
             }
             else {
            	 rec.setEmail(payload.getIssuer());
            	 System.err.println(payload.getIssuer() + " email?");
            	 System.err.println(payload.getIssuee() + " me?");
             }
             
             Long keyReturned = new Long(0);
             //do some thing with the stuff on server
             manageJDO = new ScoreManagerJDO();
             
             try {
             
            	 keyReturned = manageJDO.saveRecord(new RecordSaver(rec,null));

             }
             catch (Exception e) {
            	 e.printStackTrace();
            	 return new String(ERROR + "2");
             }
             String resultJson = new String();
             if (keyReturned == 0 ) {
            	 resultJson = new String(ERROR+"3");
             }
             ReturnJson result = new ReturnJson();
             result.setKey(keyReturned);
             result.setError(ReturnJson.ERROR_SUBMISSION_OK);
             result.setMessage("Have a nice day.");
             result.setVersion(1);
             
             Gson gsonResult = new Gson();
             resultJson = gsonResult.toJson(result);
             
             //resultJson = new String(keyReturned.toString());
             
             return resultJson;
     }


     public void testMe() {
    	 this.setJsonOut(jsonIn);
     }
     
	public String getJsonIn() {
		return jsonIn;
	}


	public void setStringJsonIn(String jsonIn) {
		this.jsonIn = jsonIn;
	}

	public void setJsonIn(BufferedReader in) {
		try {
			String sCurrentLine;
			 
			while ((sCurrentLine = in.readLine()) != null) {
				setStringJsonIn(this.jsonIn + sCurrentLine);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getJsonOut() {
		return jsonOut;
	}


	public void setJsonOut(String jsonOut) {
		this.jsonOut = jsonOut;
	}

	public Record getRec() {
		return rec;
	}

	public void setRec(Record rec) {
		this.rec = rec;
	}

    
	
}
