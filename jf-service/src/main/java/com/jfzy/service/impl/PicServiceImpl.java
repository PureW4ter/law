package com.jfzy.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.jfzy.service.PicService;
import com.jfzy.service.exception.JfApplicationRuntimeException;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

@Service
public class PicServiceImpl implements PicService {

	@Value("qiniu.accessKey")
	private String accessKey;
	@Value("qiniu.secretKey")
	private String secretKey;
	@Value("qiniu.bucket")
	private String bucket;
	@Value("qiniu.hostPrefix")
	private String hostPrefix;

	private int expires;

	private Auth auth;

	@PostConstruct
	public void init() {

		this.auth = Auth.create(accessKey, secretKey);
	}

	public static void main(String[] args) throws IOException {
		File file = new File("/Users/hualei/git/law/static/bk/img/add.png");
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		byte[] bytes = new byte[(int) file.length()];
		bis.read(bytes);
		bis.close();
		PicServiceImpl service = new PicServiceImpl();
		service.accessKey = "6qd5GGyH2kNyyLLW0SW0GbFR2D3m-VeZ_9nziRKv";
		service.bucket = "jfzy-hd-1";
		service.expires = 3600;
		service.hostPrefix = "oqtjthr3a.bkt.clouddn.com";
		service.secretKey = "bUfuLrmXhFaslKNjk8bcfG0GIyBzYB7G3RslBU8X";
		System.out.println(service.uploadPic(bytes));

	}

	@Override
	public String uploadPic(byte[] picStream) {

		Configuration cfg = new Configuration(Zone.zone0());
		UploadManager uploadManager = new UploadManager(cfg);
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);
		try {
			Response response = uploadManager.put(picStream, null, upToken);
			// 解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			return getUrl(hostPrefix, putRet.key);
		} catch (QiniuException ex) {
			throw new JfApplicationRuntimeException("上传图片失败", ex);
		}
	}

	private static String getUrl(String prefix, String fileName) {
		return String.format("http://%s/%s", prefix, fileName);
	}

	@Override
	public String getToken() {
		return auth.uploadToken(bucket, null, expires, null);
	}

}
