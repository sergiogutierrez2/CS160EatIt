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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;

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
		
		Text recipeNameTitle = new Text(recipe.getRecipe_name());
		
		recipeNameTitle.setFont(Font.font("Arial", FontWeight.THIN, FontPosture.ITALIC, 30));
		
		TextField addItemNum = new TextField();
	    addItemNum.setPromptText("Item Number");
	    addItemNum.setFont(Font.font("Arial", FontWeight.BOLD, 10));
	    addItemNum.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            if (!newValue.matches("\\d*")) {
	            	addItemNum.setText(newValue.replaceAll("[^\\d]", ""));
	            }

	            if (addItemNum.getText().length() > 8) {
	                String s = addItemNum.getText().substring(0, 8);
	                addItemNum.setText(s);
	            }
	        }
	    });
	    
	    TextField addItemName = new TextField();
	    addItemName.setPromptText("Item Name");
	    addItemName.setFont(Font.font("Arial", FontWeight.BOLD, 10));
	    addItemName.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {		            
	            if (addItemName.getText().length() > 14) {
	                String s = addItemName.getText().substring(0, 14);
	                addItemName.setText(s);
	            }
	        }
	    });

	    /* TextField addExpirationDate = new TextField();
	    addExpirationDate.setPromptText("Expiration Date");
	    addExpirationDate.setFont(Font.font("Arial", FontWeight.BOLD, 10)); */
	    
	    DatePicker addExpirationDate;
	    addExpirationDate = new DatePicker();
	    addExpirationDate.setPromptText("Expiration Date");
	    addExpirationDate.setMaxWidth(400);
	    addExpirationDate.setMaxHeight(100);
	    addExpirationDate.setMinHeight(005.);
	    addExpirationDate.setMinWidth(03.);
	    addExpirationDate.setPrefHeight(2.);
	    addExpirationDate.setPrefWidth(123.);
	    addExpirationDate.setStyle("-fx-font-size: 10 Arial");
	    //addExpirationDate.setStyle("-fx-pref-height: -10");
	    
	    TextField addPARAmount = new TextField();
	    addPARAmount.setPromptText("PAR Amount");
	    addPARAmount.setFont(Font.font("Arial", FontWeight.BOLD, 10));
	    addPARAmount.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            if (!newValue.matches("\\d*")) {
	            	addPARAmount.setText(newValue.replaceAll("[^\\d]", ""));
	            }

	            if (addPARAmount.getText().length() > 4) {
	                String s = addPARAmount.getText().substring(0, 4);
	                addPARAmount.setText(s);
	            }
	        }
	    });
	  
	    TextField addQuantity = new TextField();
	    addQuantity.setPromptText("Quantity");
	    addQuantity.setFont(Font.font("Arial", FontWeight.BOLD, 10));
	    addQuantity.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            if (!newValue.matches("\\d*")) {
	            	addQuantity.setText(newValue.replaceAll("[^\\d]", ""));
	            }

	            if (addQuantity.getText().length() > 4) {
	                String s = addQuantity.getText().substring(0, 4);
	                addQuantity.setText(s);
	            }
	        }
	    });
	    
	    TextField addAmount_Type = new TextField();
	    addAmount_Type.setPromptText("Amount_Type");
	    addAmount_Type.setFont(Font.font("Arial", FontWeight.BOLD, 10));
	    addAmount_Type.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {

	        	if (!newValue.matches("\\d*") || !newValue.matches("\\sa-zA-Z")) {
	        		addAmount_Type.setText(newValue.replaceAll("[^\\da-zA-Z]", ""));
	            }

	            if (addAmount_Type.getText().length() > 8) {
	                String s = addAmount_Type.getText().substring(0, 8);
	                addAmount_Type.setText(s);
	            }
	        }
	    });
	    
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
		mainVBox.getChildren().addAll(recipeNameTitle, mainHBox, errorMessage, newItemTextFields_vBox);
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
    					|| addExpirationDate.getEditor().getText().equals("") 
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
    						item_Exp = addExpirationDate.getEditor().getText(),
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
	    				addExpirationDate.setValue(null);
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
