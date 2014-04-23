package ch.theowinter.ToxicTodo.client.CLI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import ch.theowinter.ToxicTodo.utilities.JansiFormats;
import ch.theowinter.ToxicTodo.utilities.LogicEngine;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoCategory;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoList;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoTask;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.ToxicDatagram;

public class ClientTodoManager {
	//Integrated Systems
	private LogicEngine logic = new LogicEngine();
	private JansiFormats jansi = new JansiFormats();
	
	//Class variables
	TodoList todoList;
	ArrayList<String> localCategoryBinding;
	ArrayList<TodoTask> localTaskBinding;

	public ClientTodoManager(TodoList todoList) {
		super();
		this.todoList = todoList;
	}
	
	/* 
	 * Originally intended to handle the remove command. Then I refactored it to handle all commands
	 * from the list view. But now I've decided to let it handle all commands over the entire client-side.
	 * So even commandline-args get handled here.
	 */
	/**
	 * Handles commands issued by the user as commandline arg or in list-view.
	 * @return datagram if the command was successfully recognize, otherwise null.
	 */
	protected ToxicDatagram commandHandler(String[] userInputArray){
		ToxicDatagram datagram = null;
		if(argCheck(new String[]{"add","category","arg", "args"}, userInputArray)){
			datagram = addCategory(userInputArray);
		}
		else if(argCheck(new String[]{"list"}, userInputArray)){
			datagram = drawTodoList(false);
		}
		else if(argCheck(new String[]{"add","arg", "args"}, userInputArray)){
			datagram = addTask(userInputArray);
		}
		else if(argCheck(new String[]{"remove","category", "arg"}, userInputArray)){
			datagram = removeCategory(userInputArray);	
		}
		else if(argCheck(new String[]{"complete","task", "arg"}, userInputArray)){
			datagram = removeTask(userInputArray[2], true);
		}
		else if(argCheck(new String[]{"complete", "arg"}, userInputArray)){
			datagram = removeTask(userInputArray[1], true);
		}
		else if(argCheck(new String[]{"remove","task", "arg"}, userInputArray)){
			datagram = removeTask(userInputArray[2], false);
		}
		else if(argCheck(new String[]{"remove", "arg"}, userInputArray)){
			datagram = removeTask(userInputArray[1], false);
		}
		else if(argCheck(new String[]{"categories"}, userInputArray)||argCheck(new String[]{"list", "categories"}, userInputArray)){
			drawCategories();
		}
		else if(argCheck(new String[]{"about"}, userInputArray)||argCheck(new String[]{"info"}, userInputArray)||argCheck(new String[]{"identify"}, userInputArray)){
			drawAbout();
		}
		else if(argCheck(new String[]{"update"}, userInputArray)){
			updateTheClient();
		}
		else if(userInputArray.length>=1 && !userInputArray[0].equals("")){
			ClientToxicTodo.print("Your command: "+Arrays.toString(userInputArray) +" was not recognized.");
		}
		return datagram;
	}
	
	/**
	 * Waits for user input and then handles it through the main commandHandler.
	 * @return datagram if the command was successfully recognize, otherwise null.
	 */
	private ToxicDatagram commandHandler(){
		String[] userInputArray  = readInput().split(" ");
		return commandHandler(userInputArray);
	}

	
	/**
	 * Draws the todoList, that means all categories that contain tasks unless you specify that you also want
	 * to list categories that are empty by setting "displayEmptyCategories" to true.
	 * @return launches the commandHandler() which in turn returns a datagram containing instructions for the server.
	 */
	private ToxicDatagram drawTodoList(boolean displayEmptyCategories){
		//Used for local bindings when we want to delete a task by entering 1
		ArrayList<String> internalCategoryBinding = new ArrayList<String>();
		ArrayList<TodoTask> internalTaskBinding = new ArrayList<TodoTask>();
		
		//Clear ANSI console
		ClientToxicTodo.print(jansi.ANSI_CLS);
		
		int taskID = 0;
		for(String categoryKey : todoList.getCategoryMap().keySet()){
			//Only list category if it contains tasks or we want to display empty categories too.
			if(todoList.getCategoryMap().get(categoryKey).containsTasks() || displayEmptyCategories==true){
				ClientToxicTodo.print(jansi.ANSI_BOLD+jansi.CYAN+"###-"+todoList.getCategoryMap().get(categoryKey).getName().toUpperCase()+"-###");
				//todoList.getCategoryMap().get(categoryKey).getTasksHashMap();
				for(String taskKey : todoList.getCategoryMap().get(categoryKey).getTasksHashMap().keySet()){
					++taskID;
					ClientToxicTodo.print(jansi.GREEN+"["+taskID+"] "+formatString(todoList.getCategoryMap().get(categoryKey).getTasksHashMap().get(taskKey).getTaskText()), 2);
					//adding task to local bindings map
					internalCategoryBinding.add(categoryKey);
					internalTaskBinding.add(todoList.getCategoryMap().get(categoryKey).getTasksHashMap().get(taskKey));
				}
			}
		}
		//Update local bindings:
		localCategoryBinding = internalCategoryBinding;
		localTaskBinding = internalTaskBinding;
		return commandHandler();
	}
	
	/**
	 * Used in drawTodoList to color important **text**.
	 * @return String without **, containing jansi.RED instead.
	 */
	public String formatString(String input){
		String[] markers = input.split("\\*\\*");
		for(int i = 0; i<markers.length; i++){
			if(!(i%2==0)){
				markers[i] = jansi.RED+markers[i]+jansi.GREEN;
			}
		}
		StringBuilder outputBuilder = new StringBuilder();
		for(String s : markers) {
			outputBuilder.append(s);
		}
		return outputBuilder.toString();
	}
	
