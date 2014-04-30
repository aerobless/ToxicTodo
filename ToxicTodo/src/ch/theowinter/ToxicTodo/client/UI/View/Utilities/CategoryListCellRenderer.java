package ch.theowinter.ToxicTodo.client.UI.View.Utilities;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import ch.theowinter.ToxicTodo.SharedObjects.Elements.TodoCategory;

public class CategoryListCellRenderer extends CategoryElementPanel implements ListCellRenderer<Object>{

	private static final long serialVersionUID = -2984750801578999639L;

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		TodoCategory currentCategory = (TodoCategory)value;
		this.setText(currentCategory.getIcon(), currentCategory.getName());
		this.setCounter(currentCategory.size());
		Color background = ToxicColors.hardBlue;
            
        // check if this cell is selected
        if (isSelected) {
            background = ToxicColors.hardBlue;
            this.setBackgroundColor(background);
            this.setFontColor(ToxicColors.textWhite);

        // unselected, and not the DnD drop location
        } else {
            background = ToxicColors.softBlue;
            this.setBackgroundColor(background);
            this.setFontColor(ToxicColors.textBlack);
        };
        setBackground(background);

        return this;
	}
}
