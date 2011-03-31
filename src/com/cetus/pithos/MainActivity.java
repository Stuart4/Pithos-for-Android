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
        
        User u = User.getSingleton(getApplicationContext());
        // check if signed in...
        boolean haveCredentials = u.exists();
        
        Intent signin = new Intent(this, Signin.class);
        
        if (haveCredentials)
        	signin.putExtra("haveCredentials", haveCredentials);
        
        startActivity(signin);
    }
}