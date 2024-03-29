package application.GUI;

import application.DatabaseManager;
import application.Item;
import application.Recipe;
import application.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.scene.input.MouseEvent;
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

/**
 * This class handles the GUI of the main recipe table of all the recipes the current user has in database.
 * @author Eat_It(Summer 2021 Team)
 */
public class RecipeListGUI {
	
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		Scene scene;
	
	    private double mainWidth = 500, mainHeight = 500;
	    private VBox vbox;
	    private ObservableList<Recipe> tableData;
	    private TableView tableView;
	    private User user;
	    private Recipe currentRecipe;
	    private TableView mainInventoryListTable;
	    private ExecutableAndNotExecGUI_View executableAndNotExecGUI_View;
	    private Text errorMessage;
	    private StackPane stackPaneFromHomePage;
	    private Rectangle blockStageBackground;
	    private Stage addIngredToRecipeStage;

	    /**
	     * The constructor has access to the current user, an inventory list and the executable or not GUI.
	     * The Constructor method also is responsible for creating the table, textfields. and buttons in the view
	     * the Buttons and texfiles have listeners that execute once clicked
	     * @param user This hold an object that references the current user. 
	     * @param mainInventoryListTable This is a TableView object that hold the data for the current inventory of the user.
	     * @param executableANdNot This is an object of the GUI class that handles recipes that are executable and not.
	     */
	    public RecipeListGUI(User user, TableView mainInventoryListTable, ExecutableAndNotExecGUI_View executableAndNotExecGUI_View) {
	    	this.user = user;
	    	this.mainInventoryListTable = mainInventoryListTable;
	    	this.executableAndNotExecGUI_View = executableAndNotExecGUI_View;
	    	addIngredToRecipeStage = new Stage();
	    	createTable();
	    }
	    
