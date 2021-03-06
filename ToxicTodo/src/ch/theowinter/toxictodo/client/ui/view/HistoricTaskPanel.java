package ch.theowinter.toxictodo.client.ui.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import ch.theowinter.toxictodo.client.ui.view.utilities.PanelHeader;
import ch.theowinter.toxictodo.client.ui.view.utilities.ToxicColors;

public class HistoricTaskPanel extends JPanel{
	private static final long serialVersionUID = -9135148026512910749L;

	public HistoricTaskPanel() {
		super();
	}

	void initialize(){	
		PanelHeader taskListHeader = new PanelHeader();
	    
	    JScrollPane taskScrollPane = new JScrollPane();
		taskScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    
		JPanel totalTaskPanel = new JPanel();
	    totalTaskPanel.setLayout(new BorderLayout(0, 0));
	    totalTaskPanel.add(taskListHeader, BorderLayout.NORTH);
	    totalTaskPanel.add(taskScrollPane, BorderLayout.CENTER);
	    
	    add(totalTaskPanel);

		taskScrollPane.setBackground(ToxicColors.SOFT_GREY);
		taskScrollPane.setBorder(new LineBorder(Color.black,0));
	}
}
