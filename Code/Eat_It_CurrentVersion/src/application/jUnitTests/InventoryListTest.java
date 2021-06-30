package application.jUnitTests;

import java.util.ArrayList;

import application.InventoryList;
import application.Item;
import application.User;


public class InventoryListTest {
	public static void main(String[] args) {
		User u1 = new User("350", "Marco", "12345");
		User u2 = new User("200", "Tony", "6789");
		
		Item fruitOne = new Item( "Apple", "03/04/2021", "34", "4", "single unit");
		Item fruitTwo = new Item( "Orange", "05/05/2021", "76", "3", "single unit");
		Item fruitThree = new Item( "Banana", "08/09/2021", "287", "5", "single unit");
		Item fruitFour = new Item( "Watermelon", "10/09/2021", "21", "1", "single unit");
		
		InventoryList list = new InventoryList(u1);
		InventoryList list2 = new InventoryList(u2);
		InventoryList list3 = new InventoryList(u2);
		
		list.addItem(fruitOne);
		list.removeItem(fruitOne);
		list.addItem(fruitTwo);
		list.addItem(fruitThree);
		
		list2.addItem(fruitOne);
		list2.addItem(fruitTwo);
		list2.addItem(fruitThree);
		
		list3.addItem(fruitOne);
		list3.removeItem(fruitOne);
		list3.addItem(fruitThree);
		list3.addItem(fruitFour);
		
		System.out.println(list);
		System.out.println("Expected: Inventory List of Marco: [46 Orange 05/05/2021 3 76 single unit, 3 Banana 08/09/2021 5 287 single unit]");
		System.out.println(list2);
		System.out.println("Expected: Inventory List of Tony: [14 Apple 03/04/2021 4 34 single unit, 46 Orange 05/05/2021 3 76 single unit, 3 Banana 08/09/2021 5 287 single unit]");
		System.out.println(list3);
		System.out.println("Expected: Inventory List of Tony: [3 Banana 08/09/2021 5 287 single unit, 24 Watermelon 10/09/2021 1 21 single unit]");
		
	}

}
