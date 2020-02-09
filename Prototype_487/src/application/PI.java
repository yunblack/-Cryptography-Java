package application;

/*
 * Hyongsok Sim 
 */

import java.math.BigInteger;

public class PI {
	public String PN; // Participants E-mail Address
	public BigInteger share; // Participants share
	
	public void PartInfo(String k, BigInteger a) {
		PN = k; // Set PN with k
		share = a; // Set share with a
	}
}
