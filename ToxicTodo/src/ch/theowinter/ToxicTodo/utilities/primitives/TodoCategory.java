package ch.theowinter.ToxicTodo.utilities.primitives;

import java.io.Serializable;
import java.util.ArrayList;

public class TodoCategory implements Serializable {
	private static final long serialVersionUID = 2851662981886514578L;
	String categoryName;
	String keyword;
	ArrayList<TodoTask> todoTaskList = new ArrayList<TodoTask>();
	
	public TodoCategory(String categoryName, String keyword) {
		super();
		this.categoryName = categoryName;
		this.keyword = keyword;
	}
	
	public void add(String todo){
		todoTaskList.add(new TodoTask(todo));
	}
	
	public String getName(){
		return categoryName;
	}
	
	public ArrayList<TodoTask> getElementsInCategory(){
		return todoTaskList;
	}
	
	public String getKeyword(){
		return keyword;
	}
	
	public int size(){
		return todoTaskList.size();
	}
	
	public TodoTask remove(int i){
		return todoTaskList.remove(i);
	}
}
