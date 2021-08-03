package application.jUnitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import application.DatabaseManager;
import application.Recipe;
import application.RecipeStep;
import application.User;
import javafx.collections.ObservableList;
import sun.util.resources.cldr.bn.CalendarData_bn_BD;

class DatabaseManagerTest {
	
	@Test
	void initializationTest() {
		
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		assertFalse(dbm.getConnectedStatus(), "connect status should be false initially, but was found to be true");
		
		//assertFunction(expectedValue, ActualValue);
	}
	
	@Test
	void connectToDatabaseTest1() {
		
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		assertTrue(dbm.connectToDatabase(), "connectToDatabase() should return true, but was found to be false");
		
		dbm.closeConnection();
	}
	
	@Test
	void closeConnectionTest() {
		
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		dbm.connectToDatabase();
		
		assertTrue(dbm.closeConnection(), "closeConnection() should return true, but was found to be false");
		
		dbm.closeConnection();
	}
	
	@Test
	void isCredentialValidTest1() {
		
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		

		User user = new User("1009", "TestCred_1", "pass_word");
		
		if(!dbm.isCredentialsValid("TestCred_1", "pass_word") )
		{
			dbm.insertCredentials("TestCred_1", "pass_word", "pass_word");
			user = dbm.getUser("TestCred_1");
		}
		else
		{
			user = dbm.getUser("TestCred_1");
		}
		
		dbm.deleteCredentials(user);
		
		assertFalse(dbm.isCredentialsValid("TestCred_1", "pass_word"), "isCredentialsValid() should return false, but was found to be true");
		
		dbm.closeConnection();
	}
	
	@Test
	void isCredentialValidTest2() {
		//this test also tests the insertCredential Function
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		User user = new User("1009", "TestCred_1", "pass_word");
		
		if(!dbm.isCredentialsValid("TestCred_1", "pass_word") )
		{
			dbm.insertCredentials("TestCred_1", "pass_word", "pass_word");
		}
		else
		{
			user = dbm.getUser("TestCred_1");
		}
		
		dbm.insertCredentials("TestCred_1", "pass_word", "pass_word");
		
		user = dbm.getUser("TestCred_1");
		
		assertTrue(dbm.isCredentialsValid("TestCred_1", "pass_word"), "isCredentialsValid() should return true, but was found to be false");
		
		dbm.deleteCredentials(user);
		
		dbm.closeConnection();
	}
	
	@Test
	void insertCredentialTest() {
		//this test also tests the insertCredential Function
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		User user = new User("1009", "TestCred_1", "pass_word");
		
		if(!dbm.isCredentialsValid("TestCred_1", "pass_word") )
		{
			dbm.insertCredentials("TestCred_1", "pass_word", "pass_word");
		}
		else
		{
			user = dbm.getUser("TestCred_1");
		}
		
		
		dbm.insertCredentials("TestCred_1", "pass_word", "pass_word");
		
		user = dbm.getUser("TestCred_1");
		
		assertFalse(dbm.insertCredentials("TestCred_1", "pass_word", "pass_word")
						, "insertCredentials() should return false, but was found to be true");
		
		dbm.deleteCredentials(user);
		
		dbm.closeConnection();
	}
	
	@Test
	void deleteCredentialsTest() {
		//this test also tests the insertCredential Function
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		User user = new User("1009", "TestCred_1", "pass_word");
		
		if(!dbm.isCredentialsValid("TestCred_1", "pass_word") )
		{
			dbm.insertCredentials("TestCred_1", "pass_word", "pass_word");
		}
		else
		{
			user = dbm.getUser("TestCred_1");
		}
		
		dbm.deleteCredentials(user);
		
		dbm.insertCredentials("TestCred_1", "pass_word", "pass_word");
		
		user = dbm.getUser("TestCred_1");
		
		dbm.deleteCredentials(user);
		
		assertFalse(dbm.isCredentialsValid("TestCred_1", "pass_word"), 
				"isCredentialsValid() should return false, but was found to be true");
		
		dbm.deleteCredentials(user);
		
		dbm.closeConnection();
	}
	
	@Test
	void printCredentialsTest() {
	
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		dbm.printCredentials();
		
		assertTrue(dbm.closeConnection(), "If you see Credentials Printed in Log, then test successful");
	}
	
