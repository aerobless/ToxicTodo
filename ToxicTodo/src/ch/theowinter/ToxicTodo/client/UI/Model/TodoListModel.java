package ch.theowinter.ToxicTodo.client.UI.Model;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

public class TodoListModel  extends AbstractListModel<Object> {
	private static final long serialVersionUID = -2197405934803803950L;
	ArrayList<String> storage = new ArrayList<String>();
	
	/**
	 * Creates a new instance of this class.
	 *
	 * @param aStorage
	 */
	public TodoListModel() {
		super();
		storage.add("test 1");
		storage.add("test 2");
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return storage.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Object getElementAt(int aIndex) {
		// TODO Auto-generated method stub
		return storage.get(aIndex);
	}



}
