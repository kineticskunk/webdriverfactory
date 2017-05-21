package com.kineticskunk.basetests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterClass;
import java.io.File;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import com.kineticskunk.driverfactory.DriverFactory;
import com.kineticskunk.firefox.SetFireFoxProfile;
import com.kineticskunk.utilities.ApplicationProperties;
import com.kineticskunk.utilities.PlatformOperatingSystem;

import com.sun.jna.Library;
import com.sun.jna.Native;

interface CLibrary extends Library {
	public int chmod(String path, int mode);
}

public class TestBaseSetup {

	private static final Logger logger = LogManager.getLogger(Thread.currentThread().getName());
	private static final Marker TESTBASESETUP = MarkerManager.getMarker("TESTBASESETUP");

	private WebDriver wd;
	private ApplicationProperties ap = new ApplicationProperties();
	private HashMap<String, Object> params = new HashMap<String, Object>();
	private DriverFactory df =  new DriverFactory();
	private PlatformOperatingSystem pos = new PlatformOperatingSystem();

	private static TestBaseSetup tbs;

	public static TestBaseSetup getInstance() throws UnknownHostException {
		if (tbs == null ) {
			synchronized (TestBaseSetup.class) {
				if (tbs == null) {
					tbs = new TestBaseSetup();
				}
			}
		}
		return tbs;
	}
	
	public TestBaseSetup() throws UnknownHostException{
		this.df.setUseProxy(false);
		this.df.setUseRemoteWebDriver(false);
		this.df.setBringDriverToFront(true);
		this.df.setResizeBrowser(true);
	}

	@Parameters({ "browserType" })
	@BeforeClass
	public void setDriver(String browserType) {
		switch (browserType.toLowerCase()) {
		case "chrome":
			this.getLogger().info("-------------***LAUNCHING GOOGLE CHROME***--------------");
			try {
				this.loadWebDriverProperties("chromedesiredcapabilities.properties");
			} catch (Exception e) {
				this.getLogger().debug(TESTBASESETUP, "An error occurred while attempting to load the Chromedriver");
				this.getLogger().error(e.getLocalizedMessage());
			}
			break;
		case "firefox":
			this.getLogger().info("-------------***LAUNCHING MOZILLA FIREFOX***--------------");
			try {
				this.loadWebDriverProperties("firefoxdesiredcapabilities.properties");
				this.loadWebDriverProfilePreference("firefoxprofile.properties");
			} catch (Exception e) {
				this.getLogger().fatal("An error occurred while attempting to load the Geckodriver");
				this.getLogger().error(e.getLocalizedMessage());
			}
			break;
		default:
			this.getLogger().fatal("Brower '" + browserType + "' is unsupported");
			break;
		}
		
		try {
			this.df.setUseProxy(false);
			this.df.setResizeBrowser(true);
			this.df.setUseRemoteWebDriver(false);
			this.df.setBringDriverToFront(true);
			this.df = new DriverFactory(this.params);
			this.wd = df.getDriver();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	private void loadWebDriverProperties(String propertiesFileName) {
		try {
			this.params = this.ap.readResourcePropertyFile(propertiesFileName);
			if (pos.isMac() && System.getProperty("os.arch").contains("64") && (this.params.get("driverType").toString()).equalsIgnoreCase("chromedriver")) {
				this.params = ap.readResourcePropertyFile("chromedesiredcapabilities.properties");
				File f = new File(this.getClass().getClassLoader().getResource("chromedrivermac64").getPath());
				this.params.put("chromeDriverExecutable", f.getAbsolutePath());
				System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
				this.params.put("chrome.switches", Arrays.asList("--no-default-browser-check"));
				this.params.put("chromeOptions", getChromeOptions());
				this.params.put("chromePreferences", getChromePreferences()); 
				this.makeDriverExecutable(f.getAbsolutePath());
			}
			if (pos.isMac() && System.getProperty("os.arch").contains("64") && this.params.get("driverType").toString().equalsIgnoreCase("geckodriver")) {
				this.params = ap.readResourcePropertyFile("firefoxdesiredcapabilities.properties");
				
				File f = new File(this.getClass().getClassLoader().getResource("geckodrivermac64").getPath());
				System.setProperty("webdriver.gecko.driver", f.getAbsolutePath());
				this.params.put("geckoDriverExecutable", f.getAbsolutePath());
				this.makeDriverExecutable(f.getAbsolutePath());
			}

		} catch (Exception e) {
			getLogger().fatal("An error occurred while attempting to load the FireFox browser");
			getLogger().error(e.getLocalizedMessage());
		}
	}
	
	private void loadWebDriverProfilePreference(String profilePreferences) {
		try {
			SetFireFoxProfile p = new SetFireFoxProfile();
			p.setPreferences(ap.readResourcePropertyFile(profilePreferences));
			p.setFirefoxProfile();
			this.params.put("profilePreferences", p.getFirefoxProfile());
		} catch (Exception e) {
			getLogger().fatal("An error occurred while attempting to load the FireFox browser");
			getLogger().error(e.getLocalizedMessage());
		}
	}
	
	private void makeDriverExecutable(String driverFile) {
		CLibrary libc = (CLibrary) Native.loadLibrary("c", CLibrary.class);
		libc.chmod(driverFile, 0755);
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

	public WebDriver getDriver() {
		return wd;
	}

	public void navigateToURL(String url) {
		this.wd.navigate().to(url);
	}

	@AfterClass
	public void quitDriver() {
		wd.quit();
	}

	public Logger getLogger() {
		return logger;
	}
}