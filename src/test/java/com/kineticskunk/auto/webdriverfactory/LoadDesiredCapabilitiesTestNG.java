package com.kineticskunk.auto.webdriverfactory;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.kineticskunk.basetests.TestBaseSetup;
import com.kineticskunk.desiredcapabilities.LoadDesiredCapabilities;

public class LoadDesiredCapabilitiesTestNG extends TestBaseSetup {
	
	private LoadDesiredCapabilities ldc = new LoadDesiredCapabilities();
	
	@BeforeClass
	@Parameters( { "browserType", "desiredCapabilitiesConfigJSON" } )
	public void setLoadDesiredCapabilities(String browserType, String desiredCapabilitiesConfigJSON) {
		//this.ldc = new LoadDesiredCapabilities(browserType, desiredCapabilitiesConfigJSON);
	}
	
	@Test(priority = 0, groups = "LoadDesiredCapabilities")
	public void verifyDesiredCapabiltiesJSONFileISNOTNULL() {
		//Assert.assertTrue(this.ldc.getDesiredCapabilitiesJSONObject() != null);
	}
	
	@Test(priority = 0, groups = "LoadDesiredCapabilities")
	@Parameters( { "browserType" })
	public void verifyBrowserType(String browserType) {
	//	Assert.assertTrue(this.ldc.getDesiredCapabilitiesJSONObject() != null);
	}
	
	@AfterGroups(groups = "LoadDesiredCapabilities")
	public void afterLoadDesiredCapabilities() {
		getDriver().get("https://www.kineticskunk.com");
	}
	
	@Test(priority = 0, groups = "DriverFactory")
	public void verifyFireFoxBrowserOpening() {
		
	}

}
