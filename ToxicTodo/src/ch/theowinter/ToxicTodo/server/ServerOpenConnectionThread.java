package ch.theowinter.ToxicTodo.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.concurrent.Semaphore;

import javax.crypto.SealedObject;

import ch.theowinter.ToxicTodo.utilities.EncryptionEngine;
import ch.theowinter.ToxicTodo.utilities.primitives.ToxicDatagram;

class ServerOpenConnectionThread implements Runnable {
	private static Semaphore writeLock = new Semaphore(1);
	private static EncryptionEngine crypto;
	Socket inputSocket;

	public ServerOpenConnectionThread(Socket client) {
		super();
		inputSocket = client;
		try {
			crypto = new EncryptionEngine(ServerToxicTodo.password);
		} catch (Exception anEx) {
			System.err.println("Crypto Error - Unable to load the Encryption Engine");
		}
	}
	
	@Override
	public void run() {
		InputStream is;
		try {
			is = inputSocket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
		        	
			ServerToxicTodo.serverPrint("got a message from a client");
			SealedObject encryptedDataFromClient = (SealedObject)ois.readObject(); 
			//Decrypt data from client
        	ToxicDatagram dataFromClient = null;
    		try {
    			dataFromClient = (ToxicDatagram) crypto.dec(encryptedDataFromClient);
    		} catch (Exception anEx1) {
    			System.err.println("Encryption ERROR - Unable to encrypt & send data!");
    			anEx1.printStackTrace();
    		}
			
			ToxicDatagram dataToClient = new ToxicDatagram("ERROR - 400 - The server has no response for you.", "");
		        	
			try {
				dataToClient = runServerAction(dataFromClient.getServerControlMessage(), dataFromClient);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        	
			OutputStream os = inputSocket.getOutputStream();  
			ObjectOutputStream oos = new ObjectOutputStream(os); 
			
			//Encrypt before sending off
			Object encryptedData = null;
			try {
				encryptedData = crypto.enc(dataToClient);
			} catch (Exception anEx1) {
				System.err.println("Encryption ERROR - Unable to encrypt & send data!");
				anEx1.printStackTrace();
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
				e.printStackTrace();
				} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			}  
		}
	
	public synchronized ToxicDatagram runServerAction(String serverMessage, ToxicDatagram dataFromClient) throws InterruptedException{
		ServerToxicTodo.serverPrint("Trying to handle: "+serverMessage);
		ToxicDatagram dataToClient = new ToxicDatagram("ERROR - 500 - Bad Request to server action handler.", "");
		if(serverMessage.equals("SEND_TODOLIST_TO_CLIENT")){
			//We wait until noone's writing anymore 
			while(writeLock.availablePermits()==0){
				wait();
			}
			dataToClient = new ToxicDatagram("Answering successful request for TodoList", "", ServerToxicTodo.getServerTodoList());
		}
		else if(serverMessage.equals("ADD_TASK_TO_LIST_ON_SERVER")){
			writeLock.acquire();
			try {
				ServerToxicTodo.serverTodoList.addTask(dataFromClient.getAdditionalMessage(), dataFromClient.getTodoTask());
				dataToClient = new ToxicDatagram("Answering successful request to add new task", "");
				ServerToxicTodo.writeChangesToDisk();
			} catch (Exception e) {
				ServerToxicTodo.serverPrint("Failed to add new task.");
				dataToClient = new ToxicDatagram("Adding a new task failed. Maybe it already exists?", "");
			}
			writeLock.release();
		}
		else if(serverMessage.equals("REMOVE_AND_LOG_TASK_AS_COMPLETED_ON_SERVER")){
			writeLock.acquire();
			try {
				ServerToxicTodo.serverTodoList.removeTask(dataFromClient.getTodoTask(), dataFromClient.getAdditionalMessage());
				java.util.Date date= new java.util.Date();
				ServerToxicTodo.writeLogToFile("CompletedTasks.txt", new Timestamp(date.getTime())+" : COMPLETED : "+dataFromClient.getTodoTask().getTaskText());
				dataToClient = new ToxicDatagram("Answering successful request to remove & log task", "");
				ServerToxicTodo.writeChangesToDisk();
			} catch (Exception e) {
				ServerToxicTodo.serverPrint("Failed to remove & log task.");
				dataToClient = new ToxicDatagram("Completing a task failed.", "");
			}
			writeLock.release();
		}
		
		else if(serverMessage.equals("REMOVE_TASK_ON_SERVER")){
			writeLock.acquire();
			try {
				ServerToxicTodo.serverTodoList.removeTask(dataFromClient.getTodoTask(), dataFromClient.getAdditionalMessage());
				dataToClient = new ToxicDatagram("Answering successful request to remove task", "");
				ServerToxicTodo.writeChangesToDisk();
			} catch (Exception e) {
				ServerToxicTodo.serverPrint("Failed to remove task.");
				dataToClient = new ToxicDatagram("Removing a task failed.", "");
			}
			writeLock.release();
		}
		
		else if(serverMessage.equals("ADD_CATEGORY_TO_LIST_ON_SERVER")){
			writeLock.acquire();
			try {
				ServerToxicTodo.serverTodoList.addCategory(dataFromClient.getTodoCategory().getName(), dataFromClient.getTodoCategory().getKeyword());
				dataToClient = new ToxicDatagram("Answering successful request to add category", "");
				ServerToxicTodo.writeChangesToDisk();
			} catch (Exception e) {
				ServerToxicTodo.serverPrint("Failed to add new category.");
				dataToClient = new ToxicDatagram("Adding a category failed.", "");
			}
			writeLock.release();
		}
		
		else if(serverMessage.equals("REMOVE_CATEGORY_ON_SERVER")){
			writeLock.acquire();
			try {
				ServerToxicTodo.serverTodoList.removeCategory(dataFromClient.getTodoCategory().getKeyword());
				dataToClient = new ToxicDatagram("Answering successful request to remove category", "");
				ServerToxicTodo.writeChangesToDisk();
			} catch (Exception e) {
				ServerToxicTodo.serverPrint("Failed to remove category.");
				dataToClient = new ToxicDatagram("Removing a category failed.", "");
			}
			writeLock.release();
		}
		
		else{
			ServerToxicTodo.serverPrint("Command from Client not recognized..");
		}
		return dataToClient;
	}
	
	public synchronized void getUniqueID() throws InterruptedException{
		while(writeLock.availablePermits()==0){
			wait();
		}
	}
}