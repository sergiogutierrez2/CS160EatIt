package application;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The DatabaseManager class is a singleton object (only one instance allowed).
 * @author Eat_It(Summer 2021 Team)
 */
public class DatabaseManager {
	
	private Connection connection;
	private boolean connectedStatus; 
	private final String jdbcUrl = "jdbc:sqlite:" + System.getProperty("user.dir") +  File.separator + "src" + File.separator + "application" + File.separator + "db" + File.separator + "schema_v1.db";
	private static DatabaseManager singleDBMInstance = new DatabaseManager();
	//private User user;
	
	/**
	 * This is the DatabaseManager constructor which 
	 * has a private access modifier to ensure that 
	 * this class is a singleton.
	 * 
	 */
	private DatabaseManager(){
		connectedStatus = false;
	}
	
	/**
	 * This method is used to get the singleton instance of the 
	 * database manager class.
	 * @return Returns the singleton instance of the DatabaseManager class.
	 */
	public static DatabaseManager getSingleDatabaseManagerInstance()
	{
		return singleDBMInstance;
	}
	
	/**
	 * This method returns the path to the Sqlite database.
	 * @return Returns the path in String format. 
	 */
	public String getJdbcUrl()
	{
		return jdbcUrl;
	}
	
	/**
//	 * This method is used to get the user member of the DatabaseManager class.
//	 * @return Returns a User type.
//	 */
//	public User getUser()
//	{
//		return user;
//	}
	
	/**
	 * This method returns a User type built from the 
	 * username passed in, if that username is in the db.
	 * @param username This is the username of the User we want to build.
	 * @return Returns a User type.
	 */
	public User getUser(String username)
	{

		System.out.println("Getting user from username: " + username);
		User user = null;
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "SELECT * FROM logins where username = '" + username + "' LIMIT 1";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) 
			{
				
				Integer user_id = result.getInt("user_id"); //specified attribute name is "user_id" in sql db
				String userName = result.getString("username"); //specified attribute name is "pass_word" in sql db
				String pass_word = result.getString("pass_word");
			
				System.out.println(user_id + " | " + userName + " | " + pass_word);
				user = new User(user_id.toString(), userName, pass_word);
			}
			
