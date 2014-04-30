package ch.theowinter.ToxicTodo.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.Semaphore;

import ch.theowinter.ToxicTodo.client.UI.View.Utilities.ToxicStrings;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoCategory;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoList;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoTask;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.ToxicDatagram;

public class ClientTodoManager extends Observable{
	//Class variables
	private TodoList todoList;
	
	//Locks
	private Semaphore writeLock = new Semaphore(1);
	
	//Runtime variables:
	private boolean initSuccess = false;

	public ClientTodoManager() {
		super();
		initSuccess = updateList();
	}
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
	
	public boolean getInitSuccess(){
		return initSuccess;
	}
	
	//ATM only used in GUI
	public boolean updateList(){
		boolean success = false;
		try {
			setTodoList(generateAllTasksCategory(ClientApplication.sendToServer(new ToxicDatagram("SEND_TODOLIST_TO_CLIENT", ""))));
			success = true;
		} catch (IOException anEx) {
			System.out.println("Unable to conntect to server");
		}
		return success;
	}
	
	public ArrayList<TodoCategory> categoriesToArray(){
		ArrayList<TodoCategory> returnArray = new ArrayList<TodoCategory>();
		for(String categoryKey : getTodoList().getCategoryMap().keySet()){
			returnArray.add(getTodoList().getCategoryMap().get(categoryKey));
		}
		return returnArray;
	}
	
	public boolean addNewTask(String categoryKeyword, String taskDescription){
		boolean success = false;
		TodoTask task = new TodoTask(taskDescription);
		try {
			ClientApplication.sendToServer(new ToxicDatagram("ADD_TASK_TO_LIST_ON_SERVER", "", task, categoryKeyword));
			success = updateList();
		} catch (IOException anEx) {
			anEx.printStackTrace();
		}
		return success;
	}
	
	public boolean removeTask(boolean writeToLog, TodoTask task, String categoryKeyword){
		boolean success = false;
		String dataMessage = "REMOVE_TASK_ON_SERVER";
		if(writeToLog){
			dataMessage = "REMOVE_AND_LOG_TASK_AS_COMPLETED_ON_SERVER";
		}
		try {
			ClientApplication.sendToServer(new ToxicDatagram(dataMessage, "",task , categoryKeyword));
			success = updateList();
		} catch (IOException anEx) {
			anEx.printStackTrace();
		}	
		return success;
	}
	
	public boolean addNewCategory(String description, String keyword){
		boolean success = false;
		TodoCategory newCategory = new TodoCategory(description, keyword);
		try {
			ClientApplication.sendToServer(new ToxicDatagram("ADD_CATEGORY_TO_LIST_ON_SERVER", "",newCategory));
			success = updateList();
		} catch (IOException anEx) {
			// TODO Auto-generated catch block
			anEx.printStackTrace();
		}
		return success;
	}
	
	public TodoList generateAllTasksCategory(TodoList inputList){
		try {
			inputList.addCategory("All tasks", ToxicStrings.allTaskTodoCategoryKey);
			for(String categoryKey : inputList.getCategoryMap().keySet()){
				ArrayList<TodoTask> currentCategoryTasks = inputList.getCategoryMap().get(categoryKey).getTaskInCategoryAsArrayList();
				for(TodoTask currentTask : currentCategoryTasks){
					inputList.addTask(ToxicStrings.allTaskTodoCategoryKey, currentTask);
				}
			}
		} catch (Exception anEx) {
			//TODO: global error handler
			System.out.println("error generating all-tasks category..");
		}
		return inputList;
	}
}
