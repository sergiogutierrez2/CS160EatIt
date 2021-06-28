package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
	
	private Connection connection;
	private boolean connected; 
	private String jdbcUrl = "jdbc:sqlite:schema_v1.db";
	private static DatabaseManager singleDBMInstance = new DatabaseManager();
	
	//ensures that there is only one instance of a DatabaseManager object
	private DatabaseManager(){
		connected = false;
	}
	
	public static DatabaseManager getSingleDatabaseManagerInstance()
	{
		return singleDBMInstance;
	}
	
	public boolean connectToDatabase()
	{
		if(connected)
		{
			return true;
		}
		
		try {
			connection = DriverManager.getConnection(jdbcUrl);
			connected = true;
			return connected;
		} catch (SQLException e) {
			System.out.println("Connection Failed! Error Code: " + e.getErrorCode());
			connected = false;
			e.printStackTrace();
			
			return connected;
		}
	}
	
	public void closeConnection()
	{
		try {
			if(connected)
			{
				connection.close();
				connected = false;
			}
			else {
				System.out.println("Database is already disconnected!");
			}
			
		} catch (SQLException e) {
			System.out.println("Failed to Close Connection! Error Code: " + e.getErrorCode());
			e.printStackTrace();
		}
	}
	
	public boolean isCredentialsValid(String username, String password)
	{
		if(!connected)
		{
			connectToDatabase();
		}
		String sql = "SELECT username, pass_word FROM logins where username = ? and pass_word = ?";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
		
			pstmt.setString(1, username);
		
			pstmt.setString(2, password);
			
			ResultSet result;
			
			result = pstmt.executeQuery();
			
			boolean invalidCredentialsFlag = true;
			
			while (result.next()) 
			{
				invalidCredentialsFlag = false;
				String username_db = result.getString("username"); //specified attribute name is "username" in sql db
				String pass_word_db = result.getString("pass_word"); //specified attribute name is "pass_word" in sql db
				
				System.out.println("From DBMS: " + username_db + " | " + pass_word_db + "is VALID credential");
			}
			
			pstmt.close();
			result.close();
			
			if(invalidCredentialsFlag)
			{
				System.out.println("From DBMS: " + username + " | " + password + " is INVALID credential");
			}
			
			return invalidCredentialsFlag;
		} catch (SQLException e) {
			System.out.println("Failed to check credentials: " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}
	
	public void insertCredentials(String username, String password1, String password2)
	{
		System.out.println("Inserting Credentials");
		if(!(password1.equals(password2)))
		{
			System.out.println("Passwords Do Not Match!");
			return;
		}
		
		if(!connected)
		{
			connectToDatabase();
		}
		
		String sql = "insert into logins (username, pass_word) values ('" + username + "', '" + password1 + "')";
		
		try {
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
			statement.close();
		} catch (SQLException e) {
			System.out.println("Failed to insert credentials: " + e.getErrorCode());
			e.printStackTrace();
			
			if(e.getErrorCode() == 19)
			{
				System.out.println("Error: Duplicate Username!");
			}
		}
	}
	
	public void printCredentials()
	{
		System.out.println("Printing Credentials");
		if(!connected)
		{
			connectToDatabase();
		}
		
		String sql = "SELECT * FROM logins";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) 
			{
				 
				Integer user_id = result.getInt("user_id"); //specified attribute name is "user_id" in sql db
				String username_l = result.getString("username"); //specified attribute name is "username" in sql db
				String pass_word_l = result.getString("pass_word"); //specified attribute name is "pass_word" in sql db
				
				System.out.println(user_id + " | " + username_l + " | " + pass_word_l);
				
			}
			
			statement.close();
			result.close();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

}
