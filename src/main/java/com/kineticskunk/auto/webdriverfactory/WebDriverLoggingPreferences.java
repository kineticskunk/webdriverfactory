package com.kineticskunk.auto.webdriverfactory;

import java.util.logging.Level;

import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

public class WebDriverLoggingPreferences {

	private LoggingPreferences logs = new LoggingPreferences();

	private WebDriverLoggingPreferences (String loggingLevel) {
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

	public LoggingPreferences getLoggingPreferences() {
		return logs;
	}
}

