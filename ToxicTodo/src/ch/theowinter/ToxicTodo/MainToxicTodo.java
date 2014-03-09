package ch.theowinter.ToxicTodo;

import java.util.ArrayList;

import ch.theowinter.ToxicTodo.utilities.TodoCategory;

public class MainToxicTodo {
	ArrayList<TodoCategory> totalTodoList = new ArrayList<TodoCategory>();

	public void main(String[] args) {
		System.out.println("app started");
	}
	
	public void addCategory(String categoryName){
		TodoCategory todoCategory = new TodoCategory(categoryName);
		totalTodoList.add(todoCategory);
	}
	
	public void removeCategory(String categoryName){
		for(TodoCategory todoCategory : totalTodoList){
			if(todoCategory.getName().equals(categoryName)){
				totalTodoList.remove(todoCategory);
			}
		}
	}
	
	public int categorySize(){
		return totalTodoList.size();
	}
	
	public void listCategories(){
		for(TodoCategory todoCategory : totalTodoList){
			print(todoCategory.getName());
		}
	}
	
	public void print(String input){
		//TODO: add support for colours and stuff
		System.out.println(input);
	}
}
