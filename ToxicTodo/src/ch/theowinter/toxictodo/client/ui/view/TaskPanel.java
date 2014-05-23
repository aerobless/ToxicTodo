package ch.theowinter.toxictodo.client.ui.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import ch.theowinter.toxictodo.client.ClientTodoManager;
import ch.theowinter.toxictodo.client.ui.model.TaskPriorityComboboxModel;
import ch.theowinter.toxictodo.client.ui.view.utilities.PanelHeader;
import ch.theowinter.toxictodo.client.ui.view.utilities.ToxicColors;
import ch.theowinter.toxictodo.sharedobjects.Logger;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoCategory;

public class TaskPanel extends JPanel {
	private static final long serialVersionUID = -2022909795010691054L;

	private JTextArea txtAreaTaskDescription;
	private JTextField txtFieldSummary;
	private JComboBox<String> priorityCombobox;
	private MainWindow main;
	private ClientTodoManager todoManager;
	private JTextField txtFieldHyperlink;

	/**
	 * Create the frame.
	 */
	public TaskPanel(MainWindow main, ClientTodoManager todoManager) {
		this.main = main;
		this.todoManager = todoManager;
		setBackground(ToxicColors.SOFT_GREY);
		setBounds(100, 100, 515, 381);
		setBorder(null);
		setLayout(new BorderLayout(0, 0));
		
		PanelHeader header = new PanelHeader();
		header.setTitel("Add a new task");
		header.setSubTitel("Press 'enter' when you're done.");
		header.setIcon('\uf15b');

		add(header, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(ToxicColors.SOFT_GREY);
		add(centerPanel, BorderLayout.CENTER);
		GridBagLayout gblCenterPanel = new GridBagLayout();
		gblCenterPanel.columnWidths = new int[]{124, 0, 0, 0};
		gblCenterPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gblCenterPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gblCenterPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		centerPanel.setLayout(gblCenterPanel);
		
		JLabel spacer = new JLabel(" ");
		GridBagConstraints gbcSpacer = new GridBagConstraints();
		gbcSpacer.insets = new Insets(0, 0, 5, 5);
		gbcSpacer.gridx = 0;
		gbcSpacer.gridy = 0;
		centerPanel.add(spacer, gbcSpacer);
		
		JLabel lblType = new JLabel("Priority:");
		GridBagConstraints gbcLblType = new GridBagConstraints();
		gbcLblType.anchor = GridBagConstraints.EAST;
		gbcLblType.insets = new Insets(0, 0, 5, 5);
		gbcLblType.gridx = 0;
		gbcLblType.gridy = 1;
		centerPanel.add(lblType, gbcLblType);
		
		priorityCombobox = new JComboBox<String>(new TaskPriorityComboboxModel());
		priorityCombobox.setSelectedIndex(0);

		GridBagConstraints gbcComboBox = new GridBagConstraints();
		gbcComboBox.insets = new Insets(0, 0, 5, 5);
		gbcComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbcComboBox.gridx = 1;
		gbcComboBox.gridy = 1;
		centerPanel.add(priorityCombobox, gbcComboBox);
		
		JLabel lblSummary = new JLabel("Summary:");
		GridBagConstraints gbcLblSummary = new GridBagConstraints();
		gbcLblSummary.anchor = GridBagConstraints.EAST;
		gbcLblSummary.insets = new Insets(0, 0, 5, 5);
		gbcLblSummary.gridx = 0;
		gbcLblSummary.gridy = 2;
		centerPanel.add(lblSummary, gbcLblSummary);
		
		txtFieldSummary = new JTextField();
		GridBagConstraints gbcTxtFieldSummary = new GridBagConstraints();
		gbcTxtFieldSummary.insets = new Insets(0, 0, 5, 5);
		gbcTxtFieldSummary.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtFieldSummary.gridx = 1;
		gbcTxtFieldSummary.gridy = 2;
		centerPanel.add(txtFieldSummary, gbcTxtFieldSummary);
		txtFieldSummary.setColumns(10);
		
		JLabel lblHyperlink = new JLabel("Hyperlink:");
		GridBagConstraints gbcLblHyperlink = new GridBagConstraints();
		gbcLblHyperlink.anchor = GridBagConstraints.EAST;
		gbcLblHyperlink.insets = new Insets(0, 0, 5, 5);
		gbcLblHyperlink.gridx = 0;
		gbcLblHyperlink.gridy = 3;
		centerPanel.add(lblHyperlink, gbcLblHyperlink);
		
		txtFieldHyperlink = new JTextField();
		GridBagConstraints gbcTxtFieldHyperlink = new GridBagConstraints();
		gbcTxtFieldHyperlink.insets = new Insets(0, 0, 5, 5);
		gbcTxtFieldHyperlink.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtFieldHyperlink.gridx = 1;
		gbcTxtFieldHyperlink.gridy = 3;
		centerPanel.add(txtFieldHyperlink, gbcTxtFieldHyperlink);
		txtFieldHyperlink.setColumns(10);
		
		JLabel lblRepeatable = new JLabel("Repeatable:");
		GridBagConstraints gbcLblRepeatable = new GridBagConstraints();
		gbcLblRepeatable.anchor = GridBagConstraints.EAST;
		gbcLblRepeatable.insets = new Insets(0, 0, 5, 5);
		gbcLblRepeatable.gridx = 0;
		gbcLblRepeatable.gridy = 4;
		centerPanel.add(lblRepeatable, gbcLblRepeatable);
		
		JCheckBox checkBoxRepeatable = new JCheckBox("");
		GridBagConstraints gbcCheckBoxRepeatable = new GridBagConstraints();
		gbcCheckBoxRepeatable.anchor = GridBagConstraints.WEST;
		gbcCheckBoxRepeatable.insets = new Insets(0, 0, 5, 5);
		gbcCheckBoxRepeatable.gridx = 1;
		gbcCheckBoxRepeatable.gridy = 4;
		centerPanel.add(checkBoxRepeatable, gbcCheckBoxRepeatable);
		
		JLabel lblDescription = new JLabel("Description:");
		GridBagConstraints gbcLblDescription = new GridBagConstraints();
		gbcLblDescription.insets = new Insets(0, 0, 5, 5);
		gbcLblDescription.anchor = GridBagConstraints.NORTHEAST;
		gbcLblDescription.gridx = 0;
		gbcLblDescription.gridy = 5;
		centerPanel.add(lblDescription, gbcLblDescription);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbcScrollPane = new GridBagConstraints();
		gbcScrollPane.insets = new Insets(0, 3, 8, 8);
		gbcScrollPane.fill = GridBagConstraints.BOTH;
		gbcScrollPane.gridx = 1;
		gbcScrollPane.gridy = 5;
		centerPanel.add(scrollPane, gbcScrollPane);
		
		txtAreaTaskDescription = new JTextArea();
		txtAreaTaskDescription.setLineWrap(true);
		scrollPane.setViewportView(txtAreaTaskDescription);
		scrollPane.setBorder(txtFieldSummary.getBorder());
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(ToxicColors.SOFT_GREY);
		FlowLayout flowLayoutBottomPanel = (FlowLayout) bottomPanel.getLayout();
		flowLayoutBottomPanel.setAlignment(FlowLayout.RIGHT);
		add(bottomPanel, BorderLayout.SOUTH);
		
		JButton btnCancel = new JButton("Cancel");
		bottomPanel.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelTask();
			}
        }); 
		
		JButton btnSaveLog = new JButton("Complete");
		bottomPanel.add(btnSaveLog);
		btnSaveLog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAndCompleteTask();
			}
        }); 
		
		JButton btnSave = new JButton("Save");
		bottomPanel.add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveTask();
			}
        }); 
	}
	
	public void newTask(){
		priorityCombobox.setSelectedIndex(0);
		txtAreaTaskDescription.setText("");
		txtFieldSummary.setText("");
	}
	
	private void cancelTask(){
		priorityCombobox.setSelectedIndex(0);
		txtAreaTaskDescription.setText("");
		txtFieldSummary.setText("");
		main.switchToTasks();
	}
	
	private void saveAndCompleteTask(){
		int taskPriority = priorityCombobox.getSelectedIndex();
		TodoCategory category = main.getSelectedCategory();
		String taskDescripition = txtAreaTaskDescription.getText();
		if(category != null && taskDescripition.length()>1 && taskPriority>-1){
			try {
				todoManager.addAndCompleteTask(taskPriority,category.getKeyword(), taskDescripition);
				main.switchToTasks();
			} catch (IOException anEx) {
				Logger.log("Connection lost while trying to saveAndComplete a task.", anEx);
				main.connectionWarning();
			}
		}else{
			main.genericWarning("Unable to save", "Have you filled in all fields?");
		}
	}
	
	private void saveTask(){
		int taskPriority = priorityCombobox.getSelectedIndex();
		TodoCategory category = main.getSelectedCategory();
		String taskDescripition = txtAreaTaskDescription.getText();
		if(category != null && taskDescripition.length()>1 && taskPriority>-1){
			try {
				todoManager.addNewTask(taskPriority,category.getKeyword(), taskDescripition);
				main.switchToTasks();
			} catch (IOException anEx) {
				Logger.log("Connection lost while trying to save task.", anEx);
				main.connectionWarning();
			}
		}else{
			main.genericWarning("Unable to save", "Have you filled in all fields?");
		}
	}
}
