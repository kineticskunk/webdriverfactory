package com.kineticskunk.auto.webdriverfactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.kineticskunk.basetests.TestBaseSetup;

public class DriverFactoryTestNG extends TestBaseSetup {

	private WebDriver wd;
	
	@BeforeClass
	@Parameters({ "url" })
	public void beforeDriverFactoryTestNG(String url) {
		navigateToURL(url);
	}
	
	@Test(priority = 1, groups = "BrowserVerification")
	@Parameters( { "browserTitle" })
	public void verifyBrowserType(String browserTitle) {
		Assert.assertTrue(getDriver().getTitle().equalsIgnoreCase(browserTitle));
	}
	
	@AfterClass
	public void afterFireFoxDriverFactoryTestNG() {
		//this.wd.close();
	}
}
