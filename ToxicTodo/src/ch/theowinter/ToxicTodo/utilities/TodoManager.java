package ch.theowinter.ToxicTodo.utilities;

import java.util.ArrayList;
import java.util.Map;

import ch.theowinter.ToxicTodo.utilities.primitives.TodoCategory;
import ch.theowinter.ToxicTodo.utilities.primitives.TodoTask;

public class TodoManager{
	//Storage
	ArrayList<TodoCategory> totalTodoList = new ArrayList<TodoCategory>();
	LogicEngine logic = new LogicEngine();
	
	//Switches
	boolean debug = true;

	/**
	 * Empty constructor
	 */
	public TodoManager() {
	}
	
	/**
	 * Full constructor
	 * 
	 * @param totalTodoList
	 * @param debug
	 */
	public TodoManager(ArrayList<TodoCategory> totalTodoList, boolean debug) {
		super();
		this.totalTodoList = totalTodoList;
		this.debug = debug;
	}

	public ArrayList<TodoCategory> getTotalTodoList() {
		return totalTodoList;
	}

	public void setTotalTodoList(ArrayList<TodoCategory> totalTodoList) {
		this.totalTodoList = totalTodoList;
	}

	public void run(String[] args) {
		print("app started", debug);
		if(args.length == 0){
			print("Toxic Todo Version 0.1 - Please specify some arguments first.");
		}
		else if(args[0].equals("list")){
			//List all categories and the tasks inside
			//TODO: perhaps only list categories that have tasks
			listTodo();
		}
		else if(args[0].equals("clist")){
			//list only categories
			listCategories();
		}
		else if(args[0].equals("add")){
			String[] goodArgs = logic.concatenateArgs(args, 3);
			TodoCategory todoCat= addTaskToCategory(goodArgs[1], goodArgs[2]);
			if(todoCat != null){
				print("added a new task to category: "+todoCat.getName());
			}
			else{
				print("Unable to add a new task for keyword: "+goodArgs[1]+" Are you sure you spelled it right?");
			}
		}
	}
	
	public void addCategory(String categoryName, String keyword){
		TodoCategory todoCategory = new TodoCategory(categoryName, keyword);
		totalTodoList.add(todoCategory);
	}
	
	public void removeCategory(String categoryName){
		for(TodoCategory todoCategory : totalTodoList){
			if(todoCategory.getName().equals(categoryName)){
				totalTodoList.remove(todoCategory);
			}
		}
	}
	
	public TodoCategory addTaskToCategory(String keyword, String text){
		TodoCategory returnCat = null;
		for(TodoCategory todoCategory : totalTodoList){
			if(todoCategory.getKeyword().equals(keyword)){
				todoCategory.add(text);
				returnCat = todoCategory;
			}
		}
		return returnCat;
	}
	
	public int categorySize(){
		return totalTodoList.size();
	}
	
	public void listCategories(){
		for(TodoCategory todoCategory : totalTodoList){
			print(" -"+todoCategory.getName());
		}
	}
	
	public void listTodo(){
		int taskID= 0;
		//Map<int, double> 
		for(TodoCategory todoCategory : totalTodoList){
			print("###-"+todoCategory.getName().toUpperCase()+"-###");
			ArrayList<TodoTask> todoElements = todoCategory.getElementsInCategory();
			for(TodoTask todoElement : todoElements){
				++taskID;
				print("    ["+taskID+"] "+todoElement);
			}
		}
		//return 
	}
	
	public void completeTask(){
		
	}
	
	public int todoSize(){
		int todoSize = 0;
		for(TodoCategory todoCategory : totalTodoList){
			todoSize += todoCategory.size();
		}
		return todoSize;
	}
	
	public void print(String input){
		//TODO: add support for colours and stuff
		System.out.println(input);
	}
	
	public void print(String input, boolean debug){
		if(debug == true){
			//TODO: add support for colours and stuff
			System.out.println("DEBUG INFO:"+input);
		}
	}
}
