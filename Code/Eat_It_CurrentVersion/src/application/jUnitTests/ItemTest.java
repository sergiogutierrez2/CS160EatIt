package application.jUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.Item;

class ItemTest {

	@Test
	void testItem() {
		Item new_Item = new Item("1","rice","6/24/21","36","22","bags");
		
		assertEquals("rice", new_Item.getItem_name(), "It was suppose to be rice");
		//fail("Not yet implemented");
	}

	@Test
	void testToString() {
		Item new_Item = new Item("1", "rice","6/24/21","36","22","bags");
		new_Item.toString();
		//fail("Not yet implemented");
	}

	@Test
	void testAddItem() {
		Item new_Item = new Item("1", "rice","6/24/21","36","22","bags");
		Item new_Item2 = new Item("2", "flour","6/24/21","36","22","lbs");
		Item new_Item3= new Item("3", "wheat","6/24/21","36","22","sacks");
		//fail("Not yet implemented");
	}

}
