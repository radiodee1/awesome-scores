package org.davidliebman.android.androidawesomescores;

import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;

import android.os.Bundle;
import android.accounts.Account;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.widget.TextView;

public class WebAuthActivity extends Activity {

	public static final int DIALOG_ACCOUNTS = 1;
	
	private WebAuth auth = null;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_auth);
		
		final TextView mText = (TextView) this.findViewById(R.id.text_output2);
		
		auth = new WebAuth(this, this);
		showDialog(DIALOG_ACCOUNTS);
		
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
	      
	      GoogleAccountManager googleAccountManager = new GoogleAccountManager(WebAuthActivity.this);
	      final Account[] accounts = googleAccountManager.getAccounts();
	      
	      //final Account[] accounts = accountManager.getAccountsByType("com.google");
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
	        		gotAccount(accounts[which]);
	        	}
	        	else {
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
	}
}
