package org.davidliebman.webapps.awesomescores.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.davidliebman.webapps.awesomescores.shared.Record;



public class ScoreManagerJDO {

	PersistenceManager pm ;
	
	public ScoreManagerJDO() {
		pm = PMF.get().getPersistenceManager();
	}
	
	public Long saveRecord( RecordSaver mRec ) {
		pm = PMF.get().getPersistenceManager();
		
		try {
            pm.makePersistent(mRec);
        } finally {
            pm.close();
        }
		return mRec.getKey();
	}
	
	public void deleteRecord( RecordSaver mRec ) {
		pm.deletePersistent(mRec);
	}
	
	public ArrayList<Record> getList( String mGame ) {
		ArrayList<Record> mRecList = new ArrayList<Record> ();
		Query q = pm.newQuery(RecordSaver.class);
		q.setFilter("mAndroidAppname == gameNameParam");
		q.setOrdering("mScore desc");
		q.declareParameters("String gameNameParam");

		try {
			  List<RecordSaver> results = (List<RecordSaver>) q.execute(mGame);
			  if (!results.isEmpty()) {
			    for (RecordSaver p : results) {
			      // Process result p
			    	mRecList.add(p.getRecord());
			    }
			  } else {
			    // Handle "no results" case
			  }
			} finally {
			  q.closeAll();
			}
		return mRecList;
	}
}
