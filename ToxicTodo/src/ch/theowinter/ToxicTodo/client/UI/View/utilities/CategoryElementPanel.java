package ch.theowinter.ToxicTodo.client.UI.View.utilities;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class CategoryElementPanel extends JPanel {
	private static final long serialVersionUID = -2314006883111515559L;
	JLabel lblIcon;
	JLabel lblCategory;

	/**
	 * CategoryElementPanel is used in categoryList's cell-renderer.
	 */
	public CategoryElementPanel() {
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);

		lblIcon = new JLabel("loading");
		add(lblIcon);
		
		lblCategory = new JLabel("x");
		add(lblCategory);
		
		JLabel lblCount = new JLabel("count");
		add(lblCount);
	}
	
	public void setText(char awesomeIcon, String category){
		Font ttfReal = null;
		try {
	        InputStream in = this.getClass().getResourceAsStream("/resources/fontawesome-webfont.ttf");
			Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, in);
		    ttfReal = ttfBase.deriveFont(Font.BOLD, 40);
		    //setText(String.valueOf('\uf15b'));
		} catch (FontFormatException | IOException e) {
			System.out.println("font IO exception");
			e.printStackTrace();
		}
		lblIcon.setFont(ttfReal);
		lblIcon.setText(String.valueOf(awesomeIcon));
		lblCategory.setText(category);
	}
	
	public void setFontColor(Color color){
		lblIcon.setForeground(color);
		lblCategory.setForeground(color);
	}
}
