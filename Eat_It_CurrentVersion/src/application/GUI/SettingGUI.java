package application.GUI;

import application.DatabaseManager;
import application.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This is a java class for building the Settings View.
 * 
 * @author Eat_It(Summer 2021 Team)
 *
 */
public class SettingGUI 
{
	private DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
	private Scene settings_scene;
	private Button cancel_Settings_Btn;
	
	private ObservableList<User> tableData;
	private TableView tableView;
	private User user;
	private TitledPane tp1;
	private TitledPane tp2;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SettingGUI(Stage stage, LoginGUI loginGUI)
	{
		
		Image img_signUp = new Image(getClass().getResourceAsStream("/application/resources/Eat_It_Logo_300px.png"));
		ImageView imgView_signUp = new ImageView(img_signUp);
		imgView_signUp.setPreserveRatio(true);
		imgView_signUp.setFitHeight(100);
		imgView_signUp.setFitWidth(100);
		
		double mainRectWidth = 1100, mainRectHeight = 650;
		Image imgBackground_signUp = new Image(getClass().getResourceAsStream("/application/resources/login_reg_background.png"));
		ImageView background_2 = new ImageView(imgBackground_signUp);

		
		tableView = new TableView();
		tableView.setEditable(false);
		tableView.setBackground(null);
		
		TableViewSelectionModel<User> selectionModel = tableView.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Removes extra column
		
		TableColumn<User, String> column0 = new TableColumn<>("Username");
		column0.setCellValueFactory(new PropertyValueFactory<>("username"));
	    column0.setCellFactory(TextFieldTableCell.<User>forTableColumn());
	    column0.setMinWidth(245);
	    column0.setMaxWidth(250);
	
	    tableData = dbm.getCurrentCredentials();
	    
	    tableView.getItems().addAll(tableData);
	    
	    tableView.getColumns().addAll(column0);
	    
	    tableView.setMaxWidth(250);
	    tableView.setMaxHeight(200);
	    
	    cancel_Settings_Btn = new Button("Cancel");
	    cancel_Settings_Btn.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
	    cancel_Settings_Btn.setCursor(Cursor.HAND);
	    
	    HBox radioButtons = getRadioButtons();
	    
	    tp1 = new TitledPane("Delete Credential", getDeleteVBox());
	    tp1.setMaxWidth(200);
		tp1.setMaxHeight(200);
	    
	    VBox logo_table_VBox = new VBox();
		logo_table_VBox.getChildren().addAll(imgView_signUp, tableView, radioButtons, tp1, cancel_Settings_Btn);
		logo_table_VBox.setAlignment(Pos.CENTER);
		logo_table_VBox.setSpacing(10d);
		
		StackPane backgroundImagePane = new StackPane();
		backgroundImagePane.getChildren().addAll(background_2, logo_table_VBox);
		
		settings_scene = new Scene(backgroundImagePane, mainRectWidth, mainRectHeight);
		
//		URL url = this.getClass().getResource("/application/application.css");
//		System.out.println(url.toString());
//		String css = url.toExternalForm();
//		settings_scene.getStylesheets().add(css);
		
		cancel_Settings_Btn.setOnAction(e->{
			System.out.println("Cancel Button Pressed");
			stage.setScene(loginGUI.getLoginScene());
			stage.setTitle("Login Page");
			//errorMessage.setText("");
			
			
		});
		    
	}
	
	
	
	    
	public HBox getRadioButtons()
	{
		HBox hboxRes = new HBox();
	    final ToggleGroup group = new ToggleGroup();
	
	    RadioButton rb1 = new RadioButton("Delete");
		rb1.setToggleGroup(group);
		rb1.setSelected(true);
		
		RadioButton rb2 = new RadioButton("Edit");
		rb2.setToggleGroup(group);
	
		rb1.setUserData("delete");
		rb2.setUserData("edit");
		
		hboxRes.getChildren().addAll(rb1, rb2);
		hboxRes.setAlignment(Pos.TOP_CENTER);
		hboxRes.setMaxWidth(400);
		hboxRes.setSpacing(5);
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
	            
		    	if (group.getSelectedToggle() != null) {
	                //final Image image = new Image( getClass().getResourceAsStream(group.getSelectedToggle().getUserData().toString() +  ".jpg" ));
		    		
	                System.out.println("Redio Button E Listener: " + group.getSelectedToggle().getUserData().toString());
	               
	                if( group.getSelectedToggle().getUserData().equals("delete") )
		    		{
		    			tp1.setText("Delete Credential");
		    			tp1.setContent(getDeleteVBox());
		    		}
		    		else
		    		{
		    			setEditVBox();
		    		}
               } 
		    	
            }
		});
		
		return hboxRes;
		
	}
	
    /**
     * This is a method that returns a VBox containing
     * A text object, 2 password input fields. This action
     * requires the user to input their
     */
	public VBox getDeleteVBox()
	{
		VBox vboxRes = new VBox();
		

		Text instructionText = new Text("Enter the password for the selected credential.");
		Text errorMessage = new Text("");
		
		PasswordField pw1 = new PasswordField();
		pw1.setPromptText("Enter Password");
		pw1.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            
	        	if (!newValue.matches("\\d*") || !newValue.matches("\\sa-zA-Z")) {
	        		pw1.setText(newValue.replaceAll("[^\\da-zA-Z]", ""));
	            }
	        	
	            if (pw1.getText().length() > 20) {
	                String s = pw1.getText().substring(0, 20);
	                pw1.setText(s);
	            }
	        }
	    });
		
		
		PasswordField pw2 = new PasswordField();
		pw2.setPromptText("Re-Enter Password");
		pw2.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            
	        	if (!newValue.matches("\\d*") || !newValue.matches("\\sa-zA-Z")) {
	        		pw2.setText(newValue.replaceAll("[^\\da-zA-Z]", ""));
	            }
	        	
	            if (pw2.getText().length() > 20) {
	                String s = pw2.getText().substring(0, 20);
	                pw2.setText(s);
	            }
	        }
	    });
		CheckBox checkBox1 = new CheckBox("I Want To Delete Selected Credential.");
		checkBox1.wrapTextProperty();
		Button deleteBtn = new Button("Delete");
		
		vboxRes.getChildren().addAll(instructionText, errorMessage, pw1, pw2, checkBox1, deleteBtn);
		vboxRes.setAlignment(Pos.TOP_CENTER);
		vboxRes.setSpacing(5);
		
		deleteBtn.setOnAction(e->{
			errorMessage.setText("");
			System.out.println("Delete Button Pressed");
			if( !(pw1.getText().toString().equals("") && pw1.getText().toString().equals("")) )
			{
				String selectedUsername = "";
				ObservableList<User> tmpList = tableView.getSelectionModel().getSelectedItems();
				for(User userTmp : tmpList)
				{
					selectedUsername = userTmp.getUsername();
				}
				
				if( pw1.getText().toString().equals( pw2.getText().toString() ) && !(selectedUsername.equals("")) && dbm.isCredentialsValid(selectedUsername, pw1.getText().toString()) )
				{
					if(checkBox1.isSelected())
					{
						//delete the credential
						//refresh the table
						//clear the password fields
						//clear the checkbox
						User deleteUser = dbm.getUser(selectedUsername);
						dbm.deleteCredentials(deleteUser);
						pw1.clear();
						pw2.clear();
						checkBox1.setSelected(false);
						errorMessage.setText("Successfully deleted username: " + selectedUsername);
						updateTableView();
					}
					else
					{
						pw1.clear();
						pw2.clear();
						errorMessage.setText("Checkbox was not selected, no deletion occured.");
					}
				}
				else
				{
					System.out.println("Password incorrect or no Username selected.");
					errorMessage.setText("Passwords and Selected Username Do Not Match");
					pw1.clear();
					pw2.clear();
				}
			}
			
		});
		
		return vboxRes;
	}
	
	public VBox getEditPasswordBox()
	{
		VBox vboxRes = new VBox();
		

		Text instructionText = new Text("Fill in the required fields to edit username.");
		Text errorMessage = new Text();
		
		PasswordField currPw = new PasswordField();
		currPw.setPromptText("Enter Current Password.");
		currPw.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            
	        	if (!newValue.matches("\\d*") || !newValue.matches("\\sa-zA-Z")) {
	        		currPw.setText(newValue.replaceAll("[^\\da-zA-Z]", ""));
	            }
	        	
	            if (currPw.getText().length() > 20) {
	                String s = currPw.getText().substring(0, 20);
	                currPw.setText(s);
	            }
	        }
	    });
		
		PasswordField pw1 = new PasswordField();
		pw1.setPromptText("Enter New Password.");
		PasswordField pw2 = new PasswordField();
		pw2.setPromptText("Re-Enter New Password.");
		
		pw1.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            
	        	if (!newValue.matches("\\d*") || !newValue.matches("\\sa-zA-Z")) {
	        		pw1.setText(newValue.replaceAll("[^\\da-zA-Z]", ""));
	            }
	        	
	            if (pw1.getText().length() > 20) {
	                String s = pw1.getText().substring(0, 20);
	                pw1.setText(s);
	            }
	        }
	    });
		
		pw2.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            
	        	if (!newValue.matches("\\d*") || !newValue.matches("\\sa-zA-Z")) {
	        		pw2.setText(newValue.replaceAll("[^\\da-zA-Z]", ""));
	            }
	        	
	            if (pw2.getText().length() > 20) {
	                String s = pw2.getText().substring(0, 20);
	                pw2.setText(s);
	            }
	        }
	    });
		
		Button changePassword = new Button("Edit Password");
		
		vboxRes.getChildren().addAll(instructionText, errorMessage, currPw, pw1, pw2, changePassword);
		vboxRes.setAlignment(Pos.TOP_CENTER);
		vboxRes.setSpacing(5);
		
		changePassword.setOnAction(e->{
			errorMessage.setText("");
			System.out.println("Edit Password Button Pressed");
			if( !(pw1.getText().toString().equals("") && pw1.getText().toString().equals("") && currPw.getText().toString().equals("") ) )
			{
				String selectedUsername = "";
				String selectedUser_id = "";
				ObservableList<User> tmpList = tableView.getSelectionModel().getSelectedItems();
				for(User userTmp : tmpList)
				{
					selectedUser_id = userTmp.getAcc_id();
					selectedUsername = userTmp.getUsername();
				}
				
				if( pw1.getText().toString().equals( pw2.getText().toString() ) && !(selectedUsername.equals("")) && dbm.isCredentialsValid(selectedUsername, currPw.getText().toString()) )
				{
					//make sure old password matches
					//make sure new passwords are entered the same.
					//User updateUser = dbm.getUser(selectedUsername);

					dbm.updateCredentialPassword(selectedUser_id, pw1.getText().toString());
					pw1.clear();
					pw2.clear();
					currPw.clear();
					errorMessage.setText("Successfully Changed Password for Username: " + selectedUsername);
					updateTableView();

				}
				else
				{
					System.out.println("Current Password incorrect or no Username selected.");
					errorMessage.setText("Check Current Password and Make Sure New Passwords Match");
					pw1.clear();
					pw2.clear();
					currPw.clear();
					
				}
			}
			
		});
		
		return vboxRes;
	}
	
	public VBox getEditUsernameBox()
	{
		VBox vboxRes = new VBox();
		

		Text instructionText = new Text("Fill in the required fields to edit username.");
		Text errorMessage = new Text();
		
		TextField newUsernameField = new TextField(); 
		newUsernameField.setPromptText("Enter a New Username.");
		
		newUsernameField.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            
	        	if (!newValue.matches("\\d*") || !newValue.matches("\\sa-zA-Z")) {
	        		newUsernameField.setText(newValue.replaceAll("[^\\da-zA-Z]", ""));
	            }
	        	
	            if (newUsernameField.getText().length() > 20) {
	                String s = newUsernameField.getText().substring(0, 20);
	                newUsernameField.setText(s);
	            }
	        }
	    });
		
		PasswordField pw1 = new PasswordField();
		pw1.setPromptText("Enter Current Password.");
		
		pw1.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            
	        	if (!newValue.matches("\\d*") || !newValue.matches("\\sa-zA-Z")) {
	        		pw1.setText(newValue.replaceAll("[^\\da-zA-Z]", ""));
	            }
	        	
	            if (pw1.getText().length() > 20) {
	                String s = pw1.getText().substring(0, 20);
	                pw1.setText(s);
	            }
	        }
	    });
		
		PasswordField pw2 = new PasswordField();
		pw2.setPromptText("Re-Enter Current Password.");
		
		pw2.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            
	        	if (!newValue.matches("\\d*") || !newValue.matches("\\sa-zA-Z")) {
	        		pw2.setText(newValue.replaceAll("[^\\da-zA-Z]", ""));
	            }
	        	
	            if (pw2.getText().length() > 20) {
	                String s = pw2.getText().substring(0, 20);
	                pw2.setText(s);
	            }
	        }
	    });
		
		Button editUsername = new Button("Edit Username");
		
		vboxRes.getChildren().addAll(instructionText, errorMessage, newUsernameField,  pw1, pw2, editUsername);
		vboxRes.setAlignment(Pos.TOP_CENTER);
		vboxRes.setSpacing(5);
		
		editUsername.setOnAction(e->{
			errorMessage.setText("");
			System.out.println("Edit Password Button Pressed");
			if( !(pw1.getText().toString().equals("") && pw1.getText().toString().equals("") && newUsernameField.getText().toString().equals("") ) )
			{
				String selectedUsername = "";
				String selectedUser_id = "";
				ObservableList<User> tmpList = tableView.getSelectionModel().getSelectedItems();
				for(User userTmp : tmpList)
				{
					selectedUser_id = userTmp.getAcc_id();
					selectedUsername = userTmp.getUsername();
				}
				
				if( pw1.getText().toString().equals( pw2.getText().toString() ) && !( selectedUsername.equals("") ) && dbm.isCredentialsValid(selectedUsername, pw1.getText().toString()) )
				{
					//make sure old password matches
					//make sure new passwords are entered the same.
					//User updateUser = dbm.getUser(selectedUsername);

					if( dbm.updateCredentialUsername(selectedUser_id, newUsernameField.getText().toString()) )
					{
						errorMessage.setText("Successfully Changed Username from " + selectedUsername + ", to " +  newUsernameField.getText().toString() + ".");
						updateTableView();
					}
					else
					{
						errorMessage.setText("Failed Changed Username from " + selectedUsername + ", to " +  newUsernameField.getText().toString() + ".");
					}
					pw1.clear();
					pw2.clear();
					newUsernameField.clear();
					
				}
				else
				{
					System.out.println(" Check Password / if Username selected.");
					errorMessage.setText("Fail: Make Sure Passwords Match and Username is Selected");
					pw1.clear();
					pw2.clear();
					newUsernameField.clear();
				}
			}
			
		});
		
		return vboxRes;
	}
	
	public void setEditVBox()
	{
		
		HBox hboxRes = new HBox();
	    final ToggleGroup group = new ToggleGroup();
	
	    RadioButton rb1 = new RadioButton("Edit Username");
		rb1.setToggleGroup(group);
		rb1.setSelected(true);
		
		RadioButton rb2 = new RadioButton("Edit Password");
		rb2.setToggleGroup(group);
	
		rb1.setUserData("username");
		rb2.setUserData("password");
		
		hboxRes.getChildren().addAll(rb1, rb2);
		hboxRes.setAlignment(Pos.TOP_CENTER);
		hboxRes.setMaxWidth(400);
		hboxRes.setSpacing(5);
		
		VBox vbox1 = new VBox(hboxRes, getEditUsernameBox());
		tp1.setText("Edit Username");
		tp1.setContent(vbox1);
		
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
	            
		    	if (group.getSelectedToggle() != null) {
	                //final Image image = new Image( getClass().getResourceAsStream(group.getSelectedToggle().getUserData().toString() +  ".jpg" ));
		    		System.out.println("Redio Button E Listener: " + group.getSelectedToggle().getUserData().toString());
		    		if( group.getSelectedToggle().getUserData().equals("username") )
		    		{
		    			VBox vbox1 = new VBox(hboxRes, getEditUsernameBox());
		    			tp1.setText("Edit Username");
		    			tp1.setContent(vbox1);
		    			
		    		}
		    		else
		    		{
		    			VBox vbox1 = new VBox(hboxRes, getEditPasswordBox());
		    			tp1.setText("Edit Password");
		    			tp1.setContent(vbox1);
		    			
		    		}
	               
               } 
		    	
            }
		});
		//return hboxRes;
	}
	    
	public Scene getSettingScene() 
	{
		return settings_scene;
	}
	
	    
	public ObservableList<User> getTableData()
	{
		return tableData;
	}
	
	public void setTableData(ObservableList<User> updatedData)
	{
		tableData = updatedData;
	}
	
	public void updateTableView()
	{
		tableView.getItems().clear();
		
		tableData = dbm.getCurrentCredentials();
	    
	    tableView.getItems().addAll(tableData);
	}
	 
}
