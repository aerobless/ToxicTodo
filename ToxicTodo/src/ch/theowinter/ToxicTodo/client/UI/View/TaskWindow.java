package ch.theowinter.ToxicTodo.client.UI.View;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextPane;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class TaskWindow extends JFrame {
	private static final long serialVersionUID = -2022909795010691054L;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public TaskWindow() {
		setTitle("ToxicTask");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) topPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(topPanel, BorderLayout.NORTH);
		
		JLabel lblIcon = new JLabel("(-)");
		lblIcon.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		topPanel.add(lblIcon);
		
		JLabel lblTitel = new JLabel("Task");
		lblTitel.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		topPanel.add(lblTitel);
		
		JPanel centerPanel = new JPanel();
		contentPane.add(centerPanel, BorderLayout.CENTER);
		GridBagLayout gbl_centerPanel = new GridBagLayout();
		gbl_centerPanel.columnWidths = new int[]{124, 0, 0, 0};
		gbl_centerPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_centerPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_centerPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		centerPanel.setLayout(gbl_centerPanel);
		
		JLabel lblType = new JLabel("Type:");
		GridBagConstraints gbc_lblType = new GridBagConstraints();
		gbc_lblType.anchor = GridBagConstraints.EAST;
		gbc_lblType.insets = new Insets(0, 0, 5, 5);
		gbc_lblType.gridx = 0;
		gbc_lblType.gridy = 1;
		centerPanel.add(lblType, gbc_lblType);
		
		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		centerPanel.add(comboBox, gbc_comboBox);
		
		JLabel lblCompletedUntil = new JLabel("Completed until:");
		GridBagConstraints gbc_lblCompletedUntil = new GridBagConstraints();
		gbc_lblCompletedUntil.anchor = GridBagConstraints.EAST;
		gbc_lblCompletedUntil.insets = new Insets(0, 0, 5, 5);
		gbc_lblCompletedUntil.gridx = 0;
		gbc_lblCompletedUntil.gridy = 2;
		centerPanel.add(lblCompletedUntil, gbc_lblCompletedUntil);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		centerPanel.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description:");
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 3;
		centerPanel.add(lblDescription, gbc_lblDescription);
		
		JTextPane textPane = new JTextPane();
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.insets = new Insets(0, 0, 5, 5);
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.gridx = 1;
		gbc_textPane.gridy = 3;
		centerPanel.add(textPane, gbc_textPane);
		
		JPanel bottomPanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) bottomPanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		
		JButton btnCancel = new JButton("Cancel");
		bottomPanel.add(btnCancel);
		
		JButton btnSave = new JButton("Save");
		bottomPanel.add(btnSave);
	}
}
