package application.GUI;

import application.DatabaseManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegistrationGUI {
	
	private DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
	private Scene signUp_scene;
	private TextField userNameField;
	private PasswordField passwordField1;
	private PasswordField passwordField2;
	
	private Button cancelBtn, registerBtn;
	private String jdbcUrl3 = "jdbc:sqlite:schema_v1.db";
	
	public RegistrationGUI(Stage stage, LoginGUI loginGUI)
	{
		double mainRectWidth = 1100, mainRectHeight = 650;
		Image imgBackground_signUp = new Image(getClass().getResourceAsStream("/application/resources/login_reg_background.png"));
		ImageView background_2 = new ImageView(imgBackground_signUp);

		// loginSetUp();
		userNameField = new TextField();
		userNameField.setPromptText("Enter a Username");
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

		passwordField1 = new PasswordField();
		passwordField1.setPromptText("Enter New Password");
		passwordField1.setPrefWidth(200);
		passwordField1.setMaxWidth(200);
		passwordField1.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            
	        	if (!newValue.matches("\\d*") || !newValue.matches("\\sa-zA-Z")) {
	        		passwordField1.setText(newValue.replaceAll("[^\\da-zA-Z]", ""));
	            }
	        	
	            if (passwordField1.getText().length() > 20) {
	                String s = passwordField1.getText().substring(0, 20);
	                passwordField1.setText(s);
	            }
	        }
	    });
		
		passwordField2 = new PasswordField();
		passwordField2.setPromptText("Re-enter New Password");
		passwordField2.setPrefWidth(200);
		passwordField2.setMaxWidth(200);
		passwordField2.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            
	        	if (!newValue.matches("\\d*") || !newValue.matches("\\sa-zA-Z")) {
	        		passwordField2.setText(newValue.replaceAll("[^\\da-zA-Z]", ""));
	            }
	        	
	            if (passwordField2.getText().length() > 20) {
	                String s = passwordField2.getText().substring(0, 20);
	                passwordField2.setText(s);
	            }
	        }
	    });

		cancelBtn = new Button("Cancel");
		registerBtn = new Button("Register");

		cancelBtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		cancelBtn.setCursor(Cursor.HAND);

		registerBtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		registerBtn.setCursor(Cursor.HAND);

		Image img_signUp = new Image(getClass().getResourceAsStream("/application/resources/Eat_It_Logo_300px.png"));
		ImageView imgView_signUp = new ImageView(img_signUp);
	
		
		HBox cancel_registerButton_HBox = new HBox();
		cancel_registerButton_HBox.getChildren().addAll(cancelBtn, registerBtn);
		HBox.setMargin(cancel_registerButton_HBox, new Insets(30, 20, 10, 10));
		cancel_registerButton_HBox.setSpacing(10d);
		cancel_registerButton_HBox.setAlignment(Pos.CENTER);
		
		Text errorMessage = new Text("");
		errorMessage.setFont(Font.font("Arial", FontWeight.THIN, FontPosture.ITALIC, 9));
		errorMessage.setFill(Color.RED); 

		/* add all other members vertically */
		VBox inputFields_VBox_signUp = new VBox();
		inputFields_VBox_signUp.getChildren().addAll(imgView_signUp, userNameField, passwordField1, passwordField2, errorMessage, cancel_registerButton_HBox);
		inputFields_VBox_signUp.setAlignment(Pos.CENTER);
		inputFields_VBox_signUp.setSpacing(10d);

		/* Put Vertical Box in the Stack Pane */
		StackPane registrationPane = new StackPane();
		registrationPane.getChildren().addAll(background_2, inputFields_VBox_signUp);
		
		signUp_scene = new Scene(registrationPane, mainRectWidth, mainRectHeight);
		
		// *********************************
		// Event Listeners Set Up
		// *********************************
		
		cancelBtn.setOnAction(e->{
			
			System.out.println("Cancel Button Pressed");
			stage.setScene(loginGUI.getLoginScene());
			stage.setTitle("Login Page");
			errorMessage.setText("");
			userNameField.clear();
			passwordField1.clear();
			passwordField2.clear();
			
		});
		
		registerBtn.setOnAction(e->{
			
			errorMessage.setText("");
			
			
			System.out.println("register");
			
			String username = getUserNameString();
			String pass_word = getPassword1String();
			String reEnterPass = getPassword2String();
			
			System.out.println("username: " + username.toString() + "\n"
								+ "password: " + pass_word.toString() + "\n"
								+ "reEnterPass: " + reEnterPass.toString());
				
			
			if(dbm.insertCredentials(username, pass_word, reEnterPass))
			{
				dbm.printCredentials();
				dbm.isCredentialsValid(username, pass_word); //sets the User user variable
				
				HomepageGUI homepageGUI = new HomepageGUI(stage, dbm.getUser(), loginGUI.getLoginScene());
				stage.setScene(homepageGUI.getHomepageGUIscene());
				stage.setTitle("Homepage");
				
				errorMessage.setText("");
				userNameField.clear();
				passwordField1.clear();
				passwordField2.clear();
			}
			else
			{
				errorMessage.setText("Username already taken: " + username);
			
			}
			
			

		});
		
	}
	
	
	public Scene getSignUpScene() 
	{
		return signUp_scene;
	}
	
	
	public String getUserNameString()
	{
		return userNameField.getText().toString();
	}
	
	public String getPassword1String()
	{
		return passwordField1.getText().toString();
	}
	
	public String getPassword2String()
	{
		return passwordField2.getText().toString();
	}
	
	public Button getCancelButton()
	{
		return cancelBtn;
	}
	
	public Button getRegistrationButton()
	{
		return registerBtn;
	}

}
