package application;

public class simpleTimer 
{
	private double elapsedHour;
	private String date; //YYYY-MM-DD
	private String dayOfWeek; //"Monday" to "Sunday"
	
	public simpleTimer(String date, String dayOfWeek, double elapsedHour)
	{
		this.date = date;
		this.dayOfWeek = dayOfWeek;
		this.elapsedHour = elapsedHour;
		
	}
	
	public void setAll(String date, String dayOfWeek, double elapsedHour)
	{
		this.date = date;
		this.dayOfWeek = dayOfWeek;
		this.elapsedHour = elapsedHour;
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}
	
	public void setDayOfWeek(String dayOfWeek)
	{
		this.dayOfWeek = dayOfWeek;
	}
		
	public void setElapsedHour(double elapsedHour)
	{
		this.elapsedHour = elapsedHour;
	}
		
	public double getElapsedHour()
	{
		return elapsedHour;
		
	}
	
	public String getDate()
	{
		return date;
	}
	
	public String getdayOfWeek()
	{
		return dayOfWeek;
	}
	
	public String getSimpleTimerString()
	{
		return (date + " | " + dayOfWeek + " | " + elapsedHour + " |\n");
	}
	
	public void printSimpleTimerString()
	{
		System.out.println("TimeLog: " +date + ", " + dayOfWeek + ", " + elapsedHour);
	}
	
}
