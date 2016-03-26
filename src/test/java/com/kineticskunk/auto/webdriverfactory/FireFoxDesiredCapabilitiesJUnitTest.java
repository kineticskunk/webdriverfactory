package com.kineticskunk.auto.webdriverfactory;

import java.io.IOException;
import java.util.Hashtable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FireFoxDesiredCapabilitiesJUnitTest {
	
	private Hashtable<String, String> dcConfig;
	private Utilties utils;
	
	public FireFoxDesiredCapabilitiesJUnitTest() {
		dcConfig = new Hashtable<String, String>();
		utils = new Utilties();
	}
	
	@Before
	public void setupTestClass() throws IOException {
		dcConfig = utils.readPropertyFileIntoHashtable(dcConfig, "webdriver-firefox.properties");
	}

	@Test
	public void checkIfFireFoxPropertiesLoad() {
		System.out.println("In checkIfFireFoxPropertiesLoad");
		System.out.println("Size = " + dcConfig.size());
		Assert.assertTrue(dcConfig.size() > 0);
	}
	
	

}
