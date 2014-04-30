package ch.theowinter.ToxicTodo.SharedObjects.Elements;

import java.io.Serializable;
import java.util.Date;

public class TodoTask implements Serializable{
	private static final long serialVersionUID = 8551171003242753417L;

	//Basic info:
	private String taskText;
	
	//Optional data:
	private Date completeUntil;
	
	//Completion info:
	private Date completedOn;
	
	//TODO: add possibility for "daily quest" e.g. repeatable task

	public TodoTask(String aTaskText) {
		super();
		taskText = aTaskText;
	}

	public String getTaskText() {
		return taskText;
	}

	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}

	public Date getCompleteUntil() {
		return completeUntil;
	}

	public void setCompleteUntil(Date completeUntil) {
		this.completeUntil = completeUntil;
	}

	public Date getCompletedOn() {
		return completedOn;
	}

	public void setCompletedOn(Date completedOn) {
		this.completedOn = completedOn;
	}
	
}
