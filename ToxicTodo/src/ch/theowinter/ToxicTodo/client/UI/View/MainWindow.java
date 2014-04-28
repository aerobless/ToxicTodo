package ch.theowinter.ToxicTodo.client.UI.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import ch.theowinter.ToxicTodo.client.ClientSettings;
import ch.theowinter.ToxicTodo.client.ClientTodoManager;
import ch.theowinter.ToxicTodo.client.UI.Model.CategoryListModel;
import ch.theowinter.ToxicTodo.client.UI.Model.TaskListModel;
import ch.theowinter.ToxicTodo.client.UI.View.Utilities.CategoryListCellRenderer;
import ch.theowinter.ToxicTodo.client.UI.View.Utilities.FontIconButton;
import ch.theowinter.ToxicTodo.client.UI.View.Utilities.TaskListCellRenderer;
import ch.theowinter.ToxicTodo.client.UI.View.Utilities.TaskListHeader;
import ch.theowinter.ToxicTodo.client.UI.View.Utilities.ToxicColors;
import ch.theowinter.ToxicTodo.client.UI.View.Utilities.ToxicStrings;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoCategory;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoTask;

import com.explodingpixels.macwidgets.BottomBar;
import com.explodingpixels.macwidgets.BottomBarSize;
import com.explodingpixels.macwidgets.MacUtils;
import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.explodingpixels.macwidgets.UnifiedToolBar;

public class MainWindow{
	private JFrame frmToxictodo;
	private JList<TodoCategory> categoryList;
	private CategoryListModel categoryListModel;
	private TaskListModel taskListModel;
	private JList<TodoTask> taskList;
	private ClientTodoManager todoManager;
	private ClientSettings settings;
	
	//This window
	JSplitPane splitPane;
	MainWindow main = this;
	TaskListHeader taskListHeader;
	JTextField searchField;
	
	//Buttons
	FontIconButton btnNewTask;
	FontIconButton btnCompleteTask;
	FontIconButton btnRemoveTask;
	
	//Panels
	private SettingsPanel settingsPanel;
	private TaskPanel taskWindow;
	private JScrollPane taskScrollPane;
	
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

		splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setResizeWeight(0.2);
		splitPane.setBorder(new LineBorder(Color.blue,0));

		frmToxictodo.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		Dimension splitPaneMinimumSize = new Dimension(0, 0);
		
		//Custom Split Divider (thin & black)
		splitPane.setUI(new BasicSplitPaneUI() {
	        public BasicSplitPaneDivider createDefaultDivider() {
	        return new BasicSplitPaneDivider(this) {
				private static final long serialVersionUID = -3373489858114729264L;
				public void setBorder(Border b) {}
	            @Override
	                public void paint(Graphics g) {
	                g.setColor(ToxicColors.textGreySoft);
	                g.fillRect(0, 0, 1, getSize().height);
	                    super.paint(g);
	                }
	        };
	        }
	    });
		splitPane.setDividerSize(1);
		
		//INIT CATEGORY PANEL
		JScrollPane categoryListscrollPane = new JScrollPane();
		categoryListscrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		categoryListscrollPane.setBorder(new LineBorder(Color.black,0));
		
		JPanel categoryPanel = new JPanel();
		categoryListscrollPane.setViewportView(categoryPanel);
		categoryPanel.setLayout(new BorderLayout(0, 0));
		
		categoryList = new JList<TodoCategory>(categoryListModel);
		categoryPanel.add(categoryList, BorderLayout.CENTER);
		categoryList.setBackground(ToxicColors.softBlue);

		categoryList.setCellRenderer(new CategoryListCellRenderer());
		ListSelectionModel listSelectionModel = categoryList.getSelectionModel();
	    listSelectionModel.addListSelectionListener(new CategoryListSelectionHandler());
		
		splitPane.setLeftComponent(categoryListscrollPane);
		splitPane.getLeftComponent().setMinimumSize(splitPaneMinimumSize);

	    //TOTAL TASK PANEL
	    taskListHeader = new TaskListHeader();
	    
