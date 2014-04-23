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
	
	//TODO: possible multi-threading issue, we need to make sure we only write in one thread
	//CLI version is single-threaded but we may use multiple threads in the UI version..
	public void setTodoList(TodoList input){
		todoList = input;
	}
}
