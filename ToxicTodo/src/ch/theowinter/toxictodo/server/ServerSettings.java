package ch.theowinter.toxictodo.server;

public class ServerSettings {
	private int port = 5222;
	private String password = "secretPassword";
	private boolean debug = false;
	
	/** 
	 * Empty constructor because we will always load settings directly from the xml file.
	 * Changing the config during runtime is not supported.
	 */
	public ServerSettings() {
		super();
	}
	public int getPort() {
		return port;
	}
	public String getPassword() {
		return password;
	}
	public boolean isDebug() {
		return debug;
	}

}
