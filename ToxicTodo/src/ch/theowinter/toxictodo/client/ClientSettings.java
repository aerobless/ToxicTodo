package ch.theowinter.toxictodo.client;

public class ClientSettings {
	private String HOST = "localhost";
	private int PORT = 5222;
	private String password = "secretPassword";
	private boolean debug = false;
	private int consoleSize = 80;
	private boolean integratedServerEnabled = true;
	
	//Getters (CLI & GUI)
	public ClientSettings() {
		super();
	}
	public String getHOST() {
		return HOST;
	}
	public int getPORT() {
		return PORT;
	}
	public String getPassword() {
		return password;
	}
	public boolean isDebug() {
		return debug;
	}
	public int getConsoleSize() {
		return consoleSize;
	}
	
	public boolean getIntegratedServerEnabled(){
		return integratedServerEnabled;
	}
	
	//Setters (only supported in GUI)
	public void setHOST(String hOST) {
		HOST = hOST;
	}
	public void setPORT(int pORT) {
		PORT = pORT;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	public void setConsoleSize(int consoleSize) {
		this.consoleSize = consoleSize;
	}
	public void setIntegratedServerEnabled(boolean enabled){
		this.integratedServerEnabled = enabled;
	}
	
	//Save
	public void saveSettingsToDisk(){
		ClientApplication.saveSettingsToDisk();
	}
	
}
