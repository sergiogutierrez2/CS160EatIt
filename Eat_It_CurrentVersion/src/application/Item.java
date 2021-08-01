package application;

/**
 * Java Class that manages the items
 * that will be used in our recipes.
 * 
 * @author Eat_It(Summer 2021 Team)
 */
public class Item
{

	private String item_num;
	private String item_name;
	private String item_Exp;
	private String item_Quantity;
	private String item_Par;
	private String item_Quantity_Type;
	
	/**
	 * This is the constructor for the Item class that accepts
	 * an item number, item name, and item expiration date
	 * item quantity, item Par, and item quantity type.
	 * @param item_num Number of the item.
	 * @param item_name Name of the item.
	 * @param item_exp Expiration date of the item.
	 * @param item_Quantity Quantity of the item.
	 * @param item_Par Par of the item.
	 * @param item_Quantity_type Number of the item.
	 */
	public Item(String item_num, String item_name, String item_Exp, 
				String item_Quantity, String item_Par, String item_Quantity_Type) 
	{
		 this.item_num = item_num;
		 this.item_name = item_name;
		 this.item_Exp = item_Exp;
		 this.item_Quantity = item_Quantity;
		 this.item_Par = item_Par;
		 this.item_Quantity_Type = item_Quantity_Type;
	}
	
	/**
	 * Returns a string representation of the object.
	 * @return A string representation of the object.
	 */
	@Override
	public String toString() 
	{  	  
		return ("\n[item_num: " + item_num
				+ " itemName: " + item_name 
				+  ", ExpDate: " + item_Exp
				+ ", quantity: " + item_Quantity 
				+ ", parAmount: " + item_Par 
				+ ", amountType: " + item_Quantity_Type + "]");
	}
	
	/**
	 * This method returns the number of the item.
	 * @return The number of the item.
	 */
	public String getItem_num()
	{
		return item_num;
	}
	
	/**
	 * This method returns the name of item.
	 * @return The name of the item.
	 */
	public String getItem_name()
	{
		return item_name;
	}
	
	/**
	 * This method returns the expiration date of the item.
	 * @return The expiration date of the item.
	 */
	public String getItem_Exp()
	{
		return item_Exp;
	}
	
	/**
	 * This method returns the item's quantity.
	 * @return The quantity of the item.
	 */
	public String getItem_Quantity()
	{
		return item_Quantity;
	}
	
	/**
	 * This method returns the par of the item.
	 * @return The par of the item.
	 */
	public String getItem_Par()
	{
		return item_Par;
	}
	
	/**
	 * This method returns the quantity type of the item.
	 * @return The quantity type of the item.
	 */
	public String getItem_Quantity_Type()
	{
		return item_Quantity_Type;
	}

	/**
	 * This method is used to set the item's number
	 * with the given parameter.
	 * @param item_num The number of the item.
	 */
	public void setItem_num(String item_num) {
		this.item_num = item_num;
	}

	/**
	 * This method is used to set the item's name
	 * with the given parameter.
	 * @param item_name The name of the item.
	 */
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	/**
	 * This method is used to set the item's expiration date
	 * with the given parameter.
	 * @param item_Exp The item's expiration date.
	 */
	public void setItem_Exp(String item_Exp) {
		//make sure item_Exp is in the correct format
		//should be in 0M/0D/YYYY
			//not 		M/D/YYYY
		//get substrings
		String month = item_Exp.substring(0, 2);
		if(month.charAt(month.length() - 1) == '/')
		{
			//month is one char so prepend '0'
			month = "0" + month.substring(0, 1);
			
			//that means day starts at index 2
			String day = item_Exp.substring(2, 4);
			if(day.charAt(day.length() - 1) == '/')
			{
				//day is one char so prepend 0
				day = "0" + day.substring(0, 1);
				
				String year = item_Exp.substring(4);
				this.item_Exp = month + "/" + day + "/" + year;
			}
			else
			{
				// month is one char and day is two chars
				// M/DD/YYYY
				String year = item_Exp.substring(5);
				this.item_Exp = month + "/" + day + "/" + year;
			}
		}
		else
		{
			//month is two chars: MM/0D/YYYY
			String day = item_Exp.substring(3, 5);
			if(day.charAt(day.length() - 1) == '/')
			{
				
				//day is one char: MM/D/YYYY
				day = "0" + day.substring(0,1);
				
				String year = item_Exp.substring(5);
				this.item_Exp = month + "/" + day + "/" + year;
			}
			else
			{
				//both day and month are two chars
				// MM/DD/YYYY so we can leave provided string alone.
				this.item_Exp = item_Exp;
			}
		}
		
		System.out.println("item_exp: " + this.item_Exp);
			
		
		
		
	}

	/**
	 * This method is used to set the item's quantity
	 * with the given parameter.
	 * @param item_Quantity The quantity of the item.
	 */
	public void setItem_Quantity(String item_Quantity) {
		this.item_Quantity = item_Quantity;
	}

	/**
	 * This method is used to set the item's Par
	 * with the given parameter.
	 * @param item_par The par of the item.
	 */
	public void setItem_Par(String item_Par) {
		this.item_Par = item_Par;
	}

	/**
	 * This method is used to set the item's quantity type
	 * with the given parameter.
	 * @param item_Quantity_Type The quantity type of the item.
	 */
	public void setItem_Quantity_Type(String item_Quantity_Type) {
		this.item_Quantity_Type = item_Quantity_Type;
	}
}
