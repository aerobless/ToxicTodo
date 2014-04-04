package ch.theowinter.ToxicTodo.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.crypto.SealedObject;

import org.fusesource.jansi.AnsiConsole;

import ch.theowinter.ToxicTodo.utilities.EncryptionEngine;
import ch.theowinter.ToxicTodo.utilities.LogicEngine;
import ch.theowinter.ToxicTodo.utilities.primitives.TodoList;
import ch.theowinter.ToxicTodo.utilities.primitives.ToxicDatagram;

public class ClientToxicTodo {
	//Vanity info
	public static final double versionNumber = 1.1;
	public static final String author = "Theo Winter";
	public static final String website = "theowinter.ch";
	
	//Local storage
	private static LogicEngine logic = new LogicEngine();
	private static ClientTodoManager todoManager;
	private static EncryptionEngine crypto;
	
	//Settings
	private final static String settingsFile = logic.getJarDirectory("client_config.xml");
	private static ClientSettings settings;

	public static void main(String[] args) {
		//0. Load config & init stuff
		loadSettings();
		try {
			crypto = new EncryptionEngine(settings.getPassword());
		} catch (Exception anEx) {
			System.err.println("Crypto Error - Unable to load the Encryption Engine");
		}
		//1. GET todo-LIST from server
		todoManager = new ClientTodoManager(sendToServer(new ToxicDatagram("SEND_TODOLIST_TO_CLIENT", "")));
		
		//2. Run manipulations (add/remove/etc.)
		ToxicDatagram datagramForServer = todoManager.run(args);
		
		//3. Return answer to the server unless we're finished
		if(datagramForServer != null){
			sendToServer(datagramForServer);
			voidDrawList();
		}
	}
	
	private static void voidDrawList(){
		todoManager = new ClientTodoManager(sendToServer(new ToxicDatagram("SEND_TODOLIST_TO_CLIENT", "")));
		ToxicDatagram datagramForServer = todoManager.run(new String[]{});
		if(datagramForServer != null){
			sendToServer(datagramForServer);
			voidDrawList();
		}
	}
	
	private static TodoList sendToServer(ToxicDatagram datagram){
		//Encrypt before sending off
		Object encryptedData = null;
		try {
			encryptedData = crypto.enc(datagram);
		} catch (Exception anEx1) {
			System.err.println("Encryption ERROR - Unable to encrypt & send data!");
			anEx1.printStackTrace();
		}
		TodoList todoList = null;
		if(encryptedData!=null){
			try {
		    	Socket s = new Socket(settings.getHOST(), settings.getPORT());  
		    	OutputStream os = s.getOutputStream();  
		    	ObjectOutputStream oos = new ObjectOutputStream(os);  
		
				oos.writeObject(encryptedData);
				
				print("Request sent - awaiting server response", settings.isDebug());
				InputStream is = s.getInputStream();  
	        	ObjectInputStream ois = new ObjectInputStream(is);
	        	SealedObject encryptedDataFromServer = (SealedObject)ois.readObject();
	        	
	        	//Decrypt data from server
	        	ToxicDatagram dataFromServer = null;
	    		try {
	    			dataFromServer = (ToxicDatagram) crypto.dec(encryptedDataFromServer);
	    		} catch (Exception anEx1) {
	    			System.err.println("Encryption ERROR - Unable to encrypt & send data!");
	    			anEx1.printStackTrace();
	    		}
	        	
	        	todoList = dataFromServer.getTodoList();
	        	print("Received response from server: "+dataFromServer.getServerControlMessage(), settings.isDebug());
				
		    	oos.close();  
		    	os.close();  
		    	s.close();
			} catch (IOException anEx) {
				anEx.printStackTrace();
			} catch (ClassNotFoundException anEx) {
				anEx.printStackTrace();
			} 
		}
		return todoList;
	}
	
	public static void print(String input){
		AnsiConsole.out.println(input);
	}
	
	public static void print(String input, boolean debug){
		if(debug == true){
			System.out.println("DEBUG INFO:"+input);
		}
	}
	
	public static void loadSettings(){
		if(!firstTimeRun()){
			settings = (ClientSettings) logic.loadXMLFile(settingsFile);
		}
	}
	
	public static boolean firstTimeRun(){
		boolean firstTime = false;
		File f = new File(settingsFile);
		if(!f.exists()){
			print("INFORMATION:");
			print("client_settings.xml has been created because you run ToxicTodo for the first time.");
			print("Please edit the settings to chose your own server & port. - Localhost can be used for testing only.");
			firstTime = true;
			settings = new ClientSettings();
			logic.saveToXMLFile(settings, settingsFile);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return firstTime;
	}
}
