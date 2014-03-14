package ch.theowinter.ToxicTodo.utilities.primitives;

import java.io.Serializable;
import java.util.ArrayList;

public class ToxicDatagram implements Serializable{
	private static final long serialVersionUID = -373861488847346784L;
	ArrayList<TodoCategory> todoList;
	String serverControlMessage;
	String cypher;
	
	public ToxicDatagram(ArrayList<TodoCategory> serverTodo, String aServerControlMessage,
			String aCypher) {
		super();
		todoList = serverTodo;
		serverControlMessage = aServerControlMessage;
		cypher = aCypher;
	}

	/**
	 * @return the todoList
	 */
	public ArrayList<TodoCategory> getTodoList() {
		return todoList;
	}

	/**
	 * @return the serverControlMessage
	 */
	public String getServerControlMessage() {
		return serverControlMessage;
	}
	
}
