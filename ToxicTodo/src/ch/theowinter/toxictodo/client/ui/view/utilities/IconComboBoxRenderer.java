package ch.theowinter.toxictodo.client.ui.view.utilities;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import ch.theowinter.toxictodo.client.ui.model.FontString;

public class IconComboBoxRenderer extends IconTextElement implements ListCellRenderer<Object>{
	private static final long serialVersionUID = 419280285180713071L;

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value,
		int aIndex, boolean isSelected, boolean cellHasFocus) {
	        if (isSelected) {
	            setBackground(list.getSelectionBackground());
	            setForeground(list.getSelectionForeground());
	        } else {
	            setBackground(list.getBackground());
	            setForeground(list.getForeground());
	        }     
        setText(((FontString) value).getIcon(), ((FontString) value).getText(), 11, 11);
        return this;
	}
}
