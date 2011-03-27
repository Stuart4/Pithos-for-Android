package com.cetus.pithos.XMLRPC;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.cetus.pithos.User;

public class XMLRPCResponse {
	// TODO: this whole class is a disaster. Need either a better way of handling XML
	// or a serious refactor
	private String xml;
	
    public XMLRPCResponse(String xml) {
    	this.xml = xml;
    }
    
    public String getResponseString() {
    	return this.xml;
    }
    
    public void parseUser() {
    	try {
	        DocumentBuilderFactory dbf =
	        DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        InputSource is = new InputSource();
	        is.setCharacterStream(new StringReader(this.getResponseString()));

	        Document doc = db.parse(is);
	        NodeList nodes = doc.getElementsByTagName("member");

	        // iterate the employees
	        for (int i = 0; i < nodes.getLength(); i++) {
	            Element element = (Element) nodes.item(i);

	            NodeList name = element.getElementsByTagName("name");
	            Element line = (Element) name.item(0);
	            String key = getCharacterDataFromElement(line);

	            NodeList title = element.getElementsByTagName("value");
	            line = (Element) title.item(0);
	            String value = getCharacterDataFromElement(line);
	           
	            User.getSingleton().setAttribute(key, value);
	        }
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
    }
    
    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        
        if (child instanceof CharacterData) {
           CharacterData cd = (CharacterData) child;
           return cd.getData();
        }
        
        return "";
      }

    public void parseStations() {
    	
    	try {
    		DocumentBuilderFactory dbf =
    			DocumentBuilderFactory.newInstance();
    	    DocumentBuilder db = dbf.newDocumentBuilder();
    	    InputSource is = new InputSource();
    	    is.setCharacterStream(new StringReader(this.getResponseString()));

    	    Document doc = db.parse(is);
    	    NodeList nodes = doc.getElementsByTagName("data");

    	    // iterate the employees
    	    for (int i = 0; i < nodes.getLength(); i++) {
    	        Element element = (Element) nodes.item(i);

    	       // NodeList value = element.getElementsByTagName("value");
    	       // Element line = (Element) name.item(0);
    	       // line = (Element) value.item(0);
    	       
    	           
    	        //User.getSingleton().setAttribute(key, value);
    	    }
    	} catch (Exception e) {
	        e.printStackTrace();
	    }    	
    }
    
    public boolean hasFault() {        
        try {
    		DocumentBuilderFactory dbf =
    	    DocumentBuilderFactory.newInstance();
    	    DocumentBuilder db = dbf.newDocumentBuilder();
    	    InputSource is = new InputSource();
    	    is.setCharacterStream(new StringReader(this.getResponseString()));

			/*
			<methodResponse>
			    <fault>
			        <value>
			            <struct>
			                <member>
			                    <name>faultString</name>
			                    <value>com.savagebeast.radio.api.protocol.xmlrpc.RadioXmlRpcException: 192.168.162.47|1301164165871|AUTH_INVALID_USERNAME_PASSWORD|Invalid username and/or password</value>
			                </member>
			                <member>
			                    <name>faultCode</name>
			                    <value>
			                        <int>1</int>
			                    </value>
			                </member>
			            </struct>
			        </value>
			    </fault>
			</methodResponse>
			*/
    	     
    	    Document doc = db.parse(is);
    	    NodeList nodes = doc.getElementsByTagName("fault");

    	    boolean hasFault = nodes.getLength() != 0;
    	    // iterate the employees
    	    
    	    if (!hasFault) {
    	        return false;	
    	    }
    	    
    	    for (int i = 0; i < nodes.getLength(); i++) {
    	        Element element = (Element) nodes.item(i);

    	       // NodeList value = element.getElementsByTagName("value");
    	       // Element line = (Element) name.item(0);
    	       // line = (Element) value.item(0);
    	       
    	           
    	        //User.getSingleton().setAttribute(key, value);
    	        
    	    }
    	} catch (Exception e) {
	        e.printStackTrace();
	    }
    	
		return true;    	
    }
}
