package application.GUI;

import application.DatabaseManager;
import application.Item;
import application.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
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

public class InventoryListGUI {
	
		DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
		Scene scene;
	
	    private double mainWidth = 500, mainHeight = 500;
	    private VBox vbox;
	    private ObservableList<Item> tableData;
	    private TableView tableView;
	    private User user;
	    private ExecutableAndNotExecGUI_View executableAndNotExecGUI_View;
	    boolean selected;
	    
	    public InventoryListGUI(User user, ExecutableAndNotExecGUI_View executableAndNotExecGUI_View) {
	    	this.user = user;
	    	this.executableAndNotExecGUI_View = executableAndNotExecGUI_View;
	    	selected = false;
	    	createTable();
	    }
	    
	    @SuppressWarnings({ "rawtypes", "unchecked" })
		public void createTable() {
			tableView = new TableView();
		    tableView.setEditable(true);
		    tableView.setBackground(null);
		    
		    tableView.setRowFactory(new Callback<TableView<Item>, TableRow<Item>>() {  
		        @Override  
		        public TableRow<Item> call(TableView<Item> tableView2) {  
		            final TableRow<Item> row = new TableRow<>();  
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
		    
		    TableViewSelectionModel<Item> selectionModel = tableView.getSelectionModel();
		    selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
		    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Removes extra column

		    // COlUMNS
		    TableColumn<Item, String> column0 = new TableColumn<>("Item\n#");
		    column0.setCellValueFactory(new PropertyValueFactory<>("item_num"));
		    column0.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
		    column0.setMinWidth(30);
		    column0.setMaxWidth(80);
		    column0.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Item, String>>() {
	            @Override
	            public void handle(TableColumn.CellEditEvent<Item, String> t) {
	                
	            	// t.getNewValue() this is what needs to be filtered.
	            	System.out.println("New Value:" + t.getNewValue().toString());
	            	System.out.println("Old Value:" + t.getOldValue().toString());
	            	
	            	
	            	
	            	
	            	if(dbm.isInIngredientListTable(user, t.getRowValue().getItem_num()) || dbm.containsItemNum(user, t.getNewValue()) )
	            	{
	            		//dont allow change!
	            		System.out.println("Dont Change!");
	            		t.getRowValue().setItem_num(t.getOldValue());
	            		tableView.getItems().set(t.getTablePosition().getRow(), t.getRowValue()); 
	            	}
	            	else {
	            		
	            	
	            	
		                t.getRowValue().setItem_num(t.getNewValue()); //changes cell object's value to new entry
		                
		                tableView.getItems().set(t.getTablePosition().getRow(), t.getRowValue()); //changes view of table to reflect changes
		                
		                dbm.deleteIngredient( user, t.getRowValue().getItem_num() );
		                dbm.insertIngredient(user,  t.getRowValue().getItem_num(), 
		                		t.getRowValue().getItem_name(), t.getRowValue().getItem_Exp(), t.getRowValue().getItem_Par(), 
		                		t.getRowValue().getItem_Quantity(), t.getRowValue().getItem_Quantity_Type());
		                
		                dbm.getCurrentInventory(user);
		                
		                executableAndNotExecGUI_View.updateTables();
		                
	            	}
	            }
	        });
		    
		    
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
	                
	                executableAndNotExecGUI_View.updateTables();
	            }
	        });
		  
		    TableColumn<Item, String> column2 = new TableColumn<>("Expiration Date");
		    column2.setCellValueFactory(new PropertyValueFactory<>("item_Exp"));
		    column2.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
		    column2.setMinWidth(70);
		    column2.setMaxWidth(70);
		    column2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Item, String>>() {
	            @Override
	            public void handle(TableColumn.CellEditEvent<Item, String> t) {
	                
	                t.getRowValue().setItem_Exp(t.getNewValue());
	                
	                tableView.getItems().set(t.getTablePosition().getRow(), t.getRowValue());
	                
	                dbm.deleteIngredient( user, t.getRowValue().getItem_num() );
	                dbm.insertIngredient(user,  t.getRowValue().getItem_num(), 
	                		t.getRowValue().getItem_name(), t.getRowValue().getItem_Exp(), t.getRowValue().getItem_Par(), 
	                		t.getRowValue().getItem_Quantity(), t.getRowValue().getItem_Quantity_Type());
	                
	                dbm.getCurrentInventory(user);
	                
	                executableAndNotExecGUI_View.updateTables();
	            }
	        });
		    
		    TableColumn<Item, String> column3 = new TableColumn<>("Quantity");
		    column3.setCellValueFactory(new PropertyValueFactory<>("item_Quantity"));
		    column3.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
		    column3.setMinWidth(50);
		    column3.setMaxWidth(50);
		    column3.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Item, String>>() {
	            @Override
	            public void handle(TableColumn.CellEditEvent<Item, String> t) {
	                
	                t.getRowValue().setItem_Quantity(t.getNewValue());
	                
	                tableView.getItems().set(t.getTablePosition().getRow(), t.getRowValue());
	                
	                dbm.deleteIngredient( user, t.getRowValue().getItem_num() );
	                dbm.insertIngredient(user,  t.getRowValue().getItem_num(), 
	                		t.getRowValue().getItem_name(), t.getRowValue().getItem_Exp(), t.getRowValue().getItem_Par(), 
	                		t.getRowValue().getItem_Quantity(), t.getRowValue().getItem_Quantity_Type());
	                
	                dbm.getCurrentInventory(user);
	                
	                executableAndNotExecGUI_View.updateTables();
	            }
	        });
		    
		    TableColumn<Item, String> column4 = new TableColumn<>("PAR");
		    column4.setCellValueFactory(new PropertyValueFactory("item_Par"));
		    column4.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
		    column4.setMinWidth(30);
		    column4.setMaxWidth(40);
		    column4.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Item, String>>() {
	            @Override
	            public void handle(TableColumn.CellEditEvent<Item, String> t) {
	                
	                t.getRowValue().setItem_Par(t.getNewValue());
	                
	                tableView.getItems().set(t.getTablePosition().getRow(), t.getRowValue());
	                
	                dbm.deleteIngredient( user, t.getRowValue().getItem_num() );
	                dbm.insertIngredient(user,  t.getRowValue().getItem_num(), 
	                		t.getRowValue().getItem_name(), t.getRowValue().getItem_Exp(), t.getRowValue().getItem_Par(), 
	                		t.getRowValue().getItem_Quantity(), t.getRowValue().getItem_Quantity_Type());
	                
	                dbm.getCurrentInventory(user);
	                
	                executableAndNotExecGUI_View.updateTables();
	            }
	        });
		    
		    TableColumn<Item, String> column5 = new TableColumn<>("Amount\nType");
		    column5.setCellValueFactory(new PropertyValueFactory<>("item_Quantity_Type"));
		    column5.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
		    column5.setMinWidth(60);
		    column5.setMaxWidth(60);
		    column5.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Item, String>>() {
	            @Override
	            public void handle(TableColumn.CellEditEvent<Item, String> t) {
	                
	                t.getRowValue().setItem_Quantity_Type(t.getNewValue());
	                
	                tableView.getItems().set(t.getTablePosition().getRow(), t.getRowValue());
	                
	                dbm.deleteIngredient( user, t.getRowValue().getItem_num() );
	                dbm.insertIngredient(user,  t.getRowValue().getItem_num(), 
	                		t.getRowValue().getItem_name(), t.getRowValue().getItem_Exp(), t.getRowValue().getItem_Par(), 
	                		t.getRowValue().getItem_Quantity(), t.getRowValue().getItem_Quantity_Type());
	                
	                dbm.getCurrentInventory(user);
	                
	                executableAndNotExecGUI_View.updateTables();
	            }
	        });
		  
		    tableData = dbm.getCurrentInventory(user);
		    
		    tableView.getItems().addAll(tableData);
		    
		    tableView.getColumns().addAll(column0, column1, column2, column3, column4, column5);
		    
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
		    
