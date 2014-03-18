package ch.theowinter.ToxicTodo.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.fusesource.jansi.AnsiConsole;

import ch.theowinter.ToxicTodo.utilities.JansiFormats;
import ch.theowinter.ToxicTodo.utilities.primitives.TodoList;
import ch.theowinter.ToxicTodo.utilities.primitives.ToxicDatagram;

public class ClientToxicTodo {
	//Local storage
	private static ClientTodoManager todoManager;
	private static JansiFormats jansi = new JansiFormats();
	
	//Settings
	private final static String HOST = "localhost";
	private final static int PORT = 5222;
	public static boolean debug = true;

	public static void main(String[] args) {
		//1. GET todo-LIST from server
		todoManager = new ClientTodoManager(sendToServer(new ToxicDatagram("SEND_TODOLIST_TO_CLIENT", "")));
		
		//2. Run manipulations (add/remove/etc.)
		ToxicDatagram datagramForServer = todoManager.run(new String[]{"list"});//{"add", "school", "this", "actually", "works.", "nice", ":)"});
		
		//3. Return answer to the server unless we're finished
		if(datagramForServer != null){
			sendToServer(datagramForServer);
			voidDrawList();
		}
	}
	
	private static void voidDrawList(){
		todoManager = new ClientTodoManager(sendToServer(new ToxicDatagram("SEND_TODOLIST_TO_CLIENT", "")));
		ToxicDatagram datagramForServer = todoManager.run(new String[]{"list"});
		if(datagramForServer != null){
			sendToServer(datagramForServer);
			voidDrawList();
		}
	}
	
	private static TodoList sendToServer(ToxicDatagram datagram){
		TodoList todoList = null;
		try {
	    	Socket s = new Socket(HOST, PORT);  
	    	OutputStream os = s.getOutputStream();  
	    	ObjectOutputStream oos = new ObjectOutputStream(os);  
	
			oos.writeObject(datagram);
			
			print("Request sent - awaiting server response", debug);
			
			InputStream is = s.getInputStream();  
        	ObjectInputStream ois = new ObjectInputStream(is);
        	
        	ToxicDatagram dataFromServer = (ToxicDatagram)ois.readObject();
        	todoList = dataFromServer.getTodoList();
        	
        	print("Received response from server: "+dataFromServer.getServerControlMessage(), debug);
			
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
		AnsiConsole.out.println(jansi.GREEN+input);
		//System.out.println(input);
	}
	
	public static void print(String input, boolean debug){
		if(debug == true){
			//TODO: add support for colours and stuff
			System.out.println("DEBUG INFO:"+input);
		}
	}
}
