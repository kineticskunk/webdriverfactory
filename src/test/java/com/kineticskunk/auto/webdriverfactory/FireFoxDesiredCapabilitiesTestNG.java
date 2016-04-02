package com.kineticskunk.auto.webdriverfactory;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kineticskunk.auto.webdriverfactory.DesiredCapabilityException;

public class FireFoxDesiredCapabilitiesTestNG {
	
	private static DesiredCapabilities dc;
	private static FireFoxDesiredCapabilities ffdc;
	private static WebDriver driver;
	private static Hashtable<String, String> dcConfig;
	private static Utilties utils;
	
	public FireFoxDesiredCapabilitiesTestNG() {
		dc = null;
		ffdc = null;
		dcConfig = new Hashtable<String, String>();
		
	}
	
	@BeforeClass
	public static void setupTestClass() throws IOException, DesiredCapabilityException {
		utils = Utilties.getInstance();
		dcConfig = utils.readPropertyFileIntoHashtable(dcConfig, "webdriver-firefox-desired-capabilities.properties");
		ffdc = new FireFoxDesiredCapabilities(true, dcConfig);
		ffdc.setFireFoxDesiredCapabilities();
		dc = ffdc.getFireFoxDesiredCapabilities();
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void checkIfFireFoxPropertiesLoad() {
		AssertJUnit.assertTrue(dcConfig.size() > 0);
	}
	
	@Test (priority = 1, groups = "desiredcapabilities")
	public void verifyPlaform() {
		AssertJUnit.assertTrue(dc.getPlatform().is(Platform.MAC));
	}
	
	@Test (priority = 2, groups = "desiredcapabilities")
	public void verifyDesiredCapabiliesObjectInstanciation() {
		AssertJUnit.assertTrue(dc.getPlatform().is(Platform.MAC));
		System.out.println(dc.getClass().getSimpleName());
		System.out.println(dc.getBrowserName());
		System.out.println(dc.getVersion());
		System.out.println(dc.getPlatform());
		System.out.println(System.getProperty("os.version"));
	}
	
	
	
	@Test (priority = 3, groups = "desiredcapabilities")
	public void verifyFireFoxDriverInstanciation() {
		driver = new FirefoxDriver(dc);
		driver.navigate().to("http://www.kineticskunk.com");
		System.out.println(driver.getClass().getSimpleName());
	}
	
	
	
}