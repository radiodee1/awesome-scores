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
	
	public static final String URL_INITIATE_OAUTH2 = new String ("https://accounts.google.com/o/oauth2/auth");
	public static final String URL_INITIATE_API_TYPE = new String ("https://www.googleapis.com/oauth2/v1/userinfo");
	//public static final String URL_INITIATE_SHA1 = new String ("F3:FE:60:09:29:2F:F6:43:CE:EE:22:38:70:35:8F:04:8A:1C:AF:BB");
	public static final String URL_INITIATE_RESPONSE_TYPE = new String ("code");
	public static final String URL_INITIATE_CLIENT_ID = new String ("459132469396.apps.googleusercontent.com");
	public static final String URL_INITIATE_REDIRECT_URI = new String ("urn:ietf:wg:oauth:2.0:oob");
	public static final String URL_INITIATE_SCOPE = new String ("https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile");
	//public static final String URL_INITIATE_STATE = new String ("");
	
	public static final String AUTH_WEB_PREFIX = new String ("audience:server:client_id:");
	public static final String AUTH_MY_TOKEN = new String ("");
	public static final String AUTH_WEB_TOKEN = new String ("459132469396-99er3ba7o4ukn0ttdm5pil6au9h4fvid.apps.googleusercontent.com");
	
	public static final String PARAM_RESPONSE_TYPE = new String ("response_type");
	public static final String PARAM_REDIRECT_URI = new String ("redirect_uri");
	public static final String PARAM_CLIENT_ID = new String ("client_id");
	public static final String PARAM_CLIENT_SECRET = new String ("client_secret");
	public static final String PARAM_SCOPE = new String ("scope");
	public static final String PARAM_STATE = new String ("state");
	public static final String PARAM_OAUTH = new String ("OAuth ");
	
//	private final static String G_PLUS_SCOPE = 
//		      "oauth2:https://www.googleapis.com/auth/plus.me";
//	private final static String USERINFO_SCOPE =   
//		      "https://www.googleapis.com/auth/userinfo.profile";
//	private final static String SCOPES = G_PLUS_SCOPE + " " + USERINFO_SCOPE;
	
	public static final String TXT_QUESTIONMARK = new String("?");
	public static final String TXT_EQUALS = new String("=");
	public static final String TXT_SPACE = new String(" ");
	public static final String TXT_AMPERSAND = new String("&");
	
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
	
	public void buildURL() {
		this.mURL = WebAuth.URL_INITIATE_OAUTH2 + WebAuth.TXT_QUESTIONMARK +
				WebAuth.PARAM_SCOPE + WebAuth.TXT_EQUALS + WebAuth.URL_INITIATE_SCOPE + WebAuth.TXT_AMPERSAND +
				WebAuth.PARAM_CLIENT_ID + WebAuth.TXT_EQUALS + WebAuth.URL_INITIATE_CLIENT_ID + WebAuth.TXT_AMPERSAND +
				WebAuth.PARAM_REDIRECT_URI + WebAuth.TXT_EQUALS + WebAuth.URL_INITIATE_REDIRECT_URI + WebAuth.TXT_AMPERSAND +
				WebAuth.PARAM_RESPONSE_TYPE + WebAuth.TXT_EQUALS + WebAuth.URL_INITIATE_RESPONSE_TYPE + WebAuth.TXT_AMPERSAND ;
	}
	
	public void buildAuthTokenString () {
		mSendString = WebAuth.AUTH_WEB_PREFIX + WebAuth.AUTH_WEB_TOKEN;
		
	}
	
	public String useAuthUtility () {
		final String token = null;
		new AsyncTask <Object, Object, Object> () {

			@Override
			protected Object doInBackground(Object... params) {
				try {
				    token = GoogleAuthUtil.getToken(mActivity, mAccount.name, mSendString);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
			
		};
		
		return null;
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
	
	public void startWebView() {
		Uri uri = Uri.parse(mURL);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		
		mContext.startActivity(intent);
	}
	
//	public void getTokenAM() {
//		if (!this.mOAuthToken.contentEquals("")) {
//			Log.e("WebAuth", "Token is "+ this.mOAuthToken);
//			this.useAPI();
//			
//		}
//		AccountManager manager  = AccountManager.get(mContext);
//		
//		String AUTH_TOKEN_TYPE = new String("View your tasks");
//		//Log.e("WebAuth", "AM");
//		manager.getAuthToken(this.mAccount, AUTH_TOKEN_TYPE, null, mActivity, new AccountManagerCallback<Bundle>() {
//		    
//			public void run(AccountManagerFuture<Bundle> future) {
//		      try {
//		    	  
//		    	Bundle bundle = future.getResult();
//		        // If the user has authorized your application to use the tasks API
//		        // a token is available.
//		        String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
//		        // Now you can use the Tasks API...
//		        //useTasksAPI(token);
//		        Log.e("WebAuth", "bundle");
//		        setOAuthToken(token);
//		        useAPI();
//		        Intent launch = (Intent) bundle.get(AccountManager.KEY_INTENT);
//		        if (launch != null) {
//		        	Log.e("WebAuth", "intent section");
//		            mActivity.startActivityForResult(launch, 0);
//		            return;
//		        }
//		      } catch (OperationCanceledException e) {
//		    	Log.e("WebAuth", "OperationCanceledException");
//		        // TODO: The user has denied you access to the API, you should handle that
//		      } catch (Exception e) {
//		        e.printStackTrace();
//		      }
//		    }
//		  }, null);
//	}
	

	
	public void useAPI() {
		try {
			Log.e("WebAuth", "token "+ this.mOAuthToken);
			//URL url = new URL("https://www.googleapis.com/tasks/v1/users/@me/lists?key=" + your_api_key);
//			URL url = new URL(WebAuth.URL_INITIATE_API_TYPE);
//			URLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.addRequestProperty("client_id", WebAuth.URL_INITIATE_CLIENT_ID);
//			conn.addRequestProperty("client_secret", WebAuth.URL_INITIATE_SHA1);
//			conn.setRequestProperty("Authorization", "OAuth " + this.getOAuthToken());
			
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
