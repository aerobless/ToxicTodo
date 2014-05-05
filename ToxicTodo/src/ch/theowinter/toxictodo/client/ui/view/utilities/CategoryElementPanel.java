package ch.theowinter.toxictodo.client.ui.view.utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CategoryElementPanel extends JPanel {
	private static final long serialVersionUID = -2314006883111515559L;
	JLabel lblIcon;
	JLabel lblCategory;
	TaskCounterPanel backCounter;
	private JPanel frontPanel;

	/**
	 * CategoryElementPanel is used in categoryList's cell-renderer.
	 */
	public CategoryElementPanel() {
		setLayout(new BorderLayout(0, 0));
		
		frontPanel = new JPanel();
		add(frontPanel, BorderLayout.WEST);
		
		lblIcon = new JLabel("loading");
		frontPanel.add(lblIcon);
		
		lblCategory = new JLabel("x");
		frontPanel.add(lblCategory);
		
		backCounter = new TaskCounterPanel();
		add(backCounter, BorderLayout.EAST);
		backCounter.setPreferredSize(new Dimension(35, 35));
	}
	
	public void setText(char awesomeIcon, String category){
		Font ttfReal = ToxicData.AWESOME_FONT.deriveFont(Font.BOLD, 40);
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
