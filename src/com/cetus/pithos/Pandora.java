package com.cetus.pithos;

import java.util.ArrayList;

import com.cetus.pithos.XMLRPC.RPCArg;
import com.cetus.pithos.XMLRPC.RPCArgNoType;
import com.cetus.pithos.XMLRPC.RPCArgString;
import com.cetus.pithos.XMLRPC.RPCCallback;
import com.cetus.pithos.XMLRPC.XMLRPC;
import com.cetus.pithos.XMLRPC.XMLRPCCallTask;
import com.cetus.pithos.XMLRPC.XMLRPCResponse;
import com.cetus.pithos.Utils.UnicodeFormatter;
import com.cetus.pithos.Encryption.BlowFish;
import static com.cetus.pithos.Constants.RPC_URL;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Pandora {
	private Context context;
	
	public Pandora(Context ctx) {
    	this.context = ctx;
    }
    
    public void getStations(RPCCallback successCb, RPCCallback errorCb) {
    	User u = User.getSingleton(this.context);
    	
    	ArrayList<RPCArg> args = new ArrayList<RPCArg>();
    	args.add(new RPCArgNoType(u.getAttribute("authToken")));
    	
    	xmlCall("station.getStations", args, successCb, errorCb);
    }
    
    public void authenticateListener(RPCCallback successCb, RPCCallback errorCb) {
    	User u = User.getSingleton(this.context);
    	
    	ArrayList<RPCArg> args = new ArrayList<RPCArg>();
    	args.add(new RPCArgString(u.getUsername()));
    	args.add(new RPCArgString(u.getPassword()));
    	
    	xmlCall("listener.authenticateListener", args, successCb, errorCb);
    }
    
    public void populateSongList(ListView listView, String stationName) {
    	// Stations.java getting a bit crowded with callbacks. Moving ones needed for
    	// this functionality to this class.

    	// stationName coming in as string
        RPCCallback successCb = new RPCCallback() {
			public void fire(XMLRPCResponse response) {
				Log.i("pithos", "Firing success callback for song list");
				
			}			
		};
		
		ArrayList<Song> songList = new ArrayList<Song>();
		songList.add(new Song("Boards Of Canada", "Music Has The Right To Children", "Wildlife Analysis"));
		songList.add(new Song("Boards Of Canada", "Music Has The Right To Children", "Wildlife Analysis"));
		songList.add(new Song("Boards Of Canada", "Music Has The Right To Children", "Wildlife Analysis"));
		songList.add(new Song("Boards Of Canada", "Music Has The Right To Children", "Wildlife Analysis"));
		songList.add(new Song("Boards Of Canada", "Music Has The Right To Children", "Wildlife Analysis"));
		songList.add(new Song("Boards Of Canada", "Music Has The Right To Children", "Wildlife Analysis"));
		SongList adapter = new SongList(this.context,R.layout.song_item, songList);
		
		listView.setAdapter(adapter);		
    }
    
    // Here's our gateway to the RPC interface.
    private void xmlCall(String method, ArrayList<RPCArg> args, final RPCCallback successCb, final RPCCallback errorCb) {
    	String xml = XMLRPC.constructCall(method, args);
    	
    	String data = this.encrypt(xml);
    	
    	String url = RPC_URL;
    	    	
    	url += "rid=" + this.getRid();
    	    	
    	if (this.getListenerId() != null) {
    		url += "lid=" + this.getListenerId();
    	}
    	
    	url += "&method=" + method;
    	
    	// wifi only preference check
    	XMLRPCCallTask task = new XMLRPCCallTask(url, data, successCb, errorCb);
    	
    	// check preferences
    	SharedPreferences sprefs = PreferenceManager.getDefaultSharedPreferences(this.context);
    	String key = this.context.getString(R.string.wifi_only_key);
    	boolean wifiOnly = sprefs.getBoolean(key, false);
    	boolean onWifi = this.isOnWifi();
    	
    	if (wifiOnly && !onWifi) {
    		task.abort(this.context.getString(R.string.wifi_only_error));
    	} else {
    		task.execute();
    	}
    }

    // encrypt RPC details..
    private String encrypt(String xml) {
    	BlowFish b = new BlowFish(PandoraKeys.out_key_p, PandoraKeys.out_key_s);
    	String total = "";
    	 
    	for (int i = 0; i < xml.length(); i+=8) {
    		String segment = "";
    		
    		if (i + 8 >= xml.length()) { 
    			segment = xml.substring(i, xml.length());
    			segment = this.pad(segment, 8);
    		} else {
    			segment = xml.substring(i, i+8);
    		}   		 
    		
    		char[] encrypted = b.encrypt(segment);
    		
    		total += UnicodeFormatter.hexString(encrypted);	
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
        return systemTime + "P";
    }

    private String getListenerId() {
    	User u = User.getSingleton(this.context);
    	return u.getAttribute("listenerId");
    }

    private boolean isOnWifi() {
    	WifiManager wifi = (WifiManager) this.context.getSystemService(Context.WIFI_SERVICE);
    	boolean wifiEnabled = wifi.isWifiEnabled();
    	WifiInfo info = wifi.getConnectionInfo();
    	String ssid = info.getSSID();
    	
    	return wifiEnabled && ssid != null;
    }
}
