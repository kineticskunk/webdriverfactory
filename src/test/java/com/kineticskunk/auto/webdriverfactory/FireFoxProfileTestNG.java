package com.kineticskunk.auto.webdriverfactory;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.kineticskunk.firefox.SetFireFoxProfile;


public class FireFoxProfileTestNG {
	
	private SetFireFoxProfile sffp;
	
	public FireFoxProfileTestNG() {
	}
	
	@BeforeClass
	@Parameters("firefoxProfilePreferences")
	public void beforeFireFoxProfileTestNG(String firefoxProfilePreferences) throws IOException {
		sffp = new SetFireFoxProfile();
		sffp.setPreferences(firefoxProfilePreferences);
		sffp.setFirefoxProfile();
	}
	
	@Test
	public void test() {
		Assert.assertTrue(true);
	}

}
