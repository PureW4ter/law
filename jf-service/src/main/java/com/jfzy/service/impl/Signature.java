package com.jfzy.service.impl;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

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

	// public static boolean checkIsSignValidFromResponseString(String
	// responseString)
	// throws ParserConfigurationException, IOException, SAXException {
	//
	// Map<String, Object> map = XMLParser.getMapFromXML(responseString);
	// Util.log(map.toString());
	//
	// String signFromAPIResponse = map.get("sign").toString();
	// if (signFromAPIResponse == "" || signFromAPIResponse == null) {
	// Util.log("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
	// return false;
	// }
	// Util.log("服务器回包里面的签名是:" + signFromAPIResponse);
	// // 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
	// map.put("sign", "");
	// // 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
	// String signForAPIResponse = Signature.getSign(map);
	//
	// if (!signForAPIResponse.equals(signFromAPIResponse)) {
	// // 签名验不过，表示这个API返回的数据有可能已经被篡改了
	// Util.log("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
	// return false;
	// }
	// Util.log("恭喜，API返回的数据签名验证通过!!!");
	// return true;
	// }

}
