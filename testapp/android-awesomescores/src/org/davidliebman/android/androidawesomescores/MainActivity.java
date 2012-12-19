package org.davidliebman.android.androidawesomescores;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.IOException;
//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.webscale.json.client.JsonClient;
import org.webscale.json.cmd.AppEngineCmdResults;
import org.webscale.json.cmd.JsonCmd;

import com.google.gson.Gson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	

		Button mGoButton = (Button) findViewById(R.id.button1);
	    mGoButton.setOnClickListener(new OnClickListener() {
	    	@Override
	        public void onClick(View v) {
				TextView mtext = (TextView) findViewById(R.id.text_output);
	        	
	    		new AsyncTask <Object, Object, Object>() {

	    			 @Override
	    		        protected void onPreExecute() {
	    		            // TODO Auto-generated method stub
	    		            //super.onPostExecute(result);
	    		            Toast.makeText(MainActivity.this, "task started.", Toast.LENGTH_LONG).show();
	    		        }
	    			
					@Override
					protected Object doInBackground(Object... params) {
						TextView mtext = (TextView) findViewById(R.id.text_output);

			        	//mtext.setText(someExecuteMethod());
						return someExecuteMethod();
					}
					
					 @Override
				        protected void onPostExecute(Object result) {
				            // TODO Auto-generated method stub
				            //super.onPostExecute(result);
				            Toast.makeText(MainActivity.this, (String) result, Toast.LENGTH_LONG).show();
				        }
	    		}.execute(new Object());
	    		//mtext.setText("waiting...2");
	        	
	        	Toast.makeText(MainActivity.this, "And We're Off", Toast.LENGTH_SHORT).show();
	        }
	
			
	    });
    
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public String someExecuteMethod()  {
		RecordJson rec = new RecordJson();
		
		rec.setName("hello world");
		rec.setAndroidAppname("org.davidliebman.android.awesomeguy");
		rec.setEmail("hello-user@gmail.com");
		
		Gson gson = new Gson();
		String cmd = gson.toJson(rec);
		Log.e("org.davidliebman", "command="+cmd);
//		TextView mtext = (TextView) findViewById(R.id.text_output);
//		mtext.setText(cmd);
        //String cmd = new String(rec.getName());
        String res = null;
        try {
                res = sendToJsonClient(cmd);
                //Console.pt(res.getResponse());
        } catch (Exception e) {
                e.printStackTrace();
                //throw new Exception("fail" ,e);
        }
        return res;
        
	}

	public String sendToJsonClient(String cmd) throws Exception{
		String responseString = new String();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("command", cmd));
		
		HttpClient httpclient = new DefaultHttpClient();
		//HttpPost httppost = new HttpPost("http://10.0.2.2:8888/game.html");
		HttpPost httppost = new HttpPost("http://10.0.2.2:8888/game.html");

		//httppost.setHeader("User-Agent", "");
		httppost.setHeader("Content-type", "application/json");
		StringEntity entity = new StringEntity(cmd, "UTF-8");
		
		
		httppost.setEntity(entity);
		
		
		//cmd = cmd.substring(1, cmd.length()- 1);
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
	    
//        JsonClient<String> client = new JsonClient<String>();
//        client.setUuid(UUID.randomUUID().toString());
//        //client.setSubmissionURL("http://yourapp.appspot.com/JSONServlet");
//        client.setSubmissionURL("http://localhost:8888/game/game.html");
//        client.setObjTosubmit(cmd);
//        client.setJsonRequestId("jsonCommandHandler");
//        client.setResponseClazz(AppEngineCmdResults.class);
//        
//        String response = client.call();
//        return response;
	    
	    return responseString;
	}

}
