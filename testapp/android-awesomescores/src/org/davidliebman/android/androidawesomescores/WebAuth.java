package org.davidliebman.android.androidawesomescores;

import android.accounts.Account;
import android.content.Context;

public class WebAuth {
	private Context mContext = null;
	private Account mAccount = null;
	
	
	public WebAuth( Context c ) {
		mContext = c;
		
	}
	
	public void setAccount(Account mAccount ) {
		this.mAccount = mAccount;
	}
	
}
