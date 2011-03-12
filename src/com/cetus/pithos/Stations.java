package com.cetus.pithos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

// this class is going to be a list adapter

public class Stations extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stations);
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
