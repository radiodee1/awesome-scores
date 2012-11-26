package org.davidliebman.webapps.awesomescores.client;

import org.davidliebman.webapps.awesomescores.shared.FieldVerifier;
import org.davidliebman.webapps.awesomescores.shared.Record;

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

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Awesome_scores  implements EntryPoint {
	private String testHtml = new String();
	private Label gameTarget = new Label();
	private String gameTargetString = new String("none");
	private final AdminConsoleComposite adminConsoleAdd = new AdminConsoleComposite();
	private final RecordDeleteComposite adminConsoleDelete = new RecordDeleteComposite();
	private RecordListerComposite resultsVPanel = new RecordListerComposite();
	private ArrayList<Record> mList = new ArrayList<Record>();
	private final TaskPickerComposite taskPicker = new TaskPickerComposite();

	
	
	private Integer game = new Integer(TaskPickerComposite.GAME_AWESOMEGUY);
	private Integer console = new Integer(TaskPickerComposite.CONSOLE_USER);
	
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
		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("user@gmail.com");
		final Label errorLabel = new Label();

		modifyTargetString();
		
		// We can add style names to widgets
		sendButton.addStyleName("sendButton");
		//scoresButton.addStyleName("sendButton");
		taskPicker.addStyleName("sendButton");
		
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("taskPicker").add(taskPicker);
		RootPanel.get("errorLabelContainer").add(errorLabel);
		RootPanel.get("gameTarget").add(gameTarget);
		
		// Focus the cursor on the name field when the app loads
		//nameField.setFocus(true); <-- do not setFocus(true) !!
		nameField.selectAll();
		sendButton.setVisible(false);
		
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
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
		taskPicker.addClickHandler(new ClickHandler () {

			@Override
			public void onClick(ClickEvent event) {

				modifyTargetString();
				//getListFromServer();
				modifyPage();
				
				
			}
			
		});
		
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
					
				}
				else if (source == taskPicker.getButtonUserScores()) {
					getListFromServer();
				}
				
			}

		

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
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
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		//scoresButton.addClickHandler(handler);
		taskPicker.getButtonUserScores().addClickHandler(handler);
		adminConsoleAdd.getGameButton().addClickHandler(handler);
		
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
	
	public void modifyPage() {
		if (console == TaskPickerComposite.CONSOLE_ADMIN_ADD) {
			if (RootPanel.get("consoleContainer").getWidgetCount() > 0) {
				RootPanel.get("consoleContainer").remove(0);
			}
			while (RootPanel.get("resultsPanel").getWidgetCount() > 0 ) {
				RootPanel.get("resultsPanel").remove(0);
			}
			
			RootPanel.get("consoleContainer").add(adminConsoleAdd);

		}
		else if ( console == TaskPickerComposite.CONSOLE_ADMIN_DELETE) {
			if (RootPanel.get("consoleContainer").getWidgetCount() > 0) {
				RootPanel.get("consoleContainer").remove(0);
			}
			while (RootPanel.get("resultsPanel").getWidgetCount() > 0 ) {
				RootPanel.get("resultsPanel").remove(0);
			}
			
			RootPanel.get("consoleContainer").add(adminConsoleDelete);
			adminConsoleDelete.fill(mList);
		}
		else {
			while (RootPanel.get("consoleContainer").getWidgetCount() > 0) {
				RootPanel.get("consoleContainer").remove(0);
			}
			RootPanel.get("resultsPanel").add(resultsVPanel);
			//resultsVPanel = new RecordListerComposite();
			resultsVPanel.fill(mList);
		}
	}
	
	public void modifyTargetString() {
		
		int oldgame,oldconsole;
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
		if (oldgame != game && console != TaskPickerComposite.CONSOLE_ADMIN_ADD) {
			getListFromServer();
		}
		if (oldconsole != console ) {
			modifyPage();
		}
		
	}
}
