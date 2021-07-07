package application.jUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.Item;
import application.Recipe;
import application.RecipeItem;

class RecipeTest {

	@Test
	void recipeConstructorTest() {
		
		Recipe recipeObj = new Recipe("1", "Spaghetti", "30 min", "10 min", "0");
		
		assertTrue(recipeObj.getRecipe_num().equals("1"));
		assertTrue(recipeObj.getRecipe_name().equals("Spaghetti"));
		assertTrue(recipeObj.getCook_time().equals("30 min"));
		assertTrue(recipeObj.getPrep_time().equals("10 min"));
		assertTrue(recipeObj.getExecutable().equals("0"));
		
	}

	@Test
	void isExecutableTest() {
		
		
		Recipe recipeObj = new Recipe("1", "Spaghetti", "30 min", "10 min", "0");
		
		assertFalse(recipeObj.isExecutable());
		
		recipeObj.setExecutable("1");
		
		assertTrue(recipeObj.isExecutable());
		
		recipeObj.setExecutable("2");
		
		assertFalse(recipeObj.isExecutable());
	}
	
	@Test
	void ingredientList_1_Test() {
		System.out.println("\n*****START ingredientList_1_Test() TEST");
		
		Recipe recipeObj = new Recipe("1", "Spaghetti", "30 min", "10 min", "0");
		
		recipeObj.addItemToRecipe(new RecipeItem("1", "Pasta", "2"));
		
		recipeObj.addItemToRecipe(new RecipeItem("2", "Tomato", "5"));
		
		recipeObj.printIList();
		
		assertEquals(2, recipeObj.getIListSize());
		
		recipeObj.removeItemFromRecipe("2", "Tomato");
		
		recipeObj.printIList();
		
		assertEquals(1, recipeObj.getIListSize());
		
		System.out.println("\n*****END ingredientList_1_Test() TEST");
	}
	
}
