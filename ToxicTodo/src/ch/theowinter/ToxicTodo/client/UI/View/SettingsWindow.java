package ch.theowinter.ToxicTodo.client.UI.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class SettingsWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldHostIP;
	private JTextField textFieldHostPort;
	private JPasswordField passwordField;
	private JTextField txtFieldConsoleSize;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingsWindow frame = new SettingsWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SettingsWindow() {
		setTitle("Settings");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 376, 312);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setFont(new Font("Lucida Grande", Font.PLAIN, 23));
		panel.add(lblSettings);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{111, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{10, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblHostIP = new JLabel("Host-IP:");
		GridBagConstraints gbc_lblHostIP = new GridBagConstraints();
		gbc_lblHostIP.insets = new Insets(0, 0, 5, 5);
		gbc_lblHostIP.anchor = GridBagConstraints.EAST;
		gbc_lblHostIP.gridx = 0;
		gbc_lblHostIP.gridy = 1;
		panel_1.add(lblHostIP, gbc_lblHostIP);
		
		textFieldHostIP = new JTextField();
		textFieldHostIP.setToolTipText("The IP address of the computer where ToxicTodoServer is running. This can be localhost if you're running a local server.");
		GridBagConstraints gbc_textFieldHostIP = new GridBagConstraints();
		gbc_textFieldHostIP.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldHostIP.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldHostIP.gridx = 1;
		gbc_textFieldHostIP.gridy = 1;
		panel_1.add(textFieldHostIP, gbc_textFieldHostIP);
		textFieldHostIP.setColumns(10);
		
		JLabel lblHostport = new JLabel("Host-Port:");
		GridBagConstraints gbc_lblHostport = new GridBagConstraints();
		gbc_lblHostport.insets = new Insets(0, 0, 5, 5);
		gbc_lblHostport.anchor = GridBagConstraints.EAST;
		gbc_lblHostport.gridx = 0;
		gbc_lblHostport.gridy = 2;
		panel_1.add(lblHostport, gbc_lblHostport);
		
		textFieldHostPort = new JTextField();
		textFieldHostPort.setToolTipText("The default port of ToxicTodo is 5222.");
		GridBagConstraints gbc_textFieldHostPort = new GridBagConstraints();
		gbc_textFieldHostPort.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldHostPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldHostPort.gridx = 1;
		gbc_textFieldHostPort.gridy = 2;
		panel_1.add(textFieldHostPort, gbc_textFieldHostPort);
		textFieldHostPort.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 3;
		panel_1.add(lblPassword, gbc_lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setToolTipText("All communication between server and client gets AES-128 encrypted based on this password. Be sure to chose a good one or your communication can be intercepted.");
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 3;
		panel_1.add(passwordField, gbc_passwordField);
		
		JLabel lblConsolesize = new JLabel("Console-Size:");
		GridBagConstraints gbc_lblConsolesize = new GridBagConstraints();
		gbc_lblConsolesize.insets = new Insets(0, 0, 0, 5);
		gbc_lblConsolesize.anchor = GridBagConstraints.EAST;
		gbc_lblConsolesize.gridx = 0;
		gbc_lblConsolesize.gridy = 5;
		panel_1.add(lblConsolesize, gbc_lblConsolesize);
		
		txtFieldConsoleSize = new JTextField();
		txtFieldConsoleSize.setToolTipText("If you're using ToxicTodo in CLI mode enter the default size of your console window. This is used to make correct line-breaks.");
		GridBagConstraints gbc_txtFieldConsoleSize = new GridBagConstraints();
		gbc_txtFieldConsoleSize.insets = new Insets(0, 0, 0, 5);
		gbc_txtFieldConsoleSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFieldConsoleSize.gridx = 1;
		gbc_txtFieldConsoleSize.gridy = 5;
		panel_1.add(txtFieldConsoleSize, gbc_txtFieldConsoleSize);
		txtFieldConsoleSize.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_2.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel_2, BorderLayout.SOUTH);
		
		JButton btnCancel = new JButton("cancel");
		panel_2.add(btnCancel);
		
		JButton btnSave = new JButton("save");
		panel_2.add(btnSave);
	}

}
