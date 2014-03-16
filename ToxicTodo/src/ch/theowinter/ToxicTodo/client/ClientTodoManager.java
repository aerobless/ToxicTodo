package ch.theowinter.ToxicTodo.client;

import ch.theowinter.ToxicTodo.utilities.LogicEngine;
import ch.theowinter.ToxicTodo.utilities.primitives.TodoList;
import ch.theowinter.ToxicTodo.utilities.primitives.TodoTask;
import ch.theowinter.ToxicTodo.utilities.primitives.ToxicDatagram;

public class ClientTodoManager {
	TodoList todoList;
	LogicEngine logic = new LogicEngine();

	public ClientTodoManager(TodoList todoList) {
		super();
		this.todoList = todoList;
	}
	
	public ToxicDatagram run(String[] args){
		ToxicDatagram datagram = null;
		ClientToxicTodo.print("app started", ClientToxicTodo.debug);
		if(args.length == 0){
			ClientToxicTodo.print("Toxic Todo Version 0.2 - Please specify some arguments first.");
		}
		else if(args[0].equals("list")){
			drawTodoList(false);
		}
		else if(args[0].equals("clist")){
			//Display complete list
			drawTodoList(true);
		}
		else if(args[0].equals("add")){
			datagram = addTask(args);
		}
		else{
			ClientToxicTodo.print("This command: "+args[0]+" does not exist.");
		}
		return datagram;
	}
	
	/**
	 * Draw the todolist, that means all categories that contain tasks.
	 */
	private void drawTodoList(boolean displayEmptyCategories){
		for(String categoryKey : todoList.getCategoryMap().keySet()){
			int taskID = 1;
			ClientToxicTodo.print("###-"+todoList.getCategoryMap().get(categoryKey).getName().toUpperCase()+"-###");
			todoList.getCategoryMap().get(categoryKey).getTasksHashMap();
			//Only list category if it contains tasks or we want to display empty categories too.
			if(todoList.getCategoryMap().get(categoryKey).containsTasks() || displayEmptyCategories==true){
				for(String taskKey : todoList.getCategoryMap().get(categoryKey).getTasksHashMap().keySet()){
					++taskID;
					ClientToxicTodo.print("    ["+taskID+"] "+todoList.getCategoryMap().get(categoryKey).getTasksHashMap().get(taskKey).getTaskText());
				}
			}
		}
	}
	
	private ToxicDatagram addTask(String[] args){
		String[] goodArgs = logic.concatenateArgs(args, 3);
		TodoTask task = new TodoTask(goodArgs[2]);
		ToxicDatagram datagram = null;
		//Adding it locally just to check before sending it off to the server
		try {
			todoList.addTask(goodArgs[1], task);
			datagram = new ToxicDatagram("ADD_TASK_TO_LIST_ON_SERVER", "", task, goodArgs[1]);
		} catch (Exception e) {
			ClientToxicTodo.print("Local-Error: Category doesn't exist");
		}
		return datagram;
	}
}
