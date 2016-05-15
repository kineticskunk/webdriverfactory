package com.kineticskunk.driverfactory;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.kineticskunk.utilities.Converter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static com.kineticskunk.driverfactory.DriverType.valueOf;
import static org.openqa.selenium.Proxy.ProxyType.MANUAL;

public class DriverFactory {
	
	private static final Logger logger = LogManager.getLogger(DriverFactory.class.getName());
	private static final Marker DRIVEFACTORY = MarkerManager.getMarker("DRIVEFACTORY");
	
	private HashMap<String, Object> params;

    private WebDriver webdriver;
    private DriverType selectedDriverType;

    private DriverType defaultDriverType;
    private String browser;
    private String operatingSystem;
    private String systemArchitecture;
    private boolean useRemoteWebDriver;
    private boolean proxyEnabled;
    private String proxyHost;
    private Integer proxyPort;
    private String proxyDetails;
    
    public DriverFactory(HashMap<String, Object> params) {
    	this.params = params;
    	
    	this.defaultDriverType = valueOf(this.params.get("Browser").toString().toUpperCase());
    	this.browser = this.params.get("Browser").toString();
    	this.operatingSystem = System.getProperty("os.name").toUpperCase();
        this.systemArchitecture = System.getProperty("os.arch");
    	this.useRemoteWebDriver = Converter.toBoolean(this.params.get("useRemoteWebDriver").toString());
    	this.proxyEnabled = Converter.toBoolean(this.params.get("proxyEnabled").toString());
    	if (this.proxyEnabled) {
    		this.proxyHost = this.params.get("proxyHost").toString();
        	this.proxyPort = Converter.toInteger(this.params.get("proxyPort").toString());
        	this.proxyDetails = String.format("%s:%d", this.proxyHost, this.proxyPort);
    	} else {
    		this.proxyDetails = "AUTODETECT";
    	}
    }

    public WebDriver getDriver() throws Exception {
        if (null == webdriver) {
            Proxy proxy = null;
            if (this.proxyEnabled) {
                proxy = new Proxy();
                proxy.setProxyType(MANUAL);
                proxy.setHttpProxy(proxyDetails);
                proxy.setSslProxy(proxyDetails);
            }
            determineEffectiveDriverType();
            DesiredCapabilities desiredCapabilities = selectedDriverType.getDesiredCapabilities(this.params, proxy);
            instantiateWebDriver(desiredCapabilities);
        }

        return webdriver;
    }

    public void quitDriver() {
        if (null != webdriver) {
            webdriver.quit();
        }
    }

    private void determineEffectiveDriverType() {
        DriverType driverType = defaultDriverType;
        try {
            driverType = valueOf(this.browser);
        } catch (IllegalArgumentException ignored) {
        	logger.catching(ignored);
        	logger.log(Level.FATAL, DRIVEFACTORY, "Unknown driver specified, defaulting to '" + driverType + "'...");
        } catch (NullPointerException ignored) {
        	driverType  = DriverType.FIREFOX;
        	logger.log(Level.DEBUG, DRIVEFACTORY, "No driver specified, defaulting to '" + driverType + "'...");
        }
        this.selectedDriverType = driverType;
    }

    private void instantiateWebDriver(DesiredCapabilities desiredCapabilities) throws MalformedURLException {
    	logger.log(Level.INFO, DRIVEFACTORY, "Current Operating System: " + operatingSystem);
    	logger.log(Level.INFO, DRIVEFACTORY, "Current Architecture: " + systemArchitecture);
    	logger.log(Level.INFO, DRIVEFACTORY, "Current Browser Selection: " + selectedDriverType);

        if (useRemoteWebDriver) {
            URL seleniumGridURL = new URL(this.params.get("gridURL").toString());
            String desiredBrowserVersion = this.params.get("desiredBrowserVersion").toString();
            String desiredPlatform =  this.params.get("desiredPlatform").toString();

            if (null != desiredPlatform && !desiredPlatform.isEmpty()) {
                desiredCapabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));
            }

            if (null != desiredBrowserVersion && !desiredBrowserVersion.isEmpty()) {
                desiredCapabilities.setVersion(desiredBrowserVersion);
            }

            webdriver = new RemoteWebDriver(seleniumGridURL, desiredCapabilities);
        } else {
            webdriver = selectedDriverType.getWebDriverObject(desiredCapabilities);
        }
    }
}

