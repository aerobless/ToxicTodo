package ch.theowinter.ToxicTodo.client.UI.View.utilities;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoCategory;

public class CategoryListCellRenderer extends JLabel implements ListCellRenderer<Object>{
	private static final long serialVersionUID = -2984750801578999639L;

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		TodoCategory currentCategory = (TodoCategory)value;
		setText(currentCategory.getName());

        Color background;
        Color foreground;
        
        
        
        // check if this cell represents the current DnD drop location
        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

            background = Color.BLUE;
            foreground = Color.WHITE;

        // check if this cell is selected
        } else if (isSelected) {
            background = Color.RED;
            foreground = Color.WHITE;

        // unselected, and not the DnD drop location
        } else {
            background = Color.WHITE;
            foreground = Color.BLACK;
        };

        setBackground(background);
        setForeground(foreground);

        return this;
	}

}
