package org.davidliebman.android.androidawesomescores;

//import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;

import java.util.Date;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class WebAuthActivity extends Activity {

	private Context mContext;
	private WebView mWebview;
	private SharedPreferences mPrefs;
	public MyHandler mHandle;
	private RecordJson mRec;
	private WebScoreUpload web;
	private Scores mScores;
	
	private int mTask = WebAuth.TASK_USERNAME;
	private boolean mStopExecuting = true;
	private boolean mPrerequisites = true;
	private TextView mText = null;
	private String mOAuthToken = new String("");
	
	public static final int SDK_INT_PRE = 14;
	
	public static final int DIALOG_ACCOUNTS = 4;
	public static final int DIALOG_PREFERENCES = 1;
    public static final int DIALOG_WEB_SUCCESS = 2;
    public static final int DIALOG_WEB_FAILURE = 3;
	
	public WebAuth auth = null;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//mWebview = new WebView(this);
		//setContentView(mWebview);
		
		setContentView(R.layout.activity_web_auth);
		
		mContext = this;
		
		mText = (TextView) this.findViewById(R.id.text_output2);
		
		//mText.setText(new Integer(Build.VERSION.SDK_INT).toString());
		//mText.setText("mesage: " + mTask);
		
		mHandle = new MyHandler();		
		auth = new WebAuth(this, mHandle);
		
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
		
		Bundle extras = getIntent().getExtras();
		mTask = extras.getInt(WebAuth.EXTRA_NAME);
		Log.e("WebAuthActivity", "--- " + mTask);
		mRec = this.extractScoreFromIntent(extras);
		
		int mGoogleResults = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		Log.e("WebAuthActivity", "play: " + mGoogleResults);
		
		if (mGoogleResults == ConnectionResult.SUCCESS ) {
			mPrerequisites = true;
			mStopExecuting = false;
		}
		else {
			mStopExecuting = true;
			Dialog mDialog = GooglePlayServicesUtil.getErrorDialog(mGoogleResults, this, -1);
			mDialog.show();
			//setResult(RESULT_OK, new Intent());
			//finish();
		}
		
		if (mPrerequisites == true  && ! mStopExecuting) {
			switch (mTask ) {
			case WebAuth.TASK_NAME_AND_SCORE:
			case WebAuth.TASK_USERNAME:
				showDialog(DIALOG_ACCOUNTS);
				break;
			case WebAuth.TASK_SEND_SCORE:
				auth.getTokenWithAccount();
				//finish();
				break;
			}
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
		
		Dialog  dialog = new Dialog(WebAuthActivity.this);
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	//AlertDialog alertDialog;
    	AlertDialog alert;
    	LayoutInflater mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    	View mLayout;
    	String mAMessage = new String();
    	String mPositive = new String();
    	
    	
    	mLayout = mInflater.inflate(R.layout.congrats, 
    			(ViewGroup) findViewById(R.id.layout_root));
    	mLayout.findViewById(R.id.image).setBackgroundResource(R.drawable.guy_icon);
    	
	  switch (id) {
	  
	  case WebAuthActivity.DIALOG_WEB_FAILURE:
  		builder = new AlertDialog.Builder(WebAuthActivity.this);
  		builder.setView(mLayout);
  		
 	    	mAMessage = new String("Connection to the internet failed, or the server is down. Try again later.") ;
 	    	
 	    	((TextView)mLayout.findViewById(R.id.congrats_text)).setText(mAMessage);

 	    	mPositive = new String("OK"  );
 	    	//String mNegative = new String("Stay on this screen." );
 	    	builder.setCancelable(false)
 	    	       .setPositiveButton(mPositive, new DialogInterface.OnClickListener() {
 	    	    	   public void onClick(DialogInterface dialog, int id) {
 	    	    		   //mPreferences = getSharedPreferences(AWESOME_NAME, MODE_PRIVATE);
 	    	    		   removeDialog(WebAuthActivity.DIALOG_WEB_FAILURE);
 	    	    		   dialog.cancel();
 	    	        	   //removeDialog(Highscores.DIALOG_PREFERENCES);
 	    	           }
 	    	       });
 	    	alert = builder.create();
 	    	dialog = (Dialog) alert;
  		break;
  		
  	case WebAuthActivity.DIALOG_WEB_SUCCESS:
  		builder = new AlertDialog.Builder(WebAuthActivity.this);
  		builder.setView(mLayout);
  		
 	    	mAMessage = new String("Your score has been submitted to the online database. " +
 	    	"You will not be able to submit any score twice.") ;
 	    	
 	    	
 	    	((TextView)mLayout.findViewById(R.id.congrats_text)).setText(mAMessage);

 	    	mPositive = new String("OK"  );
 	    	//String mNegative = new String("Stay on this screen." );
 	    	builder.setCancelable(false)
 	    	       .setPositiveButton(mPositive, new DialogInterface.OnClickListener() {
 	    	    	   public void onClick(DialogInterface dialog, int id) {
 	    	    		   //mPreferences = getSharedPreferences(AWESOME_NAME, MODE_PRIVATE);
 	    	    		   removeDialog(WebAuthActivity.DIALOG_WEB_SUCCESS);
 	    	    		   dialog.cancel();
 	    	        	   //removeDialog(Highscores.DIALOG_PREFERENCES);
 	    	           }
 	    	       });
 	    	alert = builder.create();
 	    	dialog = (Dialog) alert;
  		break;
  		
	  
	    case DIALOG_ACCOUNTS:
	      builder = new AlertDialog.Builder(this);
	      builder.setTitle("Select a Google account");
	      
	      //GoogleAccountManager googleAccountManager = new GoogleAccountManager(mContext);
	      //final Account[] accounts = googleAccountManager.getAccounts();
	      AccountManager accountManager = AccountManager.get(WebAuthActivity.this);
	      final Account[] accounts = accountManager.getAccountsByType(null);//"com.gmail");

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
	        		auth.gotAccount(accounts[which]);
	        		
	        	}
	        	else {
	        		mStopExecuting = true;
	        	
	        	}
	        	if (mTask == WebAuth.TASK_USERNAME) {
	        		finish();
	        	}
	        	mHandle.sendEmptyMessage(WebAuth.TASK_SEND_SCORE);
	        	
	        }
	      });
	      break;
	      
	  }
	  return builder.create();
	}
	

	private RecordJson extractScoreFromIntent(Bundle mBundle) {
		RecordJson mRec = new RecordJson();
		mRec.setAndroidAppname(mBundle.getString(WebAuth.INTENT_APPNAME, ""));
		mRec.setCountry(mBundle.getString(WebAuth.INTENT_COUNTRY, ""));
		mRec.setEnableCollision(mBundle.getBoolean(WebAuth.INTENT_COLLISION, true));
		mRec.setCycles(1);
		mRec.setDate(new Date(mBundle.getLong(WebAuth.INTENT_DATE, System.currentTimeMillis())));
		mRec.setEmail(mBundle.getString(WebAuth.INTENT_EMAIL, ""));
		mRec.setEnableMonsters(mBundle.getBoolean(WebAuth.INTENT_MONSTERS, true));
		mRec.setGameSpeed(mBundle.getInt(WebAuth.INTENT_SPEED, 30));
		mRec.setKey(mBundle.getLong(WebAuth.INTENT_LOCAL_ID, 0));
		mRec.setLevel(mBundle.getInt(WebAuth.INTENT_LEVEL, 1));
		mRec.setLives(mBundle.getInt(WebAuth.INTENT_LIVES, 3));
		mRec.setName(mBundle.getString(WebAuth.INTENT_NAME, ""));
		mRec.setScore(mBundle.getInt(WebAuth.INTENT_SCORE, 10));
		mRec.setSound(mBundle.getBoolean(WebAuth.INTENT_SOUND, true));
		return mRec;
	}
	
	public void addScoreToOnlineList(RecordJson rec) {
    	
    	
    	new AsyncTask <RecordJson, Object, ReturnJson>() {

    		@Override
    		protected void onPreExecute() {

    			
    		}

    		@Override
    		protected ReturnJson doInBackground(RecordJson... params) {
    			ReturnJson returnRecord = null;
    			RecordJson sendRecord = params[0];
    			web.setUrl(WebScoreUpload.MY_URL + WebScoreUpload.MY_PATH_GAME);
    			returnRecord = web.sendRecord(params[0]);
    			if (returnRecord != null ) {
    				Scores.High mHigh = new Scores.High();
    				mHigh.setInternetKey(returnRecord.getKey());
    				mHigh.setKey(sendRecord.getRecordIdNum());
    				mScores.updateInternetKey(mHigh);
    			}
    			
    			return returnRecord;
    		}

    		@Override
    		protected void onPostExecute(ReturnJson result) {

    			if (result == null ) {
    				showDialog(WebAuthActivity.DIALOG_WEB_FAILURE);
    				return;
    			}
    			else {
    				//change scores sql
    				showDialog(WebAuthActivity.DIALOG_WEB_SUCCESS);
    		        Toast.makeText(WebAuthActivity.this, result.getMessage() + " - " + result.getKey(), Toast.LENGTH_LONG).show();
//    		        mNames = mScores.getGameHighList(0);
//    		        mAadapter = new HighAdapter(Highscores.this, R.layout.highscores, mNames);
//    		        mAadapter.setNotifyOnChange(true);
//    		    	setListAdapter(mAadapter);
    				return;
    			}
    			
    		}
    	}.execute(rec);
    	
    }
	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		Log.e("MainActivity", "here: " + requestCode + " " + resultCode);
//		//startActivity(data);
//		finish();
//	}

	public int getTask() {
		return mTask;
	}

	public void setTask(int mTask) {
		this.mTask = mTask;
	}
	
	public MyHandler getHandle() {
		return mHandle;
	}
	
	class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WebAuth.TASK_SEND_SCORE:
				auth.getTokenWithAccount();
				//finish();
				break;
			case WebAuth.TASK_FINISH:
				finish();
				break;
			}
		}
	}
}