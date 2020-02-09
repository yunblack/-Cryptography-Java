package application;

/*
 * Hyongsok Sim
 * Juyong Yun
 * Jongsun Park
 */

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class GenerateRandom {
	
	public static BigInteger TauValue(BigInteger key, PI[] pi) throws NoSuchAlgorithmException {
		BigInteger sum = BigInteger.ZERO; // Sum variable to calculate sum of shares
		BigInteger val = BigInteger.ZERO; // Val variable to store sum
		BigInteger sha; // sha variable to store SHA-256 result
		
		System.out.println("KEY : " + key);
		for(int i = 0; i < pi.length; i++)	{
			sum = sum.add(pi[i].share); // Add all share who are in the same AccessStructure
			System.out.println("SHARES : " + pi[i].share);
			System.out.println("Sum : " + val);
		}
		
		MessageDigest md = MessageDigest.getInstance("SHA-256"); // Get Encrypt Algorithm
		md.update(sum.byteValue()); // Update byteValue (which is the result)
		
		sha = new BigInteger(md.digest()); // Set sha's value as the result of SHA-256 encryption
		System.out.println("Val : " + sha); //Debug Statement
		System.out.println("TAU : " + key.subtract(sha));
		return key.subtract(sha); // Subtract sha from the key to calculate Tau value
	}
	
	public static PI GenerateRandomShares(int size, String email) {
		Random rdg = new Random(); // Create Random Object
		PI pi = new PI(); // Create PI Object
		pi.PartInfo(email, BigInteger.valueOf(rdg.nextLong())); //Set pi's information with email and
		//share which is random BigInteger now
		return pi; // return Participant info
	}
}
