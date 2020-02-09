package application;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Collector {
	DBmanager get = new DBmanager(); // Call DBmanager Class 
	ArrayList<PI> part = new ArrayList<PI>(); // Create ArrayList with PI

	public void collector(PI[] pi) {
		for (int i = 0; i < get.getAccessNumber(); i++)
			for (PI p : pi)
				if (!get.getDB(i).equals(p.PN)) {
					part.clear();
					break;
				} else {
					part.add(p);
				}
	}

	public BigInteger reconstruct(BigInteger Tau, ArrayList<BigInteger> shares) throws NoSuchAlgorithmException {
		BigInteger key = Tau;
		// Set key as Tau Value
		BigInteger sum = BigInteger.ZERO;
		// Set sum as 0

		// Foreach BigInteger s from shares
		for (BigInteger s : shares) {
			sum = sum.add(s); // sum = sum + s
			System.out.println("S : " + s); // debug statement
			System.out.println("Sum : " + sum); // debug statement
		}

		MessageDigest md = MessageDigest.getInstance("SHA-256"); // Get Encrypt Algorithm
		md.update(sum.byteValue()); // update sum's bytevalue
		sum = new BigInteger(md.digest()); // sum = encrypted value
		System.out.println("SHA : " + sum); // debug statement
		System.out.println("KEY : " + key.add(sum)); // debug statement

		return key.add(sum); // return key which is Tau + SHA256(sum)
	}
}
