package application.jUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.DatabaseManager;
import application.User;
import sun.security.pkcs11.Secmod.DbMode;
import sun.util.resources.cldr.be.CalendarData_be_BY;

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
	void connectToDatabaseTest2() {
		
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		dbm.setJdbcUrl("jdbc:sqlite:schema_v2.db");
		
		assertFalse(dbm.connectToDatabase(), "connectToDatabase() should return false, but was found to be true");
		
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
		
		dbm.deleteCredentials("TestCred_1");
		
		assertFalse(dbm.isCredentialsValid("TestCred_1", "pass_word"), "isCredentialsValid() should return false, but was found to be true");
		
		dbm.closeConnection();
	}
	
	@Test
	void isCredentialValidTest2() {
		//this test also tests the insertCredential Function
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		dbm.deleteCredentials("TestCred_1");
		
		dbm.insertCredentials("TestCred_1", "pass_word", "pass_word");
		
		
		assertTrue(dbm.isCredentialsValid("TestCred_1", "pass_word"), "isCredentialsValid() should return true, but was found to be false");
		
		dbm.deleteCredentials("TestCred_1");
		
		dbm.closeConnection();
	}
	
	@Test
	void insertCredentialTest() {
		//this test also tests the insertCredential Function
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		dbm.deleteCredentials("TestCred_1");
		
		dbm.insertCredentials("TestCred_1", "pass_word", "pass_word");
		
		assertFalse(dbm.insertCredentials("TestCred_1", "pass_word", "pass_word")
						, "insertCredentials() should return false, but was found to be true");
		
		dbm.deleteCredentials("TestCred_1");
		
		dbm.closeConnection();
	}
	
	@Test
	void deleteCredentialsTest() {
		//this test also tests the insertCredential Function
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		dbm.deleteCredentials("TestCred_1");
		
		dbm.insertCredentials("TestCred_1", "pass_word", "pass_word");
		
		dbm.deleteCredentials("TestCred_1");
		
		assertFalse(dbm.isCredentialsValid("TestCred_1", "pass_word"), 
				"isCredentialsValid() should return false, but was found to be true");
		
		dbm.deleteCredentials("TestCred_1");
		
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
		
		dbm.insertRecipe(user, "2", "carrot", "3", "2", "0");
		
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
}
