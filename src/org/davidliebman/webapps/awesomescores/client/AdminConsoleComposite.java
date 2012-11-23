package org.davidliebman.webapps.awesomescores.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;

public class AdminConsoleComposite extends Composite implements HasClickHandlers{

	private TextBox gameEmail, gameName, gameScore, gameLevel, gameLives, gameSpeed;
	private Button gameButton;
	private boolean buttonPressed = false;
	
	
	public AdminConsoleComposite() {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setWidth("180px");
		
		Label lblNameemailAddress = new Label("Name/Email Address:");
		verticalPanel.add(lblNameemailAddress);
		
		gameEmail = new TextBox();
		gameEmail.setText("user@gmail.com");
		verticalPanel.add(gameEmail);
		
		Label lblGameName = new Label("Game Name:");
		verticalPanel.add(lblGameName);
		
		gameName = new TextBox();
		gameName.setText("user");
		verticalPanel.add(gameName);
		
		Label lblHighScore = new Label("High Score:");
		verticalPanel.add(lblHighScore);
		
		gameScore = new TextBox();
		gameScore.setText("1000");
		verticalPanel.add(gameScore);
		
		Label lblLevel = new Label("Level:");
		verticalPanel.add(lblLevel);
		
		gameLevel = new TextBox();
		gameLevel.setText("1");
		verticalPanel.add(gameLevel);
		
		Label lblLives = new Label("Lives:");
		verticalPanel.add(lblLives);
		
		gameLives = new TextBox();
		gameLives.setText("3");
		verticalPanel.add(gameLives);
		
		Label lblSpeed = new Label("Speed:");
		verticalPanel.add(lblSpeed);
		
		gameSpeed = new TextBox();
		gameSpeed.setText("40");
		verticalPanel.add(gameSpeed);
		
		gameButton = new Button("Submit Scores");
		verticalPanel.add(gameButton);
		
		gameButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				buttonPressed = true;
				
			}
			
		});
	}

	public String getGameEmail() {
		return gameEmail.getText();
	}

	public void setGameEmail(String gameEmail) {
		this.gameEmail.setText(gameEmail) ;
	}

	public String getGameName() {
		return gameName.getText();
	}

	public void setGameName(String gameName) {
		this.gameName.setText(gameName) ;
	}

	public String getGameScore() {
		return gameScore.getText();
	}

	public void setGameScore(String gameScore) {
		this.gameScore.setText(gameScore);
	}

	public String getGameLevel() {
		return gameLevel.getText();
	}

	public void setGameLevel(String gameLevel) {
		this.gameLevel.setText(gameLevel) ;
	}

	public String getGameLives() {
		return gameLives.getText();
	}

	public void setGameLives(String gameLives) {
		this.gameLives.setText(gameLives) ;
	}

	public String getGameSpeed() {
		return gameSpeed.getText();
	}

	public void setGameSpeed(String gameSpeed) {
		this.gameSpeed.setText(gameSpeed);
	}
	
	

	public Button getGameButton() {
		return gameButton;
	}

//	public void setGameButton(Button gameButton) {
//		this.gameButton = gameButton;
//	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		// TODO Auto-generated method stub
		return addDomHandler(handler, ClickEvent.getType());
	}

	public boolean isButtonPressed() {
		return buttonPressed;
	}

	public void setButtonPressed(boolean buttonPressed) {
		this.buttonPressed = buttonPressed;
	}
	
	
	
}
