package ch.theowinter.ToxicTodo.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import ch.theowinter.ToxicTodo.utilities.primitives.TodoCategory;
import ch.theowinter.ToxicTodo.utilities.primitives.TodoTask;

public class ServerToxicTodo {
	//Server data:
	public static ArrayList<TodoCategory> serverTodo = new ArrayList<TodoCategory>();
	public static final String todoData = "ToxicTodo.xml";
	
	//Locks
	private static Semaphore serverRunning = new Semaphore(1);
	
	//Connection info:
	public static final int PORT = 5222;

	@SuppressWarnings("unchecked") //It's fine to suppress that warning because we can't possibly know what's in the file we're loading.
	public static void main(String[] args) { 

		//Load sample data or stored data
		if(!firstTimeRun()){
			serverTodo = (ArrayList<TodoCategory>)loadXMLFile(todoData);
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
		        	saveToXMLFile(serverTodo, todoData);
		        	
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
	
	static void saveToXMLFile(Object inputObject, String filename){
		FileOutputStream fos = null;
		try {
		    fos = new FileOutputStream(filename);
		    byte[] bytes = serializeToXML(inputObject).getBytes("UTF-8");
		    fos.write(bytes);

		} catch(Exception e) {
			System.err.println("Error: Can't save the file.");
		} finally {
		    if(fos!=null) {
		        try{ 
		            fos.close();
		        } catch (IOException e) {
		        	System.err.println("Error: Can't close File Output Stream");
		        }
		    }
		}
	}
	
	private static String serializeToXML(Object input){
		XStream saveXStream = new XStream(new StaxDriver());
		saveXStream.alias("category", TodoCategory.class);
		saveXStream.alias("task", TodoTask.class);		
		return saveXStream.toXML(input);
	}
	
	private static Object loadXMLFile(String filename){
		File xmlFile = new File(filename);
		XStream loadXStream = new XStream(new StaxDriver());
		loadXStream.alias("category", TodoCategory.class);
		loadXStream.alias("task", TodoTask.class);	
		Object loadedObject = loadXStream.fromXML(xmlFile);
		return loadedObject;
	}
	
	/**
	 * Load some sample data, when the server is started for the first time.
	 */
	private static boolean firstTimeRun(){
		boolean firstTime = false;
		File f = new File(todoData);
		if(!f.exists()){
			serverTodo.add(new TodoCategory("School work", "school"));
			serverTodo.add(new TodoCategory("Programming stuff", "programming"));
			serverTodo.add(new TodoCategory("To buy", "buy"));		
			serverTodo.get(0).add("Complete exercise 1 for vssprog");
			serverTodo.get(0).add("Complete exercise 1 for parprog");
			serverTodo.get(1).add("Build better todolist");
			serverTodo.get(1).add("fix all the bugs");
			serverTodo.get(2).add("new pens");
			firstTime = true;
		}
		return firstTime;
	}
	
	public static void serverPrint(String input){
		//TODO: add better logging and logging to file
		System.out.println(input);
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
				Thread serverConnection = new Thread(new OpenConnectionThread(client));
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

