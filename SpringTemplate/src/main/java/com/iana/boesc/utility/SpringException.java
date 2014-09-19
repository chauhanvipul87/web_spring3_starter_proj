package com.iana.boesc.utility;

import org.apache.log4j.Logger;

public class SpringException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String errorMsg;
	private String errorClass; 
	private String layoutType;
	private String errorTrace;
	
	Logger log = Logger.getLogger(SpringException.class);
	
	public SpringException(String errorTrace) {
		log.error("Excpetion Raised : in SpringException(String errorTrace) ::"+errorTrace);
		this.errorTrace = errorTrace;
		this.errorMsg 	= GlobalVariables.ERROR_MSG;
		this.errorClass = GlobalVariables.ERROR_CLASS;
		this.layoutType = GlobalVariables.LAYOUT_TYPE;
	}

	public SpringException(String exceptionMsg,String errorClass,String layoutType){
		this.errorMsg   = exceptionMsg;
		this.errorClass = errorClass;
		this.layoutType = layoutType;
		log.error("Excpetion Raised : in SpringException(String exceptionMsg,String errorClass,String layoutType) ::"+this.errorMsg);
	}
	
	public SpringException(String errorMsg,Throwable cause) {
		System.out.println("Error Trace 2 with 2 parameters ::"+cause.getMessage());
		this.errorTrace = cause.getMessage();
		this.errorMsg 	= errorMsg;
		this.errorClass = GlobalVariables.ERROR_CLASS;
		this.layoutType = GlobalVariables.LAYOUT_TYPE;
		log.error("Excpetion Raised : in SpringException(String errorMsg,Throwable cause) ::"+this.errorMsg);
		log.error("Excpetion Raised Error Trace: in SpringException(String errorMsg,Throwable cause) ::"+this.errorTrace);
	}
	
	public SpringException(String exceptionMsg,String errorClass,String layoutType,Throwable cause) {
		this.errorMsg = exceptionMsg;
		this.errorClass = errorClass;
		this.layoutType = layoutType;
		this.errorTrace = cause.getMessage();
		log.error("Excpetion Raised : in SpringException(String exceptionMsg,String errorClass,String layoutType,Throwable cause) ::"+this.errorMsg);
		log.error("Excpetion Raised Error Trace: in SpringException(String exceptionMsg,String errorClass,String layoutType,Throwable cause) ::"+this.errorTrace);
	}
	
	public String getErrorTrace() {
		return errorTrace;
	}

	public void setErrorTrace(String errorTrace) {
		this.errorTrace = errorTrace;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public String getErrorClass() {
		return errorClass;
	}

	public String getLayoutType() {
		return layoutType;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void setErrorClass(String errorClass) {
		this.errorClass = errorClass;
	}

	public void setLayoutType(String layoutType) {
		this.layoutType = layoutType;
	}

}
