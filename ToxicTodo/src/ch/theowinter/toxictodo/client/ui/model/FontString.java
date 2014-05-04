package ch.theowinter.toxictodo.client.ui.model;

public class FontString {
	String text;
	char icon;
	/**
	 * Creates a new instance of this class.
	 *
	 * @param aText
	 * @param aIcon
	 */
	public FontString(String aText, char aIcon) {
		super();
		text = aText;
		icon = aIcon;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param aText the text to set
	 */
	public void setText(String aText) {
		text = aText;
	}
	/**
	 * @return the icon
	 */
	public char getIcon() {
		return icon;
	}
	/**
	 * @param aIcon the icon to set
	 */
	public void setIcon(char aIcon) {
		icon = aIcon;
	}

	
}
