package com.cetus.pithos.XMLRPC;

public interface RPCArg{
    boolean isString();
    boolean isBoolean();
    boolean isInteger();
    boolean isList();
    Object literal();
}
