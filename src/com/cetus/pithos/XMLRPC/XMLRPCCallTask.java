package com.cetus.pithos.XMLRPC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class XMLRPCCallTask extends AsyncTask {
    RPCCallback errorCb = null;
    RPCCallback successCb = null;
	
	public XMLRPCCallTask(RPCCallback successCb, RPCCallback errorCb) {
    	this.successCb = successCb;
    	this.errorCb   = errorCb;
    }
	
	@Override
	protected Object doInBackground(Object... arg0) {
		try {
			URL text = new URL("http://google.com");
		
			HttpURLConnection http =
				(HttpURLConnection)text.openConnection();
			
			InputStream inputStream = http.getInputStream();
			
			BufferedReader bufferedReader = new BufferedReader(
	                new InputStreamReader(inputStream));
			
			Log.i("Net", "responsecode = " + http.getResponseCode());
			
			String temp;
		    while ((temp = bufferedReader.readLine()) != null) {
		    	Log.i("Net", "content line = " + temp);
		    }
		    
		    successCb.fire();
		    //errorCb.fire(); FIXME FC when trying to Toast in errorCb
		} catch (MalformedURLException mue) {
			errorCb.fire();
			mue.printStackTrace();
		} catch (IOException e) {
			errorCb.fire();
			// TODO Auto-generated catch block
			// message saying "cannot connect to internet"
			e.printStackTrace();
		}
		return null;
	}

}
