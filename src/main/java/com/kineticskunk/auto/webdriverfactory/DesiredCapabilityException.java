package com.kineticskunk.auto.webdriverfactory;

public class DesiredCapabilityException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4991316726056463211L;
	private String message = null;

	public DesiredCapabilityException() {
		super();
	}

	DesiredCapabilityException(String message) {
		super(message);
		this.message = message;
	}

	public DesiredCapabilityException(Throwable cause) {
		super(cause);
	}

	public DesiredCapabilityException(String message, Throwable cause) {
		super(message, cause);
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