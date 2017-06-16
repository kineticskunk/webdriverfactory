package com.kineticskunk.driverfactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import java.io.IOException;
import java.util.List;

public enum DriverType implements DriverSetup {

	FIREFOX {
		public DesiredCapabilities getDesiredCapabilities(DesiredCapabilities desiredCapabilities) throws IOException {
			return DesiredCapabilities.firefox().merge(desiredCapabilities);
		}

		public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
			try {
				return new FirefoxDriver(capabilities);
			} catch (WebDriverException e) {
				this.catchError(DRIVERTYPE, "FireFox", e);
				return null;
			}
		}
	},
	CHROME {
		public DesiredCapabilities getDesiredCapabilities(DesiredCapabilities desiredCapabilities) throws Exception {
			return DesiredCapabilities.chrome().merge(desiredCapabilities);
		}

		public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
			try {
				return new ChromeDriver(capabilities);
			} catch (WebDriverException e) {
				this.catchError(DRIVERTYPE, "Chrome", e);
				return null;
			}
		}
	},
	IE {
		public DesiredCapabilities getDesiredCapabilities(DesiredCapabilities desiredCapabilities) {
			return DesiredCapabilities.internetExplorer().merge(desiredCapabilities);
		}

		public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
			try {
				return new InternetExplorerDriver(capabilities);
			} catch (WebDriverException e) {
				this.catchError(DRIVERTYPE, "Internet Explorer", e);
				return null;
			}
		}
	},
	SAFARI {
		public DesiredCapabilities getDesiredCapabilities(DesiredCapabilities desiredCapabilities) {
			return DesiredCapabilities.safari().merge(desiredCapabilities);
		}

		public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
			try {
				return new SafariDriver(capabilities);
			} catch (WebDriverException e) {
				this.catchError(DRIVERTYPE, "Safari", e);
				return null;
			}
		}
	},
	OPERA {
		public DesiredCapabilities getDesiredCapabilities(DesiredCapabilities desiredCapabilities) {
			try {
				return DesiredCapabilities.operaBlink().merge(desiredCapabilities);
			} catch (WebDriverException e) {
				this.catchError(DRIVERTYPE, "Opera", e);
				return null;
			}
		}

		public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
			try {
				return new OperaDriver(capabilities);
			} catch (WebDriverException e) {
				this.catchError(DRIVERTYPE, "Opera", e);
				return null;
			}
		}
	};

	private final static Logger logger = LogManager.getLogger(DriverType.class.getName());
	private final static Marker DRIVERTYPE = MarkerManager.getMarker("DRIVERTYPE");

	public void catchError(Marker marker, String driverName, WebDriverException e) {
		logger.entry();
		logger.error(marker, "Failed to load driver " + driverName);
		logger.debug(marker, "Localized message = " + e.getLocalizedMessage());
		logger.debug(marker, "Cause = " + e.getCause().getMessage());
		logger.exit();
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
