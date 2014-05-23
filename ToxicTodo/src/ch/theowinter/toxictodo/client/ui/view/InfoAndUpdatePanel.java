package ch.theowinter.toxictodo.client.ui.view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ch.theowinter.toxictodo.client.ClientApplication;
import ch.theowinter.toxictodo.client.ui.view.utilities.PanelHeaderWhite;
import ch.theowinter.toxictodo.client.ui.view.utilities.ToxicColors;
import ch.theowinter.toxictodo.client.ui.view.utilities.ToxicUIData;
import ch.theowinter.toxictodo.sharedobjects.Logger;
import ch.theowinter.toxictodo.sharedobjects.LogicEngine;
import ch.theowinter.toxictodo.sharedobjects.SharedInformation;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.Color;

public class InfoAndUpdatePanel extends JPanel {
	private static final long serialVersionUID = -2022909795010691054L;
	private MainWindow main;

	/**
	 * Create the frame.
	 */
	public InfoAndUpdatePanel(MainWindow mainWindow) {
		this.main = mainWindow;
		setBackground(ToxicColors.SOFT_GREY);
		setBounds(100, 100, 835, 754);
		setBorder(null);
		setLayout(new BorderLayout(0, 0));
		
		PanelHeaderWhite header = new PanelHeaderWhite();
		header.setTitel("About ToxicTodo");
		header.setSubTitel("Your personalized, universal, addictive todo application");
		header.setIcon('\uf0ca');

		add(header, BorderLayout.NORTH);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(ToxicColors.SOFT_GREY);
		add(leftPanel, BorderLayout.CENTER);
		GridBagLayout gblLeftPanel = new GridBagLayout();
		gblLeftPanel.columnWidths = new int[]{20, 58, 131, 15, 0};
		gblLeftPanel.rowHeights = new int[]{20, 0, 0, 10, 0, 0, 0, 10, 19, 0, 0, 0};
		gblLeftPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gblLeftPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		leftPanel.setLayout(gblLeftPanel);
		
		JLabel lblProgramVersion = new JLabel("Version:");
		lblProgramVersion.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbcLblProgramVersion = new GridBagConstraints();
		gbcLblProgramVersion.anchor = GridBagConstraints.WEST;
		gbcLblProgramVersion.insets = new Insets(0, 0, 5, 5);
		gbcLblProgramVersion.gridx = 1;
		gbcLblProgramVersion.gridy = 1;
		leftPanel.add(lblProgramVersion, gbcLblProgramVersion);
		
		String versionNumber = getClass().getPackage().getImplementationVersion();
		if(versionNumber == null){
			versionNumber = "unoffical.local.build";
		}
		JLabel lblVersion = new JLabel(versionNumber);
		GridBagConstraints gbcLblVersion = new GridBagConstraints();
		gbcLblVersion.anchor = GridBagConstraints.WEST;
		gbcLblVersion.insets = new Insets(0, 0, 5, 5);
		gbcLblVersion.gridx = 2;
		gbcLblVersion.gridy = 1;
		leftPanel.add(lblVersion, gbcLblVersion);
		
		String buildDateInformation = getClass().getPackage().getSpecificationTitle();
		if(buildDateInformation == null){
			buildDateInformation = "This build was not downloaded from ToxicTodo's build-server.";
		}
		JLabel lblUpdatedate = new JLabel(buildDateInformation);
		GridBagConstraints gbcLblUpdatedate = new GridBagConstraints();
		gbcLblUpdatedate.gridwidth = 2;
		gbcLblUpdatedate.anchor = GridBagConstraints.WEST;
		gbcLblUpdatedate.insets = new Insets(0, 0, 5, 5);
		gbcLblUpdatedate.gridx = 1;
		gbcLblUpdatedate.gridy = 2;
		leftPanel.add(lblUpdatedate, gbcLblUpdatedate);
		
		//AUTHOR:
		JLabel lblAuthor = new JLabel("Author:");
		lblAuthor.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbcLblAuthor = new GridBagConstraints();
		gbcLblAuthor.anchor = GridBagConstraints.WEST;
		gbcLblAuthor.insets = new Insets(0, 0, 5, 5);
		gbcLblAuthor.gridx = 1;
		gbcLblAuthor.gridy = 4;
		leftPanel.add(lblAuthor, gbcLblAuthor);
		
		JLabel lblAuthorname = new JLabel(SharedInformation.AUTHOR);
		GridBagConstraints gbcLblAuthorname = new GridBagConstraints();
		gbcLblAuthorname.anchor = GridBagConstraints.WEST;
		gbcLblAuthorname.insets = new Insets(0, 0, 5, 5);
		gbcLblAuthorname.gridx = 2;
		gbcLblAuthorname.gridy = 4;
		leftPanel.add(lblAuthorname, gbcLblAuthorname);
		
		//WEBSITE:
		JLabel lblWebsite = new JLabel("Website:");
		lblWebsite.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbcLblWebsite = new GridBagConstraints();
		gbcLblWebsite.anchor = GridBagConstraints.WEST;
		gbcLblWebsite.insets = new Insets(0, 0, 5, 5);
		gbcLblWebsite.gridx = 1;
		gbcLblWebsite.gridy = 5;
		leftPanel.add(lblWebsite, gbcLblWebsite);
		
		JLabel lblTheowinterch = new JLabel(SharedInformation.WEBSITE);
		lblTheowinterch.setForeground(ToxicColors.LINK_BLUE);
		lblTheowinterch.addMouseListener(new MouseAdapter() {
			 public void mouseReleased(MouseEvent e)  
			    {  
		            Desktop desktop = java.awt.Desktop.getDesktop();
				       try {
						desktop.browse(new URI("http://"+SharedInformation.WEBSITE));
					} catch (IOException e1) {
						Logger.log("IOException while trying to convert String to URI in InfoPanel",e1);
					} catch (URISyntaxException anEx) {
						Logger.log("URISyntaxException while trying to convert String to URI in InfoPanel",anEx);
					}
			    }  
		});
		GridBagConstraints gbc_lblTheowinterch = new GridBagConstraints();
		gbc_lblTheowinterch.anchor = GridBagConstraints.WEST;
		gbc_lblTheowinterch.insets = new Insets(0, 0, 5, 5);
		gbc_lblTheowinterch.gridx = 2;
		gbc_lblTheowinterch.gridy = 5;
		leftPanel.add(lblTheowinterch, gbc_lblTheowinterch);

		//GITHUB
		JLabel lblGithub = new JLabel("GitHub:");
		lblGithub.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbcLblGithub = new GridBagConstraints();
		gbcLblGithub.anchor = GridBagConstraints.WEST;
		gbcLblGithub.insets = new Insets(0, 0, 5, 5);
		gbcLblGithub.gridx = 1;
		gbcLblGithub.gridy = 6;
		leftPanel.add(lblGithub, gbcLblGithub);
		
		JLabel lblGithubLink = new JLabel(SharedInformation.GITHUB);
		lblGithubLink.setForeground(ToxicColors.LINK_BLUE);
		lblGithubLink.addMouseListener(new MouseAdapter() {
			 public void mouseReleased(MouseEvent e)  
			    {  
			       Desktop desktop = java.awt.Desktop.getDesktop();
			       try {
					desktop.browse(new URI("http://"+SharedInformation.GITHUB));
				} catch (IOException e1) {
					Logger.log("IOException while trying to convert String to URI in InfoPanel",e1);
				} catch (URISyntaxException anEx) {
					Logger.log("URISyntaxException while trying to convert String to URI in InfoPanel",anEx);
				}
			    }  
		});
		GridBagConstraints gbc_lblGithublinkcomawesome = new GridBagConstraints();
		gbc_lblGithublinkcomawesome.anchor = GridBagConstraints.WEST;
		gbc_lblGithublinkcomawesome.insets = new Insets(0, 0, 5, 5);
		gbc_lblGithublinkcomawesome.gridx = 2;
		gbc_lblGithublinkcomawesome.gridy = 6;
		leftPanel.add(lblGithubLink, gbc_lblGithublinkcomawesome);
		
		
		JLabel lblNewLabel = new JLabel("ToxicTodo uses a bunch of awesome open source projects:");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 8;
		leftPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 9;
		leftPanel.add(scrollPane, gbc_scrollPane);
		
		JTextArea licenseTextArea = new JTextArea();
		licenseTextArea.setEditable(false);
		licenseTextArea.setText(ToxicUIData.LICENSE);
		scrollPane.setViewportView(licenseTextArea);
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(ToxicColors.SOFT_GREY);
		FlowLayout flowLayoutBottomPanel = (FlowLayout) bottomPanel.getLayout();
		flowLayoutBottomPanel.setAlignment(FlowLayout.RIGHT);
		add(bottomPanel, BorderLayout.SOUTH);
		
		JButton btnClose = new JButton("Close");
		bottomPanel.add(btnClose);
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.switchToTasks();
			}
        });
		
		JButton btnCheckForUpdate = new JButton("Check for update");
		btnCheckForUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent aE) {
				updateClientFromJenkins();
			}
		});
		bottomPanel.add(btnCheckForUpdate);
	}
	
	private void updateClientFromJenkins(){
		int result = JOptionPane.showConfirmDialog(
			    null, "You're attempting to update ToxicTodo from it's development server.\n"
			    + "BEWARE: Development updates are NOT STABLE and may break functionality.\n"
			    + "\n"
			    + "If are certain that you want to continue press 'YES'",
			    "Update Process", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
		if(result == 0){
			Logger.log("User chose to attempt to update ToxicTodo from it's development branch.");
			LogicEngine logic = new LogicEngine();
			logic.updateSoftware(ClientApplication.CLIENT_UPDATE_URL, true);
			/*
			 * We're using System.exit() because before we can update the client we need to exit out of the main application
			 * as quickly as possible. The user has been warned that the system will update and he wouldn't need to save
			 * anything anyway.
			 */
			System.exit(0); //NOSONAR
		} else{
			Logger.log("User chose NOT TO try and update ToxicTodo.");
		}
	}
}
