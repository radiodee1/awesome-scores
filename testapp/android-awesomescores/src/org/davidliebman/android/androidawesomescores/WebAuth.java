package org.davidliebman.android.androidawesomescores;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class WebAuth {
	
	public static final String URL_INITIATE_OAUTH2 = new String ("https://accounts.google.com/o/oauth2/auth");
	public static final String URL_INITIATE_RESPONSE_TYPE = new String ("code");
	public static final String URL_INITIATE_CLIENT_ID = new String ("459132469396.apps.googleusercontent.com");
	public static final String URL_INITIATE_REDIRECT_URI = new String ("urn:ietf:wg:oauth:2.0:oob");
	public static final String URL_INITIATE_SCOPE = new String ("auth userinfo.email");
	public static final String URL_INITIATE_STATE = new String ("");
	
	public static final String PARAM_RESPONSE_TYPE = new String ("response_type");
	public static final String PARAM_REDIRECT_URI = new String ("redirect_uri");
	public static final String PARAM_CLIENT_ID = new String ("client_id");
	public static final String PARAM_CLIENT_SECRET = new String ("client_secret");
	public static final String PARAM_SCOPE = new String ("scope");
	public static final String PARAM_STATE = new String ("state");
	public static final String PARAM_OAUTH = new String ("OAuth ");
	
	private Context mContext = null;
	private Account mAccount = null;
	private Activity mActivity = null;
	
	private String mClientId = new String("");
	private String mSecret = new String("");
	private String mAutherization = new String("");
	private String mApiKey = new String("");
	private String mOAuthToken = new String ("");
	
	public WebAuth( Context c, Activity a) {
		mContext = c;
		mActivity = a;
		
	}
	
	public void setAccount(Account mAccount ) {
		this.mAccount = mAccount;
	
	
	}
	
	public void getTokenAM() {
		Object mSpecial;
		AccountManager manager  = AccountManager.get(mContext);
		
		String AUTH_TOKEN_TYPE = new String("Manage your tasks");
		
		manager.getAuthToken(this.mAccount, AUTH_TOKEN_TYPE, null, mActivity, new AccountManagerCallback<Bundle>() {
		    
			public void run(AccountManagerFuture<Bundle> future) {
		      try {
		    	  
		    	Bundle bundle = future.getResult();
		        // If the user has authorized your application to use the tasks API
		        // a token is available.
		        String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
		        // Now you can use the Tasks API...
		        //useTasksAPI(token);
		        
		        setOAuthToken(token);
		        Intent launch = (Intent) bundle.get(AccountManager.KEY_INTENT);
		        if (launch != null) {
		            mActivity.startActivityForResult(launch, 0);
		            return;
		        }
		      } catch (OperationCanceledException e) {
		        // TODO: The user has denied you access to the API, you should handle that
		      } catch (Exception e) {
		        //handleException(e);
		      }
		    }
		  }, null);
	}
	
	public void useAPI() {
//		URL url = new URL("https://www.googleapis.com/tasks/v1/users/@me/lists?key=" + your_api_key);
//		URLConnection conn = (HttpURLConnection) url.openConnection();
//		conn.addRequestProperty("client_id", this.URL_INITIATE_CLIENT_ID);
//		conn.addRequestProperty("client_secret", );
//		conn.setRequestProperty("Authorization", "OAuth " + this.getOAuthToken());
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
	}

	public Account getAccount() {
		return mAccount;
	}

	
	
}
