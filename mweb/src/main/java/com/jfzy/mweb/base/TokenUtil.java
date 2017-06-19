package com.jfzy.mweb.base;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public class TokenUtil {

	private static TextEncryptor encryptor = Encryptors.delux("rlIc4MPlKFpR", "991239bab013");

	public static String generateTokenString(Token token) {
		String rawData = String.format("%s|%s|%s", token.getUserId(), "-", token.getTimestamp());
		return encryptor.encrypt(rawData);
	}

	public static Token extractToken(String tokenString) {

		String rawData = encryptor.decrypt(tokenString);

		String[] params = StringUtils.split(rawData, '|');
		if (params != null && params.length == 3 && StringUtils.isNumeric(params[0])
				&& StringUtils.isNumeric(params[2])) {
			Token t = new Token();
			t.setUserId(Integer.valueOf(params[0]));
			t.setTimestamp(Long.valueOf(params[2]));
			return t;
		} else {
			return null;
		}
	}

	public static void main(String[] args) {

		Token t = new Token();
		t.setUserId(123);
		t.setTimestamp(12345L);
		String token = generateTokenString(t);
		System.out.println(token.length());

	}
}