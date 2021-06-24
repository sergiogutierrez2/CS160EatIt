package application;

import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DataChartGUI {
	
	private Loader myL = Loader.getSingleLoaderInstance();
	
	private  LineChart<String, Number> lineChart;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void createGraph(boolean isUpdate) {
		 
		//defining the axes
        CategoryAxis xAxis = new CategoryAxis();
        Axis<Number> yAxis = new NumberAxis();
       
        xAxis.setLabel("Days You Have studied ");
        yAxis.setLabel("Hours Spent Studying");
        
       //creating the chart
       lineChart = new LineChart<String, Number>(xAxis,yAxis);
               
       lineChart.setTitle("Weekly Study Hour Overview");
       
       //defining a series
       XYChart.Series series = new XYChart.Series();
       series.setName("Hours Spent Studying");
       
       
       if(isUpdate)
       {
    	   series.getData().clear();
       }
       
       for (int i = 0; i < 7; i++)
       {									//STRING												DOUBLE
    	   series.getData().add(new XYChart.Data( myL.getDayOfTheWeek(myL.getTimeLogObjAtIndex(i)), myL.getTime(myL.getTimeLogObjAtIndex(i))));
       }
       
       //FOR UPDATING DATA JUST DELETE EVERYTHING AND ADD THEM ALL AGAIN
       
		//this is how we clear before we update!!! 
        //series.getData().clear();
       
       lineChart.getData().add(series);
	}

	public StackPane dataChart() {
        
		createGraph(false);
		
	    // data: blue background
	    Rectangle dataBackground = new Rectangle(600,300);
	    dataBackground.setArcHeight(40.0);
	    dataBackground.setArcWidth(40.0);
	    dataBackground.setFill(Color.web("#DEE7EC",1));    
	    
	    // data: layout
	    StackPane data = new StackPane();
	    data.getChildren().addAll(dataBackground,lineChart);
	    return data; 
	}
}
