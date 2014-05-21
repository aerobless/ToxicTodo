package ch.theowinter.toxictodo.sharedobjects.elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodoCategory implements Serializable, Comparable<TodoCategory> {
	private static final long serialVersionUID = 2851662981886514578L;
	
	//Properties:
	private String name;
	private String keyword;
	private char icon;
	private boolean isSystemCategory;
	
	//Container:
	private Map<String, TodoTask> todoTaskList = new HashMap<String, TodoTask>();
	
	public TodoCategory(String categoryName, String keyword) {
		super();
		this.name = categoryName;
		this.keyword = keyword;
		this.icon = '\uf07b';
		this.isSystemCategory = false;
	}

	public TodoCategory(String categoryName, String keyword, char icon, boolean systemCategory) {
		super();
		this.name = categoryName;
		this.keyword = keyword;
		this.icon = icon;
		this.isSystemCategory = systemCategory;
	}

	//Contain-Getters:
	public Map<String, TodoTask> getTasksHashMap(){
		return todoTaskList;
	}
	public List<TodoTask> getTaskInCategoryAsArrayList(){
		return new ArrayList<TodoTask>(todoTaskList.values());
	}
	public int size(){
		return todoTaskList.size();
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
		return !todoTaskList.isEmpty();
	}
	
	//Contain-Setters:
	public void add(TodoTask todo){
		todoTaskList.put(todo.getText(), todo);
	}
	public TodoTask removeTask(String key){
		return todoTaskList.remove(key);
	}

	//Property-Getters:
	public String getName(){
		return name;
	}
	public String getKeyword(){
		return keyword;
	}
	public char getIcon() {
		return icon;
	}
	public boolean isSystemCategory() {
		return isSystemCategory;
	}
	
	
	//Property-Setters:
	public void setName(String name) {
		this.name = name;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public void setIcon(char icon) {
		this.icon = icon;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		TodoCategory other = (TodoCategory) obj;
		if (keyword == null) {
			if (other.keyword != null){
				return false;
			}
		} else if (!keyword.equals(other.keyword)){
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(TodoCategory inputCategory) {
		int returnValue;
		if(inputCategory.getKeyword().equals(getKeyword())){
			returnValue = 0;
		}else {
			returnValue = getName().toLowerCase().compareTo(inputCategory.getName().toLowerCase());
		}
		return returnValue;
	}
}
