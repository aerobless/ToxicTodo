package ch.theowinter.ToxicTodo.client;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.Semaphore;

import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoCategory;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoList;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoTask;
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
			setChanged();
			notifyObservers();
		} catch (InterruptedException anEx) {
			System.out.println("Interruped while trying to setTodoList()");
			anEx.printStackTrace();
		}
	}
	
	public void updateList(){
		setTodoList(ClientApplication.sendToServer(new ToxicDatagram("SEND_TODOLIST_TO_CLIENT", "")));
	}
	
	//TODO: optimize, temporary for sidebar test
	public ArrayList<TodoCategory> categoriesToArray(){
		ArrayList<TodoCategory> returnArray = new ArrayList<TodoCategory>();
		for(String categoryKey : getTodoList().getCategoryMap().keySet()){
			//Only list category if it contains tasks or we want to display empty categories too.
			returnArray.add(getTodoList().getCategoryMap().get(categoryKey));
		}
		return returnArray;
	}
	
	public void addNewTask(String categoryKeyword, String taskDescription){
		TodoTask task = new TodoTask(taskDescription);
		ToxicDatagram datagram = new ToxicDatagram("ADD_TASK_TO_LIST_ON_SERVER", "", task, categoryKeyword);
		ClientApplication.sendToServer(datagram);
		updateList();
	}
	
	public void removeTask(boolean writeToLog, TodoTask task, String categoryKeyword){
		String dataMessage = "REMOVE_TASK_ON_SERVER";
		if(writeToLog){
			dataMessage = "REMOVE_AND_LOG_TASK_AS_COMPLETED_ON_SERVER";
		}
		ClientApplication.sendToServer(new ToxicDatagram(dataMessage, "",task , categoryKeyword));	
		updateList();
	}
}
