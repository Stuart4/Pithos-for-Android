package com.cetus.pithos;

import java.util.ArrayList;

import com.cetus.pithos.XMLRPC.RPCCallback;
import com.cetus.pithos.XMLRPC.XMLRPCResponse;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

// this class is going to be a list adapter

public class Stations extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stations);
	
	    //get stations
        final Pandora p = new Pandora(getApplicationContext());

        final ProgressDialog myProgressDialog = ProgressDialog.show(this,
            getString(R.string.please_wait), getString(R.string.retrieving_stations), true);
		
		final Context c = this;
		
        final Intent stations = new Intent(this, Stations.class);
        final Toast invalid = Toast.makeText(c, getString(R.string.retrieve_stations_error), 1000);
        
        RPCCallback successCb = new RPCCallback() {
			public void fire(XMLRPCResponse response) {
				
				myProgressDialog.dismiss();
				
				ArrayList<String> stationNames = response.parseStations();
								
				Spinner s = (Spinner) findViewById(R.id.stationsList);
				final ListView listView = (ListView) findViewById(R.id.songList);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, stationNames);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				s.setAdapter(adapter);
				
				s.setOnItemSelectedListener(new OnItemSelectedListener() {
				    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				        String stationName = parentView.getItemAtPosition(position).toString();
				        p.populateSongList(listView, stationName);
				    }

				    public void onNothingSelected(AdapterView<?> parentView) {
				        
				    }

				});
				
				Log.i("pithos", "Firing success callback for stations");
			}			
		};
		
		successCb.setContext(c);
		
		RPCCallback errorCb = new RPCCallback() {
			public void fire(XMLRPCResponse response) {
				// we are here after we have succeeded or failed at out RPC
				// call
				myProgressDialog.dismiss();
				invalid.show();
				Log.e("pithos", "Firing error callback for stations");
			}			
		};
        
        p.getStations(successCb, errorCb);
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
            showPreferences();
            return true;
        case R.id.about:
        	showAbout();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    //ignore orientation change
    public void onConfigurationChanged(Configuration newConfig) {
    	  super.onConfigurationChanged(newConfig);
    }

    private void showPreferences() {
    	startActivity(new Intent(this, Preferences.class));
    }
    
    private void showAbout() { 
    	startActivity(new Intent(this, About.class));
    }
}
