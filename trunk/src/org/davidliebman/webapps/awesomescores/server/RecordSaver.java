package org.davidliebman.webapps.awesomescores.server;

import javax.jdo.annotations.*;

import org.davidliebman.webapps.awesomescores.shared.Record;

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
	@Persistent
	private String mCountry;
	
	public RecordSaver() {
		
		mName = new String("anonymous");
		
		mNewRecord = false;
		mRecordIdNum = 0;
		
		mLevel = 0;
		mScore = 0;
		mLives = 0;
		mCycles = 0;
		mSave1 = 0;
		mGameSpeed = 0;
		mNumRecords = 0;
		mSound = true;
		mEnableJNI = true;
		mEnableMonsters = true;
		mEnableCollision = true;
		mCountry = new String("");
	}
	

public RecordSaver(Record mIn) {
		
		mName = mIn.getName();
		
		mNewRecord = mIn.getNewRecord();
		mRecordIdNum = mIn.getRecordIdNum();
		
		mLevel = mIn.getLevel();
		mScore = mIn.getScore();
		mLives = mIn.getLives();
		mCycles = mIn.getCycles();
		mSave1 = mIn.getSave1();
		mGameSpeed = mIn.getGameSpeed();
		mNumRecords = mIn.getNumRecords();
		mSound = mIn.isSound();
		mEnableJNI = mIn.isEnableJNI();
		mEnableMonsters = mIn.isEnableMonsters();
		mEnableCollision = mIn.isEnableCollision();
		mCountry = mIn.getCountry();
	}
	
	
	
    
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

	public String getCountry() {
		return mCountry;
	}

	public void setCountry(String mCountry) {
		this.mCountry = mCountry;
	}
	


	


	
	
}