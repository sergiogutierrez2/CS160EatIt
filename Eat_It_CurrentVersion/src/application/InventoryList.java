package application;

import java.util.ArrayList;

/**
 * A Java class for the Inventory List Object.
 * 
 * @author Eat_It(Summer 2021 Team)
 */
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
	
	public void removeItem(String recipe_num, String item_name)
	{
	   for(RecipeItem i : list)
	   {
		   if( (i.getRecipe_num().equals(recipe_num)) && (i.getItem_name().equals(item_name)) )
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
	
	public ArrayList<RecipeItem> getList()
	{
		return list;
	}
	
}
