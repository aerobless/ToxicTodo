package ch.theowinter.toxictodo.sharedobjects;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class LocationEngine {
	
	/**
	 * Downloads a website and returns a string containing the html
	 * or xml data.
	 *
	 * @param httpURL
	 * @return data
	 * @throws IOException
	 */
	public String webToString(String httpURL) throws IOException{
		URL url = new URL(httpURL);
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[8192];
		int len = 0;
		while ((len = in.read(buf)) != -1) {
		    baos.write(buf, 0, len);
		}
		return new String(baos.toByteArray(), encoding);
	}
	
	public Document downloadLocationInfo() throws ParserConfigurationException, SAXException, IOException{
		InputStream stream = new ByteArrayInputStream(webToString("http://freegeoip.net/xml/").getBytes(StandardCharsets.UTF_8));
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document result = documentBuilder.parse(stream);
		return result;

	}
	
	public String getCity() throws ParserConfigurationException, SAXException, IOException{
		Document locationInfo = downloadLocationInfo();
		return locationInfo.getElementsByTagName("City").item(0).getTextContent();
	}
	
	public String getRegion() throws ParserConfigurationException, SAXException, IOException{
		Document locationInfo = downloadLocationInfo();
		return locationInfo.getElementsByTagName("RegionName").item(0).getTextContent();
	}
	
	public String getCountry() throws ParserConfigurationException, SAXException, IOException{
		Document locationInfo = downloadLocationInfo();
		return locationInfo.getElementsByTagName("CountryName").item(0).getTextContent();
	}
	
	public String getPostal() throws ParserConfigurationException, SAXException, IOException{
		Document locationInfo = downloadLocationInfo();
		return locationInfo.getElementsByTagName("ZipCode").item(0).getTextContent();
	}

}
