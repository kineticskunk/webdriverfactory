package com.kineticskunk.auto.webdriverfactory;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import com.kineticskunk.basetests.TestBaseSetup;


public class ChromeDriverFactoryTestNG extends TestBaseSetup {
	
	public ChromeDriverFactoryTestNG() {
		super();
	}

	private WebDriver wd;

	@BeforeClass
	private void beforeChromeDriverFactoryTestNG() {
	}
	
	@BeforeGroups(groups = "KineticSkunk")
	public void beforeKineticSkunk() throws Exception {
		this.wd = getDriver();
		this.wd.manage().deleteAllCookies();
		this.wd.navigate().to("http://www.kineticskunk.com/");
	}
	
	@Test(groups = "KineticSkunk")
	public void verifyTitle() {
		Assert.assertTrue(this.wd.getTitle().toLowerCase().equals("home"));
	}
	
	@AfterClass
	public void afterChromeDriverFactoryTestNG() {
		this.wd.close();
	}

}
