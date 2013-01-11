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
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class WebAuth {
	
	public static final String EXTRA_NAME = "task";
	public static final int TASK_USERNAME  = 1;
	public static final int TASK_SEND_SCORE = 2;
	public static final int TASK_NAME_AND_SCORE = 3;
	
	public static final String AUTH_WEB_PREFIX = new String ("audience:server:client_id:");
	public static final String AUTH_MY_TOKEN = new String ("");
	public static final String AUTH_WEB_TOKEN = new String ("459132469396-99er3ba7o4ukn0ttdm5pil6au9h4fvid.apps.googleusercontent.com");
	
	public static final String PREFS_USRENAME = "user_name_chosen";
	public static final String PREFS_CONNECTION_WORKS = "connection_works";
	public static final String PREFS_PREFERENCES_NAME = "account_associated_prefs";
	
	private Context mContext = null;
	private Account mAccount = null;
	private Activity mActivity = null;
	private SharedPreferences mPrefs;

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
	

	
	public void gotAccount(Account mUseAccount ) {
		setAccount(mUseAccount);
		//Log.e("WebAuth", "account "+ mUseAccount.name);
		//mStopExecuting = false;
		mPrefs = mContext.getSharedPreferences(WebAuth.PREFS_PREFERENCES_NAME, 0);
		SharedPreferences.Editor ed = mPrefs.edit();
        ed.putString(WebAuth.PREFS_USRENAME, mUseAccount.name);
        ed.putBoolean(WebAuth.PREFS_CONNECTION_WORKS, false);
        ed.commit();
	}
	
	public String getTokenWithAccount () {
		buildOAuthTokenString();
		assignOAuthWithUtility();
		
		
		
		
		return mOAuthToken;
		
	}

	public void setConnectionSuccess() {
		mPrefs = mContext.getSharedPreferences(WebAuth.PREFS_PREFERENCES_NAME, 0);
		SharedPreferences.Editor ed = mPrefs.edit();
        ed.putBoolean(WebAuth.PREFS_CONNECTION_WORKS, true);
        ed.commit();
	}
	
	public void setConnectionFailed() {
		mPrefs = mContext.getSharedPreferences(WebAuth.PREFS_PREFERENCES_NAME, 0);
		SharedPreferences.Editor ed = mPrefs.edit();
        ed.putBoolean(WebAuth.PREFS_CONNECTION_WORKS, false);
        ed.commit();
	}
	
	public boolean isConnectionSuccess() {
		boolean mSet = false;
		mPrefs = mContext.getSharedPreferences(WebAuth.PREFS_PREFERENCES_NAME, 0);
		if (mPrefs.getBoolean(WebAuth.PREFS_CONNECTION_WORKS, false)) {
			mSet = true;
		}
		
		return mSet;
	}
	
	public boolean isAccountSet() {
		boolean mSet = false;
		mPrefs = mContext.getSharedPreferences(WebAuth.PREFS_PREFERENCES_NAME, 0);
		if ( ! mPrefs.getString(WebAuth.PREFS_USRENAME, "").contentEquals("")) {
			mSet = true;
		}
		return mSet;
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
