package com.kineticskunk.auto.webdriverfactory;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kineticskunk.auto.logging.TestServiceLogging;

public class FireFoxDesiredCapabilities {

	private static final String SUCCESS_MESSAGE = "Success";
	private static final String FAILURE_MESSAGE = "Fail";

	private Hashtable<String, String> dcConfiguration;
	private DesiredCapabilities dc; 
	private TestServiceLogging tsl;

	public FireFoxDesiredCapabilities(Marker marker, Hashtable<String, String> dcConfiguration) {
		this.dcConfiguration = dcConfiguration;
		this.dc = new DesiredCapabilities();
		this.tsl = new TestServiceLogging(LogManager.getLogger(FireFoxDesiredCapabilities.class.getName()), marker);
	}
	
	/**
	 * 
	 * @return
	 */
	public DesiredCapabilities loadDesiredCapabilities() {
		Set<String> keys = dcConfiguration.keySet();
		for (String key : keys) {
			this.setCapability(key, dcConfiguration.get(key), "");
		}
		return this.dc;
	}

	/**
	 * 
	 * @param capabilityName
	 * @param capabilityValue
	 */
	private void setCapability(String capabilityType, String capabilityValue, String defaultCapabilityValue) {
		tsl.enterLogger("In method setCapability", "capabilityType = '" + capabilityType + "'; capabilityValue = '" + capabilityValue + "'; defaultCapabilityValue = '" + defaultCapabilityValue + "'");
		String ct = this.getCapabilityType(capabilityType);
		if ((!ct.equals(null)) && (!ct.isEmpty())) {
			try {
				this.dc.setCapability(ct, capabilityValue);
				this.tsl.logMessage(Level.INFO, "Successfully set platform to '" + ct + "'");
			} catch (Exception ex0) {
				this.tsl.catchException(ex0);
				try {
					this.tsl.logMessage(Level.WARN, "Failed to set capability type '" + ct + "' to '" + capabilityValue + "'. It will now be set to it's default value of '" + defaultCapabilityValue + "'");
				} catch (Exception ex1) {
					this.tsl.catchException(ex0);
					this.tsl.logMessage(Level.WARN, "Failed to set capability type '" + ct + "' to '" + defaultCapabilityValue + "'");
					this.tsl.exitLogger(FAILURE_MESSAGE);
				}
			}
			this.tsl.exitLogger(SUCCESS_MESSAGE);	
		} else {
			this.tsl.logMessage(Level.WARN, "Capability type '" + capabilityType + "' is not supported" );
			this.tsl.exitLogger(FAILURE_MESSAGE);
		}
		
	}

	/**
	 * 
	 * @param capabilityName
	 * @return
	 */
	private String getCapabilityType(String capabilityType) {
		switch (capabilityType.toLowerCase()) {
		case "ACCEPT_SSL_CERTS":
			return CapabilityType.ACCEPT_SSL_CERTS;
		case "BROWSER_NAME":
			return CapabilityType.BROWSER_NAME;
		case "ELEMENT_SCROLL_BEHAVIOR":
			return CapabilityType.ELEMENT_SCROLL_BEHAVIOR;
		case "ENABLE_PROFILING_CAPABILITY":
			return CapabilityType.ENABLE_PROFILING_CAPABILITY;
		case "HAS_NATIVE_EVENTS":
			return CapabilityType.HAS_NATIVE_EVENTS;
		case "HAS_TOUCHSCREEN":
			return CapabilityType.HAS_TOUCHSCREEN;
		case "LOGGING_PREFS":
			return CapabilityType.LOGGING_PREFS;
		case "OVERLAPPING_CHECK_DISABLED":
			return CapabilityType.OVERLAPPING_CHECK_DISABLED;
		case "PAGE_LOAD_STRATEGY":
			return CapabilityType.PAGE_LOAD_STRATEGY;
		case "PLATFORM":
			return CapabilityType.PLATFORM;
		case "PROXY":
			return CapabilityType.PROXY;
		case "ROTATABLE":
			return CapabilityType.ROTATABLE;
		case "SUPPORTS_ALERTS":
			return CapabilityType.SUPPORTS_ALERTS;
		case "SUPPORTS_APPLICATION_CACHE":
			return CapabilityType.SUPPORTS_APPLICATION_CACHE;
		case "SUPPORTS_FINDING_BY_CSS":
			return CapabilityType.SUPPORTS_FINDING_BY_CSS;
		case "SUPPORTS_JAVASCRIPT":
			return CapabilityType.SUPPORTS_JAVASCRIPT;
		case "SUPPORTS_LOCATION_CONTEXT":
			return CapabilityType.SUPPORTS_LOCATION_CONTEXT;
		case "SUPPORTS_NETWORK_CONNECTION":
			return CapabilityType.SUPPORTS_NETWORK_CONNECTION;
		case "SUPPORTS_SQL_DATABASE":
			return CapabilityType.SUPPORTS_SQL_DATABASE;
		case "SUPPORTS_WEB_STORAGE":
			return CapabilityType.SUPPORTS_WEB_STORAGE;
		case "TAKES_SCREENSHOT":
			return CapabilityType.TAKES_SCREENSHOT;
		case "UNEXPECTED_ALERT_BEHAVIOUR":
			return CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR;
		case "VERSION":
			return CapabilityType.VERSION;
		case "AVOIDING_PROXY":
			return CapabilityType.ForSeleniumServer.AVOIDING_PROXY;
		case "ENSURING_CLEAN_SESSION":
			return CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION;
		case "ONLY_PROXYING_SELENIUM_TRAFFIC":
			return CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC;
		case "PROXY_PAC":
			return CapabilityType.ForSeleniumServer.PROXY_PAC;
		case "PROXYING_EVERYTHING":
			return CapabilityType.ForSeleniumServer.PROXYING_EVERYTHING;
		default:
			this.tsl.exitLogger(FAILURE_MESSAGE);
		}
		return null;
	}

	/**
	 * 
	 * @param javascriptEnabled
	 */
	private void setEnableJavaScript(String javascriptEnabled) {
		tsl.enterLogger("In method setEnableJavaScript", "javascriptEnabled = '" + javascriptEnabled + "'");
		try {
			this.dc.setJavascriptEnabled(Boolean.valueOf(javascriptEnabled));
			this.tsl.exitLogger("Successfully set 'javascriptEnabled' has been set '" + this.dc.getCapability("javascriptEnabled") + "'");
			this.tsl.exitLogger(SUCCESS_MESSAGE);
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger("Failed to set the 'javascriptEnabled' desired capability to '" + javascriptEnabled.toUpperCase() + "'");
			this.tsl.exitLogger(FAILURE_MESSAGE);
		}
		this.tsl.exitLogger(SUCCESS_MESSAGE);
	}



}
