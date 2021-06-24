package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomePageToolBarGUI {
	private Button logoutBtn;
	public HomePageToolBarGUI() {
	    logoutBtn = new Button("Logout & Save");

	}
	  public HBox toolBar(Stage stage, Scene scene) {
			// tool bar: message 
		    Text toolBarMessage = new Text("Welcome!");
		    toolBarMessage.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD, 30));
		    
		    // tool bar: logo image 
		    Image img = new Image(getClass().getResourceAsStream("/application/resources/logoTransparentSmall.png"));
		    ImageView imgView = new ImageView(img);
		    imgView.setFitWidth(100);
		    imgView.setFitHeight(100);
		    
		    // tool bar: logout button
		    logoutBtn.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD, 20));
		    logoutBtn.setStyle("-fx-background-color: thistle;");
		    logoutBtn.setCursor(Cursor.HAND);
		    
		    // horizontal layout for tool bar 
		    HBox toolBarContent = new HBox();
		    toolBarContent.getChildren().addAll(imgView, toolBarMessage, logoutBtn);
		    toolBarContent.setAlignment(Pos.CENTER);
		    toolBarContent.setSpacing(100);
		    toolBarContent.setStyle("-fx-background-color: lightblue;"); // change to og color !!
		    toolBarContent.setPadding(new Insets(20,20,20,20));
		    
		    return toolBarContent;
		}
	  
	  public Button getLogoutBtn() {
		  return logoutBtn;
	  }
	  
}
