package ch.theowinter.ToxicTodo.client.UI.Model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import ch.theowinter.ToxicTodo.client.ClientTodoManager;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoCategory;

public class CategoryListModel implements Observer, ListModel<TodoCategory>{
	ArrayList<TodoCategory> categoryList;
	
	public CategoryListModel(ClientTodoManager aTodoManager) {
		super();
		categoryList = aTodoManager.categoriesToArray();
	}

	@Override
	public int getSize() {
		return categoryList.size();
	}

	@Override
	public TodoCategory getElementAt(int aIndex) {
		return categoryList.get(aIndex);
	}

	//TODO: for update
	@Override
	public void update(Observable observedList, Object aArg) {
		
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}
}
