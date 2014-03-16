package ch.theowinter.ToxicTodo.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import ch.theowinter.ToxicTodo.utilities.primitives.TodoList;
import ch.theowinter.ToxicTodo.utilities.primitives.ToxicDatagram;

public class ClientToxicTodo {
	//Local storage
	private static ClientTodoManager todoManager;
	
	//Settings
	private final static String HOST = "localhost";
	private final static int PORT = 5222;
	public static boolean debug = true;

	public static void main(String[] args) {
		//GET todo-LIST from server
		todoManager = new ClientTodoManager(pullTodoListFromServer());
		
		//Run manipulations (add/remove/etc.)
		todoManager.run(new String[]{"list"});
		
		//return new todo-LIST to server
		print("all done");
	}
	
	private static TodoList pullTodoListFromServer(){
		TodoList todoList = null;
		try {
	    	Socket s = new Socket(HOST, PORT);  
	    	OutputStream os = s.getOutputStream();  
	    	ObjectOutputStream oos = new ObjectOutputStream(os);  
	    	
	    	ToxicDatagram dataToServer = new ToxicDatagram("SEND_TODOLIST_TO_CLIENT", "");   	
			oos.writeObject(dataToServer);
			
			print("Request sent - awaiting server response", debug);
			
			InputStream is = s.getInputStream();  
        	ObjectInputStream ois = new ObjectInputStream(is);
        	
        	ToxicDatagram dataFromServer = (ToxicDatagram)ois.readObject();
        	todoList = dataFromServer.getTodoList();
        	
        	print("Received response from server", debug);
			
	    	oos.close();  
	    	os.close();  
	    	s.close();
		} catch (IOException anEx) {
			anEx.printStackTrace();
		} catch (ClassNotFoundException anEx) {
			anEx.printStackTrace();
		} 
		return todoList;
	}
	
	//TODO: remove from TodoList - duplicate code
	public static void print(String input){
		//TODO: add support for colours and stuff
		System.out.println(input);
	}
	
	public static void print(String input, boolean debug){
		if(debug == true){
			//TODO: add support for colours and stuff
			System.out.println("DEBUG INFO:"+input);
		}
	}
	
}
