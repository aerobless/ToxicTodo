package ch.theowinter.ToxicTodo;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.theowinter.ToxicTodo.utilities.TodoCategory;

public class ToxicTodo_UnitTest {
	
	@Test
	public void startAPP(){
		
	}

	@Test
	public void testCreateTodoCategoryClass(){
		String categoryName = "Test Category";
		TodoCategory test = new TodoCategory(categoryName);
		test.add("blub");
		
		//Asserts
		assertEquals(categoryName, test.getName());
	}
	
	@Test
	public void testAddTodoCategory(){
		
	}
	
}
