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
 * @author Team Eat It (Summer 2021)
 */
public class DatabaseManager {
	
	private Connection connection;
	private boolean connectedStatus; 
	private String jdbcUrl = "jdbc:sqlite:" + System.getProperty("user.dir") +  File.separator + "src" + File.separator + "application" + File.separator + "db" + File.separator + "schema_v1.db";
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
	 * This method closed the connection to the db.
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
	 * @param user
	 * @param recipe_num
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

	public void updateExec()
	{
		//you need to get a list of the recipes:
			//getCurrentRecipeList(User user)
		
		//get ingredient list:
			//getCurrentInventory(User user)
		
		
		
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
		} catch (SQLException e) 
		{
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
		} 
		catch (SQLException e) 
		{
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
			e.printStackTrace();
		}
		
		
		return recipeList;
	}
	
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
					if(date1.compareTo(todayDate) > 0 && 
							(inventoryQuantity < recipeItemQuantity) )
					{
						//make recipe item contain missing quantity 
						recipeItem.setItem_quantity(Integer.toString(recipeItemQuantity - inventoryQuantity));
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
						String sql_Ex = "UPDATE recipes SET executable = '1' WHERE recipe_num = '" + currRecip.getRecipe_num()+"'";
						statement.executeUpdate(sql_Ex);
					}	
					else
					{	
						String sql_Ex = "UPDATE recipes SET executable = '0' WHERE recipe_num = '" + currRecip.getRecipe_num()+"'";
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
	
	public void printRecipeObservableList(ObservableList<Recipe> recipeList)
	{
		for(Recipe tempR : recipeList)
			{
				System.out.println(tempR.toString());
			}
	}

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