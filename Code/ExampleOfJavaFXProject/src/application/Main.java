package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.StringTokenizer;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
	private double mainRectWidth = 1100, mainRectHeight = 650;
	private Scene signUpPageScene = null;
    private Loader myL = Loader.getSingleLoaderInstance();
    private Scene loginPageScene = null;
    
    private int flag = 0; 
    String credentialFile = "src/application/credentials.txt"; 
    
    private ObservableList<Task> tableData = FXCollections.observableArrayList();
    
    private DataChartGUI dataChartGUI = new DataChartGUI();    
    private StackPane data; 
	private TimerGUI timerGUI = new TimerGUI();

	@Override
	public void start(Stage stage) throws IOException {		
		//Login
		buildLoginPage(stage);
	 	}
	
	public void buildLoginPage(Stage stage) {
		LoginGUI loginGUI = new LoginGUI();
		StackPane root = loginGUI.getRoot();
		Button loginBtn = loginGUI.getLoginBtn();
		Button signUpBtn = loginGUI.getSignupBtn();
		
		//Login button clicks
		loginBtn.setOnAction(e->{
			loginBtnAction(stage, loginGUI);
		});
		
		// Sign up page
		signUpBtn.setOnAction(e->
		{
			
			signUpBtnAction(stage, loginGUI);
		});
		
		loginPageScene = new Scene(root, 1280, 720);
		
		stage.setTitle("Login Page");
	    loginPageScene.setFill(Color.TRANSPARENT);
	    stage.setWidth(1280);
	    stage.setHeight(720);
	    stage.setScene(loginPageScene);
	    stage.show();
	}
	
	public void loginBtnAction(Stage stage, LoginGUI loginGUI) {
            boolean succesfull=false;
            
            try {
            	/*checks if login credentials match with account in file, false tells method it is not creating a new account*/
            	succesfull= StoreAccountInfo(loginGUI, MouseEvent.MOUSE_CLICKED,credentialFile,false);
            } catch (IOException e1) {
            	e1.printStackTrace();
            }
            
            /*if the login credentials match then open main menu*/
            if (succesfull == true) {
            	
            	 Scene homePageScene = buildHomePage(stage, loginPageScene);
        		 
        		 stage.setTitle("Home Page");
        		 homePageScene.setFill(Color.TRANSPARENT);
        		 stage.setWidth(1280);
        		 stage.setHeight(850);
        		 stage.setScene(homePageScene);
        		 stage.show();
            	
            }else {
            		/* show error if login failed*/
            		if(flag == 0) {
            			String error = "Error, incorrect username or password please try again.";
            			errorMSG(error);
            		}
            }      
	}

	public void signUpBtnAction(Stage stage, LoginGUI loginGUI) {
		StackPane signUpPageRoot = loginGUI.signupSetUp();
    	
        /* create scene and stage*/
        signUpPageScene = new Scene(signUpPageRoot, 1280, 720);
        stage.setScene(signUpPageScene); 
        stage.setTitle("Sign Up Page");
        
        Button registerBtn = loginGUI.getRegisterBtn();
        registerBtn.setOnAction(e2->{
        	registerBtnAction(stage, loginGUI);
        }); 
        
        Button cancelBtn = loginGUI.getCancelBtn();
        cancelBtn.setOnAction(e2->{
        	// build loginPage again 
        	buildLoginPage(stage);
        });
	}
	
	public void registerBtnAction(Stage stage, LoginGUI loginGUI) {
		boolean succesfull=false;             
    	try {
    		/* calls method that creates accounts while also making sure account is brand new and passwords entry match*/
    		succesfull=StoreAccountInfo(loginGUI, MouseEvent.MOUSE_CLICKED,credentialFile,true);
    	} catch (IOException e1) {
    		e1.printStackTrace();
    	}
   
    	/*If account is successfully made then it opens the main menu*/
    	if (succesfull == true) {
	   
    		Scene sceneHomePage = buildHomePage(stage, loginPageScene);
		 
    		stage.setTitle("Home Page");
    		sceneHomePage.setFill(Color.TRANSPARENT);
    		stage.setWidth(1280);
    		stage.setHeight(850);
    		stage.setScene(sceneHomePage);
		 	
    		StackPane root2 = new StackPane();
    		Label label = new Label("You are now registered.");
    		root2.getChildren().add(label);
    		Scene secondScene = new Scene(root2, 500,500);
    		Stage secondStage4 = new Stage();
    		secondStage4.setScene(secondScene);
    		secondStage4.setTitle("Second Form");
    		secondStage4.show();               
    	}else {
	   
    		/*displays the error that occurred when making an account*/
    		if(flag == 3) {
    			String error = "Error, this account already exist";
    			errorMSG(error);
    		}else if (flag == 1) {
    			String error = "Error,your password entries did not match. Please try again.";
    			errorMSG(error);
    		}else if (flag == 2) {
    			String error = "Error, that user username already exist. Please sign in or use a new username.";
    			errorMSG(error);
    		}
    	}
	}
	
	public Scene buildHomePage(Stage stage, Scene scene) {
		
		TaskGUI taskGUI = new TaskGUI(tableData);
    	/* Tool Bar */  
		HomePageToolBarGUI toolBarGUI = new HomePageToolBarGUI();
		HBox toolBarContent = toolBarGUI.toolBar(stage, scene);
		Button logoutBtn = toolBarGUI.getLogoutBtn();
		
		logoutBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, eH -> 
        {
        	//taskGUI.saveCompletedStatus();
        	System.out.println("Printing Obserable Array!");
        	myL.printObservableArrayList(tableData);
        	System.out.println("mostRecentUserName: " + myL.getMostRecentUsername());
        	myL.writeUserInfoFile(myL.getMostRecentUsername());
        	stage.close();
        	//stage.setScene(scene);
         });
		
		/* Timer */
		timerGUI = new TimerGUI();
		StackPane timer = timerGUI.getTimer();
		Button sButton = timerGUI.getsButton();
		Button rButton = timerGUI.getrButton();
		Timer stopWatch = timerGUI.getStopwatch();		
		sButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sButtonAction(sButton, stopWatch, stage, scene);
			}
		});
		
		rButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				rButtonAction(rButton, sButton, stopWatch, stage, scene);
			}
		});
		
		/* Data Chart */
		data = drawDataChart();
		
		/* Vertical layout pane for timer and data */
		 VBox leftWidgets = new VBox();
		 leftWidgets.getChildren().addAll(timer, data);    
		 leftWidgets.setSpacing(20);
		 leftWidgets.setPadding(new Insets(20,20,20,20));
		
		 StackPane highlights = taskGUI.highlights();
		 
		//Tasks Table
   		 VBox taskTable = taskGUI.createTable();
   		 
   		 //create background
   		Rectangle background = new Rectangle(600,620);
		background.setArcHeight(40.0);
		background.setArcWidth(40.0);
		background.setFill(Color.web("#DEE7EC",1));

   		 // stack task and highlights
   		 VBox rightWidgets = new VBox();
   		 rightWidgets.getChildren().addAll(highlights, taskTable);
   		 rightWidgets.setSpacing(40);
   		 rightWidgets.setPadding(new Insets(20,20,20,20));
   		 
   		 StackPane stackHighlightsAndTasks = new StackPane();
   		 stackHighlightsAndTasks.getChildren().addAll(background,rightWidgets);
   		 
   		 
		 // Set up Page Layout
		 BorderPane rootHomePage = new BorderPane();
		 rootHomePage.setTop(toolBarContent);
		 rootHomePage.setLeft(leftWidgets);
		 rootHomePage.setRight(stackHighlightsAndTasks);
		 
		 Scene sceneHomePage = new Scene(rootHomePage, 1280, 720);
		 URL url = this.getClass().getResource("application.css");
		 String css = url.toExternalForm();
		 sceneHomePage.getStylesheets().add(css);
		
		 return sceneHomePage;
	}
	
	public void sButtonAction(Button sButton, Timer stopWatch, Stage stage, Scene scene) {
		System.out.println("Value of paused sos = "+ stopWatch.getSos());
			if(stopWatch.getSos()) {
				System.out.println("stop button is clicked");
				
				myL.setPreviousElapsedTime();
				myL.resetTotalElapsedTime();
				myL.updateTimeLogArray();

				dataChartGUI.createGraph(true);
				data = dataChartGUI.dataChart();
								
				stopWatch.playTimeline();
				stopWatch.setSos(false);
				sButton.setText("Stop");
				System.out.println("Elapsed Hour: " + stopWatch.getElapsedHour() +" (1)");
				
				} else {
					myL.setPreviousElapsedTime();
					myL.resetTotalElapsedTime();
					myL.updateTimeLogArray();
					dataChartGUI.createGraph(true);
					stopWatch.pauseTimeline();
					stopWatch.setSos(true);
					sButton.setText("Start");
					System.out.println("Elapsed Hour: " + stopWatch.getElapsedHour() +" (2)");
					
										
			}
	}
	
	public void rButtonAction(Button rButton, Button sButton, Timer stopWatch, Stage stage, Scene scene) {
		//reset button:
		myL.setTotalElapsedTime(stopWatch.getElapsedHour());
		myL.setPreviousElapsedTime();
		myL.resetTotalElapsedTime();
		myL.updateTimeLogArray();
		
		Scene homePageScene = buildHomePage(stage, loginPageScene);
		stage.setTitle("Home Page");
		homePageScene.setFill(Color.TRANSPARENT);
		stage.setWidth(1280);
		stage.setHeight(850);
		stage.setScene(homePageScene);
		stage.show();
		
		stopWatch.setMins(0);
		stopWatch.setSecs(0);
		stopWatch.setMillis(0);
		stopWatch.pauseTimeline();
		stopWatch.setTimerText("00:00");
		
		if(stopWatch.getSos() == false) {
			stopWatch.setSos(true);
			sButton.setText("Start");
		}			        
	}
	
	public StackPane drawDataChart() {
		data = dataChartGUI.dataChart();
		return data;
	}
	
	public boolean StoreAccountInfo(LoginGUI loginGUI, EventType<MouseEvent> mouseClicked, String file, boolean create) throws IOException {
		TextField userNameField = loginGUI.getUserNameField();
		TextField passwordField = loginGUI.getPasswordField();
		TextField passwordField2 = loginGUI.getPasswordField2();
		
		StringBuilder username = new StringBuilder();
		StringBuilder password = new StringBuilder();
		StringBuilder reEnterPass = new StringBuilder();
		boolean isItDuplicate = false;
		
		
		/* gets input from textbox and stores them in username and password */
		
		username.append(userNameField.getText().toString());
		password.append(passwordField.getText().toString());
		
		/*if passwordField is not null then the user must be signing up which the checks to see if passwords match*/
		if(passwordField2 != null) {
			
			reEnterPass.append(passwordField2.getText().toString());
			
			if (!password.toString().contentEquals(reEnterPass)) {
				flag = 1;
				return false;
			}	
		}
		
		/*checks if username already exist*/
		if(checkIfUsernameDup(file, username) && create == true) {
			flag=2;
			return false;
		}
		
		/*checks if an account with exact same name and password exist*/
		if(create == true) {
		isItDuplicate = readFile(file,username,password);
		if(isItDuplicate == true) {
			flag = 0;
			return false;
		}else{
			
		/*no duplicate account is detected so it beings saving the information*/	
		
		try {
			File myObj = new File(credentialFile);
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		Writer write;
    	try {
			write = new BufferedWriter(new FileWriter(credentialFile, true));
			write.write(username.toString());
			write.write(" ");
			write.write(password.toString());
			write.write("\n");
			write.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    	myL.createUserInfoFile(username.toString());
	    	myL.readUserInfoFile(username.toString());
	    	
	    	for(int i = 0; i < myL.taskArraySize; i++)
	    	{
	    		tableData.add(myL.taskArray[i]); 
	    	}
	    	
			return true;
		}
		
		}
		else
		{
			/*the user was not creating a class, instead he was signing in so we check if login credentials match and exist*/
			return readFile(file,username,password);
		}
	}
	
	public boolean readFile(String file,StringBuilder username,StringBuilder password) 
	{    
		String testUser,Pass = null;
		
		try {
		
			FileReader Accountfile = new FileReader(file);
		    BufferedReader buff = new BufferedReader(Accountfile);
		    boolean eof = false;
		
		while(!eof) // does this while its not the end of file
		{
			
			String line = buff.readLine(); // Gets a line from the file

			if(line==null)
				eof = true; // if the line is null then its the end of the file 
			
			if(line != null)
			{
				StringTokenizer st = new StringTokenizer(line);	// converts line into tokens 
				
					while(st.hasMoreTokens()) // while their is tokens it will continue 
					{
						testUser = st.nextToken(); // gets token from converted line and puts it in testUser
						if(st.hasMoreTokens())
							Pass = st.nextToken();
						
						/* checks to see if login information matches an account in the file*/
						if(testUser.contentEquals(username) && Pass.contentEquals(password)) {
							flag = 3;
							buff.close();
							
							myL.createUserInfoFile(username.toString());
			            	myL.readUserInfoFile(username.toString());
			            	
			            	//update observableList 
			            	for(int i = 0; i < myL.taskArraySize; i++)
			    	    	{
			    	    		tableData.add(myL.taskArray[i]); 
			    	    	}
							return true;
						}
					}			
			}		
}
		buff.close();
		}catch(IOException e){
			
			System.out.printf("Error -- %s ", e.toString()); // prints out exception if their is one 
		}
    
		return false;
	   }

	public boolean checkIfUsernameDup(String file,StringBuilder username) 
	{    
		String testUser;
		
		try {
		
			FileReader Accountfile = new FileReader(file);
		    BufferedReader buff = new BufferedReader(Accountfile);
		    boolean eof = false;
		
		while(!eof) //does this while its not the end of file
		{
			
			String line = buff.readLine(); // gets a line from the file

			if(line==null)
				eof = true; // if the line is null then its the end of the file 
			
			if(line != null)
			{
				StringTokenizer st = new StringTokenizer(line);	// converts line into tokens 
				
					while(st.hasMoreTokens()) // while their is tokens it will continue 
					{
						testUser = st.nextToken(); // gets token from converted line and puts it in testUser
						
						//checks if username already exist
						if(testUser.contentEquals(username)) {
							buff.close();
							return true;
						}
					}			
			}		
		}
		buff.close();
		}catch(IOException e){
			
			System.out.printf("Error -- %s ", e.toString()); // prints out exception if their is one 
		}
    
		return false;
	   }
    
    public void errorMSG(String msg) {
    	
    	StackPane root10 = new StackPane();
        Label label = new Label(msg);
        
        /*create background*/
        Rectangle background = new Rectangle(mainRectWidth, mainRectHeight);
        background.setFill(Color.web("#DEE7EC",1));
        background.setArcHeight(40.0);
        background.setArcWidth(40.0);
       
        /*create button*/
        Button okButton = new Button("Ok");
        
        okButton.setStyle("-fx-background-color: #AB81CD; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
	    okButton.setCursor(Cursor.HAND);
	    
	    StackPane stack = new StackPane();
	    VBox layout = new VBox();
	    BorderPane center= new BorderPane();
	    
	    /*align button and label in the correct spot*/
	    layout.getChildren().addAll(label,okButton);
	    layout.setSpacing(50);
	    layout.setAlignment(Pos.CENTER);
	    
	    /*put background,button, and label layout together*/
	    stack.getChildren().addAll(background,layout);
	    center.setCenter(stack);

	    /*create scene and stage*/
        root10.getChildren().add(center);
        Scene secondScene = new Scene(root10,600, 600);
        Stage secondStage5 = new Stage();
        secondStage5.setScene(secondScene); 
        secondStage5.setTitle("Second Form");
        secondStage5.show();
     	 
        /*close error pop up page*/
        okButton.setOnAction(e3->{  
        	
            secondStage5.close();
        });       	   
    	
    }
    
	public static void main(String[] args) {
		launch(args);
	}
}
