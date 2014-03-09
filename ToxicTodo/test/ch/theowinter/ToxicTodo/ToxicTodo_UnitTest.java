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
		main.addCategory("School work", "school");
		main.addCategory("Programming stuff", "programming");
		main.addCategory("To buy", "buy");
		return main;
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
	}
	
	@Test
	public void testListCommand(){
		MainToxicTodo main = createTestEnviornment();
		String[] args = {"list","test"};
		main.main(args);
	}
}
