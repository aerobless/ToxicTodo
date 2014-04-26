package ch.theowinter.ToxicTodo.client.UI.Controller;

import ch.theowinter.ToxicTodo.client.ClientSettings;
import ch.theowinter.ToxicTodo.client.ClientTodoManager;
import ch.theowinter.ToxicTodo.client.UI.View.MainWindow;

public class ClientController {
	public void start(ClientTodoManager aTodoManager, ClientSettings someSettings){
		MainWindow mainWindow = new MainWindow(aTodoManager, someSettings);
		mainWindow.launchGUI();
	}
}
