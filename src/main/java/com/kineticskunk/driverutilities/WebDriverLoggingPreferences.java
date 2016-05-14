package com.kineticskunk.driverutilities;

import java.util.logging.Level;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

public class WebDriverLoggingPreferences {

	private LoggingPreferences logs;
	
	public WebDriverLoggingPreferences() {
		this.logs = new LoggingPreferences();
	}

	public WebDriverLoggingPreferences (String loggingLevel) {
		this();
		this.logs.enable(LogType.BROWSER, this.getLevel(loggingLevel));
		this.logs.enable(LogType.CLIENT, this.getLevel(loggingLevel));
		this.logs.enable(LogType.DRIVER, this.getLevel(loggingLevel));
		this.logs.enable(LogType.PERFORMANCE, this.getLevel(loggingLevel));
		this.logs.enable(LogType.PROFILER, this.getLevel(loggingLevel));
		this.logs.enable(LogType.SERVER, this.getLevel(loggingLevel));
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
		this.logs.enable(LogType.BROWSER, this.getLevel(loggingLevel));
	}
	
	public void setClientLogLevel(String loggingLevel) {
		this.logs.enable(LogType.CLIENT, this.getLevel(loggingLevel));
	}
	
	public void setDriverLogLevel(String loggingLevel) {
		this.logs.enable(LogType.DRIVER, this.getLevel(loggingLevel));
	}
	
	public void setPerformanceLogLevel(String loggingLevel) {
		this.logs.enable(LogType.PERFORMANCE, this.getLevel(loggingLevel));
	}
	
	public void setProfileLogLevel(String loggingLevel) {
		this.logs.enable(LogType.PROFILER, this.getLevel(loggingLevel));
	}
	
	public void setServerLogLevel(String loggingLevel) {
		this.logs.enable(LogType.SERVER, this.getLevel(loggingLevel));
	}

	public LoggingPreferences getLoggingPreferences() {
		return logs;
	}
}