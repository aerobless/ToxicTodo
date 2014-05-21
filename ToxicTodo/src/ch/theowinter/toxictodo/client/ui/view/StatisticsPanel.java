package ch.theowinter.toxictodo.client.ui.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.theowinter.toxictodo.client.ClientTodoManager;
import ch.theowinter.toxictodo.client.ui.view.utilities.PanelHeaderWhite;
import ch.theowinter.toxictodo.client.ui.view.utilities.ToxicColors;
import ch.theowinter.toxictodo.client.ui.view.utilities.ToxicUIData;
import ch.theowinter.toxictodo.sharedobjects.Logger;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoList;

public class StatisticsPanel extends JPanel {
	private static final long serialVersionUID = -2022909795010691054L;
	private MainWindow main;
	private TodoList historicTodoList;

	/**
	 * Create the frame.
	 */
	public StatisticsPanel(MainWindow mainWindow, ClientTodoManager todoManager) {
		this.main = mainWindow;
		try {
			this.historicTodoList = todoManager.updateHistoricList();
		} catch (IOException e) {
			Logger.log("IOException while trying to get the historic todo list.", e);
			//todo internet warning.
		}
		
		setBackground(ToxicColors.SOFT_GREY);
		setBounds(100, 100, 515, 381);
		setBorder(null);
		setLayout(new BorderLayout(0, 0));
		
		PanelHeaderWhite header = new PanelHeaderWhite();
		header.setTitel("Statistics");
		header.setSubTitel("Track your progress when completing tasks.");
		header.setIcon('\uf080');

		add(header, BorderLayout.NORTH);
		
		JPanel generalStatisticsPane = new JPanel();
		add(generalStatisticsPane, BorderLayout.CENTER);
		generalStatisticsPane.setBackground(ToxicColors.SOFT_GREY);
		GridBagLayout gblGeneralStatisticsPane = new GridBagLayout();
		gblGeneralStatisticsPane.columnWidths = new int[]{0, 161, 67, 149, 122, 0, 0};
		gblGeneralStatisticsPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gblGeneralStatisticsPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gblGeneralStatisticsPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		generalStatisticsPane.setLayout(gblGeneralStatisticsPane);
		
		JLabel lblGeneral = new JLabel("General:");
		lblGeneral.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lblGeneral = new GridBagConstraints();
		gbc_lblGeneral.anchor = GridBagConstraints.WEST;
		gbc_lblGeneral.insets = new Insets(0, 0, 5, 5);
		gbc_lblGeneral.gridx = 1;
		gbc_lblGeneral.gridy = 1;
		generalStatisticsPane.add(lblGeneral, gbc_lblGeneral);
		
		JLabel lblCategories = new JLabel("Categories:");
		lblCategories.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lblCategories = new GridBagConstraints();
		gbc_lblCategories.anchor = GridBagConstraints.WEST;
		gbc_lblCategories.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategories.gridx = 3;
		gbc_lblCategories.gridy = 1;
		generalStatisticsPane.add(lblCategories, gbc_lblCategories);
		
		JLabel lblTotalOpenTasks = new JLabel("Total open tasks:");
		GridBagConstraints gbc_lblTotalOpenTasks = new GridBagConstraints();
		gbc_lblTotalOpenTasks.anchor = GridBagConstraints.WEST;
		gbc_lblTotalOpenTasks.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalOpenTasks.gridx = 1;
		gbc_lblTotalOpenTasks.gridy = 2;
		generalStatisticsPane.add(lblTotalOpenTasks, gbc_lblTotalOpenTasks);
		
		JLabel lblOpenTasksNumber = new JLabel("0");
		GridBagConstraints gbc_lblOpenTasksNumber = new GridBagConstraints();
		gbc_lblOpenTasksNumber.anchor = GridBagConstraints.WEST;
		gbc_lblOpenTasksNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblOpenTasksNumber.gridx = 2;
		gbc_lblOpenTasksNumber.gridy = 2;
		generalStatisticsPane.add(lblOpenTasksNumber, gbc_lblOpenTasksNumber);
		
		JLabel lblMostUsedCategory = new JLabel("Most used category:");
		GridBagConstraints gbc_lblMostUsedCategory = new GridBagConstraints();
		gbc_lblMostUsedCategory.anchor = GridBagConstraints.WEST;
		gbc_lblMostUsedCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblMostUsedCategory.gridx = 3;
		gbc_lblMostUsedCategory.gridy = 2;
		generalStatisticsPane.add(lblMostUsedCategory, gbc_lblMostUsedCategory);
		
		JLabel lblMostUsedCategoryName = new JLabel("xyz-category");
		GridBagConstraints gbc_lblMostUsedCategoryName = new GridBagConstraints();
		gbc_lblMostUsedCategoryName.anchor = GridBagConstraints.WEST;
		gbc_lblMostUsedCategoryName.insets = new Insets(0, 0, 5, 5);
		gbc_lblMostUsedCategoryName.gridx = 4;
		gbc_lblMostUsedCategoryName.gridy = 2;
		generalStatisticsPane.add(lblMostUsedCategoryName, gbc_lblMostUsedCategoryName);
		
		JLabel lblCompletedTasks = new JLabel("Total completed tasks:");
		GridBagConstraints gbc_lblCompletedTasks = new GridBagConstraints();
		gbc_lblCompletedTasks.anchor = GridBagConstraints.WEST;
		gbc_lblCompletedTasks.insets = new Insets(0, 0, 5, 5);
		gbc_lblCompletedTasks.gridx = 1;
		gbc_lblCompletedTasks.gridy = 3;
		generalStatisticsPane.add(lblCompletedTasks, gbc_lblCompletedTasks);
		
		JLabel lblCompletedTaskNumber = new JLabel("x");
		GridBagConstraints gbc_lblCompletedTaskNumber = new GridBagConstraints();
		gbc_lblCompletedTaskNumber.anchor = GridBagConstraints.WEST;
		gbc_lblCompletedTaskNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblCompletedTaskNumber.gridx = 2;
		gbc_lblCompletedTaskNumber.gridy = 3;
		generalStatisticsPane.add(lblCompletedTaskNumber, gbc_lblCompletedTaskNumber);
		lblCompletedTaskNumber.setText(""+historicTodoList.getCategoryMap().get(ToxicUIData.ALL_TASKS_TODOCATEGORY_KEY).getTasksHashMap().size());
		
		JLabel lblLeastUsedCategory = new JLabel("Least used category:");
		GridBagConstraints gbc_lblLeastUsedCategory = new GridBagConstraints();
		gbc_lblLeastUsedCategory.anchor = GridBagConstraints.WEST;
		gbc_lblLeastUsedCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblLeastUsedCategory.gridx = 3;
		gbc_lblLeastUsedCategory.gridy = 3;
		generalStatisticsPane.add(lblLeastUsedCategory, gbc_lblLeastUsedCategory);
		
		JLabel lblLeastUsedCategoryName = new JLabel("xyz-category");
		GridBagConstraints gbc_lblLeastUsedCategoryName = new GridBagConstraints();
		gbc_lblLeastUsedCategoryName.anchor = GridBagConstraints.WEST;
		gbc_lblLeastUsedCategoryName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLeastUsedCategoryName.gridx = 4;
		gbc_lblLeastUsedCategoryName.gridy = 3;
		generalStatisticsPane.add(lblLeastUsedCategoryName, gbc_lblLeastUsedCategoryName);
		
		JLabel lblNewLabel = new JLabel("This week:");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 5;
		generalStatisticsPane.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblOpendThisWeek = new JLabel("Opend this week:");
		GridBagConstraints gbc_lblOpendThisWeek = new GridBagConstraints();
		gbc_lblOpendThisWeek.anchor = GridBagConstraints.WEST;
		gbc_lblOpendThisWeek.insets = new Insets(0, 0, 5, 5);
		gbc_lblOpendThisWeek.gridx = 1;
		gbc_lblOpendThisWeek.gridy = 6;
		generalStatisticsPane.add(lblOpendThisWeek, gbc_lblOpendThisWeek);
		
		JLabel lblOpendThisWeekNumber = new JLabel("0");
		GridBagConstraints gbc_lblOpendThisWeekNumber = new GridBagConstraints();
		gbc_lblOpendThisWeekNumber.anchor = GridBagConstraints.WEST;
		gbc_lblOpendThisWeekNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblOpendThisWeekNumber.gridx = 2;
		gbc_lblOpendThisWeekNumber.gridy = 6;
		generalStatisticsPane.add(lblOpendThisWeekNumber, gbc_lblOpendThisWeekNumber);
		
		JLabel lblCompletedThisWeek = new JLabel("Completed this week:");
		GridBagConstraints gbc_lblCompletedThisWeek = new GridBagConstraints();
		gbc_lblCompletedThisWeek.anchor = GridBagConstraints.WEST;
		gbc_lblCompletedThisWeek.insets = new Insets(0, 0, 5, 5);
		gbc_lblCompletedThisWeek.gridx = 1;
		gbc_lblCompletedThisWeek.gridy = 7;
		generalStatisticsPane.add(lblCompletedThisWeek, gbc_lblCompletedThisWeek);
		
		JLabel lblCompletedThisWeekNumber = new JLabel("0");
		GridBagConstraints gbc_lblCompletedThisWeekNumber = new GridBagConstraints();
		gbc_lblCompletedThisWeekNumber.anchor = GridBagConstraints.WEST;
		gbc_lblCompletedThisWeekNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblCompletedThisWeekNumber.gridx = 2;
		gbc_lblCompletedThisWeekNumber.gridy = 7;
		generalStatisticsPane.add(lblCompletedThisWeekNumber, gbc_lblCompletedThisWeekNumber);
		
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
				setVisible(false);
				main.switchToTasks();
			}
        });
	}
}
