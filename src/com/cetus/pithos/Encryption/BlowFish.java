package com.cetus.pithos.Encryption;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;


public class BlowFish implements Encryption {
	private long[] p_boxes;
    private long[][] s_boxes;
    private long modulus;
    
    public BlowFish(long[] out_key_p, long[][] out_key_s) {
        this.p_boxes = out_key_p;
        this.s_boxes = out_key_s;
        this.modulus = (long) Math.pow(2, 32); // correct
    }
    
    public char[] decrypt(String data) {
    	char[] dummies = {};
    	// TODO
    	return dummies;
    }
    
     public char[] encrypt(String data) {
        char[] dummies = {};
        
        if (data.length() != 8) {
            return dummies; // throw runtime error
        }
       
        char[] charBytes = data.toCharArray();
        
        long xl = data.codePointAt(3) | (data.codePointAt(2) << 8) | (data.codePointAt(1) << 16) | (data.codePointAt(0) << 24);
        long xr = data.codePointAt(7) | (data.codePointAt(6) << 8) | (data.codePointAt(5) << 16) | (data.codePointAt(4) << 24);
        
        long[] result = this.cipher(xl, xr, false);
        
        long cl = result[0];
        long cr = result[1];
       
        
        char[] encoded = {
        	(char) ((cl >> 24) & 0xFF), (char) ((cl >> 16) & 0xFF), (char) ((cl >> 8) & 0xFF), (char) (cl & 0xFF),
        	(char) ((cr >> 24) & 0xFF), (char) ((cr >> 16) & 0xFF), (char) ((cr >> 8) & 0xFF), (char) (cr & 0xFF)        
        };
        return encoded;
    }
    
    // for direction, encrypt = false
    
    public long[] cipher(long xl, long xr, boolean direction) {
        // data structure 'p_boxes' is not being read correctly. value for index '1' is negative and incorrect
        if (!direction) {
            // values for xr,xl look good in this loop
        	for (int i = 0; i < 16; i++) {
            	// pboxes ... values retrieved here are not always the same as python..[1] for example
                xl = xl ^ this.p_boxes[i]; //correct
                xr = this.round (xl) ^ xr;
                long temp = xr;
                xr = xl;
                xl = temp;                
            }
            
        	// these need swapped
        	long temp = xr;
            xr = xl;
            xl = temp;
        	xr = xr ^ this.p_boxes[16];
            xl = xl ^ this.p_boxes[17];
        } else {
            // TODO decrypt
        }
       
        long[] result = { xl, xr };
        
        return result;
    }
    
    // xl is not right here when i = 1
    private long round(long xl) {
        long a = (xl & 0xFF000000L) >> 24;//correct...when i = 1, python = 243L, java = -13
        long b = (xl & 0x00FF0000L) >> 16;//correct
        long c = (xl & 0x0000FF00L) >> 8;//correct
        long d = xl & 0x000000FFL;//correct

        // Perform all ops as longs then and out the last 32-bits to
        // obtain the integer
        long f =  (long) ( this.s_boxes[0][(int)a] + (this.s_boxes[1][(int)b]) ) % this.modulus;
        f = f ^ (long) this.s_boxes[2][(int)c]; // 1812614122L in python
        f = f + (long) this.s_boxes[3][(int)d]; // 5098168528L in python
        f = (f % this.modulus) & 0xFFFFFFFFL; // 803201232L in python

        return f;
    }


}
