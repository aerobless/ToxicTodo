package ch.theowinter.ToxicTodo.utilities.primitives;

import java.io.Serializable;
import java.util.Date;


public class TodoTask implements Serializable{
	private static final long serialVersionUID = 8551171003242753417L;

	//Basic info:
	String taskText;
	
	//Optional data:
	Date completeUntil;
	
	//Completion info:
	Date completedOn;
	
	//TODO: add possibility for "daily quest" e.g. repeatable task
	

	public TodoTask(String aTaskText) {
		super();
		taskText = aTaskText;
	}
}
