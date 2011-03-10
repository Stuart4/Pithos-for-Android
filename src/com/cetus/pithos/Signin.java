package com.cetus.pithos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Signin extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        
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
					Toast.makeText(getApplicationContext(), "thanks!", 1000).show();
					verifyCredentials(usernameValue, passwordValue);
				}
			}
		});        
    }
	
	private void verifyCredentials(String username, String password) {
		boolean validCredentials = true;
		
		if (validCredentials) {
			storeCredentials(username, password);
		} else {
			Toast.makeText(getApplicationContext(), "Invalid credentials", 1000).show();
		}
	}
	
	private void storeCredentials(String username, String password) {
		User u = new User(getApplicationContext());
		u.create(username, password);
	}
}
