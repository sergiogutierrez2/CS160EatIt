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

import application.GUI.HomepageGUI;
import application.GUI.InventoryListGUI;
import application.GUI.LoginGUI;
import application.GUI.RegistrationGUI;




public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try 
		{
			LoginGUI loginGUI = new LoginGUI(primaryStage);
			
			RegistrationGUI registrationGUI = new RegistrationGUI(primaryStage, loginGUI);
			
			loginGUI.setRegistrationGUI(registrationGUI);
			
			primaryStage.setScene(loginGUI.getLoginScene());
			
			primaryStage.setTitle("Login Page");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
