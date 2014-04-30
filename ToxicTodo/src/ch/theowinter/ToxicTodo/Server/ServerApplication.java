package ch.theowinter.ToxicTodo.Server;

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

import ch.theowinter.ToxicTodo.SharedObjects.LogicEngine;
import ch.theowinter.ToxicTodo.SharedObjects.Elements.TodoCategory;
import ch.theowinter.ToxicTodo.SharedObjects.Elements.TodoList;

public class ServerApplication implements Runnable{
	//Vanity info
	public static final double serverVersion = 1.28;
	public static final String author = "Theo Winter";
	public static final String website = "theowinter.ch";
	public static final String serverUpdateURL = "http://w1nter.net:8080/job/ToxicTodo/lastSuccessfulBuild/artifact/ToxicTodo/dist/ToxicTodoServer.jar";	
	
	//Class variables
	static TodoList serverTodoList = new TodoList();
	static LogicEngine logic = new LogicEngine();
	static String settingsFile = logic.getJarDirectory("server_config.xml");
	private static final String todoDataFile = logic.getJarDirectory("ToxicTodo.xml");
	static ServerSettings settings;
	
	//Locks
	private static Semaphore stopServer = new Semaphore(1);

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
		while(stopServer.availablePermits()>0){
			BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
			try {
				String input = buffer.readLine();
				if(input.equals("stop") || input.equals("exit") || input.equals("q")){
					logic.saveToXMLFile(serverTodoList, todoDataFile);
					stopServer.acquire();
				}
				else if(input.equals("update")){
					serverPrint("Updating... Please wait a few seconds before starting the server again!");
					logic.updateSoftware(serverUpdateURL);
					stopServer.acquire();
				}
				else if(input.equals("about")||input.equals("identify")){
					serverPrint("### - ABOUT TOXIC TODO - ###");
					String space = "  ";
					serverPrint(space+"Version: "+serverVersion);
					serverPrint(space+"Author:  "+author);
					serverPrint(space+"Website: "+website);
				}
				else{
					serverPrint("Command *"+input+"* not recognized.");
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
		ServerApplication.serverTodoList = serverTodoList;
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
			serverPrint("Server_config.xml has been created because you run ToxicTodo for the first time.");
			serverPrint("You can edit the settings to chose your prefered port and encryption password.");
			firstTime = true;
			settings = new ServerSettings();
			logic.saveToXMLFile(settings, settingsFile);
		}
		File todoDataOnDisk = new File(todoDataFile);
		if(!todoDataOnDisk.exists()){
			try {
				serverTodoList.addCategory(new TodoCategory("School work", "school"));
				serverTodoList.addCategory(new TodoCategory("Programming projects", "programming"));
				serverTodoList.addCategory(new TodoCategory("Shopping list", "buy"));
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
			while(stopServer.availablePermits()>0){
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

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		main(new String[]{});		
	}
}

