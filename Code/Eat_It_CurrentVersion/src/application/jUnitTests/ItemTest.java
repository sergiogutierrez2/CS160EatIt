package application.jUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.Item;

class ItemTest {

	@Test
	void testItem() {
		Item new_Item = new Item( "rice","6/24/21","36","22","bags");
		
		//fail("Not yet implemented");
	}

	@Test
	void testToString() {
		Item new_Item = new Item( "rice","6/24/21","36","22","bags");
		new_Item.toString();
		//fail("Not yet implemented");
	}

	@Test
	void testAddItem() {
		Item new_Item = new Item( "rice","6/24/21","36","22","bags");
		Item new_Item2 = new Item( "flour","6/24/21","36","22","lbs");
		Item new_Item3= new Item( "wheat","6/24/21","36","22","sacks");
		//fail("Not yet implemented");
	}

}
