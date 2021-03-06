package ch.theowinter.toxictodo.client.cli;

public class JansiFormats {
	
	private JansiFormats() {
		super();
	}
	
	//Colors
    public static final String BLACK = "\u001B[0;30m";
    public static final String RED = "\u001B[0;31m";
    public static final String GREEN = "\u001B[0;32m";
    public static final String YELLOW = "\u001B[0;33m";
    public static final String BLUE = "\u001B[0;34m";
    public static final String MAGENTA = "\u001B[0;35m";
    public static final String CYAN = "\u001B[0;36m";
    public static final String WHITE = "\u001B[0;37m";
    
    //Additional Settings
    public static final String ANSI_CLS = "\u001b[2J";
    public static final String ANSI_HOME = "\u001b[H";
    public static final String ANSI_BOLD = "\u001b[1m";
    public static final String ANSI_AT55 = "\u001b[10;10H";
    public static final String ANSI_REVERSEON = "\u001b[7m";
    public static final String ANSI_NORMAL = "\u001b[0m";
    public static final String ANSI_WHITEONBLUE = "\u001b[37;44m";
}
