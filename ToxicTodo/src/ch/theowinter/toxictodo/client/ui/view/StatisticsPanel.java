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
		GridBagLayout gbl_generalStatisticsPane = new GridBagLayout();
		gbl_generalStatisticsPane.columnWidths = new int[]{182, 0, 0, 0};
		gbl_generalStatisticsPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_generalStatisticsPane.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_generalStatisticsPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		generalStatisticsPane.setLayout(gbl_generalStatisticsPane);
		
		JLabel spacer = new JLabel(" ");
		GridBagConstraints gbcSpacer = new GridBagConstraints();
		gbcSpacer.insets = new Insets(0, 0, 5, 5);
		gbcSpacer.gridx = 0;
		gbcSpacer.gridy = 0;
		generalStatisticsPane.add(spacer, gbcSpacer);
		
		JLabel lblTotalCompletedTasks = new JLabel("Total completed tasks:");
		GridBagConstraints gbc_lblTotalCompletedTasks = new GridBagConstraints();
		gbc_lblTotalCompletedTasks.anchor = GridBagConstraints.EAST;
		gbc_lblTotalCompletedTasks.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalCompletedTasks.gridx = 0;
		gbc_lblTotalCompletedTasks.gridy = 1;
		generalStatisticsPane.add(lblTotalCompletedTasks, gbc_lblTotalCompletedTasks);
		
		JLabel lblCompletedTaskAmount = new JLabel("x");
		GridBagConstraints gbc_lblCompletedTaskAmount = new GridBagConstraints();
		gbc_lblCompletedTaskAmount.anchor = GridBagConstraints.WEST;
		gbc_lblCompletedTaskAmount.insets = new Insets(0, 0, 5, 5);
		gbc_lblCompletedTaskAmount.gridx = 1;
		gbc_lblCompletedTaskAmount.gridy = 1;
		generalStatisticsPane.add(lblCompletedTaskAmount, gbc_lblCompletedTaskAmount);
		lblCompletedTaskAmount.setText(""+historicTodoList.getCategoryMap().get(ToxicUIData.allTaskTodoCategoryKey).getTasksHashMap().size());
		
		JLabel lblTasksCompletedThisMonth = new JLabel("Completed this month:");
		GridBagConstraints gbc_lblTasksCompletedThisMonth = new GridBagConstraints();
		gbc_lblTasksCompletedThisMonth.anchor = GridBagConstraints.EAST;
		gbc_lblTasksCompletedThisMonth.insets = new Insets(0, 0, 5, 5);
		gbc_lblTasksCompletedThisMonth.gridx = 0;
		gbc_lblTasksCompletedThisMonth.gridy = 2;
		generalStatisticsPane.add(lblTasksCompletedThisMonth, gbc_lblTasksCompletedThisMonth);
		
		JLabel lblCompletedTasksThisMonthAmount = new JLabel("y");
		GridBagConstraints gbc_lblCompletedTasksThisMonthAmount = new GridBagConstraints();
		gbc_lblCompletedTasksThisMonthAmount.anchor = GridBagConstraints.WEST;
		gbc_lblCompletedTasksThisMonthAmount.insets = new Insets(0, 0, 5, 5);
		gbc_lblCompletedTasksThisMonthAmount.gridx = 1;
		gbc_lblCompletedTasksThisMonthAmount.gridy = 2;
		generalStatisticsPane.add(lblCompletedTasksThisMonthAmount, gbc_lblCompletedTasksThisMonthAmount);
		
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
	
	public void updateHistoricTodoManager(){
		
	}
}
