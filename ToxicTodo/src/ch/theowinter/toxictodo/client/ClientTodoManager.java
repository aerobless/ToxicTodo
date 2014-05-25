package ch.theowinter.toxictodo.client;

import java.awt.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Semaphore;

import javax.swing.JOptionPane;
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
		TodoList originalTodoList = ClientApplication.sendToServer(new ToxicDatagram("SEND_TODOLIST_TO_CLIENT"));
		TodoList advancedTodoList = generateAllTasksCategory(originalTodoList);
		try {
			advancedTodoList.addCategory(generateTodyCategory(originalTodoList));
		} catch (Exception e) {
			Logger.log("Unable to add daily-Task category to advancedTodoList", e);
		}
		setTodoList(advancedTodoList);
	}
	
	public TodoList updateHistoricList() throws IOException {
		return generateAllTasksCategory(ClientApplication.sendToServer(new ToxicDatagram("SEND_HISTORIC_TODOLIST_TO_CLIENT")));
	}
	
	public List<TodoCategory> categoriesToArray(){
		List<TodoCategory> returnArray = new ArrayList<TodoCategory>();
		for(String categoryKey : getTodoList().getCategoryMap().keySet()){
			returnArray.add(getTodoList().getCategoryMap().get(categoryKey));
		}
		return returnArray;
	}
	
	public void addNewTask(int priority, String categoryKeyword, String summary, String taskDescription,
			String hyperlink, boolean daily, boolean weekly, boolean monthly) throws IOException{
		
		String location;
		try {
			location = locationEngine.getCity();
		} catch (ParserConfigurationException | SAXException e) {
			Logger.log("Unable to get location.", e);
			location = "No Location";
		}
		//Setting additional properties
		TodoTask task = new TodoTask(priority, summary, location, new Date());
		task.setDescription(taskDescription);
		task.setHyperlink(hyperlink);
		task.setDaily(daily);
		task.setWeekly(weekly);
		task.setMonthly(monthly);
		
		String outputCategoryKeyword = categoryKeyword;
		if(categoryKeyword.equals(ToxicUIData.ALL_TASKS_TODOCATEGORY_KEY)){
			outputCategoryKeyword = "orphan";
		}
		ClientApplication.sendToServer(new ToxicDatagram("ADD_TASK_TO_LIST_ON_SERVER", task, outputCategoryKeyword));
		updateList();
	}
	
	public void removeTask(boolean writeToLog, TodoTask task, String categoryKeyword, Component frame) throws IOException{
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
		String outputCategoryKeyword = categoryKeyword;
		if(categoryKeyword.equals(ToxicUIData.ALL_TASKS_TODOCATEGORY_KEY)){
			outputCategoryKeyword = todoList.getCategoryKeywordForTask(finalizedTask);
		}
		
		//Handle repeateable tasks
		if(finalizedTask.isDaily() || finalizedTask.isWeekly() || finalizedTask.isMonthly()){
			if(writeToLog){
				dataMessage = "LOG_TASK_AS_COMPLETED_ON_SERVER";
				finalizedTask.setSummary(finalizedTask.getSummary()+" REPEATABLE:"+Math.random());
			}else{
				int dialogResult = JOptionPane.showConfirmDialog(frame, "Are you certain that you want to delete a"
						+ " repeatable task?","Confirm removal of a repeatable task",JOptionPane.YES_NO_OPTION);
				if(dialogResult==JOptionPane.NO_OPTION){
					categoryKeyword = null;
				}
			}
		}
		
		if(categoryKeyword != null){
			ClientApplication.sendToServer(new ToxicDatagram(dataMessage, finalizedTask , outputCategoryKeyword));
			updateList();
		}
	}
	
	public void addAndCompleteTask(int priority, String categoryKeyword, String summary) throws IOException{
		String location;
		try {
			location = locationEngine.getCity();
		} catch (ParserConfigurationException | SAXException e) {
			Logger.log("Unable to get location.", e);
			location = "No Location";
		}
		TodoTask task = new TodoTask(priority, summary, location, new Date());
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
			inputList.addCategory(new TodoCategory("All tasks", ToxicUIData.ALL_TASKS_TODOCATEGORY_KEY, '\uf135',true));
			for(String categoryKey : inputList.getCategoryMap().keySet()){
				List<TodoTask> currentCategoryTasks = inputList.getCategoryMap().get(categoryKey).getTaskInCategoryAsArrayList();
				for(TodoTask currentTask : currentCategoryTasks){
					inputList.addTask(ToxicUIData.ALL_TASKS_TODOCATEGORY_KEY, currentTask);
				}
			}
		} catch (Exception e) {
			Logger.log("error generating all-tasks category..", e);
		}
		return inputList;
	}
	
	/**
	 * Generate an additional category consisting of all tasks that are daily-repeatable
	 * and haven't been completed today.
	 * It returns the "TODAY_DAILY_TASK_KEY" category, that can be added to the todoList.
	 * 
	 * @param originalTodoList
	 * @return
	 */
	public TodoCategory generateTodyCategory(TodoList originalTodoList){
		Date today = new Date();
		TodoCategory todayTasks = new TodoCategory("Today's Tasks", ToxicUIData.TODAY_DAILY_TASK_KEY, '\uf073',true);
		try {
			for(String categoryKey : originalTodoList.getCategoryMap().keySet()){
				List<TodoTask> currentCategoryTasks = originalTodoList.getCategoryMap().get(categoryKey).getTaskInCategoryAsArrayList();
				for(TodoTask currentTask : currentCategoryTasks){
					if(currentTask.isDaily() && ((currentTask.getCompletionDate()==null) || !isSameDay(currentTask.getCompletionDate(), today))){
						todayTasks.add(currentTask);
					}
				}
			}
		} catch (Exception e) {
			Logger.log("error generating all-tasks category..", e);
		}
		return todayTasks;
	}
	
	/*
	 * http://stackoverflow.com/questions/2517709/comparing-two-dates-to-see-if-they-are-in-the-same-day
	 * Doesn't respect daylight saving or anything advanced but it's exact enough for our purpose.
	 */
	public static boolean isSameDay(Date date1, Date date2) {
	    // Strip out the time part of each date.
	    long julianDayNumber1 = date1.getTime() / 86400000; //Millies per day
	    long julianDayNumber2 = date2.getTime() / 86400000;
	    // If they now are equal then it is the same day.
	    return julianDayNumber1 == julianDayNumber2;
	}
}
