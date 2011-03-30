package com.cetus.pithos;

import java.util.HashMap;

import com.cetus.pithos.XMLRPC.RPCCallback;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import static android.provider.BaseColumns._ID;
import static com.cetus.pithos.Constants.TABLE_NAME;
import static com.cetus.pithos.Constants.USERNAME;
import static com.cetus.pithos.Constants.PASSWORD;

public class User {
	private UserData userData;
	private Context context;
	private String username;
	private String password;
	private Pandora p;
	private static User singleton;
	HashMap attributes = new HashMap();
	
    public User(Context ctx) {
    	userData = new UserData(ctx);
    	this.context = ctx;
    	this.loadUser();// loads if user exists
        p = new Pandora(ctx);
    }
    
    // TODO wrap this, sanitize input but maintain interface
    public void create() {
    	SQLiteDatabase db = userData.getWritableDatabase();
    	ContentValues values = new ContentValues();

    	values.put(USERNAME, this.username);
    	values.put(PASSWORD, this.password);

    	db.insertOrThrow(TABLE_NAME, null, values);
    } 
    
    // TODO change the name of this method...there is a method in Signin by the same name
    // confusing
    public void verifyCredentials(RPCCallback successCb, RPCCallback errorCb) {
    	// here check if credentials exist in DB. If not,
    	// run RPC call
    	if (this.exists()) {
    		return;//throw an exception?
    	}
    	
    	// This is where the credentials entered will be verified 
    	// against the site via RPC
    	p.authenticateListener(successCb, errorCb);
    }
    
    // user exists in the database. 
    // checked against instance values
    public boolean exists() {
    	SQLiteDatabase db = userData.getReadableDatabase();
        String select = "Select _id, title, title_raw from search Where(title_raw like " + this.username + ")";
                
        Cursor cursor = db.query(TABLE_NAME, new String[] {_ID, USERNAME, PASSWORD}, 
        		USERNAME + " like '%" + this.username + "%'", null, null, null, null);
        
        db.close();
        
        int count = cursor.getCount();        
    	
        cursor.close();
        
    	return count >= 1;
    }
     
    // check if a user exists in the database here.
    // whose job is it gonna be to enforce the integrity
    // user data (ensure multiple values are not able to get into db, etc.)
    public boolean isSignedIn() {
    	SQLiteDatabase db = userData.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_NAME, new String[] {_ID, USERNAME, PASSWORD}, 
        		null, null, null, null, null);
        
        int count = cursor.getCount();    
    	
    	return count >= 1;
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
    
    // TODO: Definitley gonna need a refactor in this class
    private boolean loadUser() {
    	SQLiteDatabase db = userData.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_NAME, new String[] {_ID, USERNAME, PASSWORD}, 
        		null, null, null, null, null);
        
        db.close();
        // is this necessary, does query return null if there are no results?
        boolean result = cursor.getCount() >= 1;
        
        if (result) {
        	if (cursor.moveToFirst()) {
        		int userNameColumn = cursor.getColumnIndex(USERNAME);
                int passwordColumn = cursor.getColumnIndex(PASSWORD);
                this.username = cursor.getString(userNameColumn);
                this.password = cursor.getString(passwordColumn);
                return true;
        	}
        }
        
        cursor.close();
        
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
    
}
