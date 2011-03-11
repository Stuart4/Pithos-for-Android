package com.cetus.pithos;

import android.content.Context;

public class Pandora {
    private User u;
	private Context context;
    
	public Pandora(Context ctx) {
    	this.context = ctx;
    	u = new User(ctx);
    }
    
    public boolean validCredentials() {
    	return true;
    }
    
    public void getStations() {
    	
    }
}
