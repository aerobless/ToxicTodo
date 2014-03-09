package ch.theowinter.ToxicTodo;

import java.util.ArrayList;

import ch.theowinter.ToxicTodo.utilities.TodoCategory;

public class MainToxicTodo {
	//Storage
	ArrayList<TodoCategory> totalTodoList = new ArrayList<TodoCategory>();
	
	//Switches
	boolean debug = true;

	public void main(String[] args) {
		print("app started", debug);
		if(args[0].equals("list")){
			listTodo();
		}
		else if(args[0].equals("clist")){
			listCategories();
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
	
	public void addElementToCategory(String keyword, String text){
		for(TodoCategory todoCategory : totalTodoList){
			if(todoCategory.getKeyword().equals(keyword)){
				todoCategory.add(text);
			}
		}
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
		for(TodoCategory todoCategory : totalTodoList){
			print("###-"+todoCategory.getName().toUpperCase()+"-###");
			ArrayList<String> todoElements = todoCategory.getElementsInCategory();
			for(String todoElement : todoElements){
				print("    + "+todoElement);
			}
		}
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
