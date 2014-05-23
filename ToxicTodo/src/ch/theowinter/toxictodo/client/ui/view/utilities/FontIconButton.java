package ch.theowinter.toxictodo.client.ui.view.utilities;

import java.awt.Font;

import javax.swing.JButton;

public class FontIconButton extends JButton{
	private static final long serialVersionUID = -2573050760508438145L;
	/**
	 * A button with an icon from the "fontawesome"-font. You must specify a tooltip
	 * because a button with just an icon and no further instructions is bad design.
	 * 
	 * ICON SOURCE: http://fortawesome.github.io/Font-Awesome/cheatsheet/
	 * 
	 * @param awesomeIcon
	 * @param tooltip
	 */
	public FontIconButton(char awesomeIcon, String tooltip) {
		initFontIconButton(awesomeIcon, tooltip, 14);
	}
	
	public FontIconButton(char awesomeIcon, String tooltip, int fontSize) {
		initFontIconButton(awesomeIcon, tooltip, fontSize);
	}
	
	private void initFontIconButton(char awesomeIcon, String tooltip, int fontSize){
		Font ttfReal = ToxicUIData.AWESOME_FONT.deriveFont(Font.BOLD, fontSize);
		setFont(ttfReal);
		setText(String.valueOf(awesomeIcon));
		setForeground(ToxicColors.ICON_GREY);
		setToolTipText(tooltip);
		setFocusPainted(false);
		setContentAreaFilled(false);
		setBorderPainted(true);
	}
}
