package com.ey.exceptions;

public class UserNameNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int errorCode;
	
	private String errorMsg;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public UserNameNotFoundException(String errorMsg) {
		super();
		this.errorMsg = errorMsg;
	}
	
	

}
