package ch.theowinter.toxictodo.client.ui.controller;

import javax.swing.JOptionPane;

import ch.theowinter.toxictodo.client.ClientSettings;
import ch.theowinter.toxictodo.client.ClientTodoManager;
import ch.theowinter.toxictodo.client.ui.view.MainWindow;
import ch.theowinter.toxictodo.server.ServerApplication;
import ch.theowinter.toxictodo.sharedobjects.Logger;

public class ClientController {
	ClientSettings settings;
	
	public void start(ClientTodoManager aTodoManager, ClientSettings someSettings){
		settings = someSettings;
		
		if(aTodoManager.getInitSuccess()){
			createGUI(aTodoManager, someSettings);
		}else if(settings.getIntegratedServerEnabled()){
			launchAndConnectToInternalServer();
		}else{
			createLocalServerDialog();
		}
	}
	
	private void createGUI(ClientTodoManager aTodoManager, ClientSettings someSettings){
		MainWindow mainWindow = new MainWindow(aTodoManager, someSettings);
		mainWindow.launchGUI();
	}
	
	private void createLocalServerDialog(){
		Logger.log("Trying to see if user wants to run a local server");
		int result = JOptionPane.showConfirmDialog(
			    null, "We are unable to detect a server at "+settings.getHOST()+":"+settings.getPORT()+".\n"
			    + "Would you like to start the program locally?\n",
			    "Unable to detect server", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
		if(result == 0){
			launchAndConnectToInternalServer();
		} else{
			Logger.log("User chose to not use a local server and we can't connect to a dedicated server.");
		}
	}
	
	private void launchAndConnectToInternalServer(){
		Logger.log("Launching internal server..");
		new Thread(new ServerApplication()).start();
		ClientTodoManager todoManager;
		do{
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				Logger.log("Interrupted Sleep while waiting for internal server to start.", e);
			}
			todoManager = new ClientTodoManager();
		}while(!todoManager.getInitSuccess());
		createGUI(todoManager, settings);
	}
}
