package application.GUI;

import application.DatabaseManager;
import application.Recipe;
import application.User;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
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

public class ExecutableAndNotExecGUI_View 
{
    DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
    private Recipe currentRecipe;
    private TableView tableView;
    private VBox main_VBox;
    private User user;
    private TitledPane bothExecutableListsTitlePane;
    
    ExecutableTableGUI executableTable;
    ExecutableTableGUI not_executableTable;
    
    public ExecutableAndNotExecGUI_View(User user)
    {
        this.user = user;
        executableTable = new ExecutableTableGUI(user, true);
        not_executableTable = new ExecutableTableGUI(user, false);
        updateTables();
        //dbm.updateExecutableRecipes(user);
        Button viewRecipebtn = new Button("View Recipe");
        viewRecipebtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
        viewRecipebtn.setCursor(Cursor.HAND);
        
        Button executeRecipeBtn = new Button("Execute Recipe");
        executeRecipeBtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
        executeRecipeBtn.setCursor(Cursor.HAND);
            
        HBox hb_1 = new HBox();
        hb_1.getChildren().addAll(executeRecipeBtn, viewRecipebtn);
        hb_1.setSpacing(5);
        hb_1.setAlignment(Pos.BASELINE_CENTER);
       
        Text errorMessage = new Text("");
        errorMessage.setFont(Font.font("Arial", FontWeight.THIN, FontPosture.ITALIC, 9));
        errorMessage.setFill(Color.RED); 
        
        TitledPane executableListTitlePane = new TitledPane("Executable Recipes", executableTable.getTableView());
        executableListTitlePane.setMinWidth(350);
        TitledPane not_executableListTitlePane = new TitledPane("Non-Executable Recipes", not_executableTable.getTableView());
        not_executableListTitlePane.setMinWidth(350);
        
        main_VBox = new VBox(hb_1,errorMessage,executableListTitlePane, not_executableListTitlePane);
        
        Rectangle background = new Rectangle(445,550);
        background.setArcHeight(40.0);
        background.setArcWidth(40.0);
        background.setFill(Color.web("#e3e3e3",1));
        
    
        StackPane stackPane = new StackPane(background,main_VBox);
        
        bothExecutableListsTitlePane = new TitledPane("Executable Recipes", stackPane);
        
        viewRecipebtn.setOnAction(e -> 
        {
            //if the input fields are all filled then that means that 
            //the user wants to add ingredients to the recipe that they are 
            //currently working on.
            
            currentRecipe = null;
            
                
            
            Recipe executableRecipe = executableTable.getSelectedRecipe();
            Recipe not_executableRecipe = not_executableTable.getSelectedRecipe();
            
            if(executableRecipe != null && not_executableRecipe != null)
            {
            	 errorMessage.setText("Please only Select One Recipe Either List.");
            }
            else if(executableRecipe == null && not_executableRecipe == null)
            {
            	errorMessage.setText("Select One Recipe from Either List");
            }
            else
            {
            	//we know one recipe was selected
            	currentRecipe = (executableRecipe == null)? not_executableRecipe : executableRecipe;
            	
                Stage showSelectedRecipe = new Stage();
                SelectedRecipeGUI_View popUpMenu = new SelectedRecipeGUI_View(user, currentRecipe, this);
                
                showSelectedRecipe.setScene(popUpMenu.getScene());
                showSelectedRecipe.setTitle("Selected Recipe");
                showSelectedRecipe.show();
            }
                
                
            });
        
        
        executeRecipeBtn.setOnAction(e -> 
        {
        	// Sergio's cooking animation
        	System.out.println("execute button");
            //Dre database call
        });
    
    
    }
    
    public void updateTables()
    {    
        dbm.updateExecutableRecipes(user);
        executableTable.updateTable(user, true);
        not_executableTable.updateTable(user, false);
    }
    
    public TitledPane getTitledPane()
    {
        return bothExecutableListsTitlePane;
    }

}