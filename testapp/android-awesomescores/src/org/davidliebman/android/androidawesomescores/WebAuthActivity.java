package org.davidliebman.android.androidawesomescores;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class WebAuthActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_auth);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_web_auth, menu);
		return true;
	}

}
