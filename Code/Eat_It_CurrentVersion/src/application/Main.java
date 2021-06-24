package application;
	
//imports for javaFX

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


//imports for JDBC

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;




public class Main extends Application {
	
	
	private Scene login_scene, signUp_scene;
	private StackPane root21;
	private TextField userNameField_su;
	private TextField passwordField_su;
	private TextField passwordField2;
	//private String jdbcUrl2 = "jdbc:sqlite:/Users/zuberbuhler/School/Summer2021/CS160/sqliteTesting/sqlite-tools-osx-x86-3350500/schema_v1.db";
	private String jdbcUrl3 = "jdbc:sqlite:schema_v1.db";
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// *****************
			// Login Set Up Start
			// *****************
			double mainRectWidth = 1100, mainRectHeight = 650;
			
			Image imgBackground = new Image(getClass().getResourceAsStream("/application/resources/login_reg_background.png"));
			ImageView background_1 = new ImageView(imgBackground);
			
			Text errorMessageLogin = new Text("");
			errorMessageLogin.setFont(Font.font("Arial", FontWeight.THIN, FontPosture.ITALIC, 9));
			errorMessageLogin.setFill(Color.RED); 

			// loginSetUp();
			TextField userNameField = new TextField("Enter User Name");
			userNameField.setPrefWidth(200);
			userNameField.setMaxWidth(200);

			TextField passwordField = new TextField("Enter Password");
			passwordField.setPrefWidth(200);
			passwordField.setMaxWidth(200);

			/* create the buttons for sing up page */
			Button signUpBtn = new Button("Sign Up");
			Button loginBtn = new Button("Login  ");
			
			Button cancel = new Button("Cancel");
			Button register = new Button("Register");

			signUpBtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
			signUpBtn.setCursor(Cursor.HAND);

			loginBtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
			loginBtn.setCursor(Cursor.HAND);


			Image img = new Image(getClass().getResourceAsStream("/application/resources/Eat_It_Logo_300px.png"));
			ImageView imgView = new ImageView(img);
		
			HBox signUp_LoginButton_HBox = new HBox();
			signUp_LoginButton_HBox.getChildren().addAll(loginBtn, signUpBtn);
			HBox.setMargin(signUp_LoginButton_HBox, new Insets(30, 20, 10, 10));
			signUp_LoginButton_HBox.setSpacing(10d);
			signUp_LoginButton_HBox.setAlignment(Pos.CENTER);

			/* add all other members vertically */
			VBox inputFields_VBox = new VBox();
			inputFields_VBox.getChildren().addAll(imgView, errorMessageLogin, userNameField, passwordField, signUp_LoginButton_HBox);
			inputFields_VBox.setAlignment(Pos.CENTER);
			inputFields_VBox.setSpacing(10d);



			/* Put Vertical Box in the Stack Pane */
			StackPane loginPane = new StackPane();
			loginPane.getChildren().addAll(background_1, inputFields_VBox);
			
			login_scene = new Scene(loginPane, mainRectWidth, mainRectHeight);
			
			

			// *****************
			// Login Set Up End
			// *****************
			
			
			
