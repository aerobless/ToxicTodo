package ch.theowinter.toxictodo.client.ui.view.utilities;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import ch.theowinter.toxictodo.sharedobjects.elements.TodoTask;

public class TaskListCellRenderer extends IconTextElement implements ListCellRenderer<Object>{
	private static final long serialVersionUID = 675589706298418336L;

	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list,
			Object value, int index, boolean isSelected, boolean cellHasFocus) {
		TodoTask currentTask = (TodoTask)value;

		String taskDescription = currentTask.getSummary();
		if(currentTask.isDaily()){
			taskDescription = "<b>[DAILY]</b> "+taskDescription;
		}
		if(currentTask.isWeekly()){
			taskDescription = "<b>[WEEKLY]</b> "+taskDescription;
		}
		if(currentTask.isMonthly()){
			taskDescription = "<b>[MONTHLY]</b> "+taskDescription;
		}
		
		//Yellow-Highlight of deadly important tasks
		if(currentTask.getPriority()==3){
			taskDescription = "<p style='background-color:yellow;'><font color='black'>"+taskDescription;
		}
		this.setText(ToxicUIData.PRIORITY_ARRAY.get(currentTask.getPriority()).getPriorityIcon(), taskDescription);

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
        initTooltip(currentTask);
        setBackground(background);
		return this;
	}
	
	private void initTooltip(TodoTask currentTask){
		String isRepeatableText = "no";
		if(currentTask.isDaily() || currentTask.isMonthly() || currentTask.isMonthly()){
			isRepeatableText = "yes<br> <b>Completion Count:</b> "+currentTask.getCompletionCount()+"<br>"
					+ "<b>Last completion:</b> "+(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(currentTask.getCompletionDate()));
		}
		
        setToolTipText("<html><b>Task: </b>"+currentTask.getSummary()+"<br>"
        		+ "<b>Priority: </b>"+ToxicUIData.PRIORITY_ARRAY.get(currentTask.getPriority()).getPriorityText()+"<br>"
        		+ "<b>Hyperlink: </b>"+currentTask.getHyperlink()+"<br>"
        		+ "<b>Repeatable: </b>"+isRepeatableText+"<br>"
        		+ "<b>Created in: </b>"+currentTask.getCreationLocation()+"</html>");
	}
}
