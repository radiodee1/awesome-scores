package org.davidliebman.webapps.awesomescores.server;

import org.davidliebman.webapps.awesomescores.shared.Record;
import org.webscale.json.JsonRequestHandler;
//import org.webscale.json.cmd.AppEngineCmdResults;
//import org.webscale.json.cmd.JsonCmd;

import com.google.gson.Gson;

public class JSONDataCollector implements JsonRequestHandler{

	
	ScoreManagerJDO manageJDO;// = new ScoreManagerJDO();

	 //private static final Logger log = Logger.getLogger(JsonCmdHandler.class);
     
     @Override
     public String executeRequest(String jsonRequestString) {
             //log.debug("JSON String received-"+ jsonRequestString);
             Gson gson = new Gson();
             Record rec = gson.fromJson(jsonRequestString, Record.class);
             Long keyReturned = new Long(0);
             //do some thing with the cmd on server
             manageJDO = new ScoreManagerJDO();
             keyReturned = manageJDO.saveRecord(new RecordSaver(rec,null));
             //AppEngineCmdResults results = ((Object) cmd).execute(cmd.getParameters());
             gson = new Gson();
             String resultJson = new String(keyReturned.toString());
             //String resultJson = gson.toJson(rec);
             return resultJson;
     }

	
}
