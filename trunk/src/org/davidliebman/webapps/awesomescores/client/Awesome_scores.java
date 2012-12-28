package org.davidliebman.webapps.awesomescores.client;

import org.davidliebman.webapps.awesomescores.shared.FieldVerifier;
import org.davidliebman.webapps.awesomescores.shared.Record;
import org.davidliebman.webapps.awesomescores.shared.SiteEnum;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.*;

//import javax.naming.InitialContext;
//import javax.persistence.EntityManager;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Awesome_scores  implements EntryPoint {
	private String testHtml = new String();
	private String configString = new String("user");
	private Label gameTarget = new Label();
	private String gameTargetString = new String("org.davidliebman.android.awesomeguy");
	private final AdminConsoleComposite adminConsoleAdd = new AdminConsoleComposite();
	private final RecordDeleteComposite adminConsoleDelete = new RecordDeleteComposite();
	private RecordListerComposite resultsVPanel = new RecordListerComposite();
	private ArrayList<Record> mList = new ArrayList<Record>();
	private TaskPickerComposite taskPicker ;//= new TaskPickerComposite();
	private MyHandler handler = new MyHandler();
	
	private Label errorLabel = new Label();
	private HTML serverResponseLabel = new HTML();
	private DialogBox dialogBox = new DialogBox();
	private Button closeButton = new Button("Close");

	private int oldgame, oldconsole;
	
	private Integer game = new Integer(0);
	private Integer console = new Integer(0);
	
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		configString = new String("user");
		this.checkUser();
		
		taskPicker = new TaskPickerComposite(configString);
		
		RootPanel.get("docDiv").setStyleName("docDiv");
		RootPanel.get("myTable").setStyleName("myTable");
		
		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("user@gmail.com");
		errorLabel = new Label();

		//oldgame = 0;
		//oldconsole = 0;
		modifyTargetString();
		this.getListFromServer();
		modifyPage();
		
		// We can add style names to widgets
		taskPicker.addStyleName("sendButton");
		
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		//RootPanel.get("taskPicker").add(taskPicker);
		RootPanel.get("errorLabelContainer").add(errorLabel);
		RootPanel.get("gameTarget").add(gameTarget);
		
		
		// Create the popup dialog box
		dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		serverResponseLabel = new HTML();
		final HTML serverListLabel = new HTML();
		final VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.add(serverListLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				
			}
		});


		adminConsoleAdd.getGameButton().addClickHandler(handler);
		
	}
	
	private void sendNameToServer() {
		// First, we validate the input.
		
		if (!FieldVerifier.isValidName(adminConsoleAdd.getGameEmail())) {
			errorLabel.setText("Please enter at least four characters for email.");
			return;
		}
		if (!FieldVerifier.isValidName(adminConsoleAdd.getGameName())) {
			errorLabel.setText("Please enter at least four characters for name.");
			return;
		}

		Record highScore = new Record();
		highScore.setEmail(adminConsoleAdd.getGameEmail());
		highScore.setName(adminConsoleAdd.getGameName());
		highScore.setScore(Integer.parseInt(adminConsoleAdd.getGameScore()));
		highScore.setLevel(Integer.parseInt(adminConsoleAdd.getGameLevel()));
		highScore.setLives(Integer.parseInt(adminConsoleAdd.getGameLives()));
		highScore.setGameSpeed(Integer.parseInt(adminConsoleAdd.getGameSpeed()));
		highScore.setCountry(adminConsoleAdd.getGameCountry());
		highScore.setEnableMonsters(adminConsoleAdd.isMonstersOn());
		highScore.setEnableCollision(adminConsoleAdd.isMonsterCollisionOn());
		highScore.setSound(adminConsoleAdd.isSoundOn());
		
		
		if (game == TaskPickerComposite.GAME_AWESOMEFLYER) {
			highScore.setAndroidAppname(TaskPickerComposite.GAME_STRING_AWESOMEFLYER);
		}
		else {
			highScore.setAndroidAppname(TaskPickerComposite.GAME_STRING_AWESOMEGUY);
		}
		
		// Then, we send the input to the server.
		//sendButton.setEnabled(false);
		//textToServerLabel.setText(textToServer);
		serverResponseLabel.setText("");
		greetingService.greetServer(highScore,
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						dialogBox
								.setText("Remote Procedure Call - Failure");
						serverResponseLabel
								.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
					}

					public void onSuccess(String result) {
						dialogBox.setText("Remote Procedure Call");
						serverResponseLabel
								.removeStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(result
								+ "<br>----------------<br>");
						dialogBox.center();
						closeButton.setFocus(true);
					}
				});
		

		
	}
	
	public void getListFromServer() {
		greetingService.listServer(gameTargetString, 
				new AsyncCallback<ArrayList<Record>>() {

					@Override
					public void onFailure(Throwable caught) {
						//serverListLabel.setHTML("fail");
						//dialogBox.center();
						gameTarget.setText("no list");
					}

					@Override
					public void onSuccess(ArrayList<Record> result) {
						
						mList = result;
						modifyPage();
		
					}
			
		});
	}
	
	public void checkUser() {
		greetingService.checkUser(new String(), 
				new AsyncCallback<Integer> () {

			@Override
			public void onFailure(Throwable caught) {
				configString = new String("user");
				gameTarget.setText(gameTargetString);
				loadTaskPicker();
			}

			@Override
			public void onSuccess(Integer result) {
				
				//SiteEnum user = result;
				switch (result) {
				case SiteEnum.USER_NONE :
					configString = new String("none");

					break;
				case SiteEnum.USER_ADMIN :
					configString = new String("admin");

					break;
				case SiteEnum.USER_PLAIN :
					configString = new String("user");

					break;
				}
				loadTaskPicker();
//				taskPicker = new TaskPickerComposite(configString);
//				RootPanel.get("taskPicker").add(taskPicker);
//				taskPicker.getButtonUserScores().addClickHandler(handler);
//				taskPicker.getButtonDeleteRecord().addClickHandler(handler);
//				taskPicker.getBtnAwesomeFlyer().addClickHandler(handler);
//				taskPicker.getBtnAwesomeguy().addClickHandler(handler);
//				taskPicker.getBtnNewButton().addClickHandler(handler);
			}
			
		});
	}
	
	public void loadTaskPicker() {
		taskPicker = new TaskPickerComposite(configString);
		RootPanel.get("taskPicker").add(taskPicker);
		taskPicker.getButtonUserScores().addClickHandler(handler);
		taskPicker.getButtonDeleteRecord().addClickHandler(handler);
		taskPicker.getBtnAwesomeFlyer().addClickHandler(handler);
		taskPicker.getBtnAwesomeguy().addClickHandler(handler);
		taskPicker.getBtnNewButton().addClickHandler(handler);
	}
	
	public void modifyPage() {
		
	
		
		if ( oldgame == game && oldconsole == console && 
				console != TaskPickerComposite.CONSOLE_ADMIN_ADD) {
			return;
		}
		
		if (console == TaskPickerComposite.CONSOLE_ADMIN_ADD) {
			while (RootPanel.get("consoleContainer").getWidgetCount() > 0) {
				RootPanel.get("consoleContainer").remove(0);
			}
			while (RootPanel.get("resultsPanel").getWidgetCount() > 0 ) {
				RootPanel.get("resultsPanel").remove(0);
			}
			
			RootPanel.get("consoleContainer").add(adminConsoleAdd);
			//adminConsoleAdd.addStyleName("adminConsoleAdd");
			

		}
		else if ( console == TaskPickerComposite.CONSOLE_ADMIN_DELETE) {
			while (RootPanel.get("consoleContainer").getWidgetCount() > 0) {
				RootPanel.get("consoleContainer").remove(0);
			}
			while (RootPanel.get("resultsPanel").getWidgetCount() > 0 ) {
				RootPanel.get("resultsPanel").remove(0);
			}
			
			RootPanel.get("resultsPanel").add(adminConsoleDelete);
			//this.getListFromServer();
			adminConsoleDelete.fill(mList);
			

		}
		else {
			while (RootPanel.get("consoleContainer").getWidgetCount() > 0) {
				RootPanel.get("consoleContainer").remove(0);
			}
			while (RootPanel.get("resultsPanel").getWidgetCount() > 0 ) {
				RootPanel.get("resultsPanel").remove(0);
			}
			RootPanel.get("resultsPanel").add(resultsVPanel);
			//resultsVPanel = new RecordListerComposite();
			//this.getListFromServer();
			resultsVPanel.fill(mList);
			

		}
		

	}
	
	public void modifyTargetString() {
		
		//int oldgame,oldconsole;
		oldgame = game;
		oldconsole = console;	
		
		game = taskPicker.getGame();
		console = taskPicker.getConsole();
		
		
		
		if (game == TaskPickerComposite.GAME_AWESOMEGUY) {
			gameTarget.setText(TaskPickerComposite.GAME_STRING_AWESOMEGUY );
			gameTargetString = new String(TaskPickerComposite.GAME_STRING_AWESOMEGUY);
		}
		else if (game == TaskPickerComposite.GAME_AWESOMEFLYER) {
			gameTarget.setText(TaskPickerComposite.GAME_STRING_AWESOMEFLYER );
			gameTargetString = new String (TaskPickerComposite.GAME_STRING_AWESOMEFLYER);
		}
		
		if (console == TaskPickerComposite.CONSOLE_USER) {
			//gameTarget.setText( new String("console " + console));
			getListFromServer();
			
		}
		if (console == TaskPickerComposite.CONSOLE_ADMIN_DELETE) {
			//gameTarget.setText( new String("console " + console));

			getListFromServer();
		}
		if (console == TaskPickerComposite.CONSOLE_ADMIN_ADD) {
			
		}
		gameTarget.setText(gameTargetString);

//		if (oldconsole != console ) {
//			modifyPage();
//		}
		
	}
	
	// Create a handler for the sendButton and nameField
			class MyHandler implements ClickHandler {
				/**
				 * Fired when the user clicks on the sendButton.
				 */
				public void onClick(ClickEvent event) {
					modifyTargetString();
					Widget source = (Widget) event.getSource();
					if(source == adminConsoleAdd.getGameButton()) {
						sendNameToServer();
						//adminConsole.setButtonPressed(false);
						modifyPage();
					}
					else if (source == taskPicker.getButtonUserScores()) {
						getListFromServer();
						modifyPage();
					}
					else if ( source == taskPicker.getButtonDeleteRecord() ) {
						getListFromServer();
						modifyPage();
					}
					else if ( source == taskPicker.getBtnNewButton()) {
						modifyPage();
					}
					//modifyPage();
				}

			

			
			}
}
