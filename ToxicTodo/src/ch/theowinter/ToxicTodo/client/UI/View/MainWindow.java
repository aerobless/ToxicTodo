package ch.theowinter.ToxicTodo.client.UI.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
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
import ch.theowinter.ToxicTodo.client.UI.View.Utilities.PanelHeaderWhite;
import ch.theowinter.ToxicTodo.client.UI.View.Utilities.TaskListCellRenderer;
import ch.theowinter.ToxicTodo.client.UI.View.Utilities.ToxicColors;
import ch.theowinter.ToxicTodo.client.UI.View.Utilities.ToxicStrings;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoCategory;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoTask;

import com.explodingpixels.macwidgets.BottomBar;
import com.explodingpixels.macwidgets.BottomBarSize;
import com.explodingpixels.macwidgets.LabeledComponentGroup;
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
	PanelHeaderWhite taskListHeader;
	JTextField searchField;
	
	//Buttons
	FontIconButton btnNewTask;
	FontIconButton btnCompleteTask;
	FontIconButton btnRemoveTask;
	FontIconButton btnEditCategory;
	FontIconButton btnNewCategory;
	
	//Panels
	private JPanel totalTaskPanel;
	private TaskPanel newTaskPanel;
	private CategoryPanel categoryPanel;
	private SettingsPanel settingsPanel;
	
	//Construction Finals
	final Dimension uniBarButtonSize = new Dimension(50, 27);

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
	    taskListHeader = new PanelHeaderWhite();
	    
	    JScrollPane taskScrollPane = new JScrollPane();
		taskScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    
	    totalTaskPanel = new JPanel();
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
	 * mac button theory:
	 * http://nadeausoftware.com/articles/2008/11/mac_java_tip_how_create_aqua_single_and_segmented_buttons
	 * 
	 * TODO: maybe needs to be replaced by a standard toolbar on windows, needs testing
	 */
	private void initToolbar(){
		UnifiedToolBar unifiedToolbar = new UnifiedToolBar();
		//Make the entire toolbar draggable, that's actually really awesome!
		unifiedToolbar.installWindowDraggerOnWindow(frmToxictodo);
		frmToxictodo.getContentPane().add(unifiedToolbar.getComponent(), BorderLayout.NORTH);
		
		//Toolbar buttons:
		//New Category:
		btnNewCategory = new FontIconButton('\uf07b', "Create a new category.");
		btnNewCategory.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnNewCategory.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNewCategory.putClientProperty("JButton.buttonType", "segmentedTextured");
		btnNewCategory.putClientProperty( "JButton.segmentPosition", "first" );
		btnNewCategory.setPreferredSize(uniBarButtonSize);
		btnNewCategory.setMinimumSize(uniBarButtonSize);
		btnNewCategory.setMaximumSize(uniBarButtonSize);
		btnNewCategory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(categoryPanel == null){
					categoryPanel = new CategoryPanel(main, todoManager);
					categoryPanel.newCategory();
					setRightContent(categoryPanel);
				}else if(categoryPanel.isVisible() == true){
					switchToTasks();
				} else{
					categoryPanel.newCategory();
					setRightContent(categoryPanel);
				}
			}
        });
		
		//Edit category:
		btnEditCategory = new FontIconButton('\uf040', "Edit an existing category.");
		btnEditCategory.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnEditCategory.setHorizontalTextPosition(SwingConstants.CENTER);
		btnEditCategory.putClientProperty("JButton.buttonType", "segmentedTextured");
		btnEditCategory.putClientProperty( "JButton.segmentPosition", "last" );
		btnEditCategory.setPreferredSize(uniBarButtonSize);
		btnEditCategory.setMinimumSize(uniBarButtonSize);
		btnEditCategory.setMaximumSize(uniBarButtonSize);
		btnEditCategory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TodoCategory editCategory = getSelectedCategory();
				//double-safety - we never want to edit all-tasks.
				if(editCategory.getKeyword()!=ToxicStrings.allTaskTodoCategoryKey){
					if(categoryPanel == null){
						categoryPanel = new CategoryPanel(main, todoManager);
						categoryPanel.setCategory(editCategory);
						setRightContent(categoryPanel);
					}else if(categoryPanel.isVisible() == true){
						switchToTasks();
					} else{
						categoryPanel.setCategory(getSelectedCategory());
						setRightContent(categoryPanel);
					}
				}
			}
        });	
        ButtonGroup categoryGroup = new ButtonGroup();
        categoryGroup.add(btnNewCategory);
        categoryGroup.add(btnEditCategory);
        
        unifiedToolbar.addComponentToLeft(new LabeledComponentGroup("Categories", categoryGroup).getComponent());

		//New Task:
		btnNewTask = new FontIconButton('\uf15b', "Create a new task.");
		btnNewTask.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnNewTask.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNewTask.putClientProperty("JButton.buttonType", "segmentedTextured");
		btnNewTask.putClientProperty( "JButton.segmentPosition", "first" );
		btnNewTask.setPreferredSize(uniBarButtonSize);
		btnNewTask.setMinimumSize(uniBarButtonSize);
		btnNewTask.setMaximumSize(uniBarButtonSize);
		btnNewTask.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(newTaskPanel == null){
					newTaskPanel = new TaskPanel(main, todoManager);
					setRightContent(newTaskPanel);
				}else if(newTaskPanel.isVisible() == true){
					switchToTasks();
				} else{
					setRightContent(newTaskPanel);
				}
			}
        });  
		 
		//Complete Task:
		btnCompleteTask = new FontIconButton('\uf00c', "Complete the selcted task.");
		btnCompleteTask.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCompleteTask.setHorizontalTextPosition(SwingConstants.CENTER);	
		btnCompleteTask.putClientProperty("JButton.buttonType", "segmentedTextured");
		btnCompleteTask.putClientProperty( "JButton.segmentPosition", "middle" );
		btnCompleteTask.setPreferredSize(uniBarButtonSize);
		btnCompleteTask.setMinimumSize(uniBarButtonSize);
		btnCompleteTask.setMaximumSize(uniBarButtonSize);
		btnCompleteTask.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TodoTask task = getSelectedTask();
				TodoCategory categoryKey = getSelectedCategory();
				if(task != null && categoryKey != null){
					todoManager.removeTask(true, task, categoryKey.getKeyword());
					taskListModel.filter(searchField.getText());
				}else{
					System.out.println("category or task is null");
				}
			}
        }); 
		
		//Remove Task:
		btnRemoveTask = new FontIconButton('\uf014', "Remove the selected task without logging success.");
		btnRemoveTask.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnRemoveTask.setHorizontalTextPosition(SwingConstants.CENTER);	
		btnRemoveTask.putClientProperty("JButton.buttonType", "segmentedTextured");
		btnRemoveTask.putClientProperty( "JButton.segmentPosition", "last" );
		btnRemoveTask.setPreferredSize(uniBarButtonSize);
		btnRemoveTask.setMinimumSize(uniBarButtonSize);
		btnRemoveTask.setMaximumSize(uniBarButtonSize);
		btnRemoveTask.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TodoTask task = getSelectedTask();
				TodoCategory categoryKey = getSelectedCategory();
				if(task != null && categoryKey != null){
					todoManager.removeTask(false, task, categoryKey.getKeyword());
					taskListModel.filter(searchField.getText());
				}else{
					System.out.println("category or task is null");
				}
			}
        });
		
        ButtonGroup taskGroup = new ButtonGroup();
        taskGroup.add(btnNewTask);
        taskGroup.add(btnCompleteTask);
        taskGroup.add(btnRemoveTask);
       
        unifiedToolbar.addComponentToLeft(new LabeledComponentGroup("Tasks", taskGroup).getComponent());
		
		//Search:
		searchField = new JTextField(10);
        searchField.putClientProperty("JTextField.variant", "search");
        unifiedToolbar.addComponentToRight(new LabeledComponentGroup(" ",
        		searchField).getComponent());
        searchField.getDocument().addDocumentListener(new SearchListener());	
	}
	
	public void switchToTasks(){
		searchField.setText("");
		splitPane.getRightComponent().setVisible(false);
		setRightContent(totalTaskPanel);
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
	    	taskListHeader.setSubTitel(getMotivationText());
	    	if(currentCategory.getKeyword().equals(ToxicStrings.allTaskTodoCategoryKey)){
	    		btnNewTask.setEnabled(false);
	    		btnCompleteTask.setEnabled(false);
	    		btnRemoveTask.setEnabled(false);
	    		btnEditCategory.setEnabled(false);
	    	} else{
	    		btnNewTask.setEnabled(true);
	    		btnCompleteTask.setEnabled(true);
	    		btnRemoveTask.setEnabled(true);
	    		btnEditCategory.setEnabled(true);
	    	}
	    	if (lsm.isSelectionEmpty()) {
	        	//should be impossible to achieve
	        	System.out.println("empty selection, o'really?");
	        }
	    	taskListModel.filter(searchField.getText());
	    }
	    
	    /*
	     * Random motivational quotes:
	     * Source: http://www.forbes.com/sites/kevinkruse/2013/05/28/inspirational-quotes/
	     */
	    public String getMotivationText(){
	    	ArrayList<String> motivationArray = new ArrayList<String>();
	    	motivationArray.add("Strive not to be a success, but rather to be of value. – Albert Einstein");
	    	motivationArray.add("You miss 100% of the shots you don’t take. – Wayne Gretzky");
	    	//motivationArray.add("The most common way people give up their power is by thinking they don’t have any. – Alice Walker");
	    	motivationArray.add("Your time is limited, so don’t waste it living someone else’s life. – Steve Jobs");
	    	motivationArray.add("Start where you are. Use what you have.  Do what you can. – Arthur Ashe");
	    	
	    	return motivationArray.get(getRandomInRange(0,(motivationArray.size()-1)));
	    }
	    
	    public int getRandomInRange(int min, int max){
	    	return min + (int)(Math.random() * ((max - min) + 1));
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
			taskList.setSelectedIndex(0);
		}
	}
}
