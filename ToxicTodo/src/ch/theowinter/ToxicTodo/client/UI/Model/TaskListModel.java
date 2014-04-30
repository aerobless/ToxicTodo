package ch.theowinter.ToxicTodo.client.UI.Model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataListener;

import ch.theowinter.ToxicTodo.SharedObjects.Elements.TodoTask;
import ch.theowinter.ToxicTodo.client.ClientTodoManager;

public class TaskListModel extends AbstractListModel<TodoTask> implements Observer{
	private static final long serialVersionUID = 5378790622340613400L;
	
	ArrayList<TodoTask> filteredTaskList;
	ArrayList<TodoTask> originalTaskList;
	ArrayList<ListDataListener> listners = new ArrayList<ListDataListener>();
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
		filteredTaskList = originalTaskList;
		fireContentsChanged(this, 0, filteredTaskList.size()-1);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(currentCategory != null){
			originalTaskList = getTaskArray(currentCategory);
			filteredTaskList = originalTaskList;
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
	
	private ArrayList<TodoTask> getTaskArray(String catKey){
		currentCategory = catKey;
		return todoManager.getTodoList().getCategoryMap().get(catKey).getTaskInCategoryAsArrayList();
	}
	
	public void filter(String input){
		ArrayList<TodoTask> workList = new ArrayList<TodoTask>();
		for(TodoTask aTask : originalTaskList){
			if(aTask.getText().toLowerCase().contains(input.toLowerCase())){
				workList.add(aTask);
			}
		}
		filteredTaskList = workList;
		fireContentsChanged(this, 0, filteredTaskList.size()-1);
	}
}
