package application.GUI;

import application.DatabaseManager;
import application.Item;
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

public class InventoryListGUI {
	
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		Scene scene;
	
	    private double mainWidth = 500, mainHeight = 500;
	    private VBox vbox;
	    private ObservableList<Item> tableData;
	    private TableView tableView;
	    private User user;
	    
	    public InventoryListGUI(User user) {
	    	this.user = user;
	    	createTable();
	    }
	    
	    @SuppressWarnings({ "rawtypes", "unchecked" })
		public void createTable() {
			tableView = new TableView();
		    tableView.setEditable(true);
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
		    column1.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Item, String>>() {
	            @Override
	            public void handle(TableColumn.CellEditEvent<Item, String> t) {
	                
	                t.getRowValue().setItem_name(t.getNewValue());
	                
	                tableView.getItems().set(t.getTablePosition().getRow(), t.getRowValue());
	                
	                dbm.deleteIngredient( user, t.getRowValue().getItem_num() );
	                dbm.insertIngredient(user,  t.getRowValue().getItem_num(), 
	                		t.getRowValue().getItem_name(), t.getRowValue().getItem_Exp(), t.getRowValue().getItem_Par(), 
	                		t.getRowValue().getItem_Quantity(), t.getRowValue().getItem_Quantity_Type());
	                
	                dbm.getCurrentInventory(user);
	                
	                
	            }
	        });
		  
		    //
		    TableColumn<Item, String> column2 = new TableColumn<>("Expiration Date");
		    column2.setCellValueFactory(new PropertyValueFactory<>("item_Exp"));
		    column2.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
		    column2.setMinWidth(70);
		    column2.setMaxWidth(70);
		   
		    
		    TableColumn<Item, String> column3 = new TableColumn<>("Quantity");
		    column3.setCellValueFactory(new PropertyValueFactory<>("item_Quantity"));
		    column3.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
		    column3.setMinWidth(50);
		    column3.setMaxWidth(50);
		    
		    TableColumn<Item, String> column4 = new TableColumn<>("PAR");
		    column4.setCellValueFactory(new PropertyValueFactory("item_Par"));
		    column4.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
		    column4.setMinWidth(30);
		    column4.setMaxWidth(30);
		    
		    TableColumn<Item, String> column5 = new TableColumn<>("Amount\nType");
		    column5.setCellValueFactory(new PropertyValueFactory<>("item_Quantity_Type"));
		    column5.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
		    column5.setMinWidth(60);
		    column5.setMaxWidth(60);
		    
		    
		    
		  
		    tableData = dbm.getCurrentInventory(user);
		    
		    tableView.getItems().addAll(tableData);
		    
		    tableView.getColumns().addAll(column0, column1, column2, column3, column4, column5);
		    
		    
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
			
			Button autoGenItemNumberBtn = new Button("Generate Item #");
			autoGenItemNumberBtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 10px; -fx-font-size: 9px; -fx-text-fill: #ffffff");
			autoGenItemNumberBtn.setCursor(Cursor.HAND);
			    
		    
		    Button addButton = new Button("Add");
		    addButton.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		    addButton.setCursor(Cursor.HAND);
		    
		    
		    Button deleteButton = new Button("Delete");
		    deleteButton.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		    deleteButton.setCursor(Cursor.HAND);
		    
		    Button updateButton = new Button("Update");
		    updateButton.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		    updateButton.setCursor(Cursor.HAND);
		    	    
		    HBox hb = new HBox();
		    hb.getChildren().addAll(addItemNum, 
		    						addItemName, 
		    						addExpirationDate);
		    hb.setAlignment(Pos.CENTER);
		    HBox hb_2 = new HBox();
		    hb_2.getChildren().addAll(addPARAmount, 
	    							addQuantity, 
	    							addAmount_Type);
		    hb_2.setAlignment(Pos.CENTER);
		    
		    hb_2.setSpacing(5);
		    hb.setSpacing(5);
		    
		    HBox hb_1 = new HBox(autoGenItemNumberBtn, addButton, deleteButton, updateButton);
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
		    
		    autoGenItemNumberBtn.setOnAction(e -> 
		    {
		    	addItemNum.setText(dbm.autogenerateItemNum(user));
		    	
		    });
		    
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
		    
		    deleteButton.setOnAction(e -> 
		    {
		    	ObservableList<Item> tmpList = tableView.getSelectionModel().getSelectedItems();
		    	for(Item item : tmpList)
		    	{
		    		dbm.deleteIngredient(user, item.getItem_num());
		    	}
		    	tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
		    });
		    
		    deleteButton.setOnAction(e -> 
		    {
		    	ObservableList<Item> tmpList = tableView.getSelectionModel().getSelectedItems();
		    	for(Item item : tmpList)
		    	{
		    		dbm.deleteIngredient(user, item.getItem_num());
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
	    

}
