package ch.theowinter.ToxicTodo.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import ch.theowinter.ToxicTodo.utilities.LogicEngine;
import ch.theowinter.ToxicTodo.utilities.primitives.TodoList;

public class ServerToxicTodo {
	//Server data:
	static TodoList serverTodoList = new TodoList();
	static LogicEngine logic = new LogicEngine();
	public static final String localTodoDataStorage = "ToxicTodo.xml";
	public final static String password = "secretPassword";
	
	//Locks
	private static Semaphore serverRunning = new Semaphore(1);
	
	//Connection info:
	public static final int PORT = 5222;

	public static void main(String[] args) { 

		//Load sample data or stored data
		if(!firstTimeRun()){
			serverTodoList = (TodoList)logic.loadXMLFile(localTodoDataStorage);
		}
		
		//Open up a connection:
		Thread connectionBuilder = new Thread(new ConnectionBuilderThread());
		connectionBuilder.setDaemon(true);
		connectionBuilder.start();
		
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
					//Save before shutting the server down
					logic.saveToXMLFile(serverTodoList, localTodoDataStorage);
		        	
		        	//After this the main method is finished and the daemon threads get killed
					serverRunning.acquire();
				}
			} catch (IOException e) {
				System.err.println("Toxic Todo: Server Control Thread - IO Exception");
			} catch (InterruptedException e) {
				System.err.println("InterruptedException");
			}
		}
	}
	
	/**
	 * Load some sample data, when the server is started for the first time.
	 */
	private static boolean firstTimeRun(){
		boolean firstTime = false;
		File f = new File(localTodoDataStorage);
		if(!f.exists()){
			try {
				serverTodoList.addCategory("School work", "school");
				serverTodoList.addCategory("Programming stuff", "programming");
				serverTodoList.addCategory("To buy", "buy");
				serverTodoList.addTask("school", "Complete exercise 1 for vssprog");
				serverTodoList.addTask("school", "Complete exercise 1 for parprog");
				serverTodoList.addTask("programming", "Build better todolist");
				serverTodoList.addTask("programming", "fix all the bugs");
				serverTodoList.addTask("buy", "new pens");
			} catch (Exception e) {
				e.printStackTrace();
			}
			firstTime = true;
		}
		return firstTime;
	}
	
	public static void serverPrint(String input){
		//TODO: add better logging and logging to file
		System.out.println("Server: "+input);
	}
	
	public static TodoList getServerTodoList() {
		return serverTodoList;
	}

	public static void setServerTodoList(TodoList serverTodoList) {
		ServerToxicTodo.serverTodoList = serverTodoList;
	}
	
	public static void writeLogToFile(String logFilename, String logMessage){
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(logFilename, true)))) {
		    out.println(logMessage);
		}catch (IOException e) {
			serverPrint("ERROR: IO-Exception when trying to write log to file. ID: 777");
		}
	}
	
	public static void writeChangesToDisk(){
		logic.saveToXMLFile(serverTodoList, localTodoDataStorage);
	}

	static class ConnectionBuilderThread implements Runnable{
		@Override
		public void run() {
			try {
			while(serverRunning.availablePermits()>0){
				@SuppressWarnings("resource") //socket gets closed in the OpenConnectionThread
				Socket client = new Socket();
				client.setSoTimeout(100);
				ServerSocket server;
				server = new ServerSocket(PORT);
				serverPrint("server> Waiting for client...");
				
				client = server.accept();
				
				//Open up a connection:
				Thread serverConnection = new Thread(new ServerOpenConnectionThread(client));
				serverConnection.setDaemon(true);
				serverConnection.start();

				//Report active Threads
				serverPrint("Active Threads: "+Thread.activeCount());

				server.close();
				}
				} catch (IOException e) {
					e.printStackTrace();
			}
		}
	}
}

