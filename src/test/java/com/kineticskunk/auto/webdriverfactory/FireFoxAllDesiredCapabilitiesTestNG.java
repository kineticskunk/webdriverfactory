package com.kineticskunk.auto.webdriverfactory;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kineticskunk.auto.webdriverfactory.DesiredCapabilityException;

public class FireFoxAllDesiredCapabilitiesTestNG {
	
	private static WebDriver driver;
	private static DesiredCapabilities dc;
	private static FireFoxDesiredCapabilities ffdc;
	private static FireFoxProfile ffp;
	private static Hashtable<String, String> dcConfig;
	private static Hashtable<String, String> ffConfig;
	private static Utilties utils;
	
	public FireFoxAllDesiredCapabilitiesTestNG() {
		dc = null;
		ffdc = null;
		utils = Utilties.getInstance();
		dcConfig = new Hashtable<String, String>();
		ffConfig = new Hashtable<String, String>();
	}
	
	@BeforeClass
	public static void setupTestClass() throws IOException, DesiredCapabilityException {
		dcConfig = utils.readPropertyFileIntoHashtable(dcConfig, "webdriver-firefox-desired-capabilities.properties");
		ffConfig = utils.readPropertyFileIntoHashtable(ffConfig, "webdriver-firefox-profile.properties");
		ffdc = new FireFoxDesiredCapabilities(true, dcConfig);
		ffp = new FireFoxProfile(true, ffConfig);
		ffdc.setFireFoxDesiredCapabilities();
		dc = ffdc.getFireFoxDesiredCapabilities();
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void checkIfFireFoxPropertiesLoad() {
		Assert.assertTrue(dcConfig.size() > 0);
	}
	
	/*@Test (priority = 1, groups = "desiredcapabilities")
	public void verifyPlaform() {
		Assert.assertTrue(dc.getPlatform().is(Platform.MAC));
	}
	
	@Test (priority = 2, groups = "desiredcapabilities")
	public void verifyDesiredCapabiliesObjectInstanciation() {
		Assert.assertTrue(dc.getPlatform().is(Platform.MAC));
		
	}
*/
	@Test (priority = 3, groups = "desiredcapabilities")
	public void verifyFireFoxDriverInstanciation() {
		dc.setCapability(FirefoxDriver.PROFILE, ffp);
		driver = new FirefoxDriver(dc);
		driver.navigate().to("http://www.kineticskunk.com");
		System.out.println(driver.getClass().getSimpleName());
	}
	
}