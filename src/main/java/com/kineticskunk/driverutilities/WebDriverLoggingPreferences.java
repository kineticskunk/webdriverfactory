package com.kineticskunk.driverutilities;

import java.util.logging.Level;

import org.json.simple.JSONObject;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

public class WebDriverLoggingPreferences {
	
	private static final String BROWSERLOGLEVEL = "browserloglevel";
	private static final String CLIENTLOGLEVEL = "clientloglevel";
	private static final String DRIVERLOGLEVEL = "driverloglevel";
	private static final String PERFORMANCELOGLEVEL = "performanceloglevel";
	private static final String PROFILELOGLEVEL = "profileloglevel";
	private static final String SERVERLOGLEVEL = "serverloglevel";

	private LoggingPreferences logPrefs;
	
	public WebDriverLoggingPreferences() {
		this.logPrefs = new LoggingPreferences();
	}

	public WebDriverLoggingPreferences (JSONObject loggingPrefs) {
		this();
		this.logPrefs.enable(LogType.BROWSER, this.getLevel(loggingPrefs.get(BROWSERLOGLEVEL).toString()));
		this.logPrefs.enable(LogType.CLIENT, this.getLevel(loggingPrefs.get(CLIENTLOGLEVEL).toString()));
		this.logPrefs.enable(LogType.DRIVER, this.getLevel(loggingPrefs.get(DRIVERLOGLEVEL).toString()));
		this.logPrefs.enable(LogType.PERFORMANCE, this.getLevel(loggingPrefs.get(PERFORMANCELOGLEVEL).toString()));
		this.logPrefs.enable(LogType.PROFILER, this.getLevel(loggingPrefs.get(PROFILELOGLEVEL).toString()));
		this.logPrefs.enable(LogType.SERVER, this.getLevel(loggingPrefs.get(SERVERLOGLEVEL).toString()));
	}

	private Level getLevel(String loggingLevel) {
		switch (loggingLevel.toUpperCase()) {
		case "ALL":
			return Level.ALL;
		case"CONFIG":
			return Level.CONFIG;
		case "FINE":
			return Level.FINE;
		case "FINER":
			return Level.FINER;
		case "FINEST":
			return Level.FINEST;
		case "INFO":
			return Level.INFO;
		case "OFF":
			return Level.OFF;
		case "SEVERE":
			return Level.SEVERE;
		case "WARNING":
			return Level.WARNING;
		default:
			return Level.ALL;
		}
	}
	
	public void setBrowserLogLevel(String loggingLevel) {
		this.logPrefs.enable(LogType.BROWSER, this.getLevel(loggingLevel));
	}
	
	public void setClientLogLevel(String loggingLevel) {
		this.logPrefs.enable(LogType.CLIENT, this.getLevel(loggingLevel));
	}
	
	public void setDriverLogLevel(String loggingLevel) {
		this.logPrefs.enable(LogType.DRIVER, this.getLevel(loggingLevel));
	}
	
	public void setPerformanceLogLevel(String loggingLevel) {
		this.logPrefs.enable(LogType.PERFORMANCE, this.getLevel(loggingLevel));
	}
	
	public void setProfileLogLevel(String loggingLevel) {
		this.logPrefs.enable(LogType.PROFILER, this.getLevel(loggingLevel));
	}
	
	public void setServerLogLevel(String loggingLevel) {
		this.logPrefs.enable(LogType.SERVER, this.getLevel(loggingLevel));
	}

	public LoggingPreferences getLoggingPreferences() {
		return logPrefs;
	}
}