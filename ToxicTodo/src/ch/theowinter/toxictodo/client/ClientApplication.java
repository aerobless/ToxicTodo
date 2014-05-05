package ch.theowinter.toxictodo.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.crypto.SealedObject;

import ch.theowinter.toxictodo.client.cli.CliController;
import ch.theowinter.toxictodo.client.ui.controller.ClientController;
import ch.theowinter.toxictodo.sharedobjects.EncryptionEngine;
import ch.theowinter.toxictodo.sharedobjects.Logger;
import ch.theowinter.toxictodo.sharedobjects.LogicEngine;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoList;
import ch.theowinter.toxictodo.sharedobjects.elements.ToxicDatagram;

public class ClientApplication {
	//Vanity info
	public static final double CLIENT_VERSION = 1.35;
	public static final String AUTHOR = "Theo Winter";
	public static final String WEBSITE = "http://theowinter.ch";
	public static final String CLIENT_UPDATE_URL = "http://w1nter.net:8080/job/ToxicTodo/lastSuccessfulBuild/artifact/ToxicTodo/dist/ToxicTodoClient.jar";
	
	//Local storage
	private static LogicEngine logic = new LogicEngine();
	private static EncryptionEngine crypto;
	
	//Settings
	private final static String SETTINGS_FILE = logic.getJarDirectory("client_config.xml");
	public final static ClientSettings settings = loadSettings();

	public static void main(String[] args) {
		//0. Load config & init stuff
		try {
			crypto = new EncryptionEngine(settings.getPassword());
		} catch (Exception e) {
			Logger.log("Crypto Error: Unable to load the Encryption Engine", e);
		}
		
		//2. Create either GUI or CLI controller
		if(args.length<1){
			ClientTodoManager todoManager = new ClientTodoManager();
			ClientController guiClient = new ClientController();
			guiClient.start(todoManager, settings);
		}
		else{
			try {
				ClientTodoManager todoManager = new ClientTodoManager(sendToServer(new ToxicDatagram("SEND_TODOLIST_TO_CLIENT")));
				CliController cli = new CliController(todoManager);
				cli.start(args);
			} catch (IOException anEx) {
				Logger.log("ERROR: Unable to establish a connection with the server.");
				Logger.log("Are you certain that you're running a server on "+settings.getHOST()+":"+settings.getPORT()+"?");
				Logger.log("If you're running the server on a different IP or port, then you should change the client_config.xml!");
				System.exit(0);
			}
		}
	}
	
	public static TodoList sendToServer(ToxicDatagram datagram) throws IOException{
		//Encrypt before sending off
		Object encryptedData = null;
		try {
			encryptedData = crypto.enc(datagram);
		} catch (Exception e) {
			Logger.log("Encryption ERROR - Unable to encrypt & send data!", e);
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
	        	
	        	ToxicDatagram dataFromServer = decrypt(encryptedDataFromServer);
	        	todoList = dataFromServer.getTodoList();
		
		    	oos.close();  
		    	os.close();  
		    	s.close();
			} catch (ClassNotFoundException e) {
				Logger.log("Class not found in Client Application.", e);
			} 
		}
		return todoList;
	}
	
	private static ToxicDatagram decrypt(SealedObject encryptedDataFromServer) throws IOException{
    	ToxicDatagram dataFromServer = null;
		try {
			dataFromServer = (ToxicDatagram) crypto.dec(encryptedDataFromServer);
		} catch (Exception e) {
			Logger.log("Encryption ERROR - Unable to encrypt & send data!", e);
			throw new IOException();
		}
		return dataFromServer;
	}
	
	public static ClientSettings loadSettings(){
		ClientSettings loadingSettings;
		if(!firstTimeRun()){
			loadingSettings = (ClientSettings) logic.loadXMLFile(SETTINGS_FILE);
		}else{
			loadingSettings = new ClientSettings();
			logic.saveToXMLFile(settings, SETTINGS_FILE);
		}
		return loadingSettings;
	}
	
	public static boolean firstTimeRun(){
		boolean firstTime = false;
		File f = new File(SETTINGS_FILE);
		if(!f.exists()){
			//TODO: maybe add a better warning since this gets clear almost immediately in CLI version and not displayed at all in GUI.
			Logger.log("INFORMATION:");
			Logger.log("client_settings.xml has been created because you run ToxicTodo for the first time.");
			Logger.log("Please edit the settings to chose your own server & port. - Localhost can be used for testing only.");
			firstTime = true;
		}
		return firstTime;
	}
	
	public static void saveSettingsToDisk(){
		logic.saveToXMLFile(settings, SETTINGS_FILE);
	}
}
