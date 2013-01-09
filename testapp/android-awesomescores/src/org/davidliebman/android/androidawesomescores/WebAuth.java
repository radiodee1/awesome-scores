package org.davidliebman.android.androidawesomescores;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import com.google.android.gms.auth.GoogleAuthUtil;

//import com.google.api.client.extensions.android.http.AndroidHttp;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.jackson.JacksonFactory;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class WebAuth {
	
//	public static final String URL_INITIATE_OAUTH2 = new String ("https://accounts.google.com/o/oauth2/auth");
//	public static final String URL_INITIATE_API_TYPE = new String ("https://www.googleapis.com/oauth2/v1/userinfo");
//	//public static final String URL_INITIATE_SHA1 = new String ("F3:FE:60:09:29:2F:F6:43:CE:EE:22:38:70:35:8F:04:8A:1C:AF:BB");
//	public static final String URL_INITIATE_RESPONSE_TYPE = new String ("code");
//	public static final String URL_INITIATE_CLIENT_ID = new String ("459132469396.apps.googleusercontent.com");
//	public static final String URL_INITIATE_REDIRECT_URI = new String ("urn:ietf:wg:oauth:2.0:oob");
//	public static final String URL_INITIATE_SCOPE = new String ("https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile");
//	//public static final String URL_INITIATE_STATE = new String ("");
	
	public static final String AUTH_WEB_PREFIX = new String ("audience:server:client_id:");
	public static final String AUTH_MY_TOKEN = new String ("");
	public static final String AUTH_WEB_TOKEN = new String ("459132469396-99er3ba7o4ukn0ttdm5pil6au9h4fvid.apps.googleusercontent.com");
	
//	public static final String PARAM_RESPONSE_TYPE = new String ("response_type");
//	public static final String PARAM_REDIRECT_URI = new String ("redirect_uri");
//	public static final String PARAM_CLIENT_ID = new String ("client_id");
//	public static final String PARAM_CLIENT_SECRET = new String ("client_secret");
//	public static final String PARAM_SCOPE = new String ("scope");
//	public static final String PARAM_STATE = new String ("state");
//	public static final String PARAM_OAUTH = new String ("OAuth ");
	
//	private final static String G_PLUS_SCOPE = 
//		      "oauth2:https://www.googleapis.com/auth/plus.me";
//	private final static String USERINFO_SCOPE =   
//		      "https://www.googleapis.com/auth/userinfo.profile";
//	private final static String SCOPES = G_PLUS_SCOPE + " " + USERINFO_SCOPE;
	
//	public static final String TXT_QUESTIONMARK = new String("?");
//	public static final String TXT_EQUALS = new String("=");
//	public static final String TXT_SPACE = new String(" ");
//	public static final String TXT_AMPERSAND = new String("&");
	
	private Context mContext = null;
	private Account mAccount = null;
	private Activity mActivity = null;
	
	private String mClientId = new String("");
	private String mSecret = new String("");
	private String mAutherization = new String("");
	private String mApiKey = new String("");
	private String mOAuthToken = new String ("");
	private String mURL = new String("");
	
	private String mSendString = new String("");
	
	public WebAuth( Context c, Activity a) {
		mContext = c;
		mActivity = a;
		
	}
	
	public void setAccount(Account mAccount ) {
		this.mAccount = mAccount;
		
	
	}
	
	
	
	public void buildOAuthTokenString () {
		mSendString = WebAuth.AUTH_WEB_PREFIX + WebAuth.AUTH_WEB_TOKEN;
		
	}
	
	public String assignOAuthWithUtility () {
		
		new AsyncTask <String, Object, String> () {

			@Override
			protected void onPreExecute() {
				
			}
			
			@Override
			protected String doInBackground(String... params) {
				String token = null;
				try {
					Log.e("WebAuth ---","name: " + mAccount.name);
				    token = GoogleAuthUtil.getToken(mActivity, mAccount.name, mSendString);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				return token;
			}
			
			@Override
			protected void onPostExecute(String mResult) {
				Log.e("WebAuth ---", "token: " + mResult);
				mOAuthToken = mResult;
			}
			
		}.execute("");
		
		return mOAuthToken;
	}
	
	public String getTokenFromWeb() {
		String responseString = new String();

		try {
			HttpClient httpclient = new DefaultHttpClient();
		
			HttpGet httpget = new HttpGet();
			httpget.setURI(new URI(mURL));

		
		
		    HttpResponse response = httpclient.execute(httpget);//?command="+cmd));
		    StatusLine statusLine = response.getStatusLine();
		    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
		        ByteArrayOutputStream out = new ByteArrayOutputStream();
		        response.getEntity().writeTo(out);
		        out.close();
		        responseString = out.toString();
		        
		    } else{
		        //Closes the connection.
		        response.getEntity().getContent().close();
		        throw new IOException(statusLine.getReasonPhrase());
		    }		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	    
	    return responseString;
	}
	
	
	

	

	
	public void useAPI() {
		try {
			Log.e("WebAuth", "token "+ this.mOAuthToken);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getClientId() {
		return mClientId;
	}

	public void setClientId(String mClientId) {
		this.mClientId = mClientId;
	}

	public String getSecret() {
		return mSecret;
	}

	public void setSecret(String mSecret) {
		this.mSecret = mSecret;
	}

	public String getAutherization() {
		return mAutherization;
	}

	public void setAutherization(String mAutherization) {
		this.mAutherization = mAutherization;
	}

	public String getApiKey() {
		return mApiKey;
	}

	public void setApiKey(String mApiKey) {
		this.mApiKey = mApiKey;
	}

	public String getOAuthToken() {
		return mOAuthToken;
	}

	public void setOAuthToken(String mOAuthToken) {
		this.mOAuthToken = mOAuthToken;
		Log.e("WebAuth", "token " + this.mOAuthToken);
	}

	public Account getAccount() {
		return mAccount;
	}

	public String getURL() {
		return mURL;
	}

	public void setURL(String mURL) {
		this.mURL = mURL;
	}

	
	
}
