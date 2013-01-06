package org.davidliebman.android.androidawesomescores;

//import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;

import android.os.Build;
import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class WebAuthActivity extends Activity {

	private Context mContext;
	
	private boolean mStopExecuting = true;
	private boolean mPrerequisites = true;
	
	public static final int SDK_INT_PRE = 14;
	
	public static final int DIALOG_ACCOUNTS = 1;
	
	private WebAuth auth = null;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_auth);
		
		mContext = this;
		
		final TextView mText = (TextView) this.findViewById(R.id.text_output2);
		
		if (Build.VERSION.SDK_INT >=  SDK_INT_PRE) {
			mPrerequisites = true;
		}
		else {
			mPrerequisites = false;
		}
		
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
						//auth.getTokenAM();
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
		
	}
}
