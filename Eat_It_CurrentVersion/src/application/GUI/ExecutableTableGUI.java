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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * This class created a tablebView that can be used as executable or not.
 * 
 * @author Eat_It(Summer 2021 Team)
 */
public class ExecutableTableGUI {
	
	DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
	Scene scene;

    private double mainWidth = 500, mainHeight = 500;
    private VBox vbox;
    private ObservableList<Recipe> tableData;
    private TableView tableView;
    private User user;
    private boolean execOrNot;
    TableViewSelectionModel<Recipe> selectionModel;
    
    public ExecutableTableGUI(User user, boolean execOrNot) {
    	this.user = user;
    	this.execOrNot = execOrNot;
    	createTable();
    }
    
    /**
     * This method creates the generic tables that are used by the GUI to display the recipes.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void createTable() {
		tableView = new TableView();
	    tableView.setEditable(false);
	    tableView.setBackground(null);
	    
	    selectionModel = tableView.getSelectionModel();
	    selectionModel.setSelectionMode(SelectionMode.SINGLE);
	    
	    
	    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Removes extra column

	    tableView.setRowFactory(new Callback<TableView<Recipe>, TableRow<Recipe>>() {  
	        @Override  
	        public TableRow<Recipe> call(TableView<Recipe> tableView) {  
	            final TableRow<Recipe> row = new TableRow<>();  
	            row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {  
	                @Override  
	                public void handle(MouseEvent event) {  
	                    final int index = row.getIndex();  
	                    if (index >= 0 && index < tableView.getItems().size() && tableView.getSelectionModel().isSelected(index)  ) {
	                        tableView.getSelectionModel().clearSelection();
	                        event.consume();  
	                    }  
	                }  
	            });  
	            return row;  
	        }  
	    });  
	    // COlUMNS
	    TableColumn<Recipe, String> column0 = new TableColumn<>("Recipe #");
	    column0.setCellValueFactory(new PropertyValueFactory<>("recipe_num"));
	    column0.setCellFactory(TextFieldTableCell.<Recipe>forTableColumn());
	    column0.setMinWidth(50);
	    column0.setMaxWidth(50);
	    
	    TableColumn<Recipe, String> column1 = new TableColumn<>("Recipe Name");
	    column1.setCellValueFactory(new PropertyValueFactory<>("recipe_name"));
	    column1.setCellFactory(TextFieldTableCell.<Recipe>forTableColumn());
	    column1.setMinWidth(150);
	    column1.setMaxWidth(150);
	  
	    tableData = dbm.getExecutableRecipes(user, execOrNot);
	    System.out.println( tableData.toString() );
	    
	    tableView.getItems().addAll(tableData);
	    
	    tableView.getColumns().addAll(column0, column1);
	    
	    tableView.setMaxHeight(200);
	    
	    vbox = new VBox(tableView);
	    vbox.setSpacing(5);
	    vbox.setAlignment(Pos.CENTER);
	    vbox.setBackground(null);
	    vbox.setMaxHeight(210);
	    
	    scene = new Scene(vbox, mainWidth, mainHeight);
	    
	    
	    
	    /* **********************************
	     * Event Listeners Start
	     * ********************************** */
	  
    	
	    
	    /* **********************************
	     * Event Listeners End
	     * ********************************** */
	}
    
    /**
     * This method updates the tables by clearing all data, making a call to dbm to get list of executables.
     * This methods adds the new data to the table to display the most current.
     * @param user This is User type that we build the table from.
     * @param execOrNot This is a boolean that decides which table to build.
     */
    public void updateTable(User user, boolean execOrNot)
    {
    	tableView.getItems().clear();
    	tableData = dbm.getExecutableRecipes(user, execOrNot);
    	tableView.getItems().addAll(tableData);
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
    /**
     * This method returns the selected recipe in the table.
     * @return Returns null if no recipe is selected, otherwise return the selected recipe.
     */
    public Recipe getSelectedRecipe()
    {
    	if(selectionModel.isEmpty())
    	{
    		return null;
    	}
    	else
    	{
    		return selectionModel.getSelectedItem();
    	}
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
