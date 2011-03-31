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
        
        u = User.getSingleton(getApplicationContext());
        Bundle extras = getIntent().getExtras();
        boolean haveCredentials = false;
        
        if (extras != null)
        	haveCredentials = extras.getBoolean("haveCredentials");
        
        if (haveCredentials) {
        	signinUser(u.getUsername(), u.getPassword());
        } else {
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
						signinUser(usernameValue, passwordValue);
					}
				}
			});
        }
    }
	
	private void signinUser(String username, String password) {
		u.setUsername(username);
		u.setPassword(password);
		
		// start progress here?
		final ProgressDialog myProgressDialog = ProgressDialog.show(this,
                "Please wait...", "Verifying Credentials...", true);// TODO Externalize
		
		final Context c = this;
		
       
        final Toast invalidCredentials = Toast.makeText(c, "Invalid Credentials", 1000);
        final Toast error = Toast.makeText(c, "A non-rpc error has occurred. Maybe bad url or io issue. suckin' :-(", 1000);
        
        RPCCallback successCb = new RPCCallback() {
			public void fire(XMLRPCResponse response) {
				Log.i("pithos", "Firing success callback for signin");
				
				//end progress here?
				myProgressDialog.dismiss();
				
				if (response.hasFault()) {
					invalidCredentials.show();
					// send back to signin screen?
				} else {
					// parse response, add to user
					response.parseUser();
					
					User u = User.getSingleton();
					
					u.create();
					
					Intent stations = new Intent(c, Stations.class);
					// we have succeeded...show stations
					startActivity(stations);					
				}
			}			
		};
		
		RPCCallback errorCb = new RPCCallback() {
			public void fire(XMLRPCResponse response) { 
				
				// this is a network error. not a credentials signin error
				myProgressDialog.dismiss();
				error.show();
				Log.e("pithos", "Firing error callback");
			}			
		};
		
		u.verifyCredentials(successCb, errorCb);
	}
}
