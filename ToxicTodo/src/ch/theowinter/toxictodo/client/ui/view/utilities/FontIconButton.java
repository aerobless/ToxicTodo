package ch.theowinter.toxictodo.client.ui.view.utilities;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

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
		try {
			InputStream in = this.getClass().getResourceAsStream("/resources/fontawesome-webfont.ttf");
		    Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, in);
		    Font ttfReal = ttfBase.deriveFont(Font.BOLD, 14);
		    setFont(ttfReal);
		    setText(String.valueOf(awesomeIcon));
		    setForeground(ToxicColors.iconGrey);
		    } catch (FontFormatException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	 setToolTipText(tooltip);
	 setFocusPainted(false);
	 setContentAreaFilled(false);
	 setBorderPainted(true);
	}
}
