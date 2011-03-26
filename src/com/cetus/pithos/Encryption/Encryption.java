package com.cetus.pithos.Encryption;

public interface Encryption {
	// keep this clean in case the encryption algorithm changes
	// later in the future
	
    // not sure what these  are going to return at the moment
	// these will pbly return byte arrays
	char[] encrypt(String toEncrypt);
	char[] decrypt(String toDecrypt);
	
}