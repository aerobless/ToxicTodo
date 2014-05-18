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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ch.theowinter.toxictodo.client.ClientTodoManager;
import ch.theowinter.toxictodo.client.ui.model.FontString;
import ch.theowinter.toxictodo.client.ui.model.IconComboBoxModel;
import ch.theowinter.toxictodo.client.ui.view.MainWindow;
import ch.theowinter.toxictodo.client.ui.view.utilities.IconComboBoxRenderer;
import ch.theowinter.toxictodo.client.ui.view.utilities.PanelHeaderWhite;
import ch.theowinter.toxictodo.client.ui.view.utilities.ToxicColors;
import ch.theowinter.toxictodo.sharedobjects.Logger;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoCategory;

public class CategoryPanel extends JPanel {
	private static final long serialVersionUID = -2022909795010691054L;
	private MainWindow main;
	private ClientTodoManager todoManager;
	private JComboBox<FontString> iconCombobox;
	private JTextField txtFieldCategoryTitel;
	private TodoCategory oldCategory;
	private JTextField txtFieldCategoryKeyword;
	private PanelHeaderWhite header;
	
	//Buttons
	private JButton btnSave;
	private JButton btnDelete;

	/**
	 * Create the frame.
	 */
	public CategoryPanel(MainWindow main, ClientTodoManager todoManager) {
		this.main = main;
		this.todoManager = todoManager;
		setBackground(ToxicColors.SOFT_GREY);
		setBounds(100, 100, 450, 300);
		setBorder(null);
		setLayout(new BorderLayout(0, 0));
		
		header = new PanelHeaderWhite();
		add(header, BorderLayout.NORTH);
		header.setSubTitel("Press the 'save' button when you're done.");
		header.setIcon('\uf07b');
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(ToxicColors.SOFT_GREY);
		add(centerPanel, BorderLayout.CENTER);
		GridBagLayout gblCenterPanel = new GridBagLayout();
		gblCenterPanel.columnWidths = new int[]{124, 0, 0, 0};
		gblCenterPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gblCenterPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gblCenterPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		centerPanel.setLayout(gblCenterPanel);
		
		JLabel spaceLabel = new JLabel("  ");
		GridBagConstraints gbcSpaceLabel = new GridBagConstraints();
		gbcSpaceLabel.insets = new Insets(0, 0, 5, 5);
		gbcSpaceLabel.gridx = 1;
		gbcSpaceLabel.gridy = 0;
		centerPanel.add(spaceLabel, gbcSpaceLabel);
		
		JLabel lblIcon = new JLabel("Icon:");
		GridBagConstraints gbcLblIcon = new GridBagConstraints();
		gbcLblIcon.anchor = GridBagConstraints.EAST;
		gbcLblIcon.insets = new Insets(0, 0, 5, 5);
		gbcLblIcon.gridx = 0;
		gbcLblIcon.gridy = 1;
		centerPanel.add(lblIcon, gbcLblIcon);
		
		iconCombobox = new JComboBox<FontString>(new IconComboBoxModel());
		iconCombobox.setRenderer(new IconComboBoxRenderer());

		GridBagConstraints gbcIconCombobox = new GridBagConstraints();
		gbcIconCombobox.insets = new Insets(0, 0, 5, 5);
		gbcIconCombobox.fill = GridBagConstraints.HORIZONTAL;
		gbcIconCombobox.gridx = 1;
		gbcIconCombobox.gridy = 1;
		centerPanel.add(iconCombobox, gbcIconCombobox);
		
		JLabel lblCompletedUntil = new JLabel("Category Titel:");
		GridBagConstraints gbcLblCompletedUntil = new GridBagConstraints();
		gbcLblCompletedUntil.anchor = GridBagConstraints.EAST;
		gbcLblCompletedUntil.insets = new Insets(0, 0, 5, 5);
		gbcLblCompletedUntil.gridx = 0;
		gbcLblCompletedUntil.gridy = 2;
		centerPanel.add(lblCompletedUntil, gbcLblCompletedUntil);
		
		txtFieldCategoryTitel = new JTextField();
		GridBagConstraints gbcTxtFieldCategoryTitel = new GridBagConstraints();
		gbcTxtFieldCategoryTitel.insets = new Insets(0, 0, 5, 5);
		gbcTxtFieldCategoryTitel.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtFieldCategoryTitel.gridx = 1;
		gbcTxtFieldCategoryTitel.gridy = 2;
		centerPanel.add(txtFieldCategoryTitel, gbcTxtFieldCategoryTitel);
		txtFieldCategoryTitel.setColumns(10);
		
		JLabel lblDescription = new JLabel("Keyword:");
		GridBagConstraints gbcLblDescription = new GridBagConstraints();
		gbcLblDescription.insets = new Insets(0, 0, 5, 5);
		gbcLblDescription.anchor = GridBagConstraints.EAST;
		gbcLblDescription.gridx = 0;
		gbcLblDescription.gridy = 3;
		centerPanel.add(lblDescription, gbcLblDescription);
		gbcTxtFieldCategoryTitel.insets = new Insets(0, 0, 5, 5);
		gbcTxtFieldCategoryTitel.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtFieldCategoryTitel.gridx = 1;
		gbcTxtFieldCategoryTitel.gridy = 3;
		
		txtFieldCategoryKeyword = new JTextField();
		GridBagConstraints gbcTxtFieldOneWordID = new GridBagConstraints();
		gbcTxtFieldOneWordID.insets = new Insets(0, 0, 5, 5);
		gbcTxtFieldOneWordID.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtFieldOneWordID.gridx = 1;
		gbcTxtFieldOneWordID.gridy = 3;
		centerPanel.add(txtFieldCategoryKeyword, gbcTxtFieldOneWordID);
		txtFieldCategoryKeyword.setColumns(10);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(ToxicColors.SOFT_GREY);
		FlowLayout flowLayout_1 = (FlowLayout) bottomPanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		add(bottomPanel, BorderLayout.SOUTH);
		
		btnDelete = new JButton("Delete Category");
		bottomPanel.add(btnDelete);
		
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
	}
	
