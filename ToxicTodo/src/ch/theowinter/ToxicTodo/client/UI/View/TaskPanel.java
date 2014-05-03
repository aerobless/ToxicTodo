package ch.theowinter.ToxicTodo.client.UI.View;

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

import ch.theowinter.ToxicTodo.SharedObjects.Elements.TodoCategory;
import ch.theowinter.ToxicTodo.client.ClientTodoManager;
import ch.theowinter.ToxicTodo.client.UI.Model.TaskPriorityComboboxModel;
import ch.theowinter.ToxicTodo.client.UI.View.Utilities.PanelHeaderWhite;
import ch.theowinter.ToxicTodo.client.UI.View.Utilities.ToxicColors;

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
		setBackground(ToxicColors.softGrey);
		setBounds(100, 100, 450, 300);
		setBorder(null);
		setLayout(new BorderLayout(0, 0));
		
		PanelHeaderWhite header = new PanelHeaderWhite();
		header.setTitel("Add a new task");
		header.setSubTitel("Press 'enter' when you're done.");
		header.setIcon('\uf15b');

		add(header, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(ToxicColors.softGrey);
		add(centerPanel, BorderLayout.CENTER);
		GridBagLayout gbl_centerPanel = new GridBagLayout();
		gbl_centerPanel.columnWidths = new int[]{124, 0, 0, 0};
		gbl_centerPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_centerPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_centerPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		centerPanel.setLayout(gbl_centerPanel);
		
		JLabel spacer = new JLabel(" ");
		GridBagConstraints gbc_spacer = new GridBagConstraints();
		gbc_spacer.insets = new Insets(0, 0, 5, 5);
		gbc_spacer.gridx = 0;
		gbc_spacer.gridy = 0;
		centerPanel.add(spacer, gbc_spacer);
		
		JLabel lblType = new JLabel("Priority:");
		GridBagConstraints gbc_lblType = new GridBagConstraints();
		gbc_lblType.anchor = GridBagConstraints.EAST;
		gbc_lblType.insets = new Insets(0, 0, 5, 5);
		gbc_lblType.gridx = 0;
		gbc_lblType.gridy = 1;
		centerPanel.add(lblType, gbc_lblType);
		
		priorityCombobox = new JComboBox<String>(new TaskPriorityComboboxModel());
		priorityCombobox.setSelectedIndex(0);

		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		centerPanel.add(priorityCombobox, gbc_comboBox);
		
		JLabel lblCompletedUntil = new JLabel("Completed until:");
		GridBagConstraints gbc_lblCompletedUntil = new GridBagConstraints();
		gbc_lblCompletedUntil.anchor = GridBagConstraints.EAST;
		gbc_lblCompletedUntil.insets = new Insets(0, 0, 5, 5);
		gbc_lblCompletedUntil.gridx = 0;
		gbc_lblCompletedUntil.gridy = 2;
		centerPanel.add(lblCompletedUntil, gbc_lblCompletedUntil);
		
		txtFieldCompletedUntil = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		centerPanel.add(txtFieldCompletedUntil, gbc_textField);
		txtFieldCompletedUntil.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description:");
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 3;
		centerPanel.add(lblDescription, gbc_lblDescription);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 3, 8, 8);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 3;
		centerPanel.add(scrollPane, gbc_scrollPane);
		
		txtAreaTaskDescription = new JTextArea();
		txtAreaTaskDescription.setLineWrap(true);
		scrollPane.setViewportView(txtAreaTaskDescription);
		scrollPane.setBorder(txtFieldCompletedUntil.getBorder());
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(ToxicColors.softGrey);
		FlowLayout flowLayout_1 = (FlowLayout) bottomPanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
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
