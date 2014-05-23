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
import ch.theowinter.toxictodo.client.ui.view.utilities.PanelHeader;
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
		
		PanelHeader header = new PanelHeader();
		header.setTitel("Statistics");
		header.setSubTitel("Track your progress when completing tasks.");
		header.setIcon('\uf080');
		add(header, BorderLayout.NORTH);
		
		JPanel generalStatisticsPane = new JPanel();
		add(generalStatisticsPane, BorderLayout.CENTER);
		generalStatisticsPane.setBackground(ToxicColors.SOFT_GREY);
		GridBagLayout gblGeneralStatisticsPane = new GridBagLayout();
		gblGeneralStatisticsPane.columnWidths = new int[]{20, 161, 67, 149, 122, 0, 0};
		gblGeneralStatisticsPane.rowHeights = new int[]{20, 0, 0, 0, 20, 0, 0, 0, 0, 0};
		gblGeneralStatisticsPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gblGeneralStatisticsPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		generalStatisticsPane.setLayout(gblGeneralStatisticsPane);
		
		JLabel lblGeneral = new JLabel("General:");
		lblGeneral.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbcLblGeneral = new GridBagConstraints();
		gbcLblGeneral.anchor = GridBagConstraints.WEST;
		gbcLblGeneral.insets = new Insets(0, 0, 5, 5);
		gbcLblGeneral.gridx = 1;
		gbcLblGeneral.gridy = 1;
		generalStatisticsPane.add(lblGeneral, gbcLblGeneral);
		
		JLabel lblCategories = new JLabel("Categories:");
		lblCategories.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbcLblCategories = new GridBagConstraints();
		gbcLblCategories.anchor = GridBagConstraints.WEST;
		gbcLblCategories.insets = new Insets(0, 0, 5, 5);
		gbcLblCategories.gridx = 3;
		gbcLblCategories.gridy = 1;
		generalStatisticsPane.add(lblCategories, gbcLblCategories);
		
		JLabel lblTotalOpenTasks = new JLabel("Total open tasks:");
		GridBagConstraints gbcLblTotalOpenTasks = new GridBagConstraints();
		gbcLblTotalOpenTasks.anchor = GridBagConstraints.WEST;
		gbcLblTotalOpenTasks.insets = new Insets(0, 0, 5, 5);
		gbcLblTotalOpenTasks.gridx = 1;
		gbcLblTotalOpenTasks.gridy = 2;
		generalStatisticsPane.add(lblTotalOpenTasks, gbcLblTotalOpenTasks);
		
		JLabel lblOpenTasksNumber = new JLabel(""+todoManager.getTodoList().getCategoryMap().get(ToxicUIData.ALL_TASKS_TODOCATEGORY_KEY).getTasksHashMap().size());
		GridBagConstraints gbcLblOpenTasksNumber = new GridBagConstraints();
		gbcLblOpenTasksNumber.anchor = GridBagConstraints.WEST;
		gbcLblOpenTasksNumber.insets = new Insets(0, 0, 5, 5);
		gbcLblOpenTasksNumber.gridx = 2;
		gbcLblOpenTasksNumber.gridy = 2;
		generalStatisticsPane.add(lblOpenTasksNumber, gbcLblOpenTasksNumber);
		
		JLabel lblMostUsedCategory = new JLabel("Most used category:");
		GridBagConstraints gbcLblMostUsedCategory = new GridBagConstraints();
		gbcLblMostUsedCategory.anchor = GridBagConstraints.WEST;
		gbcLblMostUsedCategory.insets = new Insets(0, 0, 5, 5);
		gbcLblMostUsedCategory.gridx = 3;
		gbcLblMostUsedCategory.gridy = 2;
		generalStatisticsPane.add(lblMostUsedCategory, gbcLblMostUsedCategory);
		
		JLabel lblMostUsedCategoryName = new JLabel("xyz-category");
		GridBagConstraints gbc_lblMostUsedCategoryName = new GridBagConstraints();
		gbc_lblMostUsedCategoryName.anchor = GridBagConstraints.WEST;
		gbc_lblMostUsedCategoryName.insets = new Insets(0, 0, 5, 5);
		gbc_lblMostUsedCategoryName.gridx = 4;
		gbc_lblMostUsedCategoryName.gridy = 2;
		generalStatisticsPane.add(lblMostUsedCategoryName, gbc_lblMostUsedCategoryName);
		
		JLabel lblCompletedTasks = new JLabel("Total completed tasks:");
		GridBagConstraints gbcLblCompletedTasks = new GridBagConstraints();
		gbcLblCompletedTasks.anchor = GridBagConstraints.WEST;
		gbcLblCompletedTasks.insets = new Insets(0, 0, 5, 5);
		gbcLblCompletedTasks.gridx = 1;
		gbcLblCompletedTasks.gridy = 3;
		generalStatisticsPane.add(lblCompletedTasks, gbcLblCompletedTasks);
		
		JLabel lblCompletedTaskNumber = new JLabel(""+historicTodoList.getCategoryMap().get(ToxicUIData.ALL_TASKS_TODOCATEGORY_KEY).getTasksHashMap().size());
		GridBagConstraints gbcLblCompletedTaskNumber = new GridBagConstraints();
		gbcLblCompletedTaskNumber.anchor = GridBagConstraints.WEST;
		gbcLblCompletedTaskNumber.insets = new Insets(0, 0, 5, 5);
		gbcLblCompletedTaskNumber.gridx = 2;
		gbcLblCompletedTaskNumber.gridy = 3;
		generalStatisticsPane.add(lblCompletedTaskNumber, gbcLblCompletedTaskNumber);
		
		JLabel lblLeastUsedCategory = new JLabel("Least used category:");
		GridBagConstraints gbcLblLeastUsedCategory = new GridBagConstraints();
		gbcLblLeastUsedCategory.anchor = GridBagConstraints.WEST;
		gbcLblLeastUsedCategory.insets = new Insets(0, 0, 5, 5);
		gbcLblLeastUsedCategory.gridx = 3;
		gbcLblLeastUsedCategory.gridy = 3;
		generalStatisticsPane.add(lblLeastUsedCategory, gbcLblLeastUsedCategory);
		
		JLabel lblLeastUsedCategoryName = new JLabel("xyz-category");
		GridBagConstraints gbcLblLeastUsedCategoryName = new GridBagConstraints();
		gbcLblLeastUsedCategoryName.anchor = GridBagConstraints.WEST;
		gbcLblLeastUsedCategoryName.insets = new Insets(0, 0, 5, 5);
		gbcLblLeastUsedCategoryName.gridx = 4;
		gbcLblLeastUsedCategoryName.gridy = 3;
		generalStatisticsPane.add(lblLeastUsedCategoryName, gbcLblLeastUsedCategoryName);
		
		JLabel lblNewLabel = new JLabel("This week:");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbcLblNewLabel = new GridBagConstraints();
		gbcLblNewLabel.anchor = GridBagConstraints.WEST;
		gbcLblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbcLblNewLabel.gridx = 1;
		gbcLblNewLabel.gridy = 5;
		generalStatisticsPane.add(lblNewLabel, gbcLblNewLabel);
		
		JLabel lblOpendThisWeek = new JLabel("Opend this week:");
		GridBagConstraints gbcLblOpendThisWeek = new GridBagConstraints();
		gbcLblOpendThisWeek.anchor = GridBagConstraints.WEST;
		gbcLblOpendThisWeek.insets = new Insets(0, 0, 5, 5);
		gbcLblOpendThisWeek.gridx = 1;
		gbcLblOpendThisWeek.gridy = 6;
		generalStatisticsPane.add(lblOpendThisWeek, gbcLblOpendThisWeek);
		
		JLabel lblOpendThisWeekNumber = new JLabel("0");
		GridBagConstraints gbcLblOpendThisWeekNumber = new GridBagConstraints();
		gbcLblOpendThisWeekNumber.anchor = GridBagConstraints.WEST;
		gbcLblOpendThisWeekNumber.insets = new Insets(0, 0, 5, 5);
		gbcLblOpendThisWeekNumber.gridx = 2;
		gbcLblOpendThisWeekNumber.gridy = 6;
		generalStatisticsPane.add(lblOpendThisWeekNumber, gbcLblOpendThisWeekNumber);
		
		JLabel lblCompletedThisWeek = new JLabel("Completed this week:");
		GridBagConstraints gbcLblCompletedThisWeek = new GridBagConstraints();
		gbcLblCompletedThisWeek.anchor = GridBagConstraints.WEST;
		gbcLblCompletedThisWeek.insets = new Insets(0, 0, 5, 5);
		gbcLblCompletedThisWeek.gridx = 1;
		gbcLblCompletedThisWeek.gridy = 7;
		generalStatisticsPane.add(lblCompletedThisWeek, gbcLblCompletedThisWeek);
		
		JLabel lblCompletedThisWeekNumber = new JLabel("0");
		GridBagConstraints gbcLblCompletedThisWeekNumber = new GridBagConstraints();
		gbcLblCompletedThisWeekNumber.anchor = GridBagConstraints.WEST;
		gbcLblCompletedThisWeekNumber.insets = new Insets(0, 0, 5, 5);
		gbcLblCompletedThisWeekNumber.gridx = 2;
		gbcLblCompletedThisWeekNumber.gridy = 7;
		generalStatisticsPane.add(lblCompletedThisWeekNumber, gbcLblCompletedThisWeekNumber);
		
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
