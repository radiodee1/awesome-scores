package org.davidliebman.webapps.awesomescores.client;

import java.util.ArrayList;

import org.davidliebman.webapps.awesomescores.shared.Record;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;



public class RecordListerComposite extends VerticalPanel {
	ArrayList<Record> mList = new ArrayList<Record>();
	VerticalPanel mPanel = new VerticalPanel();
	
	public RecordListerComposite() {
		//this.initWidget(mPanel);
		
		//fill(new ArrayList<Record> ());
		
	}
	
	
	
	public void fill(ArrayList <Record> mL) {
		mList = mL;
		for(int x = 0; x < mList.size(); x ++ ) {
			this.add(new DisplayRecordComposite(mList.get(x), x));
		}
	}
}
