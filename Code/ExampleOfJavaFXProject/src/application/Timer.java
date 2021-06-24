package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Timer {
	private int mins = 0, secs = 0, millis = 0;
	private boolean sos;
	private Timeline timeline;
	private Text stopwatchTime;
	private double elapsedHour;
	private boolean paused; 
		
	public Timer() {
		paused = false;
		
		sos = true; 
		Font font = new Font("Arial", 30);
		stopwatchTime = new Text("00:00");
	    stopwatchTime.setFont(font);

		timeline = new Timeline(new KeyFrame(Duration.millis(1),
								new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				change(stopwatchTime);
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(false);
	}
	public void setPaused(boolean b) {
		paused = b;
	}
	public boolean getPaused() {
		return paused;
	}
	public void change(Text text) {
		if(millis == 1000) {
			secs++;
			millis = 0;
		}
		if(secs == 60) {
			mins++;
			secs = 0;
		}
		text.setText((((mins/10) == 0) ? "0" : "") + mins + ":"
		 + (((secs/10) == 0) ? "0" : "") + secs);
		

		setElapsedHour();
		millis++;
	}
	public void playTimeline() {
		timeline.play();
	}
	
	public void pauseTimeline() {
		timeline.pause();
	}
	
	public boolean getSos() {
		return sos;
	}
	
	public void setSos(boolean value) {
		sos = value; 
	}
	
	public Timeline getTimeline() {
		return timeline; 
	}
	public void setMins(int value) {
		mins = value; 
	}
	public void setTimerText(String value) {
		stopwatchTime.setText(value);
	}
	
	public void setSecs(int value) {
		secs = value; 
	}
	public void setMillis(int value) {
		millis = value;
	}
	public Text getText() {
		return stopwatchTime; 
	}
	
	//returns array of size 3 that holds [0]millis, [1]secs, [2]mins
	public int[] getTimes()
	{
		int [] arr = new int[3];
		arr[0] = millis;
		arr[1] = secs;
		arr[2] = mins;
		
		return arr;
	}
	
	public void setElapsedHour()
	{
		elapsedHour = calcElapsedHour();
	}
	
	public double getElapsedHour()
	{
		return elapsedHour;
	}
	
	public double calcElapsedHour()
	{		
		double notRoundedRes = (double) ((double)mins / 60.0) + ((double) secs / 3600.0);
		double roundedRes = (Math.round(notRoundedRes * 10000.0) / 10000.0);
		return roundedRes;
	}
	
	
}
