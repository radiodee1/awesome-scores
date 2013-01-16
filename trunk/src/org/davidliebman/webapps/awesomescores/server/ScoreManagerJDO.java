package org.davidliebman.webapps.awesomescores.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.davidliebman.webapps.awesomescores.shared.Record;



public class ScoreManagerJDO {

	PersistenceManager pm ;
	
	public static final int STORE_LIMIT = 100;
	
	public ScoreManagerJDO() {
		pm = PMF.get().getPersistenceManager();
	}
	
	public Long saveRecord( Record r ) {
		return saveRecord(new RecordSaver(r, null));
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
		pm = PMF.get().getPersistenceManager();
		try {
			pm.deletePersistent(pm.getObjectById(mRec.getClass(), mRec.getKey()));
			

		} finally {
			pm.close();
		}
		
		//pm.deletePersistent(mRec);
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
				for (int x = 0; x < results.size(); x ++ ) {
					if (x < STORE_LIMIT) {
						mRecList.add(results.get(x).getRecord());
					}
					else {
						// remove record
						pm.deletePersistent(results.get(x));
					}
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
