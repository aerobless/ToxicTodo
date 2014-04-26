package ch.theowinter.ToxicTodo.client.UI.Model;

import java.util.ArrayList;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoTask;

public class TaskListModel implements ListModel<TodoTask>{
	ArrayList<TodoTask> taskList;
	ArrayList<ListDataListener> listners = new ArrayList<ListDataListener>();
	
	public TaskListModel(ArrayList<TodoTask> taskList) {
		super();
		this.taskList = taskList;
	}

	@Override
	public int getSize() {
		return taskList.size();
	}

	@Override
	public TodoTask getElementAt(int index) {
		return taskList.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listners.remove(l);
	}
	
	private void notifyListeners() {
	for (ListDataListener l : listners)
	    {
	      l.contentsChanged(null);
	    } 
	  }
	
	public void changeCategory(ArrayList<TodoTask> aTasklist){
		taskList = aTasklist;
		notifyListeners();
	}
}
