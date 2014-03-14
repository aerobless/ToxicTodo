package ch.theowinter.ToxicTodo.utilities;

public class LogicEngine {

    /**
     * Concatenates an array from startPosition to the end of the array.
     * Originally built for my minecraft plugin "BloodAST - Advanced Server Tools"
     * @param args
     * @param arrayLength
     * @return concatenatedArray
     */    
    public String[] concatenateArgs(String[] args, int arrayLength){
    	int startPosition = arrayLength-1;
      	String[] concatenatedArray = new String[arrayLength];
    	if (args.length>arrayLength){
	    	StringBuilder builder = new StringBuilder();
	    	//Append all args from the startPosition to the end of the array, -1 because Array starts at 0
	    	for(int i = startPosition; i<args.length;i++){
	    		builder.append(args[i]);
	    		if(i < (args.length-1)){
	    			builder.append(" ");
	    		}
	    	}
	    	//Copy args that weren't concatenated into the new array
	    	for(int i=0; i<concatenatedArray.length; i++){
	    		concatenatedArray[i]=args[i];
	    	}
	    	//Replace last arg with our newly built string, -1 because Array starts at 0
	    	concatenatedArray[startPosition]=builder.toString();
    	}
    	else{
    		concatenatedArray = args;
    	}
		return concatenatedArray;
    }
}
