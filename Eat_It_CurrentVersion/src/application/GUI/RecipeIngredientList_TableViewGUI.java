package application.GUI;

import application.DatabaseManager;
import application.Item;
import application.Recipe;
import application.RecipeItem;
import application.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Java Class that manages the user's Ingredient list GUI.
 * 
 * @author Eat_It(Summer 2021 Team)
 */
public class RecipeIngredientList_TableViewGUI 
{
	DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
	Scene scene;

    private double mainWidth = 500, mainHeight = 500;
    private VBox vbox;
    private ObservableList<RecipeItem> tableData;
    private TableView tableView;
    private User user;
    private Recipe recipe;
    private Text errorMessage;
    private TableView simpleIngredientTableView;
    private ExecutableAndNotExecGUI_View executableAndNotExecGUI_View;
    
    /**
	 * This is the constructor for the RecipeIngredientList_TableViewGUI class that accepts
	 * a user, recipe, and view of the executable and not executable recipes.
	 * @param user The user that signed in.
	 * @param recipe The recipe of the user.
	 * @param executableAndNotExecGUI_View The view of the recipes that are executable and not executable.
	 */
    public RecipeIngredientList_TableViewGUI(User user, Recipe recipe, ExecutableAndNotExecGUI_View executableAndNotExecGUI_View) 
    {
    	this.user = user;
    	this.recipe = recipe;
    	this.executableAndNotExecGUI_View = executableAndNotExecGUI_View;
    	createTable();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void createTable() 
    {
		tableView = new TableView();
	    tableView.setEditable(true);
	    tableView.setBackground(null);
	    
	    TableViewSelectionModel<RecipeItem> selectionModel = tableView.getSelectionModel();
	    selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
	    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Removes extra column

	    // COlUMNS
	    TableColumn<RecipeItem, String> column0 = new TableColumn<>("Recipe\n#");
	    column0.setCellValueFactory(new PropertyValueFactory<>("recipe_num"));
	    column0.setCellFactory(TextFieldTableCell.<RecipeItem>forTableColumn());
	    column0.setMinWidth(50);
	    column0.setMaxWidth(50);
	    column0.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<RecipeItem, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<RecipeItem, String> t) {
                t.getRowValue().setRecipe_num(t.getOldValue());
        		tableView.getItems().set(t.getTablePosition().getRow(), t.getRowValue()); 
            }
        });
	    
	    TableColumn<RecipeItem, String> column1 = new TableColumn<>("Ingredient Name");
	    column1.setCellValueFactory(new PropertyValueFactory<>("item_name"));
	    column1.setCellFactory(TextFieldTableCell.<RecipeItem>forTableColumn());
	    column1.setMinWidth(100);
	    column1.setMaxWidth(100);
	    column1.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<RecipeItem, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<RecipeItem, String> t) {
                t.getRowValue().setItem_name(t.getOldValue());
        		tableView.getItems().set(t.getTablePosition().getRow(), t.getRowValue()); 
            }
        });
	  
	    TableColumn<RecipeItem, String> column2 = new TableColumn<>("Item Quantity");
	    column2.setCellValueFactory(new PropertyValueFactory<>("item_quantity"));
	    column2.setCellFactory(TextFieldTableCell.<RecipeItem>forTableColumn());
	    column2.setMinWidth(90);
	    column2.setMaxWidth(90);
	    column2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<RecipeItem, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<RecipeItem, String> t) {
                
            	// t.getNewValue() this is what needs to be filtered.
            	String s = t.getNewValue().toString();
            	
            	try
            	{
            		int x = Integer.parseInt(s);
            		
            		if(x > 0)
            		{
            			t.getRowValue().setItem_quantity(t.getNewValue()); //changes cell object's value to new entry
                        
                        tableView.getItems().set(t.getTablePosition().getRow(), t.getRowValue()); //changes view of table to reflect changes
                        
                        dbm.deleteRecipeIngredient(user, t.getRowValue().getRecipe_num(), t.getRowValue().getItem_num());
                        
                        dbm.insertRecipeIngredient(user, t.getRowValue().getRecipe_num(), t.getRowValue().getItem_num(), 
                        							t.getRowValue().getItem_name(), t.getRowValue().getItem_quantity());
                        
                        dbm.getCurrentInventory(user);
                        
                        dbm.updateExecutableRecipes(user);
                        executableAndNotExecGUI_View.updateTables();
            		}
            		else
            		{
            			t.getRowValue().setItem_quantity(t.getOldValue());
                		tableView.getItems().set(t.getTablePosition().getRow(), t.getRowValue()); 
            		}
            		
            	}
            	catch(NumberFormatException exception)
            	{
            		System.out.println("Failed to parse int. Don't allow edit!");
            		t.getRowValue().setItem_quantity(t.getOldValue());
            		tableView.getItems().set(t.getTablePosition().getRow(), t.getRowValue()); 
            	}
            }
        });
	   
	    //make a dbm function to get a Olist of the ingredients for the recipe
	    tableData = dbm.getRecipesIngredientList(user, recipe);
	    
	    tableView.getItems().addAll(tableData);
	    
	    tableView.getColumns().addAll(column0, column1, column2);
	    
	    errorMessage = new Text("");
		errorMessage.setFont(Font.font("Arial", FontWeight.THIN, FontPosture.ITALIC, 9));
		errorMessage.setFill(Color.RED); 
	    
	    
	    	    
	    vbox = new VBox(errorMessage, tableView);
	    vbox.setSpacing(5);
	    vbox.setAlignment(Pos.CENTER);
	    vbox.setBackground(null);
	    
	    scene = new Scene(vbox, mainWidth, mainHeight);
	    
	    /* **********************************
	     * Event Listeners Start
	     * ********************************** */
	    
	    
	    /* **********************************
	     * Event Listeners End
	     * ********************************** */
	}
    
    /**
	 * This method returns the table view of the class.
	 * @return The table view of the class.
	 */
    public TableView getTableView()
    {
    	return tableView;
    	
    }
    
    /**
	 * This method returns the scene of the class.
	 * @return The scene of the class.
	 */
    public Scene getScene()
    {
    	return scene;
    }
    
    /**
	 * This method returns the VBox of the class.
	 * @return The VBox of the class.
	 */
    public VBox getVBox()
    {
    	return vbox;
    }
    
    /**
	 * This method returns null.
	 * @return Returns null.
	 */
    public ObservableList<Recipe> getCurrentInventory()
    {
    	return null;
    }
    
    /**
     * This function takes in the selected items 
     * from the simple ingredient list and converts them in to 
     * recipe item type for insertion into the recipe's ingredient table.
     * @param selectedList This is the selected items from simpleIngredient chart
     */
    public void updateRecipeItemList(ObservableList<Item> selectedList)
    {
    	//convert each item to a recipe item and add one by one
    	//to the database... only if they are successfull inserted into 
    	//the db then add them to the table view.
    	for(Item item : selectedList)
    	{
    		if(dbm.insertRecipeIngredient(user, recipe.getRecipe_num(), item.getItem_num(), item.getItem_name(), "1"))
    		{
    			tableView.getItems().add(new RecipeItem(recipe.getRecipe_num(), item.getItem_num(), item.getItem_name(), "1"));
    		}
    		else {
    			errorMessage.setText("Failed to insert one or more item: Duplicate error");
    		}
    	}
    	
    }
}