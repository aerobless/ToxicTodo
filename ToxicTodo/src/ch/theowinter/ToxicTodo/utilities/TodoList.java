package ch.theowinter.ToxicTodo.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TodoList {
	//Storage
	ArrayList<TodoCategory> totalTodoList = new ArrayList<TodoCategory>();
	LogicEngine logic = new LogicEngine();
	
	//Switches
	boolean debug = true;

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
		ArrayList<String[]> mapTasks = new ArrayList<String[]>();
		mapTasks.add(new String[]{"first one is empty","because we start at 1"});
		for(TodoCategory todoCategory : totalTodoList){
			print("###-"+todoCategory.getName().toUpperCase()+"-###");
			ArrayList<String> todoElements = todoCategory.getElementsInCategory();
			for(String todoElement : todoElements){
				++taskID;
				mapTasks.add(new String[] {todoElement, todoCategory.getKeyword()});
				print("    ["+taskID+"] "+todoElement);
			}
		}
		//DELETE PART
		System.out.println("wanna delete test?");
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		String line;
		try {
			line = buffer.readLine();
			int n = Integer.parseInt(line);
			for(TodoCategory todoCategory : totalTodoList){
				if(todoCategory.getKeyword().equals(mapTasks.get(n)[1])){
					int insideIndex = todoCategory.getElementsInCategory().indexOf(mapTasks.get(n)[0]);
					todoCategory.remove(insideIndex);
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
