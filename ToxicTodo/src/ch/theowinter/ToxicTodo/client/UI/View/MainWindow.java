package ch.theowinter.ToxicTodo.client.UI.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ch.theowinter.ToxicTodo.client.ClientSettings;
import ch.theowinter.ToxicTodo.client.ClientTodoManager;
import ch.theowinter.ToxicTodo.client.UI.Model.CategoryListModel;
import ch.theowinter.ToxicTodo.client.UI.Model.TaskListModel;
import ch.theowinter.ToxicTodo.client.UI.View.utilities.CategoryListCellRenderer;
import ch.theowinter.ToxicTodo.client.UI.View.utilities.FontIconButton;
import ch.theowinter.ToxicTodo.client.UI.View.utilities.TaskListCellRenderer;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoCategory;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoTask;

import com.explodingpixels.macwidgets.BottomBar;
import com.explodingpixels.macwidgets.BottomBarSize;
import com.explodingpixels.macwidgets.MacUtils;
import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.explodingpixels.macwidgets.UnifiedToolBar;

public class MainWindow {
	private JFrame frmToxictodo;
	private JList<TodoCategory> categoryList;
	private CategoryListModel categoryListModel;
	private TaskListModel taskListModel;
	private JList<TodoTask> taskList;
	private ClientTodoManager todoManager;
	private ClientSettings settings;
	
	//Windows
	private SettingsWindow settingsWindow;
	
	//Construction Finals
	final Dimension uniBarButtonSize = new Dimension(50, 25);

	/**
	 * Launch the GUI application.
	 */
	public void launchGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow(todoManager, settings);
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
	public MainWindow(ClientTodoManager aTodoManager, ClientSettings someSettings) {
		todoManager = aTodoManager;
		settings = someSettings;
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
		splitPane.setBorder(new LineBorder(Color.blue,0));

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
	                            new CategoryListSelectionHandler());
		
		JScrollPane taskListScrollPane = new JScrollPane();
		taskListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		splitPane.setRightComponent(taskListScrollPane);
		
		//TASK LIST:
		taskListModel = new TaskListModel(categoryListModel.getElementAt(0).getTaskInCategoryAsArrayList());
		System.out.println(taskListModel.getSize());
		taskList = new JList<TodoTask>(taskListModel);
		taskList.setCellRenderer(new TaskListCellRenderer());
		taskList.setBackground(new Color(237, 237, 237));
		
		taskListScrollPane.setViewportView(taskList);
		taskListScrollPane.setBackground(new Color(237, 237, 237));
		taskListScrollPane.setBorder(new LineBorder(Color.black,0));
		
		initToolbar();
		 
		//BOTTOM STATUS BAR
		BottomBar bottomBar = new BottomBar(BottomBarSize.LARGE);
	    bottomBar.addComponentToLeft(MacWidgetFactory.createEmphasizedLabel("Status"));  
		frmToxictodo.getContentPane().add(bottomBar.getComponent(), BorderLayout.SOUTH);  
		
		//Settings:
		FontIconButton btnSettings = new FontIconButton('\uf013', "Change the program settings.");
		btnSettings.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnSettings.setHorizontalTextPosition(SwingConstants.CENTER);	
		btnSettings.putClientProperty("JButton.buttonType", "textured");
		btnSettings.setPreferredSize(uniBarButtonSize);
		btnSettings.setMinimumSize(uniBarButtonSize);
		btnSettings.setMaximumSize(uniBarButtonSize);
		btnSettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(settingsWindow == null){
					settingsWindow = new SettingsWindow(settings);
					settingsWindow.setVisible(true);
					settingsWindow.setAlwaysOnTop(true);
					settingsWindow.setDefaultCloseOperation(2);
				} else{
					settingsWindow.setVisible(true);
				}
			}
        });      
		bottomBar.addComponentToRight(btnSettings);
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
		//New Task:
		FontIconButton btnNewTask = new FontIconButton('\uf15b', "Create a new task.");
		btnNewTask.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnNewTask.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNewTask.putClientProperty("JButton.buttonType", "textured");
		btnNewTask.setPreferredSize(uniBarButtonSize);
		btnNewTask.setMinimumSize(uniBarButtonSize);
		btnNewTask.setMaximumSize(uniBarButtonSize);
		unifiedToolbar.addComponentToLeft(btnNewTask);
		 
		//Complete Task:
		FontIconButton btnCompleteTask = new FontIconButton('\uf00c', "Complete the selcted task.");
		btnCompleteTask.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCompleteTask.setHorizontalTextPosition(SwingConstants.CENTER);	
		btnCompleteTask.putClientProperty("JButton.buttonType", "textured");
		btnCompleteTask.setPreferredSize(uniBarButtonSize);
		btnCompleteTask.setMinimumSize(uniBarButtonSize);
		btnCompleteTask.setMaximumSize(uniBarButtonSize);
		unifiedToolbar.addComponentToLeft(btnCompleteTask);	
		
		//Remove Task:
		FontIconButton btnRemoveTask = new FontIconButton('\uf014', "Remove the selected task without logging success.");
		btnRemoveTask.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnRemoveTask.setHorizontalTextPosition(SwingConstants.CENTER);	
		btnRemoveTask.putClientProperty("JButton.buttonType", "textured");
		btnRemoveTask.setPreferredSize(uniBarButtonSize);
		btnRemoveTask.setMinimumSize(uniBarButtonSize);
		btnRemoveTask.setMaximumSize(uniBarButtonSize);
		unifiedToolbar.addComponentToLeft(btnRemoveTask);	
	}
	
	class CategoryListSelectionHandler implements ListSelectionListener {
	    public void valueChanged(ListSelectionEvent e) {
	    	ListSelectionModel lsm = (ListSelectionModel)e.getSource();
	    	taskListModel.changeCategory(categoryListModel.getElementAt(categoryList.getSelectedIndex()).getTaskInCategoryAsArrayList());

	        if (lsm.isSelectionEmpty()) {
	        	//should be impossible to achieve
	        	System.out.println("empty selection, o'really?");
	        }
	    }
	}
}
