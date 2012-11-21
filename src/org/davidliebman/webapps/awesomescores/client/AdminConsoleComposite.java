package org.davidliebman.webapps.awesomescores.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;

public class AdminConsoleComposite extends Composite {

	private TextBox gameEmail, gameName, gameScore, gameLevel, gameLives, gameSpeed;
	//private Button gameButton;
	
	public AdminConsoleComposite() {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setWidth("180px");
		
		Label lblNameemailAddress = new Label("Name/Email Address:");
		verticalPanel.add(lblNameemailAddress);
		
		TextBox txtbxUsergmailcom = new TextBox();
		txtbxUsergmailcom.setText("user@gmail.com");
		verticalPanel.add(txtbxUsergmailcom);
		
		Label lblGameName = new Label("Game Name:");
		verticalPanel.add(lblGameName);
		
		TextBox txtbxUser = new TextBox();
		txtbxUser.setText("user");
		verticalPanel.add(txtbxUser);
		
		Label lblHighScore = new Label("High Score:");
		verticalPanel.add(lblHighScore);
		
		TextBox textBox = new TextBox();
		textBox.setText("1000");
		verticalPanel.add(textBox);
		
		Label lblLevel = new Label("Level:");
		verticalPanel.add(lblLevel);
		
		TextBox textBox_1 = new TextBox();
		textBox_1.setText("1");
		verticalPanel.add(textBox_1);
		
		Label lblLives = new Label("Lives:");
		verticalPanel.add(lblLives);
		
		TextBox textBox_2 = new TextBox();
		textBox_2.setText("3");
		verticalPanel.add(textBox_2);
		
		Label lblSpeed = new Label("Speed:");
		verticalPanel.add(lblSpeed);
		
		TextBox textBox_3 = new TextBox();
		textBox_3.setText("40");
		verticalPanel.add(textBox_3);
		
		Button btnSubmitScores = new Button("Submit Scores");
		verticalPanel.add(btnSubmitScores);
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
	
}
