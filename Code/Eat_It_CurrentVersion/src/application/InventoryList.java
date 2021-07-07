package application;

import java.util.ArrayList;

public class InventoryList{
	private ArrayList<RecipeItem> list;
	
	public InventoryList() {
		 list = new ArrayList<RecipeItem>();
	}

	@Override
	public String toString() { 
        String s = "Inventory List: ";
        s = s + list.toString();
        return s;
	}

	public void addItem(RecipeItem i)
	{
	   list.add(i);
	}
	
	public void removeItem(String item_num, String item_name)
	{
	   for(RecipeItem i : list)
	   {
		   if( (i.getItem_num().equals(item_num)) && (i.getItem_name().equals(item_name)) )
		   {
			   list.remove(i);
			   break;
		   }
	   }
	}
	
	public int getSize()
	{
		return list.size();
	}
	
}
