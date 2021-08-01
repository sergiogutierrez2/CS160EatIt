package application.GUI;

import java.net.URL;

import application.User;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Java Class that manages the homepage GUI of the user's ingredients and recipes.
 * 
 * @author Eat_It(Summer 2021 Team)
 */
public class HomepageGUI {
	
	private double mainWidth = 1380, mainHeight = 780;
	private Scene homepageScene;
	private Button logoutBtn;
	private User user;
	private StackPane mainPane;
	private Stage stageFromAddIngredientListView;
	private Stage stageFromViewingRecipe;
	
	/**
	 * This is the constructor for the HomepageGUI class that accepts
	 * a stage, user and login scene.
	 * @param stage The stage of the homepage.
	 * @param user The user that signed in.
	 * @param loginScene The login scene.
	 */
	public HomepageGUI(Stage stage, User user, Scene loginScene)
	{
		// we need to pass in a user somehow, but for now im just making one
		this.user = user;
		
		Image img = new Image(getClass().getResourceAsStream("/application/resources/Eat_It_Logo_300px.png"));
		ImageView imgView = new ImageView(img);
		imgView.setFitWidth(100);
		imgView.setFitHeight(100);
		
		logoutBtn = new Button("Logout");
		logoutBtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		logoutBtn.setCursor(Cursor.HAND);
		
		
		HBox hbox_TopLogo_Logout = new HBox();
		hbox_TopLogo_Logout.getChildren().addAll(imgView, logoutBtn);
		hbox_TopLogo_Logout.setSpacing(1100);
		hbox_TopLogo_Logout.setAlignment(Pos.CENTER);
		
		VBox entireHomePage_VBox = new VBox();
		entireHomePage_VBox.getChildren().add(hbox_TopLogo_Logout);
		
		ExecutableAndNotExecGUI_View executableAndNotExecGUI_View = new ExecutableAndNotExecGUI_View(user);
		InventoryListGUI inventoryListGUI = new InventoryListGUI(user, executableAndNotExecGUI_View);
		RecipeListGUI recipeListGUI = new RecipeListGUI(user, inventoryListGUI.getTableView(), executableAndNotExecGUI_View);
		
		executableAndNotExecGUI_View.setInventoryTableView(inventoryListGUI.getTableView());
		
		VBox vbox_recipeListGUI = recipeListGUI.getVBox();
		VBox vbox_inventoryListGUI = inventoryListGUI.getVBox();
		//vbox_inventoryListGUI.setPrefSize(300, 500);
		
		vbox_recipeListGUI.setMaxWidth(420);
		vbox_recipeListGUI.setMaxHeight(600);
		
		vbox_inventoryListGUI.setMaxWidth(420);
		vbox_inventoryListGUI.setMaxHeight(600);
		
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
		
		Rectangle mainBackground = new Rectangle(mainWidth, mainHeight);
		mainBackground.setFill(linearGrad);
		
		Rectangle blockStageBackground = new Rectangle(mainWidth, mainHeight);
		blockStageBackground.setStyle("-fx-background-color: grey; -fx-opacity: 0.75;");
		
		StackPane inventoryListStackPane = new StackPane();
		inventoryListStackPane.getChildren().addAll(vbox_inventoryListGUI);
		inventoryListStackPane.setAlignment(Pos.CENTER);
		
		StackPane recipeListStackPane = new StackPane();
		recipeListStackPane.getChildren().addAll(vbox_recipeListGUI);
		recipeListStackPane.setAlignment(Pos.CENTER);
		
		TitledPane ingredientsPane = new TitledPane("Ingredients", inventoryListStackPane);
		ingredientsPane.setMinWidth(400);
		ingredientsPane.setMaxHeight(600);
		ingredientsPane.setMinHeight(600);
		
		
		TitledPane recipesListPane = new TitledPane("Recipe List", recipeListStackPane);
		recipesListPane.setMinWidth(400);
		recipesListPane.setMaxHeight(600);
		recipesListPane.setMinHeight(600);
		
		TitledPane execView = executableAndNotExecGUI_View.getTitledPane();
		execView.setMaxHeight(600);
		execView.setMinHeight(600);
		
		HBox hbox = new HBox(ingredientsPane, recipesListPane, execView);
		hbox.setPadding(new Insets(10, 0, 0, 10));
		hbox.setSpacing(5);
		entireHomePage_VBox.getChildren().add(hbox);
		
		mainPane = new StackPane(mainBackground, entireHomePage_VBox);
		
		homepageScene = new Scene(mainPane, mainWidth, mainHeight);
	
		recipeListGUI.setStackPaneFromHomepage(mainPane, blockStageBackground);
		stageFromAddIngredientListView = recipeListGUI.getAddIngredientListStage();
		
		executableAndNotExecGUI_View.setStackPaneFromHomepage(mainPane, blockStageBackground);
		stageFromViewingRecipe = executableAndNotExecGUI_View.getViewRecipeStage();
		
		URL url = this.getClass().getResource("/application/application.css");
		System.out.println(url.toString());
		String css = url.toExternalForm();
		homepageScene.getStylesheets().add(css);
		ingredientsPane.getStylesheets().add(css);
		
		logoutBtn.setOnAction(e-> {
			this.user = null;
			stage.setScene(loginScene);
			stage.setTitle("Login Page");
		});
		
		logoutBtn.setOnMouseEntered(new EventHandler<MouseEvent>() 
		{
			 @Override
		    public void handle(MouseEvent t) {
				 logoutBtn.setStyle("-fx-background-color: #C792DF; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		    }
		});
		
		logoutBtn.setOnMouseExited(new EventHandler<MouseEvent>() 
		{
			 @Override
		    public void handle(MouseEvent t) {
				 logoutBtn.setStyle("-fx-background-color: #000000; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		    }
		});
		
		blockStageBackground.setOnMouseClicked(new EventHandler<MouseEvent>() 
		{
			 @Override
		    public void handle(MouseEvent t) {
				 
				 if(stageFromAddIngredientListView.isShowing())
				 {
					 mainPane.getChildren().remove(blockStageBackground);
					 stageFromAddIngredientListView.close();
				 }
				 if(stageFromViewingRecipe.isShowing())
				 {
					 mainPane.getChildren().remove(blockStageBackground);
					 stageFromViewingRecipe.close();
				 }
				 
				 
				 
		    }
		});
	}
	
	/**
	 * This method returns the scene of the homepage.
	 * @return The homepage scene.
	 */
	public Scene getHomepageGUIscene()
	{
		return homepageScene;
	}
}
