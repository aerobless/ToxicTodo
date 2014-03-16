package ch.theowinter.ToxicTodo.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import ch.theowinter.ToxicTodo.utilities.primitives.ToxicDatagram;

class ServerOpenConnectionThread implements Runnable {
	private static Semaphore writeLock = new Semaphore(1);
	Socket inputSocket;

	public ServerOpenConnectionThread(Socket client) {
		super();
		inputSocket = client;
	}
	
	@Override
	public void run() {
		InputStream is;
		try {
			is = inputSocket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
		        	
			ServerToxicTodo.serverPrint("got a message from a client");
			ToxicDatagram dataFromClient = (ToxicDatagram)ois.readObject();  	
			ToxicDatagram dataToClient = new ToxicDatagram("ERROR - 400 - The server has no response for you.", "");
		        	
			try {
				dataToClient = runServerAction(dataFromClient.getServerControlMessage(), dataFromClient);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        	
			OutputStream os = inputSocket.getOutputStream();  
			ObjectOutputStream oos = new ObjectOutputStream(os);     	
			oos.writeObject(dataToClient);
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
				dataToClient = new ToxicDatagram("Answering successful request to add new Task", "");
			} catch (Exception e) {
				ServerToxicTodo.serverPrint("Failed to add new task.");
				dataToClient = new ToxicDatagram("Adding a new task failed. Maybe it already exists?", "");
			}
		}
		return dataToClient;
	}
	
	public synchronized void getUniqueID() throws InterruptedException{
		while(writeLock.availablePermits()==0){
			wait();
		}
	}
}