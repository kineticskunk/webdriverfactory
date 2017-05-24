package com.kineticskunk.basetests;

import org.testng.annotations.AfterClass;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import com.kineticskunk.driverfactory.DriverFactory;

public class TestBaseSetup {
	
	private static int WAIT = 60;
	private static final Logger logger = LogManager.getLogger(Thread.currentThread().getName());
	private static final Marker TESTBASESETUP = MarkerManager.getMarker("TESTBASESETUP");

	private DriverFactory df;

	private WebDriver wd;
	
	@BeforeClass
	@Parameters({ "browserType", "desiredCapabilitiesConfigJSON" })
	public void setDriver(String browserType, String desiredCapabilitiesConfigJSON) throws Exception {
		this.df =  new DriverFactory(browserType, desiredCapabilitiesConfigJSON);
		/*switch (browserType.toLowerCase()) {
		case "chrome":
			this.getLogger().info("-------------***LAUNCHING GOOGLE CHROME***--------------");
			try {
				this.df =  new DriverFactory(browserType, desiredCapabilitiesConfigJSON);
			} catch (Exception e) {
				this.getLogger().debug(TESTBASESETUP, "An error occurred while attempting to load the Chromedriver");
				this.getLogger().error(e.getLocalizedMessage());
			}
			break;
		case "firefox":
			this.getLogger().info("-------------***LAUNCHING MOZILLA FIREFOX***--------------");
			try {
				//ldc.loadWebDriverProperties("firefoxdesiredcapabilities.properties");
				//ldc.loadWebDriverProfilePreference("firefoxprofile.properties");
			} catch (Exception e) {
				this.getLogger().fatal("An error occurred while attempting to load the Geckodriver");
				this.getLogger().error(e.getLocalizedMessage());
			}
			break;
		default:
			this.getLogger().fatal("Brower '" + browserType + "' is unsupported");
			break;
		}*/
		this.wd.manage().timeouts().implicitlyWait(WAIT, TimeUnit.SECONDS);
		this.wd = this.df.getDriver();
		
	}

	public WebDriver getDriver() {
		return wd;
	}

	public void navigateToURL(String url) {
		this.wd.navigate().to(url);
	}

	@AfterClass
	public void quitDriver() {
		//wd.quit();
	}

	public Logger getLogger() {
		return logger;
	}
}