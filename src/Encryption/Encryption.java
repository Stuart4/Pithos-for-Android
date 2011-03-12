package Encryption;

public interface Encryption {
	// keep this clean in case the encryption algorithm changes
	// later in the futyre
	
    // not sure what these  are going to return at the moment
	// these will pbly return byte arrays
	String encrypt();
	String decrypt();
	
}
