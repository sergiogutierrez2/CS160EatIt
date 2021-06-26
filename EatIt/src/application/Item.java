package application;

import java.util.ArrayList;

public class Item{
	private int item_num;
	private String item_name;
	private String item_Exp;
	private int item_Quantity;
	private int item_Par;
	private String item_Quantity_Type;
	
	private ArrayList<Item> item_List = new ArrayList<Item>();
	public Item(int Ingredient_num,String Ingredient_name, String Expiration_date, int Par_amount, int quantity, String Amount_Type) {
		 item_num = Ingredient_num;
		 item_name = Ingredient_name;
		 item_Exp = Expiration_date;
		 item_Quantity = quantity;
		 item_Par = Par_amount;
		 item_Quantity_Type = Amount_Type;
	}
	@Override
	public String toString() { System.out.println(item_num+" "+item_name+" "+item_Exp+" "+item_Quantity+" "+ item_Par+" "+item_Quantity_Type);
	return (item_num+item_name+item_Exp+item_Quantity+ item_Par+item_Quantity_Type);}
	public void addItem(Item newItem)
	{
		item_List.add(newItem);
	}
	
}
