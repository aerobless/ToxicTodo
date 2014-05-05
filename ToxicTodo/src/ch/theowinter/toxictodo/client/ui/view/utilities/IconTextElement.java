package ch.theowinter.toxictodo.client.ui.view.utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class IconTextElement extends JPanel{
	private static final long serialVersionUID = 6926949587536088474L;
	JLabel lblIcon;
	JPanel iconPanel;
	JLabel lblTaskDescription;

	/**
	 * CategoryElementPanel is used in categoryList's cell-renderer.
	 */
	public IconTextElement() {
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);

		iconPanel = new JPanel();
		add(iconPanel);
		lblIcon = new JLabel("loading");
		iconPanel.add(lblIcon);
		iconPanel.setOpaque(false);
		FlowLayout flowLayoutIcon = new FlowLayout();
		flowLayoutIcon.setVgap(0);
		flowLayoutIcon.setHgap(0);
		iconPanel.setLayout(flowLayoutIcon);

		iconPanel.setPreferredSize(new Dimension(20, 20));
		iconPanel.setMaximumSize(new Dimension(20, 20));
		iconPanel.setMinimumSize(new Dimension(20, 20));
		
		lblTaskDescription = new JLabel("x");
		add(lblTaskDescription);
	}
	/**
	 * Default size = 20
	 *
	 * @param awesomeIcon
	 * @param taskDescription
	 */
	public void setText(char awesomeIcon, String taskDescription){
		setText(awesomeIcon, taskDescription, 20, 999);
	}
	
	public void setText(char awesomeIcon, String taskDescription, int iconSize, int textSize){
		//TODO: ugly fix.. we should make this clean sometime.
		if(textSize!=999){
			iconPanel.setPreferredSize(new Dimension(iconSize+10, iconSize));
			iconPanel.setMaximumSize(new Dimension(iconSize+10, iconSize));
			iconPanel.setMinimumSize(new Dimension(iconSize+10, iconSize));	
		}
		Font ttfReal = ToxicData.AWESOME_FONT.deriveFont(Font.BOLD, iconSize);
		lblIcon.setFont(ttfReal);
		lblIcon.setText(String.valueOf(awesomeIcon));
		lblTaskDescription.setText(formatText(taskDescription));
		if(textSize!=999){
			lblTaskDescription.setFont(new Font("Lucida Grande", Font.PLAIN, textSize));
		}
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
