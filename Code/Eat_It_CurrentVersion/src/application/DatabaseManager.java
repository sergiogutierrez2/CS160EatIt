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
	
	//if item number already exists then containsItemNum should be true
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
	
	public void deleteRecipe(User user, String recipe_num)
	{
		System.out.println("Deleting Ingredient " + recipe_num );
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "DELETE FROM recipes WHERE user_id = '" + user.getAcc_id() + "' AND recipe_num = '" + recipe_num + "'";
		
		try {
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
			statement.close();
		} catch (SQLException e) {
			System.out.println("Failed to delete recipe: " + e.getErrorCode());
			e.printStackTrace();
			
		}
	}
	
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return String.valueOf(autoGenRecipeNum);
	}
	
	//return highest item_num plus one
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return String.valueOf(autoGenItemNum);
	}
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return currentInventoryList;
	}
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return recipeList;
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
