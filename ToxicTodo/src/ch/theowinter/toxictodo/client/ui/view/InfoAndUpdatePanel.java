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
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ch.theowinter.toxictodo.client.ui.view.utilities.PanelHeaderWhite;
import ch.theowinter.toxictodo.client.ui.view.utilities.ToxicColors;
import ch.theowinter.toxictodo.sharedobjects.Logger;
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
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(ToxicColors.SOFT_GREY);
		add(leftPanel, BorderLayout.WEST);
		GridBagLayout gbl_leftPanel = new GridBagLayout();
		gbl_leftPanel.columnWidths = new int[]{128, 131, 0};
		gbl_leftPanel.rowHeights = new int[]{0, 0, 0, 21, 0, 0, 0, 0, 0, 0};
		gbl_leftPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_leftPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		leftPanel.setLayout(gbl_leftPanel);
		
		JLabel spacer = new JLabel(" ");
		GridBagConstraints gbcSpacer = new GridBagConstraints();
		gbcSpacer.insets = new Insets(0, 0, 5, 5);
		gbcSpacer.gridx = 0;
		gbcSpacer.gridy = 0;
		leftPanel.add(spacer, gbcSpacer);
		
		JLabel lblProgramVersion = new JLabel("Program Version:");
		GridBagConstraints gbc_lblProgramVersion = new GridBagConstraints();
		gbc_lblProgramVersion.anchor = GridBagConstraints.EAST;
		gbc_lblProgramVersion.insets = new Insets(0, 0, 5, 5);
		gbc_lblProgramVersion.gridx = 0;
		gbc_lblProgramVersion.gridy = 1;
		leftPanel.add(lblProgramVersion, gbc_lblProgramVersion);
		
		JLabel lblVersion = new JLabel(""+SharedInformation.VERSION);
		GridBagConstraints gbc_lblVersion = new GridBagConstraints();
		gbc_lblVersion.anchor = GridBagConstraints.WEST;
		gbc_lblVersion.insets = new Insets(0, 0, 5, 0);
		gbc_lblVersion.gridx = 1;
		gbc_lblVersion.gridy = 1;
		leftPanel.add(lblVersion, gbc_lblVersion);
		
		JLabel lblLastUpdated = new JLabel("Last updated:");
		GridBagConstraints gbc_lblLastUpdated = new GridBagConstraints();
		gbc_lblLastUpdated.anchor = GridBagConstraints.EAST;
		gbc_lblLastUpdated.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastUpdated.gridx = 0;
		gbc_lblLastUpdated.gridy = 2;
		leftPanel.add(lblLastUpdated, gbc_lblLastUpdated);
		
		JLabel lblUpdatedate = new JLabel(SharedInformation.LAST_UPDATE);
		GridBagConstraints gbc_lblUpdatedate = new GridBagConstraints();
		gbc_lblUpdatedate.anchor = GridBagConstraints.WEST;
		gbc_lblUpdatedate.insets = new Insets(0, 0, 5, 0);
		gbc_lblUpdatedate.gridx = 1;
		gbc_lblUpdatedate.gridy = 2;
		leftPanel.add(lblUpdatedate, gbc_lblUpdatedate);
		
		JLabel label = new JLabel(" ");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 3;
		leftPanel.add(label, gbc_label);
		
		JLabel lblAuthor = new JLabel("Author:");
		GridBagConstraints gbc_lblAuthor = new GridBagConstraints();
		gbc_lblAuthor.anchor = GridBagConstraints.EAST;
		gbc_lblAuthor.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuthor.gridx = 0;
		gbc_lblAuthor.gridy = 4;
		leftPanel.add(lblAuthor, gbc_lblAuthor);
		
		JLabel lblAuthorname = new JLabel(SharedInformation.AUTHOR);
		GridBagConstraints gbc_lblAuthorname = new GridBagConstraints();
		gbc_lblAuthorname.anchor = GridBagConstraints.WEST;
		gbc_lblAuthorname.insets = new Insets(0, 0, 5, 0);
		gbc_lblAuthorname.gridx = 1;
		gbc_lblAuthorname.gridy = 4;
		leftPanel.add(lblAuthorname, gbc_lblAuthorname);
		
		JLabel lblWebsite = new JLabel("Website:");
		GridBagConstraints gbc_lblWebsite = new GridBagConstraints();
		gbc_lblWebsite.anchor = GridBagConstraints.EAST;
		gbc_lblWebsite.insets = new Insets(0, 0, 5, 5);
		gbc_lblWebsite.gridx = 0;
		gbc_lblWebsite.gridy = 5;
		leftPanel.add(lblWebsite, gbc_lblWebsite);
		
		JButton btnWebsiteLink = new JButton(SharedInformation.WEBSITE);
		GridBagConstraints gbc_website = new GridBagConstraints();
		gbc_website.anchor = GridBagConstraints.WEST;
		gbc_website.insets = new Insets(0, 0, 5, 0);
		gbc_website.gridx = 1;
		gbc_website.gridy = 5;
		
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
		leftPanel.add(webLinkPanel, gbc_website);
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
		JLabel lblGithub = new JLabel("GitHub:");
		GridBagConstraints gbc_lblGithub = new GridBagConstraints();
		gbc_lblGithub.anchor = GridBagConstraints.EAST;
		gbc_lblGithub.insets = new Insets(0, 0, 5, 5);
		gbc_lblGithub.gridx = 0;
		gbc_lblGithub.gridy = 6;
		leftPanel.add(lblGithub, gbc_lblGithub);
		
		JButton btnGitHubLink = new JButton(SharedInformation.GITHUB);
		GridBagConstraints gbc_lblGithublink = new GridBagConstraints();
		gbc_lblGithublink.anchor = GridBagConstraints.WEST;
		gbc_lblGithublink.insets = new Insets(0, 0, 5, 0);
		gbc_lblGithublink.gridx = 1;
		gbc_lblGithublink.gridy = 6;
		
		//Ugly hack to get the URL button position right
		JPanel gitHubLinkPanel = new JPanel();
		gitHubLinkPanel.setPreferredSize(new Dimension(273, 20));
		gitHubLinkPanel.setBackground(this.getBackground());
		gitHubLinkPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		gitHubLinkPanel.add(btnGitHubLink);
		btnGitHubLink.setPreferredSize(new Dimension(320, 15));
		btnGitHubLink.setForeground(ToxicColors.LINK_BLUE);
		btnGitHubLink.setBorderPainted(false);
		leftPanel.add(gitHubLinkPanel, gbc_lblGithublink);
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
		Logger.log("not implemented yet");
	}
}