	@Test
	void getCurrentInventoryList() {
		
		System.out.println("\nSTART getCurrentInventoryList() TEST");
	
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		User user = new User("1", "zuber1", "password");
		
		dbm.deleteIngredient(user, "4");
		
		dbm.insertIngredient(user, "4", "carrot", "12-12-2021", "3", "2", "whole");
		
		dbm.getCurrentInventory(user);
		
		assertTrue(dbm.containsItemNum(user, "4"));
		
		dbm.deleteIngredient(user, "4");
		
		dbm.closeConnection();
		
		System.out.println("END getCurrentInventoryList() TEST\n");
	}
	
	@Test
	void insertIngredientTest() {
	
		System.out.println("\nSTART insertIngredientTest() TEST");
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		User user = new User("1", "zuber1", "password");
		
		dbm.deleteIngredient(user, "2");
		
		dbm.getCurrentInventory(user);
		
		dbm.insertIngredient(user, "2", "carrot", "12-12-2021", "3", "2", "whole");
		
		assertTrue(dbm.containsItemNum(user, "2"));
		
		dbm.getCurrentInventory(user);
		
		dbm.deleteIngredient(user, "2");
		
		dbm.getCurrentInventory(user);
		
		dbm.closeConnection();
		
		System.out.println("END insertIngredientTest() TEST\n");
	}
	
	@Test
	void insertRecipeTest() {
	
		System.out.println("\nSTART insertRecipeTest() TEST");
		
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		User user = new User("1", "zuber1", "password");
		
		dbm.deleteRecipe(user, "2");
		
		dbm.getCurrentRecipeList(user);
		
		dbm.insertRecipe(user, "2", "RecipeName", "3", "2", "0");
		
		assertTrue(dbm.containsRecipeNum(user, "2"));
		
		dbm.getCurrentRecipeList(user);
		
		dbm.deleteRecipe(user, "2");
		
		dbm.getCurrentRecipeList(user);
		
		dbm.closeConnection();
		
		System.out.println("END insertRecipeTest() TEST\n");
	}
	
	@Test
	void deleteIngredientTest() {
	
		System.out.println("START deleteIngredientTest() TEST");
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		User user = new User("1", "zuber1", "password");
		
		System.out.println("before deleteIngredient()");
		
		dbm.insertIngredient(user, "2", "carrot", "12-12-2021", "3", "2", "whole");
		
		dbm.getCurrentInventory(user);
		
		dbm.deleteIngredient(user, "2");
		
		assertFalse(dbm.containsItemNum(user, "2"), "dbms should not contain item_num 2 and should return false, but returned true");
		
		System.out.println("after deleteIngredient()");

		dbm.getCurrentInventory(user);
		
		dbm.closeConnection();
		
		System.out.println("END deleteIngredientTest() TEST\n");
	}
	
	@Test
	void insertRecipeIngredientTest() {
		
		System.out.println("\nSTART insertRecipeIngredientTest() TEST");
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		User user = new User("1", "zuber1", "password");
		Recipe recipe = new Recipe("1", "Pasta", "30 min", "10 min");
		
		dbm.deleteRecipe(user, recipe.getRecipe_num());
		
		dbm.deleteRecipeIngredient(user, "1", "noodles");

		dbm.insertRecipe(user, recipe.getRecipe_num(), recipe.getRecipe_name(), recipe.getCook_time(), recipe.getPrep_time(), recipe.getExecutable());
		
		dbm.getRecipesIngredientList(user, recipe);
		
		dbm.insertRecipeIngredient(user, "1", "1", "noodles", "1");
		
		assertTrue(dbm.containsRecipeIngredient(user, "1", "1"));
		
		dbm.getRecipesIngredientList(user, recipe);
		
		dbm.deleteRecipeIngredient(user, "1", "1");
		
		dbm.getRecipesIngredientList(user, recipe);
		
		dbm.deleteRecipe(user, recipe.getRecipe_num());
		
		dbm.closeConnection();
		
		System.out.println("END insertRecipeIngredientTest() TEST\n");
	}
	
