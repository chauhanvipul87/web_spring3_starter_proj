package com.iana.boesc.utility;

import org.apache.log4j.Logger;

public class WrongConfigurationException extends Exception {

	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(WrongConfigurationException.class);
	
	private String message = null;
	 
    public WrongConfigurationException() {
        super();
    }
 
    public WrongConfigurationException(String message) {
        super(message);
        this.message = message;
        log.error( "Excpetion Raised in WrongConfigurationException: "+message);
    }
 
    public WrongConfigurationException(Throwable cause) {
        super(cause);
        log.error( "Excpetion Raised in WrongConfigurationException: "+cause.getMessage());
    }
 
    @Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }
}
