package ch.theowinter.ToxicTodo.client.UI.View.Utilities;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.border.MatteBorder;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;

public class TaskListHeader extends JPanel {
	private static final long serialVersionUID = 1231624288433035648L;
	private JLabel lblTitel;
	private JLabel lblSubtitel;
	private JLabel lblIcon;
	private JPanel iconPanel;
	private JPanel titelPanel;
	private JPanel subtitelPanel;
	private JPanel topSpace;
	private JPanel bottomSpace;
	private Color background = ToxicColors.dirtyWhite;

	public TaskListHeader() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel textPanel = new JPanel();
		add(textPanel, BorderLayout.CENTER);

		//Styling:
		setBackground(background);
		textPanel.setBackground(background);
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
		
		topSpace = new JPanel();
		FlowLayout fl_topSpace = (FlowLayout) topSpace.getLayout();
		fl_topSpace.setVgap(7);
		textPanel.add(topSpace);
		topSpace.setBackground(background);
		
		titelPanel = new JPanel();
		FlowLayout fl_titelPanel = (FlowLayout) titelPanel.getLayout();
		fl_titelPanel.setVgap(0);
		fl_titelPanel.setAlignment(FlowLayout.LEFT);
		textPanel.add(titelPanel);
		titelPanel.setBackground(background);
		
		lblTitel = new JLabel("titel");
		titelPanel.add(lblTitel);
		lblTitel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		lblTitel.setForeground(ToxicColors.textGrey);
		
		subtitelPanel = new JPanel();
		FlowLayout fl_subtitelPanel = (FlowLayout) subtitelPanel.getLayout();
		fl_subtitelPanel.setVgap(0);
		fl_subtitelPanel.setAlignment(FlowLayout.LEFT);
		textPanel.add(subtitelPanel);
		subtitelPanel.setBackground(background);
		
		lblSubtitel = new JLabel("subtitel");
		subtitelPanel.add(lblSubtitel);
		lblSubtitel.setForeground(ToxicColors.textGreySoft);
		
		bottomSpace = new JPanel();
		FlowLayout fl_bottomSpace = (FlowLayout) bottomSpace.getLayout();
		fl_bottomSpace.setVgap(7);
		textPanel.add(bottomSpace);
		setBorder(new MatteBorder(0,0,1,0,ToxicColors.textGreySoft));
		bottomSpace.setBackground(background);
		
		iconPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) iconPanel.getLayout();
		flowLayout.setHgap(10);
		add(iconPanel, BorderLayout.WEST);
		iconPanel.setBackground(background);
		
		lblIcon = new JLabel("ICON");
		iconPanel.add(lblIcon);
	}
	
	public void setTitel(String titel){
		lblTitel.setText(titel);
	}
	
	public void setSubTitel(String subtitel){
		lblTitel.setText(subtitel);
	}
	
	public void setIcon(char icon){
		lblIcon.setText(String.valueOf(icon));
	}
}
