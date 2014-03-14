package ch.theowinter.ToxicTodo.utilities;

import java.util.Date;


public class TodoTask {
	
	//Basic info:
	double taskID;

	String taskText;
	
	//Optional data:
	Date completeUntil;
	
	//Completion info:
	Date completedOn;
	
	//TODO: add possibility for "daily quest" e.g. repeatable task
	

	public TodoTask(double aTaskID, String aTaskText) {
		super();
		taskID = aTaskID;
		taskText = aTaskText;
	}
}
