package ch.theowinter.toxictodo.sharedobjects.elements;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
	private String completionLocation;
	private Date creationDate;
	private Date completionDate;
	private List<Date> repeatableHistory;

	/**
	 * Create a TodoTask with the simplified
	 * constructor. Requires only a text.
	 *
	 * @param aTaskText
	 */
	public TodoTask(String aTaskText) {
		super();
		text = aTaskText;
	}
	
	/**
	 * Create a a TodoTask with the full
	 * constructor. (If you only have a todo-text
	 * then use the simplified constructor.
	 *
	 * @param priority
	 * @param repeatable
	 * @param text
	 * @param creationLocation
	 * @param creationDate
	 */
	public TodoTask(int priority, boolean repeatable, String text,
			String creationLocation, Date creationDate) {
		super();
		this.priority = priority;
		this.repeatable = repeatable;
		this.text = text;
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
		return completionLocation;
	}

	public void setCompletionLocatioN(String completionLocatioN) {
		this.completionLocation = completionLocatioN;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public List<Date> getRepeatableHistory() {
		return repeatableHistory;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completedOn) {
		this.completionDate = completedOn;
	}
}
