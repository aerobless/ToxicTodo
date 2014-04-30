package ch.theowinter.ToxicTodo.SharedObjects.Elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class TodoTask implements Serializable{
	private static final long serialVersionUID = 8551171003242753417L;

	//Task Properties:
	private int priority;
	private char icon;
	private boolean repeatable;
	
	//Task Data:
	private String summary;
	private String text;
	private String hyperlink;
	
	//Task History:
	private String creationLocation;
	private String completionLocatioN;
	private Date creationDate;
	private Date completionDate;
	private ArrayList<Date> repeatableHistory;

	//Constructor:
	public TodoTask(String aTaskText) {
		super();
		text = aTaskText;
	}
	
	public TodoTask(int priority, boolean repeatable, String text,
			String creationLocation, Date creationDate) {
		super();
		this.priority = priority;
		this.repeatable = repeatable;
		this.text = text;
		this.creationLocation = creationLocation;
		this.creationDate = creationDate;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public char getIcon() {
		return icon;
	}

	public void setIcon(char icon) {
		this.icon = icon;
	}

	public boolean isRepeatable() {
		return repeatable;
	}

	public void setRepeatable(boolean repeatable) {
		this.repeatable = repeatable;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHyperlink() {
		return hyperlink;
	}

	public void setHyperlink(String hyperlink) {
		this.hyperlink = hyperlink;
	}

	public String getCreationLocation() {
		return creationLocation;
	}

	public void setCreationLocation(String creationLocation) {
		this.creationLocation = creationLocation;
	}

	public String getCompletionLocatioN() {
		return completionLocatioN;
	}

	public void setCompletionLocatioN(String completionLocatioN) {
		this.completionLocatioN = completionLocatioN;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public ArrayList<Date> getRepeatableHistory() {
		return repeatableHistory;
	}

	@Deprecated
	public String getTaskText() {
		return text;
	}

	@Deprecated
	public void setTaskText(String taskText) {
		this.text = taskText;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completedOn) {
		this.completionDate = completedOn;
	}
	
}
