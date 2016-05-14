package com.kineticskunk.firefox;

import java.util.HashMap;
import org.apache.commons.lang3.EnumUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kineticskunk.driverutilities.DesiredCapabilityException;
import com.kineticskunk.driverutilities.WebDriverLoggingPreferences;
import com.kineticskunk.ini.PlatformOperatingSystem;
import com.kineticskunk.utilities.Converter;

public class FireFoxDesiredCapabilities {

	private static final Logger logger = LogManager.getLogger(FireFoxDesiredCapabilities.class.getName());
	private static final Marker FIREFOXDESIREDCAPABILITIES = MarkerManager.getMarker("FIREFOXDESIREDCAPABILITIES");

	private static final String USE_HOST_PLATFORM = "use_host_platform";
	private static final String USE_HOST_OS_VERSION = "use_host_os_version";

	private DesiredCapabilities dc;
	private WebDriverLoggingPreferences wdlp;
	private PlatformOperatingSystem pos;
	private HashMap<String, Object> params;

	public FireFoxDesiredCapabilities() {
		this.dc = new DesiredCapabilities();
		this.pos = new PlatformOperatingSystem();
	}

	public FireFoxDesiredCapabilities(HashMap<String, Object> params) throws DesiredCapabilityException {
		this();
		this.params = params;
	}
	
	/**
	 * Set FireFox DesiredCapabilities
	 * @throws DesiredCapabilityException
	 */
	public void setFireFoxDesiredCapabilities() throws DesiredCapabilityException {
		for (String key : this.params.keySet()) {
			String value = params.get(key).toString();
			if (!value.isEmpty() || !value.equals(null) || !value.equals("") || value.equals(Keys.SPACE)) {
				if (EnumUtils.isValidEnum(desiredCapabilitiesKeysEnum.class, key.toUpperCase())) {
					switch (key.toUpperCase()) {
					case "VERSION":
						this.dc.setCapability(key.toUpperCase(), System.getProperty("os.version"));
						break;
					case "PLATFORM":
						this.dc.setCapability(key.toUpperCase(), System.getProperty("os.name"));
						break;
					default:
						this.dc.setCapability(key.toUpperCase(), value);
					}
				} else if (EnumUtils.isValidEnum(seleniumServerDesiredCapabiltiesEnum.class, key.toUpperCase())){
					this.setSeleniumServerCapability(key.toUpperCase(), value);
				}
			} else {
				logger.log(Level.DEBUG, FIREFOXDESIREDCAPABILITIES, "Capability type '" + key.toUpperCase() + "' has not being set as the value is either empty, null or equals " + (char)34 + (char)34  + ".");
			}
		}
	}

