package com.kineticskunk.auto.webdriverfactory;

public class FireFoxExceptions extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4531214503108654866L;
	private int errorCode;

	public FireFoxExceptions(String message, int errorCode) {
		 super(message);
		 this.errorCode = errorCode;
	}
}
