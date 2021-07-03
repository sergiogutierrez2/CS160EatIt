package application.jUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InventoryListTest {

	@Test
	void testToString() {
		User u1 = new User("350", "Marco", "12345");
		User u2 = new User("200", "Tony", "6789");
		
		Item fruitOne = new Item("14", "Apple", "03/04/2021", "34", "4", "single unit");
		Item fruitTwo = new Item("46", "Orange", "05/05/2021", "76", "3", "single unit");
		Item fruitThree = new Item("3", "Banana", "08/09/2021", "287", "5", "single unit");
		Item fruitFour = new Item("24", "Watermelon", "10/09/2021", "21", "1", "single unit");
		
		InventoryList test = new InventoryList(u1);
		InventoryList test2 = new InventoryList(u2);
		InventoryList test3 = new InventoryList(u2);
		
		test.addItem(fruitOne);
		test.removeItem(fruitOne);
		test.addItem(fruitTwo);
		test.addItem(fruitThree);
		
		test2.addItem(fruitOne);
		test2.addItem(fruitTwo);
		test2.addItem(fruitThree);
		
		test3.addItem(fruitOne);
		test3.removeItem(fruitOne);
		test3.addItem(fruitThree);
		test3.addItem(fruitFour);
		
		String output = test.toString();
		String output2 = test2.toString();
		String output3 = test3.toString();
		
		assertEquals(output, "Inventory List of Marco: [itemName: Orange, ExpDate: 05/05/2021, quantity: 76, parAmount: 3, amountType: single unit, itemName: Banana, ExpDate: 08/09/2021, quantity: 287, parAmount: 5, amountType: single unit]");
		assertEquals(output2, "Inventory List of Tony: [itemName: Apple, ExpDate: 03/04/2021, quantity: 34, parAmount: 4, amountType: single unit, itemName: Orange, ExpDate: 05/05/2021, quantity: 76, parAmount: 3, amountType: single unit, itemName: Banana, ExpDate: 08/09/2021, quantity: 287, parAmount: 5, amountType: single unit]");
		assertEquals(output3, "Inventory List of Tony: [itemName: Banana, ExpDate: 08/09/2021, quantity: 287, parAmount: 5, amountType: single unit, itemName: Watermelon, ExpDate: 10/09/2021, quantity: 21, parAmount: 1, amountType: single unit]");
	}
}

