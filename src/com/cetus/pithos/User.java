package com.cetus.pithos;

import java.util.HashMap;

import com.cetus.pithos.XMLRPC.RPCCallback;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


public class User {
	private Context context;
	private String username = null;
	private String password = null;
	private Pandora p;
	private static User singleton;
	private HashMap attributes = new HashMap();
	
    public User(Context ctx) {
    	this.context = ctx;
    	this.loadUser();
        p = new Pandora(ctx);
    }
    
    public void create() {
    	// add credentials to preferences
    	this.prefsSetUserPass();
    } 
    
    public void verifyCredentials(RPCCallback successCb, RPCCallback errorCb) {
    	// This is where the credentials entered will be verified 
    	// against the site via RPC
    	p.authenticateListener(successCb, errorCb);
    }
    
    // only checks if user info is set in preferences. Not whether it
    // is correct or not
    public boolean exists() {
    	if (!this.username.equalsIgnoreCase("") && !this.password.equalsIgnoreCase(""))
    		return true;
    	else
    		return false;
    }
    
    public static synchronized User getSingleton(Context c) {
    	if (singleton == null)
    		singleton = new User(c);
    	
    	return singleton;
    }
    
    // this is ugly...having a hard time getting a 'context' var to 
    // feed this every time. it's (the context) only needed the first time (hopefully). FIXME
    public static synchronized User getSingleton() {
    	return singleton;
    }
    
    public void setAttribute(String key, String value) {
    	attributes.put(key, value);
    }
    
    public String getAttribute(String key) {
    	// exception needed?
    	return (String) this.attributes.get(key);
    }
    
    private boolean loadUser() {
    	// retrieve credentials from preferences and
    	// instantiate object
    	this.username = this.prefsGetUser();
    	this.password = this.prefsGetPassword();
        return false;
    }
    
    public void setUsername(String username) { 
        this.username = username;
    }

    public void setPassword(String password) { 
        this.password = password;
    }
    
    public String getUsername() { 
        return this.username;
    }

    public String getPassword() { 
        return this.password;
    }

    private void prefsSetUserPass() {
    	SharedPreferences sprefs = PreferenceManager.getDefaultSharedPreferences(this.context); 
    	Editor e = sprefs.edit();
    	String usernameKey = this.context.getString(R.string.username_key);     
    	String passwordKey = this.context.getString(R.string.password_key);
    	
    	e.putString(usernameKey, this.username);
    	e.putString(passwordKey, this.password);
    	e.commit();
    }
 
    private String prefsGetUser() {
    	String username = "";
        
    	SharedPreferences sprefs = PreferenceManager.getDefaultSharedPreferences(this.context);
        
        String key = this.context.getString(R.string.username_key);
        
        try {
            username = sprefs.getString(key, "");
        } catch (ClassCastException e) {
        	//hmmmmm
        }
    	
    	return username;
    }
    
    private String prefsGetPassword() {
        String password = "";
        
    	SharedPreferences sprefs = PreferenceManager.getDefaultSharedPreferences(this.context);
        
        String key = this.context.getString(R.string.password_key);
        
        try {
            password = sprefs.getString(key, "");
        } catch (ClassCastException e) {
        	//hmmmmm
        }
    	
    	return password;
    }
}
