package ch.theowinter.ToxicTodo.client.UI.View.utilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoCategory;

public class CategoryListCellRenderer extends CategoryElementPanel implements ListCellRenderer<Object>{

	private static final long serialVersionUID = -2984750801578999639L;

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		TodoCategory currentCategory = (TodoCategory)value;

		this.setText('\uf15b', currentCategory.getName());

		Color background = new Color(83, 145, 206);
            
        // check if this cell is selected
        if (isSelected) {
            background = new Color(83, 145, 206);
            this.setFontColor(Color.WHITE);

        // unselected, and not the DnD drop location
        } else {
            background = new Color(230, 234, 239);
            this.setFontColor(Color.BLACK);
        };
        setBackground(background);

        return this;
	}
}
