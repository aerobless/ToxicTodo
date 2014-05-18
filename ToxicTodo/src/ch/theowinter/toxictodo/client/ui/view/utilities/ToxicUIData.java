package ch.theowinter.toxictodo.client.ui.view.utilities;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ch.theowinter.toxictodo.client.ui.model.TaskPriority;
import ch.theowinter.toxictodo.sharedobjects.Logger;

public class ToxicUIData {
	public static final String ALL_TASKS_TODOCATEGORY_KEY = "allTaskTodoCategoryKeyDoNotUseDirectly";
	public static final List<TaskPriority> PRIORITY_ARRAY = generatePriorityArray();
	public static final Font AWESOME_FONT = getAwesomeFont();
	
	private ToxicUIData() {
		super();
	}

	private static List<TaskPriority> generatePriorityArray(){
		ArrayList<TaskPriority> priorityArray = new ArrayList<TaskPriority> ();
		priorityArray.add(new TaskPriority(0, "not important", '\uf0da'));
		priorityArray.add(new TaskPriority(0, "Keep in mind", '\uf123'));
		priorityArray.add(new TaskPriority(0, "Important!", '\uf005'));
		priorityArray.add(new TaskPriority(0, "Deadly important!", '\uf0e7'));
		return priorityArray;
	}
	
	private static Font getAwesomeFont(){
		Font ttfBase = null;
	    try {
			InputStream in = ToxicUIData.class.getResourceAsStream("/resources/fontawesome-webfont.ttf");
			ttfBase = Font.createFont(Font.TRUETYPE_FONT, in);
		} catch (FontFormatException e) {
			Logger.log("FontFormatException when trying to load the AWESOME font.", e);
		} catch (IOException e) {
			Logger.log("IOException when trying to load the AWESOME font.", e);
		}
	    return ttfBase;
	}
}
