package ch.theowinter.ToxicTodo.client.UI.View.Utilities;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoTask;

public class TaskListCellRenderer extends TaskElementPanel implements ListCellRenderer<Object>{
	private static final long serialVersionUID = 675589706298418336L;

	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list,
			Object value, int index, boolean isSelected, boolean cellHasFocus) {
		TodoTask currentTask = (TodoTask)value;

		this.setText('\uf15b', currentTask.getTaskText());

		Color background = new Color(237, 237, 237);
            
        // check if this cell is selected
        if (isSelected) {
            background = new Color(83, 145, 206);
            this.setFontColor(Color.WHITE);

        // unselected, and not the DnD drop location
        } else {
            background = new Color(237, 237, 237);
            this.setFontColor(Color.BLACK);
        };
        setBackground(background);

		return this;
	}
}
