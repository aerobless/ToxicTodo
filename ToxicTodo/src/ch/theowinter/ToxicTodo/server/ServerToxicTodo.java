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
	static String settingsFile = logic.getJarDirectory()+"/"+"server_config.xml";
	private static final String todoDataFile = logic.getJarDirectory()+"/"+"ToxicTodo.xml";
	static ServerSettings settings;
	
	//Locks
	private static Semaphore serverRunning = new Semaphore(1);

	public static void main(String[] args) { 

		//Load sample data or stored data
		loadSettings();
		
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
					logic.saveToXMLFile(serverTodoList, todoDataFile);
		        	
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
		logic.saveToXMLFile(serverTodoList, todoDataFile);
	}
	
	public static void loadSettings(){
		if(!firstTimeRun()){
			settings = (ServerSettings) logic.loadXMLFile(settingsFile);
		}
		serverTodoList = (TodoList)logic.loadXMLFile(todoDataFile);
	}

	/**
	 * Load some sample data, when the server is started for the first time and
	 * create a new settings file.
	 */
	private static boolean firstTimeRun(){
		boolean firstTime = false;
		File settingsOnDisk = new File(settingsFile);
		if(!settingsOnDisk.exists()){
			serverPrint("INFORMATION:");
			serverPrint("server_settings.xml has been created because you run ToxicTodo for the first time.");
			serverPrint("You can edit the settings to chose your prefered port and encryption password.");
			firstTime = true;
			settings = new ServerSettings();
			logic.saveToXMLFile(settings, settingsFile);
		}
		File todoDataOnDisk = new File(todoDataFile);
		if(!todoDataOnDisk.exists()){
			try {
				serverTodoList.addCategory("School work", "school");
				serverTodoList.addCategory("Programming stuff", "programming");
				serverTodoList.addCategory("To buy", "buy");
				serverTodoList.addTask("school", "Complete exercise 1 for vssprog");
				serverTodoList.addTask("school", "Complete exercise 1 for parprog");
				serverTodoList.addTask("programming", "Build better todolist");
				serverTodoList.addTask("programming", "fix all the bugs");
				serverTodoList.addTask("buy", "new pens");
				writeChangesToDisk();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return firstTime;
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
				server = new ServerSocket(settings.getPort());
				serverPrint("server> Waiting for client...");
				
				client = server.accept();
				
				//Open up a connection:
				Thread serverConnection = new Thread(new ServerOpenConnectionThread(client, settings.getPassword()));
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

