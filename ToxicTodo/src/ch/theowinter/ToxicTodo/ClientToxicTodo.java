package ch.theowinter.ToxicTodo;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import ch.theowinter.ToxicTodo.utilities.TodoList;
import ch.theowinter.ToxicTodo.utilities.ToxicDatagram;

public class ClientToxicTodo {
	private final static String HOST = "localhost";
	private final static int PORT = 5222;
	public static boolean debug = true;

	public static void main(String[] args) {
		
		//GET todo-LIST from server
		TodoList todo = pollServerForTodoList();
		
		//Run manipulations (add/remove/etc.)
		todo.run(args);
		
		//return new todo-LIST to server
		updateServerTodoList(todo);
		print("all done");
	}
	
	private static TodoList pollServerForTodoList(){
		TodoList todo = new TodoList();
		try {
	    	Socket s = new Socket(HOST, PORT);  
	    	OutputStream os = s.getOutputStream();  
	    	ObjectOutputStream oos = new ObjectOutputStream(os);  
	    	
	    	ToxicDatagram dataToServer = new ToxicDatagram(null, "getList", "not implemented yet - insecure");   	
			oos.writeObject(dataToServer);
			
			print("Request sent - awaiting server response", debug);
			
			InputStream is = s.getInputStream();  
        	ObjectInputStream ois = new ObjectInputStream(is);
        	
        	ToxicDatagram dataFromServer = (ToxicDatagram)ois.readObject();
        	todo = dataFromServer.getTodoList();
        	
        	print("Received response from server", debug);
			
	    	oos.close();  
	    	os.close();  
	    	s.close();
		} catch (IOException anEx) {
			anEx.printStackTrace();
		} catch (ClassNotFoundException anEx) {
			anEx.printStackTrace();
		} 
		return todo;
	}
	
	private static boolean updateServerTodoList(TodoList todo){
		boolean success = false;
		
		try {
	    	Socket s = new Socket(HOST, PORT);  
	    	OutputStream os = s.getOutputStream();  
	    	ObjectOutputStream oos = new ObjectOutputStream(os);  
	    	
	    	ToxicDatagram dataToServer = new ToxicDatagram(todo, "sendList", "not implemented yet - insecure");   	
			oos.writeObject(dataToServer);
			
			print("Request sent - awaiting server response", debug);
			
			InputStream is = s.getInputStream();  
        	ObjectInputStream ois = new ObjectInputStream(is);
        	
        	ToxicDatagram dataFromServer = (ToxicDatagram)ois.readObject();
        	
        	if(dataFromServer.getServerControlMessage().equals("success")){
        		success = true;
        	}
        	print("Received response from server", debug);
			
	    	oos.close();  
	    	os.close();  
	    	s.close();
		} catch (IOException anEx) {
			anEx.printStackTrace();
		} catch (ClassNotFoundException anEx) {
			anEx.printStackTrace();
		} 
		
		
		return success;	
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
