package ch.theowinter.ToxicTodo.client.UI.Model;

import java.util.Observable;
import java.util.Observer;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoTask;

public class TaskListModel implements Observer, ListModel<TodoTask>{

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TodoTask getElementAt(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
