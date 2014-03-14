package ch.theowinter.ToxicTodo;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import ch.theowinter.ToxicTodo.utilities.TodoManager;
import ch.theowinter.ToxicTodo.utilities.primitives.TodoCategory;
import ch.theowinter.ToxicTodo.utilities.primitives.ToxicDatagram;

public class ServerToxicTodo {
	//Server data:
	private static ArrayList<TodoCategory> serverTodo = new ArrayList<TodoCategory>();
	
	//Connection info:
	public static final int PORT = 5222;

	//fix throws exception to correctly handled try-catches
	public static void main(String[] args) throws Exception {
		
		//SAMPLE DATA:
		serverTodo.add(new TodoCategory("School work", "school"));
		serverTodo.add(new TodoCategory("Programming stuff", "programming"));
		serverTodo.add(new TodoCategory("To buy", "buy"));		
		serverTodo.get(0).add("Complete exercise 1 for vssprog");
		serverTodo.get(0).add("Complete exercise 1 for parprog");
		serverTodo.get(1).add("Build better todolist");
		serverTodo.get(1).add("fix all the bugs");
		serverTodo.get(2).add("new pens");
		
		//TODO: add contion instead of true
		while(true){
	        try (ServerSocket ss = new ServerSocket(PORT)) {
	        	serverPrint("Toxic Todo Server - Ready for clients to connect.");
	        	Socket s = ss.accept();  
	        	InputStream is = s.getInputStream();  
	        	ObjectInputStream ois = new ObjectInputStream(is);
	        	
	        	serverPrint("got a message from a client");
	        	ToxicDatagram dataFromClient = (ToxicDatagram)ois.readObject();  	
	        	ToxicDatagram dataToClient ;
	        	//temporary handling code
	        	if(dataFromClient.getServerControlMessage().equals("getList")){
	        		dataToClient = new ToxicDatagram(serverTodo, "success", "");
	        	}
	        	else{
	        		dataToClient = new ToxicDatagram(null, "failure - bad request", "");	
	        		serverPrint("bad request by client");
	        	}
	        	
		    	OutputStream os = s.getOutputStream();  
		    	ObjectOutputStream oos = new ObjectOutputStream(os);  
		    	
		    	oos.writeObject(dataToClient);
		    	oos.close();  
		    	os.close();  
	
	        	is.close();  
	        	s.close();  
	        	ss.close();
	        }
        }

	}
	
	public static void serverPrint(String input){
		//TODO: add better logging and logging to file
		System.out.println(input);
	}
}
