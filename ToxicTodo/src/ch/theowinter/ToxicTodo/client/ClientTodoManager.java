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
			notifyObservers();
		} catch (InterruptedException anEx) {
			System.out.println("Interruped while trying to setTodoList()");
			anEx.printStackTrace();
		}
	}
	
	public void updateList(){
		setTodoList(ClientApplication.sendToServer(new ToxicDatagram("SEND_TODOLIST_TO_CLIENT", "")));
	}
	
	public ArrayList<String> toArray(){
		boolean displayEmptyCategories = false; //TODO decide if we want that
		//Used for local bindings when we want to delete a task by entering 1
		ArrayList<String> internalCategoryBinding = new ArrayList<String>();
		ArrayList<TodoTask> internalTaskBinding = new ArrayList<TodoTask>();
		
		ArrayList<String> returnArray = new ArrayList<String>();
		
		int taskID = 0;
		for(String categoryKey : getTodoList().getCategoryMap().keySet()){
			//Only list category if it contains tasks or we want to display empty categories too.
			if(getTodoList().getCategoryMap().get(categoryKey).containsTasks() || displayEmptyCategories==true){
				returnArray.add("###-"+getTodoList().getCategoryMap().get(categoryKey).getName().toUpperCase()+"-###");
				//todoList.getCategoryMap().get(categoryKey).getTasksHashMap();
				for(String taskKey : getTodoList().getCategoryMap().get(categoryKey).getTasksHashMap().keySet()){
					++taskID;
					returnArray.add("  ["+taskID+"] "+getTodoList().getCategoryMap().get(categoryKey).getTasksHashMap().get(taskKey).getTaskText());
					//adding task to local bindings map
					internalCategoryBinding.add(categoryKey);
					internalTaskBinding.add(getTodoList().getCategoryMap().get(categoryKey).getTasksHashMap().get(taskKey));
				}
			}
		}
		//TODO: duplicate code
		//TODO: local bindings strategy
		//Update local bindings:
		//localCategoryBinding = internalCategoryBinding;
		//localTaskBinding = internalTaskBinding;
		return returnArray;
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
}
