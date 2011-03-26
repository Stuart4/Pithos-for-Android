package com.cetus.pithos.XMLRPC;

import java.util.ArrayList;
import java.util.HashMap;

public class XMLRPC {
    //public XMLRPC() {}
    
    //xmlrpc_make_call
    public static String constructCall(String method, ArrayList<RPCArg> args) {
    	String params = new String();
    	
    	Long systemTime = System.currentTimeMillis() / 1000L;
    	RPCArg time = new RPCArgInt(systemTime.intValue());
    	args.add(0, time);
    	
    	for (RPCArg arg : args) {
    		String value = "";
    		Object o = arg.literal();
    		
    		// determine the arg type
    		// TODO Not handling List type yet
    		if (arg.isString()) {
    			value = "<value><string>" + (String) o + "</string></value>";// value of 'o' was escaped for this
    		} else if (arg.isBoolean()) { 
    			value = "<value><boolean>" + (Boolean) o + "</boolean></value>";// value of 'o' was escaped for this
    		} else if (arg.isInteger()) {
    			value = "<value><int>" + (Integer) o + "</int></value>";// value of 'o' was escaped for this
    		} else { // RPCArgNoType
    			value = "<value>" + (String) o + "</value>";
    		}
    		
    		params += "<param>" + value + "</param>";
    	}
    	
    	return "<?xml version=\"1.0\"?><methodCall><methodName>" + method + "</methodName><params>" + params + "</params></methodCall>";
	}
    
}

