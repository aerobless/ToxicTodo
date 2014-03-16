package ch.theowinter.ToxicTodo.utilities.primitives;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Provides high-level access to todoTasks and todoCategories
 * @author theowinter
 */
public class TodoList {
	HashMap<String, TodoCategory> categoryMap = new HashMap<String, TodoCategory>();
	

	public TodoList() {
		super();
		categoryMap.put("orphan", new TodoCategory("Tasks without category", "orphan"));
	}

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
	
	/**
	 * Removes a category if it exists, throws error if it doesn't exist. If the category
	 * contains tasks, they'll be moved to the orphan category before this category is
	 * deleted.
	 * 
	 * @param keyword
	 * @throws Exception
	 */
	public void removeCategory(String keyword) throws Exception{
		if(categoryMap.get(keyword)!=null){
			if(categoryMap.get(keyword).containsTasks()){
				//Exporting orphaned tasks to orphan category before deleting old category.
				ArrayList<TodoTask> orphanTasks = categoryMap.get(keyword).getElementsInCategory();
				for(TodoTask orphan : orphanTasks){
					categoryMap.get("orphan").add(orphan);
				}
			}
			categoryMap.remove(keyword);
		}
		else{
			throw new Exception("Category doesn't exist.");
		}
	}
	
	//categories first
	public void addTask(String taskText){
		
	}
}