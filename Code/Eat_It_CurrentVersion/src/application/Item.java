package application;


public class Item{
	private String item_name;
	private String item_Exp;
	private String item_Quantity;
	private String item_Par;
	private String item_Quantity_Type;
	
	public Item(String item_name, String item_Exp, 
				String item_Quantity, String item_Par, String item_Quantity_Type) 
	{
		 //item_num = Ingredient_num;
		 this.item_name = item_name;
		 this.item_Exp = item_Exp;
		 this.item_Quantity = item_Quantity;
		 this.item_Par = item_Par;
		 this.item_Quantity_Type = item_Quantity_Type;
	}
	
	@Override
	public String toString() 
	{  
//		System.out.println( item_name + " " 
//							+ item_Exp + " " 
//							+ item_Quantity + " "
//							+ item_Par + " "
//							+ item_Quantity_Type);
//		  
		return ("itemName: " + item_name 
				+  ", ExpDate: " + item_Exp
				+ ", quantity: " + item_Quantity 
				+ ", parAmount: " + item_Par 
				+ ", amountType: " + item_Quantity_Type );
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
	
	
}
