package com.jfzy.service;

public interface ShortUrlService {

	String getRealUrl(String code);

	String getShortUrl(String realUrl);

}
