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

import com.kineticskunk.driverutilities.DesiredCapabilityException;
import com.kineticskunk.firefox.SetFireFoxDesiredCapabilities;
import com.kineticskunk.firefox.SetFireFoxProfile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.openqa.selenium.remote.CapabilityType.PROXY;

public enum DriverType implements DriverSetup {

    FIREFOX {
        public DesiredCapabilities getDesiredCapabilities(HashMap<String, Object> params, Proxy proxySettings) throws DesiredCapabilityException, IOException {
        	SetFireFoxDesiredCapabilities dc = new SetFireFoxDesiredCapabilities();
        	dc.setDesiredCapabilitiesProperties(params.get("desiredCapabilites").toString());
        	dc.setFireFoxDesiredCapabilities();
        	if (params.get("addProfile").toString().equalsIgnoreCase("TRUE")) {
        		SetFireFoxProfile p = new SetFireFoxProfile();
        		if (params.containsKey("profilePreferences")) {
        			p.setPreferences(params.get("profilePreferences").toString());
            		if (params.get("addFireBug").toString().equalsIgnoreCase("TRUE")) {
            			if (params.containsKey("firebugPreferences")) {
            				p.setPreferences(params.get("firebugPreferences").toString());
                			p.setAddFireBug(params.get("fireBugLocation").toString(), params.get("fireBugName").toString());
            			}
            		} else {
            			logger.log(Level.INFO, DRIVERTYPE, "Not adding FireBug extension name = '" + params.get("profilePreferences").toString() + "'");
            		}
            		p.setFirefoxProfile();
            		dc.setFireFoxProfile(p.getFirefoxProfile());
        		} else {
        			logger.log(Level.INFO, DRIVERTYPE, "Not adding FireBug extension name = '" + params.get("profilePreferences").toString() + "'");
        		}
        	} else {
    			logger.log(Level.INFO, DRIVERTYPE, "FireFox has been loaded with standard WebDriver extension");        		
        	}
            return addProxySettings(dc.getFireFoxDesiredCapabilities(), proxySettings);
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new FirefoxDriver(capabilities);
        }
    },
    CHROME {
        public DesiredCapabilities getDesiredCapabilities(HashMap<String, Object> params, Proxy proxySettings) {
        	System.setProperty("webdriver.chrome.driver", (new File(params.get("chromedriver").toString()).getAbsolutePath()));
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
            HashMap<String, String> chromePreferences = new HashMap<String, String>();
            chromePreferences.put("profile.password_manager_enabled", "false");
            capabilities.setCapability("chrome.prefs", chromePreferences);
            return addProxySettings(capabilities, proxySettings);
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new ChromeDriver(capabilities);
        }
    },
    IE {
        public DesiredCapabilities getDesiredCapabilities(HashMap<String, Object> params,Proxy proxySettings) {
            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
            capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
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

    protected DesiredCapabilities addProxySettings(DesiredCapabilities capabilities, Proxy proxySettings) {
        if (null != proxySettings) {
            capabilities.setCapability(PROXY, proxySettings);
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
