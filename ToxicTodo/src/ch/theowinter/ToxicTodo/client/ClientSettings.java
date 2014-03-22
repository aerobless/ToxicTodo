package ch.theowinter.ToxicTodo.client;

public class ClientSettings {
	private final String HOST = "localhost";
	private final int PORT = 5222;
	private final String password = "secretPassword";
	private final boolean debug = false;
	
	/** 
	 * Empty constructor because we will always load settings directly from the xml file.
	 * Changing the config during runtime is not supported.
	 */
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
}
