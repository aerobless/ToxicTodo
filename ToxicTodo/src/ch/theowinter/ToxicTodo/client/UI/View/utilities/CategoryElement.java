package ch.theowinter.ToxicTodo.client.UI.View.utilities;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class CategoryElement extends JPanel {
	private static final long serialVersionUID = -2314006883111515559L;
	JLabel lblIcon;
	JLabel lblCategory;

	/**
	 * Create the panel.
	 */
	public CategoryElement() {
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		
		
		lblIcon = new JLabel();
		add(lblIcon);
		
		lblCategory = new JLabel();
		add(lblCategory);

	}
	
	public void setText(char awesomeIcon, String category){
		Font ttfReal = null;
		try {
	        InputStream in = this.getClass().getResourceAsStream("/resources/fontawesome-webfont.ttf");
			Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, in);
		    ttfReal = ttfBase.deriveFont(Font.BOLD, 40);
		    //setText(String.valueOf('\uf15b'));
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lblIcon.setFont(ttfReal);
		lblIcon.setText(String.valueOf(awesomeIcon));
		
		lblCategory.setText(category);
	}
}
