package application.GUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.DatabaseManager;
import application.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This is a class for the login GUI.
 * This class creates GUI for the login screen.
 * 
 * @author Eat_It(Summer 2021 Team)
 */
public class LoginGUI {
	private DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
	private RegistrationGUI registrationGUI;
	private SettingGUI settingGUI;
	private Scene login_scene;
	private Text errorMessageLogin;
	private TextField userNameField;
	private PasswordField passwordField;
	private Button signUpBtn, loginBtn, settings;
	private String jdbcUrl3 = "jdbc:sqlite:schema_v1.db";
	private ObservableList<User> settingsTableData;
	
	/**
	 * This is how the GUI for the login page is created and 
	 * it accepts all the credentials.
	 * @param stage This is the page passed to the constructor. 
	 */
	public LoginGUI(Stage stage)
	{
		double mainRectWidth = 1100, mainRectHeight = 650;
		
		Image imgBackground = new Image(getClass().getResourceAsStream("/application/resources/login_reg_background.png"));
		ImageView background_1 = new ImageView(imgBackground);
		
		errorMessageLogin = new Text("");
		errorMessageLogin.setFont(Font.font("Arial", FontWeight.THIN, FontPosture.ITALIC, 9));
		errorMessageLogin.setFill(Color.RED); 

		// loginSetUp();
		userNameField = new TextField();
		userNameField.setPromptText("Enter User Name");
		userNameField.setPrefWidth(200);
		userNameField.setMaxWidth(200);
		userNameField.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            
	        	if (!newValue.matches("\\d*") || !newValue.matches("\\sa-zA-Z")) {
	        		userNameField.setText(newValue.replaceAll("[^\\da-zA-Z]", ""));
	            }
	        	
	            if (userNameField.getText().length() > 20) {
	                String s = userNameField.getText().substring(0, 20);
	                userNameField.setText(s);
	            }
	        }
	    });

		passwordField = new PasswordField();
		passwordField.setPromptText("Enter Password");
		passwordField.setPrefWidth(200);
		passwordField.setMaxWidth(200);
		passwordField.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            
	        	if (!newValue.matches("\\d*") || !newValue.matches("\\sa-zA-Z")) {
	        		passwordField.setText(newValue.replaceAll("[^\\da-zA-Z]", ""));
	            }
	        	
	            if (passwordField.getText().length() > 20) {
	                String s = passwordField.getText().substring(0, 20);
	                passwordField.setText(s);
	            }
	        }
	    });

		/* create the buttons for sing up page */
		signUpBtn = new Button("Sign Up");
		loginBtn = new Button("Login");
		
		signUpBtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		signUpBtn.setCursor(Cursor.HAND);

		loginBtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		loginBtn.setCursor(Cursor.HAND);
		
		settings = new Button("settings");
		settings.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		settings.setCursor(Cursor.HAND);
		

		Image img = new Image(getClass().getResourceAsStream("/application/resources/Eat_It_Logo_300px.png"));
		ImageView imgView = new ImageView(img);
	
		HBox signUp_LoginButton_HBox = new HBox();
		signUp_LoginButton_HBox.getChildren().addAll(loginBtn, signUpBtn);
		HBox.setMargin(signUp_LoginButton_HBox, new Insets(30, 20, 10, 10));
		signUp_LoginButton_HBox.setSpacing(10d);
		signUp_LoginButton_HBox.setAlignment(Pos.CENTER);

		/* add all other members vertically */
		VBox inputFields_VBox = new VBox();
		inputFields_VBox.getChildren().addAll(imgView, errorMessageLogin, userNameField, passwordField, signUp_LoginButton_HBox, settings);
		inputFields_VBox.setAlignment(Pos.CENTER);
		inputFields_VBox.setSpacing(10d);

		/* Put Vertical Box in the Stack Pane */
		StackPane loginPane = new StackPane();
		loginPane.getChildren().addAll(background_1, inputFields_VBox);
		
		login_scene = new Scene(loginPane, mainRectWidth, mainRectHeight);
		
		// *********************************
		// Event Listeners Set Up
		// *********************************
		
		signUpBtn.setOnAction(e->{
			
			System.out.println("Sign Up Button Pressed");
			stage.setScene(registrationGUI.getSignUpScene());
			stage.setTitle("Sign Up Page");
		
		});
		
		signUpBtn.setOnMouseEntered(new EventHandler<MouseEvent>() 
		{
			 @Override
		    public void handle(MouseEvent t) {
				 signUpBtn.setStyle("-fx-background-color: #C792DF; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		    }
			
		});
		
		signUpBtn.setOnMouseExited(new EventHandler<MouseEvent>() 
		{
			 @Override
		    public void handle(MouseEvent t) {
				 signUpBtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		    }
			
		});
		
		loginBtn.setOnAction(e-> {
			
			String username = getUserNameString();
			String pass_word = getPasswordString();
			boolean isValidCredentials = dbm.isCredentialsValid(username, pass_word);
			
			if(!isValidCredentials)
			{
				getLoginErrorMessageText().setText("Invalid Credentials");
			}
			else 
			{
				getLoginErrorMessageText().setText("");
				System.out.println("Login Successful");
				User user = dbm.getUser(username);
				HomepageGUI homepageGUI = new HomepageGUI(stage, user, login_scene);
				stage.setScene(homepageGUI.getHomepageGUIscene());
				stage.setTitle("Homepage");
				
				userNameField.clear();
				passwordField.clear();
			}
		});
		
		loginBtn.setOnMouseEntered(new EventHandler<MouseEvent>() 
		{
			@Override
		    public void handle(MouseEvent t) {
				loginBtn.setStyle("-fx-background-color: #C792DF; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		    }
		});
		
		loginBtn.setOnMouseExited(new EventHandler<MouseEvent>() 
		{
			@Override
		    public void handle(MouseEvent t) {
				loginBtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		    }
		});
		
		settings.setOnAction(e-> {
			
			System.out.println("Setting Button Pressed");
			stage.setScene(settingGUI.getSettingScene());
			stage.setTitle("Settings Page");
			userNameField.clear();
			passwordField.clear();
			settingGUI.updateTableView();
		});
		
		settings.setOnMouseEntered(new EventHandler<MouseEvent>() 
		{
			@Override
		    public void handle(MouseEvent t) {
				settings.setStyle("-fx-background-color: #C792DF; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		    }
		});
		
		settings.setOnMouseExited(new EventHandler<MouseEvent>() 
		{
			@Override
		    public void handle(MouseEvent t) {
				settings.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		    }
		});
	}
	
	/**
	 * This will get the login Scene.
	 * @return The login scene.
	 */
	public Scene getLoginScene() 
	{
		return login_scene;
	}
	
	public Text getLoginErrorMessageText() 
	{
		return errorMessageLogin;
	}
	
	public String getUserNameString()
	{
		return userNameField.getText().toString();
	}
	
	public String getPasswordString()
	{
		return passwordField.getText().toString();
	}
	
	public Button getLoginButton()
	{
		return loginBtn;
	}
	
	public Button getSignUpButton()
	{
		return signUpBtn;
	}
	
	public void setRegistrationGUI(RegistrationGUI registrationGUI)
	{
		this.registrationGUI = registrationGUI;
	}
	
	public void setSettingGUI(SettingGUI settingGUI)
	{
		this.settingGUI = settingGUI;
	}

}