	@Test
	void deleteRecipeIngredientTest() {
	
		System.out.println("START deleteIngredientTest() TEST");
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		User user = new User("1", "zuber1", "password");
		Recipe recipe = new Recipe("1", "Pasta", "30 min", "10 min");
		
		dbm.deleteRecipe(user, recipe.getRecipe_num());
		
		dbm.deleteRecipeIngredient(user, "1", "noodles");
		
		
		dbm.insertRecipe(user, recipe.getRecipe_num(), recipe.getRecipe_name(), recipe.getCook_time(), recipe.getPrep_time(), recipe.getExecutable());
		
		dbm.insertRecipeIngredient(user, "1", "1", "noodles", "1 pack");
		
		dbm.getRecipesIngredientList(user, recipe);
		
		assertTrue(dbm.containsRecipeIngredient(user, "1", "1"));
		
		dbm.deleteRecipeIngredient(user, "1", "1");
		
		assertFalse(dbm.containsRecipeIngredient(user, "1", "1"));

		dbm.deleteRecipe(user, recipe.getRecipe_num());
		
		dbm.closeConnection();
		
		System.out.println("END deleteIngredientTest() TEST\n");
	}
		
	@Test
	void insertRecipeStepsTest() {
		
		System.out.println("\nSTART insertRecipeStepsTest() TEST");
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		User user = new User("1", "zuber1", "password");
		Recipe recipe = new Recipe("1", "Pasta", "30 min", "10 min");
		RecipeStep recipeStep = new RecipeStep(recipe.getRecipe_num(), "1", "boil water");
		dbm.deleteRecipe(user, recipe.getRecipe_num());
		
		dbm.deleteRecipeSteps(user, recipe.getRecipe_num(), recipeStep.getStep_num());

		dbm.insertRecipe(user, recipe.getRecipe_num(), recipe.getRecipe_name(), recipe.getCook_time(), recipe.getPrep_time(), recipe.getExecutable());
		
		dbm.insertRecipeSteps(user, recipe.getRecipe_num(), recipeStep.getStep_num(), recipeStep.getStep_desc());
				
		assertTrue(dbm.containsRecipeStep(user, recipe.getRecipe_num(), recipeStep.getStep_num()));
		
		dbm.getRecipesStepList(user, recipe);
		
		dbm.deleteRecipeSteps(user, recipe.getRecipe_num(), recipeStep.getStep_num());
		
		dbm.getRecipesStepList(user, recipe);
		
		dbm.deleteRecipe(user, recipe.getRecipe_num());
		
		dbm.closeConnection();
		
		System.out.println("END insertRecipeStepsTest() TEST\n");
	}
		
	@Test
	void autoGeneratedItemNumTest() {
	
		System.out.println("\nSTART autoGeneratedItemNumTest() TEST");
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		User user = new User("1", "zuber1", "password");
		
		dbm.deleteIngredient(user, "99");
		
		dbm.insertIngredient(user, "99", "carrot", "12-12-2021", "3", "2", "whole");
		
		String result = dbm.autogenerateItemNum(user);
		assertEquals("100", result, "highest item number is 99 so expected return of function is 100, but returned some different than 100");

		System.out.println("result = " + result);
		
		dbm.getCurrentInventory(user);
		
		dbm.deleteIngredient(user, "99");
		
		dbm.closeConnection();
		
		System.out.println("END autoGeneratedItemNumTest() TEST\n");
		
	}
	
	@Test
	void getExecutableRecipesTest()
	{
		System.out.println("\nSTART getExecutableRecipesTest() TEST");
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		User user = new User("1", "zuber1", "password");
		dbm.deleteRecipe(user, "98");
		dbm.deleteRecipe(user, "99");
		dbm.deleteRecipe(user, "100");
		Recipe tempRecipe1 = new Recipe("99", "tempRecipe1", "20 min", "21 min", "0");
		Recipe tempRecipe2 = new Recipe("98", "tempRecipe2", "20 min", "21 min", "0");
		Recipe tempRecipe3 = new Recipe("100", "tempRecipe3", "30 min", "31 min", "1");
		
		dbm.insertRecipe(user, tempRecipe1);
		dbm.insertRecipe(user, tempRecipe2);
		dbm.insertRecipe(user, tempRecipe3);
		
		ObservableList<Recipe> executableRecipeList = dbm.getExecutableRecipes(user, true);
		ObservableList<Recipe> non_executableRecipeList = dbm.getExecutableRecipes(user, false);
		
		System.out.println("Exec Recipes: " +  executableRecipeList.toString() );
		System.out.println("Non Exec Recipes: " +  non_executableRecipeList.toString() );
		
		dbm.deleteRecipe(user, "99");
		dbm.deleteRecipe(user, "100");
		dbm.deleteRecipe(user, "98");
		
		dbm.getCurrentRecipeList(user);
		
		System.out.println("END getExecutableRecipesTest() TEST\n");
		
	}
	
