package ch.theowinter.toxictodo.client.ui.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ch.theowinter.toxictodo.client.ClientSettings;
import ch.theowinter.toxictodo.client.ui.view.utilities.PanelHeader;
import ch.theowinter.toxictodo.client.ui.view.utilities.ToxicColors;
import ch.theowinter.toxictodo.sharedobjects.Logger;

public class SettingsPanel extends JPanel{
	private static final long serialVersionUID = 5091902326508795291L;
	
	private JTextField textFieldHostIP;
	private JTextField textFieldHostPort;
	private JPasswordField passwordField;
	private JTextField txtFieldConsoleSize;
	private ClientSettings settings;
	private JCheckBox checkboxInternalServerEnabled;
	private MainWindow main;
	
	/**
	 * Create the frame.
	 */
	public SettingsPanel(ClientSettings settings, MainWindow main) {
		this.main = main;
		this.settings = settings;
		setBackground(ToxicColors.SOFT_GREY);
		setBorder(new EmptyBorder(0, 0, 0, 0));

		setLayout(new BorderLayout(0, 0));
		
		//Header:
		PanelHeader header = new PanelHeader();
		add(header, BorderLayout.NORTH);
		header.setTitel("Settings");
		header.setSubTitel("Personalize ToxicTodo to your liking!");
		header.setIcon('\uf085');
		
		JPanel centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setBackground(ToxicColors.SOFT_GREY);
		GridBagLayout settingsGridBag = new GridBagLayout();
		settingsGridBag.columnWidths = new int[]{156, 0, 0, 0};
		settingsGridBag.rowHeights = new int[]{10, 0, 0, 0, 0, 0, 0, 0};
		settingsGridBag.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		settingsGridBag.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		centerPanel.setLayout(settingsGridBag);
		
		//Internal Server Checkbox:
		JLabel lblUseInternalServer = new JLabel("Use internal server:");
		String useInternalServerTooltip = "A local todo-server is started when ToxicTodo is launched and shutdown when ToxicTodo is closed.";
		lblUseInternalServer.setToolTipText(useInternalServerTooltip);
		GridBagConstraints gbcLblUseInternalServer = new GridBagConstraints();
		gbcLblUseInternalServer.anchor = GridBagConstraints.EAST;
		gbcLblUseInternalServer.insets = new Insets(0, 0, 5, 5);
		gbcLblUseInternalServer.gridx = 0;
		gbcLblUseInternalServer.gridy = 1;
		centerPanel.add(lblUseInternalServer, gbcLblUseInternalServer);
		
		checkboxInternalServerEnabled = new JCheckBox("");
		checkboxInternalServerEnabled.setToolTipText(useInternalServerTooltip);
		
		GridBagConstraints gbcCheckboxInternalServerEnabled = new GridBagConstraints();
		gbcCheckboxInternalServerEnabled.anchor = GridBagConstraints.WEST;
		gbcCheckboxInternalServerEnabled.insets = new Insets(0, 0, 5, 5);
		gbcCheckboxInternalServerEnabled.gridx = 1;
		gbcCheckboxInternalServerEnabled.gridy = 1;
		centerPanel.add(checkboxInternalServerEnabled, gbcCheckboxInternalServerEnabled);
		checkboxInternalServerEnabled.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent aE) {
				if(checkboxInternalServerEnabled.isSelected()){
					textFieldHostIP.setEnabled(false);
					textFieldHostPort.setEnabled(false);
					passwordField.setEnabled(false);
				}else{
					textFieldHostIP.setEnabled(true);
					textFieldHostPort.setEnabled(true);
					passwordField.setEnabled(true);
				}
			}
        });
		
		//HOST IP:
		JLabel lblHostIP = new JLabel("Host-IP:");
		String hostTooltip = "The IP address of the computer where ToxicTodoServer is running. This can be localhost if you're running a local server.";
		
		lblHostIP.setToolTipText(hostTooltip);
		GridBagConstraints gbcLblHostIP = new GridBagConstraints();
		gbcLblHostIP.insets = new Insets(0, 0, 5, 5);
		gbcLblHostIP.anchor = GridBagConstraints.EAST;
		gbcLblHostIP.gridx = 0;
		gbcLblHostIP.gridy = 2;
		centerPanel.add(lblHostIP, gbcLblHostIP);
		textFieldHostIP = new JTextField();
		textFieldHostIP.setToolTipText(hostTooltip);
		GridBagConstraints gbcTextFieldHostIP = new GridBagConstraints();
		gbcTextFieldHostIP.insets = new Insets(0, 0, 5, 5);
		gbcTextFieldHostIP.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldHostIP.gridx = 1;
		gbcTextFieldHostIP.gridy = 2;
		centerPanel.add(textFieldHostIP, gbcTextFieldHostIP);
		textFieldHostIP.setColumns(10);
		textFieldHostIP.setText(settings.getHOST());

		//HOST PORT:
		JLabel lblHostport = new JLabel("Host-Port:");
		String hostPortTooltip = "The default port of ToxicTodo is 5222.";
		lblHostport.setToolTipText(hostPortTooltip);
		GridBagConstraints gbcLblHostport = new GridBagConstraints();
		gbcLblHostport.insets = new Insets(0, 0, 5, 5);
		gbcLblHostport.anchor = GridBagConstraints.EAST;
		gbcLblHostport.gridx = 0;
		gbcLblHostport.gridy = 3;
		centerPanel.add(lblHostport, gbcLblHostport);
		textFieldHostPort = new JTextField();
		textFieldHostPort.setToolTipText(hostPortTooltip);
		GridBagConstraints gbcTextFieldHostPort = new GridBagConstraints();
		gbcTextFieldHostPort.insets = new Insets(0, 0, 5, 5);
		gbcTextFieldHostPort.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldHostPort.gridx = 1;
		gbcTextFieldHostPort.gridy = 3;
		centerPanel.add(textFieldHostPort, gbcTextFieldHostPort);
		textFieldHostPort.setColumns(10);
		textFieldHostPort.setText(settings.getPORT()+"");
		
		//HOST PASSWORD:
		JLabel lblPassword = new JLabel("Password:");
		String passwordTooltop = "All communication between server and client gets AES-128 encrypted based on this password.";
		lblPassword.setToolTipText(passwordTooltop);
		GridBagConstraints gbcLblPassword = new GridBagConstraints();
		gbcLblPassword.insets = new Insets(0, 0, 5, 5);
		gbcLblPassword.anchor = GridBagConstraints.EAST;
		gbcLblPassword.gridx = 0;
		gbcLblPassword.gridy = 4;
		centerPanel.add(lblPassword, gbcLblPassword);
		passwordField = new JPasswordField();
		passwordField.setToolTipText(passwordTooltop);
		GridBagConstraints gbcPasswordField = new GridBagConstraints();
		gbcPasswordField.insets = new Insets(0, 0, 5, 5);
		gbcPasswordField.fill = GridBagConstraints.HORIZONTAL;
		gbcPasswordField.gridx = 1;
		gbcPasswordField.gridy = 4;
		centerPanel.add(passwordField, gbcPasswordField);
		passwordField.setText(settings.getPassword());
		
		//CONSOLE SIZE:
		JLabel lblConsoleSize = new JLabel("Console-Size:");
		String consoleSizeTooltip = "If you're using ToxicTodo in CLI mode enter the default size of your console window. This is used to make correct line-breaks.";
		lblConsoleSize.setToolTipText(consoleSizeTooltip);
		GridBagConstraints gbcLblConsolesize = new GridBagConstraints();
		gbcLblConsolesize.insets = new Insets(0, 0, 0, 5);
		gbcLblConsolesize.anchor = GridBagConstraints.EAST;
		gbcLblConsolesize.gridx = 0;
		gbcLblConsolesize.gridy = 6;
		centerPanel.add(lblConsoleSize, gbcLblConsolesize);
		txtFieldConsoleSize = new JTextField();
		txtFieldConsoleSize.setToolTipText(consoleSizeTooltip);
		GridBagConstraints gbcTxtFieldConsoleSize = new GridBagConstraints();
		gbcTxtFieldConsoleSize.insets = new Insets(0, 0, 0, 5);
		gbcTxtFieldConsoleSize.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtFieldConsoleSize.gridx = 1;
		gbcTxtFieldConsoleSize.gridy = 6;
		centerPanel.add(txtFieldConsoleSize, gbcTxtFieldConsoleSize);
		txtFieldConsoleSize.setColumns(10);
		txtFieldConsoleSize.setText(settings.getConsoleSize()+"");
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(ToxicColors.SOFT_GREY);
		FlowLayout flowLayoutBottomPanel = (FlowLayout) bottomPanel.getLayout();
		flowLayoutBottomPanel.setAlignment(FlowLayout.RIGHT);
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
		
		//Has to be set after everything is built up.
		checkboxInternalServerEnabled.setSelected(settings.getIntegratedServerEnabled());
	}
	
	private void saveChanges(){
		try{
			settings.setHOST(textFieldHostIP.getText());
			int port =  Integer.parseInt(textFieldHostPort.getText());
			settings.setPORT(port);
			int consoleSize = Integer.parseInt(txtFieldConsoleSize.getText());
			settings.setConsoleSize(consoleSize);
			settings.setPassword(new String(passwordField.getPassword()));
			settings.setIntegratedServerEnabled(checkboxInternalServerEnabled.isSelected());
			settings.saveSettingsToDisk();
			setVisible(false);
			main.switchToTasks();
		} catch (IllegalArgumentException e){
			Logger.log("bad input", e);
			//TODO: warn user or something with a window or red colored input box
		}
	}
	
	private void cancelChanges(){
		textFieldHostIP.setText(settings.getHOST());
		textFieldHostPort.setText(settings.getPORT()+"");
		passwordField.setText(settings.getPassword());
		txtFieldConsoleSize.setText(settings.getConsoleSize()+"");
		checkboxInternalServerEnabled.setSelected(settings.getIntegratedServerEnabled());
		setVisible(false);
		main.switchToTasks();
	}
}
