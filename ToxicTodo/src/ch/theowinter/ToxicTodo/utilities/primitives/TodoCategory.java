package ch.theowinter.ToxicTodo.utilities.primitives;

import java.io.Serializable;
import java.util.ArrayList;

public class TodoCategory implements Serializable {
	private static final long serialVersionUID = 2851662981886514578L;
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
	
	public String remove(int i){
		return todoTasks.remove(i);
	}
}
