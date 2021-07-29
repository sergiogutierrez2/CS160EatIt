package application;

/**
 * TODO: Write a description of the class here.
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
	
	public String getItem_num()
	{
		return item_num;
	}
	
	public String getItem_name()
	{
		return item_name;
	}
	
	public String getItem_Exp()
	{
		return item_Exp;
	}
	
	public String getItem_Quantity()
	{
		return item_Quantity;
	}
	
	public String getItem_Par()
	{
		return item_Par;
	}
	
	public String getItem_Quantity_Type()
	{
		return item_Quantity_Type;
	}

	public void setItem_num(String item_num) {
		this.item_num = item_num;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

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

	public void setItem_Quantity(String item_Quantity) {
		this.item_Quantity = item_Quantity;
	}

	public void setItem_Par(String item_Par) {
		this.item_Par = item_Par;
	}

	public void setItem_Quantity_Type(String item_Quantity_Type) {
		this.item_Quantity_Type = item_Quantity_Type;
	}
}
