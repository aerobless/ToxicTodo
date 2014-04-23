package ch.theowinter.ToxicTodo.client;

import java.util.Observable;
import java.util.concurrent.Semaphore;

import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoList;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.ToxicDatagram;

public class ClientTodoManager extends Observable{
	//Class variables
	private TodoList todoList;
	
	//Locks
	private Semaphore writeLock = new Semaphore(1);

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

	public void setTodoList(TodoList input){
		try {
			writeLock.acquire();
			todoList = input;
			writeLock.release();
			notifyObservers();
		} catch (InterruptedException anEx) {
			System.out.println("Interruped while trying to setTodoList()");
			anEx.printStackTrace();
		}
	}
	
	public void updateList(){
		setTodoList(ClientApplication.sendToServer(new ToxicDatagram("SEND_TODOLIST_TO_CLIENT", "")));
	}
}
