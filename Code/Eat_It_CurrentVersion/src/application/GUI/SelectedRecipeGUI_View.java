package application.GUI;

import application.DatabaseManager;
import application.Recipe;
import application.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
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

public class SelectedRecipeGUI_View {

    DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
    private Scene homepage_scene;
    private Stage SelectedRecipeGUI_Stage;
    private Scene SelectedRecipeView_scene;
    
    public SelectedRecipeGUI_View(User user, Recipe recipe ) {
        Text recipeNameTitle = new Text(recipe.getRecipe_name());
        
        recipeNameTitle.setFont(Font.font("Arial", FontWeight.THIN, FontPosture.ITALIC, 30));
        RecipeIngredientList_TableViewGUI recipeIngredListGUI = new RecipeIngredientList_TableViewGUI(user, recipe);
        RecipeSteps_TableViewGUI recipeSteps_TableViewGUI = new RecipeSteps_TableViewGUI(user, recipe);
        
        
        Rectangle selectedRecipe_background = new Rectangle(800, 500);
        selectedRecipe_background.setArcHeight(40.0);
        selectedRecipe_background.setArcWidth(40.0);
        selectedRecipe_background.setFill(Color.web("#e3e3e3",1));
        
        HBox mainHBox = new HBox( recipeIngredListGUI.getTableView(), recipeSteps_TableViewGUI.getTableView() );
        mainHBox.setAlignment(Pos.CENTER);
        mainHBox.setSpacing(50);
        
        Text missingIngTitle = new Text("Missing Ingredients:");
        
        //Recipe.missingItemList
        // loop the itemlist and add the items to a String
        // 		S += item.name + ", ";
        
        // 		Item1(Needs quantity), Item2, Item3, Item4
        
//        for(String missingItem : recipe.missingList.get)
//        {
//        	
//        }
//        TextArea ta = new TextArea();
        
        recipeNameTitle.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.ITALIC, 20));
        
        VBox mainVBox = new VBox(recipeNameTitle,mainHBox,missingIngTitle);
        mainVBox.setAlignment(Pos.CENTER);
        mainHBox.setSpacing(50);
        
        StackPane stackpane = new StackPane(selectedRecipe_background, mainVBox);

        SelectedRecipeView_scene = new Scene(stackpane);
    }
    
    
    public Scene getScene()
    {
        return SelectedRecipeView_scene;
    }
    
}