	@Test
	void getCurrentCredentialsTest()
	{
		System.out.println("\nSTART getCurrentCredentialsTest() TEST");
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		//User user = new User("1", "zuber1", "password");
		
		//dbm.insertCredentials( user.getAcc_id(), user.getUsername(), user.getPassword() ); 
		
		dbm.getCurrentCredentials();
		
		
		
		System.out.println("END getCurrentCredentialsTest() TEST\n");
		
	}
	
	@Test
	void deleteCredentialsWithUserIdTest()
	{
		System.out.println("\nSTART deleteCredentialsWithUserIdTest() TEST");
		
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		dbm.connectToDatabase();
		
		User user = new User("150", "testUser", "password");
		
		dbm.deleteCredentials(user);
		
		dbm.insertCredentials(user.getUsername(), user.getPassword(), user.getPassword());
		
		dbm.printCredentials();
		
		dbm.deleteCredentials(user);
		
		dbm.printCredentials();
		
		dbm.closeConnection();
		
		System.out.println("END deleteCredentialsWithUserIdTest() TEST\n");
	}
	
	@Test
	void usernameExistsTest()
	{
		System.out.println("\nSTART usernameExistsTest() TEST");
		
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		dbm.connectToDatabase();
		
		User user = new User("150", "testUser", "password");
		
		dbm.deleteCredentials(user);
		
		dbm.insertCredentials(user.getUsername(), user.getPassword(), user.getPassword());
		
		dbm.printCredentials();
		
		User userTemp = dbm.getUser(user.getUsername());
		if(userTemp != null)
		{
			assertFalse( dbm.usernameExists( userTemp.getAcc_id(), userTemp.getUsername() ), "dbm.usernameExists return false, when it should have returned true." );
			
			dbm.deleteCredentials(user);
			
			dbm.printCredentials();
			
			dbm.closeConnection();
			
			System.out.println("END usernameExistsTest() TEST\n");
		}
		else
		{
			assertTrue(false, "userTemp was null!");
		}
	}
	
	@Test
	void usernameExistsTest2()
	{
		System.out.println("\nSTART usernameExistsTest2() TEST");
		
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		dbm.connectToDatabase();
		
		User user = new User("150", "testUser", "password");
		
		dbm.deleteCredentials(user);
		
		dbm.insertCredentials(user.getUsername(), user.getPassword(), user.getPassword());
		
		dbm.printCredentials();
		
		User userTemp = dbm.getUser(user.getUsername());
		if(userTemp != null)
		{
			assertTrue( dbm.usernameExists( userTemp.getAcc_id(), "demo" ), "dbm.usernameExists return false, when it should have returned true." );
			
			dbm.deleteCredentials(user);
			
			dbm.printCredentials();
			
			dbm.closeConnection();
			
			System.out.println("END usernameExistsTest() TEST\n");
		}
		else
		{
			assertTrue(false, "userTemp was null!");
		}
	}
	
