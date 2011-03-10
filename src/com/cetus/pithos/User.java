package com.cetus.pithos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import static com.cetus.pithos.Constants.TABLE_NAME;
import static com.cetus.pithos.Constants.USERNAME;
import static com.cetus.pithos.Constants.PASSWORD;

public class User {
	private UserData userData;
	private Context ctx;
	
    public User(Context ctx) {
    	userData = new UserData(ctx);
    	this.ctx = ctx;
    }
    
    // TODO wrap this, sanitize input but maintain interface
    public void create(String username, String password) {
    	SQLiteDatabase db = userData.getWritableDatabase();
    	ContentValues values = new ContentValues();

    	values.put(USERNAME, username);
    	values.put(PASSWORD, password);

    	db.insertOrThrow(TABLE_NAME, null, values);
    }
    
    public boolean hasValidCredentials() {
    	return false;
    }
    
    public boolean exists() {
    	return false;
    }
    
    // TODO accessors
}
