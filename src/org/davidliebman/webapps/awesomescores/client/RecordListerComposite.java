package org.davidliebman.webapps.awesomescores.client;

import java.util.ArrayList;

import org.davidliebman.webapps.awesomescores.shared.Record;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;



public class RecordListerComposite extends Composite implements HasClickHandlers{
	ArrayList<Record> mList = new ArrayList<Record>();
	VerticalPanel mPanel = new VerticalPanel();
	//ListBox scrollPanel = new ListBox();
	
	public RecordListerComposite() {
		
		//ScrollPanel scrollPanel = new ScrollPanel();
		mPanel.setStyleName("recordListing");
		//scrollPanel.setStyleName("recordDisplay");
		mPanel.setStyleName("recordDisplay");
		mPanel.setBorderWidth(1);
		//scrollPanel.setAlwaysShowScrollBars(true);
		this.initWidget(mPanel);
		mPanel.setSize("315px", "0");
		//this.initWidget(scrollPanel);
		//scrollPanel.setSize("333px", "550px");
		//((ForIsWidget) scrollPanel).add(mPanel);
		//fill(new ArrayList<Record> ());
		
		setStyleName("recordListing");
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



	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		// TODO Auto-generated method stub
		return addDomHandler(handler, ClickEvent.getType());
	}
}
