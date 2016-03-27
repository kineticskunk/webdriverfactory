package com.kineticskunk.auto.webdriverfactory;

import java.util.Hashtable;
import java.util.Set;
import org.apache.commons.lang3.EnumUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kineticskunk.auto.logging.TestServiceLogging;

public class FireFoxDesiredCapabilities {

	private DesiredCapabilities dc;
	private Hashtable<String, String> dcCondfig;
	private PlatformOperatingSystem pos;
	private TestServiceLogging tsl;

	public FireFoxDesiredCapabilities() {
		this.dc = new DesiredCapabilities();
		this.pos = new PlatformOperatingSystem();
		this.tsl = null;
	}

	public FireFoxDesiredCapabilities(Hashtable<String, String> dcCondfig) {
		this();
		this.dcCondfig = dcCondfig;
	}

	/**
	 * Construct FireFoxDesiredCapabilities with logging enabled
	 * @param marker
	 * @param dcCondfig
	 */
	public FireFoxDesiredCapabilities(Hashtable<String, String> dcCondfig, boolean enableLogging) {
		this(dcCondfig);
		this.tsl = new TestServiceLogging(LogManager.getLogger(FireFoxDesiredCapabilities.class.getName()), enableLogging);
	}

	/**
	 * 
	 * @return
	 */
	public DesiredCapabilities getDesiredCapabilities() {
		Set<String> keys = this.dcCondfig.keySet();
		for (String key : keys) {
			if (EnumUtils.isValidEnum(desiredCapabilitiesEnum.class, key))  {
				if (key.equalsIgnoreCase("LOGGING_PREFS")) {
					WebDriverLogginPreferences logs = new WebDriverLogginPreferences(dcCondfig.get(key));
					
				}
				this.setCapability(key, dcCondfig.get(key), "");
			} else if (EnumUtils.isValidEnum(forSeleniumServerDesiredCapabiltiesEnum.class, key)) {
				this.setSeleniumServerCapability(key, dcCondfig.get(key), "");
			} else {
				this.tsl.logMessage(Level.ERROR, "Capability type '" + key + "' is NOT SUPPORTED.");
			}
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
		try {
			if (this.getCapabilityType(capabilityType).equalsIgnoreCase(CapabilityType.PLATFORM)) {
				this.dc.setCapability(this.getCapabilityType(capabilityType), pos.getPlatform());
			} else {
				this.dc.setCapability(this.getCapabilityType(capabilityType), capabilityValue);
			}
			this.tsl.exitLogger(true);
			return;
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger(false);
			return;
		}
	}

	/**
	 * 
	 * @param capabilityName
	 * @return
	 */
	private String getCapabilityType(String capabilityType) {
		switch (capabilityType.toUpperCase()) {
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
		}
		return null;
	}

	private enum desiredCapabilitiesEnum {
		ACCEPT_SSL_CERTS,
		BROWSER_NAME,
		ELEMENT_SCROLL_BEHAVIOR,
		ENABLE_PROFILING_CAPABILITY,
		HAS_NATIVE_EVENTS,
		HAS_TOUCHSCREEN,
		LOGGING_PREFS,
		OVERLAPPING_CHECK_DISABLED,
		PAGE_LOAD_STRATEGY,
		PLATFORM,
		PROXY,
		ROTATABLE,
		SUPPORTS_ALERTS,
		SUPPORTS_APPLICATION_CACHE,
		SUPPORTS_FINDING_BY_CSS,
		SUPPORTS_JAVASCRIPT,
		SUPPORTS_LOCATION_CONTEXT,
		SUPPORTS_NETWORK_CONNECTION,
		SUPPORTS_SQL_DATABASE,
		SUPPORTS_WEB_STORAGE,
		TAKES_SCREENSHOT,
		UNEXPECTED_ALERT_BEHAVIOUR,
		VERSION;
	}

	private void setSeleniumServerCapability(String capabilityType, String capabilityValue, String defaultCapabilityValue) {
		tsl.enterLogger("In method setCapability", "capabilityType = '" + capabilityType + "'; capabilityValue = '" + capabilityValue + "'; defaultCapabilityValue = '" + defaultCapabilityValue + "'");
		try {
			switch (capabilityType.toUpperCase()) {
			case "AVOIDING_PROXY":
				this.dc.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, capabilityValue);
				break;
			case "ENSURING_CLEAN_SESSION":
				this.dc.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, capabilityValue);
				break;
			case "ONLY_PROXYING_SELENIUM_TRAFFIC":
				this.dc.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, capabilityValue);
				break;
			case "PROXY_PAC":
				this.dc.setCapability(CapabilityType.ForSeleniumServer.PROXY_PAC, capabilityValue);
				break;
			case "PROXYING_EVERYTHING":
				this.dc.setCapability(CapabilityType.ForSeleniumServer.PROXYING_EVERYTHING, capabilityValue);
				break;
			}
			this.tsl.exitLogger(true);
			return;
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger(false);
			return;
		}
	}

	private enum forSeleniumServerDesiredCapabiltiesEnum {
		AVOIDING_PROXY,
		ENSURING_CLEAN_SESSION,
		ONLY_PROXYING_SELENIUM_TRAFFIC,
		PROXY_PAC,
		PROXYING_EVERYTHING;
	}
	
}
