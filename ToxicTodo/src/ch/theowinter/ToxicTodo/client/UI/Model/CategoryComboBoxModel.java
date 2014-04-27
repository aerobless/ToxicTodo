package ch.theowinter.ToxicTodo.client.UI.Model;

import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import ch.theowinter.ToxicTodo.client.ClientTodoManager;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoCategory;

public class CategoryComboBoxModel implements ComboBoxModel<String>{
	private Object selectedItem;
	ClientTodoManager todoManager;
	ArrayList<TodoCategory> categoryList;
	
	public CategoryComboBoxModel(ClientTodoManager todoManager) {
		this.todoManager = todoManager;
		categoryList = todoManager.categoriesToArray();
	}

	@Override
	public int getSize() {
		return categoryList.size();
	}

	@Override
	public String getElementAt(int index) {
		return categoryList.get(index).getName();
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
	public void setSelectedItem(Object anItem) {
		 selectedItem = anItem;
	}

	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}

}
