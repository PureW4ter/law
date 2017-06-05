package com.jfzy.mweb.base;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

public class TokenUtil {

	public static String generateTokenString(Token token) {
		// TextEncryptor encryptor = Encryptors.text("rlIc4MPlKFpR",
		// KeyGenerators.string().generateKey());
		String rawData = String.format("%s|%s|%s", token.getUserId(), token.getOpenId(), token.getTimestamp());
		// return encryptor.encrypt(rawData);

		return rawData;
	}

	public static Token extractToken(String tokenString) {
		TextEncryptor encryptor = Encryptors.delux("rlIc4MPlKFpR", KeyGenerators.string().generateKey());

		String rawData = encryptor.decrypt(tokenString);

		String[] params = StringUtils.split(rawData, '|');
		if (params != null && params.length == 3 && StringUtils.isNumeric(params[0])
				&& StringUtils.isNumeric(params[2])) {
			Token t = new Token();
			t.setUserId(Integer.valueOf(params[0]));
			t.setOpenId(params[1]);
			t.setTimestamp(Long.valueOf(params[2]));
			return t;
		} else {
			return null;
		}
	}

	public static void main(String[] args) {

		//
		//
		//
		// Token t = new Token();
		// t.setUserId(123);
		// t.setOpenId("E012JF");
		// t.setTimestamp(12345L);
		// System.out.println(generateTokenString(t));
	}
}
