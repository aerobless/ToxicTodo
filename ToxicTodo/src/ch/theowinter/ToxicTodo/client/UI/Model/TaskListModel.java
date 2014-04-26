package ch.theowinter.ToxicTodo.client.UI.Model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoTask;

public class TaskListModel implements Observer, ListModel<TodoTask>{
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

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	public void changeCategory(ArrayList<TodoTask> aTasklist){
		taskList = aTasklist;
		notifyListeners();
	}
}
