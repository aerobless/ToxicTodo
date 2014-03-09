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
		main.addCategory("School work");
		main.addCategory("Programming stuff");
		main.addCategory("To buy");
		return main;
	}
	
	@Test
	public void startAPP(){
		
	}

	@Test
	public void testCreateTodoCategoryClass(){
		String categoryName = "Test Category";
		TodoCategory test = new TodoCategory(categoryName);
		test.add("blub");
		test.add("blab");
		test.add("blob");
		
		//Asserts
		assertEquals(categoryName, test.getName());
		assertEquals(3, test.getTodolist().size());
	}
	
	@Test
	public void testAddTodoCategory(){
		MainToxicTodo main = createTestEnviornment();
		main.addCategory("forth cat");
		main.addCategory("fifth cat");
		
		assertEquals(5, main.categorySize());
	}
	
	@Test
	public void testRemoveTodoCategory(){
		MainToxicTodo main = createTestEnviornment();
		main.removeCategory("Programming stuff");
		main.listCategories();
	}
}
