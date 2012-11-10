package org.davidliebman.webapps.awesomescores.server;

import javax.jdo.annotations.*;
import com.google.appengine.api.datastore.Key;

//import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable
public  class RecordSaver  {
	
	public final static int SPEED_SLOW = 	16;
	public final static int SPEED_MEDIUM = 	20;
	public final static int SPEED_FAST = 	24;
	public final static int SPEED_FASTER = 	32;
	public final static int SPEED_SYSTEM = 	40;
	
	public static int RADIO_PLAYERS_TEN = 10;
	public static int RADIO_PLAYERS_FIVE = 5;
	public static int RADIO_PLAYERS_FIFTY = 50;
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String mAndroidAppname;
	
	@Persistent
	private boolean mNewRecord;
	@Persistent
	private int mRecordIdNum;
	@Persistent
	private int mLevel;
	@Persistent
	private int mScore;
	@Persistent
	private int mLives;
	@Persistent
	private int mCycles;//not used much
	@Persistent
	private int mSave1;//not used much
	@Persistent
	private String mName = new String();
	
	@Persistent
	private int mGameSpeed;
	@Persistent
	private int mNumRecords;
	@Persistent
	private boolean mSound;
	@Persistent
	private boolean mEnableJNI;
	@Persistent
	private boolean mEnableMonsters;
	@Persistent
	private boolean mEnableCollision;
	
	
	public RecordSaver() {
		
		mName = new String("anonymous");
		
		mNewRecord = false;
		mRecordIdNum = 0;
		
		mLevel = 1;
		mScore = 10;
		mLives = 3;
		mCycles = 0;
		mSave1 = 0;
		mGameSpeed = RecordSaver.SPEED_SYSTEM;
		mNumRecords = RADIO_PLAYERS_FIFTY;
		mSound = true;
		mEnableJNI = true;
		mEnableMonsters = true;
		mEnableCollision = true;
	}
	
/*
     public void addToPreferences( SharedPreferences preferences) {
    	 SharedPreferences.Editor out = preferences.edit();
    	 out.putString("mNewRecord",new Boolean(mNewRecord).toString());
    	 out.putInt("mRecordIdNum", mRecordIdNum);
    	 out.putString("mName",mName);
         out.putInt("mLevel",mLevel);
         out.putInt("mScore",mScore);
         out.putInt("mLives",mLives);
         out.putInt("mCycles",mCycles);
         out.putInt("mSave1",mSave1);
         out.putInt("mGameSpeed",mGameSpeed);
         out.putInt("mNumRecords",mNumRecords);
         out.putString("mSound",new Boolean(mSound).toString());
         out.putString("mEnableJNI",new Boolean(mEnableJNI).toString());
         out.putString("mEnableMonsters",new Boolean(mEnableMonsters).toString());
         out.putString("mEnableCollision",new Boolean(mEnableCollision).toString());
         out.commit();
         
     }
     
     public void getFromPreferences(SharedPreferences in) {
    	 mNewRecord = new Boolean(in.getString("mNewRecord","false")).booleanValue();
    	 mRecordIdNum = in.getInt("mRecordIdNum", 0);
    	 mName = in.getString("mName","anonymous");
         mLevel = in.getInt("mLevel",1);
         mScore = in.getInt("mScore",10);
         mLives = in.getInt("mLives",3);
         mCycles = in.getInt("mCycles",0);
         mSave1 = in.getInt("mSave1",0);
         mGameSpeed = in.getInt("mGameSpeed",Record.SPEED_SYSTEM);
         mNumRecords= in.getInt("mNumRecords",RADIO_PLAYERS_FIFTY);
         mSound = new Boolean(in.getString("mSound","")).booleanValue();
         mEnableJNI = new Boolean(in.getString("mEnableJNI","")).booleanValue();
         mEnableMonsters = new Boolean(in.getString("mEnableMonsters","")).booleanValue();
         mEnableCollision = new Boolean(in.getString("mEnableCollision","")).booleanValue();
     }
     
*/
     /*
     public void listInLog() {
		Log.i("Record", "Is New Record : " + new Boolean(mNewRecord).toString());
		Log.i("Record", "Record Database Num : "+ mRecordIdNum);
		Log.i("Record", "Player Name : " + mName);
		Log.i("Record", "Player Level : "+ mLevel);
		Log.i("Record", "Player Score : " + mScore);
		Log.i("Record", "Player Lives : " + mLives);
		Log.i("Record", "Player Cycles : " + mCycles);
		Log.i("Record", "Player Save1 : " + mSave1);
		Log.i("Record", "Game Speed : " + mGameSpeed);
		Log.i("Record", "High Score Number : " + mNumRecords);
		Log.i("Record", "Sound Enabled : " + new Boolean(mSound).toString());
		Log.i("Record", "JNI Enabled : " + new Boolean(mEnableJNI).toString());
		Log.i("Record", "Monsters Enabled : " + new Boolean(mEnableMonsters).toString());
		Log.i("Record", "Collision Enabled : " + new Boolean(mEnableCollision).toString());
	}
     */

    
	
	
    
