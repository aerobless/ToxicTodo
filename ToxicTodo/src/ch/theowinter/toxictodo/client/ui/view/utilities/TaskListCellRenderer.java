package ch.theowinter.toxictodo.client.ui.view.utilities;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import ch.theowinter.toxictodo.sharedobjects.elements.TodoTask;

public class TaskListCellRenderer extends IconTextElement implements ListCellRenderer<Object>{
	private static final long serialVersionUID = 675589706298418336L;

	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list,
			Object value, int index, boolean isSelected, boolean cellHasFocus) {
		TodoTask currentTask = (TodoTask)value;

		this.setText(ToxicUIData.PRIORITY_ARRAY.get(currentTask.getPriority()).getPriorityIcon(), currentTask.getSummary());

		Color background = ToxicColors.SOFT_GREY;
            
        // check if this cell is selected
        if (isSelected) {
            background = ToxicColors.HARD_BLUE;
            this.setFontColor(Color.WHITE);

        // unselected, and not the DnD drop location
        } else {
            background = ToxicColors.SOFT_GREY;
            this.setFontColor(Color.BLACK);
        }
        setBackground(background);
		return this;
	}
}
