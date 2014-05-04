package ch.theowinter.toxictodo.client.ui.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import ch.theowinter.toxictodo.client.ClientSettings;
import ch.theowinter.toxictodo.client.ui.view.utilities.ToxicColors;
import ch.theowinter.toxictodo.sharedobjects.Logger;

public class SettingsPanel extends JPanel{
	private static final long serialVersionUID = 5091902326508795291L;
	
	private JTextField textFieldHostIP;
	private JTextField textFieldHostPort;
	private JPasswordField passwordField;
	private JTextField txtFieldConsoleSize;
	private ClientSettings settings;
	private MainWindow main;
	
	/**
	 * Create the frame.
	 */
	public SettingsPanel(ClientSettings settings, MainWindow main) {
		this.main = main;
		this.settings = settings;
		setBackground(ToxicColors.softGrey);
		setBorder(new EmptyBorder(5, 5, 5, 5));

		setLayout(new BorderLayout(0, 0));
		
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(ToxicColors.softGrey);
		FlowLayout flowLayout = (FlowLayout) headerPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(headerPanel, BorderLayout.NORTH);
		
		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setFont(new Font("Lucida Grande", Font.PLAIN, 23));
		headerPanel.add(lblSettings);
		
		JPanel centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setBackground(ToxicColors.softGrey);
		GridBagLayout settingsGridBag = new GridBagLayout();
		settingsGridBag.columnWidths = new int[]{111, 0, 0, 0};
		settingsGridBag.rowHeights = new int[]{10, 0, 0, 0, 0, 0, 0};
		settingsGridBag.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		settingsGridBag.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		centerPanel.setLayout(settingsGridBag);
		
		//HOST IP:
		JLabel lblHostIP = new JLabel("Host-IP:");
		String hostTooltip = "The IP address of the computer where ToxicTodoServer is running. This can be localhost if you're running a local server.";
		lblHostIP.setToolTipText(hostTooltip);
		GridBagConstraints gbc_lblHostIP = new GridBagConstraints();
		gbc_lblHostIP.insets = new Insets(0, 0, 5, 5);
		gbc_lblHostIP.anchor = GridBagConstraints.EAST;
		gbc_lblHostIP.gridx = 0;
		gbc_lblHostIP.gridy = 1;
		centerPanel.add(lblHostIP, gbc_lblHostIP);
		textFieldHostIP = new JTextField();
		textFieldHostIP.setToolTipText(hostTooltip);
		GridBagConstraints gbc_textFieldHostIP = new GridBagConstraints();
		gbc_textFieldHostIP.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldHostIP.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldHostIP.gridx = 1;
		gbc_textFieldHostIP.gridy = 1;
		centerPanel.add(textFieldHostIP, gbc_textFieldHostIP);
		textFieldHostIP.setColumns(10);
		textFieldHostIP.setText(settings.getHOST());

		//HOST PORT:
		JLabel lblHostport = new JLabel("Host-Port:");
		String hostPortTooltip = "The default port of ToxicTodo is 5222.";
		lblHostport.setToolTipText(hostPortTooltip);
		GridBagConstraints gbc_lblHostport = new GridBagConstraints();
		gbc_lblHostport.insets = new Insets(0, 0, 5, 5);
		gbc_lblHostport.anchor = GridBagConstraints.EAST;
		gbc_lblHostport.gridx = 0;
		gbc_lblHostport.gridy = 2;
		centerPanel.add(lblHostport, gbc_lblHostport);
		textFieldHostPort = new JTextField();
		textFieldHostPort.setToolTipText(hostPortTooltip);
		GridBagConstraints gbc_textFieldHostPort = new GridBagConstraints();
		gbc_textFieldHostPort.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldHostPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldHostPort.gridx = 1;
		gbc_textFieldHostPort.gridy = 2;
		centerPanel.add(textFieldHostPort, gbc_textFieldHostPort);
		textFieldHostPort.setColumns(10);
		textFieldHostPort.setText(settings.getPORT()+"");
		
		//HOST PASSWORD:
		JLabel lblPassword = new JLabel("Password:");
		String passwordTooltop = "All communication between server and client gets AES-128 encrypted based on this password.";
		lblPassword.setToolTipText(passwordTooltop);
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 3;
		centerPanel.add(lblPassword, gbc_lblPassword);
		passwordField = new JPasswordField();
		passwordField.setToolTipText(passwordTooltop);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 3;
		centerPanel.add(passwordField, gbc_passwordField);
		passwordField.setText(settings.getPassword());
		
		//CONSOLE SIZE:
		JLabel lblConsoleSize = new JLabel("Console-Size:");
		String consoleSizeTooltip = "If you're using ToxicTodo in CLI mode enter the default size of your console window. This is used to make correct line-breaks.";
		lblConsoleSize.setToolTipText(consoleSizeTooltip);
		GridBagConstraints gbc_lblConsolesize = new GridBagConstraints();
		gbc_lblConsolesize.insets = new Insets(0, 0, 0, 5);
		gbc_lblConsolesize.anchor = GridBagConstraints.EAST;
		gbc_lblConsolesize.gridx = 0;
		gbc_lblConsolesize.gridy = 5;
		centerPanel.add(lblConsoleSize, gbc_lblConsolesize);
		txtFieldConsoleSize = new JTextField();
		txtFieldConsoleSize.setToolTipText(consoleSizeTooltip);
		GridBagConstraints gbc_txtFieldConsoleSize = new GridBagConstraints();
		gbc_txtFieldConsoleSize.insets = new Insets(0, 0, 0, 5);
		gbc_txtFieldConsoleSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFieldConsoleSize.gridx = 1;
		gbc_txtFieldConsoleSize.gridy = 5;
		centerPanel.add(txtFieldConsoleSize, gbc_txtFieldConsoleSize);
		txtFieldConsoleSize.setColumns(10);
		txtFieldConsoleSize.setText(settings.getConsoleSize()+"");
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(ToxicColors.softGrey);
		FlowLayout flowLayout_1 = (FlowLayout) bottomPanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		add(bottomPanel, BorderLayout.SOUTH);
		
		//BUTTONS
		JButton btnCancel = new JButton("cancel");
		bottomPanel.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelChanges();
			}
        }); 
		
		JButton btnSave = new JButton("save");
		bottomPanel.add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveChanges();
			}
        }); 
	}
	
	private void saveChanges(){
		try{
			settings.setHOST(textFieldHostIP.getText());
			int port =  Integer.parseInt(textFieldHostPort.getText());
			settings.setPORT(port);
			int consoleSize = Integer.parseInt(txtFieldConsoleSize.getText());
			settings.setConsoleSize(consoleSize);
			settings.setPassword(new String(passwordField.getPassword()));
			settings.saveSettingsToDisk();
			setVisible(false);
			main.switchToTasks();
		} catch (IllegalArgumentException e){
			Logger.log("bad input");
			//TODO: warn user or something with a window or red colored input box
		}
	}
	
	private void cancelChanges(){
		textFieldHostIP.setText(settings.getHOST());
		textFieldHostPort.setText(settings.getPORT()+"");
		passwordField.setText(settings.getPassword());
		txtFieldConsoleSize.setText(settings.getConsoleSize()+"");
		setVisible(false);
		main.switchToTasks();
	}
}
