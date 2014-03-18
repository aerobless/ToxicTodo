package ch.theowinter.ToxicTodo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ch.theowinter.ToxicTodo.utilities.JansiFormats;
import ch.theowinter.ToxicTodo.utilities.LogicEngine;
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
		ClientToxicTodo.print("app started", ClientToxicTodo.debug);
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
		else if(args[0].equals("add")){
			datagram = addTask(args);
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
		if(userInputArray.length>=2){
				if(userInputArray[0].equals("add")){
					datagram = addTask(userInputArray);
				}
				//Removing & Completing
				else{
					try{
						int userChoice = Integer.parseInt(userInputArray[1]);
						if(userChoice<=localCategoryBinding.size()){
							if(userInputArray[0].equals("complete")){
								datagram = new ToxicDatagram("REMOVE_AND_LOG_TASK_AS_COMPLETED_ON_SERVER", "", localTaskBinding.get(userChoice-1), localCategoryBinding.get(userChoice-1)); //minus 1 because we draw numbers from 1 upwords and array starts at 0
							}
							else if(userInputArray[0].equals("remove")){
								datagram = new ToxicDatagram("REMOVE_TASK_ON_SERVER", "", localTaskBinding.get(userChoice-1), localCategoryBinding.get(userChoice-1));
							}
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
