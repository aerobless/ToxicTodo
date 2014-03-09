package ch.theowinter.ToxicTodo.utilities;

import java.util.ArrayList;

public class TodoCategory {
	String categoryName;
	String keyword;
	ArrayList<String> todoTasks = new ArrayList<String>();
	
	public TodoCategory(String categoryName, String keyword) {
		super();
		this.categoryName = categoryName;
		this.keyword = keyword;
	}
	
	public void add(String todo){
		todoTasks.add(todo);
	}
	
	public String getName(){
		return categoryName;
	}
	
	public ArrayList<String> getElementsInCategory(){
		return todoTasks;
	}
	
	public String getKeyword(){
		return keyword;
	}
	
	public int size(){
		return todoTasks.size();
	}
}
