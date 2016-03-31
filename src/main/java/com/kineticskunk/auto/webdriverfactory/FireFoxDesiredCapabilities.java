package com.kineticskunk.auto.webdriverfactory;

import java.util.Hashtable;
import java.util.Set;

import org.apache.commons.lang3.EnumUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kineticskunk.auto.logging.TestServiceLogging;

public class FireFoxDesiredCapabilities {
	
	private static final String AUTODETECT = "autodetect";
	private static final String USE_HOST_PLATFORM = "use_host_platform";
	private static final String USE_HOST_OS_VERSION = "use_host_os_version";

	private DesiredCapabilities dc;
	private Hashtable<String, String> dcConfig;
	private PlatformOperatingSystem pos;
	private TestServiceLogging tsl;

	public FireFoxDesiredCapabilities(boolean enableLogging) {
		this.dc = new DesiredCapabilities();
		this.pos = new PlatformOperatingSystem();
		this.tsl = new TestServiceLogging(LogManager.getLogger(FireFoxDesiredCapabilities.class.getName()), enableLogging);
	}

	public FireFoxDesiredCapabilities(boolean enableLogging, Hashtable<String, String> dcConfig) {
		this(enableLogging);
		this.dcConfig = dcConfig;
	}
	
	/*

	private void setCapability(String capabilityType, Object capabilityValue) {
		tsl.enterLogger("In method setCapability", "capabilityType = '" + capabilityType + "'; capabilityValue = '" + capabilityValue + "'");
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
*/
	protected void setFireFoxDesiredCapabilities() {
		Set<String> keys = this.dcConfig.keySet();
		for (String key : keys) {
			String value = dcConfig.get(key);
			if (!value.isEmpty() || !value.equals(null) || !value.equals("") || value.equals(Keys.SPACE)) {
				switch (key.toUpperCase()) {
				case "ACCEPT_SSL_CERTS":			
					this.setAcceptSslCerts(value);
					break;
				case "BROWSER_NAME":
					this.setBrowserName(value);
					break;
				case "ELEMENT_SCROLL_BEHAVIOR":
					this.setEnrollmentBehaviour(value);
					break;
				case "ENABLE_PROFILING_CAPABILITY":
					this.setEnableProfilingCapability(value);
					break;
				case "HAS_NATIVE_EVENTS":
					this.setHasNativeEvents(value);
					break;
				case "HAS_TOUCHSCREEN":
					this.setHasTouchScreen(value);
					break;
				case "LOGGING_PREFS":
					this.setLoggingPrefs(value);
					break;
				case "OVERLAPPING_CHECK_DISABLED":
					this.setOverlappingCheckDisabled(value);
					break;
				case "PAGE_LOAD_STRATEGY":
					this.setPageLoadStrategy(value);
					break;
				case "PLATFORM":
					this.setPlatform(value);
					break;
				case "PROXY":
					this.setProxy(value);
					break;
				case "ROTATABLE":
					this.setRotable(value);
					break;
				case "SUPPORTS_ALERTS":
					this.setSupportAlerts(value);
					break;
				case "SUPPORTS_APPLICATION_CACHE":
					this.setSupportsApplicationCache(value);
					break;
				case "SUPPORTS_FINDING_BY_CSS":
					this.setSupportsFindingByCSS(value);
					break;
				case "SUPPORTS_JAVASCRIPT":
					this.setSupportsJavaScript(value);
					break;
				case "SUPPORTS_LOCATION_CONTEXT":
					this.setSupportsLocationContext(value);
					break;
				case "SUPPORTS_NETWORK_CONNECTION":
					this.setSupportsNetworkConnection(value);
					break;
				case "SUPPORTS_SQL_DATABASE":
					this.setSupportsSQLDatabase(value);
					break;
				case "SUPPORTS_WEB_STORAGE":
					this.setSupportsWebStorage(value);
					break;
				case "TAKES_SCREENSHOT":
					this.setTakesScreenShot(value);
					break;
				case "UNEXPECTED_ALERT_BEHAVIOUR":
					this.setUnexpectedAlertBehaviour(value);
					break;
				case "VERSION":
					this.setVersion(value);
					break;
				}
			} else {
				this.tsl.logMessage(Level.INFO, "Capability type '" + key + "' has not being set.");
			}
		}
	}
	
	protected DesiredCapabilities getFireFoxDesiredCapabilities() {
		return dc;
	}

