package ch.theowinter.ToxicTodo.client.UI.View.utilities;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.awt.BorderLayout;

public class CategoryElementPanel extends JPanel {
	private static final long serialVersionUID = -2314006883111515559L;
	JLabel lblIcon;
	JLabel lblCategory;
	TaskCounter backCounter;
	private JPanel frontPanel;

	/**
	 * CategoryElementPanel is used in categoryList's cell-renderer.
	 */
	public CategoryElementPanel() {
		setLayout(new BorderLayout(0, 0));
		
		frontPanel = new JPanel();
		//frontPanel.setBackground(bg);
		add(frontPanel, BorderLayout.WEST);
		
		lblIcon = new JLabel("loading");
		frontPanel.add(lblIcon);
		
		lblCategory = new JLabel("x");
		frontPanel.add(lblCategory);
		
		backCounter = new TaskCounter();
		add(backCounter, BorderLayout.EAST);
		backCounter.setPreferredSize(new Dimension(35, 35));
	}
	
	public void setText(char awesomeIcon, String category){
		Font ttfReal = null;
		try {
	        InputStream in = this.getClass().getResourceAsStream("/resources/fontawesome-webfont.ttf");
			Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, in);
		    ttfReal = ttfBase.deriveFont(Font.BOLD, 40);
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
		backCounter.setCounterBoxColor(color);
	}
	
	public void setCounter(int number){
		backCounter.setCounter(number);
	}
	
	public void setBackgroundColor(Color panelColor){
		backCounter.setCounterBackgroundColor(panelColor);
		setBackground(panelColor);
		frontPanel.setBackground(panelColor);
		backCounter.setBackground(panelColor);
		backCounter.setCounterFontColor(panelColor);
	}
}
