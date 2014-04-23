package ch.theowinter.ToxicTodo.client.UI.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

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
		todoListModel = new TodoListModel(null);
		frmToxictodo = new JFrame();
		frmToxictodo.setTitle("ToxicTodo");
		frmToxictodo.setBounds(100, 100, 844, 495);
		frmToxictodo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmToxictodo.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
		frmToxictodo.getContentPane().add(topPanel, BorderLayout.NORTH);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.2);
		frmToxictodo.getContentPane().add(splitPane, BorderLayout.CENTER);
		todoList = new JList(todoListModel);
		splitPane.setLeftComponent(todoList);
		todoList.setBorder(null);
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);

		JPanel bottomPanel = new JPanel();
		frmToxictodo.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("New button");
		bottomPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		bottomPanel.add(btnNewButton_1);
	}
}
