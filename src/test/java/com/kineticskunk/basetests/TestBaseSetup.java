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
	private Logger logger = LogManager.getLogger(Thread.currentThread().getName());
	private Marker TESTBASESETUP = MarkerManager.getMarker("TESTBASESETUP");

	private DriverFactory df;
	private WebDriver wd;
	
	@BeforeClass
	@Parameters({ "browserType", "desiredCapabilitiesConfigJSON" })
	public void setDriver(String browserType, String desiredCapabilitiesConfigJSON) throws Exception {
		try {
			this.df =  new DriverFactory(browserType, desiredCapabilitiesConfigJSON);
			this.wd.manage().timeouts().implicitlyWait(WAIT, TimeUnit.SECONDS);
			this.wd = this.df.getDriver();
		} catch (Exception e) {
			this.logger.fatal(TESTBASESETUP, "Failed to load browser " + (char)34 + browserType + (char)34 + ".");
			this.logger.fatal(TESTBASESETUP, e.getLocalizedMessage());
			this.logger.fatal(TESTBASESETUP, e.getStackTrace());
		}
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