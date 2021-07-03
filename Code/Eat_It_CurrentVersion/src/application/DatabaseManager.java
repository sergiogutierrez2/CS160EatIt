package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//a singleton object (only one instance allowed)
public class DatabaseManager {
	
	private Connection connection;
	private boolean connectedStatus; 
	private String jdbcUrl = "jdbc:sqlite:schema_v1.db";
	private static DatabaseManager singleDBMInstance = new DatabaseManager();
	private User user;
	
	//ensures that there is only one instance of a DatabaseManager object
	private DatabaseManager(){
		connectedStatus = false;
	}
	
	public static DatabaseManager getSingleDatabaseManagerInstance()
	{
		return singleDBMInstance;
	}
	
	public void setJdbcUrl(String url)
	{
		jdbcUrl = url;
	}
	
	public String getJdbcUrl()
	{
		return jdbcUrl;
	}
	
	public User getUser()
	{
		return user;
	}
	
	public boolean getConnectedStatus()
	{
		return connectedStatus;
	}
	
	public boolean connectToDatabase()
	{
		if(connectedStatus)
		{
			System.out.println("Already connected");
			return true;
		}
		if( !( jdbcUrl.equals("jdbc:sqlite:schema_v1.db") ) )
		{
			jdbcUrl = "jdbc:sqlite:schema_v1.db";
			System.out.println("Error: Wrong url");
			return false;
		}
		
		try {
			connection = DriverManager.getConnection(jdbcUrl);
			connectedStatus = true;
			return connectedStatus;
		} catch (SQLException e) {
			System.out.println("Connection Failed! Error Code: " + e.getErrorCode());
			connectedStatus = false;
			e.printStackTrace();
			
			return connectedStatus;
		}
	}
	
	//returns true if connection closed success
	public boolean closeConnection()
	{
		try {
			if(connectedStatus)
			{
				connection.close();
				connectedStatus = false;
			}
			else {
				System.out.println("Database is already disconnected!");
			}
			
			return !connectedStatus;
		} catch (SQLException e) {
			System.out.println("Failed to Close Connection! Error Code: " + e.getErrorCode());
			e.printStackTrace();
			return !connectedStatus;
		}
	}
	
