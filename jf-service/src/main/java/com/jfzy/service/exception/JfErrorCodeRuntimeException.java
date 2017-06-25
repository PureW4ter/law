package com.jfzy.service.exception;

public class JfErrorCodeRuntimeException extends JfApplicationRuntimeException {

	private static final long serialVersionUID = 5370069532853343616L;

	private String toastMessage;
	private int code;

	public JfErrorCodeRuntimeException(int code, String toastMessage, String trace) {
		super(trace);
		this.toastMessage = toastMessage;
		this.code = code;
	}

	public JfErrorCodeRuntimeException(int code, String toastMessage, String trace, Exception e) {
		super(trace, e);
		this.toastMessage = toastMessage;
	}

	public int getCode() {
		return this.code;
	}

	public String getToastMessage() {
		return this.toastMessage;
	}

}