	    taskScrollPane = new JScrollPane();
		taskScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    
	    JPanel totalTaskPanel = new JPanel();
	    totalTaskPanel.setLayout(new BorderLayout(0, 0));
	    totalTaskPanel.add(taskListHeader, BorderLayout.NORTH);
	    totalTaskPanel.add(taskScrollPane, BorderLayout.CENTER);
	    
		splitPane.setRightComponent(totalTaskPanel);
		//splitPane.setBackground(Color.black);

		taskListModel = new TaskListModel(categoryListModel.getElementAt(0).getKeyword(), todoManager);
		System.out.println(taskListModel.getSize());
		taskList = new JList<TodoTask>(taskListModel);
		taskList.setCellRenderer(new TaskListCellRenderer());
		taskList.setBackground(ToxicColors.softGrey);
		
		taskScrollPane.setViewportView(taskList);
		taskScrollPane.setBackground(ToxicColors.softGrey);
		taskScrollPane.setBorder(new LineBorder(Color.black,0));

		initToolbar();
		 
		//BOTTOM STATUS BAR
		BottomBar bottomBar = new BottomBar(BottomBarSize.LARGE);
	    bottomBar.addComponentToLeft(MacWidgetFactory.createEmphasizedLabel("Status"));  
		frmToxictodo.getContentPane().add(bottomBar.getComponent(), BorderLayout.SOUTH);  

