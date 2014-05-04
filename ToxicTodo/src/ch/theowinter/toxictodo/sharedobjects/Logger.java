package ch.theowinter.toxictodo.sharedobjects;

public class Logger {
	final static boolean logEnabled = true;
	
	final static void log(String error){
		if(logEnabled){
			System.out.println(error);
		}
		
	}
	
	final static void log(String error, Exception e){
		if(logEnabled){
			System.out.println(error);
		}
	}
}
