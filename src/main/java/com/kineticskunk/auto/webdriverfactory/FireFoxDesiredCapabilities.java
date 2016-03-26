package com.kineticskunk.auto.webdriverfactory;

import java.util.Hashtable;
import java.util.Set;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.kineticskunk.auto.logging.TestServiceLogging;

public class FireFoxDesiredCapabilities {

	private DesiredCapabilities dc;
	private Hashtable<String, String> dcCondfig;
	private TestServiceLogging tsl;

	public FireFoxDesiredCapabilities() {
		this.dc = new DesiredCapabilities();
		this.tsl = null;
	}

	public FireFoxDesiredCapabilities(Hashtable<String, String> dcCondfig) {
		this();
		this.dcCondfig = dcCondfig;
		this.tsl = null;
	}

	/**
	 * Construct FireFoxDesiredCapabilities with logging enabled
	 * @param marker
	 * @param dcCondfig
	 */
	public FireFoxDesiredCapabilities(Marker marker, Hashtable<String, String> dcCondfig) {
		this(dcCondfig);
		this.tsl = new TestServiceLogging(LogManager.getLogger(FireFoxDesiredCapabilities.class.getName()), marker, true);
	}

	/**
	 * 
	 * @return
	 */
	public DesiredCapabilities getDesiredCapabilities() {
		Set<String> keys = this.dcCondfig.keySet();
		for (String key : keys) {
			this.setCapability(key, dcCondfig.get(key), "");
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
				this.tsl.logMessage(Level.INFO, "Successfully set capability type to '" + capabilityType + "' to '" + capabilityValue + "'");
			} catch (Exception ex0) {
				this.tsl.catchException(ex0);

			}
			this.tsl.logMessage(Level.WARN, "Capability type '" + capabilityType + "' is not supported" );
			this.tsl.exitLogger(false);
		} else {

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
			this.tsl.logMessage(Level.ERROR, "Capability type '" + capabilityType.toUpperCase() + "' is NOT SUPPORTED");
			this.tsl.exitLogger(false);
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
			this.tsl.logMessage(Level.INFO, "Successfully set 'javascriptEnabled' has been set '" + this.dc.getCapability("javascriptEnabled") + "'");
			this.tsl.exitLogger(true);
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.logMessage(Level.ERROR, "Failed to set the 'javascriptEnabled' desired capability to '" + javascriptEnabled.toUpperCase() + "'");
			this.tsl.exitLogger(false);
		}
	}



}
