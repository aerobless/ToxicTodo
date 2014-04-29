package ch.theowinter.ToxicTodo.client.UI.View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import ch.theowinter.ToxicTodo.client.ClientTodoManager;
import ch.theowinter.ToxicTodo.client.UI.Model.CategoryComboBoxModel;
import ch.theowinter.ToxicTodo.client.UI.View.Utilities.TaskListHeader;
import ch.theowinter.ToxicTodo.client.UI.View.Utilities.ToxicColors;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoCategory;

public class CategoryPanel extends JPanel {
	private static final long serialVersionUID = -2022909795010691054L;

	private JTextArea txtAreaTaskDescription;
	private JTextField txtFieldCompletedUntil;
	private MainWindow main;
	private ClientTodoManager todoManager;

	/**
	 * Create the frame.
	 */
	public CategoryPanel(MainWindow main, ClientTodoManager todoManager) {
		this.main = main;
		this.todoManager = todoManager;
		setBackground(ToxicColors.softGrey);
		setBounds(100, 100, 450, 300);
		setBorder(null);
		setLayout(new BorderLayout(0, 0));
		
		TaskListHeader header = new TaskListHeader();
		add(header, BorderLayout.NORTH);
		header.setTitel("New Category");
		header.setSubTitel("Press the 'save' button when you're done.");
		header.setIcon('\uf07b');
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(ToxicColors.softGrey);
		add(centerPanel, BorderLayout.CENTER);
		GridBagLayout gbl_centerPanel = new GridBagLayout();
		gbl_centerPanel.columnWidths = new int[]{124, 0, 0, 0};
		gbl_centerPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_centerPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_centerPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		centerPanel.setLayout(gbl_centerPanel);
		
		JLabel label = new JLabel("  ");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 0;
		centerPanel.add(label, gbc_label);
		
		JLabel lblIcon = new JLabel("Icon:");
		GridBagConstraints gbc_lblIcon = new GridBagConstraints();
		gbc_lblIcon.anchor = GridBagConstraints.EAST;
		gbc_lblIcon.insets = new Insets(0, 0, 5, 5);
		gbc_lblIcon.gridx = 0;
		gbc_lblIcon.gridy = 1;
		centerPanel.add(lblIcon, gbc_lblIcon);
		
		JComboBox<String> iconCombobox = new JComboBox<String>(new CategoryComboBoxModel(todoManager));
		GridBagConstraints gbc_iconCombobox = new GridBagConstraints();
		gbc_iconCombobox.insets = new Insets(0, 0, 5, 5);
		gbc_iconCombobox.fill = GridBagConstraints.HORIZONTAL;
		gbc_iconCombobox.gridx = 1;
		gbc_iconCombobox.gridy = 1;
		centerPanel.add(iconCombobox, gbc_iconCombobox);
		
		JLabel lblCompletedUntil = new JLabel("Category Name:");
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
		//TODO: reset combobox
		txtAreaTaskDescription.setText("");
		txtFieldCompletedUntil.setText("");
		main.switchToTasks();
	}
	
	private void saveTask(){
		main.switchToTasks();
		TodoCategory category = main.getSelectedCategory();
		if(category != null){
			todoManager.addNewTask(category.getKeyword(), txtAreaTaskDescription.getText());
			txtAreaTaskDescription.setText("");
			txtFieldCompletedUntil.setText("");
		}else{
			System.out.println("No category selected.");
		}
	}
}
