package org.davidliebman.android.androidawesomescores;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.IOException;
import java.net.URI;
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
import org.apache.http.protocol.HTTP;

import com.google.gson.Gson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private String results = new String();
	//private WebScoreUpload web ;
	//private WebAuth auth = null;
	private Context mContext ;
	private String mCountry = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//web = new WebScoreUpload(this);
		//auth = new WebAuth(this);
		mContext = this;
		
		setContentView(R.layout.activity_main);
	

		Button mGoButton = (Button) findViewById(R.id.button1);
	    mGoButton.setOnClickListener(new OnClickListener() {
	    	@Override
	        public void onClick(View v) {
				final TextView mtext = (TextView) findViewById(R.id.text_output);
	        	
	    		new AsyncTask <Object, Object, Object>() {

	    			@Override
	    		    protected void onPreExecute() {
	    		        
	    		        //super.onPostExecute(result);
	    		        Toast.makeText(MainActivity.this, "task started.", Toast.LENGTH_LONG).show();
	    		    }
	    			
					@Override
					protected Object doInBackground(Object... params) {
						TextView mtext = (TextView) findViewById(R.id.text_output);
						RecordJson rec = new RecordJson();
						
						rec.setName("hello world3");
						rec.setAndroidAppname("org.davidliebman.android.awesomeguy");
						rec.setEmail("hello-user3@gmail.com");
			        	//mtext.setText(someExecuteMethod());
						//web.setUrl(WebScoreUpload.MY_URL + WebScoreUpload.MY_PATH_GAME);
						return null;//web.prepareAndSendRecord(rec);
					}
					
					 @Override
				        protected void onPostExecute(Object result) {
				            results =(String) result;
				            mtext.setText(results);
				            Toast.makeText(MainActivity.this, (String) result, Toast.LENGTH_LONG).show();
				        }
	    		}.execute(new Object());
	    		//mtext.setText("waiting...2");
	        	
	    		
	    		
	        	Toast.makeText(MainActivity.this, "And We're Off", Toast.LENGTH_SHORT).show();
	        }
	    });
	    
	    	Button mGoButton2 = (Button) findViewById(R.id.button2);
		    mGoButton2.setOnClickListener(new OnClickListener() {
		    	@Override
		        public void onClick(View v) {
					final TextView mtext = (TextView) findViewById(R.id.text_output);
		        	
		    		new AsyncTask <Object, Object, Object>() {

		    			@Override
		    		    protected void onPreExecute() {
		    		        
		    		        //super.onPostExecute(result);
		    		        Toast.makeText(MainActivity.this, "task started.", Toast.LENGTH_LONG).show();
		    		    }
		    			
						@Override
						protected Object doInBackground(Object... params) {
							TextView mtext = (TextView) findViewById(R.id.text_output);
							RecordJson rec = new RecordJson();
							
							rec.setName("hello world4");
							rec.setAndroidAppname("org.davidliebman.android.awesomeguy");
							rec.setEmail("hello-user4@gmail.com");
				        	//mtext.setText(someExecuteMethod());
							//web.setUrl("http://10.0.2.2:8888/game.html");
							//web.setUrl(WebScoreUpload.MY_URL + WebScoreUpload.MY_PATH_GAME);
							return null;//web.prepareAndSendRecord(rec);
						}
						
						 @Override
					        protected void onPostExecute(Object result) {
					            results =(String) result;
					            mtext.setText(results);
					            Toast.makeText(MainActivity.this, (String) result, Toast.LENGTH_LONG).show();
					        }
		    		}.execute(new Object());
		    	}
	    });
		   
		    
		Button mGoButton3 = (Button) findViewById(R.id.button3);
		mGoButton3.setOnClickListener( new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, WebAuthActivity.class);
				intent.putExtra(WebAuth.EXTRA_NAME, WebAuth.TASK_USERNAME);
				intent = addRecordToIntent(intent, new Scores.High());
				startActivity(intent);
				//Bundle bundle = new Bundle();
				//startActivityForResult(intent, -1, bundle);
			}
			
		});
    
	}
	
	public Intent addRecordToIntent(Intent mIntent, Scores.High mIn ) {
		mIntent.putExtra(WebAuth.INTENT_DATE, mIn.getDate());
		mIntent.putExtra(WebAuth.INTENT_COUNTRY, mCountry);
		mIntent.putExtra(WebAuth.INTENT_COLLISION, mIn.isMonsterCollision());
		mIntent.putExtra(WebAuth.INTENT_EMAIL, "");
		mIntent.putExtra(WebAuth.INTENT_LEVEL, mIn.getLevel());
		mIntent.putExtra(WebAuth.INTENT_LIVES, mIn.getLives());
		mIntent.putExtra(WebAuth.INTENT_LOCAL_ID, mIn.getKey());
		mIntent.putExtra(WebAuth.INTENT_MONSTERS, mIn.isEnableMonsters());
		mIntent.putExtra(WebAuth.INTENT_NAME, mIn.getName());
		mIntent.putExtra(WebAuth.INTENT_SCORE, mIn.getScoreKey());
		mIntent.putExtra(WebAuth.INTENT_SOUND, mIn.isSoundOn());
		mIntent.putExtra(WebAuth.INTENT_SPEED, mIn.getGameSpeed());
		mIntent.putExtra(WebAuth.INTENT_APPNAME, "");
		return mIntent;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

//	

}
