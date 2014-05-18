package ch.theowinter.toxictodo.sharedobjects;

public final class Logger {
	private final static boolean LOG_ENABLED = true;

	private Logger() {
		super();
	}

	public final static void log(String error){
		Logger.log(error, null);
	}
	
	public final static void log(String error, Exception e){
		if(LOG_ENABLED){
			System.out.println(error); //NOSONAR
		}
	}
}
