package ch.theowinter.ToxicTodo.client.UI.View.utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class TaskCounter extends JPanel {
	private static final long serialVersionUID = -728870559027172774L;
	JLabel lblONE;
	JLabel lblTWO;

	/**
	 * Create the panel.
	 */
	public TaskCounter() {
		setLayout(null);
		lblONE = new JLabel("44");
		lblONE.setBounds(6, 5, 29, 27);
		add(lblONE);
		
		lblTWO = new JLabel("IC");
		lblTWO.setBounds(0, 5, 29, 27);
		add(lblTWO);
		this.setBounds(0,0,40,40);
		try{
			InputStream in = this.getClass().getResourceAsStream("/resources/fontawesome-webfont.ttf");
		    Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, in);
		    Font ttfReal = ttfBase.deriveFont(Font.BOLD, 30);
		    lblTWO.setFont(ttfReal);
		    lblTWO.setForeground(Color.WHITE);
		    lblTWO.setText(String.valueOf('\uf0c8'));
	    } catch (FontFormatException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		setBackground(new Color(83, 145, 206));
	}
	
	public void setCounter(int number){
		lblONE.setText(number+"");
	}
	
	public void setCounterBackgroundColor(Color panelColor){
		setBackground(panelColor);
	}
	
	public void setCounterFontColor(Color fontColor){
		lblONE.setForeground(fontColor);
	}
	
	public void setCounterBoxColor(Color boxColor){
		lblTWO.setForeground(boxColor);
	}
}
