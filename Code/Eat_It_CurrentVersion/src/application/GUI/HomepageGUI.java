package application.GUI;

import application.User;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
		//
		
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
		
		VBox vbox = new VBox();
		vbox.getChildren().add(hbox_TopLogo_Logout);
		
		InventoryListGUI inventoryListGUI = new InventoryListGUI(user);
		
		VBox vbox_inventoryListGUI = inventoryListGUI.getVBox();
		vbox_inventoryListGUI.setPrefSize(500, 500);
		
		HBox hbox = new HBox(vbox_inventoryListGUI);
		
		vbox.getChildren().add(hbox);
		
		
		homepageScene = new Scene(vbox, mainWidth, mainHeight);
		
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
