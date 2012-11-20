package org.davidliebman.webapps.awesomescores.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;

public class TaskPickerComposite extends Composite implements  HasClickHandlers{

	public static final int CONSOLE_ADMIN = 1;
	public static final int CONSLOLE_USER = 2;
	public static final int GAME_AWESOMEGUY = 3;
	public static final int GAME_AWESOMEFLYER = 4;
	
	private Integer game, console;
	
	public TaskPickerComposite(Integer mGame, Integer mConsole) {
		
		game = mGame;
		console = mConsole;
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setHeight("66px");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		
		Button btnNewButton = new Button("New button");
		btnNewButton.setText("Admin Console");
		horizontalPanel.add(btnNewButton);
		
		Button btnNewButton_1 = new Button("New button");
		btnNewButton_1.setText("User Scores");
		horizontalPanel.add(btnNewButton_1);
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		verticalPanel.add(horizontalPanel_1);
		
		Button btnNewButton_2 = new Button("New button");
		btnNewButton_2.setText("Awesomeguy");
		horizontalPanel_1.add(btnNewButton_2);
		
		Button btnNewButton_3 = new Button("New button");
		btnNewButton_3.setText("AwesomeFlyer");
		horizontalPanel_1.add(btnNewButton_3);
		
		btnNewButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				console = TaskPickerComposite.CONSOLE_ADMIN;
			}
			
		});
		btnNewButton_1.addClickHandler(new ClickHandler () {

			@Override
			public void onClick(ClickEvent event) {
				console = TaskPickerComposite.CONSLOLE_USER;
			}
			
		});
		btnNewButton_2.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				game = TaskPickerComposite.GAME_AWESOMEGUY;
			}
			
		});
		btnNewButton_3.addClickHandler(new ClickHandler () {

			@Override
			public void onClick(ClickEvent event) {
				game = TaskPickerComposite.GAME_AWESOMEFLYER;
			}
			
		});
		MyHandler handler = new MyHandler();
		btnNewButton_3.addClickHandler(handler);
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



	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		// TODO Auto-generated method stub
		return addDomHandler(handler, ClickEvent.getType());
	}
	
	class MyHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
//			System.out.println(event.getSource().toString());
			
		}
		
	}


	

}
