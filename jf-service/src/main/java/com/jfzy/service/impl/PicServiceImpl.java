package com.jfzy.service.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.google.gson.Gson;
import com.jfzy.service.PicService;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

public class PicServiceImpl implements PicService {

	@Resource(name = "qiniu.accsekey")
	private String accessKey;
	@Resource(name = "qiniu.secretkey")
	private String secretKey;
	@Resource(name = "qiniu.bucket")
	private String bucket;

	@Resource(name = "qiniu.token.expires")
	private int expires;

	private Auth auth;

	@PostConstruct
	public void init() {

		this.auth = Auth.create(accessKey, secretKey);
	}

	public static void main(String[] args) throws IOException {
		// File file = new File("/Users/hualei/git/law/static/bk/img/add.png");
		// BufferedInputStream bis = new BufferedInputStream(new
		// FileInputStream(file));
		// byte[] bytes = new byte[(int) file.length()];
		// bis.read(bytes);
		// bis.close();
		// new PicServiceImpl().uploadPic(bytes);

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
			System.out.println(putRet.key);
			System.out.println(putRet.hash);
		} catch (QiniuException ex) {
			Response r = ex.response;
			System.err.println(r.toString());
			try {
				System.err.println(r.bodyString());
			} catch (QiniuException ex2) {
				// ignore
			}
		}

		return "";
	}

	@Override
	public String getToken() {
		return auth.uploadToken(bucket, null, expires, null);
	}

}
