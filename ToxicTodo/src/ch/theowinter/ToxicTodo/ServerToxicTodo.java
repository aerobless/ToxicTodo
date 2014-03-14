package ch.theowinter.ToxicTodo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import ch.theowinter.ToxicTodo.utilities.primitives.TodoCategory;
import ch.theowinter.ToxicTodo.utilities.primitives.ToxicDatagram;

public class ServerToxicTodo {
	//Server data:
	private static ArrayList<TodoCategory> serverTodo = new ArrayList<TodoCategory>();
	private static Semaphore serverRunning = new Semaphore(1);
	
	//Connection info:
	public static final int PORT = 5222;


	public static void main(String[] args) {		//TODO: fix throws exception to correctly handled try-catches	
		//SAMPLE DATA:
		serverTodo.add(new TodoCategory("School work", "school"));
		serverTodo.add(new TodoCategory("Programming stuff", "programming"));
		serverTodo.add(new TodoCategory("To buy", "buy"));		
		serverTodo.get(0).add("Complete exercise 1 for vssprog");
		serverTodo.get(0).add("Complete exercise 1 for parprog");
		serverTodo.get(1).add("Build better todolist");
		serverTodo.get(1).add("fix all the bugs");
		serverTodo.get(2).add("new pens");
		
		//Open up a connection:
		Thread serverConnection = new Thread(new ServerConnection());
		serverConnection.setDaemon(true);
		serverConnection.start();
		
		//Handle shutdown command and possibly more in the future..
		serverController();
	}
	
	private static void serverController() {
		System.out.println("Toxic Todo: Server Controller started");
		while(serverRunning.availablePermits()>0){
			BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
			try {
				String input = buffer.readLine();
				if(input.equals("stop") || input.equals("exit") || input.equals("q")){
					serverRunning.acquire();
				}
			} catch (IOException e) {
				System.err.println("Toxic Todo: Server Control Thread - IO Exception");
			} catch (InterruptedException e) {
				System.err.println("InterruptedException");
			}
		}
	}
	
	public static void serverPrint(String input){
		//TODO: add better logging and logging to file
		System.out.println(input);
	}
	
	static class ServerConnection implements Runnable {
		@Override
		public void run() {
			while(serverRunning.availablePermits()>0){
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
		        } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		}
	}
}
