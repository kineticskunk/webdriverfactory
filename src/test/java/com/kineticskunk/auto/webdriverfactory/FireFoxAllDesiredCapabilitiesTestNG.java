package com.kineticskunk.auto.webdriverfactory;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
	@Parameters ( {"desiredCapabilitiesConfigurationFile", "profileConfigurationFile"} )
	public static void setupTestClass(String desiredCapabilitiesConfigurationFile,  String profileConfigurationFile) throws IOException, DesiredCapabilityException {
		dcConfig = utils.readPropertyFileIntoHashtable(dcConfig, desiredCapabilitiesConfigurationFile);
		ffConfig = utils.readPropertyFileIntoHashtable(ffConfig, profileConfigurationFile);
		ffdc = new FireFoxDesiredCapabilities(dcConfig);
		ffp = new FireFoxProfile(ffConfig);
		ffdc.setFireFoxDesiredCapabilities();
		dc = ffdc.getFireFoxDesiredCapabilities();
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void checkIfFireFoxPropertiesLoad() {
		Assert.assertTrue(dcConfig.size() > 0);
	}
	
	@Parameters( "url" )
	@Test (priority = 3, groups = "desiredcapabilities")
	public void verifyFireFoxDriverInstanciation(String url) {
		dc.setCapability(FirefoxDriver.PROFILE, ffp);
		driver = new FirefoxDriver(dc);
		driver.navigate().to(url);
		System.out.println(driver.getClass().getSimpleName());
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	
}