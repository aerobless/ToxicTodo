package ch.theowinter.ToxicTodo.utilities;

import java.io.Serializable;

public class ToxicDatagram implements Serializable{
	private static final long serialVersionUID = -373861488847346784L;
	TodoList todoList;
	String serverControlMessage;
	String cypher;
	
	public ToxicDatagram(TodoList aTodoList, String aServerControlMessage,
			String aCypher) {
		super();
		todoList = aTodoList;
		serverControlMessage = aServerControlMessage;
		cypher = aCypher;
	}

	/**
	 * @return the todoList
	 */
	public TodoList getTodoList() {
		return todoList;
	}

	/**
	 * @return the serverControlMessage
	 */
	public String getServerControlMessage() {
		return serverControlMessage;
	}
	
}
