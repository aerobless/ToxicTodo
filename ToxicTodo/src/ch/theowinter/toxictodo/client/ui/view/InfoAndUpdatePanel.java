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

import ch.theowinter.toxictodo.client.ClientTodoManager;
import ch.theowinter.toxictodo.client.ui.view.utilities.PanelHeaderWhite;
import ch.theowinter.toxictodo.client.ui.view.utilities.ToxicColors;

public class InfoAndUpdatePanel extends JPanel {
	private static final long serialVersionUID = -2022909795010691054L;
	private MainWindow main;
	private ClientTodoManager todoManager;

	/**
	 * Create the frame.
	 */
	public InfoAndUpdatePanel(MainWindow mainWindow, ClientTodoManager todoManager) {
		this.main = mainWindow;
		this.todoManager = todoManager;
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
		add(rigthPanel, BorderLayout.EAST);
		
		JLabel lblNewLabel = new JLabel("LOGO");
		rigthPanel.add(lblNewLabel);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(ToxicColors.SOFT_GREY);
		add(leftPanel, BorderLayout.WEST);
		GridBagLayout gbl_leftPanel = new GridBagLayout();
		gbl_leftPanel.columnWidths = new int[]{156, 103, 0};
		gbl_leftPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
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
		
		JLabel lblVersion = new JLabel("version");
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
		
		JLabel lblUpdatedate = new JLabel("updateDate");
		GridBagConstraints gbc_lblUpdatedate = new GridBagConstraints();
		gbc_lblUpdatedate.anchor = GridBagConstraints.WEST;
		gbc_lblUpdatedate.insets = new Insets(0, 0, 5, 0);
		gbc_lblUpdatedate.gridx = 1;
		gbc_lblUpdatedate.gridy = 2;
		leftPanel.add(lblUpdatedate, gbc_lblUpdatedate);
		
		JLabel lblAuthor = new JLabel("Author:");
		GridBagConstraints gbc_lblAuthor = new GridBagConstraints();
		gbc_lblAuthor.anchor = GridBagConstraints.EAST;
		gbc_lblAuthor.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuthor.gridx = 0;
		gbc_lblAuthor.gridy = 4;
		leftPanel.add(lblAuthor, gbc_lblAuthor);
		
		JLabel lblAuthorname = new JLabel("authorName");
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
		
		JLabel website = new JLabel("website");
		GridBagConstraints gbc_website = new GridBagConstraints();
		gbc_website.anchor = GridBagConstraints.WEST;
		gbc_website.insets = new Insets(0, 0, 5, 0);
		gbc_website.gridx = 1;
		gbc_website.gridy = 5;
		leftPanel.add(website, gbc_website);
		
		JLabel lblGithub = new JLabel("GitHub:");
		GridBagConstraints gbc_lblGithub = new GridBagConstraints();
		gbc_lblGithub.anchor = GridBagConstraints.EAST;
		gbc_lblGithub.insets = new Insets(0, 0, 5, 5);
		gbc_lblGithub.gridx = 0;
		gbc_lblGithub.gridy = 6;
		leftPanel.add(lblGithub, gbc_lblGithub);
		
		JLabel lblGithublink = new JLabel("githubLink");
		GridBagConstraints gbc_lblGithublink = new GridBagConstraints();
		gbc_lblGithublink.anchor = GridBagConstraints.WEST;
		gbc_lblGithublink.insets = new Insets(0, 0, 5, 0);
		gbc_lblGithublink.gridx = 1;
		gbc_lblGithublink.gridy = 6;
		leftPanel.add(lblGithublink, gbc_lblGithublink);
		
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
		bottomPanel.add(btnCheckForUpdate);
	}
}
