package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LoginGUI {
	private double mainRectWidth = 1100, mainRectHeight = 650;
	private Rectangle rect1;
	private TextField userNameField = null;
	private TextField passwordField = null;
	private TextField passwordField2 = null;
	private Button cancel = null;
	private Button register = null;
	private Button loginBtn = null;
	private Button signUpBtn = null;
	private StackPane root;
	private StackPane registerBoxPane2;
	private VBox loginVBox;
	private StackPane registerBoxPane;
	private HBox hbox;
	private VBox vbox1;

	public LoginGUI() {
		build();
	}

	public void build() {
		/* Making the BlueBackGroundRectangle */
		rect1 = new Rectangle(mainRectWidth, mainRectHeight);
		rect1.setFill(Color.web("#DEE7EC", 1));
		rect1.setArcHeight(40.0);
		rect1.setArcWidth(40.0);

		/* Welcome Label */
		Text welcomeText = new Text("Welcome");
		welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 60));

		StackPane loginInputBoxPane = new StackPane();

		/* Making the OffWhite Rectangle */
		Rectangle rect2 = new Rectangle(350, 350);
		rect2.setArcHeight(40.0);
		rect2.setArcWidth(40.0);
		rect2.setFill(Color.web("#EFEAE4", 1));

		/* Sign up Text */
		Text loginText = new Text("Login");
		loginText.setFont(Font.font("Arial", FontWeight.BOLD, 13));

		// loginSetUp();
		userNameField = new TextField("Enter User Name");
		userNameField.setPrefWidth(200);
		userNameField.setMaxWidth(200);

		passwordField = new TextField("Enter Password");
		passwordField.setPrefWidth(200);
		passwordField.setMaxWidth(200);

		/* create the buttons for sing up page */
		signUpBtn = new Button("Sign Up");
		loginBtn = new Button("Login  ");

		signUpBtn.setStyle("-fx-background-color: #AB81CD; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		signUpBtn.setCursor(Cursor.HAND);

		loginBtn.setStyle("-fx-background-color: #AB81CD; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		loginBtn.setCursor(Cursor.HAND);

		cancel = new Button("Cancel");
		register = new Button("Register");

		/* Stack the OffWhite background with the labels and text field */

		registerBoxPane = new StackPane();
		registerBoxPane.getChildren().addAll(loginBtn);

		registerBoxPane2 = new StackPane();
		registerBoxPane2.getChildren().addAll(signUpBtn);

		/*
		 * set register and cancel button next to each other and placed at bottom of
		 * gray square
		 */
		HBox signupBox = new HBox();
		signupBox.getChildren().addAll(registerBoxPane2, registerBoxPane);
		HBox.setMargin(signupBox, new Insets(30, 20, 0, 0));
		signupBox.setSpacing(10d);

		HBox Move = new HBox(signupBox);
		Move.setAlignment(Pos.CENTER);

		/* add all other members vertically */
		loginVBox = new VBox();
		loginVBox.getChildren().addAll(loginText, userNameField, passwordField);
		loginVBox.setAlignment(Pos.CENTER);
		loginVBox.setSpacing(10d);

		// Creating a Grid Pane
		GridPane gridPane = new GridPane();

		// Setting the Grid alignment
		gridPane.setAlignment(Pos.CENTER);

		// Arranging all the nodes in the grid
		gridPane.add(loginVBox, 0, 0);
		gridPane.add(Move, 0, 1);

		/* put everything together */
		loginInputBoxPane.getChildren().addAll(rect2, gridPane);

		/* Making the Logo */
		Image img = new Image(getClass().getResourceAsStream("/application/resources/logoTransparentSmall.png"));
		ImageView imgView = new ImageView(img);

		/* Make logo and Off white box horizontal to each other */
		hbox = new HBox();
		hbox.getChildren().addAll(imgView, loginInputBoxPane);
		hbox.setAlignment(Pos.CENTER);

		/* Make Welcome vertical to the horizontal logo and OffWhite box */
		vbox1 = new VBox();

		vbox1.getChildren().addAll(welcomeText, hbox);
		vbox1.setPadding(new Insets(90, 0, 0, 0));
		vbox1.setSpacing(100);
		vbox1.setAlignment(Pos.TOP_CENTER);

		/* Put Vertical Box in the Stack Pane */
		root = new StackPane();
		root.getChildren().addAll(rect1, vbox1, hbox);
	}

	public StackPane signupSetUp() {
		/* set up the new text */
		Text loginText2 = new Text("Sign Up");
		loginText2.setFont(Font.font("Arial", FontWeight.BOLD, 13));

		/* Sign up Text for password */
		passwordField2 = new TextField("Re-Enter Password");
		passwordField2.setPrefWidth(200);
		passwordField2.setMaxWidth(200);

		/* create the buttons for sing up page */
		cancel.setStyle("-fx-background-color: #AB81CD; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		cancel.setCursor(Cursor.HAND);

		register.setStyle("-fx-background-color: #AB81CD; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
		register.setCursor(Cursor.HAND);

		/* add buttons to page */
		registerBoxPane.getChildren().addAll(register);
		registerBoxPane2.getChildren().addAll(cancel);

		/* add all other members vertically */
		loginVBox.getChildren().clear();
		loginVBox.getChildren().addAll(loginText2, userNameField, passwordField, passwordField2);

		/* Put Vertical Box in the Stack Pane */
		StackPane root21 = new StackPane();
		root21.getChildren().addAll(rect1, hbox);
		return root21;
	}

	public StackPane getRegisterBoxPane() {
		return registerBoxPane;
	}

	public StackPane getRegisterBoxPane2() {
		return registerBoxPane2;
	}

	public Button getLoginBtn() {
		return loginBtn;
	}

	public Button getSignupBtn() {
		return signUpBtn;
	}

	public Button getCancelBtn() {
		return cancel;
	}

	public StackPane getRoot() {
		return root;
	}

	public Button getRegisterBtn() {
		return register;
	}

	public VBox getLoginVBox() {
		return loginVBox;
	}

	public HBox getHBox() {
		return hbox;
	}

	public VBox getVBox1() {
		return vbox1;
	}

	public Rectangle getRect1() {
		return rect1;
	}

	public TextField getUserNameField() {
		return userNameField;
	}

	public TextField getPasswordField() {
		return passwordField;
	}

	public TextField getPasswordField2() {
		return passwordField2;
	}

}
