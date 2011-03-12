package com.cetus.pithos.XMLRPC;

public class RPCArgBoolean implements RPCArg {
	boolean value;
	
	public RPCArgBoolean(boolean v) {
		this.value = v;
	}
	
	@Override
	public boolean isString() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isBoolean() {
		// TODO Auto-generated method stub
		return true;
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
		return (Object) this.value;
	}
	
}
