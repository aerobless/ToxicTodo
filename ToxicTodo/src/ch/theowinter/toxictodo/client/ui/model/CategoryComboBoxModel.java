package ch.theowinter.toxictodo.client.ui.model;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import ch.theowinter.toxictodo.client.ClientTodoManager;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoCategory;

public class CategoryComboBoxModel extends AbstractListModel<String> implements ComboBoxModel<String>, Observer{
	private static final long serialVersionUID = -2267676861577121046L;
	private Object selectedItem;
	ClientTodoManager todoManager;
	List<TodoCategory> categoryList;
	
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
	public void setSelectedItem(Object anItem) {
		 selectedItem = anItem;
	}

	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}

	@Override
	public void update(Observable o, Object arg) {
		categoryList = todoManager.categoriesToArray();	
		fireContentsChanged(this, 0, categoryList.size()-1);
	}
}
