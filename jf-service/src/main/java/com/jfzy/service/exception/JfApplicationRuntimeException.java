package com.jfzy.service.exception;

public class JfApplicationRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -240138269594273041L;

	public JfApplicationRuntimeException() {
		super();
	}

	public JfApplicationRuntimeException(String message) {
		super(message);
	}

	public JfApplicationRuntimeException(String message, Exception e) {
		super(message, e);
	}

}
