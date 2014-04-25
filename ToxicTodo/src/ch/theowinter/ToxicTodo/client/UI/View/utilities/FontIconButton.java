package ch.theowinter.ToxicTodo.client.UI.View.utilities;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;

public class FontIconButton extends JButton{
	private static final long serialVersionUID = -2573050760508438145L;
	public FontIconButton(char awesomeIcon) {
		
	 try {
		  //http://stackoverflow.com/questions/21050003/jbutton-text-having-different-custom-fonts
		      InputStream in = this.getClass().getResourceAsStream("/resources/fontawesome-webfont.ttf");
		      Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, in);
		      Font ttfReal = ttfBase.deriveFont(Font.BOLD, 24);
		      setFont(ttfReal);
		      setText(String.valueOf(awesomeIcon));
		    } catch (FontFormatException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		setFocusPainted(false);
		setContentAreaFilled(false);
		setBorderPainted(true);
	  }
}
