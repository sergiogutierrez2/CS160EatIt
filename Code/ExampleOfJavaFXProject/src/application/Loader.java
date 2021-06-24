package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File; // Import the File class
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException; // Import the IOException class to handle errors
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Loader {

	
	private static Loader singleInstance = new Loader();
	
	
	final private String credentialFile = "src/application/credentials.txt";
	private String usernameFile;
	private String [][]credentialsArray;
	
	Task[] taskArray; 
	int taskArraySize = 0; //initial set when reading username.txt
	private String mostRecentUsername;
	int timeLogArraySize = 0;
	int maxTimeLogArraySize = 7;
	private simpleTimer[] timeLogArray = new simpleTimer[7];
	
	String currentMondayDate;
	
	private double totalElapsedTime = 0;
	private double previousTotalElapsedTime = 0;
	
	//what needs to be saved and return to GUI from login
	public ObservableList<Task> observableList_TaskTableData;
	
	//test1_BEGIN
	private final ObservableList<Task> tableData1 = FXCollections.observableArrayList(
			new Task("Finish Homework","04-21", "CS151"),
			new Task("Quiz", "05-18", "CS151"));
	//test1_END
	
	
	
	
	private Loader()
	{
		taskArraySize = 0;
	}
	
	public static Loader getSingleLoaderInstance()
	{
		return singleInstance;
	}
	
	String getMostRecentUsername()
	{
		return mostRecentUsername;
	}
	
	int getNumberOfCompleted()
	{
		int count = 0;
		for(int i = 0; i < taskArraySize; i++)
		{
			if(taskArray[i].getCompleted())
			{
				count++;
			}
		}
		return count;
	}
	
	private void setMostRecentUsername(String usrname) 
	{
		mostRecentUsername = usrname;
	}
	
	void updateTimeLogArray()
	{
		int index = getIndexOfDayOfTheWeek(getCurrentDayOfTheWeek());
		timeLogArray[index].setElapsedHour(previousTotalElapsedTime + totalElapsedTime);
	}
	
	int getIndexOfDayOfTheWeek(String dayOfTheWeek)
	{
		int res;
		switch(dayOfTheWeek)
		{
		case "Monday":
			res = 0;
			break;
		case "Tuesday":
			res = 1;
			break;
		case "Wednesday":
			res = 2;
			break;
		case "Thursday":
			res = 3;
			break;
		case "Friday":
			res = 4;
			break;
		case "Saturday":
			res = 5;
			break;
		case "Sunday":
			res = 6;
			break;
		default:
			res = -1;
		}
		
		return res;
	}
	
	Task buildTask(String taskName, String dueDate, String courseName, String completed)
	{
		Task resTask = new Task(taskName, dueDate, courseName);
		if(completed.equals("False"))
		{
			//System.out.println("marking as false!");
			resTask.markAsIncomplete();;
		}
		else 
		{
			resTask.markAsComplete();
		}
		
		return resTask;
	}
	
	void addTask(Task newTask) //increase taskArraySize By 1
	{
		Task[] tmpTaskArray = new Task[++taskArraySize];
		int i = 0;
		for(; i < taskArraySize - 1; i++)
		{
			tmpTaskArray[i] = taskArray[i];
		}
		tmpTaskArray[i] = newTask;
		taskArray = tmpTaskArray;
		
		printTaskArray();
	}
	
	private simpleTimer buildTimeLog(String date, String dayOfWeek, double elapsedTime)
	{
		simpleTimer resTimer = new simpleTimer(date, dayOfWeek, elapsedTime);
		return resTimer;
	}
	
	private void addTimeLog(simpleTimer newTimeLog) //increase taskArraySize By 1
	{
		simpleTimer[] tmpTimeLogArray = new simpleTimer[++timeLogArraySize];
		int i = 0;
		for(; i < timeLogArraySize - 1; i++)
		{
			tmpTimeLogArray[i] = timeLogArray[i];
		}
		tmpTimeLogArray[i] = newTimeLog;
		timeLogArray = tmpTimeLogArray;
	}
	
	void initializeTimeLogArray()
	{
		timeLogArray = new simpleTimer[7];
		String date = getCurrentMondayDate();
		timeLogArray[0] = buildTimeLog(date, "Monday", 0);
		date = getNextDay(date);
		timeLogArray[1] = buildTimeLog(date, "Tuesday", 0);
		timeLogArray[2] = buildTimeLog(date, "Wednesday", 0);
		date = getNextDay(date);
		timeLogArray[3] = buildTimeLog(date, "Thursday", 0);
		date = getNextDay(date);
		timeLogArray[4] = buildTimeLog(date, "Friday", 0);
		date = getNextDay(date);
		timeLogArray[5] = buildTimeLog(date, "Saturday", 0);
		date = getNextDay(date);
		timeLogArray[6] = buildTimeLog(date, "Sunday", 0);
	}
	
	static String getNextDay(String date)
	{
		
		int lastDayOfMonth = getLastDayOfTheMonth(date.substring(5, 7));
		int currentYear = Integer.parseInt(date.substring(0, 4));
		//System.out.println("currentYear:" + currentYear);
		int currentDay = Integer.parseInt(date.substring(getCurrentDate().length() - 2));
		int nextDay;
		
		String nextDayRes;
		String currentMonth = date.substring(5, 7);
		String monthRes;
		String yearRes;
		
		if((currentDay + 1) > lastDayOfMonth)
		{
			nextDay = 0;
			if(currentMonth.equals("12"))
			{
				currentYear++;
				//System.out.println("");
				yearRes = String.valueOf(currentYear);
				//System.out.println("yearRes:" + yearRes);
			}
			else
			{
				yearRes = String.valueOf(currentYear);
			}
			monthRes = getNextMonth(currentMonth);
			nextDayRes = String.valueOf(nextDay);
		}
		else
		{
			nextDay = currentDay + 1;
			nextDayRes = String.valueOf(nextDay);
			monthRes = currentMonth;
			yearRes = String.valueOf(currentYear);
		}
		if(monthRes.length() == 1)
		{
			monthRes = "0" + monthRes;
		}
		if(nextDayRes.length() == 1)
		{
			nextDayRes = "0" + nextDayRes;
		}
		
		return (yearRes + "-" + monthRes + "-" + nextDayRes);
	}
	
	void setCurrentMondayDate()
	{
		currentMondayDate = getCurrentMondayDate();
	}
	
	static String getCurrentDate()
	{
		LocalDate localDate = LocalDate.now();
		String dateStr = localDate.toString();
		return dateStr;
	}
	
	static String getCurrentDayOfTheWeek()
	{
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		String dayOfWeekStr = sdf.format(now).toString();
		
		return dayOfWeekStr;
	}
	
	static int getDaysFromMonday(String dayOfTheWeek)
	{
		int res;
		switch(dayOfTheWeek)
		{
		case "Monday":
		    res = 0;
		    break;
		  case "Tuesday":
		    res = 1;
		    break;
		  case "Wednesday":
		    res = 2;
		    break;
		  case "Thursday":
		    res = 3;
		    break;
		  case "Friday":
		    res = 4;
		    break;
		  case "Saturday":
		    res = 5;
		    break;
		  case "Sunday":
		    res = 6;
		    break;
			    
		  default:
			  res = -1;
		}
		return res;
	}
	
	static String getPreviousMonth(String month)
	{
		String res;
		switch(month)
		{
		case "01":
		    res = "12";
		    break;
		  case "02":
		    res = "01";
		    break;
		  case "03":
		    res = "02";
		    break;
		  case "04":
		    res = "03";
		    break;
		  case "05":
		    res = "04";
		    break;
		  case "06":
		    res = "05";
		    break;
		  case "07":
		    res = "06";
		    break;
		  case "08":
		    res = "07";
		    break;
		  case "09":
		    res = "08";
		    break;
		  case "10":
		    res = "09";
		    break;
		  case "11":
		    res = "10";
		    break;
		  case "12":
		    res = "11";
		    break;
			    
		  default:
			  res = "00";
		}
		return res;
		
	}
	
	static String getNextMonth(String month)
	{
		String res;
		switch(month)
		{
		case "01":
		    res = "02";
		    break;
		  case "02":
		    res = "03";
		    break;
		  case "03":
		    res = "04";
		    break;
		  case "04":
		    res = "05";
		    break;
		  case "05":
		    res = "06";
		    break;
		  case "06":
		    res = "07";
		    break;
		  case "07":
		    res = "08";
		    break;
		  case "08":
		    res = "09";
		    break;
		  case "09":
		    res = "10";
		    break;
		  case "10":
		    res = "11";
		    break;
		  case "11":
		    res = "12";
		    break;
		  case "12":
		    res = "1";
		    break;
			    
		  default:
			  res = "00";
		}
		return res;
		
	}
	
	static String getMonth()
	{
			  									 //0123456789
		return getCurrentDate().substring(5, 7); //2021-05-10
	}
	
	static boolean isDateCurrentMonday(String date)
	{
		return date.equals(getCurrentMondayDate());
	}
	
	static String getCurrentMondayDate()
	{
		String day = getCurrentDate().substring(getCurrentDate().length() - 2);
		
		int dayInt = Integer.parseInt(day);
		dayInt = dayInt - getDaysFromMonday(getCurrentDayOfTheWeek());
		
		String mondayYear = getCurrentDate().substring(0, 4);
		String currentMonth = getMonth();
		String mondayMonth;
		String mondayDay;
		if(dayInt == 0)
		{
			if(currentMonth == "01")
			{
				int yearInt = Integer.parseInt(mondayYear);
				yearInt--;
				mondayYear = String.valueOf(yearInt);
			}
			mondayMonth = getPreviousMonth(currentMonth);
			dayInt = getLastDayOfTheMonth(getPreviousMonth(currentMonth));
			mondayDay = String.valueOf(dayInt);
		}
		else if(dayInt < 0)
		{
			if(currentMonth == "01")
			{
				int yearInt = Integer.parseInt(mondayYear);
				yearInt--;
				mondayYear = String.valueOf(yearInt);
			}
			mondayMonth = getPreviousMonth(currentMonth);
			dayInt = getLastDayOfTheMonth(getPreviousMonth(currentMonth)) + dayInt;
			mondayDay = String.valueOf(dayInt);
		}
		else
		{
			mondayDay = String.valueOf(dayInt);
			mondayMonth = getMonth();
			mondayYear = getCurrentDate().substring(0, 4);
		}
		if(mondayMonth.length() == 1)
		{
			mondayMonth = "0" + mondayMonth;
		}
		if(mondayDay.length() == 1)
		{
			mondayDay = "0" + mondayDay;
		}
		
		return mondayYear + "-" + mondayMonth + "-" + mondayDay;
	}
	
	static int getLastDayOfTheMonth(String month)
	{
		
		int option = Integer.parseInt(month);
		int res;
		switch(option)
		{
		case 1:
		    res = 31;
		    break;
		  case 2:
		    res = 28;
		    break;
		  case 3:
		    res = 31;
		    break;
		  case 4:
		    res = 30;
		    break;
		  case 5:
		    res = 31;
		    break;
		  case 6:
		    res = 30;
		    break;
		  case 7:
		    res = 31;
		    break;
		  case 8:
		    res = 31;
		    break;
		  case 9:
		    res = 30;
		    break;
		  case 10:
		    res = 31;
		    break;
		  case 11:
		    res = 30;
		    break;
		  case 12:
		    res = 31;
		    break;
			    
		  default:
			  res = -1;
		}
		return res;
		
	}
	//boolean isCurrentMonday
	
	//removes targetTask and decrease size of taskArray
	private void removeTask(Task targetTask)
	{
		System.out.println("Removing Task: ");
		int removalIndex = findTask(targetTask);
		if(removalIndex == -1)
		{
			System.out.println("No task to remove");
			return;
		}
		
		Task[] tmpTaskArray = new Task[taskArraySize - 1];
		//we need to make a taskArray that 
		//includes everything except targetTask
		for(int i = 0, j = 0; i < taskArraySize; i++, j++)
		{
			if(i == removalIndex)
			{
				j--;
			}
			else 
			{
				tmpTaskArray[j] = taskArray[i];
			}
		}
		taskArray = tmpTaskArray;
		taskArraySize--;
	}
	
	void updateTask(Task task)
	{
		System.out.println("TaskArraySize: " + taskArraySize);
		//System.out.println("Passed in Task.complete: " + task.getCompleted());
		int index = findTaskSpecial(task);
		System.out.println("index: " + index);
//		if(index != -1)
//		{
		taskArray[index].setCompleted(task.getCompleted());
		//}
		//printTaskArray();
		writeUserInfoFile(mostRecentUsername);
	}
	//returns index in taskArray of targetTask
	private int findTask(Task targetTask)
	{
		for(int i = 0; i < taskArraySize; i++)
		{
			if(taskArray[i].isSameTask(targetTask))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	private int findTaskSpecial(Task targetTask)
	{
		for(int i = 0; i < taskArraySize; i++)
		{
			if(taskArray[i].hasSameNameCourseDueDate(targetTask))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	private void printTaskArray()
	{
		System.out.println("Printing Task Array");
		for(int i = 0; i < taskArraySize; i++)
		{
			taskArray[i].printTask();
		}
		if(taskArraySize == 0) {
			System.out.println("Task Array is Empty");
		}
	}
	
	void printTimeLogArray()
	{
		System.out.println("Printing Time Log Array");
		for(int i = 0; i < timeLogArraySize; i++)
		{
			timeLogArray[i].printSimpleTimerString();
		}
		if(timeLogArraySize == 0) {
			System.out.println("Task Array is Empty");
		}
	}
	
	private void setUsername(String username)
	{
		usernameFile = getUsernameFile(username);
	}
	
	private void makeObservableArrayListFromTaskArray()
	{
		ObservableList<Task> tableData = FXCollections.observableArrayList();
		
		for (int i = 0; i < taskArraySize; i++)
		{
			tableData.add(taskArray[i]);
		}
		
		observableList_TaskTableData = tableData;
	}
	
	ObservableList<Task> getObservableArrayList()
	{
		return observableList_TaskTableData;
	}
	
	private void printObservableArrayList()
	{
		for(Task tmpTask : observableList_TaskTableData)
		{
			tmpTask.printTask();
		}
	}
	
	void printObservableArrayList(ObservableList<Task> taskList)
	{
		for(Task tmpTask : taskList)
		{
			tmpTask.printTask();
		}
	}
	
	private void makeTaskArrayFromObservableArrayList(ObservableList<Task> taskList)
	{
		int sizeArrayList = taskList.size();
		Task[] tmpTaskArray = new Task[sizeArrayList];
		int i = 0;
		
		for(Task tmpTask : taskList)
		{
			tmpTaskArray[i] = tmpTask;
			i++; 
		}
		taskArraySize = i;
		taskArray = tmpTaskArray;
	}
	
	void deleteTaskArrayFromObservableArrayList(ObservableList<Task> taskList)
	{
		for(Task tmpTask : taskList)
		{
			removeTask(tmpTask);
		}
		printTaskArray();
	}
	
	private String getUsernameFile(String username)
	{
		return "src/application/" + username + ".txt";
	}
	
	private boolean hasSpaces(String str) 
	{
		for (int i = 0; i < str.length(); i++) 
		{
			if (str.charAt(i) == ' ') 
			{
				return true;
			}
		}
		return false;
	}
	
	private void createCredentialsFile()
	{
		try {
			// File fileDirObj = new File("test");
			File myObj = new File(credentialFile);
//			if(fileDirObj.mkdir())
//			{
//				System.out.println("Directory create: " + fileDirObj.getName());
//
//			}
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	private void readCredentialsFile()
	{
		if(!(isFileEmpty(credentialFile)))
		{
			//count how many credentials(rows) are in the array
			int rows = countRows();
			String [][]arr = new String[rows][2];
			int i = 0, j = 0;
			Scanner sc2 = null;
		    try {
		        sc2 = new Scanner(new File(credentialFile));
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();  
		    }
		    while (sc2.hasNextLine()) {
		            Scanner s2 = new Scanner(sc2.nextLine());
		        while (s2.hasNext()) {
		            arr[i][j] = s2.next();
		            if(i < rows) {
		            	if(++j < 2)
		            	{
		            	}
		            	else 
		            	{
		            		i++;
		            		j = 0;
		            	}
		            }
		        }
		    }	
		    credentialsArray = arr;
		}
		else
		{
			credentialsArray = null;
			System.out.println("Credential.txt is empty\n");
		}
	}
	
	private void writeCredentialsFile(String username, String password)
	{
		if(!isDuplicateUsername(username))
		{
			Writer output;
	    	try {
				output = new BufferedWriter(new FileWriter(credentialFile, true));
				
				output.append(username + " " + password + "\n");
			    output.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Username already taken :(");
		}
		
	}
	
	
	private int countRows() {
		int rows = 0;
		

	    try {
	      // create a new file object
	      File file = new File(credentialFile);

	      // create an object of Scanner
	      // associated with the file
	      Scanner sc = new Scanner(file);

	      // read each line and
	      // count number of lines
	      while(sc.hasNextLine()) {
	        sc.nextLine();
	        rows++;
	      }
	      //System.out.println("Total Number of Lines: " + rows);

	      // close scanner
	      sc.close();
	    } catch (Exception e) {
	      e.getStackTrace();
	    }
		
		return rows;
	}
	
	
	void createUserInfoFile(String username)
	{
		//timeLogArray = null;
		setMostRecentUsername(username);
		usernameFile = getUsernameFile(username);
		try {
			// File fileDirObj = new File("test");
			File myObj = new File(usernameFile);
//			if(fileDirObj.mkdir())
//			{
//				System.out.println("Directory create: " + fileDirObj.getName());
//
//			}
			if (myObj.createNewFile()) {
				System.out.println("\tFile created: " + myObj.getName());
				initializeTimeLogArray();
				writeUserInfoFile(username);

			} else {
				System.out.println("\tFile already exists.");
				//readUserInfoFile(username);
			}
		} catch (IOException e) {
			System.out.println("\tAn error occurred.");
			e.printStackTrace();
		}
	}
	
	
	void readUserInfoFile(String username)
	{
		makeObservableArrayListFromTaskArray();
		initializeTimeLogArray();
		usernameFile = getUsernameFile(username);
		taskArray = null;
		timeLogArray = null;
		taskArraySize = 0;
		timeLogArraySize = 0;
		if(isFileEmpty(usernameFile))
		{
			System.out.println("Reading an empty file");
			initializeTimeLogArray();
		}
		else
		{
			File userFile = new File(usernameFile);
			
			try {
				Scanner s = new Scanner(userFile); //testing if we can open file
				s.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			Scanner s;
			try {
				s = new Scanner(userFile).useDelimiter("\\s*\\|\\s*");
				
				s.next(); //Grabs first word "Task" from file
				int tmpTaskArraySize = s.nextInt(); //gets TaskArraySize
				
				//Set up Task Array:
				for(int i = 0; i < tmpTaskArraySize; i++)
				{
					String tmpTaskName = s.next().toString();
					String tmpTaskDate = s.next().toString();
					String tmpTaskCourse = s.next().toString();
					
					//System.out.println(tmpTaskName + ", " + tmpTaskDate + ", " + tmpTaskCourse);
					String tmpTaskCompleted = s.next();
					addTask(buildTask(tmpTaskName, tmpTaskDate, tmpTaskCourse, tmpTaskCompleted));
				}
				taskArraySize = tmpTaskArraySize; //save TaskArraySize
				
				s.next(); //Grabs word "TimeLogs" from file
				int tmpTimeLogArraySize = s.nextInt(); //gets time log Array size
				
				//Set Up Time Log Array
				if(tmpTimeLogArraySize != 0)
				{
					String tmpTimeLogDate = s.next();
					System.out.println("MondayDate: " + tmpTimeLogDate);
					if(isDateCurrentMonday(tmpTimeLogDate))
					{
						System.out.println("Current Monday True");
						String tmpTimeLogDayOfWeek = s.next();
						System.out.println("MondayDate: " + tmpTimeLogDate);
						double tmpTimeLogElapsedTime = s.nextDouble();
						if(tmpTimeLogDayOfWeek.equals(getCurrentDayOfTheWeek()))
						{
							System.out.print("Setting prevTotatElapsedTime");
							previousTotalElapsedTime = tmpTimeLogElapsedTime;
						}
						System.out.println("tmpTimeLogElapsedTime: " + tmpTimeLogElapsedTime);
						addTimeLog( buildTimeLog(tmpTimeLogDate, tmpTimeLogDayOfWeek, tmpTimeLogElapsedTime ));
						for(int i = 1; i < tmpTimeLogArraySize; i++)
						{
							tmpTimeLogDate = s.next();
							tmpTimeLogDayOfWeek = s.next();
							tmpTimeLogElapsedTime = s.nextDouble();
							addTimeLog( buildTimeLog(tmpTimeLogDate, tmpTimeLogDayOfWeek, tmpTimeLogElapsedTime ));
							if(tmpTimeLogDayOfWeek.equals(getCurrentDayOfTheWeek()))
							{
								System.out.print("Setting prevTotatElapsedTime");
								previousTotalElapsedTime = tmpTimeLogElapsedTime;
							}
						}
						//printTimeLogArray();
					}
					else
					{
						initializeTimeLogArray();
					}
				}
				else 
				{
					initializeTimeLogArray();
				}
				
				
				
//				for(int i = 0; i < tmpTimeLogArraySize; i++)
//				{
//						String tmpTimeLogDate = s.next();
//					
//						String tmpTimeLogDayOfWeek = s.next();
//						double tmpTimeLogElapsedTime = s.nextDouble();
//						addTimeLog( buildTimeLog(tmpTimeLogDate, tmpTimeLogDayOfWeek, tmpTimeLogElapsedTime ));
//				}
				
				//timeLogArraySize = tmpTimeLogArraySize;
				timeLogArraySize = 7;
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
			
	}
	
	private void clearFile()
	{
		try {
			PrintWriter pw = new PrintWriter(usernameFile);
			pw.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			e.printStackTrace();
		}
	}
	
	
	void writeUserInfoFile(String username)
	{
		usernameFile = getUsernameFile(username);
		clearFile();
		System.out.println("usernameFile: " + usernameFile);
		if(isFileEmpty(usernameFile))
		{
			System.out.println("Empty File");
		}
	
		updateTimeLogArray();
		Writer output;
    	try {
			output = new BufferedWriter(new FileWriter(usernameFile, true));
			
			output.append("Tasks | " + taskArraySize +" |\n");
			for(int i = 0; i < taskArraySize; i++)
			{
				output.append(taskArray[i].getTaskString());
			}
			output.append("TimeLogs | " + timeLogArraySize + " |\n");
			
			for(int i = 0; i < timeLogArraySize; i++)
			{
				output.append(timeLogArray[i].getSimpleTimerString());
			}
			
		    output.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private void printCredentialsArray(int rows, int cols)
	{
		System.out.println("Printing Credentials: ");
		for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
            	if(j == 1) {
            		System.out.println(", password: arr[" + i + "][" + j + "] = "
                            + credentialsArray[i][j]);
            	}
            	else
            	{
            		System.out.print("username: arr[" + i + "][" + j + "] = "
                            + credentialsArray[i][j]);
            	}
	}
	
	
	private boolean isDuplicateUsername(String username)
	{
		int rows = countRows();
		readCredentialsFile();
		
		for(int i = 0; i < rows; i++) {
			if(credentialsArray[i][0].equals(username))
			{
				return true;
			}
		}
		return false;
	}
	
	
	private boolean isValidCredential(String username, String password)
	{
		int rows = countRows();
		readCredentialsFile();
		
		for(int i = 0; i < rows; i++) {
			if(credentialsArray[i][0].equals(username))
			{
				if(credentialsArray[i][1].equals(password))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		
		return false;
	}
	
	
	private boolean isFileEmpty(String filenameWithoutType)
	{
		File newFile = new File(filenameWithoutType);

	    if (newFile.length() == 0) {
	      System.out.println("File is empty ...");
	      return true;
	    } else {
	      System.out.println("File is not empty ...");
	      return false;
	    }
	}
	
	static void printObservableTaskArray(ObservableList<Task> thatTaskList)
	{
		for(Task tmpTask : thatTaskList)
		{
			tmpTask.printTask();
		}
	}
	
	
	private void printFileContents(String filenameWithoutType)
	{
		try (BufferedReader br = new BufferedReader(new FileReader("src/application/" 
														+ filenameWithoutType + ".txt"))) 
		{
			   String line;
			    while ((line = br.readLine()) != null) 
			    {
			       System.out.println(line);
			    }
		} catch (IOException e)
		{
				
		}
	}
	
	void increaseTotalElapsedTime(double increaseTime)
	{
		totalElapsedTime += increaseTime;
	}
	
	void setTotalElapsedTime(double time)
	{
		totalElapsedTime = time;
	}
	
	void resetTotalElapsedTime()
	{
		totalElapsedTime = 0;
	}
	
	void setPreviousElapsedTime() 
	{
		previousTotalElapsedTime += totalElapsedTime;
	}

	String getDayOfTheWeek(simpleTimer obj)
	{
		return obj.getdayOfWeek();
	}
	
	Double getTime(simpleTimer obj)
	{
		return obj.getElapsedHour();
	}
	
	simpleTimer getTimeLogObjAtIndex(int i)
	{
		return timeLogArray[i];
	}
	
	public static void main(String args[])
	{
		System.out.println(getNextDay("2021-04-30"));
//		if(isDateCurrentMonday(getCurrentDate()))
//		{
//			System.out.println("Date matches currentMonday");
//		}
//		else	
//		{
//			System.out.println("Date does not match currentMonday");
//		}
		//System.out.println(changeCurrentDayToMonday(1));
		Loader myL = getSingleLoaderInstance();
		
//		myL.initializeTimeLogArray();
//		
//		myL.printTimeLogArray();
//		
//		System.out.println(myL.getDayOfTheWeek(myL.getTimeLogObjAtIndex(5)));
		

		System.out.println("Checking if User file is Created if not Create It!");
		myL.createUserInfoFile("Matthew");
		myL.readUserInfoFile("Matthew");
		System.out.println("\n");
		
		myL.writeUserInfoFile("Matthew");
		System.out.println("Printing File Contents of User File");
		myL.printFileContents("Matthew");
		System.out.println("\n");
		
		
		System.out.println("Reading user contents and creating objects");
		myL.readUserInfoFile("Matthew");
		System.out.println("\n");
		
		myL.writeUserInfoFile("Matthew");
		
		myL.printTaskArray();
		System.out.println("\n");
		myL.printTimeLogArray();
		
		//System.out.println("\n\n\n\nDOING CREDENTIAL FILE STUFF\n");
		
//		myL.createCredentialsFile();
//		
//		myL.writeCredentialsFile("Matthew", "Z");
//		
//		myL.writeCredentialsFile("Matthew1", "Z1");
//		
//		myL.readCredentialsFile();
//		
//		myL.printCredentialsArray(myL.countRows(), 2);
	}
}
