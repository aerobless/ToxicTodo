package ch.theowinter.toxictodo.client.ui.model;

public class TaskPriority {
	int priorityID;
	String priorityText;
	char priorityIcon;
	
	/**
	 * Creates a new instance of this class.
	 *
	 * @param aPriorityID
	 * @param aPriorityText
	 * @param aPriorityIcon
	 */
	public TaskPriority(int aPriorityID, String aPriorityText,
			char aPriorityIcon) {
		super();
		priorityID = aPriorityID;
		priorityText = aPriorityText;
		priorityIcon = aPriorityIcon;
	}

	/**
	 * @return the priorityID
	 */
	public int getPriorityID() {
		return priorityID;
	}

	/**
	 * @return the priorityText
	 */
	public String getPriorityText() {
		return priorityText;
	}

	/**
	 * @return the priorityIcon
	 */
	public char getPriorityIcon() {
		return priorityIcon;
	}
}