	//returns true when credential is valid
	public boolean isCredentialsValid(String username, String password)
	{
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		String sql = "SELECT user_id, username, pass_word FROM logins where username = ? and pass_word = ?";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
		
			pstmt.setString(1, username);
		
			pstmt.setString(2, password);
			
			ResultSet result;
			
			result = pstmt.executeQuery();
			
			boolean invalidCredentialsFlag = true;
			
			while (result.next()) 
			{
				System.out.println("DBMS: Credentials should be valid!");
				invalidCredentialsFlag = false;
				int user_id = result.getInt("user_id");
				String username_db = result.getString("username"); //specified attribute name is "username" in sql db
				String pass_word_db = result.getString("pass_word"); //specified attribute name is "pass_word" in sql db
				
				System.out.println("From DBMS: " + user_id + " | " + username_db + " | " + pass_word_db + " is VALID credential");
				user = new User(String.valueOf(user_id), username_db, pass_word_db);
			}
			
			pstmt.close();
			result.close();
			
			if(invalidCredentialsFlag)
			{
				System.out.println("From DBMS: " + username + " | " + password + " is INVALID credential");
			}
			
			return !invalidCredentialsFlag;
		} catch (SQLException e) {
			System.out.println("Failed to check credentials: " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean containsIngredient(User user, String ingredient)
	{
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		String sql = "SELECT user_id, ingredient_name FROM ingredient where user_id = ? and ingredient_name = ?";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
		
			pstmt.setInt(1, Integer.parseInt(user.getAcc_id()));
		
			pstmt.setString(2, ingredient);
			
			ResultSet result;
			
			result = pstmt.executeQuery();
			
			boolean containsIngredientFlag = false;
			
			while (result.next()) 
			{
				System.out.println("DBMS: Ingredient should be valid!");
				containsIngredientFlag = true;
				int user_id = result.getInt("user_id");
				String ingredient_db = result.getString("ingredient_name"); //specified attribute name is "username" in sql db
				
				
				System.out.println("From DBMS: " + user_id + " | " + ingredient_db);
				
			}
			
			pstmt.close();
			result.close();
			
			if(!containsIngredientFlag)
			{
				System.out.println("From DBMS: " + user.getAcc_id() + " | " + ingredient + " is not contained in DB");
			}
			
			return containsIngredientFlag;
		} catch (SQLException e) {
			System.out.println("Failed to check ingredient: " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean insertCredentials(String username, String password1, String password2)
	{
		System.out.println("Inserting Credentials");
		if(!(password1.equals(password2)))
		{
			System.out.println("Passwords Do Not Match!");
			return false;
		}
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "insert into logins (username, pass_word) values ('" + username + "', '" + password1 + "')";
		
		try {
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
			statement.close();
			
			return true;
		} catch (SQLException e) {
			System.out.println("Failed to insert credentials: " + e.getErrorCode());
			e.printStackTrace();
			
			if(e.getErrorCode() == 19)
			{
				System.out.println("Error: Duplicate Username!");
			}
			return false;
		}
	}
	
	public void deleteCredentials(String username)
	{
		System.out.println("Deleting Credentials");
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "DELETE FROM logins WHERE username = '" + username + "'";
		
		try {
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
			statement.close();
		} catch (SQLException e) {
			System.out.println("Failed to delete credentials: " + e.getErrorCode());
			e.printStackTrace();
			
		}
	}
	
	public boolean insertIngredient(String user_id, String ingredient_name, String expiration_date, String par_amount, String quantity, String quantity_type)
	{
		System.out.println("Inserting Ingredient");
		
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		String sql = "insert into ingredient (user_id, ingredient_name, expiration_date, par_amount, quantity, quantity_type) values ('" + user_id 
				+ "', '" + ingredient_name + "', '" + expiration_date + "', '" + par_amount + "', '" + quantity + "', '" + quantity_type + "')";
		
		try {
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
			statement.close();
			
			return true;
		} catch (SQLException e) {
			System.out.println("Failed to insert ingredient: " + e.getErrorCode());
			e.printStackTrace();
			
			return false;
		}
	}
	
	public void deleteIngredient(User user, String ingredientName)
	{
		System.out.println("Deleting Credentials");
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "DELETE FROM ingredient WHERE user_id = '" + user.getAcc_id() + "' AND ingredient_name = '" + ingredientName + "'";
		
		try {
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
			statement.close();
		} catch (SQLException e) {
			System.out.println("Failed to delete ingredient: " + e.getErrorCode());
			e.printStackTrace();
			
		}
	}
	
	public ObservableList<Item> getCurrentInventory(User user)
	{
		System.out.println("Getting Current Inventory");
		ObservableList<Item> currentInventoryList = FXCollections.observableArrayList();
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "SELECT * FROM ingredient WHERE user_id = '" + user.getAcc_id() + "'";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) 
			{
				
				Integer user_id = result.getInt("user_id"); //specified attribute name is "user_id" in sql db
				String item_num = result.getString("item_num"); //specified attribute name is "username" in sql db
				String ingredient_name = result.getString("ingredient_name"); //specified attribute name is "pass_word" in sql db
				String expiration_date = result.getString("expiration_date");
				Integer par_amount = result.getInt("par_amount");
				Integer quantity = result.getInt("quantity");
				String quantity_type = result.getString("quantity_type");
			
				System.out.println(user_id + " | " + item_num + " | " + ingredient_name
									+ " | " + expiration_date + " | " + par_amount
									+ " | " + quantity + " | " + quantity_type );
				currentInventoryList.add(new Item(item_num.toString(),ingredient_name, expiration_date, quantity.toString(), par_amount.toString(), quantity_type));
			}
			
			statement.close();
			result.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return currentInventoryList;
	}
	
	public void printCredentials()
	{
		System.out.println("Printing Credentials");
		if(!connectedStatus)
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
