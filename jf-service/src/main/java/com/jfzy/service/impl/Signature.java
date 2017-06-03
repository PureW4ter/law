package com.jfzy.service.impl;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import java.util.TreeMap;

/**
 * User: rizenguo Date: 2014/10/29 Time: 15:23
 */
public class Signature {

	public static String getSign(Map<String, String> map, String apiSecret) {
		Map<String, String> signMap = new TreeMap<String, String>();
		signMap.putAll(map);
		StringBuilder str = new StringBuilder();
		for (Entry<String, String> entry : signMap.entrySet()) {
			String key = entry.getKey();
			if ("sign".equals(key)) {
				continue;
			}
			String value = entry.getValue();
			if (StringUtils.isNotEmpty(value)) {
				str.append(key);
				str.append("=");
				str.append(value.toString());
				str.append("&");
			}
		}
		str.append("key=" + apiSecret);
		String md5Encode = MD5.MD5Encode(str.toString()).toUpperCase();
		return md5Encode;
	}

	public static boolean checkSign(Map<String, String> map, String apiSecret, String sign) {

		String tmpSign = getSign(map, apiSecret);
		return StringUtils.equals(tmpSign, sign);
	}

}
