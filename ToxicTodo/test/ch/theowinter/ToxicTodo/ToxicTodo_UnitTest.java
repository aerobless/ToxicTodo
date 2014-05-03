package ch.theowinter.ToxicTodo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.crypto.SealedObject;

import org.junit.Test;

import ch.theowinter.ToxicTodo.SharedObjects.EncryptionEngine;
import ch.theowinter.ToxicTodo.SharedObjects.Elements.TodoCategory;
import ch.theowinter.ToxicTodo.SharedObjects.Elements.TodoList;
import ch.theowinter.ToxicTodo.SharedObjects.Elements.TodoTask;
import ch.theowinter.ToxicTodo.client.CLI.CliController;

public class ToxicTodo_UnitTest {
	
	@Test
	public void addCategoryToTodoList(){
		boolean success = false;
		TodoList todoList = new TodoList();
		try {
			todoList.addCategory(new TodoCategory("test-category for lulz", "testcat_keyword"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Check that we can't have two categories with the same keyword
		try {
			todoList.addCategory(new TodoCategory("test-category with same keyword", "testcat_keyword"));
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
			todoList.addCategory(new TodoCategory("school work", "school"));
			todoList.addCategory(new TodoCategory("home work", "home"));
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
			todoList.addCategory(new TodoCategory("school work", "school"));
			todoList.addCategory(new TodoCategory("home work", "home"));
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
			todoList.addCategory(new TodoCategory("school work", "school"));
			todoList.addCategory(new TodoCategory("home work", "home"));
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
	
	@Test
	public void addCategoryAndTasks(){
		boolean successfulTest = true;

		//SETUP
		TodoList todoList = new TodoList();
		TodoTask testedTask = new TodoTask("Do homework");
		try {
			todoList.addCategory(new TodoCategory("school work", "school"));
			todoList.addCategory(new TodoCategory("home work", "home"));
			todoList.addTask("school", "Do VSS exercises");
			todoList.addTask("school", testedTask);
			todoList.addCategory(new TodoCategory("todo TEST", "todo"));
			todoList.addTask("todo", "Do VSS exercises");
		} catch (Exception e) {
			successfulTest=false;
		}
		assertTrue(successfulTest);		
	}
	
	@Test
	public void encryptionTests() throws Exception{
		EncryptionEngine crypto = new EncryptionEngine("testPassword1234");
		String topSecretData = "This message is secret";
		Object encryptedData = crypto.enc(topSecretData);

		String decryptedData = (String)crypto.dec((SealedObject)encryptedData);
		assertEquals(topSecretData, decryptedData);
	}
	
	@Test
	public void encryptionTestsWithTwoDifferentEngines() throws Exception{
		EncryptionEngine crypto1 = new EncryptionEngine("testPassword1234");
		EncryptionEngine crypto2 = new EncryptionEngine("testPassword1234");
		String topSecretData = "This message is secret";
		Object encryptedData = crypto1.enc(topSecretData);
		
		String decryptedData = (String)crypto2.dec((SealedObject)encryptedData);
		assertEquals(topSecretData, decryptedData);
	}

	@Test
	public void encryptionTestWithBadPassword(){
		boolean testFailedCorrectly = false;
		try {
			EncryptionEngine crypto = new EncryptionEngine("testPassword1234");
			EncryptionEngine enemyCrypto = new EncryptionEngine("IdontHaveThePassword");
			String topSecretData = "This message is secret";
			Object encryptedData = crypto.enc(topSecretData);
			String decryptedData = (String)enemyCrypto.dec((SealedObject)encryptedData);
			System.out.println("ddd"+decryptedData);
		} catch (Exception anEx) {
			testFailedCorrectly = true;
		}
		assertTrue(testFailedCorrectly);
	}
	
	@Test
	public void redFormatterTest(){
		CliController test = new CliController(null);
		System.out.println(test.formatString("this is **some** random text"));
	}
	
	@Test
	public void editCategory(){
		boolean successfulTest = true;
		//SETUP
		TodoList todoList = new TodoList();
		TodoTask testedTask = new TodoTask("Do homework");
		try {
			todoList.addCategory(new TodoCategory("school work", "school"));
			todoList.addCategory(new TodoCategory("home work", "home"));
			todoList.addTask("school", "Do VSS exercises");
			todoList.addTask("school", testedTask);
			todoList.addCategory(new TodoCategory("todo TEST", "todo"));
			todoList.addTask("todo", "Do VSS exercises");
		} catch (Exception e) {
			successfulTest=false;
		}
		try{
			todoList.editCategory("school", "newSchool", "New School Work", 'i');
		} catch (Exception e){
			successfulTest=false;
		}
		TodoCategory editedCat = todoList.getCategoryMap().get("newSchool");
		TodoCategory doesntExist = todoList.getCategoryMap().get("school");
		assertEquals("New School Work", editedCat.getName());
		assertEquals(null, doesntExist);
		assertTrue(successfulTest);	
	}
}
