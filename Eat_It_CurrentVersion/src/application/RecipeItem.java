package application;

/**
 * This class is designed to hold the information for items that belong to a certain recipe.
 * @author Eat_It(Summer 2021 Team)
 */
public class RecipeItem 
{
	private String recipe_num;
	private String item_num;
	private String item_name;
	private String item_quantity;
	
	/**
	 * Constructor for the item list class.
	 * The constructor accepts parameters and assigns them to private fields of the same type held within the class.
	 * @param recipe_num This is the recipe_num of the recipe.
	 * @param item_num This is the item_num of the recipe ingredient.
	 * @param item_name This is the item_name of the recipe.
	 * @param item_quantity This is the item_quantity of the recipe.
	 */
	public RecipeItem(String recipe_num, String item_num, String item_name, String item_quantity)
	{
		this.recipe_num = recipe_num;
		this.item_num = item_num;
		this.item_name = item_name;
		this.item_quantity = item_quantity;
	}

	
	public String getRecipe_num() {
		return recipe_num;
	}

	public void setRecipe_num(String recipe_num) {
		this.recipe_num = recipe_num;
	}
	
	

	public String getItem_num() {
		return item_num;
	}


	public void setItem_num(String item_num) {
		this.item_num = item_num;
	}


	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_quantity() {
		return item_quantity;
	}

	/**
	 * This method updates the quantity of the item used in the recipe.
	 * @param item_quantity Is a string that holds the new quantity for that item.
	 */
	public void setItem_quantity(String item_quantity) {
		this.item_quantity = item_quantity;
	}
	
	@Override
	public String toString() 
	{  	  
		return ("\n[item_num: " + recipe_num
				+ " itemName: " + item_name 
				+  ", item_quantity: " + item_quantity + "]");
	}
	
	
}
