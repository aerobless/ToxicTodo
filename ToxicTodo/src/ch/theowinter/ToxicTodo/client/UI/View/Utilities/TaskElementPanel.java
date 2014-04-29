package ch.theowinter.ToxicTodo.client.UI.View.Utilities;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TaskElementPanel extends JPanel{
	private static final long serialVersionUID = 6926949587536088474L;
	JLabel lblIcon;
	JLabel lblTaskDescription;

	/**
	 * CategoryElementPanel is used in categoryList's cell-renderer.
	 */
	public TaskElementPanel() {
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);

		lblIcon = new JLabel("loading");
		add(lblIcon);
		
		lblTaskDescription = new JLabel("x");
		add(lblTaskDescription);
	}
	
	public void setText(char awesomeIcon, String taskDescription){
		Font ttfReal = null;
		try {
	        InputStream in = this.getClass().getResourceAsStream("/resources/fontawesome-webfont.ttf");
			Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, in);
		    ttfReal = ttfBase.deriveFont(Font.BOLD, 20);
		    //setText(String.valueOf('\uf15b'));
		} catch (FontFormatException | IOException e) {
			System.out.println("font IO exception");
			e.printStackTrace();
		}
		lblIcon.setFont(ttfReal);
		lblIcon.setText(String.valueOf(awesomeIcon));
		lblTaskDescription.setText(formatText(taskDescription));
	}
	
	public void setFontColor(Color color){
		lblIcon.setForeground(color);
		lblTaskDescription.setForeground(color);
	}
	
	private String formatText(String input){
		//format ** -->
		String[] markers = input.split("\\*\\*");
		for(int i = 0; i<markers.length; i++){
			if(!(i%2==0)){
				markers[i] = "<font color=red>"+markers[i]+"</font>";
			}
		}
		StringBuilder outputBuilder = new StringBuilder();
		for(String s : markers) {
			outputBuilder.append(s);
		}
		return "<html>"+outputBuilder.toString()+"</html>";
	}
}
