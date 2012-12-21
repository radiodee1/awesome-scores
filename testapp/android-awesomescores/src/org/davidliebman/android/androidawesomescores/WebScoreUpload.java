package org.davidliebman.android.androidawesomescores;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.util.Log;

import com.google.gson.Gson;

public class WebScoreUpload {

	public static final String MY_URL = new String("http://10.0.2.2:8888/test.html");
	
	public String prepareAndSendRecord(RecordJson rec)  {
		
		
		Gson gson = new Gson();
		String cmd = gson.toJson(rec);
        String res = null;
        String key = null;
        String msg = null;
        try {
                res = sendToJsonClient(cmd);
                Gson gsonResult = new Gson();
                ReturnJson result = gsonResult.fromJson(res, ReturnJson.class);
                key = new String(new Long(result.getKey()).toString());
                msg = new String(result.getMessage());
        } catch (Exception e) {
                e.printStackTrace();
                //throw new Exception("fail" ,e);
        }
        return key + " - "+ msg;
        
	}

	public String sendToJsonClient(String cmd) throws Exception{
		String responseString = new String();

		
		Log.e("org.davidliebman", "command="+cmd);

		
		HttpClient httpclient = new DefaultHttpClient();
		
		HttpPost httppost = new HttpPost();
		httppost.setURI(new URI(MY_URL));

		//httppost.setHeader("User-Agent", "");
		httppost.setHeader("Content-type", "application/json");
		StringEntity entity = new StringEntity(cmd,HTTP.UTF_8 );
		
		httppost.setEntity(entity);
		
	    HttpResponse response = httpclient.execute(httppost);//?command="+cmd));
	    StatusLine statusLine = response.getStatusLine();
	    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        response.getEntity().writeTo(out);
	        out.close();
	        responseString = out.toString();
	        //..more logic
	    } else{
	        //Closes the connection.
	        response.getEntity().getContent().close();
	        throw new IOException(statusLine.getReasonPhrase());
	    }		
	    
	    
	    return responseString;
	}
}
