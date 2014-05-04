package ch.theowinter.toxictodo.sharedobjects;

public class Logger {
	private final static boolean logEnabled = true;
	
	public final static void log(String error){
		Logger.log(error, null);
	}
	
	public final static void log(String error, Exception e){
		if(logEnabled){
			System.out.println(error);
		}
	}
}
