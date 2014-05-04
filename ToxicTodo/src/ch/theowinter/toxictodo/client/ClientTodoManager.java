package ch.theowinter.toxictodo.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.concurrent.Semaphore;

import ch.theowinter.toxictodo.client.ui.view.utilities.ToxicData;
import ch.theowinter.toxictodo.sharedobjects.Logger;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoCategory;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoList;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoTask;
import ch.theowinter.toxictodo.sharedobjects.elements.ToxicDatagram;

public class ClientTodoManager extends Observable{
	//Class variables
	private TodoList todoList;
	
	//Locks
	private Semaphore writeLock = new Semaphore(1);
	
	//Runtime variables:
	private boolean initSuccess = false;

	public ClientTodoManager() {
		super();
		try {
			updateList();
			initSuccess = true;
		} catch (IOException anEx) {
			initSuccess = false;
		}
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
		} catch (InterruptedException e) {
			Logger.log("Interruped while trying to setTodoList()", e);
		}
	}
	
	public boolean getInitSuccess(){
		return initSuccess;
	}

	public void updateList() throws IOException{
		setTodoList(generateAllTasksCategory(ClientApplication.sendToServer(new ToxicDatagram("SEND_TODOLIST_TO_CLIENT"))));
	}
	
	public ArrayList<TodoCategory> categoriesToArray(){
		ArrayList<TodoCategory> returnArray = new ArrayList<TodoCategory>();
		for(String categoryKey : getTodoList().getCategoryMap().keySet()){
			returnArray.add(getTodoList().getCategoryMap().get(categoryKey));
		}
		return returnArray;
	}
	
	public void addNewTask(int priority, String categoryKeyword, String taskDescription) throws IOException{
		Date today = new Date();
		TodoTask task = new TodoTask(priority, false, taskDescription, "location: not implemented yet", today);
		ClientApplication.sendToServer(new ToxicDatagram("ADD_TASK_TO_LIST_ON_SERVER", task, categoryKeyword));
		updateList();
	}
	
	public void removeTask(boolean writeToLog, TodoTask task, String categoryKeyword) throws IOException{
		String dataMessage = "REMOVE_TASK_ON_SERVER";
		if(writeToLog){
			dataMessage = "REMOVE_AND_LOG_TASK_AS_COMPLETED_ON_SERVER";
		}
		ClientApplication.sendToServer(new ToxicDatagram(dataMessage, task , categoryKeyword));
		updateList();
	}
	
	public void addNewCategory(String description, String keyword, char icon, boolean systemCategory) throws IOException{
		TodoCategory newCategory = new TodoCategory(description, keyword, icon, systemCategory);
		ClientApplication.sendToServer(new ToxicDatagram("ADD_CATEGORY_TO_LIST_ON_SERVER",null, newCategory));
		updateList();
	}
	
	public void editCategory(String oldKeyword, String newKeyword, String name, char icon) throws IOException{
		TodoCategory newCategory = new TodoCategory(name, newKeyword, icon, false);
		ClientApplication.sendToServer(new ToxicDatagram("EDIT_CATEGORY_ON_SERVER", oldKeyword, newCategory));
		updateList();
	}
	
	public void removeCategory(TodoCategory category) throws IOException{
		ClientApplication.sendToServer(new ToxicDatagram("REMOVE_CATEGORY_ON_SERVER",null, category));
		updateList();
	}
	
	public TodoList generateAllTasksCategory(TodoList inputList){
		try {
			inputList.addCategory(new TodoCategory("All tasks", ToxicData.allTaskTodoCategoryKey, '\uf135',true));
			for(String categoryKey : inputList.getCategoryMap().keySet()){
				ArrayList<TodoTask> currentCategoryTasks = inputList.getCategoryMap().get(categoryKey).getTaskInCategoryAsArrayList();
				for(TodoTask currentTask : currentCategoryTasks){
					inputList.addTask(ToxicData.allTaskTodoCategoryKey, currentTask);
				}
			}
		} catch (Exception e) {
			Logger.log("error generating all-tasks category..", e);
		}
		return inputList;
	}
}
