package ch.theowinter.ToxicTodo.client;

import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoList;

public class ClientTodoManager {
	
	//Class variables
	TodoList todoList;

	public ClientTodoManager(TodoList todoList) {
		super();
		this.todoList = todoList;
	}
	
	/**
	 * @return the todoList
	 */
	public TodoList getTodoList() {
		return todoList;
	}
	
}
