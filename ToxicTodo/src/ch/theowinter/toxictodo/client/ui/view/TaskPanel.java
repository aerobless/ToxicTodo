package ch.theowinter.toxictodo.client.ui.view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.Box;
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
import ch.theowinter.toxictodo.sharedobjects.elements.TodoTask;

public class TaskPanel extends JPanel {
	private static final long serialVersionUID = -2022909795010691054L;
	private ClientTodoManager todoManager;
	private MainWindow main;
	
	//Decoration
	private PanelHeader header;
	
	//Data
	private JComboBox<String> priorityCombobox;
	private JTextField summaryTextField;
	private JTextField hyperlinkTextField;
	private JCheckBox dailyCheckbox;
	private JCheckBox weeklyCheckbox;
	private JCheckBox monthlyCheckbox;
	private JTextArea descriptionTextArea;
	
	//History
	private String oldSummary;

	//Buttons
	JButton btnSave;
	JButton btnCancel;
	JButton btnSaveLog;

	
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
		
		header = new PanelHeader();
		header.setTitel("Add a new task");
		header.setSubTitel("Press 'save' when you're done.");
		header.setIcon('\uf15b');

		add(header, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(ToxicColors.SOFT_GREY);
		add(centerPanel, BorderLayout.CENTER);
		GridBagLayout gblCenterPanel = new GridBagLayout();
		gblCenterPanel.columnWidths = new int[]{108, 0, 20, 0};
		gblCenterPanel.rowHeights = new int[]{15, 0, 0, 0, 0, 0, 0, 0};
		gblCenterPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gblCenterPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		centerPanel.setLayout(gblCenterPanel);
		
		JLabel spacer = new JLabel(" ");
		GridBagConstraints gbcSpacer = new GridBagConstraints();
		gbcSpacer.insets = new Insets(0, 0, 5, 5);
		gbcSpacer.gridx = 0;
		gbcSpacer.gridy = 0;
		centerPanel.add(spacer, gbcSpacer);
		
		JLabel lblType = new JLabel("Task Priority:");
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
		
		summaryTextField = new JTextField();
		GridBagConstraints gbcTxtFieldSummary = new GridBagConstraints();
		gbcTxtFieldSummary.insets = new Insets(0, 0, 5, 5);
		gbcTxtFieldSummary.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtFieldSummary.gridx = 1;
		gbcTxtFieldSummary.gridy = 2;
		centerPanel.add(summaryTextField, gbcTxtFieldSummary);
		summaryTextField.setColumns(10);
		
		JLabel lblHyperlink = new JLabel("Hyperlink:");
		GridBagConstraints gbcLblHyperlink = new GridBagConstraints();
		gbcLblHyperlink.anchor = GridBagConstraints.EAST;
		gbcLblHyperlink.insets = new Insets(0, 0, 5, 5);
		gbcLblHyperlink.gridx = 0;
		gbcLblHyperlink.gridy = 3;
		centerPanel.add(lblHyperlink, gbcLblHyperlink);
		
		JPanel hyperlinkPanel = new JPanel();
		GridBagConstraints gbcHyperlinkPanel = new GridBagConstraints();
		gbcHyperlinkPanel.fill = GridBagConstraints.BOTH;
		gbcHyperlinkPanel.insets = new Insets(0, 0, 5, 5);
		gbcHyperlinkPanel.gridx = 1;
		gbcHyperlinkPanel.gridy = 3;
		centerPanel.add(hyperlinkPanel, gbcHyperlinkPanel);
		hyperlinkPanel.setOpaque(false);
		hyperlinkPanel.setLayout(new BorderLayout(0, 0));
		
		hyperlinkTextField = new JTextField();
		hyperlinkPanel.add(hyperlinkTextField, BorderLayout.CENTER);
		hyperlinkTextField.setColumns(10);
		
		JButton btnLaunchHyperlink = new JButton("Launch");
		btnLaunchHyperlink.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(hyperlinkTextField.getText().startsWith("http://")||hyperlinkTextField.getText().startsWith("https://")){
				    Desktop desktop = java.awt.Desktop.getDesktop();
				       try {
						desktop.browse(new URI(hyperlinkTextField.getText()));
					} catch (IOException e1) {
						Logger.log("IOException while trying to convert String to URI in TaskPanel",e1);
					} catch (URISyntaxException anEx) {
						Logger.log("URISyntaxException while trying to convert String to URI in TaskPanel",anEx);
					}
				} else {
					Logger.log("User tried to launch a URL that doesn't start with http:// nor https://.");
					Logger.log("At this time ftp:// etc. are not supported.");
				}
			}
		});
		hyperlinkPanel.add(btnLaunchHyperlink, BorderLayout.EAST);
		
		JPanel repeatableRowJPanel = new JPanel();
		repeatableRowJPanel.setOpaque(false);
		FlowLayout flRepeatableRowJPanel = (FlowLayout) repeatableRowJPanel.getLayout();
		flRepeatableRowJPanel.setVgap(0);
		flRepeatableRowJPanel.setHgap(0);
		flRepeatableRowJPanel.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbcRepeatableRowJPanel = new GridBagConstraints();
		gbcRepeatableRowJPanel.insets = new Insets(0, 0, 5, 5);
		gbcRepeatableRowJPanel.fill = GridBagConstraints.BOTH;
		gbcRepeatableRowJPanel.gridx = 1;
		gbcRepeatableRowJPanel.gridy = 4;
		centerPanel.add(repeatableRowJPanel, gbcRepeatableRowJPanel);
		
		dailyCheckbox = new JCheckBox("Daily");
		repeatableRowJPanel.add(dailyCheckbox);
		
		weeklyCheckbox = new JCheckBox("Weekly");
		repeatableRowJPanel.add(weeklyCheckbox);
		
		monthlyCheckbox = new JCheckBox("Monthly");
		repeatableRowJPanel.add(monthlyCheckbox);
		
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
		
		descriptionTextArea = new JTextArea();
		descriptionTextArea.setLineWrap(true);
		scrollPane.setViewportView(descriptionTextArea);
		scrollPane.setBorder(summaryTextField.getBorder());
		
		JPanel buttonRowJPanel = new JPanel();
		buttonRowJPanel.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) buttonRowJPanel.getLayout();
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		GridBagConstraints gbcButtonRowJPanel = new GridBagConstraints();
		gbcButtonRowJPanel.insets = new Insets(0, 0, 0, 5);
		gbcButtonRowJPanel.fill = GridBagConstraints.BOTH;
		gbcButtonRowJPanel.gridx = 1;
		gbcButtonRowJPanel.gridy = 6;
		centerPanel.add(buttonRowJPanel, gbcButtonRowJPanel);
		
		btnSaveLog = new JButton("Complete");
		buttonRowJPanel.add(btnSaveLog);
		
		Component horizontalStrut = Box.createHorizontalStrut(30);
		buttonRowJPanel.add(horizontalStrut);
		
		btnCancel = new JButton("Cancel");
		buttonRowJPanel.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelTask();
			}
        }); 
		
		btnSave = new JButton("Save");
		buttonRowJPanel.add(btnSave);
	}
	
	public void loadTask(TodoTask loadedTask){
		oldSummary = loadedTask.getSummary();
		
		priorityCombobox.setSelectedIndex(loadedTask.getPriority());
		descriptionTextArea.setText(loadedTask.getDescription());
		summaryTextField.setText(loadedTask.getSummary());
		hyperlinkTextField.setText(loadedTask.getHyperlink());
		dailyCheckbox.setSelected(loadedTask.isDaily());
		weeklyCheckbox.setSelected(loadedTask.isWeekly());
		monthlyCheckbox.setSelected(loadedTask.isMonthly());
		
		btnSaveLog.setVisible(false);

		header.setTitel("Edit a task");
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateTask();
			}
        });
	}
	
	public void cleanTask(){
		priorityCombobox.setSelectedIndex(0);
		descriptionTextArea.setText("");
		summaryTextField.setText("");
		hyperlinkTextField.setText("");
		dailyCheckbox.setSelected(false);
		weeklyCheckbox.setSelected(false);
		monthlyCheckbox.setSelected(false);
		
		btnSaveLog.setVisible(true);
		
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveTask();
			}
        });
		
		btnSaveLog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAndCompleteTask();
			}
        }); 
	}
	
	private void cancelTask(){
		cleanTask();
		main.switchToTasks();
	}
	
	//TODO: add additional properties such as description, hyperlink, daily etc.
	//TODO: make less duplicated
	private void saveAndCompleteTask(){
		int taskPriority = priorityCombobox.getSelectedIndex();
		TodoCategory category = main.getSelectedCategory();
		String taskDescripition = descriptionTextArea.getText();
		if(fieldsVerified()){
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
		if(fieldsVerified()){
			try {
				int taskPriority = priorityCombobox.getSelectedIndex();
				TodoCategory category = main.getSelectedCategory();
				String taskSummary = summaryTextField.getText();
				String taskDescription = descriptionTextArea.getText();
				String hyperlink = hyperlinkTextField.getText();
				boolean daily = dailyCheckbox.isSelected();
				boolean weekly = weeklyCheckbox.isSelected();
				boolean monthly = monthlyCheckbox.isSelected();
				
				todoManager.addNewTask(taskPriority,category.getKeyword(), taskSummary, taskDescription, hyperlink, daily, weekly, monthly);
				main.switchToTasks();
			} catch (IOException anEx) {
				Logger.log("Connection lost while trying to save task.", anEx);
				main.connectionWarning();
			}
		}else{
			main.genericWarning("Unable to save", "Have you filled in all fields?");
		}
	}
	
	private void updateTask(){
		if(fieldsVerified()){
			try {
				TodoTask editedTask = new TodoTask(summaryTextField.getText());
				editedTask.setPriority(priorityCombobox.getSelectedIndex());
				editedTask.setDescription(descriptionTextArea.getText());
				editedTask.setHyperlink(hyperlinkTextField.getText());
				editedTask.setDaily(dailyCheckbox.isSelected());
				editedTask.setWeekly(weeklyCheckbox.isSelected());
				editedTask.setMonthly(monthlyCheckbox.isSelected());
				todoManager.editTask(editedTask, oldSummary);
				main.switchToTasks();
			} catch (IOException anEx) {
				Logger.log("Connection lost while trying to save task.", anEx);
				main.connectionWarning();
			}
		}else{
			main.genericWarning("Unable to save", "Have you filled in all fields?");
		}
	}

	/**
	 * Verifies that all fields are filled in correctly.
	 * 
	 * @param taskPriority
	 * @param category
	 * @param taskSummary
	 * @return
	 */
	private boolean fieldsVerified() {
		return main.getSelectedCategory() != null && summaryTextField.getText().length()>1 && priorityCombobox.getSelectedIndex()>-1;
	}
}
