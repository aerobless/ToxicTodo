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
import ch.theowinter.toxictodo.sharedobjects.Logger;
import ch.theowinter.toxictodo.sharedobjects.LogicEngine;
import ch.theowinter.toxictodo.sharedobjects.SharedInformation;

public class InfoAndUpdatePanel extends JPanel {
	private static final long serialVersionUID = -2022909795010691054L;
	private MainWindow main;

	/**
	 * Create the frame.
	 */
	public InfoAndUpdatePanel(MainWindow mainWindow) {
		this.main = mainWindow;
		setBackground(ToxicColors.SOFT_GREY);
		setBounds(100, 100, 515, 381);
		setBorder(null);
		setLayout(new BorderLayout(0, 0));
		
		PanelHeaderWhite header = new PanelHeaderWhite();
		header.setTitel("About ToxicTodo");
		header.setSubTitel("Add better subtitle");
		header.setIcon('\uf15b');

		add(header, BorderLayout.NORTH);
		
		JPanel rigthPanel = new JPanel();
		add(rigthPanel, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("LOGO");
		rigthPanel.add(lblNewLabel);
		rigthPanel.setBackground(this.getBackground());
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(ToxicColors.SOFT_GREY);
		add(leftPanel, BorderLayout.WEST);
		GridBagLayout gblLeftPanel = new GridBagLayout();
		gblLeftPanel.columnWidths = new int[]{128, 131, 0};
		gblLeftPanel.rowHeights = new int[]{0, 0, 0, 21, 0, 0, 0, 0, 0, 0};
		gblLeftPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gblLeftPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		leftPanel.setLayout(gblLeftPanel);
		
		JLabel spacer = new JLabel(" ");
		GridBagConstraints gbcSpacer = new GridBagConstraints();
		gbcSpacer.insets = new Insets(0, 0, 5, 5);
		gbcSpacer.gridx = 0;
		gbcSpacer.gridy = 0;
		leftPanel.add(spacer, gbcSpacer);
		
		JLabel lblProgramVersion = new JLabel("Program Version:");
		GridBagConstraints gbcLblProgramVersion = new GridBagConstraints();
		gbcLblProgramVersion.anchor = GridBagConstraints.EAST;
		gbcLblProgramVersion.insets = new Insets(0, 0, 5, 5);
		gbcLblProgramVersion.gridx = 0;
		gbcLblProgramVersion.gridy = 1;
		leftPanel.add(lblProgramVersion, gbcLblProgramVersion);
		
		JLabel lblVersion = new JLabel(getClass().getPackage().getImplementationVersion());
		GridBagConstraints gbcLblVersion = new GridBagConstraints();
		gbcLblVersion.anchor = GridBagConstraints.WEST;
		gbcLblVersion.insets = new Insets(0, 0, 5, 0);
		gbcLblVersion.gridx = 1;
		gbcLblVersion.gridy = 1;
		leftPanel.add(lblVersion, gbcLblVersion);
		
		JLabel lblLastUpdated = new JLabel("Built on:");
		GridBagConstraints gbcLblLastUpdated = new GridBagConstraints();
		gbcLblLastUpdated.anchor = GridBagConstraints.EAST;
		gbcLblLastUpdated.insets = new Insets(0, 0, 5, 5);
		gbcLblLastUpdated.gridx = 0;
		gbcLblLastUpdated.gridy = 2;
		leftPanel.add(lblLastUpdated, gbcLblLastUpdated);
		
		JLabel lblUpdatedate = new JLabel(getClass().getPackage().getSpecificationTitle());
		GridBagConstraints gbcLblUpdatedate = new GridBagConstraints();
		gbcLblUpdatedate.anchor = GridBagConstraints.WEST;
		gbcLblUpdatedate.insets = new Insets(0, 0, 5, 0);
		gbcLblUpdatedate.gridx = 1;
		gbcLblUpdatedate.gridy = 2;
		leftPanel.add(lblUpdatedate, gbcLblUpdatedate);
		
		JLabel label = new JLabel(" ");
		GridBagConstraints gbcLabel = new GridBagConstraints();
		gbcLabel.insets = new Insets(0, 0, 5, 5);
		gbcLabel.gridx = 0;
		gbcLabel.gridy = 3;
		leftPanel.add(label, gbcLabel);
		
		//AUTHOR:
		JLabel lblAuthor = new JLabel("Author:");
		GridBagConstraints gbcLblAuthor = new GridBagConstraints();
		gbcLblAuthor.anchor = GridBagConstraints.EAST;
		gbcLblAuthor.insets = new Insets(0, 0, 5, 5);
		gbcLblAuthor.gridx = 0;
		gbcLblAuthor.gridy = 4;
		leftPanel.add(lblAuthor, gbcLblAuthor);
		
		JLabel lblAuthorname = new JLabel(SharedInformation.AUTHOR);
		GridBagConstraints gbcLblAuthorname = new GridBagConstraints();
		gbcLblAuthorname.anchor = GridBagConstraints.WEST;
		gbcLblAuthorname.insets = new Insets(0, 0, 5, 0);
		gbcLblAuthorname.gridx = 1;
		gbcLblAuthorname.gridy = 4;
		leftPanel.add(lblAuthorname, gbcLblAuthorname);
		
		//WEBSITE:
		JLabel lblWebsite = new JLabel("Website:");
		GridBagConstraints gbcLblWebsite = new GridBagConstraints();
		gbcLblWebsite.anchor = GridBagConstraints.EAST;
		gbcLblWebsite.insets = new Insets(0, 0, 5, 5);
		gbcLblWebsite.gridx = 0;
		gbcLblWebsite.gridy = 5;
		leftPanel.add(lblWebsite, gbcLblWebsite);
		
		JButton btnWebsiteLink = new JButton(SharedInformation.WEBSITE);
		GridBagConstraints gbcWebsite = new GridBagConstraints();
		gbcWebsite.anchor = GridBagConstraints.WEST;
		gbcWebsite.insets = new Insets(0, 0, 5, 0);
		gbcWebsite.gridx = 1;
		gbcWebsite.gridy = 5;
		
		//Ugly hack to get the URL button position right
		JPanel webLinkPanel = new JPanel();
		webLinkPanel.setPreferredSize(new Dimension(136, 20));
		webLinkPanel.setBackground(this.getBackground());
		webLinkPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		webLinkPanel.add(btnWebsiteLink);
		btnWebsiteLink.setPreferredSize(new Dimension(150, 15));
		btnWebsiteLink.setForeground(ToxicColors.LINK_BLUE);
		btnWebsiteLink.setBorderPainted(false);
		btnWebsiteLink.setFocusPainted(false);
		btnWebsiteLink.setMargin(new Insets(0, 0, 0, 0));
		btnWebsiteLink.setContentAreaFilled(false);
		btnWebsiteLink.setOpaque(false);
		btnWebsiteLink.setHorizontalAlignment(SwingConstants.LEFT);
		btnWebsiteLink.setHorizontalTextPosition(SwingConstants.LEFT);
		leftPanel.add(webLinkPanel, gbcWebsite);
		btnWebsiteLink.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
		
		//GITHUB
		JLabel lblGithub = new JLabel("GitHub:");
		GridBagConstraints gbcLblGithub = new GridBagConstraints();
		gbcLblGithub.anchor = GridBagConstraints.EAST;
		gbcLblGithub.insets = new Insets(0, 0, 5, 5);
		gbcLblGithub.gridx = 0;
		gbcLblGithub.gridy = 6;
		leftPanel.add(lblGithub, gbcLblGithub);
		
		JButton btnGitHubLink = new JButton(SharedInformation.GITHUB);
		GridBagConstraints gbcLblGithublink = new GridBagConstraints();
		gbcLblGithublink.anchor = GridBagConstraints.WEST;
		gbcLblGithublink.insets = new Insets(0, 0, 5, 0);
		gbcLblGithublink.gridx = 1;
		gbcLblGithublink.gridy = 6;
		
		//Ugly hack to get the URL button position right
		JPanel gitHubLinkPanel = new JPanel();
		gitHubLinkPanel.setPreferredSize(new Dimension(273, 20));
		gitHubLinkPanel.setBackground(this.getBackground());
		gitHubLinkPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		gitHubLinkPanel.add(btnGitHubLink);
		btnGitHubLink.setPreferredSize(new Dimension(320, 15));
		btnGitHubLink.setForeground(ToxicColors.LINK_BLUE);
		btnGitHubLink.setBorderPainted(false);
		leftPanel.add(gitHubLinkPanel, gbcLblGithublink);
		btnGitHubLink.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
