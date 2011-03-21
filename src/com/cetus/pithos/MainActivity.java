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
        //setContentView(R.layout.main);
        //setContentView(R.layout.stations);    
        
        User u = new User(getApplicationContext());
        // check if signed in...
        boolean signedIn = false;//u.isSignedIn();
        if (signedIn) {
        	showStations();
        } else {
        	showSignin();
        }
    }

    private void showSignin() {
    	startActivity(new Intent(this, Signin.class));
    }
    
    private void showStations() {
    	startActivity(new Intent(this, Stations.class));
    }
}