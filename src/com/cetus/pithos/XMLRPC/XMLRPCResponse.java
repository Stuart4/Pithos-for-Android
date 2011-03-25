package com.cetus.pithos.XMLRPC;

public class XMLRPCResponse {
	
	private String xml;
	
    public XMLRPCResponse(String xml) {
    	this.xml = xml;
    }
    
    public String getResponseString() {
    	return this.xml;
    }
}
