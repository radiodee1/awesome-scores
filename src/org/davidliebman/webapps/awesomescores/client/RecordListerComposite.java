package org.davidliebman.webapps.awesomescores.client;

import java.util.ArrayList;

import org.davidliebman.webapps.awesomescores.shared.Record;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;



public class RecordListerComposite extends Composite {
	ArrayList<Record> mList = new ArrayList<Record>();
	VerticalPanel mPanel = new VerticalPanel();
	//ListBox scrollPanel = new ListBox();
	
	public RecordListerComposite() {
		
		ScrollPanel scrollPanel = new ScrollPanel();
		mPanel.setStyleName("verticalScroll");
		//scrollPanel.setStyleName("recordDisplay");
		mPanel.setStyleName("recordDisplay");
		mPanel.setBorderWidth(1);
		//scrollPanel.setAlwaysShowScrollBars(true);
		this.initWidget(mPanel);
		mPanel.setSize("333px", "0");
		//this.initWidget(scrollPanel);
		scrollPanel.setSize("363px", "550px");
		//((ForIsWidget) scrollPanel).add(mPanel);
		//fill(new ArrayList<Record> ());
		
	}
	
	
	
	public void fill(ArrayList <Record> mL) {
		mList = mL;
		while (mPanel.getWidgetCount() > 0) {
			mPanel.remove(0);
		}
		//mPanel = new VerticalPanel();
		
		for(int x = 0; x < mList.size(); x ++ ) {
			mPanel.add(new DisplayRecordComposite(mList.get(x), x));
		}
	}
}
