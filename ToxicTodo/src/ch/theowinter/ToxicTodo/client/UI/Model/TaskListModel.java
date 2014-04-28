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
		filteredTaskList = getTaskArray(categoryKeyword);
		fireContentsChanged(this, 0, filteredTaskList.size()-1);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(currentCategory != null){
			filteredTaskList = getTaskArray(currentCategory);
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
	
	//BETA WORKING ON
	public void filter(String input){
		ArrayList<TodoTask> workList = originalTaskList;
		/*for(TodoTask aTask : originalTaskList){
			if(!aTask.getTaskText().toLowerCase().contains(input.toLowerCase())){
				workList.remove(aTask);
			}
		}*/
		//filteredTaskList = workList;
		//fireContentsChanged(this, 0, filteredTaskList.size()-1);
	}
}
