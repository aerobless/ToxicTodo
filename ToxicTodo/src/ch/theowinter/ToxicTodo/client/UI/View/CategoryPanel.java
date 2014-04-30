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
import javax.swing.JTextField;

import ch.theowinter.ToxicTodo.client.ClientTodoManager;
import ch.theowinter.ToxicTodo.client.UI.Model.FontString;
import ch.theowinter.ToxicTodo.client.UI.Model.IconComboBoxModel;
import ch.theowinter.ToxicTodo.client.UI.View.Utilities.IconComboBoxRenderer;
import ch.theowinter.ToxicTodo.client.UI.View.Utilities.PanelHeaderWhite;
import ch.theowinter.ToxicTodo.client.UI.View.Utilities.ToxicColors;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoCategory;

public class CategoryPanel extends JPanel {
	private static final long serialVersionUID = -2022909795010691054L;
	private MainWindow main;
	private ClientTodoManager todoManager;
	private JComboBox<FontString> iconCombobox;
	private JTextField txtFieldCategoryTitel;
	private JTextField txtFieldCategoryKeyword;
	private PanelHeaderWhite header;
	
	//Buttons
	private JButton btnSave;

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
		
		header = new PanelHeaderWhite();
		add(header, BorderLayout.NORTH);
		header.setSubTitel("Press the 'save' button when you're done.");
		header.setIcon('\uf07b');
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(ToxicColors.softGrey);
		add(centerPanel, BorderLayout.CENTER);
		GridBagLayout gbl_centerPanel = new GridBagLayout();
		gbl_centerPanel.columnWidths = new int[]{124, 0, 0, 0};
		gbl_centerPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_centerPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_centerPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
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
		
		iconCombobox = new JComboBox<FontString>(new IconComboBoxModel());
		iconCombobox.setRenderer(new IconComboBoxRenderer());
		GridBagConstraints gbc_iconCombobox = new GridBagConstraints();
		gbc_iconCombobox.insets = new Insets(0, 0, 5, 5);
		gbc_iconCombobox.fill = GridBagConstraints.HORIZONTAL;
		gbc_iconCombobox.gridx = 1;
		gbc_iconCombobox.gridy = 1;
		centerPanel.add(iconCombobox, gbc_iconCombobox);
		
		JLabel lblCompletedUntil = new JLabel("Category Titel:");
		GridBagConstraints gbc_lblCompletedUntil = new GridBagConstraints();
		gbc_lblCompletedUntil.anchor = GridBagConstraints.EAST;
		gbc_lblCompletedUntil.insets = new Insets(0, 0, 5, 5);
		gbc_lblCompletedUntil.gridx = 0;
		gbc_lblCompletedUntil.gridy = 2;
		centerPanel.add(lblCompletedUntil, gbc_lblCompletedUntil);
		
		txtFieldCategoryTitel = new JTextField();
		GridBagConstraints gbc_txtFieldCategoryTitel = new GridBagConstraints();
		gbc_txtFieldCategoryTitel.insets = new Insets(0, 0, 5, 5);
		gbc_txtFieldCategoryTitel.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFieldCategoryTitel.gridx = 1;
		gbc_txtFieldCategoryTitel.gridy = 2;
		centerPanel.add(txtFieldCategoryTitel, gbc_txtFieldCategoryTitel);
		txtFieldCategoryTitel.setColumns(10);
		
		JLabel lblDescription = new JLabel("One-Word-ID:");
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.anchor = GridBagConstraints.EAST;
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 3;
		centerPanel.add(lblDescription, gbc_lblDescription);
		gbc_txtFieldCategoryTitel.insets = new Insets(0, 0, 5, 5);
		gbc_txtFieldCategoryTitel.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFieldCategoryTitel.gridx = 1;
		gbc_txtFieldCategoryTitel.gridy = 3;
		
		txtFieldCategoryKeyword = new JTextField();
		GridBagConstraints gbc_txtFieldOneWordID = new GridBagConstraints();
		gbc_txtFieldOneWordID.insets = new Insets(0, 0, 5, 5);
		gbc_txtFieldOneWordID.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFieldOneWordID.gridx = 1;
		gbc_txtFieldOneWordID.gridy = 3;
		centerPanel.add(txtFieldCategoryKeyword, gbc_txtFieldOneWordID);
		txtFieldCategoryKeyword.setColumns(10);
		
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
				cancelAndClearCategory();
			}
        }); 
		
		btnSave = new JButton("Save");
		bottomPanel.add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveTask();
			}
        }); 
	}
	
	public void setCategory(TodoCategory todoCategory){
		iconCombobox.setSelectedIndex(0);
		txtFieldCategoryTitel.setText(todoCategory.getName());
		txtFieldCategoryKeyword.setText(todoCategory.getKeyword());
		btnSave.setEnabled(false);
		btnSave.setToolTipText("Editing categories is currently not supported by the server.");
		header.setTitel("Edit category: "+todoCategory.getName());
	}
	public void newCategory(){
		iconCombobox.setSelectedIndex(0);
		txtFieldCategoryTitel.setText("");
		txtFieldCategoryKeyword.setText("");
		btnSave.setEnabled(true);
		btnSave.setToolTipText("All done? Press save.");
		header.setTitel("New Category");
	}
	
	private void cancelAndClearCategory(){
		iconCombobox.setSelectedIndex(0);
		txtFieldCategoryTitel.setText("");
		txtFieldCategoryKeyword.setText("");
		main.switchToTasks();
	}
	
	private void saveTask(){
		//TODO: better check for empty name, empty description, bad description etc. same with new task
		String categoryTitel = txtFieldCategoryTitel.getText();
		String categoryKeyword = txtFieldCategoryKeyword.getText();
		if(categoryTitel.length()>2&&categoryKeyword.length()>2){
			try {
				todoManager.addNewCategory(categoryTitel, categoryKeyword);
			} catch (IOException anEx) {
				main.connectionWarning();
			}
			cancelAndClearCategory();
		}
	}
}