	    /**
	     * This method creates a table to hold all the recipes.
	     * The table is editable and the event listener assures the new data entered is within the constraints.
	     */  
	    @SuppressWarnings({ "rawtypes", "unchecked" })
		public void createTable() {
			tableView = new TableView();
		    tableView.setEditable(true);
		    tableView.setBackground(null);
		    
		    TableViewSelectionModel<Recipe> selectionModel = tableView.getSelectionModel();
		    selectionModel.setSelectionMode(SelectionMode.SINGLE);
		    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Removes extra column

		    // COlUMNS
		    TableColumn<Recipe, String> column0 = new TableColumn<>("Recipe n#");
		    column0.setCellValueFactory(new PropertyValueFactory<>("recipe_num"));
		    column0.setCellFactory(TextFieldTableCell.<Recipe>forTableColumn());
		    column0.setMinWidth(70);
		    column0.setMaxWidth(70);
		    column0.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Recipe, String>>() {
	            @Override
	            public void handle(TableColumn.CellEditEvent<Recipe, String> t) {
	                
	            	// t.getNewValue() this is what needs to be filtered.
	            	try
	            	{
	            		int x = Integer.parseInt(t.getNewValue().toString());
	            		
	            		if( dbm.containsRecipeNum(user, Integer.toString(x)) )
	            		{
	            			
	            			 t.getRowValue().setRecipe_num(t.getOldValue());
	            			 tableView.getItems().set(t.getTablePosition().getRow(), t.getRowValue());
	            			 errorMessage.setText("Recipe number is taken already!");
	            		}
	            		else
	            		{
	            			t.getRowValue().setRecipe_num(Integer.toString(x)); //changes cell object's value to new entry
	     	                
	     	                tableView.getItems().set(t.getTablePosition().getRow(), t.getRowValue()); //changes view of table to reflect changes
	     	                
	     	                dbm.deleteRecipe( user, t.getRowValue().getRecipe_num() );
	     	                
	     	                dbm.insertRecipe( user,  Integer.toString(x), t.getRowValue().getRecipe_name(),
	     	                		t.getRowValue().getCook_time(), t.getRowValue().getPrep_time(), t.getRowValue().getExecutable() );	
	     	                
	     	                dbm.getCurrentInventory(user);
	            		}
	            	}
	            	catch(NumberFormatException e)
	            	{
	            		System.out.println("Not a valid recipe num!");
	            	}
	            	
	               
	                
	            }
	        });
		    
		    
		    TableColumn<Recipe, String> column1 = new TableColumn<>("Recipe Name");
		    column1.setCellValueFactory(new PropertyValueFactory<>("recipe_name"));
		    column1.setCellFactory(TextFieldTableCell.<Recipe>forTableColumn());
		    column1.setMinWidth(120);
		    column1.setMaxWidth(120);
		    column1.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Recipe, String>>() {
	            @Override
	            public void handle(TableColumn.CellEditEvent<Recipe, String> t) {
	                
	            	// t.getNewValue() this is what needs to be filtered.
	            	
	                t.getRowValue().setRecipe_name(t.getNewValue()); //changes cell object's value to new entry
	                
	                tableView.getItems().set(t.getTablePosition().getRow(), t.getRowValue()); //changes view of table to reflect changes
	                
	                dbm.deleteRecipe( user, t.getRowValue().getRecipe_num() );
	                
	                dbm.insertRecipe( user, t.getRowValue().getRecipe_num(), t.getRowValue().getRecipe_name(),
	                		t.getRowValue().getCook_time(), t.getRowValue().getPrep_time(), t.getRowValue().getExecutable() );	
	                
	                dbm.getCurrentInventory(user);
	                
	                dbm.updateExecutableRecipes(user);
	                executableAndNotExecGUI_View.updateTables();
	                
	            }
	        });
		  
		    //
		    TableColumn<Recipe, String> column2 = new TableColumn<>("Cook Time");
		    column2.setCellValueFactory(new PropertyValueFactory<>("cook_time"));
		    column2.setCellFactory(TextFieldTableCell.<Recipe>forTableColumn());
		    column2.setMinWidth(80);
		    column2.setMaxWidth(80);
		    column2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Recipe, String>>() {
	            @Override
	            public void handle(TableColumn.CellEditEvent<Recipe, String> t) {
	                
	            	// t.getNewValue() this is what needs to be filtered.
	            	
	                t.getRowValue().setCook_time(t.getNewValue()); //changes cell object's value to new entry
	                
	                tableView.getItems().set(t.getTablePosition().getRow(), t.getRowValue()); //changes view of table to reflect changes
	                
	                dbm.deleteRecipe( user, t.getRowValue().getRecipe_num() );
	                
	                dbm.insertRecipe( user, t.getRowValue().getRecipe_num(), t.getRowValue().getRecipe_name(),
	                		t.getRowValue().getCook_time(), t.getRowValue().getPrep_time(), t.getRowValue().getExecutable() );	
	                
	                dbm.getCurrentInventory(user);
	                
	                dbm.updateExecutableRecipes(user);
	                executableAndNotExecGUI_View.updateTables();
	            }
	        });
		   
		    
		    TableColumn<Recipe, String> column3 = new TableColumn<>("Prep Time");
		    column3.setCellValueFactory(new PropertyValueFactory<>("prep_time"));
		    column3.setCellFactory(TextFieldTableCell.<Recipe>forTableColumn());
		    column3.setMinWidth(80);
		    column3.setMaxWidth(80);
		    column3.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Recipe, String>>() {
	            @Override
	            public void handle(TableColumn.CellEditEvent<Recipe, String> t) {
	                
	            	// t.getNewValue() this is what needs to be filtered.
	            	
	                t.getRowValue().setPrep_time(t.getNewValue()); //changes cell object's value to new entry
	                
	                tableView.getItems().set(t.getTablePosition().getRow(), t.getRowValue()); //changes view of table to reflect changes
	                
	                dbm.deleteRecipe( user, t.getRowValue().getRecipe_num() );
	                
	                dbm.insertRecipe( user, t.getRowValue().getRecipe_num(), t.getRowValue().getRecipe_name(),
	                		t.getRowValue().getCook_time(), t.getRowValue().getPrep_time(), t.getRowValue().getExecutable() );	
	                
	                dbm.getCurrentInventory(user);
	                
	                dbm.updateExecutableRecipes(user);
	                executableAndNotExecGUI_View.updateTables();
	            }
	        });
		    