	public void setCategory(TodoCategory todoCategory){
		iconCombobox.setSelectedIndex(0);
		txtFieldCategoryTitel.setText(todoCategory.getName());
		txtFieldCategoryKeyword.setText(todoCategory.getKeyword());
		oldCategory = todoCategory;
		header.setTitel("Edit category: "+todoCategory.getName());
		header.setIcon(todoCategory.getIcon());
		btnSave.setToolTipText("All done? Press save.");
		btnSave.setText("Save changes");
		
		btnDelete.setVisible(true);
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteCategory();
			}
        }); 
		//Save changes Listener
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editCategory();
			}
        }); 
	}
	
	public void newCategory(){
		iconCombobox.setSelectedIndex(0);
		txtFieldCategoryTitel.setText("");
		txtFieldCategoryKeyword.setText("");
		btnSave.setEnabled(true);
		btnSave.setToolTipText("All done? Press save.");
		header.setTitel("New Category");
		
		btnDelete.setVisible(false);
		btnSave.setText("Save");
		//Save Listener
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveCategory();
			}
        }); 
	}
	
	private void cancelAndClearCategory(){
		iconCombobox.setSelectedIndex(0);
		txtFieldCategoryTitel.setText("");
		txtFieldCategoryKeyword.setText("");
		main.switchToTasks();
	}
	
	private void deleteCategory(){
		int yesOrNo = 0;
		int dialogResult = JOptionPane.showConfirmDialog (main.frmToxictodo, "Are you certain you want to delete: "+oldCategory.getKeyword(),"Confirm removal of "+oldCategory.getKeyword(),yesOrNo);
		if(dialogResult == JOptionPane.YES_OPTION){
			try {
				main.resetCategorySelection();
				todoManager.removeCategory(oldCategory);
			} catch (IOException e) {
				Logger.log("No connection to server", e);
				main.connectionWarning();
			}
			cancelAndClearCategory();
		}
	}
	
	private void editCategory(){
		char icon = ((FontString)iconCombobox.getSelectedItem()).getIcon();
		String categoryTitel = txtFieldCategoryTitel.getText();
		String categoryKeyword = txtFieldCategoryKeyword.getText();
		if(categoryTitel.length()>2&&categoryKeyword.length()>2){
			try {
				todoManager.editCategory(oldCategory.getKeyword(), categoryKeyword, categoryTitel, icon);
			} catch (IOException e) {
				Logger.log("No connection to server", e);
				main.connectionWarning();
			}
			cancelAndClearCategory();
		}
	}
	
	private void saveCategory(){
		char icon = ((FontString)iconCombobox.getSelectedItem()).getIcon();
		String categoryTitel = txtFieldCategoryTitel.getText();
		String categoryKeyword = txtFieldCategoryKeyword.getText();
		if(categoryTitel.length()>2&&categoryKeyword.length()>2){
			try {
				todoManager.addNewCategory(categoryTitel, categoryKeyword, icon, false);
			} catch (IOException e) {
				Logger.log("No connection to server", e);
				main.connectionWarning();
			}
			cancelAndClearCategory();
		}
	}
}
