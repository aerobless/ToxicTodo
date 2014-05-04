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
		if(!aTodoManager.getInitSuccess()){
			createLocalServerDialog();
		}else{
			createGUI(aTodoManager, someSettings);
		}
	}
	
	private void createGUI(ClientTodoManager aTodoManager, ClientSettings someSettings){
		MainWindow mainWindow = new MainWindow(aTodoManager, someSettings);
		mainWindow.launchGUI();
	}
	
	private void createLocalServerDialog(){
		int result = JOptionPane.showConfirmDialog(
			    null, "We were unable to detect a server.\n"
			    + "Would you like to start the program locally?\n",
			    "Unable to detect server", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
		if(result == 0){
			//YES - We start a local server for the user.
			System.out.println("Running integrated server!");
			new Thread(new ServerApplication()).start();
			ClientTodoManager todoManager;
			do{
				try {
					Thread.sleep(5);
				} catch (InterruptedException anEx) {
					System.out.println("Interrupted Sleep");
				}
				todoManager = new ClientTodoManager();
			}while(!todoManager.getInitSuccess());
			if(todoManager.getInitSuccess()){
				createGUI(todoManager, settings);
			}else{
				Logger.log("ERROR 7: Unable to connect");
				System.exit(0);
			}
		} else{
			//NO - We exit here because the user doesn't want to run a local server.
			System.exit(0);	
		}
	}
}
