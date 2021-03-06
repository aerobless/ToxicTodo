package ch.theowinter.toxictodo.client.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fusesource.jansi.AnsiConsole;

import ch.theowinter.toxictodo.client.ClientApplication;
import ch.theowinter.toxictodo.client.ClientTodoManager;
import ch.theowinter.toxictodo.sharedobjects.Logger;
import ch.theowinter.toxictodo.sharedobjects.LogicEngine;
import ch.theowinter.toxictodo.sharedobjects.SharedInformation;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoCategory;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoTask;
import ch.theowinter.toxictodo.sharedobjects.elements.ToxicDatagram;

public class CliController {
	ClientTodoManager todoManager;
	
	//Integrated Systems
	private LogicEngine logic = new LogicEngine();
	
	//Local Bindings
	List<String> localCategoryBinding;
	List<TodoTask> localTaskBinding;
		
	/**
	 * Creates a new instance of this class.
	 *
	 * @param aTodoManager
	 */
	public CliController(ClientTodoManager aTodoManager) {
		super();
		todoManager = aTodoManager;
	}

	public void start(String[] args){
		print("CLI Controller in control of operations.");
		
		//2. Run manipulations (add/remove/etc.)
		ToxicDatagram datagramForServer = commandHandler(args);
		
		//3. Return answer to the server unless we're finished
		if(datagramForServer != null){
			try {
				ClientApplication.sendToServer(datagramForServer);
				voidDrawList();
			} catch (IOException e) {
				Logger.log("Unable to establish a connection with the server.", e);
				noConnectionError();
			}
		}
	}
	
	//TODO: add good description
	public void voidDrawList(){
		try {
			todoManager.setTodoList(ClientApplication.sendToServer(new ToxicDatagram("SEND_TODOLIST_TO_CLIENT")));
			ClientApplication.sendToServer(commandHandler(new String[]{"list"}));
			voidDrawList();
		} catch (IOException e) {
			Logger.log("Unable to establish a connection with the server.", e);
			noConnectionError();
		}
	}

	public void print(String input, int indentation){
		int charactersPerLine = ClientApplication.INITAL_SETTINGS.getConsoleSize();
		for(int i=charactersPerLine; i < input.length(); i+=charactersPerLine){
			input = new StringBuilder(input).insert(i, "\n").toString();
		}
		String indentStr = new String(new char[indentation]).replace('\0', ' ');
		String output = input.replaceAll("(?m)^", indentStr);
		print(output);
	}

