package org.davidliebman.android.androidawesomescores;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class WebAuth {
	private Context mContext = null;
	private Account mAccount = null;
	private Activity mActivity = null;
	
	public WebAuth( Context c, Activity a) {
		mContext = c;
		mActivity = a;
		
	}
	
	public void setAccount(Account mAccount ) {
		this.mAccount = mAccount;
	
		
		
		Object mSpecial;
		AccountManager manager  = AccountManager.get(mContext);
		
		String AUTH_TOKEN_TYPE = new String("Manage your tasks");
		
		manager.getAuthToken(this.mAccount, AUTH_TOKEN_TYPE, null, mActivity, new AccountManagerCallback<Bundle>() {
		    
			public void run(AccountManagerFuture<Bundle> future) {
		      try {
		        // If the user has authorized your application to use the tasks API
		        // a token is available.
		        String token = future.getResult().getString(AccountManager.KEY_AUTHTOKEN);
		        // Now you can use the Tasks API...
		        //useTasksAPI(token);
		      } catch (OperationCanceledException e) {
		        // TODO: The user has denied you access to the API, you should handle that
		      } catch (Exception e) {
		        //handleException(e);
		      }
		    }
		  }, null);
	
	}
	
	
	
}
