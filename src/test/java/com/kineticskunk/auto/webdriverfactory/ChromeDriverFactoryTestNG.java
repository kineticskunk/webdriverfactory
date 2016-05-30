package com.kineticskunk.auto.webdriverfactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
import com.kineticskunk.driverfactory.DriverFactory;

public class ChromeDriverFactoryTestNG {
	
	private DriverFactory df =  new DriverFactory();
	private WebDriver wd;

	@BeforeClass
	private void beforeDriverFactoryTestNG() throws IOException {
		this.df = new DriverFactory(this.getChromeParams());
		this.df.setUseProxy(false);
		this.df.setUseRemoteWebDriver(false);
		this.df.setBringDriverToFront(true);
		this.df.setResizeBrowser(true);
	}
	
	private HashMap<String, Object> getChromeParams() throws IOException {
		HashMap<String, Object>params = new HashMap<String, Object>();
		params.put("chromeDriverExecutable", "/usr/local/ks-test-automation/webdriver-lib/chromedriver");
		params.put("acceptSslCerts", true);
		params.put("browser", "chrome");
		params.put("version", "any");
		params.put("platform", "any");
		params.put("javascriptEnabled", true);
		params.put("takesScreenshot", true);
		params.put("handlesAlerts", true);
		params.put("databaseEnabled", true);
		params.put("locationContextEnabled", true);
		params.put("applicationCacheEnabled", true);
		params.put("browserConnectionEnabled", true);
		params.put("cssSelectorsEnabled", true);
		params.put("rotatable", false);
		params.put("webStorageEnabled", true);
		params.put("acceptSSLCerts", true);
		params.put("nativeEvents", true);
		params.put("proxy", "UseSystemSettings");
		params.put("unexpectedAlertBehavior", "dismiss");
		params.put("pageLoadingStrategy", "normal");
		params.put("elementScrollBehavior", 0);
		params.put("loggingPrefs", "all");
		params.put("chrome.switches", Arrays.asList("--no-default-browser-check"));
		params.put("chromeOptions", getChromeOptions());
		params.put("chromePreferences", getChromePreferences());
		return params;
	}
	
	private ChromeOptions getChromeOptions() {
		ChromeOptions co = new ChromeOptions();
		co.addArguments("start-maximized");
		co.addArguments("ignore-certificate-errors");
		co.addArguments("--new-window");
		return co;
	}
	
	private HashMap<String, Object> getChromePreferences() {
		HashMap<String, Object> chromePreferences = new HashMap<String, Object>();
		chromePreferences.put("profile.password_manager_enabled", "false");
		return chromePreferences;
	}

	@BeforeGroups(groups = "KineticSkunk")
	public void beforeKineticSkunk() throws Exception {
		this.wd = df.getDriver();
		
		
		
		
		this.wd.manage().deleteAllCookies();
		this.wd.navigate().to("http://kineticskunk.com/");
		
		
	}
	
	@Test(groups = "KineticSkunk")
	public void verifyTitle() {
		Assert.assertTrue(this.wd.getTitle().toLowerCase().equals("home"));
	}
	
	@AfterClass
	public void afterFireFoxDriverFactoryTestNG() {
		this.wd.close();
	}

}
