package application.GUI;

import application.DatabaseManager;
import application.Item;
import application.Recipe;
import application.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RecipeList_AddRecipeItemsGUI {

	DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
	private Scene homepage_scene;
	private Stage addRecipeIngredientsStage;
	private Scene RecipeList_AddRecipeItemsGUI_scene;
	private TableView inventoryListTableView;
	private TableView mainInventoryListTable;
	
	public RecipeList_AddRecipeItemsGUI(User user, Recipe recipe, TableView mainInventoryListTable)
	{
		this.mainInventoryListTable = mainInventoryListTable;
		
		SimpleIngredientList_TableViewGUI simpleIngredListGUI = new SimpleIngredientList_TableViewGUI(user);
		RecipeIngredientList_TableViewGUI recipeIngredListGUI = new RecipeIngredientList_TableViewGUI(user, recipe);
		RecipeSteps_TableViewGUI recipeSteps_TableViewGUI = new RecipeSteps_TableViewGUI(user, recipe);
		
		inventoryListTableView = simpleIngredListGUI.getTableView();
		Button addIngredientToRecipe = new Button("Add Ingredient");
		
		TextField addItemNum = new TextField();
	    addItemNum.setPromptText("Item Number");
	    addItemNum.setFont(Font.font("Arial", FontWeight.BOLD, 10));
	    
	    TextField addItemName = new TextField();
	    addItemName.setPromptText("Item Name");
	    addItemName.setFont(Font.font("Arial", FontWeight.BOLD, 10));
	    
	    TextField addExpirationDate = new TextField();
	    addExpirationDate.setPromptText("Expiration Date");
	    addExpirationDate.setFont(Font.font("Arial", FontWeight.BOLD, 10));
	    
	    TextField addPARAmount = new TextField();
	    addPARAmount.setPromptText("PAR Amount");
	    addPARAmount.setFont(Font.font("Arial", FontWeight.BOLD, 10));
	  
	    TextField addQuantity = new TextField();
	    addQuantity.setPromptText("Quantity");
	    addQuantity.setFont(Font.font("Arial", FontWeight.BOLD, 10));
	    
	    TextField addAmount_Type = new TextField();
	    addAmount_Type.setPromptText("Amount_Type");
	    addAmount_Type.setFont(Font.font("Arial", FontWeight.BOLD, 10));
	    
	    Text errorMessage = new Text("");
		errorMessage.setFont(Font.font("Arial", FontWeight.THIN, FontPosture.ITALIC, 9));
		errorMessage.setFill(Color.RED); 
	    Button addIgredToRecipeIngredList = new Button("Add");
	    addIgredToRecipeIngredList.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
	    addIgredToRecipeIngredList.setCursor(Cursor.HAND);
	    
	    Button autoGenItemNumberBtn = new Button("Generate Item #");
		autoGenItemNumberBtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 10px; -fx-font-size: 9px; -fx-text-fill: #ffffff");
		autoGenItemNumberBtn.setCursor(Cursor.HAND);
		
		Button addIngredientToItemListBtn = new Button("Add New Item");
		addIngredientToItemListBtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		addIngredientToItemListBtn.setCursor(Cursor.HAND);
		
		HBox mainHBox = new HBox(simpleIngredListGUI.getVBox(), addIngredientToRecipe, recipeIngredListGUI.getVBox(), recipeSteps_TableViewGUI.getVBox() );
		mainHBox.setAlignment(Pos.CENTER);
		mainHBox.setSpacing(50);
		
		HBox newItemTextFields_1 = new HBox(addIngredientToItemListBtn, autoGenItemNumberBtn, addItemNum, addItemName, addExpirationDate);
		newItemTextFields_1.setAlignment(Pos.CENTER);
		HBox newItemTextFields_2 = new HBox( addPARAmount, addQuantity, addAmount_Type);
		newItemTextFields_2.setAlignment(Pos.CENTER);
		
		VBox newItemTextFields_vBox = new VBox(newItemTextFields_1, newItemTextFields_2);
		
		VBox mainVBox = new VBox();
		mainVBox.getChildren().addAll(mainHBox, errorMessage, newItemTextFields_vBox);
		mainVBox.setAlignment(Pos.CENTER);
		
		Rectangle inventoryList_background = new Rectangle(1200, 600);
		inventoryList_background.setArcHeight(40.0);
		inventoryList_background.setArcWidth(40.0);
		inventoryList_background.setFill(Color.web("#e3e3e3",1));
		
		StackPane stackpane = new StackPane(inventoryList_background, mainVBox);

		RecipeList_AddRecipeItemsGUI_scene = new Scene(stackpane);
		
		autoGenItemNumberBtn.setOnAction(e -> 
	    {
	    	addItemNum.setText(dbm.autogenerateItemNum(user));
	    	
	    } );
		
		addIngredientToItemListBtn.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) 
    		{
    			
    			if( addItemNum.getText().equals("")
    					|| addItemName.getText().equals("") 
    					|| addExpirationDate.getText().equals("") 
    					|| addPARAmount.getText().equals("") 
    					|| addQuantity.getText().equals("") 
						|| addAmount_Type.getText().equals("") )
    			{
    				System.out.println("User did not fill in all of the fields!");
    				errorMessage.setText("Please fill in all text fields!");
    			}
    			else
    			{
    				String item_num = addItemNum.getText();
    				String item_name = addItemName.getText(), 
    						item_Exp = addExpirationDate.getText(), 
    						item_Quantity_Type = addAmount_Type.getText();
    				String item_Par = addPARAmount.getText(), 
    						item_Quantity = addQuantity.getText();
    				
    				System.out.println("itemName: " + item_name + ", ExpDate: " + item_Exp
    									+ ", parAmount: " + item_Par + ", quantity: " + item_Quantity
    									+ ", amountType: " + item_Quantity_Type);
    			
    				Item tmpItem = new Item(item_num, item_name, item_Exp, item_Par, item_Quantity, item_Quantity_Type);
	    			
	    			
	    			Boolean successfulInsertion = dbm.insertIngredient(user, item_num, item_name, item_Exp, item_Par, item_Quantity, item_Quantity_Type);
	    		
	    			if(successfulInsertion)
	    			{
	    				inventoryListTableView.getItems().add(tmpItem);
	    				mainInventoryListTable.getItems().add(tmpItem);
	    				addItemNum.clear();
	    				addItemName.clear();
		    			addExpirationDate.clear();
		    			addPARAmount.clear();
		    			addQuantity.clear();
		    			addAmount_Type.clear();
		    			errorMessage.setText("");
	    			}
	    			else
	    			{
	    				errorMessage.setText("Failed to insert! Change Item Number!");
	    			}
    			}
    		}
	    });
		
		addIngredientToRecipe.setOnAction(new EventHandler<ActionEvent>() 
		{
    		@Override
    		public void handle(ActionEvent e) {
    			
    			recipeIngredListGUI.updateRecipeItemList(simpleIngredListGUI.getSelectedItems());
				
			}
	    });
	}  
	
	public Scene getScene()
	{
		return RecipeList_AddRecipeItemsGUI_scene;
	}
	
}