			// *********************************
			// Registration Scene Set Up Start
			// *********************************
//			
//			
//			Text loginText2 = new Text("Sign Up");
//			loginText2.setFont(Font.font("Arial", FontWeight.BOLD, 13));
////
////			/* Sign up Text for password */
//			passwordField2 = new TextField("Re-Enter Password");
//			passwordField2.setPrefWidth(200);
//			passwordField2.setMaxWidth(200);
////			
//			
//			userNameField_su = new TextField("Enter User Name");
//			userNameField_su.setMaxWidth(200);
//
//			passwordField_su = new TextField("Enter Password");
//			passwordField_su.setPrefWidth(200);
//			passwordField_su.setMaxWidth(200);
//			
////			/* create the buttons for sing up page */
//			cancel.setStyle("-fx-background-color: #AB81CD; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
//			cancel.setCursor(Cursor.HAND);
//
//			register.setStyle("-fx-background-color: #AB81CD; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
//			register.setCursor(Cursor.HAND);
//
////			/* add buttons to page */
//			StackPane registerBoxPane_su = new StackPane();
//			StackPane registerBoxPane2_su = new StackPane();
//			registerBoxPane_su.getChildren().addAll(register);
//			registerBoxPane2_su.getChildren().addAll(cancel);
//
////			/* add all other members vertically */
//			VBox loginVBox2 = new VBox();
//			loginVBox2.getChildren().clear();
//			loginVBox2.getChildren().addAll(loginText2, userNameField_su, passwordField_su, passwordField2);
////
//			
//			HBox signupBox_su = new HBox();
//			signupBox_su.getChildren().addAll(registerBoxPane2_su, registerBoxPane_su);
//			HBox.setMargin(signupBox_su, new Insets(30, 20, 0, 0));
//			signupBox_su.setSpacing(10d);
//
//			HBox Move_su = new HBox(signupBox_su);
//			Move_su.setAlignment(Pos.CENTER);
//			
//			GridPane gridPane_su = new GridPane();
//
//			// Setting the Grid alignment
//			gridPane_su.setAlignment(Pos.CENTER);
//			
//			// Arranging all the nodes in the grid
//			gridPane_su.add(loginVBox2, 0, 0);
//			gridPane_su.add(Move_su, 0, 1);
//
//			/* put everything together */
//			
//			root21 = new StackPane();
//			
//			Rectangle rect_su = new Rectangle(mainRectWidth, mainRectHeight);
//			rect_su.setFill(Color.web("#FFFFFF", 1));
//			rect_su.setArcHeight(40.0);
//			rect_su.setArcWidth(40.0);
//			
//			Rectangle rect2_su = new Rectangle(350, 350);
//			rect2_su.setArcHeight(40.0);
//			rect2_su.setArcWidth(40.0);
//			rect2_su.setFill(Color.web("#EFEAE4", 1));
//			
//			StackPane loginInputBoxPane_su = new StackPane();
//			loginInputBoxPane_su.getChildren().addAll(rect2_su, gridPane_su);
//			
//			
//			HBox hbox_su = new HBox();
//			//hbox.getChildren().addAll(imgView, loginInputBoxPane);
//			hbox_su.getChildren().addAll(loginInputBoxPane_su);
//
//			hbox_su.setAlignment(Pos.CENTER);
//
//			
//			
//			root21.getChildren().addAll(rect_su, hbox_su);
//			
//			scene_su = new Scene(root21, mainRectWidth, mainRectHeight);
			
			// replace names:
			Image imgBackground_signUp = new Image(getClass().getResourceAsStream("/application/resources/login_reg_background.png"));
			ImageView background_2 = new ImageView(imgBackground_signUp);
			

			// loginSetUp();
			TextField userNameField_su = new TextField("Enter User Name");
			userNameField_su.setPrefWidth(200);
			userNameField_su.setMaxWidth(200);

			TextField passwordField1_su = new TextField("Enter New Password");
			passwordField1_su.setPrefWidth(200);
			passwordField1_su.setMaxWidth(200);
			
			TextField passwordField2_su = new TextField("Re-Enter New Password");
			passwordField2_su.setPrefWidth(200);
			passwordField2_su.setMaxWidth(200);

			Button cancel_signUp = new Button("Cancel");
			Button register_signUp = new Button("Register");

			cancel_signUp.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
			cancel_signUp.setCursor(Cursor.HAND);

			register_signUp.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
			register_signUp.setCursor(Cursor.HAND);


			Image img_signUp = new Image(getClass().getResourceAsStream("/application/resources/Eat_It_Logo_300px.png"));
			ImageView imgView_signUp = new ImageView(img_signUp);
		
			HBox cancel_registerButton_HBox = new HBox();
			cancel_registerButton_HBox.getChildren().addAll(cancel_signUp, register_signUp);
			HBox.setMargin(cancel_registerButton_HBox, new Insets(30, 20, 10, 10));
			cancel_registerButton_HBox.setSpacing(10d);
			cancel_registerButton_HBox.setAlignment(Pos.CENTER);

			/* add all other members vertically */
			VBox inputFields_VBox_signUp = new VBox();
			inputFields_VBox_signUp.getChildren().addAll(imgView_signUp, userNameField_su, passwordField1_su, passwordField2_su, cancel_registerButton_HBox);
			inputFields_VBox_signUp.setAlignment(Pos.CENTER);
			inputFields_VBox_signUp.setSpacing(10d);



			/* Put Vertical Box in the Stack Pane */
			StackPane registrationPane = new StackPane();
			registrationPane.getChildren().addAll(background_2, inputFields_VBox_signUp);
			
			signUp_scene = new Scene(registrationPane, mainRectWidth, mainRectHeight);
			
			 
			
			// *********************************
			// Registration Scene Set Up End
			// *********************************
			

			// *********************************
			// Event Listeners Set Up Start
			// *********************************
			
