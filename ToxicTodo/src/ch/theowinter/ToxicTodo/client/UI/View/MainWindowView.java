package ch.theowinter.ToxicTodo.client.UI.View;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import ch.theowinter.ToxicTodo.client.UI.Model.TodoListModel;

public class MainWindowView {
	private JFrame frmToxictodo;
	private JList<String> todoList;
	private TodoListModel todoListModel;

	/**
	 * Launch the GUI application.
	 */
	public void launchGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindowView window = new MainWindowView();
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
	public MainWindowView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmToxictodo = new JFrame();
		frmToxictodo.setTitle("ToxicTodo");
		frmToxictodo.setBounds(100, 100, 376, 493);
		frmToxictodo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmToxictodo.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
		frmToxictodo.getContentPane().add(topPanel, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel();
		frmToxictodo.getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));

		todoListModel = new TodoListModel();
		todoList = new JList(todoListModel);
		todoList.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		centerPanel.add(todoList, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		centerPanel.add(panel, BorderLayout.WEST);
		
		Component horizontalStrut = Box.createHorizontalStrut(2);
		panel.add(horizontalStrut);
		
		JPanel panel_1 = new JPanel();
		centerPanel.add(panel_1, BorderLayout.EAST);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(2);
		panel_1.add(horizontalStrut_1);
		
		JPanel bottomPanel = new JPanel();
		frmToxictodo.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("New button");
		bottomPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		bottomPanel.add(btnNewButton_1);
	}
}
