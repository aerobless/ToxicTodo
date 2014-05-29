package ch.theowinter.toxictodo.sharedobjects;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import ch.theowinter.toxictodo.client.ClientApplication;
import ch.theowinter.toxictodo.client.ClientSettings;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoCategory;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoList;
import ch.theowinter.toxictodo.sharedobjects.elements.TodoTask;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

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
    	} else{
    		concatenatedArray = args;
    	}
		return concatenatedArray;
    }
    
    
    /**
     * Save something as xml file.
     * @param inputObject
     * @param filename
     */
    public void saveToXMLFile(Object inputObject, String filename){
		FileOutputStream fos = null;
		try {
		    fos = new FileOutputStream(filename);
		    byte[] bytes = serializeToXML(inputObject).getBytes("UTF-8");
		    fos.write(bytes);

		} catch(Exception e) {
			Logger.log("Error: Can't save the file.", e);
		} finally {
		    if(fos!=null) {
		        try{ 
		            fos.close();
		        } catch (IOException e) {
		        	Logger.log("Error: Can't close File Output Stream", e);
		        }
		    }
		}
	}
	
	private String serializeToXML(Object input){
		XStream saveXStream = new XStream(new StaxDriver());
		saveXStream.alias("list", TodoList.class);
		saveXStream.alias("category", TodoCategory.class);
		saveXStream.alias("task", TodoTask.class);	
		saveXStream.alias("client_settings", ClientSettings.class);
		return saveXStream.toXML(input);
	}
	
	/**
	 * Load something from a xml file.
	 * @param filename
	 * @return
	 */
	public Object loadXMLFile(String filename){
		File xmlFile = new File(filename);
		XStream loadXStream = new XStream(new StaxDriver());
		loadXStream.alias("list", TodoList.class);
		loadXStream.alias("category", TodoCategory.class);
		loadXStream.alias("task", TodoTask.class);	
		loadXStream.alias("client_settings", ClientSettings.class);
		Object loadedObject = loadXStream.fromXML(xmlFile);
		return loadedObject;
	}
	
	public String getJarDirectory(String filename){
		URL jarLocation = LogicEngine.class.getProtectionDomain().getCodeSource().getLocation();
		URL dataXML = null;
		try {
			dataXML = new URL(jarLocation, filename);
		} catch (MalformedURLException e) {
			Logger.log("Malformed URL in LogicEngine. JarLoc: "+jarLocation+" Filename: "+filename, e);
		}
		return dataXML.getPath();
	}
	
	public boolean updateSoftware(String updateURL, boolean updateWithGUI){
		String tinyUpdater = "http://w1nter.net:8080/job/TinyUpdater/lastSuccessfulBuild/artifact/TinyUpdater/dist/TinyUpdater.jar";
		String[] updateArray  = tinyUpdater.split("/");
		String downloadPath = getJarDirectory(updateArray[updateArray.length-1]);
		downloadFile(tinyUpdater, downloadPath);
		String isGUI = "";
		if(updateWithGUI){
			isGUI=" ToxicTodo";
		}
		try {
			if("windows".equals(ClientApplication.OS)){
				Runtime.getRuntime().exec("java -jar "+downloadPath.substring(1)+" 10 "+updateURL+isGUI);
			}else{
				Runtime.getRuntime().exec("java -jar "+downloadPath+" 10 "+updateURL+isGUI);
			}
		} catch (IOException e) {
			Logger.log("IOException trying to updateSoftware in LogicEngine", e);
		}
		return true;
	}
	
	public void downloadFile(String downloadURL, String filenameAndPath) {
		try {
			URL url = new URL(downloadURL);
			URLConnection con = url.openConnection(); 
			DataInputStream dis = new DataInputStream(con.getInputStream());
			byte[] fileData = new byte[con.getContentLength()];
			for (int x = 0; x < fileData.length; x++) {
				fileData[x] = dis.readByte();
			}
			dis.close(); 
			FileOutputStream fos = new FileOutputStream(new File(filenameAndPath));
			fos.write(fileData); 
			fos.close();
		} catch (MalformedURLException m) {
			Logger.log("Malformed URL in LogicEngine.",m);
		} catch (IOException io) {
			Logger.log("IOException URL in LogicEngine.",io);
		}
	}
}
