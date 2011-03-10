package com.cetus.pithos;

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
	
    public User(Context ctx) {
    	userData = new UserData(ctx);
    	this.context = ctx;
    }
    
    // TODO wrap this, sanitize input but maintain interface
    public void create() {
    	SQLiteDatabase db = userData.getWritableDatabase();
    	ContentValues values = new ContentValues();

    	values.put(USERNAME, this.username);
    	values.put(PASSWORD, this.password);

    	db.insertOrThrow(TABLE_NAME, null, values);
    }
    
    public void setUsername(String username) { 
        this.username = username;
    }

    public void setPassword(String password) { 
        this.password = password;
    }
    
    public boolean hasValidCredentials() {
    	// This is where the credentials entered will be verified 
    	// against the site via RPC
    	return true;
    }
    
    // user exists in the database. 
    // checked against instance values
    public boolean exists() {
    	SQLiteDatabase db = userData.getReadableDatabase();
        String select = "Select _id, title, title_raw from search Where(title_raw like " + this.username + ")";
        
        Cursor cursor = db.query(TABLE_NAME, new String[] {_ID, USERNAME, PASSWORD}, 
        		USERNAME + " like '%" + this.username + "%'", null, null, null, null);
        
        int count = cursor.getCount();        
    	
    	return count >= 1;
    }
     
    // check if a user exists in the database here.
    // whose job is it gonna be to enforce the integrity
    // user data (ensure multiple values are not able to get into db, etc.)
    public boolean isSignedIn() {
    	SQLiteDatabase db = userData.getReadableDatabase();
        String select = "Select _id, title, title_raw from search Where(title_raw like " + this.username + ")";
        
        Cursor cursor = db.query(TABLE_NAME, new String[] {_ID, USERNAME, PASSWORD}, 
        		null, null, null, null, null);
        
        int count = cursor.getCount();    
    	
    	return count >= 1;
    }
    
    // TODO accessors
}
