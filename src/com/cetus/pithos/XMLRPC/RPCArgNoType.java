package com.cetus.pithos.XMLRPC;

public class RPCArgNoType implements RPCArg {
	String value;
	
	public RPCArgNoType(String v) {
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