	/**
	 * 
	 * @param capabilityType
	 * @param capabilityValue
	 * @param defaultCapabilityValue
	 */
	private void setSeleniumServerCapability(String capabilityType, String capabilityValue) {
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

			return;
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				logger.catching(Level.DEBUG, ex);
	
			}
			return;
		}
	}

	/**
	 * Return firefox desired capabilities
	 * @return
	 */
	public DesiredCapabilities getFireFoxDesiredCapabilities() {
		return dc;
	}

	/**
	 * 
	 * @author yodaqua
	 * @since
	 */
	private enum desiredCapabilitiesKeysEnum {
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

	/**
	 * 
	 * @author yodaqua
	 *
	 */
	private enum seleniumServerDesiredCapabiltiesEnum {
		AVOIDING_PROXY,
		ENSURING_CLEAN_SESSION,
		ONLY_PROXYING_SELENIUM_TRAFFIC,
		PROXY_PAC,
		PROXYING_EVERYTHING;
	}

	protected void setDesiredCapability(String capabilityType, String capabilityValue) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, "Capability type '" + capabilityType + "' has being set to '" + capabilityValue + "'");
			this.dc.setCapability(capabilityType, capabilityValue);
		} catch (Exception ex) {
			logger.catching(ex);
		}
	}

	/**
	 * Loads the 'ACCEPT_SSL_CERTS' value into the DesiredCapabilities object
	 * 
	 * @author yodaqua
	 * @param value
	 */
	public void setAcceptSslCerts(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, "Capability type 'ACCEPT_SSL_CERTS' has being set to '" + value + "'");
			this.dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, Converter.toBoolean(value));
		} catch (Exception ex) {
			logger.catching(ex);

		}
	}

	/**
	 * Loads the 'BROWSER_NAME' value into the DesiredCapabilities object
	 * 
	 * @author yodaqua
	 * @param value
	 */
	public void setBrowserName(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.BROWSER_NAME.toString(), value);
			this.dc.setCapability(CapabilityType.BROWSER_NAME, value);

		} catch (Exception ex) {
			logger.catching(ex);

		}
	}

	/**
	 * Loads the 'ELEMENT_SCROLL_BEHAVIOR' value into the DesiredCapabilities object
	 * 
	 * @author yodaqua
	 * @param value
	 */
	public void setEnrollmentBehaviour(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.ELEMENT_SCROLL_BEHAVIOR.toString(), value);
			this.dc.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, Converter.toInteger(value));

		} catch (Exception ex) {
			logger.catching(ex);

		}
	}

	public void setEnableProfilingCapability(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.ENABLE_PROFILING_CAPABILITY.toString(), value);
			this.dc.setCapability(CapabilityType.ENABLE_PROFILING_CAPABILITY, Converter.toBoolean(value));

		} catch (Exception ex) {
			logger.catching(ex);

		}
	}

	public void setHasNativeEvents(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.HAS_NATIVE_EVENTS.toString(), value);
			this.dc.setCapability(CapabilityType.HAS_NATIVE_EVENTS, Converter.toBoolean(value));

		} catch (Exception ex) {
			logger.catching(ex);

		}
	}

	public void setHasTouchScreen(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.HAS_TOUCHSCREEN.toString(), value);
			this.dc.setCapability(CapabilityType.HAS_TOUCHSCREEN, Converter.toBoolean(value));

		} catch (Exception ex) {
			logger.catching(ex);

		}
	}

	public void setLoggingPrefs(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.LOGGING_PREFS.toString(), value);
			wdlp = new WebDriverLoggingPreferences(value);
			this.dc.setCapability(CapabilityType.LOGGING_PREFS, wdlp.getLoggingPreferences());

		} catch (Exception ex) {
			logger.catching(ex);

		}
	}

	public String getLoggingPreferences(String enabled) {
		return wdlp.getLoggingPreferences().getLevel(enabled).toString();
	}

	public void setOverlappingCheckDisabled(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.OVERLAPPING_CHECK_DISABLED.toString(), value);
			this.dc.setCapability(CapabilityType.OVERLAPPING_CHECK_DISABLED, Converter.toBoolean(value));

		} catch (Exception ex) {
			logger.catching(ex);

		}
	}

	public void setPageLoadStrategy(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.PAGE_LOAD_STRATEGY.toString(), value);
			if (EnumUtils.isValidEnum(pageLoadStrategies.class, value.toLowerCase())) {
				this.dc.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, value);
			} else {
				logger.log(Level.DEBUG, FIREFOXDESIREDCAPABILITIES, "The PAGE_LOAD_STRATEGY value of '" + value + "' IS NOT SUPPORTED. Setting the PAGE_LOAD_STRATEGY to 'unstable'");
				this.dc.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "unstable");
			}

		} catch (Exception ex) {
			logger.catching(ex);

		}

	}

	private enum pageLoadStrategies {
		none,
		unstable,
		eager,
		conservative,
		normal;
	}

	public void setPlatform(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.PLATFORM.toString(), value);
			if (value.equalsIgnoreCase(USE_HOST_PLATFORM)) {
				this.dc.setCapability(CapabilityType.PLATFORM, pos.getHostPlatform());
			} else {
				this.dc.setCapability(CapabilityType.PLATFORM, pos.getPlatform(value));
			}
		} catch (Exception ex) {
			logger.catching(ex);
		}
	}

	public void setRotable(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.ROTATABLE.toString(), value);
			this.dc.setCapability(CapabilityType.ROTATABLE, Converter.toBoolean(value));
		} catch (Exception ex) {
			logger.catching(ex);
		}
	}

	/**
	 * 
	 * @param value
	 */
	public void setSupportAlerts(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.SUPPORTS_ALERTS.toString(), value);
			this.dc.setCapability(CapabilityType.SUPPORTS_ALERTS, Converter.toBoolean(value));
		} catch (Exception ex) {
			logger.catching(ex);
		}
	}

	public void setSupportsApplicationCache(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.SUPPORTS_APPLICATION_CACHE.toString(), value);
			this.dc.setCapability(CapabilityType.SUPPORTS_APPLICATION_CACHE, Converter.toBoolean(value));
		} catch (Exception ex) {
			logger.catching(ex);
		}
	}

	public void setSupportsFindingByCSS(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.SUPPORTS_FINDING_BY_CSS.toString(), value);
			this.dc.setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS, Converter.toBoolean(value));
		} catch (Exception ex) {
			logger.catching(ex);
		}
	}

	public void setSupportsJavaScript(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.SUPPORTS_JAVASCRIPT.toString(), value);
			this.dc.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, Converter.toBoolean(value));
		} catch (Exception ex) {
			logger.catching(ex);
		}
	}

	public void setSupportsLocationContext(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.SUPPORTS_LOCATION_CONTEXT.toString(), value);
			this.dc.setCapability(CapabilityType.SUPPORTS_LOCATION_CONTEXT, Converter.toBoolean(value));
		} catch (Exception ex) {
			logger.catching(ex);
		}
	}

	public void setSupportsNetworkConnection(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.SUPPORTS_NETWORK_CONNECTION.toString(), value);
			this.dc.setCapability(CapabilityType.SUPPORTS_NETWORK_CONNECTION, Converter.toBoolean(value));
		} catch (Exception ex) {
			logger.catching(ex);
		}
	}

	public void setSupportsSQLDatabase(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.SUPPORTS_SQL_DATABASE.toString(), value);
			this.dc.setCapability(CapabilityType.SUPPORTS_SQL_DATABASE, Converter.toBoolean(value));
		} catch (Exception ex) {
			logger.catching(ex);
		}
	}

	public void setSupportsWebStorage(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.SUPPORTS_WEB_STORAGE.toString(), value);
			this.dc.setCapability(CapabilityType.SUPPORTS_WEB_STORAGE, Converter.toBoolean(value));
		} catch (Exception ex) {
			logger.catching(ex);
		}
	}

	/**
	 * Set the 
	 * @param value
	 */
	public void setTakesScreenShot(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.TAKES_SCREENSHOT.toString(), value);
			this.dc.setCapability(CapabilityType.TAKES_SCREENSHOT, Converter.toBoolean(value));
		} catch (Exception ex) {
			logger.catching(ex);
		}
	}
	
	/**
	 * Set Unexpected Alert Behaviour value. Valid values are 'ACCEPT', 'DISMISS' and 'IGNORE'. In the event of an invalid Unexpected Alert Behaviour value, the value will defaul to 'ACCEPT'
	 * 
	 * @param value
	 */
	public void setUnexpectedAlertBehaviour(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.UNEXPECTED_ALERT_BEHAVIOUR.toString(), value);
			if (value.equalsIgnoreCase("ACCEPT") || value.equalsIgnoreCase("DISMISS") || value.equalsIgnoreCase("IGNORE")) {
				this.dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.valueOf(value));
			} else {
				logger.log(Level.DEBUG, FIREFOXDESIREDCAPABILITIES, "Unexpected Alert Behaviour '" + value.toUpperCase() + " is invalid. Setting the Unexpected Alert Behaviour value to 'ACCEPT'");
				this.dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
			}
		} catch (Exception ex) {
			logger.catching(ex);
		}
	}
	
	/**
	 * Set the OS version of the target machine. Set <strong>value</string> to "use_host_os_version" if the the host is to be used for test execution
	 * 
	 * @param value
	 */
	public void setVersion(String value) {
		try {
			logger.log(Level.INFO, FIREFOXDESIREDCAPABILITIES, desiredCapabilitiesKeysEnum.VERSION.toString(), value);
			if (value.equalsIgnoreCase(USE_HOST_OS_VERSION)) {
				value = System.getProperty("os.version");
			}
			this.dc.setCapability(CapabilityType.VERSION, value);
		} catch (Exception ex) {
			logger.catching(ex);

		}
	}

}