package application;

import java.util.ArrayList;

public class InventoryList{
	private ArrayList<Item> list;
	private User usert;
	
	public InventoryList(User u) {
		 list = new ArrayList<Item>();
		 usert = u;
	}

	@Override
	public String toString() { 
        String s = "Inventory List of " + usert.getUser() + ": ";
        s = s + list.toString();
	return s;
         }

        public void addItem(Item i)
        {
           list.add(i);
        }

        public void removeItem(Item i)
        {
           list.remove(i);
        }
}