	protected void setAcceptSslCerts(String value) {
		try {
			this.dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, value);
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger(false);
		}
	}

	protected void setBrowserName(String value) {
		this.dc.setCapability(CapabilityType.BROWSER_NAME, value);
	}

	protected void setEnrollmentBehaviour(String value) {
		this.dc.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, value);
	}

	protected void setEnableProfilingCapability(String value) {
		this.dc.setCapability(CapabilityType.ENABLE_PROFILING_CAPABILITY, value);
	}

	protected void setHasNativeEvents(String value) {
		this.dc.setCapability(CapabilityType.HAS_NATIVE_EVENTS, value);
	}

	protected void setHasTouchScreen(String value) {
		this.dc.setCapability(CapabilityType.HAS_TOUCHSCREEN, value);
	}

	protected void setLoggingPrefs(String value) {
		this.dc.setCapability(CapabilityType.LOGGING_PREFS, value);
	}

	protected void setOverlappingCheckDisabled(String value) {
		this.dc.setCapability(CapabilityType.OVERLAPPING_CHECK_DISABLED, value);
	}

	protected void setPageLoadStrategy(String value) {
		//TODO get page load strategy
		this.dc.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, value);
	}

	protected void setPlatform(String value) {
		try {
			tsl.enterLogger("Setting capability type 'Platform' to '" +  value + "'" );
			if (value.equalsIgnoreCase(USE_HOST_PLATFORM)) {
				this.dc.setCapability(CapabilityType.PLATFORM, pos.getHostPlatform());
			} else {
				this.dc.setCapability(CapabilityType.PLATFORM, pos.getPlatform(value));
			}
			this.tsl.exitLogger(true);
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger(false);
		}
		
	}

	protected void setProxy(String value) {
		try {
			//TODO write proper proxy class!!!!
			if (!value.equalsIgnoreCase(AUTODETECT)) {
				WebDriverProxy wdp = new WebDriverProxy();
				wdp.setHTTPProxy(value);
				wdp.setFTPProxy(value);
				wdp.setSSLProxy(value);
			} else {
				this.setProxy(AUTODETECT);
			}
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger(false);
		}
		
		this.dc.setCapability(CapabilityType.PROXY, value);
	}

	protected void setRotable(String value) {
		this.dc.setCapability(CapabilityType.ROTATABLE, value);
	}

	protected void setSupportAlerts(String value) {
		this.dc.setCapability(CapabilityType.SUPPORTS_ALERTS, value);
	}

	protected void setSupportsApplicationCache(String value) {
		this.dc.setCapability(CapabilityType.SUPPORTS_APPLICATION_CACHE, value);
	}

	protected void setSupportsFindingByCSS(String value) {
		this.dc.setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS, value);
	}

	protected void setSupportsJavaScript(String value) {
		this.dc.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, value);
	}

	protected void setSupportsLocationContext(String value) {
		this.dc.setCapability(CapabilityType.SUPPORTS_LOCATION_CONTEXT, value);
	}

	protected void setSupportsNetworkConnection(String value) {
		this.dc.setCapability(CapabilityType.SUPPORTS_NETWORK_CONNECTION, value);
	}

	protected void setSupportsSQLDatabase(String value) {
		this.dc.setCapability(CapabilityType.SUPPORTS_SQL_DATABASE, value);
	}

	protected void setSupportsWebStorage(String value) {
		this.dc.setCapability(CapabilityType.SUPPORTS_WEB_STORAGE, value);
	}

	protected void setTakesScreenShot(String value) {
		this.dc.setCapability(CapabilityType.TAKES_SCREENSHOT, value);
	}

	protected void setUnexpectedAlertBehaviour(String value) {
		this.dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, value);
	}

	protected void setVersion(String value) {
		try {
			tsl.enterLogger("Setting capability type 'VERSION' to " +  value );
			if (value.equalsIgnoreCase(USE_HOST_OS_VERSION)) {
				value = System.getProperty("os.version");
			}
			this.dc.setCapability(CapabilityType.VERSION, value);
			this.tsl.exitLogger(true);
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger(false);
		}
	}

	/*
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
	}*/

	/**
	 * 
	 * @author yodaqua
	 * @since
	 */
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

	private enum pageLoadStrategies {
		NONE,
		UNSTABLE,
		EAGER,
		CONSERVATIVE,
		NORMAL;
	}

	/**
	 * 
	 * @param capabilityType
	 * @param capabilityValue
	 * @param defaultCapabilityValue
	 */
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

	/**
	 * 
	 * @author yodaqua
	 *
	 */
	private enum forSeleniumServerDesiredCapabiltiesEnum {
		AVOIDING_PROXY,
		ENSURING_CLEAN_SESSION,
		ONLY_PROXYING_SELENIUM_TRAFFIC,
		PROXY_PAC,
		PROXYING_EVERYTHING;
	}

}