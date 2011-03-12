package com.cetus.pithos.XMLRPC;

import java.util.ArrayList;
import java.util.HashMap;

public class XMLRPC {
    //public XMLRPC() {}
    
    //xmlrpc_make_call
    public static String formCall(String method, ArrayList<RPCArg> args) {
    	String params = new String();
    	
    	for (RPCArg arg : args) {
    		String value = "";
    		Object o = arg.literal();
    		
    		// determine the arg type
    		if (arg.isString()) {
    			value = "<value><string>" + (String) o + "</string></value>";// value of 'o' was escaped for this
    		} else if (arg.isBoolean()) { 
    			value = "<value><boolean>" + (Boolean) o + "</boolean></value>";// value of 'o' was escaped for this
    		} else if (arg.isInteger()) {
    			value = "<value><int>" + (Integer) o + "</int></value>";// value of 'o' was escaped for this
    		}
    		
    		params += "<param>" + value + "</param>";
    	}
    	
    	return "<?xml version=\"1.0\"?><methodCall><methodName>" + method + "</methodName><params>" + params + "</params></methodCall>";
	}
    
}

