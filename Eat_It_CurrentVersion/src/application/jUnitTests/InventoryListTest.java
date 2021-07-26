package application.jUnitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import application.InventoryList;
import application.RecipeItem;
import application.User;

class InventoryListTest {

	@Test
	void testToString() {
		
		RecipeItem fruitOne = new RecipeItem("14", "1", "Apple", "34");
		RecipeItem fruitTwo = new RecipeItem("46", "1", "Orange", "3");
		RecipeItem fruitThree = new RecipeItem("3", "1", "Banana", "5");
		RecipeItem fruitFour = new RecipeItem("24", "1", "Watermelon", "1");
		
		InventoryList test = new InventoryList();
		InventoryList test2 = new InventoryList();
		InventoryList test3 = new InventoryList();
		
		test.addItem(fruitOne);
		test.removeItem("14", "Apple");
		test.addItem(fruitTwo);
		test.addItem(fruitThree);
		
		test2.addItem(fruitOne);
		test2.addItem(fruitTwo);
		test2.addItem(fruitThree);
		
		test3.addItem(fruitOne);
		test3.removeItem("14", "Apple");
		test3.addItem(fruitThree);
		test3.addItem(fruitFour);
		
		String output = test.toString();
		String output2 = test2.toString();
		String output3 = test3.toString();
		
		System.out.println(output);
		System.out.println(output2);
		System.out.println(output3);
		
		assertEquals("Inventory List: [\n"
				+ "[item_num: 46 itemName: Orange, ExpDate: 3], \n"
				+ "[item_num: 3 itemName: Banana, ExpDate: 5]]", output);
		assertEquals("Inventory List: [\n"
				+ "[item_num: 14 itemName: Apple, ExpDate: 34], \n"
				+ "[item_num: 46 itemName: Orange, ExpDate: 3], \n"
				+ "[item_num: 3 itemName: Banana, ExpDate: 5]]" , output2);
		assertEquals("Inventory List: [\n"
				+ "[item_num: 3 itemName: Banana, ExpDate: 5], \n"
				+ "[item_num: 24 itemName: Watermelon, ExpDate: 1]]", output3);
		
	}
	
}

