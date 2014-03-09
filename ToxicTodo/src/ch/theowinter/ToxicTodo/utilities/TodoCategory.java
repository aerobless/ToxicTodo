package ch.theowinter.ToxicTodo.utilities;

import java.util.ArrayList;

public class TodoCategory {
	String categoryName;
	String keyword;
	ArrayList<String> todoElements = new ArrayList<String>();
	
	public TodoCategory(String categoryName, String keyword) {
		super();
		this.categoryName = categoryName;
		this.keyword = keyword;
	}
	
	public void add(String todo){
		todoElements.add(todo);
	}
	
	public String getName(){
		return categoryName;
	}
	
	public ArrayList<String> getElementsInCategory(){
		return todoElements;
	}
	
	public String getKeyword(){
		return keyword;
	}
}
