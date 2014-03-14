package ch.theowinter.ToxicTodo;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerToxicTodo {
	public static final int PORT = 5222;

	//fix throws exception to correctly handled try-catches
	public static void main(String[] args) throws Exception {
		//TODO: add contion instead of true
		while(true){
	        try (ServerSocket ss = new ServerSocket(PORT)) {
	        	System.out.println("waiting on client...");
	        	Socket s = ss.accept();  
	        	InputStream is = s.getInputStream();  
	        	ObjectInputStream ois = new ObjectInputStream(is);
	        	/*
	        	Ex02_5_FraktalHelper fractalHelp = (Ex02_5_FraktalHelper)ois.readObject();
	        	
	        	Ex02_5_FraktalGenerator generator = new Ex02_5_FraktalGenerator();
	        	 */
	        	System.out.println("Sending response");
	        	
		    	OutputStream os = s.getOutputStream();  
		    	ObjectOutputStream oos = new ObjectOutputStream(os);  
		    	
		    	oos.writeObject("ddd");
		    	oos.close();  
		    	os.close();  
	
	        	is.close();  
	        	s.close();  
	        	ss.close();
	        }
        }

	}

}
