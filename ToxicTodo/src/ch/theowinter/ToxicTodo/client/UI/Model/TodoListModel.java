package ch.theowinter.ToxicTodo.client.UI.Model;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractListModel;

import ch.theowinter.ToxicTodo.client.ClientTodoManager;

public class TodoListModel  extends AbstractListModel<Object> implements Observer{
	private static final long serialVersionUID = -2197405934803803950L;
	ClientTodoManager todoManager;
	ArrayList<String> data = new ArrayList<String>();
	
	/**
	 * Creates a new instance of this class.
	 *
	 * @param aStorage
	 */
	public TodoListModel(ClientTodoManager aTodoManager) {
		super();
		todoManager = aTodoManager;
		//data = formatTodoArray(todoManager.toArray()); //old test
		data = newFormatTodoArray(todoManager.categoriesToArray());
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return data.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Object getElementAt(int aIndex) {
		// TODO Auto-generated method stub
		return data.get(aIndex);
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable observedList, Object aArg) {
		
	}
	
	@Deprecated
	private ArrayList<String> formatTodoArray(ArrayList<String> input){
		for(int i=0; i<input.size(); i++){
			String currentItem = input.get(i);
			if(currentItem.charAt(0) == '#'){
				input.set(i,  "<html><h3>"+currentItem+"</h3></html>");
			}
		}
		return input;
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
/*
		
		Font awesomeFont = getAwesomeFont();
		GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		genv.registerFont(awesomeFont);*/
		for(int i=0; i<input.size(); i++){
			String currentItem = input.get(i);
			input.set(i,  "<html><font face=font>"+String.valueOf('\uf15b')+"</font><h3>"+currentItem+"</h3></html>");
		}
		return input;
	}
	
	//TODO: make global exception handler !!!
	private Font getAwesomeFont(){
		//Worst case we return null and there are no icons, program won't crash.
		Font ttfReal = null;
		try {
			InputStream in = this.getClass().getResourceAsStream("/resources/fontawesome-webfont.ttf");
		    Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, in);
		    ttfReal = ttfBase.deriveFont(Font.BOLD, 13);
		} catch (FontFormatException e) {
			System.out.println("Font format error");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Exception");
			e.printStackTrace();
		}
		return ttfReal;
	}
}
