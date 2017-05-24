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
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.gson.JsonElement;
import com.kineticskunk.driverfactory.DriverFactory;
import com.kineticskunk.firefox.SetFireFoxProfile;
import com.kineticskunk.utilities.PlatformOperatingSystem;
import com.kineticskunk.utilities.Converter;

public class LoadDesiredCapabilities {

	private final Logger logger = LogManager.getLogger(Thread.currentThread().getName());
	private final Marker LOADDESIREDCAPABILITIES = MarkerManager.getMarker("LOADDESIREDCAPABILITIES");

	private static final String CONFIGLOADSETTINGS = "configloadingsetting";
	
	private static final String COMMONDESIREDCAPABILITIES = "commondesiredcapabilities";
	private static final String LOADFIREFOXPREFERENCES = "loadfirefoxprofilepreferences";
	private static final String FIREFOXDESIREDCAPABILITIES = "firefoxdesiredcapabilities";
	private static final String FIREFOXPROFILEPREFERENCES = "firefoxprofilepreferences";

	private boolean loadfirefoxprofilepreferences = false;

	private JSONParser parser = new JSONParser();
	private JSONObject desiredCapabilitiesJSONObject = null;


	private DesiredCapabilities dc = new DesiredCapabilities();

	public LoadDesiredCapabilities() {
	}

	public LoadDesiredCapabilities(String browserType, String desiredCapabilitiesConfigJSON) {
		this.setDesiredCapabilitiesJSONObject(desiredCapabilitiesConfigJSON);
		if (this.jsonKeyExists(this.desiredCapabilitiesJSONObject, CONFIGLOADSETTINGS)) {
			JSONObject configloadingsetting = (JSONObject) this.desiredCapabilitiesJSONObject.get(CONFIGLOADSETTINGS);
			this.loadfirefoxprofilepreferences = getJSONBooleanValue(configloadingsetting, LOADFIREFOXPREFERENCES);
		}
		this.setBrowerDesiredCapabilities(browserType);
		//TODO add code to handle configloadingsetting
		//this.browserType = browserType;

		/*try {
			this.df.setUseProxy(false);
			this.df.setResizeBrowser(true);
			this.df.setUseRemoteWebDriver(false);
			this.df.setBringDriverToFront(true);
			//this.df = new DriverFactory(this.params);
			//this.wd = df.getDriver();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//this.wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	private Boolean getJSONBooleanValue(JSONObject jsonObject, String key) {
		if (this.jsonKeyExists(jsonObject, key)) {
			return Converter.toBoolean(jsonObject.get(key));
		}
		return false;
	}

	private boolean jsonKeyExists(JSONObject jsonObject, String key) {
		if (jsonObject.containsKey(key)) {
			return true; 
		} else {
			this.logger.error(LOADDESIREDCAPABILITIES, "JSONObject " + (char)34 + jsonObject.toJSONString() + (char)34 + " object doesn't contain key " + (char)34 + key + (char)34);
		}

		return false;
	}

	public void setDesiredCapabilitiesJSONObject(String desiredCapabilitiesConfigJSON) {
		try {
			this.desiredCapabilitiesJSONObject = (JSONObject) this.parser.parse(new FileReader(new File(this.getClass().getClassLoader().getResource(desiredCapabilitiesConfigJSON).getPath())));
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

	public void setDesiredCapabilities(JSONObject jsonObj, String jsonKey) {
		if (this.jsonKeyExists(jsonObj, jsonKey)) {
			this.logger.info(LOADDESIREDCAPABILITIES, "Loading " + (char)34 + jsonKey + (char)34 + " desired capabilities ");
			JSONObject jsonKeyObj = (JSONObject) jsonObj.get(jsonKey);
			Iterator<?> iterator = jsonKeyObj.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<?, ?> entry = (Entry<?, ?>) iterator.next();
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();
				if (Converter.isBoolean(value)) {
					this.dc.setCapability(key, Converter.toBoolean(value));
				} else if (Converter.isNumeric(value)) {
					this.dc.setCapability(key, Converter.toInteger(value));
				} else {
					if (key.equalsIgnoreCase("firefox_binary")) {
						this.dc.setCapability(key, new FirefoxBinary(new File(value.toString())));
					} else {
						this.dc.setCapability(key, value);
					}
				}
				this.logger.info(LOADDESIREDCAPABILITIES, "Loaded desiredCapability " + (char)34 + key + (char)34 + " with value " + (char)34 + this.dc.getCapability(key) + (char)34);
			}
		} else {
			this.logger.info(LOADDESIREDCAPABILITIES, "JSON Key " + (char)34 + jsonKey + (char)34 + " does not exist in " + (char)34 + jsonObj.toJSONString() + (char)34);
		}
	}

	public JSONObject getDesiredCapabilitiesJSONObject() {
		return this.desiredCapabilitiesJSONObject;
	}

	public void setBrowerDesiredCapabilities(String browserType) {
		try {
			if (this.desiredCapabilitiesJSONObject != null) {
				this.setDesiredCapabilities(desiredCapabilitiesJSONObject, COMMONDESIREDCAPABILITIES);
				switch (browserType.toLowerCase()) {
				case "firefox":
					this.setDesiredCapabilities(this.desiredCapabilitiesJSONObject, FIREFOXDESIREDCAPABILITIES);



					if (this.loadfirefoxprofilepreferences) {
						if (this.jsonKeyExists(this.desiredCapabilitiesJSONObject, FIREFOXPROFILEPREFERENCES)) {
							JSONObject firefoxprofilePreferences = (JSONObject) this.desiredCapabilitiesJSONObject.get(FIREFOXPROFILEPREFERENCES);
							this.dc.setCapability(FirefoxDriver.PROFILE, this.getFirefoxProfile(firefoxprofilePreferences));
						} else {
							//TODO add logging info
						}
					} else {
						//TODO add logging info
					}
					break;
				case "chrome":
					//this.setDriverExecutable("webdriver.chrome.driver", browserType);
					//this.dc.setCapability("chromeDriverExecutable", driverExecutable.getAbsolutePath());
					this.dc.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
					this.dc.setCapability("chromeOptions", getChromeOptions());
					this.dc.setCapability("chromePreferences", getChromePreferences()); 
					break;
				case "ie":
					System.setProperty("webdriver.ie.driver", browserType);
					break;
				}		
			} 
		} catch (Exception ex) {

		}
	}

	public DesiredCapabilities getDesiredCapabilities() {
		return this.dc;
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

	public FirefoxBinary getFireFoxBinary(JSONObject firefoxprofilePreferences) {
		File pathToBinary = new File("/Users/yodaqua/ks-test-automation/firefox-lib/Firefox_53.app/Contents/MacOS/firefox-bin");

		return new FirefoxBinary(pathToBinary);
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

/*
 * private FirefoxProfile setFireFoxProfilePreferences(JSONObject firefoxprofilePreferences) {
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
 */ 
