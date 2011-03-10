package com.cetus.pithos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* static var imports */
import static android.provider.BaseColumns._ID;
import static com.cetus.pithos.Constants.TABLE_NAME;
import static com.cetus.pithos.Constants.USERNAME;
import static com.cetus.pithos.Constants.PASSWORD;
/* /static var imports */

public class UserData extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "user.db";
	private static final int DATABASE_VERSION = 1;
	
	public UserData(Context ctx) {
    	super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + _ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + USERNAME
			+ " TEXT," + PASSWORD + " TEXT NOT NULL);");
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}
