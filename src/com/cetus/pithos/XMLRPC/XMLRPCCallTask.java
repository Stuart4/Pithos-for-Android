package com.cetus.pithos.XMLRPC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.cetus.pithos.Constants.USER_AGENT;

import android.os.AsyncTask;
import android.util.Log;

public class XMLRPCCallTask extends AsyncTask {
    String url;
	String data;
	RPCCallback errorCb = null;
    RPCCallback successCb = null;
	
	public XMLRPCCallTask(String url, String data, RPCCallback successCb, RPCCallback errorCb) {
    	this.url = url;
		this.data = data;
		this.successCb = successCb;
    	this.errorCb   = errorCb;
    }
	
	@Override
	protected Object doInBackground(Object... arg0) {
		String responseString = "";
		
		try {
			URL text = new URL(this.url);
			HttpURLConnection http =
				(HttpURLConnection)text.openConnection();
			
			
			
			http.setRequestProperty("http.agent", USER_AGENT);
			http.setRequestProperty("Content-Type", "text/xml");
			http.setDoOutput(true);
			
			OutputStreamWriter out = new OutputStreamWriter(
                    http.getOutputStream());
			
			// Data to post here
			out.write(this.data);
			out.close();
			
			InputStream inputStream = http.getInputStream();
			
			BufferedReader bufferedReader = new BufferedReader(
	                new InputStreamReader(inputStream));
			
			Log.d("Net", "responsecode = " + http.getResponseCode());
			
			String temp;
			while ((temp = bufferedReader.readLine()) != null) {
		    	responseString += temp;
		    	Log.d("Net", "content line = " + temp);
		    }
		    
			
			// for testing
			//responseString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><methodResponse><params><param><value><struct><member><name>isAwareOfProfile</name><value><boolean>1</boolean></value></member><member><name>autoplayAdInterval</name><value><int>15</int></value></member><member><name>subscriptionHasExpired</name><value><boolean>0</boolean></value></member><member><name>seenQuickMixPanel</name><value><boolean>0</boolean></value></member><member><name>webAuthToken</name><value>w/6m3tsTt0hMOjbg7PIoSptV/eHqjDRIqDXHdOWivlctG0qRp00cp285IDSsu3jXlKlrEJcxTiEs86YoOpYQ6g023C3Oq+Ufs</value></member><member><name>isMonthlyPayer</name><value><boolean>0</boolean></value></member><member><name>disallowProfileNotes</name><value><boolean>0</boolean></value></member><member><name>hasExplicitContentFilterEnabled</name><value><boolean>0</boolean></value></member><member><name>adCookieValue</name><value>1:30:1:44721:an,ip:4:0:0:0:510</value></member><member><name>subscriptionDaysLeft</name><value><int>0</int></value></member><member><name>monthlyListeningCapHours</name><value><int>40</int></value></member><member><name>fbDisconnected</name><value><boolean>1</boolean></value></member><member><name>showUsageMeter</name><value><boolean>1</boolean></value></member><member><name>isProfilePrivate</name><value><boolean>1</boolean></value></member><member><name>adTargetingGroups</name><value><array><data></data></array></value></member><member><name>timeoutCredits</name><value><int>0</int></value></member><member><name>fbEmailHash</name><value>882099861_a1696b4f1d79d7a4ec461e585cb224b6</value></member><member><name>pingAndo</name><value><boolean>1</boolean></value></member><member><name>zipcode</name><value>44721</value></member><member><name>isGiftee</name><value><boolean>0</boolean></value></member><member><name>authToken</name><value>`ycxtIeL0T3ddCVYIs1GQlGdCZ85Tw83y</value></member><member><name>allowExtraHours</name><value><boolean>0</boolean></value></member><member><name>accountMessageKey</name><value></value></member><member><name>twShareUserTokens</name><value></value></member><member><name>listenerId</name><value>146220266</value></member><member><name>birthYear</name><value>1981</value></member><member><name>alertCode</name><value></value></member><member><name>emailOptIn</name><value><boolean>0</boolean></value></member><member><name>fbShareUserTokens</name><value></value></member><member><name>autoRenew</name><value><boolean>1</boolean></value></member><member><name>webName</name><value>davidcollins4481</value></member><member><name>fbConnectAvailable</name><value><boolean>1</boolean></value></member><member><name>username</name><value>davidcollins4481@gmail.com</value></member><member><name>billingFrequency</name><value></value></member><member><name>bookmarkUrl</name><value>http://www.pandora.com/people/davidcollins4481</value></member><member><name>googleAfoaAdsEnabled</name><value><boolean>1</boolean></value></member><member><name>autoplayAdStart</name><value><int>3</int></value></member><member><name>notifyOnNote</name><value><boolean>0</boolean></value></member><member><name>gender</name><value>MALE</value></member><member><name>listenerState</name><value>REGISTERED</value></member></struct></value></param></params></methodResponse>";
		    
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
