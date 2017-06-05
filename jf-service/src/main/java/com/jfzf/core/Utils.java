package com.jfzf.core;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import com.jfzy.service.exception.JfApplicationRuntimeException;

public class Utils {
	public static String getMd5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes());
			return new String(new BigInteger(1, md.digest()).toString());
		} catch (NoSuchAlgorithmException e) {
			throw new JfApplicationRuntimeException(404, "Failed in getMd5");
		}
	}
	public static String getSmsCode(int length) {
	    String base = "0123456789";     
	    Random random = new Random();
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());
	        sb.append(base.charAt(number));     
	    }
	    return sb.toString();
	 }
}
