package ch.theowinter.ToxicTodo.utilities;

import java.util.ArrayList;

public class TodoCategory {
	String categoryName;
	ArrayList<String> todoElements = new ArrayList<String>();
	
	public TodoCategory(String categoryName) {
		super();
		this.categoryName = categoryName;
	}
	
	public void add(String todo){
		todoElements.add(todo);
	}
	
	public String getName(){
		return categoryName;
	}
	
	public ArrayList<String> getTodolist(){
		return todoElements;
	}
}
