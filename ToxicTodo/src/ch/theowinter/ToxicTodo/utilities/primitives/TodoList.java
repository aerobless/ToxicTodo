package ch.theowinter.ToxicTodo.utilities.primitives;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Provides high-level access to todoTasks and todoCategories
 * @author theowinter
 */
public class TodoList implements Serializable {
	private static final long serialVersionUID = -5867425003353980895L;
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
				ArrayList<TodoTask> orphanTasks = categoryMap.get(keyword).getTaskInCategoryAsArrayList();
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
	
	/**
	 * Add a new task to the category.
	 * 
	 * @param categoryKeyword
	 * @param taskText
	 * @throws Exception
	 */
	public void addTask(String categoryKeyword, String taskText) throws Exception{
		addTask(categoryKeyword, new TodoTask(taskText));
	}
	
	public void addTask(String categoryKeyword, TodoTask todoTask) throws Exception{
		if(categoryMap.get(categoryKeyword)!=null){
			categoryMap.get(categoryKeyword).add(todoTask);
		}
		else{
			throw new Exception("Category doesn't exist.");
		}
	}
	
	/**
	 * Remove a task from the category. Throws an error if the task doesn't exist.
	 * @param todoTask
	 * @param category
	 * @throws Exception
	 */
	public void removeTask(TodoTask todoTask, String category) throws Exception{
		if(categoryMap.get(category).get(todoTask.getTaskText())!=null){
			categoryMap.get(category).removeTask(todoTask.getTaskText());	
		}
		else{
			throw new Exception("Task doesn't exist.");
		}
	}

	public HashMap<String, TodoCategory> getCategoryMap() {
		return categoryMap;
	}
	
}
