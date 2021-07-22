package application.GUI;

import java.net.URL;

import application.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class HomepageGUI {
	
	private double mainWidth = 1280, mainHeight = 720;
	private Scene homepageScene;
	Button logoutBtn;
	private User user;
	
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
		RecipeListGUI recipeListGUI = new RecipeListGUI(user, inventoryListGUI.getTableView());
		
		
		VBox vbox_recipeListGUI = recipeListGUI.getVBox();
		VBox vbox_inventoryListGUI = inventoryListGUI.getVBox();
		//vbox_inventoryListGUI.setPrefSize(300, 500);
		
		vbox_recipeListGUI.setMaxWidth(375);
		vbox_recipeListGUI.setMaxHeight(600);
		
		vbox_inventoryListGUI.setMaxWidth(375);
		vbox_inventoryListGUI.setMaxHeight(600);
		
		Rectangle mainBackground = new Rectangle(1320,770);
		mainBackground.setArcHeight(40.0);
		mainBackground.setArcWidth(40.0);
		mainBackground.setFill(Color.web("#CDD3CE",1));
		
		Rectangle inventoryList_background = new Rectangle(400,550);
		inventoryList_background.setArcHeight(40.0);
		inventoryList_background.setArcWidth(40.0);
		inventoryList_background.setFill(Color.web("#e3e3e3",1));
		
		Rectangle recipeList_background = new Rectangle(400,550);
		recipeList_background.setArcHeight(40.0);
		recipeList_background.setArcWidth(40.0);
		recipeList_background.setFill(Color.web("#e3e3e3",1));
		
		StackPane inventoryListStackPane = new StackPane();
		inventoryListStackPane.getChildren().addAll(inventoryList_background, vbox_inventoryListGUI);
		inventoryListStackPane.setAlignment(Pos.CENTER);
		
		StackPane recipeListStackPane = new StackPane();
		recipeListStackPane.getChildren().addAll(recipeList_background, vbox_recipeListGUI);
		recipeListStackPane.setAlignment(Pos.CENTER);
		
		TitledPane ingredientsPane = new TitledPane("Ingredients", inventoryListStackPane);
		ingredientsPane.setMinWidth(350);
		TitledPane recipesListPane = new TitledPane("Recipe List", recipeListStackPane);
		recipesListPane.setMinWidth(400);
		
		TitledPane execView = executableAndNotExecGUI_View.getTitledPane();
		
		HBox hbox = new HBox(ingredientsPane, recipesListPane, execView);
		hbox.setPadding(new Insets(10, 0, 0, 10));
		hbox.setSpacing(5);
		entireHomePage_VBox.getChildren().add(hbox);
		
		StackPane mainPane = new StackPane(mainBackground, entireHomePage_VBox);
		
		homepageScene = new Scene(mainPane, mainWidth, mainHeight);
	
		
		URL url = this.getClass().getResource("/application/application.css");
		System.out.println(url.toString());
		String css = url.toExternalForm();
		homepageScene.getStylesheets().add(css);
		
		logoutBtn.setOnAction(e-> {
			this.user = null;
			stage.setScene(loginScene);
			stage.setTitle("Login Page");
		});
	}
	
	public Scene getHomepageGUIscene()
	{
		return homepageScene;
	}
}
