package com.cetus.pithos;

import com.cetus.pithos.XMLRPC.RPCCallback;
import com.cetus.pithos.XMLRPC.XMLRPCResponse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
					validateUser(usernameValue, passwordValue);
				}
			}
		});        
    }
	
	private void validateUser(String username, String password) {
		u.setUsername(username);
		u.setPassword(password);
		
		// start progress here?
		final ProgressDialog myProgressDialog = ProgressDialog.show(this,
                "Please wait...", "Verifying Credentials...", true);// TODO Externalize
		
		final Context c = this;
		
        final Intent stations = new Intent(this, Stations.class);
        final Toast invalid = Toast.makeText(c, "Invalid Credentials", 1000);
        
        
		RPCCallback successCb = new RPCCallback() {
			public void fire(XMLRPCResponse response) {
				// we are here after we have succeeded or failed at out RPC
				// call
				
				//end progress here?
				myProgressDialog.dismiss();
				// we have succeeded...show stations
				startActivity(stations);
				
				Log.i("pithos", "Firing success callback");			
			}			
		};
		
		RPCCallback errorCb = new RPCCallback() {
			public void fire(XMLRPCResponse response) {
				// we are here after we have succeeded or failed at out RPC
				// call
				myProgressDialog.dismiss();
				invalid.show();
				Log.e("pithos", "Firing error callback");
			}			
		};
		
		u.verifyCredentials(successCb, errorCb);
	}
	
	private void storeCredentials() {
		u.create();
		 
		// we're good to go here..let's go to stations.
		// might be a good idea to create a centralized way to 
		// handle flow
		startActivity(new Intent(this, Stations.class));
	}
}
