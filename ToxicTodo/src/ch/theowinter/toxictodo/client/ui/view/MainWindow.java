package ch.theowinter.toxictodo.client.ui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import ch.theowinter.toxictodo.client.ClientApplication;
import ch.theowinter.toxictodo.client.ClientSettings;
import ch.theowinter.toxictodo.client.ClientTodoManager;
import ch.theowinter.toxictodo.client.ui.model.CategoryListModel;
import ch.theowinter.toxictodo.client.ui.model.TaskListModel;
import ch.theowinter.toxictodo.client.ui.view.utilities.ButtonTextGroup;
import ch.theowinter.toxictodo.client.ui.view.utilities.CategoryListCellRenderer;
import ch.theowinter.toxictodo.client.ui.view.utilities.FontIconButton;
import ch.theowinter.toxictodo.client.ui.view.utilities.PanelHeader;
import ch.theowinter.toxictodo.client.ui.view.utilities.TaskListCellRenderer;
import ch.theowinter.toxictodo.client.ui.view.utilities.ToxicColors;
import ch.theowinter.toxictodo.client.ui.view.utilities.ToxicUIData;
import ch.theowinter.toxictodo.sharedobjects.Logger;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoCategory;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoTask;

import com.explodingpixels.macwidgets.BottomBar;
import com.explodingpixels.macwidgets.BottomBarSize;
import com.explodingpixels.macwidgets.LabeledComponentGroup;
import com.explodingpixels.macwidgets.MacUtils;
import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.explodingpixels.macwidgets.UnifiedToolBar;

public class MainWindow{
	protected JFrame frmToxictodo;
	private JList<TodoCategory> categoryList;
	private CategoryListModel categoryListModel;
	private TaskListModel taskListModel;
	private JList<TodoTask> taskList;
	private ClientTodoManager todoManager;
	private ClientSettings settings;
	
	//Constants
	private static final String TEXTURED = "textured";
	private static final String SEGMENTED_TEXTURED = "segmentedTextured";
	private static final String POS_FIRST = "first";
	private static final String POS_MIDDLE = "middle";
	private static final String POS_LAST = "last";
	
	//This window
	JSplitPane splitPane;
	MainWindow main = this;
	PanelHeader taskListHeader;
	JTextField searchField;
	
	//Buttons
	FontIconButton btnNewTask;
	FontIconButton btnCompleteTask;
	FontIconButton btnRemoveTask;
	FontIconButton btnEditCategory;
	FontIconButton btnNewCategory;
	FontIconButton btnCompletedTaskList;
	FontIconButton btnStatistics;
	FontIconButton btnRefresh;
	FontIconButton btnInfo;
	FontIconButton btnSettings;
	
	
	//Panels
	private JPanel taskListPanel;
	private TaskPanel specificTaskPanel;
	private CategoryPanel categoryPanel;
	private SettingsPanel settingsPanel;
	
	//Short-Lived Panels (recreated on every use):
	private StatisticsPanel statisticsPanel;
	private InfoAndUpdatePanel infoPanel;
	
	//Construction Finals
	final Dimension uniBarButtonSize = toolbarButtonSize();
	final Dimension bottomBarButtonSize = new Dimension(45, 15);
	
	/**
	 * Create the application.
	 */
	public MainWindow(ClientTodoManager aTodoManager, ClientSettings someSettings) {
		todoManager = aTodoManager;
		settings = someSettings;
		initialize();
	}

