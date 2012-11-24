package org.davidliebman.webapps.awesomescores.client;

import org.davidliebman.webapps.awesomescores.shared.Record;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class DisplayRecordComposite extends Composite {

	private Image image;
	private Label label, lblNum, lblName,lblScore, lblScoreNum, lblDate ;
	private DateLabel dateLabel;
	private Button btnSeeMore;
	
	
	public DisplayRecordComposite(Record r, int num) {
		
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		this.initWidget(horizontalPanel);
		setHeight("62px");
		
		image = new Image("ic_guy_0.png");
		horizontalPanel.add(image);
		image.setHeight("58px");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		horizontalPanel.add(verticalPanel);
		verticalPanel.setWidth("208px");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		verticalPanel.add(horizontalPanel_1);
		
		label = new Label("# ");
		horizontalPanel_1.add(label);
		
		lblNum = new Label("Num");
		horizontalPanel_1.add(lblNum);
		
		lblName = new Label("name");
		horizontalPanel_1.add(lblName);
		
		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		verticalPanel.add(horizontalPanel_2);
		
		lblScore = new Label("Score: ");
		horizontalPanel_2.add(lblScore);
		
		lblScoreNum = new Label("0");
		horizontalPanel_2.add(lblScoreNum);
		
		HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
		verticalPanel.add(horizontalPanel_3);
		
		lblDate = new Label("Date: ");
		horizontalPanel_3.add(lblDate);
		horizontalPanel_3.setCellVerticalAlignment(lblDate, HasVerticalAlignment.ALIGN_MIDDLE);
		
		dateLabel = new DateLabel();
		dateLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		horizontalPanel_3.add(dateLabel);
		horizontalPanel_3.setCellVerticalAlignment(dateLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		
		btnSeeMore = new Button("See More");
		horizontalPanel_3.add(btnSeeMore);
		
		setRecord(r, num);
	}

	public void setRecord(Record mRec, int n) {
		
		lblNum.setText(new Integer(n + 1).toString());
		
		lblName.setText(mRec.getName());
		lblScore.setText(new Integer(mRec.getScore()).toString());
		dateLabel.setValue(mRec.getDate());
	}
	
}
