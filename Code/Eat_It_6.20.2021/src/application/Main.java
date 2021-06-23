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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


//imports for JDBC

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;




public class Main extends Application {
	
	
	private Scene scene, scene_su;
	private StackPane root21;
	private TextField userNameField_su;
	private TextField passwordField_su;
	private TextField passwordField2;
	//private String jdbcUrl2 = "jdbc:sqlite:/Users/zuberbuhler/School/Summer2021/CS160/sqliteTesting/sqlite-tools-osx-x86-3350500/schema_v1.db";
	private String jdbcUrl3 = "jdbc:sqlite:/application/schema_v1.db";
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// *****************
			// Login Set Up Start
			// *****************
			double mainRectWidth = 1100, mainRectHeight = 650;
			Rectangle rect1 = new Rectangle(mainRectWidth, mainRectHeight);
			rect1.setFill(Color.web("#FFFFFF", 1));
			rect1.setArcHeight(40.0);
			rect1.setArcWidth(40.0);

			/* Welcome Label */
			Text welcomeText = new Text("Welcome");
			welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 60));

			StackPane loginInputBoxPane = new StackPane();

			/* Making the OffWhite Rectangle */
			Rectangle rect2 = new Rectangle(350, 350);
			rect2.setArcHeight(40.0);
			rect2.setArcWidth(40.0);
			rect2.setFill(Color.web("#EFEAE4", 1));

			/* Sign up Text */
			Text loginText = new Text("Login");
			loginText.setFont(Font.font("Arial", FontWeight.BOLD, 13));

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

			signUpBtn.setStyle("-fx-background-color: #AB81CD; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
			signUpBtn.setCursor(Cursor.HAND);

			loginBtn.setStyle("-fx-background-color: #AB81CD; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
			loginBtn.setCursor(Cursor.HAND);


			/* Stack the OffWhite background with the labels and text field */

			StackPane registerBoxPane = new StackPane();
			registerBoxPane.getChildren().addAll(loginBtn);

			StackPane registerBoxPane2 = new StackPane();
			registerBoxPane2.getChildren().addAll(signUpBtn);

			/*
			 * set register and cancel button next to each other and placed at bottom of
			 * gray square
			 */
			HBox signupBox = new HBox();
			signupBox.getChildren().addAll(registerBoxPane2, registerBoxPane);
			HBox.setMargin(signupBox, new Insets(30, 20, 0, 0));
			signupBox.setSpacing(10d);

			HBox Move = new HBox(signupBox);
			Move.setAlignment(Pos.CENTER);

			/* add all other members vertically */
			VBox loginVBox = new VBox();
			loginVBox.getChildren().addAll(loginText, userNameField, passwordField);
			loginVBox.setAlignment(Pos.CENTER);
			loginVBox.setSpacing(10d);

			// Creating a Grid Pane
			GridPane gridPane = new GridPane();

			// Setting the Grid alignment
			gridPane.setAlignment(Pos.CENTER);

			// Arranging all the nodes in the grid
			gridPane.add(loginVBox, 0, 0);
			gridPane.add(Move, 0, 1);

			/* put everything together */
			loginInputBoxPane.getChildren().addAll(rect2, gridPane);

			/* Making the Logo */
			//Image img = new Image(getClass().getResourceAsStream("/application/resources/logoTransparentSmall.png"));
			//ImageView imgView = new ImageView(img);

			/* Make logo and Off white box horizontal to each other */
			HBox hbox = new HBox();
			//hbox.getChildren().addAll(imgView, loginInputBoxPane);
			hbox.getChildren().addAll(loginInputBoxPane);

			hbox.setAlignment(Pos.CENTER);

			/* Make Welcome vertical to the horizontal logo and OffWhite box */
			VBox vbox1 = new VBox();

			vbox1.getChildren().addAll(welcomeText, hbox);
			vbox1.setPadding(new Insets(90, 0, 0, 0));
			vbox1.setSpacing(100);
			vbox1.setAlignment(Pos.TOP_CENTER);

			/* Put Vertical Box in the Stack Pane */
			StackPane root1 = new StackPane();
			root1.getChildren().addAll(rect1, vbox1, hbox);
			
			scene = new Scene(root1, mainRectWidth, mainRectHeight);
			
			

			// *****************
			// Login Set Up End
			// *****************
			
			
			
			// *********************************
			// Registration Scene Set Up Start
			// *********************************
			
			
			Text loginText2 = new Text("Sign Up");
			loginText2.setFont(Font.font("Arial", FontWeight.BOLD, 13));
//
//			/* Sign up Text for password */
			passwordField2 = new TextField("Re-Enter Password");
			passwordField2.setPrefWidth(200);
			passwordField2.setMaxWidth(200);
//			
			
			userNameField_su = new TextField("Enter User Name");
			userNameField_su.setMaxWidth(200);

			passwordField_su = new TextField("Enter Password");
			passwordField_su.setPrefWidth(200);
			passwordField_su.setMaxWidth(200);
			
//			/* create the buttons for sing up page */
			cancel.setStyle("-fx-background-color: #AB81CD; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
			cancel.setCursor(Cursor.HAND);

			register.setStyle("-fx-background-color: #AB81CD; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
			register.setCursor(Cursor.HAND);

//			/* add buttons to page */
			StackPane registerBoxPane_su = new StackPane();
			StackPane registerBoxPane2_su = new StackPane();
			registerBoxPane_su.getChildren().addAll(register);
			registerBoxPane2_su.getChildren().addAll(cancel);

//			/* add all other members vertically */
			VBox loginVBox2 = new VBox();
			loginVBox2.getChildren().clear();
			loginVBox2.getChildren().addAll(loginText2, userNameField_su, passwordField_su, passwordField2);
//
			
			HBox signupBox_su = new HBox();
			signupBox_su.getChildren().addAll(registerBoxPane2_su, registerBoxPane_su);
			HBox.setMargin(signupBox_su, new Insets(30, 20, 0, 0));
			signupBox_su.setSpacing(10d);

			HBox Move_su = new HBox(signupBox_su);
			Move_su.setAlignment(Pos.CENTER);
			
			GridPane gridPane_su = new GridPane();

			// Setting the Grid alignment
			gridPane_su.setAlignment(Pos.CENTER);
			
			// Arranging all the nodes in the grid
			gridPane_su.add(loginVBox2, 0, 0);
			gridPane_su.add(Move_su, 0, 1);

			/* put everything together */
			
			root21 = new StackPane();
			
			Rectangle rect_su = new Rectangle(mainRectWidth, mainRectHeight);
			rect_su.setFill(Color.web("#FFFFFF", 1));
			rect_su.setArcHeight(40.0);
			rect_su.setArcWidth(40.0);
			
			Rectangle rect2_su = new Rectangle(350, 350);
			rect2_su.setArcHeight(40.0);
			rect2_su.setArcWidth(40.0);
			rect2_su.setFill(Color.web("#EFEAE4", 1));
			
			StackPane loginInputBoxPane_su = new StackPane();
			loginInputBoxPane_su.getChildren().addAll(rect2_su, gridPane_su);
			
			
			HBox hbox_su = new HBox();
			//hbox.getChildren().addAll(imgView, loginInputBoxPane);
			hbox_su.getChildren().addAll(loginInputBoxPane_su);

			hbox_su.setAlignment(Pos.CENTER);

			
			
			root21.getChildren().addAll(rect_su, hbox_su);
			
			scene_su = new Scene(root21, mainRectWidth, mainRectHeight);
			
			
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
				
				System.out.println("username: " + username.toString() + "\n"
									+ "password: " + pass_word.toString());
				
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
				
				
				
			});
			
			signUpBtn.setOnAction(e->{
			
				primaryStage.setScene(scene_su);
			
			});
			
			cancel.setOnAction(e->{
				System.out.println("Cancel");
				
				primaryStage.setScene(scene);
				
			});
			
			register.setOnAction(e->{
				System.out.println("register");
				StringBuilder username = new StringBuilder();
				StringBuilder pass_word = new StringBuilder();
				StringBuilder reEnterPass = new StringBuilder();
				
				username.append(userNameField_su.getText().toString());
				pass_word.append(passwordField_su.getText().toString());
				reEnterPass.append(passwordField2.getText().toString());
				
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
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
