package com.jfzy.service.exception;

public class JfApplicationRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -240138269594273041L;
	private int code;

	public JfApplicationRuntimeException(int code, String message) {
		super(message);
		this.code = code;
	}

	public JfApplicationRuntimeException(String message) {
		super(message);
	}

	public JfApplicationRuntimeException(int code, String message, Exception e) {
		super(message, e);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