	public void print(String input){
		AnsiConsole.out.println(input+JansiFormats.ANSI_NORMAL);
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
	public ToxicDatagram commandHandler(String[] userInputArray){
		ToxicDatagram datagram = null;
		if(argCheck(new String[]{"add","category","arg", "args"}, userInputArray)){
			datagram = addCategory(userInputArray);
		} else if (argCheck(new String[]{"list"}, userInputArray)){
			datagram = drawTodoList(false);
		} else if (argCheck(new String[]{"add","arg", "args"}, userInputArray)){
			datagram = addTask(userInputArray);
		} else if (argCheck(new String[]{"remove","category", "arg"}, userInputArray)){
			datagram = removeCategory(userInputArray);	
		} else if (argCheck(new String[]{"complete","task", "arg"}, userInputArray)){
			datagram = removeTask(userInputArray[2], true);
		} else if (argCheck(new String[]{"complete", "arg"}, userInputArray)){
			datagram = removeTask(userInputArray[1], true);
		} else if (argCheck(new String[]{"remove","task", "arg"}, userInputArray)){
			datagram = removeTask(userInputArray[2], false);
		} else if (argCheck(new String[]{"remove", "arg"}, userInputArray)){
			datagram = removeTask(userInputArray[1], false);
		} else if (argCheck(new String[]{"categories"}, userInputArray)||argCheck(new String[]{"list", "categories"}, userInputArray)){
			drawCategories();
		} else if (argCheck(new String[]{"about"}, userInputArray)||argCheck(new String[]{"info"}, userInputArray)||argCheck(new String[]{"identify"}, userInputArray)){
			drawAbout();
		} else if (argCheck(new String[]{"update"}, userInputArray)){
			updateTheClient();
		} else if (userInputArray.length>=1 && userInputArray[0].length()>0){
			print("Your command: "+Arrays.toString(userInputArray) +" was not recognized.");
		} else {
			/*
			 * We're exciting with System.exit() here because the user chose that he wants quit and
			 * we want to let him do that as fast as possible. Since this code is only executed in the
			 * CLI-version there are no windows or additional threads to clean. Also this is stand-alone
			 * application that isn't run on a server or java EE. So it's fine to use System.exit().
			 * 
			 * The //NOSONAR tag prevents this from showing up as a "major" issue on sonarQube code analysis server.
			 */
			System.exit(0); //NOSONAR
		}
		return datagram;
	}
	
	public boolean argCheck(String[] commands, String[] userArgs){
		boolean success = true;
		if(commands.length <= userArgs.length){
			for(int i = 0; i<commands.length; i++){
				if(!(commands[i].equals(userArgs[i]) || "args".equals(commands[i])|| "arg".equals(commands[i]))){
					success = false;
				}
			}
		} else{
			success = false;
		}
		return success;
	}
	
	/**
	 * Waits for user input and then handles it through the main commandHandler.
	 * @return datagram if the command was successfully recognize, otherwise null.
	 */
	private ToxicDatagram commandHandler(){
		String[] userInputArray  = readInput().split(" ");
		return commandHandler(userInputArray);
	}
	
	public String readInput(){
		String input = null;
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		try {
			input = buffer.readLine();
		} catch (IOException e) {
			Logger.log("Toxic Todo: INPUT IO Exception", e);
		}
		return input;
	}
	
	/**
	 * Draws the todoList, that means all categories that contain tasks unless you specify that you also want
	 * to list categories that are empty by setting "displayEmptyCategories" to true.
	 * @return launches the commandHandler() which in turn returns a datagram containing instructions for the server.
	 */
	public ToxicDatagram drawTodoList(boolean displayEmptyCategories){
		//Used for local bindings when we want to delete a task by entering 1
		List<String> internalCategoryBinding = new ArrayList<String>();
		List<TodoTask> internalTaskBinding = new ArrayList<TodoTask>();
		
		//Clear ANSI console
		print(JansiFormats.ANSI_CLS);
		
		int taskID = 0;
		for(String categoryKey : todoManager.getTodoList().getCategoryMap().keySet()){
			//Only list category if it contains tasks or we want to display empty categories too.
			if(todoManager.getTodoList().getCategoryMap().get(categoryKey).containsTasks() || displayEmptyCategories){
				print(JansiFormats.ANSI_BOLD+JansiFormats.CYAN+"###-"+todoManager.getTodoList().getCategoryMap().get(categoryKey).getName().toUpperCase()+"-###");
				for(String taskKey : todoManager.getTodoList().getCategoryMap().get(categoryKey).getTasksHashMap().keySet()){
					++taskID;
					print(JansiFormats.GREEN+"["+taskID+"] "+formatString(todoManager.getTodoList().getCategoryMap().get(categoryKey).getTasksHashMap().get(taskKey).getSummary()), 2);
					//adding task to local bindings map
					internalCategoryBinding.add(categoryKey);
					internalTaskBinding.add(todoManager.getTodoList().getCategoryMap().get(categoryKey).getTasksHashMap().get(taskKey));
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
				markers[i] = JansiFormats.RED+markers[i]+JansiFormats.GREEN;
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
	public ToxicDatagram drawCategories(){
		//Clear ANSI console
		print(JansiFormats.ANSI_CLS);
		for(String categoryKey : todoManager.getTodoList().getCategoryMap().keySet()){
			String category = todoManager.getTodoList().getCategoryMap().get(categoryKey).getName();
			int nofTasks = todoManager.getTodoList().getCategoryMap().get(categoryKey).getTasksHashMap().keySet().size();
			print(JansiFormats.ANSI_BOLD+JansiFormats.CYAN+"[<-"+category+"->]");
			print(JansiFormats.GREEN+"Category Key: "+categoryKey, 2);
			print(JansiFormats.GREEN+"Number of active tasks: "+nofTasks, 2);
		}
		return commandHandler();
	}
	
	/**
	 * Draws some pretty about the program stuff. Like author and copyright.
	 */
	public void drawAbout(){
		//Clear ANSI console
		print(JansiFormats.ANSI_CLS);
		print(JansiFormats.ANSI_BOLD+JansiFormats.CYAN+"### - ABOUT TOXIC TODO - ###");
		print(JansiFormats.GREEN+"Version: "+SharedInformation.VERSION, 2);
		print(JansiFormats.GREEN+"Author:  "+SharedInformation.AUTHOR, 2);
		print(JansiFormats.GREEN+"Website: "+SharedInformation.WEBSITE, 2);
	}
	
	//TODO: global update handler in todo manger or somewhere
	public void updateTheClient(){
		//Clear ANSI console
		print(JansiFormats.ANSI_CLS);
		print(JansiFormats.ANSI_BOLD+JansiFormats.CYAN+"### - TOXIC TODO UPDATER - ###");
		print(JansiFormats.GREEN+"Downloading latest release from CI-server...");
		if(logic.updateSoftware(ClientApplication.CLIENT_UPDATE_URL, false)){
			print(JansiFormats.GREEN+"The update has been successfully downloaded.");
			print(JansiFormats.GREEN+"Please let a few seconds pass before issuing a command to ToxicTodo");
		}
	}
	
	public ToxicDatagram removeTask(String task, boolean writeToLog) {
		ToxicDatagram datagram = null;
		if(localCategoryBinding==null||localTaskBinding==null){
			print("You can't blindly remove or complete tasks.");
		} else{
			try{
				int userChoice = Integer.parseInt(task);
				if(userChoice<=localCategoryBinding.size()){
					String dataMessage = "REMOVE_TASK_ON_SERVER";
					if(writeToLog){
						dataMessage = "REMOVE_AND_LOG_TASK_AS_COMPLETED_ON_SERVER";
					}
					datagram = new ToxicDatagram(dataMessage, localTaskBinding.get(userChoice-1), localCategoryBinding.get(userChoice-1));	
				} else{
					print("There's no task with that ID.");
				}
			} catch(NumberFormatException e){
				print("'"+task+"' is not a valid number.");
			}
		}
		return datagram;
	}
	
	public ToxicDatagram addTask(String[] args){
		String[] goodArgs = logic.concatenateArgs(args, 3);
		TodoTask task = new TodoTask(goodArgs[2]);
		return new ToxicDatagram("ADD_TASK_TO_LIST_ON_SERVER", task, goodArgs[1]);
	}
	
	public ToxicDatagram addCategory(String[] args){
		ToxicDatagram datagram = null;
		if(args.length>=4){
			String[] goodArgs = logic.concatenateArgs(args, 4);
			TodoCategory category = new TodoCategory(goodArgs[3], goodArgs[2]);
			datagram = new ToxicDatagram("ADD_CATEGORY_TO_LIST_ON_SERVER",null, category);
		} else{
			print("You can add a category like this:");
			print("add category keyword long category name");
		}
		return datagram;
	}
	
	public ToxicDatagram removeCategory(String[] args){
		ToxicDatagram datagram = null;
		if(args.length==3){
			TodoCategory category = new TodoCategory(args[2], args[2]);
			datagram = new ToxicDatagram("REMOVE_CATEGORY_ON_SERVER",null, category);
		} else{
			print("You can remove a category like this:");
			print("remove category keyword");
		}
		return datagram;	
	}
	
	private void noConnectionError(){
		print("ERROR: Unable to establish a connection with the server.");
		print("If you're running the server on a different IP or port, then you should change the client_config.xml!");
	}
}
