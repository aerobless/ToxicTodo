package ch.theowinter.ToxicTodo.client.UI.Model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractListModel;

import ch.theowinter.ToxicTodo.client.ClientTodoManager;

public class TodoListModel  extends AbstractListModel<Object> implements Observer{
	private static final long serialVersionUID = -2197405934803803950L;
	ClientTodoManager todoManager;
	ArrayList<String> data = new ArrayList<String>();
	
	/**
	 * Creates a new instance of this class.
	 *
	 * @param aStorage
	 */
	public TodoListModel(ClientTodoManager aTodoManager) {
		super();
		todoManager = aTodoManager;
		data.add("test 1");
		data.add("test 2");
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return data.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Object getElementAt(int aIndex) {
		// TODO Auto-generated method stub
		return data.get(aIndex);
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable observedList, Object aArg) {
		
	}
}
