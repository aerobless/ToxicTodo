package ch.theowinter.ToxicTodo.client.UI.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import ch.theowinter.ToxicTodo.client.ClientTodoManager;
import ch.theowinter.ToxicTodo.client.UI.Model.TodoListModel;

import com.bulenkov.iconloader.IconLoader;
import com.explodingpixels.macwidgets.MacButtonFactory;
import com.explodingpixels.macwidgets.MacUtils;
import com.explodingpixels.macwidgets.UnifiedToolBar;

public class MainWindowView {
	private JFrame frmToxictodo;
	private JList<String> todoList;
	private TodoListModel todoListModel;
	private ClientTodoManager todoManager;

	/**
	 * Launch the GUI application.
	 */
	public void launchGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindowView window = new MainWindowView(todoManager);
					window.frmToxictodo.setVisible(true);
					// methods here.
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindowView(ClientTodoManager aTodoManager) {
		todoManager = aTodoManager;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		todoListModel = new TodoListModel(todoManager);
		frmToxictodo = new JFrame();
		frmToxictodo.setTitle("ToxicTodo");
		frmToxictodo.setBounds(100, 100, 844, 495);
		frmToxictodo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmToxictodo.getContentPane().setLayout(new BorderLayout(0, 0));
		
		//Enable unified style, probably needs to be disabled on windows..
		MacUtils.makeWindowLeopardStyle(frmToxictodo.getRootPane());

		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setResizeWeight(0.3);
		//splitPane.setBorder(new LineBorder(Color.blue,0)); //no main border

		frmToxictodo.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		
		Dimension splitPaneMinimumSize = new Dimension(0, 0);
		splitPane.getRightComponent().setMinimumSize(splitPaneMinimumSize);
		
		JScrollPane todoListscrollPane = new JScrollPane();
		todoListscrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		todoListscrollPane.setBorder(new LineBorder(Color.black,0));
		splitPane.setLeftComponent(todoListscrollPane);
		
		splitPane.getLeftComponent().setMinimumSize(splitPaneMinimumSize);
		
		JPanel todoListPanel = new JPanel();
		todoListscrollPane.setViewportView(todoListPanel);
		todoListPanel.setLayout(new BorderLayout(0, 0));
		
		todoList = new JList(todoListModel);
		todoListPanel.add(todoList, BorderLayout.CENTER);
		todoList.setBorder(null);

		JPanel bottomPanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) bottomPanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		bottomPanel.setBorder(null);
		frmToxictodo.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
		JButton btnNewTask = new JButton("new task");
		btnNewTask.setToolTipText("Add a new task to the todo list.");
		bottomPanel.add(btnNewTask);
		
		JButton btnRemoveTask = new JButton("complete task");
		btnRemoveTask.setToolTipText("Complete a task.");
		bottomPanel.add(btnRemoveTask);
		
		/*
		 * Creating a unified toolbar according to:
		 * http://jtechdev.com/2013/05/29/style-java-application-for-mac-os-x/
		 */
		UnifiedToolBar unifiedToolbar = new UnifiedToolBar();
		frmToxictodo.getContentPane().add(unifiedToolbar.getComponent(), BorderLayout.NORTH);
		
		// Create a button for the toolbar
		JButton saveButton = new JButton("Save");
		// Make sure the text is in the correct position
		saveButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
		 
		// Set the icon of the button
		saveButton.setIcon(IconLoader.findIcon(SettingsWindow.class.getResource("/resources/icon-32.png")));
		saveButton.putClientProperty("JButton.buttonType", "textured");
		 
		// Make the dimensions of the button consistant
		saveButton.setPreferredSize(new Dimension(50, 35));
		saveButton.setMinimumSize(new Dimension(50, 35));
		saveButton.setMaximumSize(new Dimension(50, 35));
		 
		AbstractButton macSaveButton = MacButtonFactory.makeUnifiedToolBarButton(saveButton);
		// Add the button to the toolbar
		unifiedToolbar.addComponentToLeft(macSaveButton);
		 
		// Create a button for the toolbar
		JButton cogButton = new JButton("Configure");
		// Make sure the text is in the correct position
		cogButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		cogButton.setHorizontalTextPosition(SwingConstants.CENTER);
		 
		//Working on getting retina ready images as button...http://bulenkov.com/iconloader/
		cogButton.setIcon(IconLoader.findIcon(SettingsWindow.class.getResource("/resources/icon-32.png")));
		cogButton.putClientProperty("JButton.buttonType", "textured");
		 
		// Make the dimensions of the button consistant
		cogButton.setPreferredSize(new Dimension(50, 35));
		cogButton.setMinimumSize(new Dimension(50, 35));
		cogButton.setMaximumSize(new Dimension(50, 35));
		 
		AbstractButton macCogButton = MacButtonFactory.makeUnifiedToolBarButton(cogButton);
		
		// Add the button to the toolbar
		unifiedToolbar.addComponentToLeft(macCogButton);	
	}
}
