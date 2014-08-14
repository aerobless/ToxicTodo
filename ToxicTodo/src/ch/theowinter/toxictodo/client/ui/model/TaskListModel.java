package ch.theowinter.toxictodo.client.ui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataListener;

import ch.theowinter.toxictodo.client.ClientTodoManager;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoCategory;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoTask;

public class TaskListModel extends AbstractListModel<TodoTask> implements Observer{
	private static final long serialVersionUID = 5378790622340613400L;
	
	List<TodoTask> filteredTaskList;
	List<TodoTask> originalTaskList;
	List<ListDataListener> listners = new ArrayList<ListDataListener>();
	ClientTodoManager todoManager;
	String currentCategory;
	
	public TaskListModel(String categoryKeyword, ClientTodoManager todoManager) {
		super();
		this.todoManager = todoManager;
		this.originalTaskList = getTaskArray(categoryKeyword);
		this.filteredTaskList = originalTaskList;
		todoManager.addObserver(this);
	}

	public void changeCategory(String categoryKeyword){
		originalTaskList = getTaskArray(categoryKeyword);
		filteredTaskList = sort(originalTaskList);
		fireContentsChanged(this, 0, filteredTaskList.size()-1);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(currentCategory != null){
			originalTaskList = getTaskArray(currentCategory);
			filteredTaskList = sort(originalTaskList);
			fireContentsChanged(this, 0, filteredTaskList.size()-1);
		}
	}

	@Override
	public int getSize() {
		return filteredTaskList.size();
	}

	/**
	 * Returns null if the index is out of bounds.
	 */
	@Override
	public TodoTask getElementAt(int index) {
		TodoTask task = null;
		if(index<=(filteredTaskList.size()-1)){
			task =filteredTaskList.get(index);
		}
		return task;
	}
	
	private List<TodoTask> getTaskArray(String catKey){
		currentCategory = catKey;
		TodoCategory category = todoManager.getTodoList().getCategoryMap().get(catKey);
		if(category != null){
			return category.getTaskInCategoryAsArrayList();
		}else{
			return null;
		}
	}
	
	public void filter(String input){
		List<TodoTask> workList = new ArrayList<TodoTask>();
		for(TodoTask aTask : originalTaskList){
			if(aTask.getSummary().toLowerCase().contains(input.toLowerCase())){
				workList.add(aTask);
			}
		}
		filteredTaskList = sort(workList);
		fireContentsChanged(this, 0, filteredTaskList.size()-1);
	}
	
	/**
	 * Sort the TaskList alphabetically by summary.
	 *
	 * @param list
	 * @return
	 */
	public List<TodoTask> sort(List<TodoTask> list){
		Collections.sort(list,
				new Comparator<TodoTask>(){
					@Override
					public int compare(TodoTask task1, TodoTask task2) {
						return task1.getSummary().compareTo(task2.getSummary());
					}
				}
			);
		return list;
	}
}
