package ch.theowinter.ToxicTodo.utilities.primitives;

import java.io.Serializable;
import java.util.ArrayList;

public class ToxicDatagram implements Serializable{
	private static final long serialVersionUID = -373861488847346784L;
	
	//Must have:
	private String serverControlMessage;
	private String cypher;
	
	//Optional
	private ArrayList<TodoCategory> oldTodoList; //deprecated
	private TodoList todoList;
	private TodoTask todoTask;
	private TodoCategory todoCategory;
	
	@Deprecated
	public ToxicDatagram(ArrayList<TodoCategory> serverTodo, String aServerControlMessage,
			String aCypher) {
		super();
		oldTodoList = serverTodo;
		serverControlMessage = aServerControlMessage;
		cypher = aCypher;
	}
	
	//new constructor for sending the entire list
	public ToxicDatagram(String serverControlMessage, String cypher,
			TodoList todoList) {
		super();
		this.serverControlMessage = serverControlMessage;
		this.cypher = cypher;
		this.todoList = todoList;
	}

	//constructor for sending a task
	public ToxicDatagram(String serverControlMessage, String cypher,
			TodoTask todoTask) {
		super();
		this.serverControlMessage = serverControlMessage;
		this.cypher = cypher;
		this.todoTask = todoTask;
	}
	
	//constructor for sending a category
	public ToxicDatagram(String serverControlMessage, String cypher,
			TodoCategory todoCategory) {
		super();
		this.serverControlMessage = serverControlMessage;
		this.cypher = cypher;
		this.todoCategory = todoCategory;
	}

	@Deprecated
	public ArrayList<TodoCategory> getOldTodoList() {
		return oldTodoList;
	}

	public String getServerControlMessage() {
		return serverControlMessage;
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
}
