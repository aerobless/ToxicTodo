package ch.theowinter.ToxicTodo.client.UI.View.Utilities;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import ch.theowinter.ToxicTodo.client.UI.Model.FontString;

public class IconComboBoxRenderer extends TaskElementPanel implements ListCellRenderer<Object>{
	private static final long serialVersionUID = 419280285180713071L;

	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value,
			int aIndex, boolean isSelected, boolean cellHasFocus) {
	        //Get the selected index. (The index param isn't
	        //always valid, so just use the value.)
			//int selectedIndex = ((Integer)value).intValue();
	
	        if (isSelected) {
	            setBackground(list.getSelectionBackground());
	            setForeground(list.getSelectionForeground());
	        } else {
	            setBackground(list.getBackground());
	            setForeground(list.getForeground());
	        }
	
	        //Set the icon and text.  If icon was null, say so.
	        setText(((FontString) value).getIcon(), ((FontString) value).getText());
	        return this;
	}
}
