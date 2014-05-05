package ch.theowinter.toxictodo.client;

public class ClientSettings {
	private String host = "localhost";
	private int port = 5222;
	private String password = "secretPassword";
	private boolean debug = false;
	private int consoleSize = 80;
	private boolean integratedServerEnabled = true;
	
	//Getters (CLI & GUI)
	public ClientSettings() {
		super();
	}
	public String getHOST() {
		return host;
	}
	public int getPORT() {
		return port;
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
		host = hOST;
	}
	public void setPORT(int pORT) {
		port = pORT;
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
