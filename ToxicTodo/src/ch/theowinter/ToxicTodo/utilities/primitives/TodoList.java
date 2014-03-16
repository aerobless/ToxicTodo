package ch.theowinter.ToxicTodo.utilities.primitives;

import java.util.HashMap;

/**
 * Provides high-level access to todoTasks and todoCategories
 * @author theowinter
 */
public class TodoList {
	HashMap<String, TodoCategory> categoryMap = new HashMap<String, TodoCategory>();

	/**
	 * Add a new category to the todoList. A category consists of a "long" name and
	 * a short keyword. The keyword is used to add tasks.
	 * 
	 * @param categoryName
	 * @param keyword
	 * @throws Exception 
	 */
	public void addCategory(String categoryName, String keyword) throws Exception{
		if(categoryMap.get(keyword)==null){
			TodoCategory newCategory = new TodoCategory(categoryName, keyword);
			categoryMap.put(keyword, newCategory);
		}
		else{
			throw new Exception("A category with this keyword already exists. Keywords have to be unique.");
		}
	}
	
	public void removeCategory(String keyword){
		categoryMap.remove(keyword);
		
	}
	
	//categories first
	public void addTask(String taskText){
		
	}
}