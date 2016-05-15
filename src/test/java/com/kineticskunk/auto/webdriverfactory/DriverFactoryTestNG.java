package com.kineticskunk.auto.webdriverfactory;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import com.kineticskunk.driverfactory.DriverFactory;

public class DriverFactoryTestNG {

	private HashMap<String, Object> params;
	private DriverFactory df;
	private WebDriver wd;
	
	@BeforeClass
	@Parameters("firefoxProfilePreferences")
	private void beforeDriverFactoryTestNG(String firefoxProfilePreferences) {
		this.params = new HashMap<String, Object>();
		this.params.put("chromedriver", "/usr/local/ks-test-automation/webdriver-lib/chromedriver");
		this.params.put("Browser", "FIREFOX");
		this.params.put("useRemoteWebDriver", false);
		this.params.put("proxyEnabled", false);
		this.params.put("proxyHost", "");
		this.params.put("proxyPort", "");
		this.params.put("proxyDetails", "");
		this.params.put("addProfile", "true");
		this.params.put("profileName", firefoxProfilePreferences);
		this.df = new DriverFactory(this.params);
	}

	@BeforeGroups(groups = "KineticSkunk")
	public void beforeKineticSkunk() throws Exception {
		this.wd = df.getDriver();
		this.wd.navigate().to("http://kineticskunk.com/");
	}

	@Test(groups = "KineticSkunk")
	public void verifyTitle() {
		Assert.assertTrue(this.wd.getTitle().toLowerCase().equals("home"));
	}

}
