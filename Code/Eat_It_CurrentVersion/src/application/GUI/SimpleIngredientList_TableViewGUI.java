package application.GUI;

import application.DatabaseManager;
import application.Item;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SimpleIngredientList_TableViewGUI 
{
	DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
	Scene scene;

    private double mainWidth = 500, mainHeight = 500;
    private VBox vbox;
    private ObservableList<Item> tableData;
    private TableView tableView;
    private User user;
    
    //Text Fields:
    private TextField addItemNum;
    private TextField addItemName;
    private TextField addExpirationDate;
    private TextField addPARAmount;
    private TextField addQuantity;
    private TextField addAmount_Type;
    
    private Text errorMessage;
    
    private Button autoGenItemNumberBtn;
    private Button addButton;
    private Button deleteButton;
    private Button updateButton;
    
    
    public SimpleIngredientList_TableViewGUI(User user) {
    	this.user = user;
    	createTable();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void createTable() {
		tableView = new TableView();
	    tableView.setEditable(false);
	    tableView.setBackground(null);
	    
	    TableViewSelectionModel<Item> selectionModel = tableView.getSelectionModel();
	    selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
	    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Removes extra column

	    // COlUMNS
	    TableColumn<Item, String> column0 = new TableColumn<>("Item\n#");
	    column0.setCellValueFactory(new PropertyValueFactory<>("item_num"));
	    column0.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
	    column0.setMinWidth(30);
	    column0.setMaxWidth(30);
	    
	    TableColumn<Item, String> column1 = new TableColumn<>("Item Name");
	    column1.setCellValueFactory(new PropertyValueFactory<>("item_name"));
	    column1.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
	    column1.setMinWidth(60);
	    column1.setMaxWidth(90);
	    
	    TableColumn<Item, String> column2 = new TableColumn<>("Item Quantity");
	    column2.setCellValueFactory(new PropertyValueFactory<>("item_Quantity"));
	    column2.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
	    column2.setMinWidth(90);
	    column2.setMaxWidth(90);
	  
	    tableData = dbm.getCurrentInventory(user);
	    
	    tableView.getItems().addAll(tableData);
	    
	    tableView.getColumns().addAll(column0, column1, column2);
	    
	    	    
	    vbox = new VBox(tableView);
	    vbox.setSpacing(5);
	    vbox.setAlignment(Pos.CENTER);
	    vbox.setBackground(null);
	    
	    scene = new Scene(vbox, mainWidth, mainHeight);
	    
	    /* **********************************
	     * Event Listeners Start
	     * ********************************** */
	    if(autoGenItemNumberBtn != null) {
	    	autoGenItemNumberBtn.setOnAction(e -> 
		    {
		    	addItemNum.setText(dbm.autogenerateItemNum(user));
		    	
		    });
	    }
	    
	    if(addButton != null)
	    {
	    	addButton.setOnAction(new EventHandler<ActionEvent>() {
	    		@Override
	    		public void handle(ActionEvent e) {
	    			
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
		    				tableView.getItems().add(tmpItem);
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
	    }
	    
	    if(deleteButton != null)
	    {
	    	deleteButton.setOnAction(e -> 
		    {
		    	ObservableList<Item> tmpList = tableView.getSelectionModel().getSelectedItems();
		    	for(Item item : tmpList)
		    	{
		    		dbm.deleteIngredient(user, item.getItem_num());
		    	}
		    	tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
		    });
	    }
	    
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
    
    public void addToTable(Item item)
    {
    	Boolean successfulInsertion = dbm.insertIngredient(user, item.getItem_num(), item.getItem_name(), item.getItem_Exp(), item.getItem_Par(), item.getItem_Quantity(), item.getItem_Quantity_Type());
		
		if(successfulInsertion)
		{
			tableView.getItems().add(item);
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
    
    public TableView getTableView()
    {
    	return tableView;
    }
    
    public ObservableList<Item> getSelectedItems()
    {
    	return tableView.getSelectionModel().getSelectedItems();
    }
}
