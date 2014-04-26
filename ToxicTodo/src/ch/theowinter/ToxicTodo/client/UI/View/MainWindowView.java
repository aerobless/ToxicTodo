package ch.theowinter.ToxicTodo.client.UI.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ch.theowinter.ToxicTodo.client.ClientTodoManager;
import ch.theowinter.ToxicTodo.client.UI.Model.CategoryListModel;
import ch.theowinter.ToxicTodo.client.UI.Model.TaskListModel;
import ch.theowinter.ToxicTodo.client.UI.View.utilities.CategoryListCellRenderer;
import ch.theowinter.ToxicTodo.client.UI.View.utilities.FontIconButton;
import ch.theowinter.ToxicTodo.client.UI.View.utilities.TaskListCellRenderer;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoCategory;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoTask;

import com.explodingpixels.macwidgets.MacUtils;
import com.explodingpixels.macwidgets.UnifiedToolBar;

public class MainWindowView {
	private JFrame frmToxictodo;
	private JList<TodoCategory> categoryList;
	private ListModel<TodoCategory> categoryListModel;
	private ListModel<TodoTask> taskListModel;
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
		categoryListModel = new CategoryListModel(todoManager);
		frmToxictodo = new JFrame();
		frmToxictodo.setTitle("ToxicTodo");
		frmToxictodo.setBounds(100, 100, 844, 495);
		frmToxictodo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmToxictodo.getContentPane().setLayout(new BorderLayout(0, 0));
		
		//Enable unified style, probably needs to be disabled on windows..
		MacUtils.makeWindowLeopardStyle(frmToxictodo.getRootPane());

		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setResizeWeight(0.25);
		//splitPane.setBorder(new LineBorder(Color.blue,0)); //no main border

		frmToxictodo.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		Dimension splitPaneMinimumSize = new Dimension(0, 0);
		
		JScrollPane categoryListscrollPane = new JScrollPane();
		categoryListscrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		categoryListscrollPane.setBorder(new LineBorder(Color.black,0));
		splitPane.setLeftComponent(categoryListscrollPane);
		
		splitPane.getLeftComponent().setMinimumSize(splitPaneMinimumSize);
		
		//CATEGORY SIDEBAR:
		JPanel categoryPanel = new JPanel();
		categoryListscrollPane.setViewportView(categoryPanel);
		categoryPanel.setLayout(new BorderLayout(0, 0));
		
		categoryList = new JList<TodoCategory>(categoryListModel);
		categoryPanel.add(categoryList, BorderLayout.CENTER);
		categoryList.setBackground(new Color(230, 234, 239));

		categoryList.setCellRenderer(new CategoryListCellRenderer());
		ListSelectionModel listSelectionModel = categoryList.getSelectionModel();
	    listSelectionModel.addListSelectionListener(
	                            new SharedListSelectionHandler());
		
		JScrollPane taskListScrollPane = new JScrollPane();
		taskListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		splitPane.setRightComponent(taskListScrollPane);
		
		//TASK LIST:
		categoryList.setSelectedIndex(1); //We need this for now so that it has always something selected..
		taskListModel = new TaskListModel(categoryListModel.getElementAt(categoryList.getSelectedIndex()).getTaskInCategoryAsArrayList()); //TODO: ddd
		System.out.println(taskListModel.getSize());
		JList<TodoTask> taskList = new JList<TodoTask>(taskListModel);
		taskList.setCellRenderer(new TaskListCellRenderer());
		taskList.setBackground(new Color(237, 237, 237));
		
		taskListScrollPane.setViewportView(taskList);
		taskListScrollPane.setBackground(new Color(237, 237, 237));
		taskListScrollPane.setBorder(new LineBorder(Color.black,0));
		
		//OTHER STUFF:
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
		initToolbar();
	}
	
	/*
	 * Creating a UNIFIED mac toolbar according to:
	 * http://jtechdev.com/2013/05/29/style-java-application-for-mac-os-x/
	 * 
	 * TODO: maybe needs to be replaced by a standard toolbar on windows, needs testing
	 */
	private void initToolbar(){
		UnifiedToolBar unifiedToolbar = new UnifiedToolBar();
		//Make the entire toolbar draggable, that's actually really awesome!
		unifiedToolbar.installWindowDraggerOnWindow(frmToxictodo);
		frmToxictodo.getContentPane().add(unifiedToolbar.getComponent(), BorderLayout.NORTH);
		
		//Toolbar buttons:
		Dimension uniBarButtonSize = new Dimension(50, 25);
		
		//New Task:
		FontIconButton newTask = new FontIconButton('\uf15b', "Create a new task.");
		newTask.setVerticalTextPosition(SwingConstants.BOTTOM);
		newTask.setHorizontalTextPosition(SwingConstants.CENTER);
		newTask.putClientProperty("JButton.buttonType", "textured");
		newTask.setPreferredSize(uniBarButtonSize);
		newTask.setMinimumSize(uniBarButtonSize);
		newTask.setMaximumSize(uniBarButtonSize);
		unifiedToolbar.addComponentToLeft(newTask);
		 
		//Complete Task:
		FontIconButton completeTask = new FontIconButton('\uf00c', "Complete the selcted task.");
		completeTask.setVerticalTextPosition(SwingConstants.BOTTOM);
		completeTask.setHorizontalTextPosition(SwingConstants.CENTER);	
		completeTask.putClientProperty("JButton.buttonType", "textured");
		completeTask.setPreferredSize(uniBarButtonSize);
		completeTask.setMinimumSize(uniBarButtonSize);
		completeTask.setMaximumSize(uniBarButtonSize);
		unifiedToolbar.addComponentToLeft(completeTask);	
		
		//Remove Task:
		FontIconButton removeTask = new FontIconButton('\uf014', "Remove the selected task without logging success.");
		removeTask.setVerticalTextPosition(SwingConstants.BOTTOM);
		removeTask.setHorizontalTextPosition(SwingConstants.CENTER);	
		removeTask.putClientProperty("JButton.buttonType", "textured");
		removeTask.setPreferredSize(uniBarButtonSize);
		removeTask.setMinimumSize(uniBarButtonSize);
		removeTask.setMaximumSize(uniBarButtonSize);
		unifiedToolbar.addComponentToLeft(removeTask);	
	}
	
	class SharedListSelectionHandler implements ListSelectionListener {
	    public void valueChanged(ListSelectionEvent e) {
	        ListSelectionModel lsm = (ListSelectionModel)e.getSource();

	        int firstIndex = e.getFirstIndex();
	        int lastIndex = e.getLastIndex();
	        boolean isAdjusting = e.getValueIsAdjusting();
	        System.out.println("Event for indexes "
	                      + firstIndex + " - " + lastIndex
	                      + "; isAdjusting is " + isAdjusting
	                      + "; selected indexes:");

	        if (lsm.isSelectionEmpty()) {
	        	System.out.println(" <none>");
	        } else {
	            // Find out which indexes are selected.
	            int minIndex = lsm.getMinSelectionIndex();
	            int maxIndex = lsm.getMaxSelectionIndex();
	            for (int i = minIndex; i <= maxIndex; i++) {
	                if (lsm.isSelectedIndex(i)) {
	                	System.out.println(" " + i);
	                }
	            }
	        }
	    }
	}
	
}
