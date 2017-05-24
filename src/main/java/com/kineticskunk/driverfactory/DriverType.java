package com.kineticskunk.driverfactory;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
//import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import com.kineticskunk.desiredcapabilities.LoadDesiredCapabilities;
import com.kineticskunk.driverutilities.DesiredCapabilityException;

import java.io.IOException;
import java.util.List;

import static org.openqa.selenium.remote.CapabilityType.PROXY;

public enum DriverType implements DriverSetup {

	FIREFOX {
		public DesiredCapabilities getDesiredCapabilities(String browserType, String desiredCapabilitiesConfigJSON, Proxy proxySettings) throws DesiredCapabilityException, IOException {
			DriverExecutable de = new DriverExecutable(browserType);
			de.setDriverExecutable();
			return this.loadDesiredCapabilities(browserType, desiredCapabilitiesConfigJSON, proxySettings);
		}

		public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
			return new FirefoxDriver(capabilities);
		}
	},
	CHROME {
		public DesiredCapabilities getDesiredCapabilities(String browserType, String desiredCapabilitiesConfigJSON, Proxy proxySettings) throws Exception {
			return this.loadDesiredCapabilities(browserType, desiredCapabilitiesConfigJSON, proxySettings);
		}

		public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
			return new ChromeDriver(capabilities);
		}
	},
	IE {
		public DesiredCapabilities getDesiredCapabilities(String browserType, String desiredCapabilitiesConfigJSON, Proxy proxySettings) {

			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability("requireWindowFocus", true);
			return addProxySettings(capabilities, proxySettings);
		}

		public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
			return new InternetExplorerDriver(capabilities);
		}
	},
	SAFARI {
		public DesiredCapabilities getDesiredCapabilities(String browserType, String desiredCapabilitiesConfigJSON, Proxy proxySettings) {
			DesiredCapabilities capabilities = DesiredCapabilities.safari();
			capabilities.setCapability("safari.cleanSession", true);
			return addProxySettings(capabilities, proxySettings);
		}

		public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
			return new SafariDriver(capabilities);
		}
	},
	OPERA {
		public DesiredCapabilities getDesiredCapabilities(String browserType, String desiredCapabilitiesConfigJSON, Proxy proxySettings) {
			DesiredCapabilities capabilities = DesiredCapabilities.operaBlink();
			return addProxySettings(capabilities, proxySettings);
		}

		public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
			return new OperaDriver(capabilities);
		}
		/*  },
    PHANTOMJS {
        public DesiredCapabilities getDesiredCapabilities(HashMap<String, Object> params, Proxy proxySettings) {
            DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
            final List<String> cliArguments = new ArrayList<String>();
            cliArguments.add("--web-security=false");
            cliArguments.add("--ssl-protocol=any");
            cliArguments.add("--ignore-ssl-errors=true");
            capabilities.setCapability("phantomjs.cli.args", applyPhantomJSProxySettings(cliArguments, proxySettings));
            capabilities.setCapability("takesScreenshot", true);

            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new PhantomJSDriver(capabilities);
        }*/
	};

	private static final Logger logger = LogManager.getLogger(DriverType.class.getName());
	private static final Marker DRIVERTYPE = MarkerManager.getMarker("DRIVERTYPE");

	public void catchError(Level level, Marker marker, String methodName,  String params[], Exception ex) {
		logger.entry();
		if (logger.isDebugEnabled()) {
			logger.log(level, marker, "RUN {} USING {}", methodName, params);
			logger.log(level, marker, "Cause = " + ex.getCause());
			logger.log(level, marker, "Message = " + ex.getMessage());
			logger.log(level, marker, "Local Message = " + ex.getLocalizedMessage());
			logger.catching(level, ex);
		}
		logger.exit();
	}

	public DesiredCapabilities loadDesiredCapabilities(String browserType, String desiredCapabilitiesConfigJSON, Proxy proxySettings) {
		LoadDesiredCapabilities ldc = new LoadDesiredCapabilities();
		try {
			ldc = new LoadDesiredCapabilities(browserType, desiredCapabilitiesConfigJSON);
			return addProxySettings(ldc.getDesiredCapabilities(), proxySettings);
		} catch (Exception ex) {
			catchError(Level.FATAL, DRIVERTYPE, "getDesiredCapabilities: failed to load", new String[]{ldc.getDesiredCapabilities().toString(), proxySettings.toString()}, ex);
			return null;
		}
	}
	protected DesiredCapabilities addProxySettings(DesiredCapabilities capabilities, Proxy proxySettings) {
		try {
			if (null != proxySettings) {
				capabilities.setCapability(PROXY, proxySettings);
			}
		} catch (Exception ex) {
			catchError(Level.FATAL, DRIVERTYPE, "addProxySettings", new String[]{capabilities.toString(), proxySettings.toString()}, ex);
		}
		return capabilities;
	}

	protected List<String> applyPhantomJSProxySettings(List<String> cliArguments, Proxy proxySettings) {
		if (null == proxySettings) {
			cliArguments.add("--proxy-type=none");
		} else {
			cliArguments.add("--proxy-type=http");
			cliArguments.add("--proxy=" + proxySettings.getHttpProxy());
		}
		return cliArguments;
	}
}
