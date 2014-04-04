package ch.theowinter.ToxicTodo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import ch.theowinter.ToxicTodo.utilities.JansiFormats;
import ch.theowinter.ToxicTodo.utilities.LogicEngine;
import ch.theowinter.ToxicTodo.utilities.primitives.TodoCategory;
import ch.theowinter.ToxicTodo.utilities.primitives.TodoList;
import ch.theowinter.ToxicTodo.utilities.primitives.TodoTask;
import ch.theowinter.ToxicTodo.utilities.primitives.ToxicDatagram;

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
	
	public ToxicDatagram run(String[] arguments){
		ToxicDatagram datagram = null;
		ClientToxicTodo.print("app started", false);
		if(arguments.length == 0){
			ClientToxicTodo.print("Toxic Todo Version 0.2 - Please specify some arguments first.");
			datagram = drawTodoList(false);			
		}
		else if(arguments[0].equals("list")){
			datagram = drawTodoList(false);
		}
		else if(arguments[0].equals("clist")){
			//Display complete list
			datagram = drawTodoList(true);
		}
		else if(arguments[0].equals("remove")){
			datagram = removeCategory(arguments);
		}
		else if(arguments[0].equals("add")){
			if(arguments.length>=2 && arguments[1].equals("category")){
				datagram = addCategory(arguments);
			}
			else{
				datagram = addTask(arguments);
			}
		}
		else{
			ClientToxicTodo.print("This command: "+arguments[0]+" does not exist.");
		}
		return datagram;
	}
	
	/**
	 * Draw the todolist, that means all categories that contain tasks.
	 * @return 
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
				ClientToxicTodo.print(jansi.ANSI_BOLD+jansi.CYAN+"###-"+todoList.getCategoryMap().get(categoryKey).getName().toUpperCase()+"-###"+jansi.ANSI_NORMAL);
				todoList.getCategoryMap().get(categoryKey).getTasksHashMap();
				for(String taskKey : todoList.getCategoryMap().get(categoryKey).getTasksHashMap().keySet()){
					++taskID;
					ClientToxicTodo.print(jansi.GREEN+"    ["+taskID+"] "+todoList.getCategoryMap().get(categoryKey).getTasksHashMap().get(taskKey).getTaskText()+jansi.ANSI_NORMAL);
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

	/* 
	 * Originally intended to handle the remove command. Then I refactored it to handle all commands
	 * from the list view. But now I've decided to let it handle all commands over the entire client-side.
	 * So even commandline-args get handled here.
	 */
	
	/**
	 * Handles commands issued by the user as commandline arg or in list-view.
	 * @return datagram if the command was successfully recognize, otherwise null.
	 */
	private ToxicDatagram commandHandler(){
		ToxicDatagram datagram = null;
		String[] userInputArray  = readInput().split(" ");
		if(argCheck(new String[]{"add","category","arg", "args"}, userInputArray)){
			datagram = addCategory(userInputArray);
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
		else {
			ClientToxicTodo.print("Your command: "+Arrays.toString(userInputArray) +" was not recognized.");
		}
		return datagram;
	}

	private ToxicDatagram removeTask(String task, boolean writeToLog) {
		ToxicDatagram datagram = null;
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
