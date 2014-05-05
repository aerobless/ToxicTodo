package ch.theowinter.toxictodo.sharedobjects.elements;

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
		categoryMap.put("orphan", new TodoCategory("Tasks without category", "orphan", '\uf018', true));
	}

	/**
	 * Add a new category to the todoList.
	 * 
	 * @param todoCategory
	 * @throws Exception
	 */
	public void addCategory(TodoCategory todoCategory) throws Exception{
		if(categoryMap.get(todoCategory.getKeyword())==null){
			categoryMap.put(todoCategory.getKeyword(), todoCategory);
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
	 * Edit an existing category. Throws an error if the category doesn't exist.
	 * 
	 * @param oldKeyword
	 * @param newKeyword
	 * @param newName
	 * @param newIcon
	 * @throws Exception
	 */
	public void editCategory(String oldKeyword, String newKeyword, String newName, char newIcon) throws Exception {
		TodoCategory editCategory = categoryMap.get(oldKeyword);
		if(editCategory!=null){
			editCategory.setIcon(newIcon);
			editCategory.setKeyword(newKeyword);
			editCategory.setName(newName);
			categoryMap.remove(oldKeyword);
			categoryMap.put(newKeyword, editCategory);
		}
		else{
			throw new Exception("Category doesn't exist.");
		}
	}
	
	/**
	 * Add a new task to the category. Throws error when category doesn't exist.
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
		if(categoryMap.get(category).get(todoTask.getText())!=null){
			categoryMap.get(category).removeTask(todoTask.getText());	
		}
		else{
			throw new Exception("Task doesn't exist.");
		}
	}

	public HashMap<String, TodoCategory> getCategoryMap() {
		return categoryMap;
	}
}