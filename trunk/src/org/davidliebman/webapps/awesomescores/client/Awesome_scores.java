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

import java.util.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Awesome_scores  implements EntryPoint {
	private String testHtml = new String();
	private Label gameTarget = new Label("What Game!!??");
	private String gameTargetString = new String("none");
	private final AdminConsoleComposite adminConsole = new AdminConsoleComposite();
	
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
		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("user@gmail.com");
		final Label errorLabel = new Label();

//		final Button scoresButton = new Button("Submit Scores");

		
		final TaskPickerComposite taskPicker = new TaskPickerComposite();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");
		//scoresButton.addStyleName("sendButton");
		taskPicker.addStyleName("sendButton");
		
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("taskPicker").add(taskPicker);
		//RootPanel.get("nameFieldContainer").add(nameField);
//		RootPanel.get("sendButtonContainer").add(sendButton);
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
		VerticalPanel dialogVPanel = new VerticalPanel();
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
				//sendButton.setEnabled(true);
				//sendButton.setFocus(true);
				//scoresButton.setEnabled(true);
				//scoresButton.setFocus(true);
			}
		});
		taskPicker.addClickHandler(new ClickHandler () {

			@Override
			public void onClick(ClickEvent event) {
				
//				gameplayerName.setText(event.toDebugString() + 
//						taskPicker.getConsole() + " " + 
//						taskPicker.getGame());
				game = taskPicker.getGame();
				console = taskPicker.getConsole();
				modifyTargetString();
				modifyPage();
			}
			
		});
		
		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				if(console == TaskPickerComposite.CONSOLE_ADMIN) {
					sendNameToServer();
					//adminConsole.setButtonPressed(false);
					
				}
				//sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				
				if (!FieldVerifier.isValidName(adminConsole.getGameEmail())) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				errorLabel.setText("text");
				
				Record highScore = new Record();
				highScore.setEmail(adminConsole.getGameEmail());
				highScore.setName(adminConsole.getGameName());
				highScore.setScore(Integer.parseInt(adminConsole.getGameScore()));
				highScore.setLevel(Integer.parseInt(adminConsole.getGameLevel()));
				highScore.setLives(Integer.parseInt(adminConsole.getGameLives()));
				highScore.setGameSpeed(Integer.parseInt(adminConsole.getGameSpeed()));
				
				
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
				
				greetingService.listServer(gameTargetString, 
						new AsyncCallback<ArrayList<Record>>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								serverListLabel.setHTML("fail");
								dialogBox.center();
							}

							@Override
							public void onSuccess(ArrayList<Record> result) {
								testHtml = new String();
								for (int x = 0; x < result.size(); x ++ ) {
									testHtml = testHtml + result.get(x).getName() + "<br>\n";
								}

								serverListLabel.setHTML(testHtml);
								dialogBox.center();
							}
					
				});
				
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		//scoresButton.addClickHandler(handler);
		//nameField.addKeyUpHandler(handler);
		adminConsole.getGameButton().addClickHandler(handler);
	}
	
	public void modifyPage() {
		if (console == TaskPickerComposite.CONSOLE_ADMIN) {
			if (RootPanel.get("consoleContainer").getWidgetCount() > 0) {
				RootPanel.get("consoleContainer").remove(0);
			}
			RootPanel.get("consoleContainer").add(adminConsole);

		}
		else {
			RootPanel.get("consoleContainer").remove(0);
		}
	}
	
	public void modifyTargetString() {
		if (game == TaskPickerComposite.GAME_AWESOMEGUY) {
			gameTarget.setText(TaskPickerComposite.GAME_STRING_AWESOMEGUY);
			gameTargetString = new String(TaskPickerComposite.GAME_STRING_AWESOMEGUY);
		}
		else if (game == TaskPickerComposite.GAME_AWESOMEFLYER) {
			gameTarget.setText(TaskPickerComposite.GAME_STRING_AWESOMEFLYER);
			gameTargetString = new String (TaskPickerComposite.GAME_STRING_AWESOMEFLYER);
		}
	}
}
