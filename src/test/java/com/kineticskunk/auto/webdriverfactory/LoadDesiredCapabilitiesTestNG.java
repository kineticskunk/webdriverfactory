package com.kineticskunk.auto.webdriverfactory;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.kineticskunk.desiredcapabilities.LoadDesiredCapabilities;
import com.kineticskunk.driverfactory.DriverExecutable;

public class LoadDesiredCapabilitiesTestNG {
	
	private static final String testSite = "https://enterprise-demo.orangehrmlive.com/";
	private static final String testSiteTitle = "OrangeHRM";
	
	private LoadDesiredCapabilities ldc = new LoadDesiredCapabilities();
	private WebDriver wd;
	private DriverExecutable de;
	
	@BeforeClass
	@Parameters( { "browserType", "desiredCapabilitiesConfigJSON" } )
	public void setLoadDesiredCapabilities(String browserType, String desiredCapabilitiesConfigJSON) {
		this.ldc = new LoadDesiredCapabilities(browserType, desiredCapabilitiesConfigJSON);
		this.ldc.setBrowerDesiredCapabilities();
		this.de = new DriverExecutable(browserType);
		this.de.setDriverExecutable();
	}
	
	@Test(priority = 0, groups = "LoadDesiredCapabilities")
	public void verifyDesiredCapabiltiesJSONFileISNOTNULL() {
		Assert.assertTrue(this.ldc.getDesiredCapabilitiesJSONObject() != null);
	}
	
	@Test(priority = 0, groups = "LoadDesiredCapabilities")
	@Parameters( { "browserType" })
	public void verifyBrowserType(String browserType) {
		Assert.assertTrue(this.ldc.getDesiredCapabilities().getBrowserName().equalsIgnoreCase(browserType));
	}
	
	@AfterGroups(groups = "LoadDesiredCapabilities")
	@Parameters( { "browserType" })
	public void afterLoadDesiredCapabilities(String browserType) {
		switch (browserType.toLowerCase()) {
		case "firefox":
			this.wd = new FirefoxDriver(this.ldc.getDesiredCapabilities());
			break;
		case "chrome":
			this.wd = new ChromeDriver(this.ldc.getDesiredCapabilities());
			break;
		case "opera":
			this.wd = new OperaDriver(this.ldc.getDesiredCapabilities());
			break;
		}
		this.wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		this.wd.get(testSite);
	}
	
	@Test(priority = 0, groups = "DriverFactory")
	public void verifyBrowserOpening() {
		Assert.assertTrue(this.wd.getTitle().equalsIgnoreCase(testSiteTitle));
	}
	
	@AfterClass()
	public void afterLoadDesiredCapabilitiesTestNG() {
		this.wd.close();
		this.wd.quit();
	}
}
