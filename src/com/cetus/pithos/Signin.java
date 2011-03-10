package com.cetus.pithos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Signin extends Activity {
	private User u;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        
        u = new User(getApplicationContext());
        
        Button login = (Button) findViewById(R.id.signinButton);
        
        login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// get the username and password
				TextView un = (EditText) findViewById(R.id.username);
				TextView pw = (EditText) findViewById(R.id.password);
				
				String usernameValue = un.getText().toString();
				String passwordValue = pw.getText().toString();
				
				if (usernameValue.equalsIgnoreCase("") || passwordValue.equalsIgnoreCase("")) {
					Toast.makeText(getApplicationContext(), "missing credentials", 1000).show();	
				} else {
					verifyCredentials(usernameValue, passwordValue);
				}
			}
		});        
    }
	
	private void verifyCredentials(String username, String password) {
		u.setUsername(username);
		u.setPassword(password);
		
		boolean validCredentials = u.hasValidCredentials();
		boolean userExists = u.exists();
		
		if (validCredentials && !userExists) {
			storeCredentials();
			//Toast.makeText(getApplicationContext(), "Valid credentials", 1000).show();
		} else {
			Toast.makeText(getApplicationContext(), "Invalid credentials", 1000).show();
		}
	}
	
	private void storeCredentials() {
		u.create();
		 
		// we're good to go here..let's go to stations.
		// might be a good idea to create a centralized way to 
		// handle flow
		startActivity(new Intent(this, Stations.class));
	}
}
