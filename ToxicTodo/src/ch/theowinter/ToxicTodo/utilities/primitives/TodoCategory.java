package ch.theowinter.ToxicTodo.utilities.primitives;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class TodoCategory implements Serializable {
	private static final long serialVersionUID = 2851662981886514578L;
	private String categoryName;
	private String keyword;
	private HashMap<String, TodoTask> todoTaskList = new HashMap<String, TodoTask>();
	
	public TodoCategory(String categoryName, String keyword) {
		super();
		this.categoryName = categoryName;
		this.keyword = keyword;
	}
	
	
	public void add(TodoTask todo){
		todoTaskList.put(todo.getTaskText(), todo);
	}
	
	@Deprecated
	public void add(String todo){
		todoTaskList.put(todo, new TodoTask(todo));
	}
	
	public String getName(){
		return categoryName;
	}
	
	public HashMap<String, TodoTask> getTasksHashMap(){
		return todoTaskList;
	}
	
	public ArrayList<TodoTask> getTaskInCategoryAsArrayList(){
		return new ArrayList<TodoTask>(todoTaskList.values());
	}
	
	public String getKeyword(){
		return keyword;
	}
	
	public int size(){
		return todoTaskList.size();
	}
	
	public TodoTask removeTask(String key){
		return todoTaskList.remove(key);
	}
	
	public TodoTask get(String key){
		return todoTaskList.get(key);
	}
	
	/**
	 * Check whether a category contains tasks
	 * or whether it can be safely deleted.
	 * @return boolean
	 */
	public boolean containsTasks(){
		boolean containsTasks = false;
		if(todoTaskList.size()>0){
			containsTasks = true;
		}
		return containsTasks;
	}
}
