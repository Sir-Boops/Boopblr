package me.boops.crypto;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Sum {

	public String hash(String string) throws Exception {

		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] messageDigest = md.digest(string.getBytes());
		BigInteger number = new BigInteger(1, messageDigest);
		String hashtext = number.toString(16);
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}
		return hashtext;
	}
}
