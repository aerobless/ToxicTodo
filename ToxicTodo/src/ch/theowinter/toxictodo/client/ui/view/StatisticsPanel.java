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
		GridBagLayout gblGeneralStatisticsPane = new GridBagLayout();
		gblGeneralStatisticsPane.columnWidths = new int[]{182, 0, 0, 0};
		gblGeneralStatisticsPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gblGeneralStatisticsPane.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gblGeneralStatisticsPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		generalStatisticsPane.setLayout(gblGeneralStatisticsPane);
		
		JLabel spacer = new JLabel(" ");
		GridBagConstraints gbcSpacer = new GridBagConstraints();
		gbcSpacer.insets = new Insets(0, 0, 5, 5);
		gbcSpacer.gridx = 0;
		gbcSpacer.gridy = 0;
		generalStatisticsPane.add(spacer, gbcSpacer);
		
		JLabel lblTotalCompletedTasks = new JLabel("Total completed tasks:");
		GridBagConstraints gbcLblTotalCompletedTasks = new GridBagConstraints();
		gbcLblTotalCompletedTasks.anchor = GridBagConstraints.EAST;
		gbcLblTotalCompletedTasks.insets = new Insets(0, 0, 5, 5);
		gbcLblTotalCompletedTasks.gridx = 0;
		gbcLblTotalCompletedTasks.gridy = 1;
		generalStatisticsPane.add(lblTotalCompletedTasks, gbcLblTotalCompletedTasks);
		
		JLabel lblCompletedTaskAmount = new JLabel("x");
		GridBagConstraints gbcLblCompletedTaskAmount = new GridBagConstraints();
		gbcLblCompletedTaskAmount.anchor = GridBagConstraints.WEST;
		gbcLblCompletedTaskAmount.insets = new Insets(0, 0, 5, 5);
		gbcLblCompletedTaskAmount.gridx = 1;
		gbcLblCompletedTaskAmount.gridy = 1;
		generalStatisticsPane.add(lblCompletedTaskAmount, gbcLblCompletedTaskAmount);
		lblCompletedTaskAmount.setText(""+historicTodoList.getCategoryMap().get(ToxicUIData.allTaskTodoCategoryKey).getTasksHashMap().size());
		
		JLabel lblTasksCompletedThisMonth = new JLabel("Completed this month:");
		GridBagConstraints gbcLblTasksCompletedThisMonth = new GridBagConstraints();
		gbcLblTasksCompletedThisMonth.anchor = GridBagConstraints.EAST;
		gbcLblTasksCompletedThisMonth.insets = new Insets(0, 0, 5, 5);
		gbcLblTasksCompletedThisMonth.gridx = 0;
		gbcLblTasksCompletedThisMonth.gridy = 2;
		generalStatisticsPane.add(lblTasksCompletedThisMonth, gbcLblTasksCompletedThisMonth);
		
		JLabel lblCompletedTasksThisMonthAmount = new JLabel("y");
		GridBagConstraints gbcLblCompletedTasksThisMonthAmount = new GridBagConstraints();
		gbcLblCompletedTasksThisMonthAmount.anchor = GridBagConstraints.WEST;
		gbcLblCompletedTasksThisMonthAmount.insets = new Insets(0, 0, 5, 5);
		gbcLblCompletedTasksThisMonthAmount.gridx = 1;
		gbcLblCompletedTasksThisMonthAmount.gridy = 2;
		generalStatisticsPane.add(lblCompletedTasksThisMonthAmount, gbcLblCompletedTasksThisMonthAmount);
		
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