			loginBtn.setOnAction(e->{
				
				StringBuilder username = new StringBuilder();
				StringBuilder pass_word = new StringBuilder();
				
				username.append(userNameField.getText().toString());
				pass_word.append(passwordField.getText().toString());
				
				System.out.println("username: '" + username.toString() + "'\n"
									+ "password: '" + pass_word.toString() + "'");
				
				try 
				{
					//choose either jdbcUrl or jdbcUrl2
					//Connection connection = DriverManager.getConnection(jdbcUrl);
					Connection connection = DriverManager.getConnection(jdbcUrl3);
					
					String sql = "SELECT username, pass_word FROM logins where username = ? and pass_word = ?";
					System.out.println("SELECT username, pass_word FROM logins where username = '" + username.toString() + "'");
					//String sql_1 = "insert into logins (username, pass_word) values ('" + username.toString() + "', '" + pass_word.toString() + "')";

					//Statement statement = connection.createStatement();
					PreparedStatement pstmt  = connection.prepareStatement(sql);
					
					pstmt.setString(1, username.toString());
					pstmt.setString(2, pass_word.toString());
		
					
					//statement.execute(sql_1);
					ResultSet result = pstmt.executeQuery();
					
					boolean invalidCredentialsFlag = true;
					while (result.next()) 
					{
						
						invalidCredentialsFlag = false;
						//Integer user_id = result.getInt("user_id"); //specified attribute name is "user_id" in sql db
						String username_l = result.getString("username"); //specified attribute name is "username" in sql db
						String pass_word_l = result.getString("pass_word"); //specified attribute name is "pass_word" in sql db
						
						System.out.println(" | " + username_l + " | " + pass_word_l);
						//System.out.println(user_id + " | " + username_l + " | " + pass_word_l);
						
						
					}
					
					if(invalidCredentialsFlag)
					{
						errorMessageLogin.setText("Invalid Credentials");
					}
					else {
						errorMessageLogin.setText("");
						System.out.println("Login Successful");
					}
					
					result.close();
					//statement.close();
					pstmt.close();
					connection.close();
				
				
					
					
				} catch (SQLException e2) {
					System.out.println("Error return from query to SQLite database2");
					e2.printStackTrace();
				}
				
				
				
			});
			
			signUpBtn.setOnAction(e->{
			
				primaryStage.setScene(signUp_scene);
			
			});
			
			cancel_signUp.setOnAction(e->{
				System.out.println("Cancel");
				
				primaryStage.setScene(login_scene);
				
			});
			
			register_signUp.setOnAction(e->{
				System.out.println("register");
				StringBuilder username = new StringBuilder();
				StringBuilder pass_word = new StringBuilder();
				StringBuilder reEnterPass = new StringBuilder();
				
				username.append(userNameField_su.getText().toString());
				pass_word.append(passwordField1_su.getText().toString());
				reEnterPass.append(passwordField2_su.getText().toString());
				
				System.out.println("username: " + username.toString() + "\n"
									+ "password: " + pass_word.toString() + "\n"
									+ "reEnterPass: " + reEnterPass.toString());
				
				if( !( pass_word.toString().equals( reEnterPass.toString() ) ) )
				{
					System.out.println("passwords don't match");
				} 
				else
				{
					try 
					{
						//choose either jdbcUrl or jdbcUrl2
						//Connection connection = DriverManager.getConnection(jdbcUrl);
						Connection connection = DriverManager.getConnection(jdbcUrl3);
						
						String sql = "SELECT * FROM logins";
						String sql_1 = "insert into logins (username, pass_word) values ('" + username.toString() + "', '" + pass_word.toString() + "')";

						Statement statement = connection.createStatement();
						
						try {
							statement.execute(sql_1);
						} catch (SQLException e1) {
							System.out.println("Error returned by SQLite database");
							System.out.println(e1.getErrorCode());	//returning 19 means duplicate username
							
							if(e1.getErrorCode() == 19)
							{
								System.out.println("Error: Duplicate Username!");
							}
							//e1.printStackTrace();
						}
						
						ResultSet result = statement.executeQuery(sql);
						
						while (result.next()) 
						{
							 
							Integer user_id = result.getInt("user_id"); //specified attribute name is "user_id" in sql db
							String username_l = result.getString("username"); //specified attribute name is "username" in sql db
							String pass_word_l = result.getString("pass_word"); //specified attribute name is "pass_word" in sql db
							
							System.out.println(user_id + " | " + username_l + " | " + pass_word_l);
							
						}
						
						result.close();
						statement.close();
						connection.close();
						
						
					} catch (SQLException e2) {
						System.out.println("Error return from query to SQLite database");
						//e2.printStackTrace();
					}
					
				}
				
			});
			
			// *********************************
			// Event Listeners Set Up End
			// *********************************
			
			primaryStage.setScene(login_scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
