package ch.theowinter.ToxicTodo;

import ch.theowinter.ToxicTodo.utilities.TodoList;

public class ClientToxicTodo {

	public static void main(String[] args) {
		
		//GET TODO-LIST from server
		
		TodoList todo = new TodoList();
		todo.run(args);
		
		//return new TODO-LIST to server
	}
}
