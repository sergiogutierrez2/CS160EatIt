package application.jUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.RecipeItem;

class RecipeItemTest {

	@Test
	void constructorAndGettersTest() 
	{
		RecipeItem rItem = new RecipeItem("1", "recipeItemName", "3");
		
		assertTrue(rItem.getItem_num().equals("1"));
		assertTrue(rItem.getItem_name().equals("recipeItemName"));
		assertTrue(rItem.getItem_quantity().equals("3"));
		
	}
	
	@Test
	void setItem_numTest() 
	{
		RecipeItem rItem = new RecipeItem("1", "recipeItemName", "3");
		
		rItem.setItem_num("2");
		assertTrue(rItem.getItem_num().equals("2"));
	}
	
	@Test
	void setItem_nameTest() 
	{
		RecipeItem rItem = new RecipeItem("1", "recipeItemName", "3");
		
		rItem.setItem_name("recipeItemName_2");
		
		assertTrue(rItem.getItem_name().equals("recipeItemName_2"));
	}
	
	@Test
	void setItem_quantityTest() 
	{
		RecipeItem rItem = new RecipeItem("1", "recipeItemName", "3");
		
		rItem.setItem_quantity("4");
		
		assertTrue(rItem.getItem_quantity().equals("4"));
	}

}
