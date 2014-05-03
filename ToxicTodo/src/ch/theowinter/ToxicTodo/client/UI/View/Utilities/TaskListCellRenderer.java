package ch.theowinter.ToxicTodo.client.UI.View.Utilities;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import ch.theowinter.ToxicTodo.SharedObjects.Elements.TodoTask;

public class TaskListCellRenderer extends IconTextElement implements ListCellRenderer<Object>{
	private static final long serialVersionUID = 675589706298418336L;

	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list,
			Object value, int index, boolean isSelected, boolean cellHasFocus) {
		TodoTask currentTask = (TodoTask)value;

		this.setText(ToxicData.priorityArray.get(currentTask.getPriority()).getPriorityIcon(), currentTask.getText());

		Color background = ToxicColors.softGrey;
            
        // check if this cell is selected
        if (isSelected) {
            background = ToxicColors.hardBlue;
            this.setFontColor(Color.WHITE);

        // unselected, and not the DnD drop location
        } else {
            background = ToxicColors.softGrey;
            this.setFontColor(Color.BLACK);
        };
        setBackground(background);

		return this;
	}
}
