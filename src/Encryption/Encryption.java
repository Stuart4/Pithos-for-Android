package Encryption;

public interface Encryption {
	// keep this clean in case the encryption algorithm changes
	// later in the future
	
    // not sure what these  are going to return at the moment
	// these will pbly return byte arrays
	int[] encrypt(String toEncrypt);
	int[] decrypt(String toDecrypt);
	
}
