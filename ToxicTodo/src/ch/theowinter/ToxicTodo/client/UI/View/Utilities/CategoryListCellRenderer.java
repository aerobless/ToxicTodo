package ch.theowinter.ToxicTodo.client.UI.View.Utilities;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoCategory;

public class CategoryListCellRenderer extends CategoryElementPanel implements ListCellRenderer<Object>{

	private static final long serialVersionUID = -2984750801578999639L;

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		TodoCategory currentCategory = (TodoCategory)value;
		this.setText('\uf15b', currentCategory.getName());
		this.setCounter(currentCategory.size());
		Color background = new Color(83, 145, 206);
            
        // check if this cell is selected
        if (isSelected) {
            background = new Color(83, 145, 206);
            this.setBackgroundColor(background);
            this.setFontColor(Color.WHITE);

        // unselected, and not the DnD drop location
        } else {
            background = new Color(230, 234, 239);
            this.setBackgroundColor(background);
            this.setFontColor(Color.BLACK);
        };
        setBackground(background);

        return this;
	}
}
