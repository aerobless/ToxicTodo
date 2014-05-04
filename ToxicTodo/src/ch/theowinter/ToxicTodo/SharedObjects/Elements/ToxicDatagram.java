package ch.theowinter.ToxicTodo.SharedObjects.Elements;

import java.io.Serializable;

public class ToxicDatagram implements Serializable{
	private static final long serialVersionUID = -373861488847346784L;
	
	//Must have:
	private String controlMessage;
	
	//Optional
	private TodoList todoList;
	private String additionalMessage;
	private TodoTask todoTask;
	private TodoCategory todoCategory;
	
	//new constructor for sending the entire list
	public ToxicDatagram(String serverControlMessage,
			TodoList todoList) {
		super();
		this.controlMessage = serverControlMessage;
		this.todoList = todoList;
	}

	//constructor for sending a task
	public ToxicDatagram(String serverControlMessage, 
			TodoTask todoTask, String additionalMessage) {
		super();
		this.controlMessage = serverControlMessage;
		this.todoTask = todoTask;
		this.additionalMessage = additionalMessage;
	}
	
	//constructor for sending a category
	public ToxicDatagram(String serverControlMessage, String additionalMessage,
			TodoCategory todoCategory) {
		super();
		this.additionalMessage = additionalMessage;
		this.controlMessage = serverControlMessage;
		this.todoCategory = todoCategory;
	}
	
	//constructor for only sending a control-message 
	public ToxicDatagram(String serverControlMessage) {
		super();
		this.controlMessage = serverControlMessage;
	}

	public String getServerControlMessage() {
		return controlMessage;
	}

	public TodoTask getTodoTask() {
		return todoTask;
	}

	public void setTodoTask(TodoTask todoTask) {
		this.todoTask = todoTask;
	}

	public TodoCategory getTodoCategory() {
		return todoCategory;
	}

	public void setTodoCategory(TodoCategory todoCategory) {
		this.todoCategory = todoCategory;
	}

	public void setTodoList(TodoList todoList) {
		this.todoList = todoList;
	}
	
	public TodoList getTodoList() {
		return todoList;
	}

	public String getAdditionalMessage() {
		return additionalMessage;
	}

	public void setAdditionalMessage(String additionalMessage) {
		this.additionalMessage = additionalMessage;
	}
	
	
}
