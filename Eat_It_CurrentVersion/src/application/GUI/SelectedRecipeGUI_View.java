package application.GUI;

import java.net.URL;
import java.util.ArrayList;

import application.DatabaseManager;
import application.Recipe;
import application.RecipeItem;
import application.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class is used to build the view of selected recipes.
 * @author Eat_It(Summer 2021 Team)
 */
public class SelectedRecipeGUI_View {

    DatabaseManager dbm = DatabaseManager.getSingleDatabaseManagerInstance();
    private Scene homepage_scene;
    private Stage SelectedRecipeGUI_Stage;
    private Scene SelectedRecipeView_scene;
    private ExecutableAndNotExecGUI_View executableAndNotExecGUI_View;
    
    /**
     * This creates the GUI for the selected Recipe.
     * @param user Of the user. 
     * @param recipe Of the recipe.
     * @param executableAndNotExecGUI_View Of the executable GUI.
     */
    public SelectedRecipeGUI_View(User user, Recipe recipe, ExecutableAndNotExecGUI_View executableAndNotExecGUI_View) {
        Text recipeNameTitle = new Text(recipe.getRecipe_name());
        this.executableAndNotExecGUI_View = executableAndNotExecGUI_View;
        recipeNameTitle.setFont(Font.font("Arial", FontWeight.THIN, FontPosture.ITALIC, 30));
        RecipeIngredientList_TableViewGUI recipeIngredListGUI = new RecipeIngredientList_TableViewGUI(user, recipe, executableAndNotExecGUI_View);
        RecipeSteps_TableViewGUI recipeSteps_TableViewGUI = new RecipeSteps_TableViewGUI(user, recipe);
        
        LinearGradient linearGrad = new LinearGradient(
                0,   // start X 
                0,   // start Y
                0,   // end X
                1, // end Y
                true, // proportional
                CycleMethod.NO_CYCLE, // cycle colors
                // stops
                new Stop(0.1f, Color.WHITE),
                new Stop(1.0f, Color.CADETBLUE));
        
        Rectangle selectedRecipe_background = new Rectangle(700, 700);
        selectedRecipe_background.setFill(linearGrad);
        
        HBox mainHBox = new HBox( recipeIngredListGUI.getTableView(), recipeSteps_TableViewGUI.getTableView() );
        mainHBox.setAlignment(Pos.CENTER);
        mainHBox.setSpacing(50);
        
        Text missingIngTitle = new Text("Missing Ingredients:");
        
       
        
        ArrayList<RecipeItem> missingList = dbm.getMissingIngredientListForRecipe(user, recipe);
       
        String missingItemListStr = "";
        for(RecipeItem missingItem : missingList)
        {
            missingItemListStr += missingItem.getItem_name() + "(missing: " + missingItem.getItem_quantity() + ")\n";
        }
        TextArea ta = new TextArea();  
        ta.setEditable(false);
        ta.setText(missingItemListStr);
        ta.setWrapText(true);
        ta.setMaxWidth(300);
        ta.setMaxHeight(150);
        
        recipeNameTitle.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.ITALIC, 20));
        
        VBox mainVBox = new VBox(recipeNameTitle,mainHBox,missingIngTitle, ta);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setSpacing(20);
        mainHBox.setSpacing(50);
        
        StackPane stackpane = new StackPane(selectedRecipe_background, mainVBox);

        SelectedRecipeView_scene = new Scene(stackpane);
        
        
        URL url = this.getClass().getResource("/application/application.css");
        System.out.println(url.toString());
        String css = url.toExternalForm();
        SelectedRecipeView_scene.getStylesheets().add(css);
        
    }
    
    public Scene getScene()
    {
        return SelectedRecipeView_scene;
    }
}