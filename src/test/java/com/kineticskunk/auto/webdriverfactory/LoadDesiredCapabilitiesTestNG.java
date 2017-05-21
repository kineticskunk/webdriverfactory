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
	public void setLoadDesiredCapabilities(String browserType, String desiredCapabilitiesConfigJSON) {
		ldc.setDesiredCapabilitiesJSONObject(desiredCapabilitiesConfigJSON);
		ldc.setDesiredCapabilities(browserType);
	}
	
	@Test(groups = "KineticSkunk")
	public void verifyDesiredCapabiltiesJSONFileISNOTNULL() {
		Assert.assertTrue(this.ldc.getDesiredCapabilitiesJSONObject() != null);
	}
	
	@Test(groups = "KineticSkunk")
	@Parameters( { "browserType" })
	public void verifyBrowserType(String browserType) {
		Assert.assertTrue(this.ldc.getDesiredCapabilitiesJSONObject() != null);
	}

}
