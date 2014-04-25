package ch.theowinter.ToxicTodo.client.UI.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import ch.theowinter.ToxicTodo.client.ClientTodoManager;
import ch.theowinter.ToxicTodo.client.UI.Model.TodoListModel;

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
		
		JPanel topPanel = new JPanel();
		topPanel.setBorder(null);
		frmToxictodo.getContentPane().add(topPanel, BorderLayout.NORTH);
		

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
		//todoListscrollPane.setViewportBorder(new LineBorder(Color.blue,3));
		todoListscrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
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
		bottomPanel.setBorder(null);
		frmToxictodo.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("New button");
		bottomPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		bottomPanel.add(btnNewButton_1);
	}
}
