package com.kineticskunk.auto.webdriverfactory;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.kineticskunk.desiredcapabilities.LoadDesiredCapabilities;

public class LoadDesiredCapabilitiesTestNG {
	
	private LoadDesiredCapabilities ldc = new LoadDesiredCapabilities();
	
	@BeforeClass
	@Parameters( { "browserType", "desiredCapabilities" } )
	public void setLoadDesiredCapabilities(String browserType, String desiredCapabilities) {
		ldc.setDesiredCapabilitiesJSONObject(desiredCapabilities);
		ldc.setDesiredCapabilities(browserType);
	}
	
	@Test(groups = "KineticSkunk")
	public void verifyDesiredCapabiltiesJSONFileISNOTNULL() {
		Assert.assertTrue(this.ldc.getDesiredCapabilitiesJSONObject() != null);
	}
	
	

}
