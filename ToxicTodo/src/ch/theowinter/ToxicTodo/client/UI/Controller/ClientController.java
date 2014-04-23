package ch.theowinter.ToxicTodo.client.UI.Controller;

import ch.theowinter.ToxicTodo.client.ClientTodoManager;
import ch.theowinter.ToxicTodo.client.UI.View.MainWindowView;

public class ClientController {
	public void start(ClientTodoManager aTodoManager){
		MainWindowView mainWindow = new MainWindowView();
		mainWindow.launchGUI();
	}
}
