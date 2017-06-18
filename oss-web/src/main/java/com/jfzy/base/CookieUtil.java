package com.jfzy.base;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public class CookieUtil {

	private static final String COOKIE_NAME_TOKEN = "tk";

	private static TextEncryptor encryptor = Encryptors.delux("clIr7MPlKFpC", "881239bab013");

	public static String generateTokenString(Token token) {
		String rawData = String.format("%s|%s", token.getUserId(), System.currentTimeMillis());
		return encryptor.encrypt(rawData);
	}

	public static int extractToken(String tokenString) {

		String rawData = encryptor.decrypt(tokenString);

		String[] params = StringUtils.split(rawData, '|');
		if (params != null && params.length == 2 && StringUtils.isNumeric(params[0])
				&& StringUtils.isNumeric(params[1])) {
			return Integer.valueOf(params[0]);
		} else {
			return 0;
		}
	}

	public static void addAuthCookie(Token token, HttpServletResponse resp) {
		String tokenStr = generateTokenString(token);

		Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, tokenStr);
		cookie.setPath("/");
		resp.addCookie(cookie);
	}

	public static Token getAuthFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; ++i) {
				Cookie cookie = cookies[i];
				if (cookie != null && StringUtils.equals(cookie.getName(), COOKIE_NAME_TOKEN)) {

					return null;
				}
			}
		}

		return null;
	}

}
