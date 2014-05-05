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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import ch.theowinter.toxictodo.client.ClientTodoManager;
import ch.theowinter.toxictodo.client.ui.model.TaskPriorityComboboxModel;
import ch.theowinter.toxictodo.client.ui.view.utilities.PanelHeaderWhite;
import ch.theowinter.toxictodo.client.ui.view.utilities.ToxicColors;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoCategory;

public class TaskPanel extends JPanel {
	private static final long serialVersionUID = -2022909795010691054L;

	private JTextArea txtAreaTaskDescription;
	private JTextField txtFieldCompletedUntil;
	private JComboBox<String> priorityCombobox;
	private MainWindow main;
	private ClientTodoManager todoManager;

	/**
	 * Create the frame.
	 */
	public TaskPanel(MainWindow main, ClientTodoManager todoManager) {
		this.main = main;
		this.todoManager = todoManager;
		setBackground(ToxicColors.SOFT_GREY);
		setBounds(100, 100, 450, 300);
		setBorder(null);
		setLayout(new BorderLayout(0, 0));
		
		PanelHeaderWhite header = new PanelHeaderWhite();
		header.setTitel("Add a new task");
		header.setSubTitel("Press 'enter' when you're done.");
		header.setIcon('\uf15b');

		add(header, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(ToxicColors.SOFT_GREY);
		add(centerPanel, BorderLayout.CENTER);
		GridBagLayout gblCenterPanel = new GridBagLayout();
		gblCenterPanel.columnWidths = new int[]{124, 0, 0, 0};
		gblCenterPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gblCenterPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gblCenterPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
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
		
		JLabel lblCompletedUntil = new JLabel("Completed until:");
		GridBagConstraints gbcLblCompletedUntil = new GridBagConstraints();
		gbcLblCompletedUntil.anchor = GridBagConstraints.EAST;
		gbcLblCompletedUntil.insets = new Insets(0, 0, 5, 5);
		gbcLblCompletedUntil.gridx = 0;
		gbcLblCompletedUntil.gridy = 2;
		centerPanel.add(lblCompletedUntil, gbcLblCompletedUntil);
		
		txtFieldCompletedUntil = new JTextField();
		GridBagConstraints gbcTextField = new GridBagConstraints();
		gbcTextField.insets = new Insets(0, 0, 5, 5);
		gbcTextField.fill = GridBagConstraints.HORIZONTAL;
		gbcTextField.gridx = 1;
		gbcTextField.gridy = 2;
		centerPanel.add(txtFieldCompletedUntil, gbcTextField);
		txtFieldCompletedUntil.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description:");
		GridBagConstraints gbcLblDescription = new GridBagConstraints();
		gbcLblDescription.insets = new Insets(0, 0, 5, 5);
		gbcLblDescription.anchor = GridBagConstraints.NORTHEAST;
		gbcLblDescription.gridx = 0;
		gbcLblDescription.gridy = 3;
		centerPanel.add(lblDescription, gbcLblDescription);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbcScrollPane = new GridBagConstraints();
		gbcScrollPane.insets = new Insets(0, 3, 8, 8);
		gbcScrollPane.fill = GridBagConstraints.BOTH;
		gbcScrollPane.gridx = 1;
		gbcScrollPane.gridy = 3;
		centerPanel.add(scrollPane, gbcScrollPane);
		
		txtAreaTaskDescription = new JTextArea();
		txtAreaTaskDescription.setLineWrap(true);
		scrollPane.setViewportView(txtAreaTaskDescription);
		scrollPane.setBorder(txtFieldCompletedUntil.getBorder());
		
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
		txtFieldCompletedUntil.setText("");
	}
	
	private void cancelTask(){
		priorityCombobox.setSelectedIndex(0);
		txtAreaTaskDescription.setText("");
		txtFieldCompletedUntil.setText("");
		main.switchToTasks();
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
				main.connectionWarning();
			}
		}else{
			main.genericWarning("Unable to save", "Have you filled in all fields?");
		}
	}
}
