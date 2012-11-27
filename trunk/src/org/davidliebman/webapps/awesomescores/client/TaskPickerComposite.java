package org.davidliebman.webapps.awesomescores.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class TaskPickerComposite extends Composite implements  HasClickHandlers{

	public static final int CONSOLE_ADMIN_ADD = 1;
	public static final int CONSOLE_ADMIN_DELETE = 2;
	public static final int CONSOLE_USER = 3;
	public static final int GAME_AWESOMEGUY = 4;
	public static final int GAME_AWESOMEFLYER = 5;
	
	public static final String GAME_STRING_AWESOMEGUY = 
			new String ("org.davidliebman.android.awesomeguy");
	
	public static final String GAME_STRING_AWESOMEFLYER =
			new String ("org.davidliebman.android.flyer");
	
	public Button btnNewButton, btnNewButton_1, btnNewButton_2, btnNewButton_3;
	
	private Integer game, console;
	private Button btnAdminDelete;
	
	public TaskPickerComposite() {
		
		game = GAME_AWESOMEGUY;
		console = CONSOLE_USER;
		
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setHeight("66px");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		
		btnNewButton = new Button("New button");
		btnNewButton.setText("Admin Add");
		horizontalPanel.add(btnNewButton);
		
		btnAdminDelete = new Button("Admin Delete");
		horizontalPanel.add(btnAdminDelete);
		
		btnNewButton_1 = new Button("New button");
		btnNewButton_1.setText("User Scores");
		horizontalPanel.add(btnNewButton_1);
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel_1);
		
		btnNewButton_2 = new Button("New button");
		btnNewButton_2.setText("Awesomeguy");
		horizontalPanel_1.add(btnNewButton_2);
		
		btnNewButton_3 = new Button("New button");
		btnNewButton_3.setText("AwesomeFlyer");
		horizontalPanel_1.add(btnNewButton_3);
		
		MyHandler handler = new MyHandler();
		btnNewButton.addClickHandler(handler);
		btnNewButton_1.addClickHandler(handler);
		btnNewButton_2.addClickHandler(handler);
		btnNewButton_3.addClickHandler(handler);
		btnAdminDelete.addClickHandler(handler);
		
	}

	public Integer getGame() {
		return game;
	}



	public void setGame(Integer game) {
		this.game = game;
	}



	public Integer getConsole() {
		return console;
	}



	public void setConsole(Integer console) {
		this.console = console;
	}



	public Button getButtonUserScores() {
		return btnNewButton_1;
	}

	public Button getButtonDeleteRecord() {
		return this.btnAdminDelete;
	}
	

	public Button getBtnNewButton() {
		return btnNewButton;
	}

	
	public Button getBtnAwesomeguy() {
		return btnNewButton_2;
	}

	public Button getBtnAwesomeFlyer() {
		return btnNewButton_3;
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		// TODO Auto-generated method stub
		return addDomHandler(handler, ClickEvent.getType());
	}
	
	class MyHandler implements ClickHandler {
		
		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == btnNewButton) {
				console = TaskPickerComposite.CONSOLE_ADMIN_ADD;

			}
			if (event.getSource() == btnNewButton_1) {
				console = TaskPickerComposite.CONSOLE_USER;

			}
			if (event.getSource() == btnAdminDelete ) {
				console = TaskPickerComposite.CONSOLE_ADMIN_DELETE;
			}
			
			if (event.getSource() == btnNewButton_2) {
				game = TaskPickerComposite.GAME_AWESOMEGUY;

			}
			if (event.getSource() == btnNewButton_3) {
				game = TaskPickerComposite.GAME_AWESOMEFLYER;

			}
			//System.out.println(event.getSource().toString());
			
			
		}
		
	}


	

}
