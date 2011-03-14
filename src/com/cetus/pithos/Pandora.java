package com.cetus.pithos;

import java.util.ArrayList;

import com.cetus.pithos.XMLRPC.RPCArg;
import com.cetus.pithos.XMLRPC.RPCArgString;
import com.cetus.pithos.XMLRPC.XMLRPC;

import Encryption.BlowFish;
import android.content.Context;


public class Pandora {
    private User u;
	private Context context;
	private final String PROTOCOL_VERSION = "29";
	private final String RPC_URL = "http://www.pandora.com/radio/xmlrpc/v"+PROTOCOL_VERSION+"?";
	private final String USER_AGENT = "Pithos/0.2";
	
	public Pandora(Context ctx, User u) {
    	this.context = ctx;
    	this.u = u;
    }
    
    public boolean validCredentials() {
    	authenticateListener();
    	return true;
    }
    
    public void getStations() {
    	
    }
    
    public void authenticateListener() {
    	ArrayList<RPCArg> args = new ArrayList<RPCArg>();
    	args.add(new RPCArgString(u.getUsername()));
    	args.add(new RPCArgString(u.getPassword()));
    	
    	xmlCall("listener.authenticateListener", args);
    }
    
    // Here's our gateway to the RPC interface.
    // this is gonna have to be threaded
    private void xmlCall(String method, ArrayList<RPCArg> args) {
    	// looking pretty good here.
    	String xml = XMLRPC.constructCall(method, args);
    	
    	String data = this.encrypt(xml);
    }

    // encrypt RPC details..
    private String encrypt(String xml) {
    	BlowFish b = new BlowFish();
    	String total = "";
    	
    	for (int i = 0; i < xml.length(); i+=8) {
    		if (i + 8 >= xml.length())
    			break;
    		
    		
    		//padding only used on end	
    	}
    	
    	return "";
    }
    
    private String pad(String segment, int l) {
    	
    	int i = 0;
    	while (++i < l - segment.length()) {
    		segment += "\0";
    	}

    	return segment;    	
    }
}
