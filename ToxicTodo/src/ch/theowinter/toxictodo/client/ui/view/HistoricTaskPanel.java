package ch.theowinter.toxictodo.client.ui.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import ch.theowinter.toxictodo.client.ui.view.utilities.PanelHeaderWhite;
import ch.theowinter.toxictodo.client.ui.view.utilities.ToxicColors;

public class HistoricTaskPanel extends JPanel{
	private static final long serialVersionUID = -9135148026512910749L;

	public HistoricTaskPanel() {
		super();
		// TODO Auto-generated constructor stub
	}

	void initialize(){	
		PanelHeaderWhite taskListHeader = new PanelHeaderWhite();
	    
	    JScrollPane taskScrollPane = new JScrollPane();
		taskScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    
		JPanel totalTaskPanel = new JPanel();
	    totalTaskPanel.setLayout(new BorderLayout(0, 0));
	    totalTaskPanel.add(taskListHeader, BorderLayout.NORTH);
	    totalTaskPanel.add(taskScrollPane, BorderLayout.CENTER);
	    
	    add(totalTaskPanel);
	/*
	    TaskListModel taskListModel = new TaskListModel(categoryListModel.getElementAt(0).getKeyword(), todoManager);
		taskList = new JList<TodoTask>(taskListModel);
		taskList.setCellRenderer(new TaskListCellRenderer());
		taskList.setBackground(ToxicColors.SOFT_GREY);*/
		
		/*taskScrollPane.setViewportView(taskList);*/
		taskScrollPane.setBackground(ToxicColors.SOFT_GREY);
		taskScrollPane.setBorder(new LineBorder(Color.black,0));
	}
}
