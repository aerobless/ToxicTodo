package ch.theowinter.toxictodo.server;

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

import ch.theowinter.toxictodo.sharedobjects.Logger;
import ch.theowinter.toxictodo.sharedobjects.LogicEngine;
import ch.theowinter.toxictodo.sharedobjects.SharedInformation;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoCategory;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoList;

public class ServerApplication implements Runnable{
	//Vanity info
	public static final String SERVER_UPDATE_URL = "http://w1nter.net:8080/job/ToxicTodo/lastSuccessfulBuild/artifact/ToxicTodo/dist/ToxicTodoServer.jar";	
	
	//Class variables
	static TodoList todoListActiveTasks = new TodoList();
	static TodoList todoListHistoricTasks = new TodoList();
	static LogicEngine logic = new LogicEngine();
	static String settingsFile = logic.getJarDirectory("server_config.xml");
	private static final String ACTIVE_TODODATA_FILE = logic.getJarDirectory("ToxicTodo.xml");
	private static final String HISTORIC_TODODATA_FILE = logic.getJarDirectory("ToxicTodoHistory.xml");
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
		Logger.log("Toxic Todo: Server Controller started");
		while(stopServer.availablePermits()>0){
			BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
			try {
				String input = buffer.readLine();
				if("stop".equals(input) || "exit".equals(input) || "q".equals(input)){
					logic.saveToXMLFile(todoListActiveTasks, ACTIVE_TODODATA_FILE);
					stopServer.acquire();
				} else if ("update".equals(input)){
					Logger.log("Updating... Please wait a few seconds before starting the server again!");
					logic.updateSoftware(SERVER_UPDATE_URL, false);
					stopServer.acquire();
				} else if ("about".equals(input)||"identify".equals(input)){
					Logger.log("### - ABOUT TOXIC TODO - ###");
					String space = "  ";
					Logger.log(space+"Version: "+SharedInformation.VERSION);
					Logger.log(space+"Author:  "+SharedInformation.AUTHOR);
					Logger.log(space+"Website: "+SharedInformation.WEBSITE);
				} else {
					Logger.log("Command *"+input+"* not recognized.");
				}
			} catch (IOException e) {
				Logger.log("Toxic Todo: Server Control Thread - IO Exception", e);
			} catch (InterruptedException e) {
				Logger.log("InterruptedException", e);
			}
		}
	}
	
	public static TodoList getServerActiveTodoList() {
		return todoListActiveTasks;
	}
	
	public static TodoList getServerHistoricTodoList() {
		return todoListHistoricTasks;
	}

	public static void setServerTodoList(TodoList serverTodoList) {
		ServerApplication.todoListActiveTasks = serverTodoList;
	}
	
	public static void writeLogToFile(String logFilename, String logMessage){
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(logFilename, true)))) {
		    out.println(logMessage);
		}catch (IOException e) {
			Logger.log("ERROR: IO-Exception when trying to write log to file. ID: 777", e);
		}
	}
	
	public static void writeChangesToDisk(){
		logic.saveToXMLFile(todoListActiveTasks, ACTIVE_TODODATA_FILE);
		logic.saveToXMLFile(todoListHistoricTasks, HISTORIC_TODODATA_FILE);
	}
	
	public static void loadSettings(){
		if(!firstTimeRun()){
			settings = (ServerSettings) logic.loadXMLFile(settingsFile);
		}
		todoListActiveTasks = (TodoList)logic.loadXMLFile(ACTIVE_TODODATA_FILE);
		todoListHistoricTasks = (TodoList)logic.loadXMLFile(HISTORIC_TODODATA_FILE);
	}

	/**
	 * Load some sample data, when the server is started for the first time and
	 * create a new settings file.
	 */
	private static boolean firstTimeRun(){
		boolean firstTime = false;
		File settingsOnDisk = new File(settingsFile);
		if(!settingsOnDisk.exists()){
			Logger.log("INFORMATION:");
			Logger.log("Server_config.xml has been created because you run ToxicTodo for the first time.");
			Logger.log("You can edit the settings to chose your prefered port and encryption password.");
			firstTime = true;
			settings = new ServerSettings();
			logic.saveToXMLFile(settings, settingsFile);
		}
		File todoDataOnDisk = new File(ACTIVE_TODODATA_FILE);
		if(!todoDataOnDisk.exists()){
			try {
				todoListActiveTasks.addCategory(new TodoCategory("School work", "school"));
				todoListActiveTasks.addCategory(new TodoCategory("Programming projects", "programming"));
				todoListActiveTasks.addCategory(new TodoCategory("Shopping list", "buy"));
				todoListActiveTasks.addTask("school", "Complete exercise 1 for vssprog");
				todoListActiveTasks.addTask("school", "Complete exercise 1 for parprog");
				todoListActiveTasks.addTask("programming", "Build better todolist");
				todoListActiveTasks.addTask("programming", "fix all the bugs");
				todoListActiveTasks.addTask("buy", "new pens");
				writeChangesToDisk();
				Logger.log("Successfully created default categories and tasks.");
			} catch (Exception e) {
				Logger.log("Unable to add default categories on server.", e);
			}
		}
		File todoHistoryOnDisk = new File(HISTORIC_TODODATA_FILE);
		if(!todoHistoryOnDisk.exists()){
			try {
				todoListHistoricTasks.addCategory(new TodoCategory("System", "system"));
				todoListHistoricTasks.addTask("system", "ToxicTodo History System initalized");
				Logger.log("Sucessfully initalized History-System.");
			} catch (Exception e) {
				Logger.log("Unable to init history on server.", e);
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
				Logger.log("server> Waiting for client...");
				
				client = server.accept();
				
				//Open up a connection:
				Thread serverConnection = new Thread(new ServerOpenConnectionThread(client, settings.getPassword()));
				serverConnection.setDaemon(true);
				serverConnection.start();

				//Report active Threads
				Logger.log("Active Threads: "+Thread.activeCount());

				server.close();
				}
				} catch (IOException e) {
					Logger.log("IO-Exception in ConnectionBuilderThread", e);
			}
		}
	}

	@Override
	public void run() {
		main(new String[]{});		
	}
}

