package ch.theowinter.toxictodo.sharedobjects.elements;

import java.io.Serializable;
import java.util.Date;

public class TodoTask implements Serializable{
	private static final long serialVersionUID = 8551171003242753417L;

	//Task Properties:
	private int priority;
	private char icon;
	private boolean daily;
	private boolean weekly;
	private boolean monthly;
	
	//Task Data:
	private String summary;
	private String description;
	private String hyperlink;
	
	//Task History:
	private String creationLocation;
	private String completionLocation;
	private Date creationDate;
	private Date completionDate;
	private int completionCount;

	/**
	 * Create a TodoTask with the simplified
	 * constructor. Requires only a description.
	 *
	 * @param aTaskText
	 */
	public TodoTask(String summary) {
		super();
		this.summary = summary;
	}
	
	/**
	 * Create a a TodoTask with the full
	 * constructor. (If you only have a todo-description
	 * then use the simplified constructor.
	 *
	 * @param priority
	 * @param description
	 * @param creationLocation
	 * @param creationDate
	 */
	public TodoTask(int priority, String summary,
			String creationLocation, Date creationDate) {
		super();
		this.priority = priority;
		this.summary = summary;
		this.creationLocation = creationLocation;
		this.creationDate = creationDate;
	}

	/**
	 * Priority is interpreted on the client-side
	 * only. The server doesn't care about the int.
	 * 
	 * See ToxicData for a translation from int to string.
	 *
	 * @return priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Priority is interpreted on the client-side
	 * only. The server doesn't care about the int.
	 *
	 * @param priority
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public char getIcon() {
		return icon;
	}

	public void setIcon(char icon) {
		this.icon = icon;
	}

	public boolean isDaily() {
		return daily;
	}

	public void setDaily(boolean daily) {
		this.daily = daily;
	}

	public boolean isWeekly() {
		return weekly;
	}

	public void setWeekly(boolean weekly) {
		this.weekly = weekly;
	}

	public boolean isMonthly() {
		return monthly;
	}

	public void setMonthly(boolean monthly) {
		this.monthly = monthly;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return completionLocation;
	}

	public void setCompletionLocation(String completionLocatioN) {
		this.completionLocation = completionLocatioN;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completedOn) {
		this.completionDate = completedOn;
	}

	public int getCompletionCount() {
		return completionCount;
	}

	public void incrementCompletionCount() {
		completionCount +=1;
	}
}