//		    TextField addExpirationDate = new TextField();
//		    addExpirationDate.setPromptText("Expiration Date");
//		    addExpirationDate.setFont(Font.font("Arial", FontWeight.BOLD, 10));
//		    addExpirationDate.textProperty().addListener(new ChangeListener<String>() {
//		        @Override
//		        public void changed(ObservableValue<? extends String> observable, String oldValue, 
//		            String newValue) {
//		             
//		            if (!newValue.matches("\\d") && !newValue.matches("/")) {
//		            	addExpirationDate.setText(newValue.replaceAll("[^\\d/]", ""));
//		            }
//		        	
//		            if (addExpirationDate.getText().length() > 8) {
//		                String s = addExpirationDate.getText().substring(0, 8);
//		                addExpirationDate.setText(s);
//		            } 		            
//		        }
//		    });
		    
		    DatePicker addExpirationDate;
		    addExpirationDate = new DatePicker();
		    addExpirationDate.setPromptText("Expiration Date");
		    addExpirationDate.setMaxWidth(400);
		    addExpirationDate.setMaxHeight(400);
		    addExpirationDate.setMinHeight(05.);
		    addExpirationDate.setMinWidth(05.);
		    addExpirationDate.setPrefHeight(16.);
		    addExpirationDate.setPrefWidth(120.);
		    addExpirationDate.setStyle("-fx-font-size: 10 Arial");
		    
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
			
			Button autoGenItemNumberBtn = new Button("Generate Item #");
			autoGenItemNumberBtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 10px; -fx-font-size: 9px; -fx-text-fill: #ffffff");
			autoGenItemNumberBtn.setCursor(Cursor.HAND);
		    
		    Button addButton = new Button("Add");
		    addButton.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		    addButton.setCursor(Cursor.HAND);
		    
		    Button deleteButton = new Button("Delete");
		    deleteButton.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		    deleteButton.setCursor(Cursor.HAND);
		    
//		    Button updateButton = new Button("Update");
//		    updateButton.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
//		    updateButton.setCursor(Cursor.HAND);
		    
		    TextField searchItem = new TextField();
		    searchItem.setPromptText("Search Inventory");
		    searchItem.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		    
		    Button searchItembtn = new Button("Search");
		    searchItembtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		    searchItembtn.setCursor(Cursor.HAND);
		    	    
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
		    
		    HBox hb_3 = new HBox();
		    hb_3.getChildren().addAll(searchItem, searchItembtn);
		    hb_3.setAlignment(Pos.CENTER);
		    hb_3.setSpacing(5);
		    
		    hb_2.setSpacing(5);
		    hb.setSpacing(5);
		    
		    HBox hb_1 = new HBox(autoGenItemNumberBtn, addButton, deleteButton);
		    hb_1.setAlignment(Pos.CENTER);
		    hb_1.setSpacing(5);
		    	    
		    vbox = new VBox(hb, hb_2, errorMessage, hb_1, hb_3,  tableView);
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
		    				tableView.getItems().add(tmpItem);
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
	    			executableAndNotExecGUI_View.updateTables();
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
		    	
		    	executableAndNotExecGUI_View.updateTables();
		    });
		    
		    searchItembtn.setOnAction(e -> 
		    {
		    	ObservableList<Item> tmpList = dbm.getCurrentInventory(user);
		    	tableView.getItems().clear();
		    	for(Item item : tmpList)
		    	{
		    		if (item.getItem_name().contains(searchItem.getText()) ) tableView.getItems().add(item);
		    	}
		    	//tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
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
	    
}
