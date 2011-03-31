package com.cetus.pithos;

import android.provider.BaseColumns;

public interface Constants extends BaseColumns {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    
    // Pandora constants
    public static final String USER_AGENT = "Pithos/0.2";
    public static final String PROTOCOL_VERSION = "29";
	public static final String RPC_URL = "http://www.pandora.com/radio/xmlrpc/v"+PROTOCOL_VERSION+"?";
	
}
