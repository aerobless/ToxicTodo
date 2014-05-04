package ch.theowinter.toxictodo.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.concurrent.Semaphore;

import javax.crypto.SealedObject;

import ch.theowinter.toxictodo.sharedobjects.EncryptionEngine;
import ch.theowinter.toxictodo.sharedobjects.Logger;
import ch.theowinter.toxictodo.sharedobjects.elements.ToxicDatagram;

class ServerOpenConnectionThread implements Runnable {
	private Semaphore writeLock = new Semaphore(1);
	private EncryptionEngine crypto;
	private String password;
	private Socket inputSocket;

	public ServerOpenConnectionThread(Socket client, String password) {
		super();
		inputSocket = client;
		this.password = password;
		try {
			crypto = new EncryptionEngine(this.password);
		} catch (Exception anEx) {
			Logger.log("Crypto Error - Unable to load the Encryption Engine", anEx);
		}
	}
	
	@Override
	public void run() {
		InputStream is;
		try {
			is = inputSocket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
		        	
			Logger.log("got a message from a client");
			SealedObject encryptedDataFromClient = (SealedObject)ois.readObject(); 
			
			//Decrypt data from client
        	ToxicDatagram dataFromClient = null;
    		try {
    			dataFromClient = (ToxicDatagram) crypto.dec(encryptedDataFromClient);
    		} catch (Exception e) {
    			Logger.log("Encryption ERROR - Unable to encrypt & send data!", e);
    		}
			
			ToxicDatagram dataToClient = new ToxicDatagram("ERROR - 400 - The server has no response for you.");
		        	
			try {
					dataToClient = runServerAction(dataFromClient.getServerControlMessage(), dataFromClient);
				} catch (Exception e) {
					Logger.log("Client tried to send a packet with a bad cipher - cannot decrypt", e);
				}
		        	
			OutputStream os = inputSocket.getOutputStream();  
			ObjectOutputStream oos = new ObjectOutputStream(os); 
			
			//Encrypt before sending off
			Object encryptedData = null;
			try {
				encryptedData = crypto.enc(dataToClient);
			} catch (Exception e) {
				Logger.log("Encryption ERROR - Unable to encrypt & send data!", e);
			}
			if(encryptedData!=null){
			oos.writeObject(encryptedData);
			}
			oos.close();  
			os.close();  	
			is.close();  
			inputSocket.close();
			} 
		catch (IOException e) {
				Logger.log("IOException in ServerOpenConnectionThread", e);
				} 
		catch (ClassNotFoundException e) {
			Logger.log("ClassNotFoundException in ServerOpenConnectionThread", e);
			}  
		}
	
	public synchronized ToxicDatagram runServerAction(String serverMessage, ToxicDatagram dataFromClient) throws InterruptedException{
		Logger.log("Trying to handle: "+serverMessage);
		ToxicDatagram dataToClient = new ToxicDatagram("ERROR - 500 - Bad Request to server action handler.");
		if(serverMessage.equals("SEND_TODOLIST_TO_CLIENT")){
			//We wait until noone's writing anymore 
			while(writeLock.availablePermits()==0){
				wait();
			}
			dataToClient = new ToxicDatagram("Answering successful request for TodoList", ServerApplication.getServerTodoList());
		}
		else if(serverMessage.equals("ADD_TASK_TO_LIST_ON_SERVER")){
			writeLock.acquire();
			try {
				ServerApplication.serverTodoList.addTask(dataFromClient.getAdditionalMessage(), dataFromClient.getTodoTask());
				dataToClient = new ToxicDatagram("Answering successful request to add new task");
				ServerApplication.writeChangesToDisk();
			} catch (Exception e) {
				Logger.log("Failed to add new task.");
				dataToClient = new ToxicDatagram("Adding a new task failed. Maybe it already exists?");
			}
			writeLock.release();
		}
		else if(serverMessage.equals("REMOVE_AND_LOG_TASK_AS_COMPLETED_ON_SERVER")){
			writeLock.acquire();
			try {
				ServerApplication.serverTodoList.removeTask(dataFromClient.getTodoTask(), dataFromClient.getAdditionalMessage());
				java.util.Date date= new java.util.Date();
				ServerApplication.writeLogToFile("CompletedTasks.txt", new Timestamp(date.getTime())+" : COMPLETED : "+dataFromClient.getTodoTask().getText());
				dataToClient = new ToxicDatagram("Answering successful request to remove & log task");
				ServerApplication.writeChangesToDisk();
			} catch (Exception e) {
				Logger.log("Failed to remove & log task.");
				dataToClient = new ToxicDatagram("Completing a task failed.");
			}
			writeLock.release();
		}
		
		else if(serverMessage.equals("REMOVE_TASK_ON_SERVER")){
			writeLock.acquire();
			try {
				ServerApplication.serverTodoList.removeTask(dataFromClient.getTodoTask(), dataFromClient.getAdditionalMessage());
				dataToClient = new ToxicDatagram("Answering successful request to remove task");
				ServerApplication.writeChangesToDisk();
			} catch (Exception e) {
				Logger.log("Failed to remove task.");
				dataToClient = new ToxicDatagram("Removing a task failed.");
			}
			writeLock.release();
		}
		
		else if(serverMessage.equals("ADD_CATEGORY_TO_LIST_ON_SERVER")){
			writeLock.acquire();
			try {
				ServerApplication.serverTodoList.addCategory(dataFromClient.getTodoCategory());
				dataToClient = new ToxicDatagram("Answering successful request to add category");
				ServerApplication.writeChangesToDisk();
			} catch (Exception e) {
				Logger.log("Failed to add new category.");
				dataToClient = new ToxicDatagram("Adding a category failed.");
			}
			writeLock.release();
		}
		
		else if(serverMessage.equals("REMOVE_CATEGORY_ON_SERVER")){
			writeLock.acquire();
			try {
				ServerApplication.serverTodoList.removeCategory(dataFromClient.getTodoCategory().getKeyword());
				dataToClient = new ToxicDatagram("Answering successful request to remove category");
				ServerApplication.writeChangesToDisk();
			} catch (Exception e) {
				Logger.log("Failed to remove category.");
				dataToClient = new ToxicDatagram("Removing a category failed.");
			}
			writeLock.release();
		}
		
		else if(serverMessage.equals("EDIT_CATEGORY_ON_SERVER")){
			writeLock.acquire();
			try {
				ServerApplication.serverTodoList.editCategory(dataFromClient.getAdditionalMessage(),
						dataFromClient.getTodoCategory().getKeyword(),
						dataFromClient.getTodoCategory().getName(),
						dataFromClient.getTodoCategory().getIcon());
				dataToClient = new ToxicDatagram("Answering successful request to edit category");
				ServerApplication.writeChangesToDisk();
			} catch (Exception e) {
				Logger.log("Failed to edit category.");
				dataToClient = new ToxicDatagram("Editing a category failed.");
			}
			writeLock.release();
		}
		
		else{
			Logger.log("Command from Client not recognized..");
		}
		return dataToClient;
	}
	
	public synchronized void getUniqueID() throws InterruptedException{
		while(writeLock.availablePermits()==0){
			wait();
		}
	}
}