	/**
	 * Launch the GUI application.
	 */
	public void launchGUI() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainWindow window = new MainWindow(todoManager, settings);
					window.frmToxictodo.setVisible(true);
				} catch (Exception e) {
					Logger.log("Fatal UI Exception", e);
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		categoryListModel = new CategoryListModel(todoManager);
		
		frmToxictodo = new JFrame();
		frmToxictodo.setTitle("ToxicTodo");
		frmToxictodo.setBounds(100, 100, 890, 495);
		frmToxictodo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmToxictodo.getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		if("osx".equals(ClientApplication.OS)){
			MacUtils.makeWindowLeopardStyle(frmToxictodo.getRootPane());
		} else {
	        try {
				UIManager.setLookAndFeel(
						UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException anEx) {
				Logger.log("Unable to properly set the SystemLookAndFeel on a non-osx system.", anEx);
			}
		}
		
		//Initialize Panels:
		initSplitPane();
		initCategoryPanel();
	    initTaskPanel();
	    
	    //Initialize Toolbars:
		initToolbar();
		initBottomBar();
		
		//Needs to be called last or we get a nullpointer in the category-listener.
		categoryList.setSelectedIndex(0);
		
		//Fix divider:
		splitPane.setDividerLocation(250);
	}

	/**
	 * Initialize the SplitPane in the center of the main window.
	 */
	private void initSplitPane() {
		splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setResizeWeight(0.2);
		splitPane.setBorder(new LineBorder(ToxicColors.BORDER_GREY,0));

		frmToxictodo.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		//Custom Split Divider (thin & black)
		splitPane.setUI(new BasicSplitPaneUI() {
	        @Override
			public BasicSplitPaneDivider createDefaultDivider() {
	        return new BasicSplitPaneDivider(this) {
				private static final long serialVersionUID = -3373489858114729264L;
	            @Override
	                public void paint(Graphics g) {
	            	if("osx".equals(ClientApplication.OS)){
	                    g.setColor(ToxicColors.SOFT_TEXT_GREY);
	            	} else {
	            	  	setBorder(new LineBorder(ToxicColors.BORDER_GREY,0));
	                    g.setColor(ToxicColors.BORDER_GREY);
	            	}
	                g.fillRect(0, 0, 1, getSize().height);
	                    super.paint(g);
	                }
	        };
	        }
	    }
		);
		splitPane.setDividerSize(1);
	}

	/**
	 * Initialize the BottomBar. It contains some buttons
	 * and a status message.
	 */
	private void initBottomBar() {
		//Info Button:
		btnInfo = fontIconButtonFactory('\uf129', "Information about the Program", MainWindow.TEXTURED, null, bottomBarButtonSize);
		btnInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				infoPanel = new InfoAndUpdatePanel(main);
				setRightContent(infoPanel);
				main.setButtonsLocked(true);
			}
        }); 
		
		btnSettings = fontIconButtonFactory('\uf013', "Change the program settings.", MainWindow.TEXTURED, null, bottomBarButtonSize);
		btnSettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				settingsPanel = new SettingsPanel(settings, main);
				setRightContent(settingsPanel);
				main.setButtonsLocked(true);
			}
        });
		
		if("osx".equals(ClientApplication.OS)){
			BottomBar bottomBar = new BottomBar(BottomBarSize.LARGE);
		    bottomBar.addComponentToLeft(MacWidgetFactory.createEmphasizedLabel("Status"));  
			frmToxictodo.getContentPane().add(bottomBar.getComponent(), BorderLayout.SOUTH); 
			
			bottomBar.addComponentToRight(btnInfo);
			bottomBar.addComponentToRight(btnSettings);
		}else{
			JPanel bottomBar = new JPanel();
			bottomBar.setBackground(ToxicColors.TEXT_WHITE);
			FlowLayout flowLeft = new FlowLayout();
			flowLeft.setAlignment(FlowLayout.RIGHT);
			bottomBar.setLayout(flowLeft);
			bottomBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, ToxicColors.BORDER_GREY));
			frmToxictodo.getContentPane().add(bottomBar, BorderLayout.SOUTH);
			bottomBar.add(btnInfo);
			bottomBar.add(btnSettings);
		}
 
	}

	/**
	 * Initialize the taskPanel that is displayed on the right side
	 * of the default main window, aka the right splitPane. The taskPanel can
	 * be swapped out with other panels, e.g. "new category", "new task" etc.
	 */
	private void initTaskPanel() {
		taskListHeader = new PanelHeader();
	    
	    JScrollPane taskScrollPane = new JScrollPane();
		taskScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    
	    taskListPanel = new JPanel();
	    taskListPanel.setLayout(new BorderLayout(0, 0));
	    taskListPanel.add(taskListHeader, BorderLayout.NORTH);
	    taskListPanel.add(taskScrollPane, BorderLayout.CENTER);
	    
		splitPane.setRightComponent(taskListPanel);

		taskListModel = new TaskListModel(categoryListModel.getElementAt(0).getKeyword(), todoManager);
		taskList = new JList<TodoTask>(taskListModel);
		taskList.setCellRenderer(new TaskListCellRenderer());
		taskList.setBackground(ToxicColors.SOFT_GREY);
		
		taskScrollPane.setViewportView(taskList);
		taskScrollPane.setBackground(ToxicColors.SOFT_GREY);
		taskScrollPane.setBorder(new LineBorder(Color.black,0));
		
	    taskList.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent event){
        	  if (event.getClickCount() == 2) {
        		  if(specificTaskPanel == null){
        			  specificTaskPanel = new TaskPanel(main, todoManager);
        			  specificTaskPanel.loadTask(taskList.getSelectedValue());
        			  setRightContent(specificTaskPanel);
        			  main.setButtonsLocked(true);
        		  } else {
        			  setRightContent(specificTaskPanel);
        			  specificTaskPanel.loadTask(taskList.getSelectedValue());
        			  main.setButtonsLocked(true);
        		  }
        	  }
        	}
		});
	}

	/**
	 * Initialize the categoryPanel that is displayed on the left side
	 * of the default main window, aka the left splitPane.
	 *
	 * @param splitPaneMinimumSize
	 */
	private void initCategoryPanel() {
		Dimension splitPaneMinimumSize = new Dimension(0, 0);
		JScrollPane categoryListscrollPane = new JScrollPane();
		categoryListscrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		categoryListscrollPane.setBorder(new LineBorder(Color.black,0));
		
		JPanel categoryJPanel = new JPanel();
		categoryListscrollPane.setViewportView(categoryJPanel);
		categoryJPanel.setLayout(new BorderLayout(0, 0));
		
		categoryList = new JList<TodoCategory>(categoryListModel);
		categoryJPanel.add(categoryList, BorderLayout.CENTER);
		
		if("osx".equals(ClientApplication.OS)){
			categoryList.setBackground(ToxicColors.SOFT_BLUE);
		}else{
			categoryList.setBackground(ToxicColors.BACKGROUND_BLUE);
		}

		categoryList.setCellRenderer(new CategoryListCellRenderer());
		ListSelectionModel listSelectionModel = categoryList.getSelectionModel();
	    listSelectionModel.addListSelectionListener(new CategoryListSelectionHandler());
		
		splitPane.setLeftComponent(categoryListscrollPane);
		splitPane.getLeftComponent().setMinimumSize(splitPaneMinimumSize);
	}
	
	/**
	 * Create a new FontIconButton with all the proper settings.
	 * 
	 * @param icon
	 * @param tooltip
	 * @param textureType
	 * @param buttonPosition
	 * @param size
	 * @return
	 */
	private FontIconButton fontIconButtonFactory(char icon, String tooltip,
			String textureType, String buttonPosition, Dimension size) {
		final FontIconButton factoryButton;
		if("osx".equals(ClientApplication.OS)){
			factoryButton = new FontIconButton(icon, tooltip);
			factoryButton.putClientProperty("JButton.buttonType", textureType);
			if(buttonPosition != null){
				factoryButton.putClientProperty( "JButton.segmentPosition", buttonPosition);
			}
		}else{
			if(size.equals(uniBarButtonSize)){
				factoryButton = new FontIconButton(icon, tooltip, 28);
			}else{
				factoryButton = new FontIconButton(icon, tooltip, 14);
			}
			
			//Test
			final Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		    Insets insets = raisedBevelBorder.getBorderInsets(factoryButton);
		    final EmptyBorder emptyBorder = new EmptyBorder(insets);
		    factoryButton.setBorder(emptyBorder);
		    factoryButton.setFocusPainted(false);
		    factoryButton.setOpaque(false);
		    factoryButton.setContentAreaFilled(false);
		    factoryButton.getModel().addChangeListener(new ChangeListener() {
		        @Override
		        public void stateChanged(ChangeEvent e) {
		            ButtonModel model = (ButtonModel) e.getSource();
		            if (model.isRollover()) {
		            	factoryButton.setForeground(ToxicColors.SELECTION_BLUE);
		            } else if (!model.isEnabled()) {
		            	factoryButton.setForeground(ToxicColors.DISABLED_GREY);
		            } else {
		             	factoryButton.setForeground(ToxicColors.TEXT_GREY);
		            }
		        }
		    });
		}
		factoryButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		factoryButton.setHorizontalTextPosition(SwingConstants.CENTER);
		factoryButton.setPreferredSize(size);
		factoryButton.setMinimumSize(size);
		factoryButton.setMaximumSize(size);
		return factoryButton;
	}

	/*
	 * Creating a UNIFIED mac toolbar according to:
	 * http://jtechdev.com/2013/05/29/style-java-application-for-mac-os-x/
	 * 
	 * mac button theory:
	 * http://nadeausoftware.com/articles/2008/11/mac_java_tip_how_create_aqua_single_and_segmented_buttons
	 * 
	 */
	private void initToolbar(){
		initToolbarButtons();   
		
		searchField = new JTextField(10);
        searchField.putClientProperty("JTextField.variant", "search");
        searchField.getDocument().addDocumentListener(new SearchListener());
		
		if("osx".equals(ClientApplication.OS)){
			initUnifiedToolbarForOSX();
		} else {
			initNormalToolbarForLinuxAndWindows();
		}
	}

	private void initNormalToolbarForLinuxAndWindows() {
		JPanel toolbarTop = new JPanel();
		toolbarTop.setLayout(new BorderLayout());
		
		JPanel buttonsLeft = new JPanel();
		toolbarTop.add(buttonsLeft, BorderLayout.WEST);
		toolbarTop.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ToxicColors.BORDER_GREY));
		
		toolbarTop.setBackground(ToxicColors.TEXT_WHITE);
		frmToxictodo.getContentPane().add(toolbarTop, BorderLayout.NORTH);
		buttonsLeft.setBackground(ToxicColors.TEXT_WHITE);
		buttonsLeft.add(new ButtonTextGroup(btnNewTask,"New Task"));
		buttonsLeft.add(new ButtonTextGroup(btnCompleteTask,"Complete"));
		buttonsLeft.add(new ButtonTextGroup(btnRemoveTask,"Remove"));
		
		Component strut1 = Box.createHorizontalStrut(20);
		buttonsLeft.add(strut1);
		
		buttonsLeft.add(new ButtonTextGroup(btnNewCategory,"New category"));
		buttonsLeft.add(new ButtonTextGroup(btnEditCategory,"Edit category"));
		
		Component strut2 = Box.createHorizontalStrut(20);
		buttonsLeft.add(strut2);
		
		buttonsLeft.add(new ButtonTextGroup(btnCompletedTaskList,"Completed Tasks"));
		buttonsLeft.add(new ButtonTextGroup(btnStatistics,"Statistics"));
		
		Component strut3 = Box.createHorizontalStrut(20);
		buttonsLeft.add(strut3);
		
		buttonsLeft.add(new ButtonTextGroup(btnRefresh,"Refresh"));
		
		JPanel searchLeft = new JPanel();
		searchLeft.setBackground(ToxicColors.TEXT_WHITE);

		toolbarTop.add(searchLeft, BorderLayout.EAST);
		searchLeft.add(searchField);
	}

	private void initUnifiedToolbarForOSX() {
		UnifiedToolBar toolbarTop = new UnifiedToolBar();
		toolbarTop.installWindowDraggerOnWindow(frmToxictodo);
		frmToxictodo.getContentPane().add(toolbarTop.getComponent(), BorderLayout.NORTH);
		
		//Buttongroup "Tasks" 
		ButtonGroup taskGroup = new ButtonGroup();
		taskGroup.add(btnNewTask);
		taskGroup.add(btnCompleteTask);
		taskGroup.add(btnRemoveTask);  
		toolbarTop.addComponentToLeft(new LabeledComponentGroup("Tasks", taskGroup).getComponent());

		//Buttongroup "Category"
		ButtonGroup categoryGroup = new ButtonGroup();
		categoryGroup.add(btnNewCategory);
		categoryGroup.add(btnEditCategory);
		toolbarTop.addComponentToLeft(new LabeledComponentGroup("Categories", categoryGroup).getComponent());
		
		//Buttongroup "Statistics"
		ButtonGroup statsGroup = new ButtonGroup();
		statsGroup.add(btnCompletedTaskList);
		statsGroup.add(btnStatistics);
		toolbarTop.addComponentToLeft(new LabeledComponentGroup("Statistics", statsGroup).getComponent());
		
		//Button "Refresh"
		toolbarTop.addComponentToLeft(new LabeledComponentGroup("Synchronize", btnRefresh).getComponent());
		
		//Search:
		toolbarTop.addComponentToRight(new LabeledComponentGroup(" ",
				searchField).getComponent());
	}

	/**
	 * Initalize all the buttons in the toolbar
	 */
	private void initToolbarButtons() {
		//New Task:
		btnNewTask = fontIconButtonFactory('\uf15b', "Create a new task.",SEGMENTED_TEXTURED,POS_FIRST, uniBarButtonSize);
		btnNewTask.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(specificTaskPanel == null){
					specificTaskPanel = new TaskPanel(main, todoManager);
					specificTaskPanel.cleanTask();
					setRightContent(specificTaskPanel);
					main.setButtonsLocked(true);
				} else{
					setRightContent(specificTaskPanel);
					specificTaskPanel.cleanTask();
					main.setButtonsLocked(true);
				}
			}
        });  
		 
		//Complete Task:
		btnCompleteTask = fontIconButtonFactory('\uf00c',
				"Complete the selcted task.", SEGMENTED_TEXTURED, POS_MIDDLE,
				uniBarButtonSize);
		btnCompleteTask.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TodoTask task = getSelectedTask();
				TodoCategory categoryKey = getSelectedCategory();
				if(task != null && categoryKey != null){
					try {
						todoManager.removeTask(true, task, categoryKey.getKeyword(), frmToxictodo);
					} catch (IOException anEx) {
						Logger.log("Connection lost while trying to complete a task", anEx);
						main.connectionWarning();
					}
					taskListModel.filter(searchField.getText());
				}else{
					Logger.log("category or task is null");
				}
			}
        }); 
		
		//Remove Task:
		btnRemoveTask = fontIconButtonFactory('\uf014',
				"Remove the selected task without logging success.",
				SEGMENTED_TEXTURED, POS_LAST, uniBarButtonSize);
		btnRemoveTask.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TodoTask task = getSelectedTask();
				TodoCategory categoryKey = getSelectedCategory();
				if(task != null && categoryKey != null){
					try {
						todoManager.removeTask(false, task, categoryKey.getKeyword(), frmToxictodo);
					} catch (IOException anEx) {
						Logger.log("Connection lost while trying to remove a task", anEx);
						main.connectionWarning();
					}
					taskListModel.filter(searchField.getText());
				}else{
					Logger.log("category or task is null");
				}
			}
        });
		
		//New Category:
		btnNewCategory = fontIconButtonFactory('\uf07b',
				"Create a new category.",
				SEGMENTED_TEXTURED, POS_FIRST, uniBarButtonSize);
		btnNewCategory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(categoryPanel == null){
					categoryPanel = new CategoryPanel(main, todoManager);
					categoryPanel.newCategory();
					setRightContent(categoryPanel);
					main.setButtonsLocked(true);
				} else{
					categoryPanel.newCategory();
					setRightContent(categoryPanel);
					main.setButtonsLocked(true);
				}
			}
        });
		
		//Edit category:
		btnEditCategory =   fontIconButtonFactory('\uf040',
				"Edit an existing category.",
				SEGMENTED_TEXTURED, POS_LAST, uniBarButtonSize);
		btnEditCategory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TodoCategory editCategory = getSelectedCategory();
				//double-safety - we never want to edit all-tasks.
				if(editCategory.getKeyword()!=ToxicUIData.ALL_TASKS_TODOCATEGORY_KEY){
					if(categoryPanel == null){
						categoryPanel = new CategoryPanel(main, todoManager);
						categoryPanel.setCategory(editCategory);
						setRightContent(categoryPanel);
						main.setButtonsLocked(true);
					} else{
						categoryPanel.setCategory(getSelectedCategory());
						setRightContent(categoryPanel);
						main.setButtonsLocked(true);
					}
				}
			}
        });	
		
        //Historic/Completed Tasks:
		btnCompletedTaskList = fontIconButtonFactory('\uf022',
				"See a list of all completed tasks",
				SEGMENTED_TEXTURED, POS_FIRST, uniBarButtonSize);
		btnCompletedTaskList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Logger.log("NOT IMPLEMENTED YET");
			}
        });	
		
        //Statistics:
		btnStatistics = fontIconButtonFactory('\uf080',
				"See statistics about your progress.",
				SEGMENTED_TEXTURED, POS_LAST, uniBarButtonSize);
		btnStatistics.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statisticsPanel = new StatisticsPanel(main, todoManager);
				setRightContent(statisticsPanel);
				main.setButtonsLocked(true);
			}
        });	
		
		btnRefresh = fontIconButtonFactory('\uf021',
				"Synchronize this client to the server.",
				TEXTURED, null, uniBarButtonSize);
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					todoManager.updateList();
				} catch (IOException e1) {
					Logger.log("No connection to server", e1);
					main.connectionWarning();
				}
			}
        });
	}
	
	private Dimension toolbarButtonSize(){
		Dimension output;
		if("osx".equals(ClientApplication.OS)){
			output = new Dimension(50, 27);
		}else{
			output = new Dimension(65, 40);
		}
		return output;
	}
	
	public void switchToTasks(){
		searchField.setText("");
		splitPane.getRightComponent().setVisible(false);
		setRightContent(taskListPanel);
		setButtonsLocked(false);
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
	
	public void setButtonsLocked(boolean isLocked){
		btnNewTask.setEnabled(!isLocked);
		btnCompleteTask.setEnabled(!isLocked);
		btnRemoveTask.setEnabled(!isLocked);
		btnEditCategory.setEnabled(!isLocked);
		btnNewCategory.setEnabled(!isLocked);
		btnCompletedTaskList.setEnabled(!isLocked);
		btnStatistics.setEnabled(!isLocked);
		btnRefresh.setEnabled(!isLocked);
		btnSettings.setEnabled(!isLocked);
		btnInfo.setEnabled(!isLocked);
	}
	
	public TodoTask getSelectedTask(){
		return taskList.getSelectedValue();
	}
	
	public void connectionWarning(){
		JOptionPane.showMessageDialog(main.frmToxictodo,
			    "You've lost the connection to the server and we're unable to complete your request.",
			    "Connection lost",
			    JOptionPane.ERROR_MESSAGE);
		Logger.log("ERROR 8: lost connection to server");
	}
	
	public void genericWarning(String titel, String text){
		JOptionPane.showMessageDialog(main.frmToxictodo, text, titel, JOptionPane.ERROR_MESSAGE);
		Logger.log("ERROR: "+titel+" : "+text);
	}
	
	public void resetCategorySelection(){
		if(categoryList != null){
			categoryList.setSelectedIndex(0);
		}
	}
	
	class CategoryListSelectionHandler implements ListSelectionListener {
	    @Override
		public void valueChanged(ListSelectionEvent e) {
	    	ListSelectionModel lsm = (ListSelectionModel)e.getSource();
	    	TodoCategory currentCategory = categoryListModel.getElementAt(categoryList.getSelectedIndex());
	    	taskListModel.changeCategory(currentCategory.getKeyword());
	    	taskListHeader.setIcon(currentCategory.getIcon());
	    	taskListHeader.setTitel(currentCategory.getName().toUpperCase());
	    	taskListHeader.setSubTitel(getMotivationText());
	    	if(currentCategory.isSystemCategory()){
	    		btnNewTask.setEnabled(false);
	    		btnEditCategory.setEnabled(false);
	    	} else{
	    		btnNewTask.setEnabled(true);
	    		btnEditCategory.setEnabled(true);
	    	}
	    	if (lsm.isSelectionEmpty()) {
	        	//should be impossible to achieve
	    		Logger.log("empty selection, o'really?");
	        }
	    	taskListModel.filter(searchField.getText());
	    }
	    
	    /*
	     * Random motivational quotes:
	     * Source: http://www.forbes.com/sites/kevinkruse/2013/05/28/inspirational-quotes/
	     */
	    public String getMotivationText(){
	    	List<String> motivationArray = new ArrayList<String>();
	    	motivationArray.add("Strive not to be a success, but rather to be of value. – Albert Einstein");
	    	motivationArray.add("You miss 100% of the shots you don’t take. – Wayne Gretzky");
	    	motivationArray.add("Your time is limited, so don’t waste it living someone else’s life. – Steve Jobs");
	    	motivationArray.add("Start where you are. Use what you have.  Do what you can. – Arthur Ashe");
	    	motivationArray.add("I would rather die of passion than of boredom. – Vincent van Gogh");
	    	motivationArray.add("Education costs money. But then so does ignorance. – Sir Claus Moser");
	    	motivationArray.add("Dream big and dare to fail. – Norman Vaughan");
	    	motivationArray.add("The only way to do great work is to love what you do. – Steve Jobs");
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
