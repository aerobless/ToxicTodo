package ch.theowinter.ToxicTodo.utilities.primitives;

import java.io.Serializable;
import java.util.ArrayList;

public class ToxicDatagram implements Serializable{
	private static final long serialVersionUID = -373861488847346784L;
	
	//Must have:
	private String controlMessage;
	private String cypher;
	
	//Optional
	private ArrayList<TodoCategory> oldTodoList; //deprecated
	private TodoList todoList;
	private String additionalMessage;
	private TodoTask todoTask;
	private TodoCategory todoCategory;
	
	@Deprecated
	public ToxicDatagram(ArrayList<TodoCategory> serverTodo, String aServerControlMessage,
			String aCypher) {
		super();
		oldTodoList = serverTodo;
		controlMessage = aServerControlMessage;
		cypher = aCypher;
	}
	
	//new constructor for sending the entire list
	public ToxicDatagram(String serverControlMessage, String cypher,
			TodoList todoList) {
		super();
		this.controlMessage = serverControlMessage;
		this.cypher = cypher;
		this.todoList = todoList;
	}

	//constructor for sending a task
	public ToxicDatagram(String serverControlMessage, String cypher,
			TodoTask todoTask, String additionalMessage) {
		super();
		this.controlMessage = serverControlMessage;
		this.cypher = cypher;
		this.todoTask = todoTask;
		this.additionalMessage = additionalMessage;
	}
	
	//constructor for sending a category
	public ToxicDatagram(String serverControlMessage, String cypher,
			TodoCategory todoCategory) {
		super();
		this.controlMessage = serverControlMessage;
		this.cypher = cypher;
		this.todoCategory = todoCategory;
	}
	
	//constructor for only sending a control-message
	public ToxicDatagram(String serverControlMessage, String cypher) {
		super();
		this.controlMessage = serverControlMessage;
		this.cypher = cypher;
	}

	@Deprecated
	public ArrayList<TodoCategory> getOldTodoList() {
		return oldTodoList;
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

	public String getCypher() {
		return cypher;
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
