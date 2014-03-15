package ch.theowinter.ToxicTodo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import ch.theowinter.ToxicTodo.utilities.primitives.TodoCategory;
import ch.theowinter.ToxicTodo.utilities.primitives.ToxicDatagram;

public class ServerToxicTodo {
	//Server data:
	private static ArrayList<TodoCategory> serverTodo = new ArrayList<TodoCategory>();
	private static final String todoData = "ToxicTodo.xml";
	
	//Locks
	private static Semaphore serverRunning = new Semaphore(1);
	private static Semaphore writeLock = new Semaphore(1);
	
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
	
	private static void saveToXMLFile(Object inputObject, String filename){
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
		return saveXStream.toXML(input);
	}
	
	private static Object loadXMLFile(String filename){
		File xmlFile = new File(filename);
		XStream loadXStream = new XStream(new StaxDriver());
		loadXStream.alias("category", TodoCategory.class);
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
	
	static class OpenConnectionThread implements Runnable {
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
			        	
			        	serverPrint("got a message from a client");
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
		        	saveToXMLFile(serverTodo, todoData);
				}
		
		public synchronized ToxicDatagram runServerAction(String serverMessage, ArrayList<TodoCategory> todoList) throws InterruptedException{
			ToxicDatagram dataToClient = new ToxicDatagram(null, "Bad request 500", "");
			if(serverMessage.equals("read")){
				//We wait until noone's writing anymore 
				while(writeLock.availablePermits()==0){
					wait();
				}
				dataToClient = new ToxicDatagram(serverTodo, "successfulREAD", "");
			}
			else if(serverMessage.equals("write")){
				writeLock.acquire();
				serverTodo = todoList;
				writeLock.release();
				dataToClient = new ToxicDatagram(null, "successfulWRITE", "");
				notifyAll();
			}
			return dataToClient;
		}
	}
}

