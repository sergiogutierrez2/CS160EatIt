package application.GUI;

import java.io.File;
import java.util.Random;

import application.DatabaseManager;
import application.Recipe;
import application.User;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.util.Duration;

/**
 * TODO: Write a description of the class here.
 * 
 * @author Eat_It(Summer 2021 Team)
 */
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
    
    private TableView inventoryListTableView;
    
    @SuppressWarnings("unchecked")
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
            	currentRecipe = (executableRecipe == null)? not_executableRecipe : executableRecipe;
            	
            	System.out.println("execute button");
            	Stage popup = new Stage();
            	popup.setTitle("Making " + currentRecipe.getRecipe_name());
            	PauseTransition delay = new PauseTransition(Duration.seconds(3));
            	
            	
            	delay.setOnFinished( event ->
            			{
            				popup.close();
                    		executeRecipeBtn.setDisable(false);
            			}
            			);
            	        	
            	Random rand = new Random();
            	int randomNum = rand.nextInt((2 - 1) + 1) + 1;

//            	Image image = new Image (new File(System.getProperty("user.dir") + 
//            			File.pathSeparator + "src" + File.pathSeparator + "application" +
//            			File.pathSeparator + "resources" + File.pathSeparator + 
//            			"Cooking" + randomNum + ".gif").toURI().toString());
            	
            	Image image = new Image(getClass().getResourceAsStream("/application/resources/Cooking" + randomNum + ".gif"));
            	
            	ImageView imageview = new ImageView(image);

            	Group root = new Group(imageview);
            	
//            	Image image2 = new Image (new File("Executable.gif").toURI().toString());
            	Image image2 = new Image(getClass().getResourceAsStream("/application/resources/Executable.gif"));
            	ImageView imageview2 = new ImageView(image2);
            	Group root2 = new Group(imageview2);
            	
    		    VBox cook = new VBox();
    		    cook.getChildren().addAll(root, root2);
    		    cook.setAlignment(Pos.CENTER);
    		    cook.setSpacing(5);
    		    
            	Scene scene = new Scene(cook, 800, 800);
            	popup.setScene(scene);

            	popup.show();
           		executeRecipeBtn.setDisable(true);
            	
            	delay.play();
            	
            	dbm.execRecipe(user, currentRecipe);
            	inventoryListTableView.getItems().clear();
            	inventoryListTableView.getItems().addAll(dbm.getCurrentInventory(user));
            	
            	dbm.updateExecutableRecipes(user);
                updateTables();
            	
            }
        	// Sergio's cooking animation
        	
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
    
    @SuppressWarnings("rawtypes")
	public void setInventoryTableView(TableView tableview)
    {
    	this.inventoryListTableView = tableview;
    }

}
