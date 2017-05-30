package com.kineticskunk.driverutilities;

import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.json.simple.JSONObject;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

public class WebDriverLoggingPreferences {
	
	private final Logger logger = LogManager.getLogger(WebDriverLoggingPreferences.class.getName());
	private final Marker WEBDRIVERLOGGINGPREFERENCES = MarkerManager.getMarker("WEBDRIVERLOGGINGPREFERENCES");
	
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
		this.setEntityLogLevel("BROWSER", loggingPrefs.get(BROWSERLOGLEVEL).toString());
		this.setEntityLogLevel("CLIENT", loggingPrefs.get(CLIENTLOGLEVEL).toString());
		this.setEntityLogLevel("DRIVER", loggingPrefs.get(DRIVERLOGLEVEL).toString());
		this.setEntityLogLevel("PERFORMANCE", loggingPrefs.get(PERFORMANCELOGLEVEL).toString());
		this.setEntityLogLevel("PROFILER", loggingPrefs.get(PROFILELOGLEVEL).toString());
		this.setEntityLogLevel("SERVER", loggingPrefs.get(SERVERLOGLEVEL).toString());
	}
	
	public void setEntityLogLevel(String entity, String loggingLevel) {
		this.logger.info(WEBDRIVERLOGGINGPREFERENCES, "Setting " + entity + " log level to " + (char)34 + loggingLevel + (char)34);
		try {
			this.setLevel(entity, loggingLevel);
		} catch (Exception e){
			this.logger.error(WEBDRIVERLOGGINGPREFERENCES, "Failed to set " + entity + " log level to " + (char)34 + loggingLevel + (char)34);
		}
	}
	
	private void setLevel(String entity, String loggingLevel) {
		switch (entity.toUpperCase()) {
		case "BROWSER":
			this.logPrefs.enable(LogType.BROWSER, this.getLevel(loggingLevel));
			break;
		case "CLIENT":
			this.logPrefs.enable(LogType.CLIENT, this.getLevel(loggingLevel));
			break;
		case "DRIVER":
			this.logPrefs.enable(LogType.DRIVER, this.getLevel(loggingLevel));
			break;
		case "PERFORMANCE":
			this.logPrefs.enable(LogType.PERFORMANCE, this.getLevel(loggingLevel));
			break;
		case "PROFILER":
			this.logPrefs.enable(LogType.PROFILER, this.getLevel(loggingLevel));
			break;
		case "SERVER":
			this.logPrefs.enable(LogType.SERVER, this.getLevel(loggingLevel));
			break;
		default:
			this.logger.debug(WEBDRIVERLOGGINGPREFERENCES, "Entity " + (char)34 + entity + (char)34 + " is not supported.");
		}
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
			this.logger.debug(WEBDRIVERLOGGINGPREFERENCES, "Logging level " + (char)34 + loggingLevel + (char)34 + " is not supported. Setting logging level to " + (char)34 + "All" + (char)34);
			return Level.ALL;
		}
	}

	public LoggingPreferences getLoggingPreferences() {
		return logPrefs;
	}
}