package com.cetus.pithos.XMLRPC;

import android.content.Context;

public abstract class RPCCallback {
	Context context;
	
	public abstract void fire(XMLRPCResponse response);

	public void setContext(Context c) {
		this.context = c;
	}
	
	public Context getContext() {
		return this.context;
	}
}
