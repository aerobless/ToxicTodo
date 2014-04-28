package ch.theowinter.ToxicTodo.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.crypto.SealedObject;

import ch.theowinter.ToxicTodo.client.CLI.CliController;
import ch.theowinter.ToxicTodo.client.UI.Controller.ClientController;
import ch.theowinter.ToxicTodo.utilities.EncryptionEngine;
import ch.theowinter.ToxicTodo.utilities.JansiFormats;
import ch.theowinter.ToxicTodo.utilities.LogicEngine;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.TodoList;
import ch.theowinter.ToxicTodo.utilities.primitiveModels.ToxicDatagram;

public class ClientApplication {
	//Vanity info
	public static final double clientVersion = 1.25;
	public static final String author = "Theo Winter";
	public static final String website = "http://theowinter.ch";
	public static final String clientUpdateURL = "http://w1nter.net:8080/job/ToxicTodo/lastSuccessfulBuild/artifact/ToxicTodo/dist/ToxicTodoClient.jar";
	
	//Local storage
	private static LogicEngine logic = new LogicEngine();
	public static ClientTodoManager todoManager;
	private static EncryptionEngine crypto;
	public static JansiFormats jansi = new JansiFormats();
	
	//Settings
	private final static String settingsFile = logic.getJarDirectory("client_config.xml");
	public static ClientSettings settings;

	public static void main(String[] args) {
		//0. Load config & init stuff
		loadSettings();
		try {
			crypto = new EncryptionEngine(settings.getPassword());
		} catch (Exception anEx) {
			System.err.println("Crypto Error: Unable to load the Encryption Engine");
		}
		//1. GET todo-LIST from server
		todoManager = new ClientTodoManager(sendToServer(new ToxicDatagram("SEND_TODOLIST_TO_CLIENT", "")));
		
		//2. Create either GUI or CLI controller
		if(args.length<1){
			print("temp: no args specified - launching GUI");
			ClientController guiClient = new ClientController();
			guiClient.start(todoManager, settings);
		}
		else{
			CliController cli = new CliController(todoManager);
			cli.start(args);
		}
	}
	
	public static TodoList sendToServer(ToxicDatagram datagram){
		//Encrypt before sending off
		Object encryptedData = null;
		try {
			encryptedData = crypto.enc(datagram);
		} catch (Exception anEx1) {
			System.err.println("Encryption ERROR - Unable to encrypt & send data!");
			System.exit(0);
		}
		TodoList todoList = null;
		if(encryptedData!=null){
			try {
		    	Socket s = new Socket(settings.getHOST(), settings.getPORT());  
		    	OutputStream os = s.getOutputStream();  
		    	ObjectOutputStream oos = new ObjectOutputStream(os);  
		
				oos.writeObject(encryptedData);
				
				InputStream is = s.getInputStream();  
	        	ObjectInputStream ois = new ObjectInputStream(is);
	        	SealedObject encryptedDataFromServer = (SealedObject)ois.readObject();
	        	
	        	//Decrypt data from server
	        	ToxicDatagram dataFromServer = null;
	    		try {
	    			dataFromServer = (ToxicDatagram) crypto.dec(encryptedDataFromServer);
	    		} catch (Exception anEx1) {
	    			System.err.println("Encryption ERROR - Unable to encrypt & send data!");
	    			System.exit(0);
	    		}
	        	
	        	todoList = dataFromServer.getTodoList();
		
		    	oos.close();  
		    	os.close();  
		    	s.close();
			} catch (IOException anEx) {
				//TODO: we likely need a GUI error message for this too, just killing the app only works in CLI mode.
				print("ERROR: Unable to establish a connection with the server.");
				print("Are you certain that you're running a server on "+settings.getHOST()+":"+settings.getPORT()+"?");
				print("If you're running the server on a different IP or port, then you should change the client_config.xml!");
				System.exit(0);
			} catch (ClassNotFoundException anEx) {
				anEx.printStackTrace();
			} 
		}
		return todoList;
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
			//TODO: maybe add a better warning since this gets clear almost immediately in CLI version and not displayed at all in GUI.
			print("INFORMATION:");
			print("client_settings.xml has been created because you run ToxicTodo for the first time.");
			print("Please edit the settings to chose your own server & port. - Localhost can be used for testing only.");
			firstTime = true;
			settings = new ClientSettings();
			logic.saveToXMLFile(settings, settingsFile);
		}
		return firstTime;
	}
	
	public static void saveSettingsToDisk(){
		logic.saveToXMLFile(settings, settingsFile);
	}
	
	public static void print(String input){
		System.out.println(input);
	}
}
