package ch.theowinter.ToxicTodo.client.UI.Model;

import java.util.ArrayList;
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
		todoManager.addObserver(this);
	}

	@Override
	public int getSize() {
		return categoryList.size();
	}

	@Override
	public TodoCategory getElementAt(int aIndex) {
		return categoryList.get(aIndex);
	}

	@Override
	public void update(Observable o, Object arg) {
		categoryList = todoManager.categoriesToArray();
		fireContentsChanged(this, 0, categoryList.size()-1);
	}
}
