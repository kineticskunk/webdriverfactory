package com.kineticskunk.auto.webdriverfactory;

import java.io.IOException;
import java.util.Hashtable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class FireFoxDesiredCapabilitiesJUnitTest {
	
	private DesiredCapabilities dc;
	private FireFoxDesiredCapabilities ffdc;
	private WebDriver driver;
	private Hashtable<String, String> dcConfig;
	private Utilties utils;
	
	public FireFoxDesiredCapabilitiesJUnitTest() {
		dc = null;
		ffdc = null;
		dcConfig = new Hashtable<String, String>();
		utils = new Utilties();
	}
	
	@Before
	public void setupTestClass() throws IOException {
		dcConfig = utils.readPropertyFileIntoHashtable(dcConfig, "webdriver-firefox-desired-capabilities.properties");
		ffdc = new FireFoxDesiredCapabilities(true, dcConfig);
		ffdc.setFireFoxDesiredCapabilities();
	}
	
	@Test
	public void checkIfFireFoxPropertiesLoad() {
		Assert.assertTrue(dcConfig.size() > 0);
	}
	
	@Test
	public void verifyDesiredCapabiliesObjectInstanciation() {
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getPlatform().is(Platform.MAC));
		System.out.println(dc.getClass().getSimpleName());
		System.out.println(dc.getBrowserName());
		System.out.println(dc.getVersion());
		System.out.println(dc.getPlatform());
		System.out.println(System.getProperty("os.version"));
	}
	
	
	
	@Test
	public void verifyFireFoxDriverInstanciation() {
		driver = new FirefoxDriver(this.dc);
		driver.navigate().to("http://www.kineticskunk.com");
		System.out.println(driver.getClass().getSimpleName());
	}
	
	
	
}