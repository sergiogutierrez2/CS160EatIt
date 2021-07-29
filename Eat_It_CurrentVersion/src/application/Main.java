package application;
	
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

import application.GUI.HomepageGUI;
import application.GUI.InventoryListGUI;
import application.GUI.LoginGUI;
import application.GUI.RecipeIngredientList_TableViewGUI;
import application.GUI.RegistrationGUI;

/**
 * TODO: Write a description of the class here.
 * 
 * @author Eat_It(Summer 2021 Team)
 */
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try 
		{
			DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
			dbm.connectToDatabase();
			
			LoginGUI loginGUI = new LoginGUI(primaryStage);
			
			RegistrationGUI registrationGUI = new RegistrationGUI(primaryStage, loginGUI);
			
			loginGUI.setRegistrationGUI(registrationGUI);
			
			//START TESTS
//			Recipe recipe = new Recipe("1", "Pasta", "30 min", "10 min", "0");
//			User user = new User("1", "zuber1", "password");
//			RecipeIngredientList_TableViewGUI temp = new RecipeIngredientList_TableViewGUI(user, recipe);
//			primaryStage.setScene(temp.getScene());
			//END TESTS
			
			primaryStage.setScene(loginGUI.getLoginScene());
			
			primaryStage.setTitle("Login Page");
			primaryStage.show();
			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
