package ch.theowinter.ToxicTodo.client.UI.Model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataListener;

import ch.theowinter.ToxicTodo.client.ClientTodoManager;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoTask;

public class TaskListModel extends AbstractListModel<TodoTask> implements Observer{
	private static final long serialVersionUID = 5378790622340613400L;
	
	ArrayList<TodoTask> taskList;
	ArrayList<ListDataListener> listners = new ArrayList<ListDataListener>();
	ClientTodoManager todoManager;
	String currentCategory;
	
	public TaskListModel(String categoryKeyword, ClientTodoManager todoManager) {
		super();
		this.todoManager = todoManager;
		this.taskList = getTaskArray(categoryKeyword);
		todoManager.addObserver(this);
	}

	public void changeCategory(String categoryKeyword){
		taskList = getTaskArray(categoryKeyword);
		fireContentsChanged(this, 0, taskList.size()-1);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(currentCategory != null){
			taskList = getTaskArray(currentCategory);
			fireContentsChanged(this, 0, taskList.size()-1);
		}
	}

	@Override
	public int getSize() {
		return taskList.size();
	}

	/**
	 * Returns null if the index is out of bounds.
	 */
	@Override
	public TodoTask getElementAt(int index) {
		TodoTask task = null;
		if(index<=(taskList.size()-1)){
			task =taskList.get(index);
		}
		return task;
	}
	
	private ArrayList<TodoTask> getTaskArray(String catKey){
		currentCategory = catKey;
		return todoManager.getTodoList().getCategoryMap().get(catKey).getTaskInCategoryAsArrayList();
	}
}
