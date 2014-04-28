package ch.theowinter.ToxicTodo.client.UI.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractListModel;

import ch.theowinter.ToxicTodo.client.ClientTodoManager;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoCategory;

public class CategoryListModel extends AbstractListModel<TodoCategory> implements Observer{
	private static final long serialVersionUID = -8918372750130357388L;
	
	ArrayList<TodoCategory> categoryList;
	ClientTodoManager todoManager; 
	
	public CategoryListModel(ClientTodoManager todoManager) {
		super();
		categoryList = todoManager.categoriesToArray();
		this.todoManager = todoManager;
		sort();
		todoManager.addObserver(this);
	}

	@Override
	public int getSize() {
		return categoryList.size();
	}

	/**
	 * Returns null if the index is out of bounds.
	 */
	@Override
	public TodoCategory getElementAt(int index) {
		TodoCategory category = null;
		if(index<=categoryList.size()){
			category =categoryList.get(index);
		}
		return category;
	}

	@Override
	public void update(Observable o, Object arg) {
		categoryList = todoManager.categoriesToArray();
		sort();
		fireContentsChanged(this, 0, categoryList.size()-1);
	}
	
	public void sort(){
		Collections.sort(categoryList);
	}
}
