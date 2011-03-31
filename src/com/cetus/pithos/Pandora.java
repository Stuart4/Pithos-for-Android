package com.cetus.pithos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.cetus.pithos.XMLRPC.RPCArg;
import com.cetus.pithos.XMLRPC.RPCArgInt;
import com.cetus.pithos.XMLRPC.RPCArgNoType;
import com.cetus.pithos.XMLRPC.RPCArgString;
import com.cetus.pithos.XMLRPC.RPCCallback;
import com.cetus.pithos.XMLRPC.XMLRPC;
import com.cetus.pithos.XMLRPC.XMLRPCCallTask;
import com.cetus.pithos.Utils.UnicodeFormatter;
import com.cetus.pithos.Encryption.BlowFish;

import static com.cetus.pithos.Constants.RPC_URL;

import android.content.Context;
import android.util.Log;


public class Pandora {
    //private User u;
	private Context context;
	
	public Pandora(Context ctx) {
    	this.context = ctx;
    }
    
    public void getStations(RPCCallback successCb, RPCCallback errorCb) {
    	User u = User.getSingleton(this.context);
    	
    	ArrayList<RPCArg> args = new ArrayList<RPCArg>();
    	args.add(new RPCArgNoType(u.getAttribute("authToken"))); // not present when signin doesnt fire
    	
    	xmlCall("station.getStations", args, successCb, errorCb);
    }
    
    public void authenticateListener(RPCCallback successCb, RPCCallback errorCb) {
    	User u = User.getSingleton(this.context);
    	
    	ArrayList<RPCArg> args = new ArrayList<RPCArg>();
    	args.add(new RPCArgString(u.getUsername()));
    	args.add(new RPCArgString(u.getPassword()));
    	
    	xmlCall("listener.authenticateListener", args, successCb, errorCb);
    }
    
    // Here's our gateway to the RPC interface.
    // this is gonna have to be threaded
    private void xmlCall(String method, ArrayList<RPCArg> args, final RPCCallback successCb, final RPCCallback errorCb) {
    	// looking pretty good here.
    	String xml = XMLRPC.constructCall(method, args);
    	
    	String data = this.encrypt(xml);
    	
    	String url = RPC_URL;
    	    	
    	url += "rid=" + this.getRid();
    	    	
    	if (this.getListenerId() != null) {
    		url += "lid=" + this.getListenerId();
    	}
    	
    	url += "&method=" + method;
    	
    	new XMLRPCCallTask(url, data, successCb, errorCb).execute();
    }

    // encrypt RPC details..
    private String encrypt(String xml) {
    	BlowFish b = new BlowFish(PandoraKeys.out_key_p, PandoraKeys.out_key_s);
    	String total = "";
    	
    	// i = 296 when padding gets attached. 
    	for (int i = 0; i < xml.length(); i+=8) {
    		String segment = "";
    		
    		if (i + 8 >= xml.length()) { 
    			segment = xml.substring(i, xml.length());
    			segment = this.pad(segment, 8);
    		} else {
    			segment = xml.substring(i, i+8);
    		}   		 
    		
    		// almost there. Still missing a few chars
    		char[] encrypted = b.encrypt(segment);
    		
    		total += UnicodeFormatter.hexString(encrypted);
    		//padding only used on end	
    	}
    	
    	return total;
    }
    
    private String pad(String segment, int l) {
    	
    	int i = 0;
    	int count = l - segment.length();
    	while (i++ < count) {
    		segment += "\0";
    	}

    	return segment;    	
    }

    private String getRid() {
    	int systemTime = new Long(System.currentTimeMillis() / 1000L).intValue();
    	systemTime = systemTime % 10000000;
    	
    	// not sure this is right
    	// coming from "self.rid = "%07iP"%(int(time.time()) % 10000000)"
        return systemTime + "P";
    }

    private String getListenerId() {
    	User u = User.getSingleton(this.context);
    	return u.getAttribute("listenerId");
    }
}
