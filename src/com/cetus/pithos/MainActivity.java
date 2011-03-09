package com.cetus.pithos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //setContentView(R.layout.stations);    
        
        // check if signed in...
        boolean signedIn = false;
        if (signedIn) {
        	
        } else {
        	showSignin();
        }
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
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    private void showSettings() {
    	startActivity(new Intent(this, Settings.class));
    }

    private void showSignin() {
    	startActivity(new Intent(this, Signin.class));
    }
}