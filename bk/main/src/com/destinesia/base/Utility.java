package com.destinesia.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.destinesia.entity.Region;
import com.destinesia.entity.dto.SmsCodeDTO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Utility {
	private static long LEVEL = 9;
	private static long TILE_COUNTS = (1<<LEVEL);
	
	public static Map<String, SmsCodeDTO> smsMap = new HashMap<String, SmsCodeDTO>();
	
	public static String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");// 去掉多余的0
			s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return s;
	}
	
	public static String generageId(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static Map<String, String> generateError(int code, String msg){
		Map<String, String> result = new HashMap<String, String>();
		result.put("code", code+"");
		result.put("status", "BAD_REQUEST");
		result.put("msg", msg);
		return result;
	}
	
	public static Region getAddressBAIDU(double lat, double lon){
		Region region = new Region();
		HttpURLConnection connection = null;
		InputStream is = null;
		BufferedReader reader = null;
		try {
			String baiduUrl = "http://api.map.baidu.com/geocoder/v2/?location=" + lat + "," + lon
					+ "&output=json&ak=2a39b7cca474970f728954980a849a43";
			String baiduResponse = "";
			connection = (HttpURLConnection) (new URL(baiduUrl).openConnection());
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(20000);

			is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is));

			String line = "";
			while ((line = reader.readLine()) != null) {
				baiduResponse += line;
			}

			JSONObject jsonObject = JSONObject.fromObject(baiduResponse);
			int status = jsonObject.getInt("status");
			if (status == 0) {
				JSONObject jsonResult = jsonObject.getJSONObject("result");
				JSONObject jsonLocation = jsonResult.getJSONObject("addressComponent");

				region.setCountry(jsonLocation.getString("country"));
				region.setDistrict(jsonLocation.getString("district"));
				region.setProvince(jsonLocation.getString("province"));
				region.setCity(jsonLocation.getString("city"));
			}
		} catch (Exception e) {
			
		}finally{
			try {
				if(reader!=null){
					reader.close();
				}
				if(is!=null){
					is.close();
				}
				if(connection !=null ){
					connection.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return region;
	}
	
	public static Region getAddressGOOGLEEN(double lat, double lon){
		Region region = new Region();
		HttpURLConnection connection = null;
		InputStream is = null;
		BufferedReader reader = null;
		try {
			String googleUrl = "https://maps.googleapis.com/maps/api/geocode/json?language=en&latlng="+ lat + "," + lon + 
					"&key=AIzaSyDFBqBBcLcfprFbVY1zhO05kxJg7k8_3H0";
			String googleResponse = "";
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("p.xgj.me", 32846));
			connection = (HttpURLConnection) (new URL(googleUrl).openConnection(proxy));
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(20000);
			is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while ((line = reader.readLine()) != null) {
				googleResponse += line;
			}
			JSONObject jsonObject = JSONObject.fromObject(googleResponse);
			JSONArray jsonResult = jsonObject.getJSONArray("results");
			
			JSONArray results = jsonResult.getJSONObject(0).getJSONArray("address_components");
			int length = results.size();
			
			region.setCountry(length>0?results.getJSONObject(length-1).getString("long_name"):"");
			region.setProvince(length>1?results.getJSONObject(length-2).getString("long_name"):"");
			region.setCity(length>2?results.getJSONObject(length-3).getString("long_name"):"");
			region.setDistrict(length>3?results.getJSONObject(length-4).getString("long_name"):"");
			region.setRoad(length>4?results.getJSONObject(length-5).getString("long_name"):"");
			
		} catch (Exception e) {
			return getAddressBAIDU(lat, lon);
		}finally{
			try {
				if(reader!=null){
					reader.close();
				}
				if(is!=null){
					is.close();
				}
				if(connection !=null ){
					connection.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return region;
	}
	
	
	public static Region getAddressGOOGLECN(double lat, double lon){
		Region region = new Region();
		HttpURLConnection connection = null;
		InputStream is = null;
		BufferedReader reader = null;
		try {
			String googleUrl = "https://maps.googleapis.com/maps/api/geocode/json?language=zh_cn&latlng="+ lat + "," + lon + 
					"&key=AIzaSyDFBqBBcLcfprFbVY1zhO05kxJg7k8_3H0";
			String googleResponse = "";
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("p.xgj.me", 32846));
			connection = (HttpURLConnection) (new URL(googleUrl).openConnection(proxy));
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(20000);
			is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = "";
			while ((line = reader.readLine()) != null) {
				googleResponse += line;
			}
			JSONObject jsonObject = JSONObject.fromObject(googleResponse);
			JSONArray jsonResult = jsonObject.getJSONArray("results");
			
			JSONArray results = jsonResult.getJSONObject(0).getJSONArray("address_components");
			int length = results.size();
			
			region.setCountryCN(length>0?results.getJSONObject(length-1).getString("long_name"):"");
			region.setProvinceCN(length>1?results.getJSONObject(length-2).getString("long_name"):"");
			region.setCityCN(length>2?results.getJSONObject(length-3).getString("long_name"):"");
			region.setDistrictCN(length>3?results.getJSONObject(length-4).getString("long_name"):"");
			region.setRoadCN(length>4?results.getJSONObject(length-5).getString("long_name"):"");
			
		} catch (Exception e) {
			return getAddressBAIDU(lat, lon);
		}finally{
			try {
				if(reader!=null){
					reader.close();
				}
				if(is!=null){
					is.close();
				}
				if(connection !=null ){
					connection.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return region;
	}
	public static Map<String, Long> getPosFromLatLon(double lat, double lon) {
		Map<String, Double> poiontRate = getRateFromLatLon(lat, lon);
		double imagePosX =  TILE_COUNTS * poiontRate.get("rateX");  
		double imagePosY =  TILE_COUNTS * poiontRate.get("rateY");
		
		//only need two decimal fraction
		imagePosX = Math.round(imagePosX*100)/100;
		imagePosY = Math.round(imagePosY*100)/100;
		Map<String, Long> result = new HashMap<String, Long>();
		result.put("xIndex", (long)imagePosX);
		result.put("yIndex", (long)imagePosY);
		result.put("level", LEVEL);
		return result;
	}
	
	
	private static Map<String, Double> getRateFromLatLon(double lat, double lon) {
		double rateX = 1.0 * (lon + 180) / 360;
		double rateY = Math.log(Math.tan((lat + 90) * Math.PI / 360));
		rateY = 0.5-rateY / 2 / Math.PI;
		Map<String, Double> result = new HashMap<String, Double>();
		result.put("rateX", rateX);
		result.put("rateY", rateY);
		return result;
	}
	
	public static String getRandomString(int length) {  
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
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
	
	public static boolean checkCode(String phone, String code){
		synchronized(Utility.class){
			SmsCodeDTO smsCodeDto = smsMap.get(phone);
			if(smsCodeDto==null){
				return false;
			}
			if(!smsCodeDto.getCode().equals(code)){
				return false;
			}
			smsMap.remove(phone);
			return true;
		}
	}
}