		//Refresh:
		FontIconButton btnRefresh = new FontIconButton('\uf021', "Change the program settings.");
		btnRefresh.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnRefresh.setHorizontalTextPosition(SwingConstants.CENTER);	
		btnRefresh.putClientProperty("JButton.buttonType", "textured");
		btnRefresh.setPreferredSize(uniBarButtonSize);
		btnRefresh.setMinimumSize(uniBarButtonSize);
		btnRefresh.setMaximumSize(uniBarButtonSize);
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				todoManager.updateList();
			}
        });      
		bottomBar.addComponentToRight(btnRefresh);
		
		//Settings:
		bottomBar.addComponentToRight(settingsButtonFactory());
		
		//Needs to be called last or we get a nullpointer in the category-listener.
		categoryList.setSelectedIndex(0);
	}
	
	/**
	 * Create the settings button and initialize it's ActionListener
	 * @return settings-button of type FontIconButton 
	 */
	private FontIconButton settingsButtonFactory(){
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
				if(settingsPanel == null){
					settingsPanel = new SettingsPanel(settings, main);
					setRightContent(settingsPanel);
				}else if(settingsPanel.isVisible() == true){
					switchToTasks();
				}else{
					setRightContent(settingsPanel);
				}
			}
        });    
		return btnSettings;
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
		/*
		//Toolbar buttons:
		//New Category:
		FontIconButton btnNewCategory = new FontIconButton('\uf07b', "Create a new task.");
		btnNewCategory.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnNewCategory.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNewCategory.putClientProperty("JButton.buttonType", "textured");
		btnNewCategory.setPreferredSize(uniBarButtonSize);
		btnNewCategory.setMinimumSize(uniBarButtonSize);
		btnNewCategory.setMaximumSize(uniBarButtonSize);
		unifiedToolbar.addComponentToLeft(btnNewCategory);
		btnNewCategory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("not implemented yet.");
			}
        });
		
		JComponent sidebarSpace = MacWidgetFactory.createSpacer(22, 5);
		unifiedToolbar.addComponentToLeft(sidebarSpace);
		*/
		//New Task:
		btnNewTask = new FontIconButton('\uf15b', "Create a new task.");
		btnNewTask.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnNewTask.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNewTask.putClientProperty("JButton.buttonType", "textured");
		btnNewTask.setPreferredSize(uniBarButtonSize);
		btnNewTask.setMinimumSize(uniBarButtonSize);
		btnNewTask.setMaximumSize(uniBarButtonSize);
		unifiedToolbar.addComponentToLeft(btnNewTask);
		btnNewTask.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(taskWindow == null){
					taskWindow = new TaskPanel(main, todoManager);
					setRightContent(taskWindow);
				}else if(taskWindow.isVisible() == true){
					switchToTasks();
				} else{
					setRightContent(taskWindow);
				}
			}
        });  
		 
		//Complete Task:
		btnCompleteTask = new FontIconButton('\uf00c', "Complete the selcted task.");
		btnCompleteTask.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCompleteTask.setHorizontalTextPosition(SwingConstants.CENTER);	
		btnCompleteTask.putClientProperty("JButton.buttonType", "textured");
		btnCompleteTask.setPreferredSize(uniBarButtonSize);
		btnCompleteTask.setMinimumSize(uniBarButtonSize);
		btnCompleteTask.setMaximumSize(uniBarButtonSize);
		unifiedToolbar.addComponentToLeft(btnCompleteTask);	
		btnCompleteTask.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TodoTask task = getSelectedTask();
				TodoCategory categoryKey = getSelectedCategory();
				if(task != null && categoryKey != null){
					todoManager.removeTask(true, task, categoryKey.getKeyword());
				}else{
					System.out.println("category or task is null");
				}
			}
        });   
		
		//Remove Task:
		btnRemoveTask = new FontIconButton('\uf014', "Remove the selected task without logging success.");
		btnRemoveTask.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnRemoveTask.setHorizontalTextPosition(SwingConstants.CENTER);	
		btnRemoveTask.putClientProperty("JButton.buttonType", "textured");
		btnRemoveTask.setPreferredSize(uniBarButtonSize);
		btnRemoveTask.setMinimumSize(uniBarButtonSize);
		btnRemoveTask.setMaximumSize(uniBarButtonSize);
		unifiedToolbar.addComponentToLeft(btnRemoveTask);	
		btnRemoveTask.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TodoTask task = getSelectedTask();
				TodoCategory categoryKey = getSelectedCategory();
				if(task != null && categoryKey != null){
					todoManager.removeTask(false, task, categoryKey.getKeyword());
				}else{
					System.out.println("category or task is null");
				}
			}
        });
		
		//Search:
		searchField = new JTextField(10);
        searchField.putClientProperty("JTextField.variant", "search");
        unifiedToolbar.addComponentToRight(searchField);
        searchField.getDocument().addDocumentListener(new SearchListener());
		
	}
	
	public void switchToTasks(){
		splitPane.getRightComponent().setVisible(false);
		setRightContent(taskScrollPane);
	}
	
	public void setRightContent(Component content){
		int oldDividerLocation = splitPane.getDividerLocation();
		splitPane.setRightComponent(content);
		splitPane.getRightComponent().setVisible(true);
		splitPane.setDividerLocation(oldDividerLocation);
	}
	
	public TodoCategory getSelectedCategory(){
		return categoryList.getSelectedValue();
	}
	
	public TodoTask getSelectedTask(){
		return taskList.getSelectedValue();
	}
	
	class CategoryListSelectionHandler implements ListSelectionListener {
	    public void valueChanged(ListSelectionEvent e) {
	    	ListSelectionModel lsm = (ListSelectionModel)e.getSource();
	    	TodoCategory currentCategory = categoryListModel.getElementAt(categoryList.getSelectedIndex());
	    	taskListModel.changeCategory(currentCategory.getKeyword());
	    	taskListHeader.setTitel(currentCategory.getName().toUpperCase());
	    	if(currentCategory.getKeyword().equals(ToxicStrings.allTaskTodoCategoryKey)){
	    		btnNewTask.setEnabled(false);
	    		btnCompleteTask.setEnabled(false);
	    		btnRemoveTask.setEnabled(false);
	    	} else{
	    		btnNewTask.setEnabled(true);
	    		btnCompleteTask.setEnabled(true);
	    		btnRemoveTask.setEnabled(true);
	    	}
	    	if (lsm.isSelectionEmpty()) {
	        	//should be impossible to achieve
	        	System.out.println("empty selection, o'really?");
	        }
	    }
	}
	
	class SearchListener implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent aE) {
			runSearch();
		}

		@Override
		public void removeUpdate(DocumentEvent aE) {
			runSearch();
		}

		@Override
		public void changedUpdate(DocumentEvent aE) {
			runSearch();
		}
		
		public void runSearch(){
			taskListModel.filter(searchField.getText());
		}
	}
}
