package ch.theowinter.ToxicTodo.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import ch.theowinter.ToxicTodo.utilities.primitives.TodoCategory;
import ch.theowinter.ToxicTodo.utilities.primitives.ToxicDatagram;

class OpenConnectionThread implements Runnable {
	private static Semaphore writeLock = new Semaphore(1);
	Socket inputSocket;

public OpenConnectionThread(Socket client) {
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
	        	ToxicDatagram dataToClient = new ToxicDatagram(null, "Server Error 400", "");
	        	
	        	try {
					dataToClient = runServerAction(dataFromClient.getServerControlMessage(), dataFromClient.getTodoList());
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
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}  
        	//Always save possible changes
        	ServerToxicTodo.saveToXMLFile(ServerToxicTodo.serverTodoList, ServerToxicTodo.todoData);
		}

public synchronized ToxicDatagram runServerAction(String serverMessage, ArrayList<TodoCategory> todoList) throws InterruptedException{
	ToxicDatagram dataToClient = new ToxicDatagram(null, "Bad request 500", "");
	if(serverMessage.equals("read")){
		//We wait until noone's writing anymore 
		while(writeLock.availablePermits()==0){
			wait();
		}
		//dataToClient = new ToxicDatagram(ServerToxicTodo.serverTodo, "successfulREAD", "");
	}
	else if(serverMessage.equals("write")){
		writeLock.acquire();
		//ServerToxicTodo.serverTodo = todoList;
		writeLock.release();
		dataToClient = new ToxicDatagram(null, "successfulWRITE", "");
		notifyAll();
	}
	return dataToClient;
}

public synchronized void getUniqueID() throws InterruptedException{
	while(writeLock.availablePermits()==0){
		wait();
	}
}
}