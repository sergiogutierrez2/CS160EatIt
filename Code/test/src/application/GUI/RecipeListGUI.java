package application.GUI;

import application.DatabaseManager;
import application.Recipe;
import application.User;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class RecipeListGUI {
	
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		Scene scene;
	
	    private double mainWidth = 500, mainHeight = 500;
	    private VBox vbox;
	    private ObservableList<Recipe> tableData;
	    private TableView tableView;
	    private User user;
	    
	    public RecipeListGUI(User user) {
	    	this.user = user;
	    	createTable();
	    }
	    
	    @SuppressWarnings({ "rawtypes", "unchecked" })
		public void createTable() {
			tableView = new TableView();
		    tableView.setEditable(true);
		    tableView.setBackground(null);
		    
		    TableViewSelectionModel<Recipe> selectionModel = tableView.getSelectionModel();
		    selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
		    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Removes extra column

		    // COlUMNS
		    TableColumn<Recipe, String> column0 = new TableColumn<>("Recipe\n#");
		    column0.setCellValueFactory(new PropertyValueFactory<>("recipe_num"));
		    column0.setCellFactory(TextFieldTableCell.<Recipe>forTableColumn());
		    column0.setMinWidth(40);
		    column0.setMaxWidth(40);
		    
		    
		    TableColumn<Recipe, String> column1 = new TableColumn<>("Recipe Name");
		    column1.setCellValueFactory(new PropertyValueFactory<>("recipe_name"));
		    column1.setCellFactory(TextFieldTableCell.<Recipe>forTableColumn());
		    column1.setMinWidth(80);
		    column1.setMaxWidth(80);
		  
		    //
		    TableColumn<Recipe, String> column2 = new TableColumn<>("Cook Time");
		    column2.setCellValueFactory(new PropertyValueFactory<>("cook_time"));
		    column2.setCellFactory(TextFieldTableCell.<Recipe>forTableColumn());
		    column2.setMinWidth(50);
		    column2.setMaxWidth(50);
		   
		    
		    TableColumn<Recipe, String> column3 = new TableColumn<>("Prep Time");
		    column3.setCellValueFactory(new PropertyValueFactory<>("prep_time"));
		    column3.setCellFactory(TextFieldTableCell.<Recipe>forTableColumn());
		    column3.setMinWidth(50);
		    column3.setMaxWidth(50);
		    
		    TableColumn<Recipe, String> column4 = new TableColumn<>("Exec?");
		    column4.setCellValueFactory(new PropertyValueFactory("executable"));
		    column4.setCellFactory(TextFieldTableCell.<Recipe>forTableColumn());
		    column4.setMinWidth(30);
		    column4.setMaxWidth(30);
		    
		    tableData = dbm.getCurrentRecipeList(user);
		    
		    tableView.getItems().addAll(tableData);
		    
		    tableView.getColumns().addAll(column0, column1, column2, column3, column4);
		    
		    
		    TextField addRecipeNumber = new TextField();
		    addRecipeNumber.setPromptText("Recipe Number");
		    addRecipeNumber.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		    
		    TextField addRecipeName = new TextField();
		    addRecipeName.setPromptText("Recipe Name");
		    addRecipeName.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		    
		    TextField addCookTime = new TextField();
		    addCookTime.setPromptText("Cook Time");
		    addCookTime.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		    
		    TextField addPrepTime = new TextField();
		    addPrepTime.setPromptText("Prep Time");
		    addPrepTime.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		    
		    Text errorMessage = new Text("");
			errorMessage.setFont(Font.font("Arial", FontWeight.THIN, FontPosture.ITALIC, 9));
			errorMessage.setFill(Color.RED); 
			
			Button autoGenRecipeNumberBtn = new Button("Generate Recipe #");
			autoGenRecipeNumberBtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 10px; -fx-font-size: 9px; -fx-text-fill: #ffffff");
			autoGenRecipeNumberBtn.setCursor(Cursor.HAND);
			    
		    
		    Button addButton = new Button("Add");
		    addButton.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		    addButton.setCursor(Cursor.HAND);
		    
		    
		    Button deleteButton = new Button("Delete");
		    deleteButton.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		    deleteButton.setCursor(Cursor.HAND);
		    
		    	    
		    HBox hb = new HBox();
		    hb.getChildren().addAll(addRecipeNumber, 
		    						addRecipeName, 
		    						addCookTime);
		    hb.setAlignment(Pos.CENTER);
		    HBox hb_2 = new HBox();
		    hb_2.getChildren().addAll(addPrepTime);
		    hb_2.setAlignment(Pos.CENTER);
		    
		    hb_2.setSpacing(5);
		    hb.setSpacing(5);
		    
		    HBox hb_1 = new HBox(autoGenRecipeNumberBtn, addButton, deleteButton);
		    hb_1.setAlignment(Pos.CENTER);
		    hb_1.setSpacing(5);
		    	    
		    vbox = new VBox(hb, hb_2, errorMessage, hb_1, tableView);
		    vbox.setSpacing(5);
		    vbox.setAlignment(Pos.CENTER);
		    vbox.setBackground(null);
		    
		    scene = new Scene(vbox, mainWidth, mainHeight);
		    
		    /* **********************************
		     * Event Listeners Start
		     * ********************************** */
		    
		    autoGenRecipeNumberBtn.setOnAction(e -> 
		    {
		    	addRecipeNumber.setText(dbm.autogenerateRecipeNum(user));
		    	
		    });
		    
		    addButton.setOnAction(new EventHandler<ActionEvent>() {
	    		@Override
	    		public void handle(ActionEvent e) {
	    			
	    			if( addRecipeNumber.getText().equals("")
	    					|| addRecipeName.getText().equals("") 
	    					|| addCookTime.getText().equals("") 
	    					|| addPrepTime.getText().equals("") )
	    			{
	    				System.out.println("User did not fill in all of the fields!");
	    				errorMessage.setText("Please fill in all text fields!");
	    			}
	    			else
	    			{
	    				String recipe_num = addRecipeNumber.getText(), recipe_name = addRecipeName.getText(), 
	    						cook_time = addCookTime.getText(), prep_time = addPrepTime.getText();	    				
	    				System.out.println("recipe_name: " + recipe_name + ", addCookTime: " + cook_time
	    									+ ", prep_time: " + prep_time);
	    			
	    				Recipe tmpRecipe = new Recipe(recipe_num, recipe_name, cook_time, prep_time);
		    			
	    				Boolean successfulInsertion = false;
	    				
	    				successfulInsertion = dbm.insertRecipe(user, recipe_num, recipe_name, cook_time, prep_time, "0");
		    		
	    				
		    			if(successfulInsertion)
		    			{
		    				tableView.getItems().add(tmpRecipe);
		    				addRecipeNumber.clear();
		    				addRecipeName.clear();
			    			addCookTime.clear();
			    			addPrepTime.clear();
			    			errorMessage.setText("");
		    			}
		    			else
		    			{
		    				errorMessage.setText("Failed to insert! Change Recipe Number!");
		    			}
	    			}
	    		}
		    });
		    
		    deleteButton.setOnAction(e -> 
		    {
		    	ObservableList<Recipe> tmpList = tableView.getSelectionModel().getSelectedItems();
		    	for(Recipe recipe : tmpList)
		    	{
		    		dbm.deleteRecipe(user, recipe.getRecipe_num());
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