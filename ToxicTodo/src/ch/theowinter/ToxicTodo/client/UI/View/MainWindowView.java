package ch.theowinter.ToxicTodo.client.UI.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import ch.theowinter.ToxicTodo.client.UI.Model.TodoListModel;

public class MainWindowView {
	private JFrame frame;
	private JList todoList;
	private TodoListModel todoListModel;

	/**
	 * Launch the GUI application.
	 */
	public void launchGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindowView window = new MainWindowView();
					window.frame.setVisible(true);
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
	public MainWindowView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Data and stuff (temp)
		todoListModel = new TodoListModel();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 376, 493);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel();
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));

		todoList = new JList(todoListModel);
		centerPanel.add(todoList, BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel();
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("New button");
		bottomPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		bottomPanel.add(btnNewButton_1);
	}
	
	private void populateTodoList(){
		// Create some items to add to the list
		

	}
}
