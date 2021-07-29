package application.GUI;

import application.DatabaseManager;
import application.Item;
import application.Recipe;
import application.RecipeStep;
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
import javafx.scene.control.TextArea;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * TODO: Write a description of the class here.
 * 
 * @author Eat_It(Summer 2021 Team)
 */
public class RecipeSteps_TableViewGUI 
{
	DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
	Scene scene;

    private double mainWidth = 500, mainHeight = 500;
    private VBox vbox;
    private ObservableList<RecipeStep> tableData;
    private TableView tableView;
    private User user;
    private Recipe recipe;
    
    //Text Fields:
    private TextField addStep;
    private TextArea addDesc;
    
    private Text errorMessage;
    
    private Button addButton;
    private Button deleteButton;
    
    
    public RecipeSteps_TableViewGUI(User user, Recipe recipe) {
    	this.user = user;
    	this.recipe = recipe;
    	createTable();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void createTable() {
    	tableView = new TableView();
    	tableView.setEditable(true);
    	tableView.setBackground(null);

    	TableViewSelectionModel<RecipeStep> selectionModel = tableView.getSelectionModel();
    	selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Removes extra column

    	// COlUMNS
    	TableColumn<RecipeStep, String> column0 = new TableColumn<>("Step\n#");
    	column0.setCellValueFactory(new PropertyValueFactory<>("step_num"));
    	column0.setCellFactory(TextFieldTableCell.<RecipeStep>forTableColumn());
    	column0.setMinWidth(50);
    	column0.setMaxWidth(50);

    	TableColumn<RecipeStep, String> column1 = new TableColumn<>("Description");
    	column1.setCellValueFactory(new PropertyValueFactory<>("step_desc"));
	    column1.setCellFactory(TextFieldTableCell.<RecipeStep>forTableColumn());
	    column1.setMinWidth(150);
	    column1.setMaxWidth(150);
	  
	    tableData = dbm.getRecipesStepList(user, recipe);
	    
	    tableView.getItems().addAll(tableData);
	    
	    tableView.getColumns().addAll(column0, column1);
	    
	    addButton = new Button("Add Step");
	    addButton.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
	    addButton.setCursor(Cursor.HAND);
	    
	    deleteButton = new Button("Delete Selected");
	    deleteButton.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
	    deleteButton.setCursor(Cursor.HAND);
	    
	    addStep = new TextField();
	    addStep.setMaxWidth(130);
	    addStep.setPromptText("Enter Step Number");
	    addStep.setFont(Font.font("Arial", FontWeight.BOLD, 10));
	    addStep.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            if (!newValue.matches("\\d*")) {
	            	addStep.setText(newValue.replaceAll("[^\\d]", ""));
	            }

	            if (addStep.getText().length() > 3) {
	                String s = addStep.getText().substring(0, 3);
	                addStep.setText(s);
	            }
	        }
	    });
	    
	    addDesc = new TextArea();
	    addDesc.setPromptText("Enter a Description of the Step");
	    addDesc.setFont(Font.font("Arial", FontWeight.BOLD, 10));
	    addDesc.setMaxHeight(40);
	    addDesc.setMaxWidth(350);
	    addDesc.setWrapText(true);
	    addDesc.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {

	            if (addDesc.getText().length() > 140) {
	                String s = addDesc.getText().substring(0, 140);
	                addDesc.setText(s);
	            }
	        }
	    });
	    
	    errorMessage = new Text("");
		errorMessage.setFont(Font.font("Arial", FontWeight.THIN, FontPosture.ITALIC, 9));
		errorMessage.setFill(Color.RED); 
	    
	    
	    HBox hbox = new HBox(addButton, deleteButton);
	    hbox.setAlignment(Pos.CENTER);
	    VBox addStep_VBox = new VBox(errorMessage, hbox, addStep, addDesc);
	    addStep_VBox.setAlignment(Pos.CENTER);
	    addStep_VBox.setSpacing(5);
	    
	    tableView.setMaxHeight(300);
	    
	    vbox = new VBox(addStep_VBox, tableView);
	    vbox.setSpacing(5);
	    vbox.setAlignment(Pos.CENTER);
	    vbox.setBackground(null);
	    vbox.setMaxHeight(300);
	    
	    
	    
	    scene = new Scene(vbox, mainWidth, mainHeight);
	    
	    
	    
	    /* **********************************
	     * Event Listeners Start
	     * ********************************** */
	  
    	addButton.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			
    			if( addStep.getText().equals("")
    					|| addDesc.getText().equals("") )
    			{
    				System.out.println("User did not fill in all of the fields!");
    				errorMessage.setText("Please fill in all text fields!");
    			}
    			else
    			{
    				String recipe_step = addStep.getText();
    				String step_desc = addDesc.getText();
    						
    				
    				System.out.println("recipe_step: " + recipe_step + ", step_desc: " + step_desc);
    			
    				RecipeStep tmpRecipeStep = new RecipeStep(recipe.getRecipe_num(), recipe_step, step_desc);
	    			
	    			
	    			Boolean successfulInsertion = dbm.insertRecipeSteps(user, recipe.getRecipe_num(), recipe_step, step_desc);
	    		
	    			if(successfulInsertion)
	    			{
	    				tableView.getItems().add(tmpRecipeStep);
	    				addStep.clear();
	    				addDesc.clear();
		    			errorMessage.setText("");
	    			}
	    			else
	    			{
	    				errorMessage.setText("Failed to insert! Change Item Number!");
	    			}
    			}
    		}
	    });
    
    	deleteButton.setOnAction(e -> 
	    {
	    	ObservableList<RecipeStep> tmpList = tableView.getSelectionModel().getSelectedItems();
	    	for(RecipeStep recipeStep : tmpList)
	    	{
	    		dbm.deleteRecipeSteps(user, recipe.getRecipe_num(), recipeStep.getStep_num());
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
    
    public ObservableList<Item> getCurrentInventory()
    {
    	return null;
    }
    
    
    
    public TableView getTableView()
    {
    	return tableView;
    }
    
    public ObservableList<Item> getSelectedItems()
    {
    	return tableView.getSelectionModel().getSelectedItems();
    }

}