	@Test
	void deleteCredentialTest()
	{
		//make sure that when we delete a credential that all
		//of that credentials recipes and all of the recipes 
		//ingredients get deleted. It is a full wipe of the users
		//children.
		System.out.println("\nSTART deleteCredentialTest TEST");
		
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		dbm.connectToDatabase();
		
		User user = new User("150", "testUser", "password");
		
		Recipe recipe1 = new Recipe("1", "Cereal", "2 min", "2 min");
		Recipe recipe2 = new Recipe("2", "Toast", "2 min", "2 min");
		
		dbm.deleteCredentials(user);
		
		dbm.insertCredentials(user.getUsername(), user.getPassword(), user.getPassword());
		
		user = dbm.getUser("testUser");
		if(user != null)
		{
			dbm.insertIngredient(user, "1", "frosted flakes", "12/12/2021", "20", "10", "cups");
			dbm.insertIngredient(user, "2", "bread", "12/12/2021", "20", "10", "cups");
			dbm.insertIngredient(user, "3", "milk", "12/12/2021", "20", "10", "cups");
			
			dbm.insertRecipe(user, recipe1);
			
			dbm.insertRecipe(user, recipe2);
			
			dbm.insertRecipeIngredient(user, "1", "1", "frosted flakes", "10");
			dbm.insertRecipeIngredient(user, "1", "3", "milk", "10");
			dbm.insertRecipeIngredient(user, "2", "2", "bread", "10");
			
			dbm.printCredentials();
			
			System.out.println( dbm.getCurrentInventory(user).toString() );
			System.out.println( dbm.getCurrentRecipeList(user).toString() );
			System.out.println( dbm.getRecipesIngredientList(user, recipe1).toString() );
			System.out.println( dbm.getRecipesIngredientList(user, recipe2).toString() );
			
			System.out.println( dbm.getRecipesStepList(user, recipe1).toString() );
			System.out.println( dbm.getRecipesStepList(user, recipe2).toString() );
			
			dbm.deleteCredentials(user);
			
			dbm.printCredentials();
			
			dbm.closeConnection();
		}
		else
		{
			System.out.println("user == null");
		}
		
		
		
		System.out.println("END deleteCredentialTest() TEST\n");
	}
	
	@Test
	void updateUsernameCredentialTest()
	{
		//make sure that when we delete a credential that all
		//of that credentials recipes and all of the recipes 
		//ingredients get deleted. It is a full wipe of the users
		//children.
		System.out.println("\nSTART updateUsernameCredentialTest TEST");
		
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		dbm.connectToDatabase();
		
		//delete testUser cred if exists
		if( dbm.isCredentialsValid("testUser1", "password") )
		{
			dbm.deleteCredentials(dbm.getUser("testUser1"));
		}
		
		if( dbm.isCredentialsValid("testUser2", "password") )
		{
			dbm.deleteCredentials(dbm.getUser("testUser2"));
		}
		
		//insert testUser credential
		dbm.insertCredentials("testUser1", "password", "password");
		
		//set up temp user var
		User user = new User("150", "testUser1", "password");
		
		//update temp user var with credential from database
		user = dbm.getUser("testUser1");
		
		if(user != null)
		{
			System.out.println( user.toString() );
			
			assertTrue(dbm.isCredentialsValid("testUser1", "password"));
			
			dbm.updateCredentialUsername(user.getAcc_id(), "testUser2");
			
			assertTrue(dbm.isCredentialsValid("testUser2", "password"));
			
			dbm.deleteCredentials(user);
			
			dbm.printCredentials();
			
			dbm.closeConnection();
		}
		else
		{
			System.out.println("user == null");
		}
		
		
		
		System.out.println("END updateUsernameCredentialTest() TEST\n");
	}
	
	@Test
	void updatePasswordCredentialTest()
	{
		//make sure that when we delete a credential that all
		//of that credentials recipes and all of the recipes 
		//ingredients get deleted. It is a full wipe of the users
		//children.
		System.out.println("\nSTART updatePasswordCredentialTest TEST");
		
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		dbm.connectToDatabase();
		
		//delete testUser cred if exists
		if( dbm.isCredentialsValid("testUser", "password") )
		{
			dbm.deleteCredentials(dbm.getUser("testUser"));
		}
		
		if( dbm.isCredentialsValid("testUser", "pass1") )
		{
			dbm.deleteCredentials(dbm.getUser("testUser"));
		}
		
		//insert testUser credential
		dbm.insertCredentials("testUser", "password", "password");
		
		//set up temp user var
		User user = new User("150", "testUser", "password");
		
		//update temp user var with credential from database
		user = dbm.getUser("testUser");
		
		if(user != null)
		{
			System.out.println( user.toString() );
			
			assertTrue(dbm.isCredentialsValid("testUser", "password"));
			
			dbm.updateCredentialPassword(user.getAcc_id(), "pass1");
			
			assertTrue(dbm.isCredentialsValid("testUser", "pass1"));
			
			dbm.deleteCredentials(user);
			
			dbm.printCredentials();
			
			dbm.closeConnection();
		}
		else
		{
			System.out.println("user == null");
		}
		
		
		
		System.out.println("END updatePasswordCredentialTest() TEST\n");
	}
}
