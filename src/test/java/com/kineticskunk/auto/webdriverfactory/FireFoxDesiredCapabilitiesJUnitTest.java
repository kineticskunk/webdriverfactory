package com.kineticskunk.auto.webdriverfactory;

import java.io.IOException;
import java.util.Hashtable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

public class FireFoxDesiredCapabilitiesJUnitTest {
	
	private DesiredCapabilities dc;
	private FireFoxDesiredCapabilities ffdc;
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
		ffdc = new FireFoxDesiredCapabilities(dcConfig, true);
	}
	
	@Test
	public void verifyDesiredCapabiliesObject() {
		dc = ffdc.getDesiredCapabilities();
		System.out.println(dc.toString());
	}
	
	@Test
	public void checkIfFireFoxPropertiesLoad() {
		System.out.println("In checkIfFireFoxPropertiesLoad");
		System.out.println("Size = " + dcConfig.size());
		Assert.assertTrue(dcConfig.size() > 0);
	}
	
}