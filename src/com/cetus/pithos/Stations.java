package com.cetus.pithos;

import com.cetus.pithos.XMLRPC.RPCCallback;
import com.cetus.pithos.XMLRPC.XMLRPCResponse;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

// this class is going to be a list adapter

public class Stations extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stations);
	
	    //get stations
        Pandora p = new Pandora(getApplicationContext());
        
        // we're supposed to be signed in and authenticated here
        // get the stations
        
        final ProgressDialog myProgressDialog = ProgressDialog.show(this,
                "Please wait...", "Retrieving Stations...", true);// TODO Externalize
		
		final Context c = this;
		
        final Intent stations = new Intent(this, Stations.class);
        final Toast invalid = Toast.makeText(c, "Cannot retrieve stations", 1000);
        
        RPCCallback successCb = new RPCCallback() {
			public void fire(XMLRPCResponse response) {
				// we are here after we have succeeded or failed at out RPC
				// call
				
				//end progress here?
				myProgressDialog.dismiss();
				
				// parse response
				response.parseStations();
				
				Log.i("pithos", "Firing success callback for stations");
			}			
		};
		
		RPCCallback errorCb = new RPCCallback() {
			public void fire(XMLRPCResponse response) {
				// we are here after we have succeeded or failed at out RPC
				// call
				myProgressDialog.dismiss();
				invalid.show();
				Log.e("pithos", "Firing error callback for stations");
			}			
		};
        
        //p.getStations(successCb, errorCb);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.credentials:
            showSettings();
            return true;
        case R.id.about:
        	showAbout();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    private void showSettings() {
    	startActivity(new Intent(this, Settings.class));
    }
    
    private void showAbout() { 
    	//TODO
    }
}
