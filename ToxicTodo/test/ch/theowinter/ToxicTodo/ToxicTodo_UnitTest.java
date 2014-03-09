package ch.theowinter.ToxicTodo;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.theowinter.ToxicTodo.utilities.TodoCategory;

public class ToxicTodo_UnitTest {
	
	/**
	 * Setup a basic test environment with some sample categories and todo entries.
	 * 
	 * @return MainToxicTodo
	 */
	public MainToxicTodo createTestEnviornment(){
		MainToxicTodo main = new MainToxicTodo();
		
		//Create categories
		main.addCategory("School work", "school");
		main.addCategory("Programming stuff", "programming");
		main.addCategory("To buy", "buy");
		
		//Add todos to categories
		main.addTaskToCategory("school", "Complete exercise 1 for vssprog");
		main.addTaskToCategory("school", "Complete exercise 1 for parprog");
		main.addTaskToCategory("programming", "Build better todolist");
		main.addTaskToCategory("programming", "fix all the bugs");
		main.addTaskToCategory("buy", "new pens");
		return main;
	}
	
	public void printListFromMain(MainToxicTodo main){
		String[] args = {"list"};
		main.main(args);
	}

	@Test
	public void testCreateTodoCategoryClass(){
		String categoryName = "Test Category";
		TodoCategory test = new TodoCategory(categoryName, "test");
		test.add("blub");
		test.add("blab");
		test.add("blob");
		
		//Asserts
		assertEquals(categoryName, test.getName());
		assertEquals(3, test.getElementsInCategory().size());
	}
	
	@Test
	public void testAddTodoCategory(){
		MainToxicTodo main = createTestEnviornment();
		main.addCategory("forth cat", "forth");
		main.addCategory("fifth cat", "fith");
		
		assertEquals(5, main.categorySize());
	}
	
	@Test
	public void testRemoveTodoCategory(){
		MainToxicTodo main = createTestEnviornment();
		main.removeCategory("Programming stuff");
		
		assertEquals(2, main.categorySize());
	}
	
	@Test
	public void testAddElementToCategory(){
		MainToxicTodo main = createTestEnviornment();
		main.addTaskToCategory("school", "Complete exercise 1 for vssprog");
		assertEquals(6, main.todoSize());
	}
	
	@Test
	public void testAddElementViaMain(){
		MainToxicTodo main = createTestEnviornment();
		String[] args = {"add","school", "InfSi1:", "Watch", "security","videos","on","YouTube"};
		main.main(args);
		assertEquals(6, main.todoSize());
		while(true){
			printListFromMain(main);
		}
	}
}
