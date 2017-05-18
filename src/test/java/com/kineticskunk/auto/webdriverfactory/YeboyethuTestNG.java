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
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import com.kineticskunk.driverfactory.DriverFactory;
import com.kineticskunk.firefox.SetFireFoxProfile;

public class YeboyethuTestNG {

	private DriverFactory df =  new DriverFactory();
	private WebDriver wd;

	@BeforeClass
	@Parameters({"desiredCapabilites", "profilePreferences", "firebugPreferences"})
	private void beforeDriverFactoryTestNG(String desiredCapabilites,  String profilePreferences, String firebugPreferences) throws IOException {
		this.df = new DriverFactory(this.getFireFoxDesiredCapabilitiesParams());
		this.df.setUseProxy(false);
		this.df.setUseRemoteWebDriver(false);
	}

	private HashMap<String, Object> getFireFoxDesiredCapabilitiesParams() throws IOException {
		HashMap<String, Object>params = new HashMap<String, Object>();
		params.put("acceptSslCerts", true);
		params.put("browser", "firefox");
		params.put("version", "45.0.2");
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
		params.put("firefox_binary", "/Users/yodaqua/ks-test-automation/firefox-lib/Firefox_49.app/Contents/MacOS/firefox-bin");
		params.put("profilePreferences", getFireFoxProfile(true));
		return params;
	}

	private FirefoxProfile getFireFoxProfile(boolean loadFireBug) throws IOException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("accept.untrusted.certificates", true);
		params.put("always.load.no.focus.lib", true);
		params.put("assume.untrusted.certificate.issuer", true);
		params.put("browser.cache.disk.enable", true);
		params.put("browser.download.dir", "/usr/local/ks-test-automation/download-lib/");
		params.put("browser.download.folderList", 2);
		params.put("browser.download.manager.alertOnEXEOpen", false);
		params.put("browser.download.manager.closeWhenDone", false);
		params.put("browser.download.manager.focusWhenStarting", false);
		params.put("browser.download.manager.showAlertOnComplete", false);
		params.put("browser.download.manager.showWhenStarting", false);
		params.put("browser.download.manager.useWindow", false);
		params.put("browser.helperApps.alwaysAsk.force", false);
		params.put("browser.helperApps.neverAsk.openFile", "text/csv,application/excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,application/zip");
		params.put("browser.helperApps.neverAsk.saveToDisk", "text/csv,application/excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,application/zip");
		params.put("enable.native.events", true);
		SetFireFoxProfile p = new SetFireFoxProfile();
		if (loadFireBug) {
			p.addFireFoxExtension("/Users/yodaqua/Library/Application Support/Firefox/Profiles/24nxve48.default/extensions/", "firebug@software.joehewitt.com.xpi");
			params.put("extensions.firebug.currentVersion", "2.0.16");
			params.put("extensions.firebug.console.enableSites", true);
			params.put("extensions.firebug.script.enableSites", true);
			params.put("extensions.firebug.defaultPanelName", "HTML");
			params.put("extensions.firebug.net.enableSites", true);
			params.put("extensions.firebug.allPagesActivation", "on");
			params.put("extensions.firebug.cookies.enableSites", true);
			
		}
		p.setPreferences(params);
		p.setFirefoxProfile();
		return p.getFirefoxProfile();
	}


	@BeforeGroups(groups = "KineticSkunk")
	public void beforeKineticSkunk() throws Exception {
		this.wd = df.getDriver();
		this.wd.manage().deleteAllCookies();
		this.wd.navigate().to("https://yeboyethushares.uat.tradedesk.co.za/VTBBEE/Index");
		
		for(String winHandle : this.wd.getWindowHandles()){
			System.out.println(this.wd.switchTo().window(winHandle).getCurrentUrl());
        }
		
		//this.wd.get("https://ajax.aspnetcdn.com/ajax/jQuery/jquery-2.1.4.min.js");
		
		WebElement we = this.wd.findElement(By.id("forWATCHLIST"));
		List<WebElement> lwe = we.findElements(By.tagName("div"));
		for (int i = 0; i <= lwe.size() - 1; i++) {
			System.out.println(lwe.get(i).getText());
		}
		
		Set<Cookie> cookies = this.wd.manage().getCookies();
		Iterator<Cookie> itr = cookies.iterator();

	    while (itr.hasNext()){
	    Cookie c = itr.next();
	    System.out.println("Cookie Name: " + c.getName() + " --- " + "Cookie Domain: " + c.getDomain() + " --- " + "Cookie Value: " + c.getValue());
	    }
		
		
	}

	@Test(groups = "KineticSkunk")
	public void verifyTitle() {
		Assert.assertTrue(this.wd.getTitle().toLowerCase().equals("home"));
	}
	
	@AfterClass
	public void afterFireFoxDriverFactoryTestNG() {
		//this.wd.close();
	}
}
