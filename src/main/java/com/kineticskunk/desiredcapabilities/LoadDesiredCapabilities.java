package com.kineticskunk.desiredcapabilities;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.gson.JsonElement;
import com.kineticskunk.driverfactory.DriverFactory;
import com.kineticskunk.firefox.SetFireFoxProfile;
import com.kineticskunk.utilities.PlatformOperatingSystem;
import com.kineticskunk.utilities.Converter;
import com.sun.jna.Native;

import com.sun.jna.Library;
import com.sun.jna.Native;

interface CLibrary extends Library {
	public int chmod(String path, int mode);
}

public class LoadDesiredCapabilities {

	private final Logger logger = LogManager.getLogger(Thread.currentThread().getName());
	private final Marker LOADDESIREDCAPABILITIES = MarkerManager.getMarker("LOADDESIREDCAPABILITIES");

	private JSONParser parser = new JSONParser();
	private JSONObject desiredCapabilitiesJSONObject = null;


	private DesiredCapabilities dc = new DesiredCapabilities();
	private String browserType = null;

	private PlatformOperatingSystem pos = new PlatformOperatingSystem();
	private DriverFactory df =  new DriverFactory();

	public LoadDesiredCapabilities() {
	}

	public LoadDesiredCapabilities(String browserType, String resourceFile) {
		this.browserType = browserType;

		try {
			this.df.setUseProxy(false);
			this.df.setResizeBrowser(true);
			this.df.setUseRemoteWebDriver(false);
			this.df.setBringDriverToFront(true);
			//this.df = new DriverFactory(this.params);
			//this.wd = df.getDriver();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//this.wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	public void setDesiredCapabilitiesJSONObject(String desiredCapabilitiesJSONFile) {
		try {
			this.desiredCapabilitiesJSONObject = (JSONObject) this.parser.parse(new FileReader(new File(this.getClass().getClassLoader().getResource(desiredCapabilitiesJSONFile).getPath())));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public JSONObject getDesiredCapabilitiesJSONObject() {
		return this.desiredCapabilitiesJSONObject;
	}

	public void setDesiredCapabilities(String browserType) {
		try {
			this.dc.setBrowserName(browserType);
			if (this.desiredCapabilitiesJSONObject != null) {
				Iterator<?> iterator = this.desiredCapabilitiesJSONObject.entrySet().iterator();
				switch (browserType.toLowerCase()) {
				case "firefox":
					while (iterator.hasNext()) {
						Entry<?, ?> entry = (Entry<?, ?>) iterator.next();
						String key = entry.getKey().toString();
						String value = entry.getValue().toString();
						if (key.equalsIgnoreCase("firefoxprofilepreferences")) {
							JSONObject firefoxprofilePreferences = (JSONObject) this.desiredCapabilitiesJSONObject.get("firefoxprofilepreferences");
							this.dc.setCapability(FirefoxDriver.PROFILE, this.getFirefoxProfile(firefoxprofilePreferences));
						} else {
							this.dc.setCapability(key, value);
							logger.info(LOADDESIREDCAPABILITIES, "Loaded desiredCapability " + key + " = " + this.dc.getCapability(key));
						}
					}
					this.setDriverExecutable("webdriver.gecko.driver", browserType);
					break;
				case "chrome":

					break;
				}		
			} 
		} catch (Exception ex) {
			//FileNotFoundException
		}
	}
	
	
	
	private FirefoxProfile getFirefoxProfile(JSONObject firefoxprofilePreferences) {
		FirefoxProfile profile = new FirefoxProfile();
		Iterator<?> profilePreferenceIterator = firefoxprofilePreferences.entrySet().iterator();
		while (profilePreferenceIterator.hasNext()) {
			Entry<?, ?> profileEntry = (Entry<?, ?>) profilePreferenceIterator.next();
			String profilePreferenceKey = profileEntry.getKey().toString();
			String profilePreferenceValue = profileEntry.getValue().toString();
			if (Converter.isBoolean(profilePreferenceValue)) {
				profile.setPreference(profilePreferenceKey, Boolean.parseBoolean(profilePreferenceValue));
				logger.info(LOADDESIREDCAPABILITIES, "Loaded FireFox Profile Preference " + profilePreferenceKey + " = " + profile.getBooleanPreference(profilePreferenceKey, false));
			} else if (Converter.isNumeric(profilePreferenceValue)) {
				profile.setPreference(profilePreferenceKey, Converter.toInteger(profilePreferenceValue));
				logger.info(LOADDESIREDCAPABILITIES, "Loaded FireFox Profile Preference " + profilePreferenceKey + " = " + profile.getIntegerPreference(profilePreferenceKey, 0));
			} else {
				profile.setPreference(profilePreferenceKey, profilePreferenceValue);
				logger.info(LOADDESIREDCAPABILITIES, "Loaded FireFox Profile Preference " + profilePreferenceKey + " = " + profile.getStringPreference(profilePreferenceKey, ""));
			}	
		}
		return profile;
	}
	
	private void setDriverExecutable(String driverPropertyName, String browserType) {
		if (pos.isMac() && System.getProperty("os.arch").contains("64") && (this.browserType.equalsIgnoreCase("chrome"))) {

			File f = new File(this.getClass().getClassLoader().getResource("chromedrivermac64").getPath());
			this.dc.setCapability("chromeDriverExecutable", f.getAbsolutePath());
			System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
			this.dc.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
			this.dc.setCapability("chromeOptions", getChromeOptions());
			this.dc.setCapability("chromePreferences", getChromePreferences()); 
			this.makeDriverExecutable(f.getAbsolutePath());
		}
		if (pos.isMac() && System.getProperty("os.arch").contains("64") && this.browserType.equalsIgnoreCase("firefox")) {
			File f = new File(this.getClass().getClassLoader().getResource("geckodrivermac64").getPath());
			System.setProperty("webdriver.gecko.driver", f.getAbsolutePath());
			this.makeDriverExecutable(f.getAbsolutePath());
		}
		
		File f = new File(this.getClass().getClassLoader().getResource("geckodrivermac64").getPath());
		System.setProperty("webdriver.gecko.driver", f.getAbsolutePath());
		this.makeDriverExecutable(f.getAbsolutePath());
	}

	public void loadSpecificWebDriverProperties(String propertiesFileName) {
		try {

			if (pos.isMac() && System.getProperty("os.arch").contains("64") && (this.browserType.equalsIgnoreCase("chrome"))) {

				File f = new File(this.getClass().getClassLoader().getResource("chromedrivermac64").getPath());
				this.dc.setCapability("chromeDriverExecutable", f.getAbsolutePath());
				System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
				this.dc.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
				this.dc.setCapability("chromeOptions", getChromeOptions());
				this.dc.setCapability("chromePreferences", getChromePreferences()); 
				this.makeDriverExecutable(f.getAbsolutePath());
			}
			if (pos.isMac() && System.getProperty("os.arch").contains("64") && this.browserType.equalsIgnoreCase("firefox")) {
				File f = new File(this.getClass().getClassLoader().getResource("geckodrivermac64").getPath());
				System.setProperty("webdriver.gecko.driver", f.getAbsolutePath());
				this.makeDriverExecutable(f.getAbsolutePath());
			}

		} catch (Exception e) {
			//getLogger().fatal("An error occurred while attempting to load the FireFox browser");
			//getLogger().error(e.getLocalizedMessage());
		}
	}

	public void loadWebDriverProfilePreference(String profilePreferences) {
		try {
			SetFireFoxProfile p = new SetFireFoxProfile();
			//	p.setPreferences(ap.readResourcePropertyFile(profilePreferences));
			p.setFirefoxProfile();
			//this.params.put("profilePreferences", p.getFirefoxProfile());
		} catch (Exception e) {
			//getLogger().fatal("An error occurred while attempting to load the FireFox browser");
			//getLogger().error(e.getLocalizedMessage());
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



}
