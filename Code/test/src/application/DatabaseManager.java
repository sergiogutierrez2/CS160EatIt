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
	
	public boolean containsRecipeIngredient(User user, String recipe_num, String ingredient_name)
	{
		System.out.println("checking if user's recipe num: " + recipe_num + ", contains ingredient: " + ingredient_name);
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		String sql = "SELECT user_id, recipe_num, ingredient_name FROM ingredientlist where user_id = ? and recipe_num = ? and ingredient_name = ?";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
		
			pstmt.setInt(1, Integer.parseInt(user.getAcc_id()));
		
			pstmt.setInt(2, Integer.parseInt(recipe_num));
			
			pstmt.setString(3, ingredient_name);
			
			ResultSet result;
			
			result = pstmt.executeQuery();
			
			boolean containsIngredientFlag = false;
			
			while (result.next()) 
			{
				System.out.println("DBMS: Ingredient already exists in recipe");
				containsIngredientFlag = true;
				
				int user_id = result.getInt("user_id");
				int recipeNum = result.getInt("recipe_num"); //specified attribute name is "username" in sql db
				String ingredientName = result.getString("ingredient_name");
				
				System.out.println("From DBMS: " + user_id + " | " + recipeNum + " | " + ingredientName);
			}
			
			pstmt.close();
			result.close();
			
			if(!containsIngredientFlag)
			{
				System.out.println("From DBMS: " + user.getAcc_id() + " | " + recipe_num + " | " + ingredient_name + " is not contained in DB");
			}
			
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
		} catch (SQLException e) {
			System.out.println("Failed to delete recipe: " + e.getErrorCode());
			e.printStackTrace();
			
		}
	}
	
	
	
	public boolean insertRecipeIngredient(User user, String recipe_num, String ingredient_name, String quantity)
	{
		System.out.println("Inserting ingredient into Recipe: " + recipe_num);

		if( containsRecipeIngredient(user, recipe_num, ingredient_name) )
		{
			return false;
		}

		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "insert into ingredientlist (user_id, recipe_num, ingredient_name, amount) values ('"
				+ user.getAcc_id() + "', '" + recipe_num + "', '" + ingredient_name  + "', '" + quantity + "')";

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
	
	public void deleteRecipeIngredient(User user, String recipe_num, String ingredient_name)
	{
		System.out.println( "Deleting Ingredient from recipe: " + recipe_num );
		
		if(!connectedStatus)
		{
			connectToDatabase();
		}
		
		String sql = "DELETE FROM ingredientlist WHERE user_id = '" + user.getAcc_id() + "' AND recipe_num = '" + recipe_num 
						+ "' AND ingredient_name = '" + ingredient_name + "'";
		
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
				String ingredient_name = result.getString("ingredient_name"); //specified attribute name is "pass_word" in sql db
				Integer amount = result.getInt("amount");
			
				System.out.println( user_id.toString() + " | " + recipe_num + " | " + ingredient_name + " | " + amount);
				ingredientList.add(new RecipeItem( recipe_num.toString(), ingredient_name, amount.toString() ) );
			}
			
			statement.close();
			result.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return ingredientList;
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