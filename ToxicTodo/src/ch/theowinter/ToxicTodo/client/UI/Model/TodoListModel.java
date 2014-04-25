package ch.theowinter.ToxicTodo.client.UI.Model;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import ch.theowinter.ToxicTodo.client.ClientTodoManager;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoCategory;

public class TodoListModel implements Observer, ListModel<TodoCategory>{
	ArrayList<TodoCategory> categoryList;
	
	public TodoListModel(ClientTodoManager aTodoManager) {
		super();
		categoryList = aTodoManager.categoriesToArray();
	}

	@Override
	public int getSize() {
		return categoryList.size();
	}

	@Override
	public TodoCategory getElementAt(int aIndex) {
		return categoryList.get(aIndex);
	}

	//TODO: for update
	@Override
	public void update(Observable observedList, Object aArg) {
		
	}
	
	private ArrayList<String> newFormatTodoArray(ArrayList<String> input){
		try {
			URL fontUrl = new URL("http://temp.w1nter.com/fontawesome-webfont.ttf");
	        Font font = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
	        font = font.deriveFont(Font.PLAIN,20);
	        GraphicsEnvironment ge =
	            GraphicsEnvironment.getLocalGraphicsEnvironment();
	        ge.registerFont(font);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(int i=0; i<input.size(); i++){
			String currentItem = input.get(i);
			input.set(i,  "<html><font face=font>"+String.valueOf('\uf15b')+"</font><h3>"+currentItem+"</h3></html>");
		}
		return input;
	}


	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}
}
