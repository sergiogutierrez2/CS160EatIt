package application;

public class Task 
{
	// Declarations
	private String taskName; // Name of the task
	private String course; // Course that the task is attached to
	private String dueDate; // Date the task needs to be done by
	//private int priority; // The priority of the task
	private boolean completed; // Whether the task has been completed or not
	
	public Task(String taskName, String dueDate, String course)
	{
		this.taskName = taskName;
		this.dueDate = dueDate;
		this.course = course;
	}
	
	// Returns task name
	public String getTaskName()
	{
		return taskName;
	}
	
	// Returns due date
	public String getDueDate()
	{
		return dueDate;
	}
	
	// Returns course name
	public String getCourse()
	{
		return course;
	}
	
	// Sets complete boolean to true
	public void markAsComplete()
	{
		completed = true;
	}	
	public void markAsIncomplete()
	{
		completed = false;
	}	
	public boolean getCompletedStatus() 
	{
		return completed;
	}
	
	public boolean getCompleted()
    {
        //System.out.println("Getting completed value of: " + getTaskName() + " " + completed);
        return completed;
    }
    
    public void setCompleted(boolean complete)
    {
        completed = complete;
        //System.out.println("Setting completed value of: " + getTaskName() + " " + completed);
    }
    
    public void print()
    {
        System.out.println("Task name: " + getTaskName() + " Completed: " + getCompleted());
    } 
	
	public boolean isSameTask(Task thatTask) 
	{
		if(this.taskName.equals(thatTask.taskName)
			&& this.dueDate.equals(thatTask.dueDate)
			&& this.course.equals(thatTask.course)
			&& (this.completed == thatTask.completed))
		{
			return true;
		}
		
		return false;
	}
	
	public boolean hasSameNameCourseDueDate(Task thatTask) 
	{
		if(this.taskName.equals(thatTask.taskName)
			&& this.dueDate.equals(thatTask.dueDate)
			&& this.course.equals(thatTask.course) )
		{
			return true;
		}
		
		return false;
	}
	
	public void printTask()
	{
		
		String trueOrFalse;
		if(getCompletedStatus())
		{
			trueOrFalse = "True";
		}
		else
		{
			trueOrFalse = "False";
		}
		System.out.println("Task: " + this.taskName +
							", " + this.dueDate + 
							", " + this.course +
							", " + trueOrFalse);
	}
	
	public void setCompletedStatus(boolean status)
	{
		completed = true;
	}
	
	public String getTaskString()
	{
		String trueOrFalse;
		if(getCompletedStatus())
		{
			trueOrFalse = "True";
		}
		else
		{
			trueOrFalse = "False";
		}
		return (this.taskName + " | " 
					+ this.dueDate + " | " 
						+ this.course  + " | "
							+ trueOrFalse + " |\n");
	}
	
}
