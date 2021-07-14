package application.GUI;

import application.DatabaseManager;
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
    
    public RecipeIngredientList_TableViewGUI(User user, Recipe recipe) {
    	this.user = user;
    	this.recipe = recipe;
    	createTable();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void createTable() {
		tableView = new TableView();
	    tableView.setEditable(false);
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
	    
	    
	    TableColumn<RecipeItem, String> column1 = new TableColumn<>("Ingredient Name");
	    column1.setCellValueFactory(new PropertyValueFactory<>("item_name"));
	    column1.setCellFactory(TextFieldTableCell.<RecipeItem>forTableColumn());
	    column1.setMinWidth(80);
	    column1.setMaxWidth(80);
	  
	    TableColumn<RecipeItem, String> column2 = new TableColumn<>("Item Quantity");
	    column2.setCellValueFactory(new PropertyValueFactory<>("item_quantity"));
	    column2.setCellFactory(TextFieldTableCell.<RecipeItem>forTableColumn());
	    column2.setMinWidth(50);
	    column2.setMaxWidth(50);
	   
	    //make a dbm function to get a Olist of the ingredients for the recipe
	    tableData = dbm.getRecipesIngredientList(user, recipe);
	    
	    tableView.getItems().addAll(tableData);
	    
	    tableView.getColumns().addAll(column0, column1, column2);
	    
	    Text errorMessage = new Text("");
		errorMessage.setFont(Font.font("Arial", FontWeight.THIN, FontPosture.ITALIC, 9));
		errorMessage.setFill(Color.RED); 
		    
	    Button addButton = new Button("Add");
	    addButton.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
	    addButton.setCursor(Cursor.HAND);
	    
	    Button deleteButton = new Button("Delete");
	    deleteButton.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
	    deleteButton.setCursor(Cursor.HAND);
	    
	    HBox hb_1 = new HBox(addButton, deleteButton);
	    hb_1.setAlignment(Pos.CENTER);
	    hb_1.setSpacing(5);
	    	    
	    vbox = new VBox(errorMessage, hb_1, tableView);
	    vbox.setSpacing(5);
	    vbox.setAlignment(Pos.CENTER);
	    vbox.setBackground(null);
	    
	    scene = new Scene(vbox, mainWidth, mainHeight);
	    
	    /* **********************************
	     * Event Listeners Start
	     * ********************************** */
	    
	    addButton.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			
    			errorMessage.setText("Failed to insert!");
    		}
	    });
	    
	    deleteButton.setOnAction(e -> 
	    {
	    	ObservableList<RecipeItem> tmpList = tableView.getSelectionModel().getSelectedItems();
	    	for(RecipeItem recipeItem : tmpList)
	    	{
	    		dbm.deleteRecipeIngredient(user, recipe.getRecipe_num() ,recipeItem.getItem_name());
	    	}
	    	tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
	    });
	    
	    /* **********************************
	     * Event Listeners End
	     * ********************************** */
	}
    
    public Scene getScene()
    {
    	return scene;
    }
    
    public VBox getVBox()
    {
    	return vbox;
    }
    
    public ObservableList<Recipe> getCurrentInventory()
    {
    	return null;
    }
}