			statement.close();
			result.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return user;
		}
		
		
		return user;
	}
	
	/**
	 * This method returns a boolean that is true if the
	 * database is currently connected.
	 * @return Returns true is connect, and false if not connected.
	 */
	public boolean getConnectedStatus()
	{
		return connectedStatus;
	}
	
	/**
	 * This method connects to the database located at the
	 * current JDBC URL.
	 * @return Returns true if connection is set up successful, false if not.
	 */
	public boolean connectToDatabase()
	{
		if(connectedStatus)
		{
			System.out.println("Already connected");
			return true;
		}
//		if( !( jdbcUrl.equals("jdbc:sqlite:schema_v1.db") ) )
//		{
//			jdbcUrl = "jdbc:sqlite:schema_v1.db";
//			System.out.println("Error: Wrong url");
//			return false;
//		}
		
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
	
	/**
	 * This method closes the connection to the db.
	 * @return Returns true if connection closed.
	 */
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
	

	/**
	 * This method is used to check if the provided credentials
	 * are valid. 
	 * @param username This is the String containing the username.
	 * @param password This is the String containing the password.
	 * @return Returns true if credential is valid, false if invalid.
	 */
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
//				user = new User(String.valueOf(user_id), username_db, pass_word_db);
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
	
	/**
	 * This method is used to check if the inventory of the user
	 * already contains the passed in item_num.
	 * @param user This is the user who's inventory we would like to check.
	 * @param item_num This is the item_num that is checked in the inventory list of the user.
	 * @return Returns true is the inventory list of the user has the item_num.
	 */
	public boolean containsItemNum(User user, String item_num)
	{
		System.out.println("check if user containsItemNum: " + item_num);
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		String sql = "SELECT user_id, item_num FROM ingredient where user_id = ? and item_num = ?";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
		
			pstmt.setInt(1, Integer.parseInt(user.getAcc_id()));
		
			pstmt.setInt(2, Integer.parseInt(item_num));
			
			ResultSet result;
			
			result = pstmt.executeQuery();
			
			boolean containsIngredientFlag = false;
			
			while (result.next()) 
			{
				System.out.println("DBMS: Item Number already exists!");
				containsIngredientFlag = true;
				int user_id = result.getInt("user_id");
				int itemNum = result.getInt("item_num"); //specified attribute name is "username" in sql db
				
				
				System.out.println("From DBMS: " + user_id + " | " + itemNum);
				
			}
			
			pstmt.close();
			result.close();
			
			if(!containsIngredientFlag)
			{
				System.out.println("From DBMS: " + user.getAcc_id() + " | " + item_num + " is not contained in DB");
			}
			
			return containsIngredientFlag;
		} catch (SQLException e) {
			System.out.println("Failed to check ingredient: " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This method is used to check if the recipe list of the user 
	 * contains the passed in recipe number.
	 * @param user This is the user who's recipe list we would like to check. 
	 * @param recipe_num This is the recipe_num that is checked in the recipe list of the user.
	 * @return Returns true if the recipe list contains the provided recipe number.
	 */
	public boolean containsRecipeNum(User user, String recipe_num)
	{
		System.out.println("check if user containRecipeNum: " + recipe_num);
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		String sql = "SELECT user_id, recipe_num FROM recipes where user_id = ? and recipe_num = ?";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
		
			pstmt.setInt(1, Integer.parseInt(user.getAcc_id()));
		
			pstmt.setInt(2, Integer.parseInt(recipe_num));
			
			ResultSet result;
			
			result = pstmt.executeQuery();
			
			boolean containsIngredientFlag = false;
			
			while (result.next()) 
			{
				System.out.println("DBMS: Item Number already exists!");
				containsIngredientFlag = true;
				int user_id = result.getInt("user_id");
				int recipeNum = result.getInt("recipe_num"); //specified attribute name is "username" in sql db
				
				
				System.out.println("From DBMS: " + user_id + " | " + recipeNum);
				
			}
			
			pstmt.close();
			result.close();
			
			if(!containsIngredientFlag)
			{
				System.out.println("From DBMS: " + user.getAcc_id() + " | " + recipe_num + " is not contained in DB");
			}
			
			return containsIngredientFlag;
		} catch (SQLException e) {
			System.out.println("Failed to check recipe: " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This method is used if the user contains the item_number in any of their recipe's ingredient list.
	 * @param user This is the user who's recipe list we would like to check. 	 
	 * @param item_num
	 * @return Returns true is user contains the item_number in any of their recipe's ingredient list.
	 */
	public boolean isInIngredientListTable(User user, String item_num)
	{
		System.out.println("check if user has item_num: " + item_num + ", any where in ingredient list table.");
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		String sql = "SELECT user_id, item_num FROM ingredientlist where user_id = ? and item_num = ?";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
		
			pstmt.setInt(1, Integer.parseInt(user.getAcc_id()));
		
			pstmt.setInt(2, Integer.parseInt(item_num));
			
			ResultSet result;
			
			result = pstmt.executeQuery();
			
			boolean containsIngredientFlag = false;
			
			while (result.next()) 
			{
				System.out.println("DBMS: Item Number exists!");
				containsIngredientFlag = true;
				int user_id = result.getInt("user_id");
				int itemNum = result.getInt("item_num"); 
				
				System.out.println("From DBMS: " + user_id + " | " + itemNum);
				
			}
			
			pstmt.close();
			result.close();
			
			if(!containsIngredientFlag)
			{
				System.out.println("From DBMS: " + user.getAcc_id() + " | " + item_num + " is not contained in DB");
			}
			
			return containsIngredientFlag;
		} catch (SQLException e) {
			System.out.println("Failed to check ingredientlist for item num. error: " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This method checks if a user's recipe contains the provided item number.
	 * @param user This is the user who's recipe we would like to check. 	 
	 * @param recipe_num This is the recipe we want to check.
	 * @param item_num This is the item we want to check is present in the recipe.
	 * @return Returns true if recipe contains recipe ingredient.
	 */
	public boolean containsRecipeIngredient(User user, String recipe_num, String item_num)
	{
		System.out.println("START checking if user's recipe num: " + recipe_num + ", contains ingredient: " + item_num);
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		String sql = "SELECT user_id, recipe_num, item_num FROM ingredientlist where user_id = ? and recipe_num = ? and item_num = ?";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
		
			pstmt.setInt(1, Integer.parseInt(user.getAcc_id()));
		
			pstmt.setInt(2, Integer.parseInt(recipe_num));
			
			pstmt.setInt(3, Integer.parseInt(item_num));
			
			ResultSet result;
			
			result = pstmt.executeQuery();
			
			boolean containsIngredientFlag = false;
			
			while (result.next()) 
			{
				System.out.println("DBMS: Ingredient already exists in recipe");
				containsIngredientFlag = true;
				
				int user_id = result.getInt("user_id");
				int recipeNum = result.getInt("recipe_num");
				int itemNum = result.getInt("item_num");
				
				System.out.println("From DBMS: " + user_id + " | " + recipeNum + " | " + itemNum);
			}
			
			pstmt.close();
			result.close();
			
			if(!containsIngredientFlag)
			{
				System.out.println("From DBMS: " + user.getAcc_id() + " | " + recipe_num + " | " + item_num + " is not contained in DB");
			}
			
			System.out.println("END containsRecipeIngredient()");
			
			return containsIngredientFlag;
			
		} catch (SQLException e) {
			System.out.println("Failed to check recipe: " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This method checks if a user's recipe contains the provided set number.
	 * @param user This is the user who's recipe we would like to check. 	 
	 * @param recipe_num This is the recipe we want to check.
	 * @param step_num This is the recipe step number we want to check is present in the recipe.
	 * @return Returns true if the recipe contains the recipe step number.
	 */
	public boolean containsRecipeStep(User user, String recipe_num, String step_num)
	{
		System.out.println("checking if user's recipe steps table for recipe: " + recipe_num + ", contains step number: " + step_num);
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		String sql = "SELECT user_id, recipe_num, step_num FROM recipeSteps where user_id = ? and recipe_num = ? and step_num = ?";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
		
			pstmt.setInt(1, Integer.parseInt(user.getAcc_id()));
		
			pstmt.setInt(2, Integer.parseInt(recipe_num));
			
			pstmt.setInt(3, Integer.parseInt(step_num));
			
			ResultSet result;
			
			result = pstmt.executeQuery();
			
			boolean containsIngredientFlag = false;
			
			while (result.next()) 
			{
				System.out.println("DBMS: Recipe Step already exists in recipe");
				containsIngredientFlag = true;
				
				int user_id = result.getInt("user_id");
				int recipeNum = result.getInt("recipe_num"); //specified attribute name is "username" in sql db
				String stepNum = result.getString("step_num");
				
				System.out.println("From DBMS: " + user_id + " | " + recipeNum + " | " + stepNum);
			}
			
			pstmt.close();
			result.close();
			
			if(!containsIngredientFlag)
			{
				System.out.println("From DBMS: " + user.getAcc_id() + " | " + recipe_num + " | " + step_num + " is not contained in DB");
			}
			
			return containsIngredientFlag;
		} catch (SQLException e) {
			System.out.println("Failed to check recipe: " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This method is used to insert a credential in the database.
	 * @param username The string with the username we want to insert in the database.
	 * @param password1 The string with the password we want to insert in the database.
	 * @param password2 The string with the confirmation password we want to insert in the database.
	 * @return Returns true if the credential was successfully inserted.
	 */
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
	
	/**
	 * This method is used to delete the credential passed in.
	 * @param username This is a String containing the username of the credential we want to remove from the database.
	 */
	public void deleteCredentials(User user)
	{
		System.out.println("Deleting Credentials");
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "DELETE FROM logins WHERE user_id = '" + user.getAcc_id() + "'";
		
		try {
			
			deleteAllRecipes(user);
			deleteAllIngredientsForUser(user);
			
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
			statement.close();
			
			
		} catch (SQLException e) {
			System.out.println("Failed to delete credentials: " + e.getErrorCode());
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to delete a credential from the database with 
	 * the provided user id number.
	 * @param User This is a User object of the Credential to be deleted.
	 */
	public void deleteCredentialsWithUserId(User user)
	{
		System.out.println("Deleting Credentials");
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "DELETE FROM logins WHERE user_id = '" + user.getAcc_id() + "'";
		
		try {
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
			statement.close();
			
			deleteAllRecipes(user);
			deleteAllIngredientsForUser(user);
		} catch (SQLException e) {
			System.out.println("Failed to delete credentials: " + e.getErrorCode());
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to update a credential from the database with 
	 * the provided username.
	 * @param user_id This is a String user_id.
	 * @Return Returns true is credential was updated successfully, false if not.
	 */
	public boolean updateCredentialUsername(String user_id, String user_name)
	{
		System.out.println("Updating Username");
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		// First check if provided username already exists
		if(usernameExists(user_id, user_name))
		{
			//username already exists
			return false;
		}
		else
		{
			try {
				Statement statement = connection.createStatement();
				
				String sql_Ex = "UPDATE logins SET username = '" + user_name + "' WHERE user_id = '" + user_id + "'";
				
				statement.executeUpdate(sql_Ex);
				
				statement.close();
				
				return true;
			} catch (SQLException e) {
				System.out.println("Failed to update credentials: " + e.getErrorCode());
				
				e.printStackTrace();
				
				return false;
			}
		}
	}
	
	/**
	 * This method is used to update a credential from the database with 
	 * the provided password.
	 * @param user_id This is a String with the user_id of the credential we want to replace.
	 * @param password This is the String with the new password we want to replace old password with.
	 * @Return Returns true is credential was updated successfully, false if not.
	 */
	public boolean updateCredentialPassword(String user_id, String password)
	{
		System.out.println("Updating password");
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		
		try {
			Statement statement = connection.createStatement();
			
			String sql_Ex = "UPDATE logins SET pass_word = '" + password + "' WHERE user_id = '" + user_id + "'";
			
			statement.executeUpdate(sql_Ex);
			
			statement.close();
			
			return true;
		} catch (SQLException e) {
			System.out.println("Failed to update credentials: " + e.getErrorCode());
			
			e.printStackTrace();
			
			return false;
		}
	}
	
	/**
	 * This method is used to update a credential from the database with 
	 * the provided username.
	 * @param user_id This is a String username.
	 * @Return Returns true is credential was updated successfully, false if not.
	 */
	public boolean usernameExists(String user_id, String username)
	{
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		String sql = "SELECT user_id, username, pass_word FROM logins where username = ? and user_id <> ?";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
		
			pstmt.setString(1, username);
			
			pstmt.setInt(2, Integer.parseInt(user_id));
			
			ResultSet result;
			
			result = pstmt.executeQuery();
			
			boolean invalidCredentialsFlag = true;
			
			while (result.next()) 
			{
				System.out.println("DBMS: Credentials should be valid!");
				invalidCredentialsFlag = false;
				int userId = result.getInt("user_id");
				String username_db = result.getString("username"); //specified attribute name is "username" in sql db
				String pass_word_db = result.getString("pass_word"); //specified attribute name is "pass_word" in sql db
				
				System.out.println("From DBMS: " + userId + " | " + username_db + " | " + pass_word_db + " is VALID credential");
				//user = new User(String.valueOf(userId), username_db, pass_word_db);
			}
			
			pstmt.close();
			result.close();
			
			if(invalidCredentialsFlag)
			{
				System.out.println("From DBMS: " + username + " is INVALID credential");
			}
			
			return !invalidCredentialsFlag;
		} catch (SQLException e) {
			System.out.println("Failed to check credentials: " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This method is used to insert an ingredient intp the database.
	 * @param user The user who's inventory we want to insert the ingredient into.
	 * @param item_num The item number of the ingredient we are inserting.
	 * @param ingredient_name This is a String of the ingredient name.
	 * @param expiration_date This is a String of the expiration date.
	 * @param par_amount This is a String of the par_amount.
	 * @param quantity This is a String of the quantity.
	 * @param quantity_type This is a String of the quantity_type.
	 * @return Returns true if the ingredient insertion was successful.
	 */
	public boolean insertIngredient(User user, String item_num, String ingredient_name, 
									String expiration_date, String par_amount, String quantity, String quantity_type)
	{
		System.out.println("Inserting Ingredient");
		
		if( containsItemNum(user, item_num) )
		{
			return false;
		}
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		String sql = "insert into ingredient (user_id, item_num, ingredient_name, expiration_date, par_amount, quantity, quantity_type) values ('" 
				+ user.getAcc_id() + "', '" + item_num + "', '" + ingredient_name + "', '" + expiration_date + "', '" 
				+ par_amount + "', '" + quantity + "', '" + quantity_type + "')";
		
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
	
	/**
	 * This method is used to delete the ingredient from the inventory list of the user.
	 * @param user The user who's inventory we want to delete the ingredient from.
	 * @param item_num This is the item number of the ingredient we want to delete.
	 */
	public void deleteIngredient(User user, String item_num)
	{
		System.out.println("Deleting Ingredient " + item_num );
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "DELETE FROM ingredient WHERE user_id = '" + user.getAcc_id() + "' AND item_num = '" + item_num + "'";
		
		try {
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
			statement.close();
		} catch (SQLException e) {
			System.out.println("Failed to delete ingredient: " + e.getErrorCode());
			e.printStackTrace();
			
		}
	}
	
	public void deleteAllIngredientsForUser(User user_1)
	{
		System.out.println( "Deleting All Ingredients from User: " + user_1.getUsername() );
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "DELETE FROM ingredient WHERE user_id = '" + user_1.getAcc_id() + "'";
		
		try 
		{
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
			statement.close();
		} catch (SQLException e) {
			System.out.println("Failed to delete all ingredients for user: " + e.getErrorCode());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This method is used to insert a recipe into the database.
	 * @param user The user who's recipe list we want to insert the recipe into.
	 * @param recipe_num This is a String of the recipe number.
	 * @param recipe_name This is a String of the recipe name.
	 * @param cook_time This is a String of the recipe cook time.
	 * @param prep_time This is a String of the recipe prep time.
	 * @param executable This is a String of the recipe executable attribute (0 or 1).
	 * @return Returns true if insertion is successful.
	 */
	public boolean insertRecipe(User user, String recipe_num, String recipe_name, 
			String cook_time, String prep_time, String executable)
	{
		System.out.println("Inserting Recipe");

		if( containsRecipeNum(user, recipe_num) )
		{
			return false;
		}

		if(!connectedStatus)
		{
			connectToDatabase();
		}
		String sql = "insert into recipes (user_id, recipe_num, recipe_name, cook_time, prep_time, executable) values ('"
				+ user.getAcc_id() + "', '" + recipe_num + "', '" + recipe_name + "', '" + cook_time + "', '" 
				+ prep_time + "', '" + executable + "')";

		try {

			Statement statement = connection.createStatement();

			statement.execute(sql);

			statement.close();

			return true;

		} catch (SQLException e) {
			System.out.println("Failed to insert Recipe: " + e.getErrorCode());
			e.printStackTrace();

			return false;
		}
	}
	
	/**
	 * This method is used to insert a recipe into the database.
	 * @param user The user who's recipe list we want to insert the recipe into.
	 * @param recipe The recipe that should be inserted into the recipe list.
	 * @return
	 */
	public boolean insertRecipe(User user, Recipe recipe)
	{
		System.out.println("Inserting Recipe");

		if( containsRecipeNum(user, recipe.getRecipe_num()) )
		{
			return false;
		}

		if(!connectedStatus)
		{
			connectToDatabase();
		}
		String sql = "insert into recipes (user_id, recipe_num, recipe_name, cook_time, prep_time, executable) values ('"
				+ user.getAcc_id() + "', '" + recipe.getRecipe_num() + "', '" + recipe.getRecipe_name() + "', '" + recipe.getCook_time() + "', '" 
				+ recipe.getPrep_time() + "', '" + recipe.getExecutable() + "')";

		try {

			Statement statement = connection.createStatement();

			statement.execute(sql);

			statement.close();

			return true;

		} catch (SQLException e) {
			System.out.println("Failed to insert Recipe: " + e.getErrorCode());
			e.printStackTrace();

			return false;
		}
	}
	
	/**
	 * This method is used to delete the recipe passed in.
	 * @param user The user who's recipe we want to delete from the recipe list.
	 * @param recipe_num The recipe number of recipe we want to delete.
	 */
	public void deleteRecipe(User user, String recipe_num)
	{
		System.out.println("Deleting recipe from recipe_num: " + recipe_num );
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "DELETE FROM recipes WHERE user_id = '" + user.getAcc_id() + "' AND recipe_num = '" + recipe_num + "'";
		
		try {
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
			statement.close();
			
			deleteAllRecipeIngredient(user, recipe_num);
		} catch (SQLException e) {
			System.out.println("Failed to delete recipe: " + e.getErrorCode());
			e.printStackTrace();
			
		}
	}
	
	/**
	 * This method is for deleting all of the recipes as well as 
	 * deleting all of the ingredients and steps attached to the recipes.
	 * @param user This is the user that we want to delete all of the recipes from.
	 */
	public void deleteAllRecipes(User user_1)
	{
		System.out.println( "Deleting All Recipes from User: " + user_1.getUsername() );
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "DELETE FROM recipes WHERE user_id = '" + user_1.getAcc_id() + "'";
		
		ObservableList<Recipe> recipeList = getCurrentRecipeList(user_1);
		if(recipeList != null)
		{
			for(Recipe recipe : recipeList)
			{
				deleteAllRecipeIngredient(user_1, recipe.getRecipe_num());
				deleteAllRecipeSteps(user_1, recipe.getRecipe_num());
			}
			
			try {
				Statement statement = connection.createStatement();
				
				statement.execute(sql);
				
				statement.close();
			} catch (SQLException e) {
				System.out.println("Failed to delete all recipes: " + e.getErrorCode());
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * This method is used to insert an ingredient into the provided recipe.
	 * @param user This is the user who's recipe we want to add an ingredient to.
	 * @param recipe_num This is the recipe number that we want to add an ingredient to.
	 * @param item_num This is the ingredient we want to add to the recipe.
	 * @param ingredient_name This is the ingredient name we want to add to recipe.
	 * @param quantity This is the quantity name we want to add to recipe.
	 * @return Returns true if insertion was successful, false if not.
	 */
	public boolean insertRecipeIngredient(User user, String recipe_num, String item_num, String ingredient_name, String quantity)
	{
		System.out.println("Inserting ingredient into Recipe: " + recipe_num);

		if( containsRecipeIngredient(user, recipe_num, item_num) )
		{
			return false;
		}

		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "insert into ingredientlist (user_id, recipe_num, item_num, ingredient_name, amount) values ('"
				+ user.getAcc_id() + "', '" + recipe_num + "', '" + item_num + "', '" + ingredient_name  + "', '" + quantity + "')";

		try {

			Statement statement = connection.createStatement();

			statement.execute(sql);

			statement.close();

			return true;

		} catch (SQLException e) {
			System.out.println("Failed to insert Recipe: " + e.getErrorCode());
			e.printStackTrace();

			return false;
		}
	}
	
	/**
	 * This method is used to delete an ingredient from the provided recipe.
	 * @param user This is the user who's recipe want to delete the ingredient from.
	 * @param recipe_num This is the recipe that we want to delete the ingredient from.
	 * @param item_num This is the item number we want to delete the recipe's ingredient list.
	 */
	public void deleteRecipeIngredient(User user, String recipe_num, String item_num)
	{
		System.out.println( "Deleting Ingredient from recipe: " + recipe_num );
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "DELETE FROM ingredientlist WHERE user_id = '" + user.getAcc_id() + "' AND recipe_num = '" + recipe_num 
						+ "' AND item_num = '" + item_num + "'";
		
		try {
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
			statement.close();
		} catch (SQLException e) {
			System.out.println("Failed to delete ingredient from recipe ingredient list: " + e.getErrorCode());
			e.printStackTrace();
		}
	}
	
	/**
	 * Deletes all recipe ingredients attached to recipe_num;
	 * @param user This is user who's recipe we want to delete all ingredient from.
	 * @param recipe_num This is the recipe number we want to delete all ingredients from.
	 */
	public void deleteAllRecipeIngredient(User user, String recipe_num)
	{
		System.out.println( "Deleting Ingredients from recipe_num: " + recipe_num );
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "DELETE FROM ingredientlist WHERE user_id = '" + user.getAcc_id() + "' AND recipe_num = '" + recipe_num + "'";
		
		try {
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
			statement.close();
		} catch (SQLException e) {
			System.out.println("Failed to delete ingredient from recipe ingredient list: " + e.getErrorCode());
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to insert a recipe step into a provided recipe.
	 * @param user This is the user's who's recipe we want to add a step to.
	 * @param recipe_num This is the recipe number where we want to add a step to.
	 * @param step_num This is the step number that we want to add to the recipe.
	 * @param step_desc This is the step description that we want to add to the recipe.
	 * @return Returns true is insertion is successful, false if not.
	 */
	public boolean insertRecipeSteps(User user, String recipe_num, String step_num, String step_desc)
	{
		System.out.println("Inserting step into RecipeSteps: " + recipe_num);

		if( containsRecipeStep(user, recipe_num, step_num) )
		{
			return false;
		}

		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "insert into recipeSteps (user_id, recipe_num, step_num, step_desc) values ('"
				+ user.getAcc_id() + "', '" + recipe_num + "', '" + step_num  + "', '" + step_desc + "')";

		try {

			Statement statement = connection.createStatement();

			statement.execute(sql);

			statement.close();

			return true;

		} catch (SQLException e) {
			System.out.println("Failed to insert Recipe: " + e.getErrorCode());
			e.printStackTrace();

			return false;
		}
	}
	
	/**
	 * This method is used to delete a recipe step from a provided recipe.
	 * @param user This is the user's who recipe we want to delete a step from.
	 * @param recipe_num This is the recipe want want to delete a step from.
	 * @param step_num This is the step we want to delete.
	 */
	public void deleteRecipeSteps(User user, String recipe_num, String step_num)
	{
		System.out.println( "Deleting recipeStep: " + step_num + ", from recipe: " + recipe_num );
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "DELETE FROM recipeSteps WHERE user_id = '" + user.getAcc_id() +  "' AND recipe_num = '" + recipe_num 
						+ "' AND step_num = '" + step_num + "'";
		
		try {
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
			statement.close();
		} catch (SQLException e) {
			
			System.out.println("Failed to delete step from recipeStep Table: " + e.getErrorCode());
			
			e.printStackTrace();
		}
	}
	
	public void deleteAllRecipeSteps(User user, String recipe_num)
	{
		System.out.println( "Deleting All Steps from recipe_num: " + recipe_num );
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "DELETE FROM recipeSteps WHERE user_id = '" + user.getAcc_id() + "' AND recipe_num = '" + recipe_num + "'";
		
		try {
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
			statement.close();
		} catch (SQLException e) {
			System.out.println("Failed to delete steps from recipeSteps list: " + e.getErrorCode());
			
			e.printStackTrace();
			
		}
	}
	
	/**
	 * This method is used to auto-generate the recipe number by finding the current
	 * largest recipe number in the database and returning that number plus one.
	 * @param user This is the user who's recipe list we need to check.
	 * @return Returns the next recipe number in String format.
	 */
	public String autogenerateRecipeNum(User user)
	{
		System.out.println("autogenerateItemNum()");
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		int autoGenRecipeNum = 1;
		
		String sql = "select recipe_num from recipes where user_id = '" + user.getAcc_id() + "' ORDER BY recipe_num DESC";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) 
			{
				
				
				String item_num = result.getString("recipe_num"); //specified attribute name is "username" in sql db
			
				System.out.println(item_num );
				autoGenRecipeNum = Integer.parseInt(item_num) + 1;
				
				return String.valueOf(autoGenRecipeNum);
			}
			
			statement.close();
			result.close();
			
			return String.valueOf(autoGenRecipeNum);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
		return String.valueOf(autoGenRecipeNum);
	}
	
	/**
	 * This method is used to auto-generate the item number by finding the current
	 * largest item number in the database and returning that number plus one.
	 * @param user This is the user who's inventory list we need to check.
	 * @return Returns the next item number in String format.
	 */
	public String autogenerateItemNum(User user)
	{
		System.out.println("autogenerateItemNum()");
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		int autoGenItemNum = 1;
		
		String sql = "select item_num from ingredient where user_id = '" + user.getAcc_id() + "' ORDER BY item_num DESC";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) 
			{
				
				
				String item_num = result.getString("item_num"); //specified attribute name is "username" in sql db
			
				System.out.println(item_num );
				autoGenItemNum = Integer.parseInt(item_num) + 1;
				
				return String.valueOf(autoGenItemNum);
			}
			
			statement.close();
			result.close();
			
			return String.valueOf(autoGenItemNum);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
		return String.valueOf(autoGenItemNum);
	}
	
	/**
	 * This method is used to return a list of the credentials
	 * in the database.
	 * 
	 * 	CREATE TABLE logins (
	 * 		user_id INTEGER PRIMARY KEY autoincrement,
	 * 		username varchar(20) NOT NULL UNIQUE,
	 *  	pass_word varchar(20) NOT NULL
	 * 	);
	 * 
	 * @return Returns an observable list of Type User.
	 */
	public ObservableList<User> getCurrentCredentials()
	{
		System.out.println("Getting Current Inventory");
		ObservableList<User> currentCredentials = FXCollections.observableArrayList();
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "SELECT * FROM logins ORDER BY user_id ASC";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) 
			{
				
				Integer user_id = result.getInt("user_id"); //specified attribute name is "user_id" in sql db
				String username = result.getString("username"); //specified attribute name is "pass_word" in sql db
				String pass_word = result.getString("pass_word");
			
				System.out.println(user_id + " | " + username + " | " + pass_word);
				currentCredentials.add( new User(user_id.toString(),username, pass_word) );
			}
			
			statement.close();
			result.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return currentCredentials;
	}
	
	/**
	 * This method returns the current inventory list in the form of an observable list.
	 * @param user This is the user who's inventory we want to return.
	 * @return Returns an observable list of type Item.
	 */
	public ObservableList<Item> getCurrentInventory(User user)
	{
		System.out.println("Getting Current Inventory");
		ObservableList<Item> currentInventoryList = FXCollections.observableArrayList();
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "SELECT * FROM ingredient WHERE user_id = '" + user.getAcc_id() + "' ORDER BY item_num ASC";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) 
			{
				
				Integer user_id = result.getInt("user_id"); //specified attribute name is "user_id" in sql db
				Integer item_num = result.getInt("item_num"); //specified attribute name is "username" in sql db
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
			e.printStackTrace();
		}
		
		
		return currentInventoryList;
	}
	
	/**
	 * This method returns the current recipe list in the form of an observable list.
	 * @param user This is the user who's recipe list we want to return.
	 * @return Returns an observable list of type Recipe.
	 */
	public ObservableList<Recipe> getCurrentRecipeList(User user)
	{
		System.out.println("Getting Recipe List for user: " + user.getUsername());
		ObservableList<Recipe> recipeList = FXCollections.observableArrayList();
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "SELECT * FROM recipes WHERE user_id = '" + user.getAcc_id() + "'";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) 
			{
				
				Integer user_id = result.getInt("user_id"); //specified attribute name is "user_id" in sql db
				Integer recipe_num = result.getInt("recipe_num"); //specified attribute name is "username" in sql db
				String recipe_name = result.getString("recipe_name"); //specified attribute name is "pass_word" in sql db
				String cook_time = result.getString("cook_time");
				String prep_time = result.getString("prep_time");
				Integer executable = result.getInt("executable");
			
				System.out.println(user_id + " | " + recipe_num + " | " + recipe_name
									+ " | " + cook_time + " | " + prep_time
									+ " | " + executable );
				recipeList.add(new Recipe( recipe_num.toString(), recipe_name, cook_time, prep_time, executable.toString() ) );
			}
			
			statement.close();
			result.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return recipeList;
	}
	
	/**
	 * This method returns the current ingredient list of the provided recipe in the form of an observable list.
	 * @param user This is the user who's recipe ingredient list we want to return.
	 * @param recipe This is the recipe that we want to get the ingredient list from.
	 * @return Returns an observable list of type RecipeItem.
	 */
	public ObservableList<RecipeItem> getRecipesIngredientList(User user, Recipe recipe)
	{
		System.out.println("Getting Ingredient List for Recipe: " + recipe.getRecipe_name());
		ObservableList<RecipeItem> ingredientList = FXCollections.observableArrayList();
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "SELECT * FROM ingredientlist WHERE user_id = '" + user.getAcc_id() + "' AND recipe_num = '" + recipe.getRecipe_num() + "'";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) 
			{
				Integer user_id = result.getInt("user_id");
				Integer recipe_num = result.getInt("recipe_num"); //specified attribute name is "username" in sql db
				Integer item_sum = result.getInt("item_num");
				String ingredient_name = result.getString("ingredient_name"); //specified attribute name is "pass_word" in sql db
				Integer amount = result.getInt("amount");
			
				System.out.println( user_id.toString() + " | " + recipe_num + " | " + ingredient_name + " | " + amount);
				ingredientList.add(new RecipeItem( recipe_num.toString(), item_sum.toString(), ingredient_name, amount.toString() ) );
			}
			
			statement.close();
			result.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return ingredientList;
	}
	
	/**
	 * This method returns the recipe's step list of the provided recipe in the form of an observable list.
	 * @param user This is the user who's recipe step list we want to return.
	 * @param recipe This is the recipe that we want to get the step list from.
	 * @return Returns an observable list of type RecipeStep.
	 */
	public ObservableList<RecipeStep> getRecipesStepList(User user, Recipe recipe)
	{
		System.out.println("Getting Recipe Step List for Recipe: " + recipe.getRecipe_name());
		ObservableList<RecipeStep> ingredientList = FXCollections.observableArrayList();
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "SELECT * FROM recipeSteps WHERE user_id = '" + user.getAcc_id() + "' AND recipe_num = '" + recipe.getRecipe_num() + "'";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) 
			{
				Integer user_id = result.getInt("user_id");
				Integer recipe_num = result.getInt("recipe_num"); //specified attribute name is "username" in sql db
				Integer step_num = result.getInt("step_num"); //specified attribute name is "pass_word" in sql db
				String step_desc = result.getString("step_desc");
			
				System.out.println( user_id.toString() + " | " + recipe_num + " | " + step_num + " | " + step_desc);
				ingredientList.add(new RecipeStep( recipe_num.toString(), step_num.toString(), step_desc ) );
			}
			
			statement.close();
			result.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return ingredientList;
	}
	
	/**
	 * This method takes in a boolean and if true returns those recipes that are executable. If boolean is false
	 * then returns the recipes are are not executable.
	 * @param execOrNot This boolean determines whether the list is the executable recipes or not executable recipes.
	 * @return Returns an ObservableList containing desired recipes.
	 */
	public ObservableList<Recipe> getExecutableRecipes(User user, boolean execOrNot)
	{
		System.out.println("Getting Executable Recipe List for user: " + user.getUsername());
		ObservableList<Recipe> recipeList = FXCollections.observableArrayList();
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "SELECT * FROM recipes WHERE user_id = '" + user.getAcc_id() + "'";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) 
			{
				
				Integer user_id = result.getInt("user_id"); //specified attribute name is "user_id" in sql db
				Integer recipe_num = result.getInt("recipe_num"); //specified attribute name is "username" in sql db
				String recipe_name = result.getString("recipe_name"); //specified attribute name is "pass_word" in sql db
				String cook_time = result.getString("cook_time");
				String prep_time = result.getString("prep_time");
				Integer executable = result.getInt("executable");
			
				System.out.println(user_id + " | " + recipe_num + " | " + recipe_name
									+ " | " + cook_time + " | " + prep_time
									+ " | " + executable );
				if(execOrNot)
				{
					if(executable.toString().equals("1"))
					{
						recipeList.add(new Recipe( recipe_num.toString(), recipe_name, cook_time, prep_time, executable.toString() ) );
					}
				}
				else
				{
					if(executable.toString().equals("0"))
					{
						recipeList.add(new Recipe( recipe_num.toString(), recipe_name, cook_time, prep_time, executable.toString() ) );
					}
				}
			}
			
			statement.close();
			result.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return recipeList;
	}
	
	/**
	 * This method is used to get the missing ingredients needed to execute the provided recipe.
	 * The recipe's ingredient list is cross checked against the inventory list.
	 * @param user This is the user who's inventory we want to check.
	 * @param recipe This is the recipe who's ingredient list we want to check.
	 * @return Returns an ArrayList of type RecipeItem.
	 */
	public ArrayList<RecipeItem> getMissingIngredientListForRecipe(User user, Recipe recipe)
    {
        ArrayList<RecipeItem> res = new ArrayList<RecipeItem>();
        //we want to get recipe's ingredients in a list
        ObservableList<Item> currInventory = getCurrentInventory(user);
        
        Calendar today = Calendar.getInstance();
        today.clear(Calendar.HOUR); today.clear(Calendar.MINUTE); today.clear(Calendar.SECOND);
        Date todayDate = today.getTime();
        
        for( RecipeItem recipeItem : getRecipesIngredientList(user, recipe) )
        {
            for(Item item : currInventory)
            {
                if(item.getItem_num().equals(recipeItem.getItem_num()))
                {
                    System.out.println("item exp_date: '" + item.getItem_Exp() + "'");
                    Date date1 = new Date();
                    try {
                        
                        date1 = new SimpleDateFormat("MM/dd/yyyy").parse(item.getItem_Exp());
                        } 
                    catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        } 
                        
                    System.out.println("todayDate: " + todayDate.toString());
                    System.out.println("exp date: " + date1.toString());
                    int inventoryQuantity = Integer.parseInt(item.getItem_Quantity());
                    int recipeItemQuantity = Integer.parseInt(recipeItem.getItem_quantity());
                    if(date1.compareTo(todayDate) < 0 || 
                            (inventoryQuantity < recipeItemQuantity) )
                    {
                        if (inventoryQuantity < recipeItemQuantity)
                        
                        //make recipe item contain missing quantity 
                        recipeItem.setItem_quantity(Integer.toString(recipeItemQuantity - inventoryQuantity));
                        
                        else if(date1.compareTo(todayDate) > 0 )
                            recipeItem.setItem_quantity(Integer.toString(recipeItemQuantity - 0));
                        res.add(recipeItem);
                    }
                }
            }
            
            
        }
        System.out.println("returning " + res.toString());
        
        return res;
        //then we want to get the currentInventory list
        
        
        //them we compare the expiration date to current day and quantity 
    }
	
	/**
	 * This method changes the recipe list to update the executable attribute.
	 * @param user This is the user who's inventory and recipe ingredient list we want to cross check.
	 */
	public void updateExecutableRecipes(User user)
	{
		System.out.println("Updating Executable Recipe List for user: " + user.getUsername());
		ObservableList<Recipe> recipeList = FXCollections.observableArrayList();
		ObservableList<RecipeItem> ingredientList = FXCollections.observableArrayList();
		
		ObservableList<Item> userItems = FXCollections.observableArrayList();
		userItems = getCurrentInventory(user);
		//variable to keep track of executable
		Boolean isExecutable = false;
		
		//accessing today's date
		Calendar today = Calendar.getInstance();
		today.clear(Calendar.HOUR); today.clear(Calendar.MINUTE); today.clear(Calendar.SECOND);
		Date todayDate = today.getTime();
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
			recipeList = getCurrentRecipeList(user);
			try
			{
			Statement statement = connection.createStatement();	
				for (Recipe currRecip: recipeList) 
				{
					ingredientList = getRecipesIngredientList(user, currRecip);//all the ingredients for that recipe
					
					for (RecipeItem currRecipeItem : ingredientList) 
					{
						//userItems = inventoryList for that user declared above
						for (Item userItem : userItems)
						{
						
							// creating a date object for expiration date
							Date date1 = new Date();
							//System.out.println("user item: " + userItem.getItem_name() + " has exp date: " + userItem.getItem_Exp());
							try {
								
								date1 = new SimpleDateFormat("MM/dd/yyyy").parse(userItem.getItem_Exp());
								//System.out.println("exp date of " + userItem.getItem_name() + ": " + date1.toString());
								} 
							catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								} 
							
							  if( Integer.parseInt(currRecipeItem.getItem_num()) == Integer.parseInt(userItem.getItem_num())) 
								{
									if ( Integer.parseInt(currRecipeItem.getItem_quantity()) <= Integer.parseInt( userItem.getItem_Quantity()) 
																		&& date1.compareTo(todayDate) > 0  ) //todayDate = today's date
									{
										System.out.println(currRecip.getRecipe_name() + " is still executable! b/c " + userItem.getItem_name());
										isExecutable = true;
									}
									else
									{
										System.out.println(currRecip.getRecipe_name() + " is no longer	 executable! b/c " + userItem.getItem_name());

										isExecutable = false;
										currRecip.addItemToMissingList(currRecipeItem);
										break;
									}
								}
						}
						if(!isExecutable)
						{
							break;
						}
					
					}
					
					if ( isExecutable ) 
					{
						String sql_Ex = "UPDATE recipes SET executable = '1' WHERE recipe_num = '" + currRecip.getRecipe_num() 
						+"' and user_id = '" + user.getAcc_id() + "'" ;
						statement.executeUpdate(sql_Ex);
					}	
					else
					{	
						String sql_Ex = "UPDATE recipes SET executable = '0' WHERE recipe_num = '" + currRecip.getRecipe_num()
						+"' and user_id = '" + user.getAcc_id() + "'" ;
						statement.executeUpdate(sql_Ex);
					}
						
					statement.close();
				
				}
			}
				
				catch (SQLException e) 
					{
						e.printStackTrace();
						}
			
			
			return;
	}
	
	/**
	 * This method is used to print the credentials from the database.
	 */
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
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to print an observable list of type Recipe.
	 * @param recipeList This is the list we want to print.
	 */
	public void printRecipeObservableList(ObservableList<Recipe> recipeList)
	{
		for(Recipe tempR : recipeList)
			{
				System.out.println(tempR.toString());
			}
	}

	/**
	 * This method is used to execute a given recipe. This means that 
	 * the inventory list's quantities will be changed according to the
	 * quantities of the recipe's ingredient list.
	 * @param user The user.
	 * @param recipe The recipe of the user.
	 */
	public void execRecipe(User user, Recipe recipe) {

        if(!connectedStatus)
        {
            connectToDatabase();
        }
        //get invetory list 
        ObservableList<Item> currentInventory = getCurrentInventory(user);

        //get recipe ingredient list
        ObservableList<RecipeItem> currentRecipeingredients = getRecipesIngredientList(user,recipe);


        Statement statement;
        try {
            for(Item item:currentInventory ){
                for(RecipeItem recipeitem:currentRecipeingredients) {
                    if(item.getItem_num().equals(recipeitem.getItem_num())) {


                        statement = connection.createStatement();

                        int itemQuantity = Integer.parseInt(item.getItem_Quantity());
                        int recipeitemQuantity = Integer.parseInt(recipeitem.getItem_quantity());
                        int finalQuantity = ((itemQuantity - recipeitemQuantity) < 0)?0:(itemQuantity - recipeitemQuantity);

                        String sql_Ex = "UPDATE ingredient SET quantity = '"+ finalQuantity +"' WHERE item_num = '" + item.getItem_num() +"'";
                        statement.executeUpdate(sql_Ex);


                        statement.close();

                        break; 

                    }
                    else {
                        continue;
                    }

                }
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
	
	/**
	 * This method is used to get the shopping list of the user. This list
	 * is generated according to par amount and expiration date of ingredients
	 * @param user This is the user.
	 * @return Returns an array list of type Item.
	 */
	public ArrayList<Item> getUserShoppingList(User user)
    {
        ArrayList<Item> res = new ArrayList<Item>();
        //we want to get recipe's ingredients in a list
        ObservableList<Item> currInventory = getCurrentInventory(user);
        
        Calendar today = Calendar.getInstance();
        today.clear(Calendar.HOUR); today.clear(Calendar.MINUTE); today.clear(Calendar.SECOND);
        Date todayDate = today.getTime();
        
            for(Item item : currInventory)
            {
                
                    System.out.println("item exp_date: '" + item.getItem_Exp() + "'");
                    Date date1 = new Date();
                    try {
                        
                        date1 = new SimpleDateFormat("MM/dd/yyyy").parse(item.getItem_Exp());
                        } 
                    catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        } 
                        
                    System.out.println("todayDate: " + todayDate.toString());
                    System.out.println("exp date: " + date1.toString());
                    int inventoryQuantity = Integer.parseInt(item.getItem_Quantity());
                    int inventoryParQuantity = Integer.parseInt(item.getItem_Par());
                    if(date1.compareTo(todayDate) < 0 || 
                            (inventoryQuantity < inventoryParQuantity) )
                    {
                        //make recipe item contain missing quantity 
                    	if(date1.compareTo(todayDate) < 0)
                    	{
                    		item.setItem_Quantity(Integer.toString(inventoryParQuantity - 0));
                            res.add(item);
                    	}
                    	else
                    	{
                    		item.setItem_Quantity(Integer.toString(inventoryParQuantity - inventoryQuantity));
                            res.add(item);
                    	}
                        
                    }
                
            }
            
            
        
        System.out.println("returning " + res.toString());
        
        return res;
        //then we want to get the currentInventory list
        
        
        //them we compare the expiration date to current day and quantity 
    }
}