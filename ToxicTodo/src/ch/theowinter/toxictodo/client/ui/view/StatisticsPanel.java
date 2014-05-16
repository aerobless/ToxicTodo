package ch.theowinter.toxictodo.client.ui.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ch.theowinter.toxictodo.client.ClientTodoManager;
import ch.theowinter.toxictodo.client.ui.view.utilities.PanelHeaderWhite;
import ch.theowinter.toxictodo.client.ui.view.utilities.ToxicColors;
import ch.theowinter.toxictodo.sharedobjects.Logger;

public class StatisticsPanel extends JPanel {
	private static final long serialVersionUID = -2022909795010691054L;
	private MainWindow main;
	private ClientTodoManager todoManager;

	/**
	 * Create the frame.
	 */
	public StatisticsPanel(MainWindow main, ClientTodoManager todoManager) {
		this.main = main;
		this.todoManager = todoManager;
		setBackground(ToxicColors.SOFT_GREY);
		setBounds(100, 100, 515, 381);
		setBorder(null);
		setLayout(new BorderLayout(0, 0));
		
		PanelHeaderWhite header = new PanelHeaderWhite();
		header.setTitel("Statistics");
		header.setSubTitel("Track your progress when completing tasks.");
		header.setIcon('\uf080');

		add(header, BorderLayout.NORTH);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
		
		JPanel generalStatisticsPane = new JPanel();
		tabbedPane.addTab("General Statistics", null, generalStatisticsPane, null);
		generalStatisticsPane.setBackground(ToxicColors.SOFT_GREY);
		GridBagLayout gbl_generalStatisticsPane = new GridBagLayout();
		gbl_generalStatisticsPane.columnWidths = new int[]{182, 0, 0, 0};
		gbl_generalStatisticsPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_generalStatisticsPane.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_generalStatisticsPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
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
		
		JLabel lblTasksCompletedThisMonth = new JLabel("Completed this month:");
		GridBagConstraints gbc_lblTasksCompletedThisMonth = new GridBagConstraints();
		gbc_lblTasksCompletedThisMonth.anchor = GridBagConstraints.EAST;
		gbc_lblTasksCompletedThisMonth.insets = new Insets(0, 0, 5, 5);
		gbc_lblTasksCompletedThisMonth.gridx = 0;
		gbc_lblTasksCompletedThisMonth.gridy = 2;
		generalStatisticsPane.add(lblTasksCompletedThisMonth, gbc_lblTasksCompletedThisMonth);
		
		JPanel additionalStats = new JPanel();
		tabbedPane.addTab("Detailed Stats", null, additionalStats, null);
		
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
				//TODO: cancel logic.
				Logger.log("Functionality not implemented yet.");
			}
        });
	}
}