//		    TableColumn<Recipe, String> column4 = new TableColumn<>("Exec?");
//		    column4.setCellValueFactory(new PropertyValueFactory("executable"));
//		    column4.setCellFactory(TextFieldTableCell.<Recipe>forTableColumn());
//		    column4.setMinWidth(30);
//		    column4.setMaxWidth(30);
//		    column4.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Recipe, String>>() {
//	            @Override
//	            public void handle(TableColumn.CellEditEvent<Recipe, String> t) {
//	                
//	            	// t.getNewValue() this is what needs to be filtered.
//	            	
//	                t.getRowValue().setRecipe_num(t.getNewValue()); //changes cell object's value to new entry
//	                
//	                tableView.getItems().set(t.getTablePosition().getRow(), t.getRowValue()); //changes view of table to reflect changes
//	                
//	                dbm.deleteRecipe( user, t.getRowValue().getRecipe_num() );
//	                
//	                dbm.insertRecipe( user, t.getRowValue().getRecipe_num(), t.getRowValue().getRecipe_name(),
//	                		t.getRowValue().getCook_time(), t.getRowValue().getPrep_time(), t.getRowValue().getExecutable() );	
//	                
//	                dbm.getCurrentInventory(user);
//	                
//	            }
//	        });
		    
		    tableData = dbm.getCurrentRecipeList(user);
		    
		    tableView.getItems().addAll(tableData);
		    
		    tableView.getColumns().addAll(column0, column1, column2, column3);
		    
		    
		    TextField addRecipeNumber = new TextField();
		    addRecipeNumber.setPromptText("Recipe Number");
		    addRecipeNumber.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		    addRecipeNumber.textProperty().addListener(new ChangeListener<String>() {
		        @Override
		        public void changed(ObservableValue<? extends String> observable, String oldValue, 
		            String newValue) {		
		            if (!newValue.matches("\\d*")) {
		            	addRecipeNumber.setText(newValue.replaceAll("[^\\d]", ""));
		            }
		        	
		            if (addRecipeNumber.getText().length() > 4) {
		                String s = addRecipeNumber.getText().substring(0, 4);
		                addRecipeNumber.setText(s);
		            }
		        }
		    });
		    
		    TextField addRecipeName = new TextField();
		    addRecipeName.setPromptText("Recipe Name");
		    addRecipeName.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		    addRecipeName.textProperty().addListener(new ChangeListener<String>() {
		        @Override
		        public void changed(ObservableValue<? extends String> observable, String oldValue, 
		            String newValue) {				        	
		            if (addRecipeName.getText().length() > 14) {
		                String s = addRecipeName.getText().substring(0, 14);
		                addRecipeName.setText(s);
		            }
		        }
		    });
		    
		    TextField addCookTime = new TextField();
		    addCookTime.setPromptText("Cook Time");
		    addCookTime.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		    addCookTime.textProperty().addListener(new ChangeListener<String>() {
		        @Override
		        public void changed(ObservableValue<? extends String> observable, String oldValue, 
		            String newValue) {		

		            if (addCookTime.getText().length() > 12) {
		                String s = addCookTime.getText().substring(0, 12);
		                addCookTime.setText(s);
		            }
		        }
		    });
		    
		    TextField addPrepTime = new TextField();
		    addPrepTime.setPromptText("Prep Time");
		    addPrepTime.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		    addPrepTime.textProperty().addListener(new ChangeListener<String>() {
		        @Override
		        public void changed(ObservableValue<? extends String> observable, String oldValue, 
		            String newValue) {		
		        	
		            if (addPrepTime.getText().length() > 8) {
		                String s = addPrepTime.getText().substring(0, 8);
		                addPrepTime.setText(s);
		            }
		        }
		    });
		    
		    errorMessage = new Text("");
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
		    
		    Button addRecipeIngredients = new Button("Add Ingredients");
		    addRecipeIngredients.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		    addRecipeIngredients.setCursor(Cursor.HAND);
		    
		    TextField searchRecipe = new TextField();
		    searchRecipe.setPromptText("Search Recipe");
		    searchRecipe.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		    
		    Button searchRecipebtn = new Button("Search");
		    searchRecipebtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		    searchRecipebtn.setCursor(Cursor.HAND);
		    
		    	    
		    HBox hb = new HBox();
		    hb.getChildren().addAll(addRecipeNumber, 
		    						addRecipeName, 
		    						addCookTime);
		    hb.setAlignment(Pos.CENTER);
		    HBox hb_2 = new HBox();
		    hb_2.getChildren().addAll(autoGenRecipeNumberBtn, addPrepTime);
		    hb_2.setAlignment(Pos.CENTER_LEFT);
		    
		    HBox hb_3 = new HBox();
		    hb_3.getChildren().addAll(searchRecipe, searchRecipebtn);
		    hb_3.setAlignment(Pos.CENTER);
		    
		    hb_3.setSpacing(5);
		    
		    hb_2.setSpacing(5);
		    hb.setSpacing(5);
		    
		    HBox hb_1 = new HBox(addButton, deleteButton, addRecipeIngredients);
		    hb_1.setAlignment(Pos.CENTER);
		    hb_1.setSpacing(5);
		    hb_1.setPadding(new Insets(0, 0, 10, 0));
		    	    
		    vbox = new VBox(hb, hb_2, errorMessage, hb_1, hb_3, tableView);
		    vbox.setSpacing(5);
		    vbox.setAlignment(Pos.CENTER);
		    vbox.setBackground(null);
		    
		    scene = new Scene(vbox, mainWidth, mainHeight);
		    
		    /* **********************************
		     * Event Listeners Start
		     * ********************************** */
		    
		    autoGenRecipeNumberBtn.setOnAction(e -> 
		    {
		    	errorMessage.setText("");
		    	
		    	addRecipeNumber.setText(dbm.autogenerateRecipeNum(user));
		    	
		    });
		    
		    autoGenRecipeNumberBtn.setOnMouseEntered(new EventHandler<MouseEvent>() 
			{
				 @Override
			    public void handle(MouseEvent t) {
					 autoGenRecipeNumberBtn.setStyle("-fx-background-color: #C792DF; -fx-background-radius: 10px; -fx-font-size: 9px; -fx-text-fill: #ffffff");
			    }
			});
		    
		    autoGenRecipeNumberBtn.setOnMouseExited(new EventHandler<MouseEvent>() 
			{
				 @Override
			    public void handle(MouseEvent t) {
					 autoGenRecipeNumberBtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 10px; -fx-font-size: 9px; -fx-text-fill: #ffffff");
			    }
			});
		    
		    addRecipeIngredients.setOnAction(e -> 
		    {
		    	//if the input fields are all filled then that means that 
		    	//the user wants to add ingredients to the recipe that they are 
		    	//currently working on.
		    	errorMessage.setText("");
		    	currentRecipe = null;
		    	
		    	if( addRecipeNumber.getText().equals("")
    					|| addRecipeName.getText().equals("") 
    					|| addCookTime.getText().equals("") 
    					|| addPrepTime.getText().equals("") )
    			{
		    		//check if they selected something
		    		ObservableList<Recipe> tmpList = tableView.getSelectionModel().getSelectedItems();
			    	for(Recipe recipe : tmpList)
			    	{
			    		currentRecipe = recipe;
			    	}
			    	//if they did not select anything
			    	if(currentRecipe == null)
			    	{
			    		errorMessage.setText("Select a recipe or enter data in all the fields");
			    	}
			    	else 
			    	{
			    		//they did select something
		    			RecipeList_AddRecipeItemsGUI popUpMenu = new RecipeList_AddRecipeItemsGUI(user, currentRecipe, mainInventoryListTable, executableAndNotExecGUI_View);
		    			
		    			addIngredToRecipeStage.setScene(popUpMenu.getScene());
		    			addIngredToRecipeStage.setTitle("Add Ingredients Pop Up");
		    			addIngredToRecipeStage.show();
		    			stackPaneFromHomePage.getChildren().add(blockStageBackground);
			    	}
    			}
		    	else
		    	{
		    		//if they filled out all of the fields and then pressed addIngredients
		    		//then this section should add the recipe to the database if possible
		    		//and then open the stage with the to add ingredients.
		    		String recipe_num = addRecipeNumber.getText().toString(), recipe_name = addRecipeName.getText().toString(), 
    						cook_time = addCookTime.getText().toString(), prep_time = addPrepTime.getText().toString();	
		    		
		    		//check if recipe already exists
		    		if( !(dbm.containsRecipeNum(user, recipe_num)) )
		    		{
		    			//if it does not, then add recipe to database.
		    			currentRecipe = new Recipe(recipe_num, recipe_name, cook_time, prep_time);
			    		
			    		Boolean successfulInsertion = false;
	    				
	    				successfulInsertion = dbm.insertRecipe(user, recipe_num, recipe_name, cook_time, prep_time, "0");
		    		
	    				
		    			if(successfulInsertion)
		    			{
		    				tableView.getItems().add(currentRecipe);
		    				addRecipeNumber.clear();
		    				addRecipeName.clear();
			    			addCookTime.clear();
			    			addPrepTime.clear();
			    			errorMessage.setText("");
			    			
			    			RecipeList_AddRecipeItemsGUI popUpMenu = new RecipeList_AddRecipeItemsGUI(user, currentRecipe, mainInventoryListTable, executableAndNotExecGUI_View);
			    			addIngredToRecipeStage.setScene(popUpMenu.getScene());
			    			addIngredToRecipeStage.setTitle("Add Ingredients Pop Up");
			    			addIngredToRecipeStage.show();
			    			blockStageBackground.setStyle("-fx-background-color: grey; -fx-opacity: 0.75;");
			    			stackPaneFromHomePage.getChildren().add(blockStageBackground);
		    			}
		    			else
		    			{
		    				errorMessage.setText("Failed to insert");
		    			}
		    		}
		    		else
		    		{
		    			errorMessage.setText("This recipe number already exists! Choose another!");
		    		}
		    		
		    	}
		    	
		    	//if not all of the input fields are full, then it means that 
		    	//they might not want to add recipe ingredients to the current 
		    	//recipe that they are working on.
		    			
		    			//in this case we should check to see if they selected
		    			//any recipe currently in the list because that means that
		    			//they probably want to add recipe ingredients to the selected recipe
		    	
		    });
		    
		    addIngredToRecipeStage.setOnHiding( event -> { stackPaneFromHomePage.getChildren().remove(blockStageBackground);} );

		    
		    addRecipeIngredients.setOnMouseEntered(new EventHandler<MouseEvent>() 
			{
				 @Override
			    public void handle(MouseEvent t) {
					 addRecipeIngredients.setStyle("-fx-background-color: #C792DF; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
			    }
			});
		    
		    addRecipeIngredients.setOnMouseExited(new EventHandler<MouseEvent>() 
			{
				 @Override
			    public void handle(MouseEvent t) {
					 addRecipeIngredients.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
			    }
			});
		    
		    addButton.setOnAction(new EventHandler<ActionEvent>() {
	    		@Override
	    		public void handle(ActionEvent e) {
	    			errorMessage.setText("");
	    			
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
			    			dbm.updateExecutableRecipes(user);
					    	executableAndNotExecGUI_View.updateTables();
		    			}
		    			else
		    			{
		    				errorMessage.setText("Failed to insert! Change Recipe Number!");
		    			}
	    			}
	    			
	    		}
		    });
		    
		    addButton.setOnMouseEntered(new EventHandler<MouseEvent>() 
			{
				 @Override
			    public void handle(MouseEvent t) {
					 addButton.setStyle("-fx-background-color: #C792DF; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
			    }
			});
		    
		    addButton.setOnMouseExited(new EventHandler<MouseEvent>() 
			{
				 @Override
			    public void handle(MouseEvent t) {
					 addButton.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
			    }
			});
		    
		    deleteButton.setOnAction(e -> 
		    {
		    	errorMessage.setText("");
		    	
		    	ObservableList<Recipe> tmpList = tableView.getSelectionModel().getSelectedItems();
		    	for(Recipe recipe : tmpList)
		    	{
		    		dbm.deleteRecipe(user, recipe.getRecipe_num());
		    	}
		    	tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
		    	
		    	//this is where database needs to update ingredientlist tables and recipe steps tables.
		    	//get recipe number of what was deleted.
		    		//deleteIngedientList()
		    		//dbm.deleteRecipeSteps(User user, String recipe_num)
		    	dbm.updateExecutableRecipes(user);
		    	executableAndNotExecGUI_View.updateTables();
		    });
		    
		    deleteButton.setOnMouseEntered(new EventHandler<MouseEvent>() 
			{
				 @Override
			    public void handle(MouseEvent t) {
					 deleteButton.setStyle("-fx-background-color: #C792DF; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
			    }
			});
		    
		    deleteButton.setOnMouseExited(new EventHandler<MouseEvent>() 
			{
				 @Override
			    public void handle(MouseEvent t) {
					 deleteButton.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
			    }
			});
		    
		    searchRecipebtn.setOnAction(e -> 
		    {
		    	ObservableList<Recipe> tmpList = dbm.getCurrentRecipeList(user);
		    	tableView.getItems().clear();
		    	for(Recipe recipe : tmpList)
		    	{
		    		if (recipe.getRecipe_name().contains(searchRecipe.getText()) ) tableView.getItems().add(recipe);
		    	}
		    	//tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
		    });
		    
		    searchRecipebtn.setOnMouseEntered(new EventHandler<MouseEvent>() 
			{
				 @Override
			    public void handle(MouseEvent t) {
					 searchRecipebtn.setStyle("-fx-background-color: #06BCC1; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
			    }
			});
		    
		    searchRecipebtn.setOnMouseExited(new EventHandler<MouseEvent>() 
			{
				 @Override
			    public void handle(MouseEvent t) {
					 searchRecipebtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
			    }
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
	    
	    public void setStackPaneFromHomepage(StackPane sp, Rectangle blockStageBackground)
	    {
	    	this.blockStageBackground = blockStageBackground;
	    	this.stackPaneFromHomePage = sp;
	    }
	    
	    public Stage getAddIngredientListStage()
	    {
	    	return addIngredToRecipeStage;
	    }
}
