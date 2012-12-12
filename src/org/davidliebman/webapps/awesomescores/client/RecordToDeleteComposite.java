package org.davidliebman.webapps.awesomescores.client;

import org.davidliebman.webapps.awesomescores.shared.Record;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;

public class RecordToDeleteComposite extends Composite implements HasClickHandlers {

	private Image image;
	private Label label, lblNum, lblName,lblScore, lblScoreNum, lblDate ;
	private DateLabel dateLabel;
	private Button btnSeeMore;
	private LayoutPanel layoutPanel;
	
	private Record record;
	
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	
	public RecordToDeleteComposite(Record r, int num) {
		
		record = r;
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setStyleName("recordDisplay");
		this.initWidget(horizontalPanel);
		horizontalPanel.setSize("315px", "88px");
		setHeight("62px");
		
		image = new Image("ic_guy_0.png");
		horizontalPanel.add(image);
		image.setHeight("58px");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		horizontalPanel.add(verticalPanel);
		verticalPanel.setWidth("146px");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		verticalPanel.add(horizontalPanel_1);
		
		label = new Label("# ");
		label.setStyleName("recordDisplay");
		horizontalPanel_1.add(label);
		
		lblNum = new Label("Num");
		lblNum.setStyleName("recordDisplay");
		horizontalPanel_1.add(lblNum);
		
		lblName = new Label("name");
		lblName.setStyleName("recordDisplay");
		horizontalPanel_1.add(lblName);
		
		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		verticalPanel.add(horizontalPanel_2);
		
		lblScore = new Label("Score: ");
		lblScore.setStyleName("recordDisplay");
		horizontalPanel_2.add(lblScore);
		
		lblScoreNum = new Label("0");
		lblScoreNum.setStyleName("recordDisplay");
		horizontalPanel_2.add(lblScoreNum);
		
		HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
		verticalPanel.add(horizontalPanel_3);
		
		lblDate = new Label("Date: ");
		lblDate.setStyleName("recordDisplay");
		horizontalPanel_3.add(lblDate);
		horizontalPanel_3.setCellVerticalAlignment(lblDate, HasVerticalAlignment.ALIGN_MIDDLE);
		
		dateLabel = new DateLabel();
		dateLabel.setStyleName("recordDisplay");
		dateLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		horizontalPanel_3.add(dateLabel);
		horizontalPanel_3.setCellVerticalAlignment(dateLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		
		layoutPanel = new LayoutPanel();
		//horizontalPanel_3.add(layoutPanel);
		horizontalPanel.add(layoutPanel);
		horizontalPanel.setCellWidth(layoutPanel, "80");
		layoutPanel.setSize("20", "20");
		
		btnSeeMore = new Button("Delete Me");
		btnSeeMore.setStyleName("recordButton-delete");
		//horizontalPanel_3.add(btnSeeMore);
		layoutPanel.add(btnSeeMore);
		btnSeeMore.setSize("40", "10");
		layoutPanel.setWidgetLeftWidth(btnSeeMore, 10.0, Unit.PX, 88.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(btnSeeMore, 14.0, Unit.PX, 34.0, Unit.PX);
		
		//btnSeeMore.setFocus(true);
		
		setRecord(r, num);
		
		btnSeeMore.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				deleteThisRecord();
				btnSeeMore.setText("working");
			}
			
		});
	}

	public void setRecord(Record mRec, int n) {
		
		lblNum.setText(new Integer(n + 1).toString() + ".  ");
		String newName = mRec.getName().substring(0, 15);
		lblName.setText(newName );
		dateLabel.setValue(mRec.getDate());
		lblScoreNum.setText(" " + mRec.getScore());
		
		switch (n % 9) {
		case 0:
			image.setUrl("/ic_guy_0.png");
			break;
		case 1:
			image.setUrl("/ic_guy_1.png");
			break;
		case 2:
			image.setUrl("/ic_guy_2.png");
			break;
		case 3:
			image.setUrl("/ic_guy_3.png");
			break;
		case 4:
			image.setUrl("/ic_guy_4.png");
			break;
		case 5:
			image.setUrl("/ic_guy_5.png");
			break;
		case 6:
			image.setUrl("/ic_guy_6.png");
			break;
		case 7:
			image.setUrl("/ic_guy_7.png");
			break;
		case 8:
			image.setUrl("/ic_guy_8.png");
			break;
		default:
			image.setUrl("/ic_guy_0.png");
			break;
		}
	}
	
	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		// TODO Auto-generated method stub
		return addDomHandler(handler, ClickEvent.getType());
	}
	
	public void deleteThisRecord() {
		this.greetingService.deleteServer(record, 
				new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				btnSeeMore.setText("fail");
			}

			@Override
			public void onSuccess(String result) {
				btnSeeMore.setText(result);
				if (result == null) {
					removeFromParent();
				}
				//btnSeeMore.setText("sent");
			}
			
		});
	}
}
