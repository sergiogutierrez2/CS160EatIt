package application;

public class RecipeItem 
{
	private String item_num;
	private String item_name;
	private String item_quantity;
	
	public RecipeItem(String item_num, String item_name, String item_quantity)
	{
		this.item_num = item_num;
		this.item_name = item_name;
		this.item_quantity = item_quantity;
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

	public void setItem_quantity(String item_quantity) {
		this.item_quantity = item_quantity;
	}
	
	@Override
	public String toString() 
	{  	  
		return ("\n[item_num: " + item_num
				+ " itemName: " + item_name 
				+  ", ExpDate: " + item_quantity + "]");
	}
	
	
}
