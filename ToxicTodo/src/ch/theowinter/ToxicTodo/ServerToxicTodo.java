package ch.theowinter.ToxicTodo;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import ch.theowinter.ToxicTodo.utilities.TodoList;
import ch.theowinter.ToxicTodo.utilities.ToxicDatagram;

public class ServerToxicTodo {
	//Server data:
	private static TodoList serverTodo = new TodoList();
	
	//Connection info:
	public static final int PORT = 5222;

	//fix throws exception to correctly handled try-catches
	public static void main(String[] args) throws Exception {
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
