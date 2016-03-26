package com.kineticskunk.auto.webdriverfactory;

public class Utilties {

	/**
	 * 
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c)) return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public boolean isBoolean(String value) {
		if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
			return true;
		}
		return false;
	}
}