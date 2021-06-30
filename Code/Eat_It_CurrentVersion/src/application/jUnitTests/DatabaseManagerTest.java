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
	
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		dbm.insertIngredient("1", "carrot", "12-12-2021", "3", "2", "whole");
		dbm.getCurrentInventory(new User("1", "zuber1", "password"));
		
		dbm.closeConnection();
		
	}
	
	@Test
	void insertIngredientTest() {
	
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		
		User user = new User("1", "zuber1", "password");
		
		dbm.getCurrentInventory(user);
		
		
		
		dbm.insertIngredient("1", "carrot", "12-12-2021", "3", "2", "whole");
		
		assertTrue(dbm.containsIngredient(user, "carrot"));
		
		dbm.getCurrentInventory(user);
		
		dbm.deleteIngredient(user, "carrot");
		
		dbm.getCurrentInventory(user);
		
		dbm.closeConnection();
		
	}
}
