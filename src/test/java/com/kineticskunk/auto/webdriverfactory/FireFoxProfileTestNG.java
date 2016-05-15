package com.kineticskunk.auto.webdriverfactory;

import java.io.IOException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.kineticskunk.firefox.SetFireFoxProfile;
import com.kineticskunk.utilities.ApplicationProperties;

public class FireFoxProfileTestNG {
	
	private ApplicationProperties ap;
	private HashMap<String, Object> params;
	private SetFireFoxProfile sffp;
	
	public FireFoxProfileTestNG() {
		ap = ApplicationProperties.getInstance();
		params = new HashMap<String, Object>();
	}
	
	@BeforeClass
	public void beforeFireFoxProfileTestNG() throws IOException {
		params = ap.readPropertyFile(params, "webdriver-firefox.properties");
		sffp = new SetFireFoxProfile();
		sffp.setFirefoxProfile(params);
	}
	
	@Test
	public void test() {
		Assert.assertTrue(true);
	}

}