	/**
	 * Draws all categories (incl. empty categories) and lists their keyword
	 * and how many active tasks they contain.
	 * @return launches the commandHandler() which in turn returns a datagram containing instructions for the server.
	 */
	private ToxicDatagram drawCategories(){
		//Clear ANSI console
		ClientToxicTodo.print(jansi.ANSI_CLS);
		for(String categoryKey : todoList.getCategoryMap().keySet()){
			String category = todoList.getCategoryMap().get(categoryKey).getName();
			int nofTasks = todoList.getCategoryMap().get(categoryKey).getTasksHashMap().keySet().size();
			ClientToxicTodo.print(jansi.ANSI_BOLD+jansi.CYAN+"[<-"+category+"->]");
			ClientToxicTodo.print(jansi.GREEN+"Category Key: "+categoryKey, 2);
			ClientToxicTodo.print(jansi.GREEN+"Number of active tasks: "+nofTasks, 2);
		}
		return commandHandler();
	}
	/**
	 * Draws some pretty about the program stuff. Like author and copyright.
	 */
	private void drawAbout(){
		//Clear ANSI console
		ClientToxicTodo.print(jansi.ANSI_CLS);
		ClientToxicTodo.print(jansi.ANSI_BOLD+jansi.CYAN+"### - ABOUT TOXIC TODO - ###");
		ClientToxicTodo.print(jansi.GREEN+"Version: "+ClientToxicTodo.clientVersion, 2);
		ClientToxicTodo.print(jansi.GREEN+"Author:  "+ClientToxicTodo.author, 2);
		ClientToxicTodo.print(jansi.GREEN+"Website: "+ClientToxicTodo.website, 2);
	}
	
	private void updateTheClient(){
		//Clear ANSI console
		ClientToxicTodo.print(jansi.ANSI_CLS);
		ClientToxicTodo.print(jansi.ANSI_BOLD+jansi.CYAN+"### - TOXIC TODO UPDATER - ###");
		ClientToxicTodo.print(jansi.GREEN+"Downloading latest release from CI-server...");
		if(logic.updateSoftware(ClientToxicTodo.clientUpdateURL)){
			ClientToxicTodo.print(jansi.GREEN+"The update has been successfully downloaded.");
			ClientToxicTodo.print(jansi.GREEN+"Please let a few seconds pass before issuing a command to ToxicTodo");
		}
	}

	private ToxicDatagram removeTask(String task, boolean writeToLog) {
		ToxicDatagram datagram = null;
		if(localCategoryBinding==null||localTaskBinding==null){
			ClientToxicTodo.print("You can't blindly remove or complete tasks.");
		}
		else{
			try{
				int userChoice = Integer.parseInt(task);
				if(userChoice<=localCategoryBinding.size()){
					String dataMessage = "REMOVE_TASK_ON_SERVER";
					if(writeToLog){
						dataMessage = "REMOVE_AND_LOG_TASK_AS_COMPLETED_ON_SERVER";
					}
					datagram = new ToxicDatagram(dataMessage, "", localTaskBinding.get(userChoice-1), localCategoryBinding.get(userChoice-1));	
				}
				else{
					ClientToxicTodo.print("There's no task with that ID.");
				}
			} catch(NumberFormatException e){
				ClientToxicTodo.print("'"+task+"' is not a valid number.");
			}
		}
		return datagram;
	}
	
	private boolean argCheck(String[] commands, String[] userArgs){
		boolean success = true;
		if(commands.length <= userArgs.length){
			for(int i = 0; i<commands.length; i++){
				if(!(commands[i].equals(userArgs[i]) || commands[i].equals("args")|| commands[i].equals("arg"))){
					success = false;
				}
			}
		}
		else{
			success = false;
		}
		return success;
	}
	
	private ToxicDatagram addTask(String[] args){
		String[] goodArgs = logic.concatenateArgs(args, 3);
		TodoTask task = new TodoTask(goodArgs[2]);
		ToxicDatagram datagram = new ToxicDatagram("ADD_TASK_TO_LIST_ON_SERVER", "", task, goodArgs[1]);
		return datagram;
	}
	
	private ToxicDatagram addCategory(String[] args){
		ToxicDatagram datagram = null;
		if(args.length>=4){
			String[] goodArgs = logic.concatenateArgs(args, 4);
			TodoCategory category = new TodoCategory(goodArgs[3], goodArgs[2]);
			datagram = new ToxicDatagram("ADD_CATEGORY_TO_LIST_ON_SERVER", "",category);
		}
		else{
			ClientToxicTodo.print("You can add a category like this:");
			ClientToxicTodo.print("add category keyword long category name");
		}
		return datagram;
	}
	
	private ToxicDatagram removeCategory(String[] args){
		ToxicDatagram datagram = null;
		if(args.length==3){
			TodoCategory category = new TodoCategory(args[2], args[2]);
			datagram = new ToxicDatagram("REMOVE_CATEGORY_ON_SERVER", "",category);
		}
		else{
			ClientToxicTodo.print("You can remove a category like this:");
			ClientToxicTodo.print("remove category keyword");
		}
		return datagram;	
	}	
	
	private String readInput(){
		String input = null;
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		try {
			input = buffer.readLine();
		} catch (IOException e) {
			System.err.println("Toxic Todo: INPUT IO Exception");
		}
		return input;
	}
}