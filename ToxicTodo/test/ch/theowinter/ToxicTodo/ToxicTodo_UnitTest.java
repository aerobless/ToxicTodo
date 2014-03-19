package ch.theowinter.ToxicTodo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.theowinter.ToxicTodo.utilities.primitives.TodoList;
import ch.theowinter.ToxicTodo.utilities.primitives.TodoTask;

public class ToxicTodo_UnitTest {

	@Test
	public void createNewTodoList(){
		TodoList todoList = new TodoList();
	}
	
	@Test
	public void addCategoryToTodoList(){
		boolean success = false;
		TodoList todoList = new TodoList();
		try {
			todoList.addCategory("test-category for lulz", "testcat_keyword");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Check that we can't have two categories with the same keyword
		try {
			todoList.addCategory("test-category with same keyword", "testcat_keyword");
		} catch (Exception e) {
			success = true;
		}
		assertTrue(success);
	}
	
	@Test
	public void removeCategory(){
		boolean creationFailure = false;
		boolean throwsCorrectException = false;
		TodoList todoList = new TodoList();
		try {
			todoList.addCategory("school work", "school");
			todoList.addCategory("home work", "home");
		} catch (Exception e) {
			creationFailure = true;
		}
		//Removing category that does exist
		try {
			todoList.removeCategory("home");
		} catch (Exception e) {
			creationFailure = true;
		}
		
		//Removing category that does NOT exist
		try {
			todoList.removeCategory("iDontExist");
		} catch (Exception e) {
			throwsCorrectException = true;
		}
		
		//TODO: add check for orphans
		
		//Check for success
		assertFalse(creationFailure);
		assertTrue(throwsCorrectException);

	}
	
	@Test
	public void addTask(){
		boolean successfulTest = true;
		boolean expectedFailure = false;

		//SETUP
		TodoList todoList = new TodoList();
		try {
			todoList.addCategory("school work", "school");
			todoList.addCategory("home work", "home");
		} catch (Exception e) {
			successfulTest = false;
		}
		
		try {
			todoList.addTask("school", "Do VSS exercises");
		} catch (Exception e) {
			successfulTest = false;
		}
		
		try {
			todoList.addTask("iDontExist", "Clean car");
		} catch (Exception e) {
			expectedFailure = true;
		}
		assertTrue(expectedFailure);
		assertTrue(successfulTest);
	}
	
	@Test
	public void removeTask(){
		boolean successfulTest = true;
		boolean expectedFailure = false;
		boolean expectedFailure2 = false;

		//SETUP
		TodoList todoList = new TodoList();
		TodoTask testedTask = new TodoTask("Do homework");
		try {
			todoList.addCategory("school work", "school");
			todoList.addCategory("home work", "home");
			todoList.addTask("school", "Do VSS exercises");
			todoList.addTask("school", testedTask);
		} catch (Exception e) {
			successfulTest=false;
		}
		
		//Tests
		try {
			todoList.removeTask(testedTask, "school");
		} catch (Exception e) {
			successfulTest=false;
		}
		
		try {
			todoList.removeTask(new TodoTask("I don't exist"), "school");
		} catch (Exception e) {
			expectedFailure=true;
		}
		
		try {
			todoList.removeTask(new TodoTask("I don't exist"), "school");
		} catch (Exception e) {
			expectedFailure2=true;
		}
		
		assertTrue(successfulTest);
		assertTrue(expectedFailure);
		assertTrue(expectedFailure2);
	}
}
