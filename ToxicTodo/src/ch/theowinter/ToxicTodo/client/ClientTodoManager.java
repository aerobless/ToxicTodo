package ch.theowinter.ToxicTodo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ch.theowinter.ToxicTodo.utilities.JansiFormats;
import ch.theowinter.ToxicTodo.utilities.LogicEngine;
import ch.theowinter.ToxicTodo.utilities.primitives.TodoCategory;
import ch.theowinter.ToxicTodo.utilities.primitives.TodoList;
import ch.theowinter.ToxicTodo.utilities.primitives.TodoTask;
import ch.theowinter.ToxicTodo.utilities.primitives.ToxicDatagram;

public class ClientTodoManager {
	TodoList todoList;
	private LogicEngine logic = new LogicEngine();
	private JansiFormats jansi = new JansiFormats();

	public ClientTodoManager(TodoList todoList) {
		super();
		this.todoList = todoList;
	}
	
	public ToxicDatagram run(String[] args){
		ToxicDatagram datagram = null;
		ClientToxicTodo.print("app started", false);
		if(args.length == 0){
			ClientToxicTodo.print("Toxic Todo Version 0.2 - Please specify some arguments first.");
		}
		else if(args[0].equals("list")){
			datagram = drawTodoList(false);
		}
		else if(args[0].equals("clist")){
			//Display complete list
			datagram = drawTodoList(true);
		}
		else if(args[0].equals("remove")){
			datagram = removeCategory(args);
		}
		else if(args[0].equals("add")){
			if(args.length>=2 && args[1].equals("category")){
				datagram = addCategory(args);
			}
			else{
				datagram = addTask(args);
			}
		}
		else{
			ClientToxicTodo.print("This command: "+args[0]+" does not exist.");
		}
		return datagram;
	}
	
	/**
	 * Draw the todolist, that means all categories that contain tasks.
	 * @return 
	 */
	private ToxicDatagram drawTodoList(boolean displayEmptyCategories){
		//Used for local bindings when we want to delete a task by entering 1
		ArrayList<String> localCategoryBinding = new ArrayList<String>();
		ArrayList<TodoTask> localTaskBinding = new ArrayList<TodoTask>();
		
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
					localCategoryBinding.add(categoryKey);
					localTaskBinding.add(todoList.getCategoryMap().get(categoryKey).getTasksHashMap().get(taskKey));
				}
			}
		}
		//Use inListActionHandler to check if user wants to remove or complete as task, if empty enter --> the program exits
		return inListActionHandler(localCategoryBinding, localTaskBinding);
	}

	//inListActionHandler belongs to drawTodoList
	private ToxicDatagram inListActionHandler(ArrayList<String> localCategoryBinding, ArrayList<TodoTask> localTaskBinding){
		ToxicDatagram datagram = null;
		String[] userInputArray  = readInput().split(" ");
		// adding task -  "add school xy" = 3args
		if(userInputArray.length>=3){
			if(userInputArray[0].equals("add") && userInputArray[1].equals("category")){
				datagram = addCategory(userInputArray);
				}
			else if(userInputArray[0].equals("add")){
				datagram = addTask(userInputArray);
				}
			else if (userInputArray[0].equals("remove") && userInputArray[1].equals("category")){
					datagram = removeCategory(userInputArray);		
				}
			else if (userInputArray[0].equals("complete") && userInputArray[1].equals("task")){
				try{
					int userChoice = Integer.parseInt(userInputArray[2]);
					if(userChoice<=localCategoryBinding.size()){
						datagram = new ToxicDatagram("REMOVE_AND_LOG_TASK_AS_COMPLETED_ON_SERVER", "", localTaskBinding.get(userChoice-1), localCategoryBinding.get(userChoice-1)); 
					}
					else{
						ClientToxicTodo.print("There's no task with that ID.");
					}
				} catch(NumberFormatException e){
					ClientToxicTodo.print("Please enter a valid number.");
				}
			}
			
			else if (userInputArray[0].equals("remove") && userInputArray[1].equals("task")){
				try{
					int userChoice = Integer.parseInt(userInputArray[2]);
					if(userChoice<=localCategoryBinding.size()){
						datagram = new ToxicDatagram("REMOVE_TASK_ON_SERVER", "", localTaskBinding.get(userChoice-1), localCategoryBinding.get(userChoice-1));	
					}
					else{
						ClientToxicTodo.print("There's no task with that ID.");
					}
				} catch(NumberFormatException e){
					ClientToxicTodo.print("Please enter a valid number.");
				}
			}
		}
		return datagram;
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
