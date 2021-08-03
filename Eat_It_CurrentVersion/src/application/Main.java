package application;
	
import application.GUI.LoginGUI;
import application.GUI.RegistrationGUI;
import application.GUI.SettingGUI;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the main class of the application and is used
 * to start up the javafx application.
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
			SettingGUI settingsGUI = new SettingGUI(primaryStage, loginGUI);
			RegistrationGUI registrationGUI = new RegistrationGUI(primaryStage, loginGUI);
			
			loginGUI.setRegistrationGUI(registrationGUI);
			loginGUI.setSettingGUI(settingsGUI);
			
			primaryStage.setScene(loginGUI.getLoginScene());
			
			primaryStage.setTitle("Login Page");
			primaryStage.setX(50);
			primaryStage.setY(50);
			
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
