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
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import com.kineticskunk.chrome.SetChromeDriverDesiredCapabilities;
import com.kineticskunk.driverutilities.DesiredCapabilityException;
import com.kineticskunk.firefox.SetFireFoxDesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.openqa.selenium.remote.CapabilityType.PROXY;

public enum DriverType implements DriverSetup {

    FIREFOX {
        public DesiredCapabilities getDesiredCapabilities(HashMap<String, Object> params, Proxy proxySettings) throws DesiredCapabilityException, IOException {
        	try {
        		SetFireFoxDesiredCapabilities dc = new SetFireFoxDesiredCapabilities(params);
            	dc.setFireFoxDesiredCapabilities();
            	return addProxySettings(dc.getFireFoxDesiredCapabilities(), proxySettings);
        	} catch (Exception ex) {
        		catchError(Level.FATAL, DRIVERTYPE, "getDesiredCapabilities", new String[]{params.toString(), proxySettings.toString()}, ex);
        		return null;
        	}
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new FirefoxDriver(capabilities);
        }
    },
    CHROME {
        public DesiredCapabilities getDesiredCapabilities(HashMap<String, Object> params, Proxy proxySettings) throws Exception {
        	System.setProperty("webdriver.chrome.driver", (new File(params.get("chromeDriverExecutable").toString()).getAbsolutePath()));
        	SetChromeDriverDesiredCapabilities cddc = new SetChromeDriverDesiredCapabilities(params);
        	cddc.setDesiredCapabilities();
            return addProxySettings(cddc.getDesiredCapabilities(), proxySettings);
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new ChromeDriver(capabilities);
        }
    },
    IE {
        public DesiredCapabilities getDesiredCapabilities(HashMap<String, Object> params,Proxy proxySettings) {
        	System.setProperty("webdriver.ie.driver", (new File(params.get("internetExplorerDriverExecutable").toString()).getAbsolutePath()));
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
        public DesiredCapabilities getDesiredCapabilities(HashMap<String, Object> params, Proxy proxySettings) {
            DesiredCapabilities capabilities = DesiredCapabilities.safari();
            capabilities.setCapability("safari.cleanSession", true);
            return addProxySettings(capabilities, proxySettings);
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new SafariDriver(capabilities);
        }
    },
    OPERA {
        public DesiredCapabilities getDesiredCapabilities(HashMap<String, Object> params, Proxy proxySettings) {
            DesiredCapabilities capabilities = DesiredCapabilities.operaBlink();
            return addProxySettings(capabilities, proxySettings);
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new OperaDriver(capabilities);
        }
    },
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
        }
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
