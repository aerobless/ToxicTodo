package ch.theowinter.toxictodo.client.ui.view.utilities;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import ch.theowinter.toxictodo.sharedobjects.elements.TodoCategory;

public class CategoryListCellRenderer extends CategoryElementPanel implements ListCellRenderer<Object>{
	public CategoryListCellRenderer() {
	}

	private static final long serialVersionUID = -2984750801578999639L;

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		TodoCategory currentCategory = (TodoCategory)value;
		this.setText(currentCategory.getIcon(), currentCategory.getName());
		this.setCounter(currentCategory.size());
		Color background = ToxicColors.HARD_BLUE;
            
        // check if this cell is selected
        if (isSelected) {
        	background = ToxicColors.HARD_BLUE;
            this.setBackgroundColor(background);
            this.setFontColor(ToxicColors.TEXT_WHITE);

        // unselected, and not the DnD drop location
        } else {
            background = ToxicColors.SOFT_BLUE;
            this.setBackgroundColor(background);
            this.setFontColor(ToxicColors.TEXT_BLACK);
        }
        setBackground(background);
        return this;
	}
}
