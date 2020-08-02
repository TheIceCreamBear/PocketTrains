package com.joseph.pockettrains.exceptions;

public class InvalidMethodCallerException extends Exception {

	private static final long serialVersionUID = -8754849747143571014L;
	
	public InvalidMethodCallerException() {
		super();
	}
	
	public InvalidMethodCallerException(String message) {
        super(message);
    }
}