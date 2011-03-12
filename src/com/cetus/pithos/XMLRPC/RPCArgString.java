package com.cetus.pithos.XMLRPC;

public class RPCArgString implements RPCArg {
	String value;
	
	public RPCArgString(String v) {
		this.value = v;
	}
	
	@Override
	public boolean isString() {
		return true;
	}

	@Override
	public boolean isBoolean() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInteger() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isList() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object literal() {
		// TODO Auto-generated method stub
		return (String) this.value;
	}
	
}