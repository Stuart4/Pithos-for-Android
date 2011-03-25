package com.cetus.pithos.XMLRPC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
		String responseString = "";
		
		try {
			URL text = new URL("http://google.com");
		
			HttpURLConnection http =
				(HttpURLConnection)text.openConnection();
			
			/*
			http.setDoOutput(true);
			
			OutputStreamWriter out = new OutputStreamWriter(
                    http.getOutputStream());
			
			// Data to post here
			out.write("");
			out.close();
			*/
			InputStream inputStream = http.getInputStream();
			
			BufferedReader bufferedReader = new BufferedReader(
	                new InputStreamReader(inputStream));
			
			Log.i("Net", "responsecode = " + http.getResponseCode());
			
			String temp;
			while ((temp = bufferedReader.readLine()) != null) {
		    	responseString += temp;
		    	Log.i("Net", "content line = " + temp);
		    }
		    
		    successCb.fire(new XMLRPCResponse(responseString));
		} catch (MalformedURLException mue) {
			errorCb.fire(new XMLRPCResponse(responseString));
			mue.printStackTrace();
		} catch (IOException e) {
			errorCb.fire(new XMLRPCResponse(responseString));
			// TODO Auto-generated catch block
			// message saying "cannot connect to internet"
			e.printStackTrace();
		}
		return null;
	}

}
