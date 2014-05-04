package ch.theowinter.toxictodo.client.ui.model;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import ch.theowinter.toxictodo.client.ui.view.utilities.ToxicData;

public class TaskPriorityComboboxModel  extends AbstractListModel<String> implements ComboBoxModel<String>{
	private static final long serialVersionUID = -6554370407871608236L;
	private Object selectedItem;

	@Override
	public int getSize() {
		return ToxicData.priorityArray.size();
	}

	@Override
	public String getElementAt(int index) {
		return ToxicData.priorityArray.get(index).getPriorityText();
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
