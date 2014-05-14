package ch.theowinter.toxictodo.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Semaphore;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import ch.theowinter.toxictodo.client.ui.view.utilities.ToxicUIData;
import ch.theowinter.toxictodo.sharedobjects.LocationEngine;
import ch.theowinter.toxictodo.sharedobjects.Logger;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoCategory;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoList;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoTask;
import ch.theowinter.toxictodo.sharedobjects.elements.ToxicDatagram;

public class ClientTodoManager extends Observable{
	//Class variables
	private TodoList todoList;
	private LocationEngine locationEngine;
	
	//Locks
	private Semaphore writeLock = new Semaphore(1);
	
	//Runtime variables:
	private boolean initSuccess = false;

	public ClientTodoManager() {
		super();
		locationEngine = new LocationEngine();
		try {
			updateList();
			initSuccess = true;
		} catch (IOException e) {
			Logger.log("Error updating TodoList in ClientTodoManager.", e);
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
	
	public List<TodoCategory> categoriesToArray(){
		List<TodoCategory> returnArray = new ArrayList<TodoCategory>();
		for(String categoryKey : getTodoList().getCategoryMap().keySet()){
			returnArray.add(getTodoList().getCategoryMap().get(categoryKey));
		}
		return returnArray;
	}
	
	public void addNewTask(int priority, String categoryKeyword, String taskDescription) throws IOException{
		String location;
		try {
			location = locationEngine.getCity();
		} catch (ParserConfigurationException | SAXException e) {
			Logger.log("Unable to get location.", e);
			location = "No Location";
		}
		TodoTask task = new TodoTask(priority, false, taskDescription, location, new Date());
		if(categoryKeyword.equals(ToxicUIData.allTaskTodoCategoryKey)){
			categoryKeyword = "orphan";
		}
		ClientApplication.sendToServer(new ToxicDatagram("ADD_TASK_TO_LIST_ON_SERVER", task, categoryKeyword));
		updateList();
	}
	
	public void removeTask(boolean writeToLog, TodoTask task, String categoryKeyword) throws IOException{
		TodoTask finalizedTask = task;
		finalizedTask.setCompletionDate(new Date());
		String location;
		try {
			location = locationEngine.getCity();
		} catch (ParserConfigurationException | SAXException e) {
			Logger.log("Unable to get location.", e);
			location = "No Location";
		}
		finalizedTask.setCompletionLocatioN(location);
		String dataMessage = "REMOVE_TASK_ON_SERVER";
		if(writeToLog){
			dataMessage = "REMOVE_AND_LOG_TASK_AS_COMPLETED_ON_SERVER";
		}
		ClientApplication.sendToServer(new ToxicDatagram(dataMessage, finalizedTask , categoryKeyword));
		updateList();
	}
	
	public void addAndCompleteTask(int priority, String categoryKeyword, String taskDescription) throws IOException{
		String location;
		try {
			location = locationEngine.getCity();
		} catch (ParserConfigurationException | SAXException e) {
			Logger.log("Unable to get location.", e);
			location = "No Location";
		}
		TodoTask task = new TodoTask(priority, false, taskDescription, location, new Date());
		task.setCompletionDate(new Date());
		task.setCompletionLocatioN(location);
		
		ClientApplication.sendToServer(new ToxicDatagram("LOG_TASK_AS_COMPLETED_ON_SERVER", task, categoryKeyword));
		//TODO: update historicTodoList(?)
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
			inputList.addCategory(new TodoCategory("All tasks", ToxicUIData.allTaskTodoCategoryKey, '\uf135',true));
			for(String categoryKey : inputList.getCategoryMap().keySet()){
				List<TodoTask> currentCategoryTasks = inputList.getCategoryMap().get(categoryKey).getTaskInCategoryAsArrayList();
				for(TodoTask currentTask : currentCategoryTasks){
					inputList.addTask(ToxicUIData.allTaskTodoCategoryKey, currentTask);
				}
			}
		} catch (Exception e) {
			Logger.log("error generating all-tasks category..", e);
		}
		return inputList;
	}
}
