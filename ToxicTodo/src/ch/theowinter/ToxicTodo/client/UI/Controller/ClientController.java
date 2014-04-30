package ch.theowinter.ToxicTodo.client.UI.Controller;

import javax.swing.JOptionPane;

import ch.theowinter.ToxicTodo.Server.ServerApplication;
import ch.theowinter.ToxicTodo.client.ClientSettings;
import ch.theowinter.ToxicTodo.client.ClientTodoManager;
import ch.theowinter.ToxicTodo.client.UI.View.MainWindow;

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
			new Thread(new ServerApplication()).start();
			 System.out.println("dd");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException anEx) {
				// TODO Auto-generated catch block
				anEx.printStackTrace();
			}
			ClientTodoManager todoManager = new ClientTodoManager();
			createGUI(todoManager, settings);
		} else{
			//NO - We exit here because the user doesn't want to run a local server.
			System.exit(0);	
		}
	}
}
