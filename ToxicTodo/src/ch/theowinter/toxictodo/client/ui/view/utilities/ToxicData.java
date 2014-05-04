package ch.theowinter.toxictodo.client.ui.view.utilities;

import java.util.ArrayList;

import ch.theowinter.toxictodo.client.ui.model.TaskPriority;

public class ToxicData {
	final public static String allTaskTodoCategoryKey = "allTaskTodoCategoryKeyDoNotUseDirectly";
	final public static ArrayList<TaskPriority> priorityArray = generatePriorityArray();
	
	private static ArrayList<TaskPriority> generatePriorityArray(){
		ArrayList<TaskPriority> priorityArray = new ArrayList<TaskPriority> ();
		priorityArray.add(new TaskPriority(0, "not important", '\uf0da'));
		priorityArray.add(new TaskPriority(0, "Keep in mind", '\uf123'));
		priorityArray.add(new TaskPriority(0, "Important!", '\uf005'));
		priorityArray.add(new TaskPriority(0, "Deadly important!", '\uf0e7'));
		return priorityArray;
	}
}
