package application.GUI;

import application.User;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ExecutableAndNotExecGUI_View 
{
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
		
		TitledPane executableListTitlePane = new TitledPane("Executable Recipes", executableTable.getTableView());
		executableListTitlePane.setMinWidth(350);
		TitledPane not_executableListTitlePane = new TitledPane("Non-Executable Recipes", not_executableTable.getTableView());
		not_executableListTitlePane.setMinWidth(350);
		main_VBox = new VBox(executableListTitlePane, not_executableListTitlePane);
		
		Rectangle background = new Rectangle(445,550);
		background.setArcHeight(40.0);
		background.setArcWidth(40.0);
		background.setFill(Color.web("#e3e3e3",1));
		
		StackPane stackPane = new StackPane(background, main_VBox);
		
		bothExecutableListsTitlePane = new TitledPane("Executable Recipes", stackPane);
		
	}
	
	public void updateTables()
	{
		executableTable.updateTable(user, true);
		not_executableTable.updateTable(user, false);
	}
	
	public TitledPane getTitledPane()
	{
		return bothExecutableListsTitlePane;
	}

}
