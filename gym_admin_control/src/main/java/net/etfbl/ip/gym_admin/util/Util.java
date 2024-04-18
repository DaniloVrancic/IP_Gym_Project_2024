package net.etfbl.ip.gym_admin.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Util {

	/**
	 *
	 * @param plainText The string to get hashed
	 * @param algorithm Needs to be one of the possible algorithm Strings in MessageDigest class
	 * @return Hashed text
	 */
	public static String hashString(String plainText, String algorithm)
	{
		try {
	        MessageDigest digest = MessageDigest.getInstance(algorithm);
	        byte[] hash = digest.digest(plainText.getBytes());
	        StringBuilder hexString = new StringBuilder();
	        for (byte b : hash) {
	            String hex = Integer.toHexString(0xff & b);
	            if (hex.length() == 1) {
					hexString.append('0');
				}
	            hexString.append(hex);
	        }
	        return hexString.toString();
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	        // Handle exception, maybe return null or throw a custom exception
	        return null;
	    }
	}

}
