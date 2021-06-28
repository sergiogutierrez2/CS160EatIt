package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegistrationGUI {
	
	private DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
	private Scene signUp_scene;
	private TextField userNameField, passwordField1, passwordField2;
	private Button cancelBtn, registerBtn;
	private String jdbcUrl3 = "jdbc:sqlite:schema_v1.db";
	
	public RegistrationGUI(Stage stage, LoginGUI loginGUI)
	{
		double mainRectWidth = 1100, mainRectHeight = 650;
		Image imgBackground_signUp = new Image(getClass().getResourceAsStream("/application/resources/login_reg_background.png"));
		ImageView background_2 = new ImageView(imgBackground_signUp);

		// loginSetUp();
		userNameField = new TextField("Enter User Name");
		userNameField.setPrefWidth(200);
		userNameField.setMaxWidth(200);

		passwordField1 = new TextField("Enter New Password");
		passwordField1.setPrefWidth(200);
		passwordField1.setMaxWidth(200);
		
		passwordField2 = new TextField("Re-Enter New Password");
		passwordField2.setPrefWidth(200);
		passwordField2.setMaxWidth(200);

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

		/* add all other members vertically */
		VBox inputFields_VBox_signUp = new VBox();
		inputFields_VBox_signUp.getChildren().addAll(imgView_signUp, userNameField, passwordField1, passwordField2, cancel_registerButton_HBox);
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
			
		});
		
		registerBtn.setOnAction(e->{
			System.out.println("register");
			
			String username = getUserNameString();
			String pass_word = getPassword1String();
			String reEnterPass = getPassword2String();
			
			System.out.println("username: " + username.toString() + "\n"
								+ "password: " + pass_word.toString() + "\n"
								+ "reEnterPass: " + reEnterPass.toString());
				
			dbm.insertCredentials(username, pass_word, reEnterPass);		
			dbm.printCredentials();

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