	public boolean isNewRecord() {
		return mNewRecord;
	}
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getAndroidAppname() {
		return mAndroidAppname;
	}

	public void setAndroidAppname(String mAndroidAppname) {
		this.mAndroidAppname = mAndroidAppname;
	}

	public void setNewRecord(boolean mNewRecord) {
		this.mNewRecord = mNewRecord;
	}
	public int getLevel() {
		return mLevel;
	}
	public void setLevel(int mLevel) {
		this.mLevel = mLevel;
	}
	public int getScore() {
		return mScore;
	}
	public void setScore(int mScore) {
		this.mScore = mScore;
	}
	
	public int incrementScore(int amount) {
		this.mScore = this.mScore + amount;
		return this.mScore;
	}
	public int getLives() {
		return mLives;
	}
	public void setLives(int mLives) {
		this.mLives = mLives;
	}
	public void incrementLives() {
		this.mLives ++;
	}

	public void decrementLives() {
		this.mLives --;
	}
	public int getCycles() {
		// not used much
		return mCycles;
	}
	public void setCycles(int mCycles) {
		// not used much
		this.mCycles = mCycles;
	}
	public int getSave1() {
		// not used much
		return mSave1;
	}
	public void setSave1(int mSave1) {
		// not used much
		this.mSave1 = mSave1;
	}
	public String getName() {
		return mName;
	}
	public void setName(String mName) {
		this.mName = mName;
	}
	public int getGameSpeed() {
		return mGameSpeed;
	}
	public void setGameSpeed(int mGameSpeed) {
		this.mGameSpeed = mGameSpeed;
	}
	public int getNumRecords() {
		return mNumRecords;
	}
	public void setNumRecords(int mNumRecords) {
		this.mNumRecords = mNumRecords;
	}
	public boolean isSound() {
		return mSound;
	}
	public void setSound(boolean mSound) {
		this.mSound = mSound;
	}
	public boolean isEnableJNI() {
		//return mEnableJNI;
		return true;
	}
	public void setEnableJNI(boolean mEnableJNI) {
		//this.mEnableJNI = mEnableJNI;
		this.mEnableJNI = true;
	}
	public boolean isEnableMonsters() {
		return mEnableMonsters;
	}
	public void setEnableMonsters(boolean mEnableMonsters) {
		this.mEnableMonsters = mEnableMonsters;
	}
	public boolean isEnableCollision() {
		return mEnableCollision;
	}
	public void setEnableCollision(boolean mEnableCollision) {
		this.mEnableCollision = mEnableCollision;
	}
	
	public int getRecordIdNum() {
		return mRecordIdNum;
	}
	public void setRecordIdNum(int mRecordIdNum) {
		this.mRecordIdNum = mRecordIdNum;
	}
	


	


	
	
}