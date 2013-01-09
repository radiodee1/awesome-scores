package org.davidliebman.android.androidawesomescores;

//import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.os.Build;
import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;


public class WebAuthActivity extends Activity {

	private Context mContext;
	private WebView mWebview;
	
	
	private boolean mStopExecuting = true;
	private boolean mPrerequisites = true;
	
	public static final int SDK_INT_PRE = 14;
	
	public static final int DIALOG_ACCOUNTS = 1;
	
	public WebAuth auth = null;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//mWebview = new WebView(this);
		//setContentView(mWebview);
		
		setContentView(R.layout.activity_web_auth);
		
		mContext = this;
		
		final TextView mText = (TextView) this.findViewById(R.id.text_output2);
		
		
		
		mText.setText(new Integer(Build.VERSION.SDK_INT).toString());
		
		auth = new WebAuth(this, this);
		if (mPrerequisites == true ) {
			showDialog(DIALOG_ACCOUNTS);
		}
		
		Button mGoButton = (Button) findViewById(R.id.button_auth);
		mGoButton.setOnClickListener(new OnClickListener () {

			@Override
			public void onClick(View v) {
				
				if (! mStopExecuting) {
					if ( mPrerequisites == true) {
						//auth.buildURL();
						//String mReply = auth.getToken();
						//auth.startWebView();
						//Log.e("WebAuthActivity",mReply);
						Log.e("WebAuthActivity" , "gettoken-am");
					}
					else {
						Log.e("WebAuthActivity", "no-gettoken-am");
					}
					
					//auth.getTokenAM();
				}
				
			}
			
		});
		
	}

	@Override
	public void onStop() {
		super.onStop();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		finish();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		int mGoogleResults = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		
		if (mGoogleResults == ConnectionResult.SUCCESS) {
			mPrerequisites = true;
		}
		else {
			Dialog mDialog = GooglePlayServicesUtil.getErrorDialog(mGoogleResults, this, -1);
			mDialog.show();
			setResult(RESULT_OK, new Intent());
			finish();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_web_auth, menu);
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
	  switch (id) {
	    case DIALOG_ACCOUNTS:
	      AlertDialog.Builder builder = new AlertDialog.Builder(this);
	      builder.setTitle("Select a Google account");
	      
	      //GoogleAccountManager googleAccountManager = new GoogleAccountManager(mContext);
	      //final Account[] accounts = googleAccountManager.getAccounts();
	      AccountManager accountManager = AccountManager.get(WebAuthActivity.this);
	      final Account[] accounts = accountManager.getAccountsByType(null);//("com.google");

	      final int size = accounts.length;
	      String[] names = new String[size + 1];
	      for (int i = 0; i < size; i++) {
	        names[i] = accounts[i].name;
	      }
	      names[size] = "exit this menu";
	      builder.setItems(names, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	          // Stuff to do when the account is selected by the user
	        	
	        	if (which != size ) {
	        		mStopExecuting = false;
	        		gotAccount(accounts[which]);
	        	}
	        	else {
	        		mStopExecuting = true;
	        		finish();
	        		//exit without change
	        	}
	        }
	      });
	      return builder.create();
	  }
	  return null;
	}
	
	public void gotAccount(Account mUseAccount ) {
		auth.setAccount(mUseAccount);
		Log.e("WebAuthActivity", "account "+ mUseAccount.name);
		mStopExecuting = false;
		
		auth.buildOAuthTokenString();
		auth.assignOAuthWithUtility();
		//this.mWebview.getSettings().setJavaScriptEnabled(true);
		//auth.buildURL();
		//String mShow = auth.getTokenFromWeb() ;//+ new JSObject().toString();
		//mWebview.loadUrl(auth.getURL());
		//Log.e("WebAuthActivity --- ", mShow);
		//this.mWebview.loadData(mShow, "text/html", "UTF-8");
		//this.mWebview.getSettings().setJavaScriptEnabled(true);
		//this.mWebview.loadDataWithBaseURL(auth.getURL(), new JSObject().toString(), "text/javascript", "UTF-8", null);
		//this.mWebview.getSettings().setJavaScriptEnabled(true);
		//mWebview.setHttpAuthUsernamePassword(null, null, mUseAccount.name, null);
		//this.mWebview.addJavascriptInterface(new JSObject(), "awesomeUsername");
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("MainActivity", "here: " + requestCode + " " + resultCode);
		//startActivity(data);
	}
	
	class JSObject {
		public String toString() { return "<script type=\"text/javascript\" language=\"javascript\"> document.write(\"" + auth.getAccount().name +
				"\");</script>"; }
	}
}