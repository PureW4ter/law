package com.destinesia.base;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties({ "localizedMessage", "cause", "stackTrace", "suppressed" })
public class StatusMsg extends RuntimeException {
	private static final long serialVersionUID = -5065283806099727271L;
	
	//其它错误
	public static final String CODE_OTHERS = "0";
	//超时
	public static final String CODE_REQUEST_TIMEOUT = "1";
	//禁止使用
	public static final String CODE_FORBIDDEN = "2";
	//没有权限
	public static final String CODE_UNAUTHORIZED = "3";
	//没有找到记录
	public static final String CODE_NOT_FOUND = "4";
	//已经存在
	public static final String CODE_ALREADY_EXISTS = "5";
	//字段无效
	public static final String CODE_FIELD_INVALID = "6";
	//请求失败
	public static final String CODE_BAD_REQUEST = "7";
	//数据库出错
	public static final String CODE_DATABASE = "8";
	//执行出错
	public static final String CODE_RUNTIME = "9";
	//服务器内部错误
	public static final String CODE_INTERNAL_SERVER_ERROR = "10";
	//验证码错误
	public static final String CODE_BAD_PASSCODE = "11";
	//邀请码已经使用
	public static final String CODE_USED_RECOMMEND = "12";
	//邀请码不存在
	public static final String CODE_NOT_EXSIT_RECOMMEND = "13";
	//整体数据操作出错，需要回滚数据库
	public static final String CODE_DATABASE_ROLLBACK = "14";

	private String code;
	private String status;
	private String message;
	

	public StatusMsg(String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public StatusMsg(String code, String message, String status) {
		super(message);
		this.code = code;
		this.status = status;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
