package com.kineticskunk.firefox;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kineticskunk.utilities.Converter;

public class LoadFireFoxProfilePreferences {
	
	private final Logger logger = LogManager.getLogger(SetFireFoxProfile.class.getName());
	private final Marker LOADFIREFOXPROFILEPREFERENCES = MarkerManager.getMarker("LOADFIREFOXPROFILEPREFERENCES");
	
	private static final String FIREFOXEXTENSIONS = "firefoxextensions";
	private static final String EXTENSION = "extension";
	private static final String FIREBUGPREFERENCES = "firebugpreferences";
	
	private JSONObject profilePreferences;
	private FirefoxProfile profile;
	private boolean loadExtensions;
	
	public LoadFireFoxProfilePreferences() {
	}
	
	public LoadFireFoxProfilePreferences(JSONObject profilePreferences) {
		this();
		this.profilePreferences = profilePreferences;
	}
	
	public void setAcceptUntrustedCertificates(boolean acceptUntrustedCertificates) {
		this.profile.setAcceptUntrustedCertificates(acceptUntrustedCertificates);
	}
	
	public void setAssumeUntrustedCertificateIssuer(boolean assumeUntrustedCertificateIssuer) {
		this.profile.setAssumeUntrustedCertificateIssuer(assumeUntrustedCertificateIssuer);
	}
	
	public void loadFireFoxExtensions() {
		//JSONObject extensions = (JSONObject) this.profilePreferences.get(FIREFOXEXTENSIONS);
		JSONArray extensions = (JSONArray) this.profilePreferences.get(FIREFOXEXTENSIONS);
		Iterator<?> extensionsIterator = extensions.iterator();
		while (extensionsIterator.hasNext()) {
			Entry<?, ?> profileEntry = (Entry<?, ?>) extensionsIterator.next();
			String key = profileEntry.getKey().toString();
			String value = profileEntry.getValue().toString();
			
				
			/*if (Converter.isBoolean(value)) {
				this.profile.setPreference(key, Boolean.parseBoolean(value));
				this.logger.info(LOADFIREFOXPROFILEPREFERENCES, "Loaded FireFox Profile Preference " + key + " = " + this.profile.getBooleanPreference(key, false));
			} else if (Converter.isNumeric(value)) {
				this.profile.setPreference(key, Converter.toInteger(value));
				this.logger.info(LOADFIREFOXPROFILEPREFERENCES, "Loaded FireFox Profile Preference " + key + " = " + this.profile.getIntegerPreference(key, 0));
			} else {
				this.profile.setPreference(key, value);
				this.logger.info(LOADFIREFOXPROFILEPREFERENCES, "Loaded FireFox Profile Preference " + key + " = " + this.profile.getStringPreference(key, ""));
			}*/	
		}
		
				
				
		
	}
	
	public void setFirefoxProfile() {
		
		
			
				
				
					/*File fireBugXPIFile = new File(this.getClass().getClassLoader().getResource(FIREBUG).getPath());
					if (fireBugXPIFile.exists()) {
						this.logger.info(LOADFIREFOXPROFILEPREFERENCES, "Loading FireFoxBug XPI file " + (char)34 + FIREBUG + (char)34);
						try {
							this.profile.addExtension(fireBugXPIFile);
						} catch (IOException e) {
							this.logger.fatal(LOADFIREFOXPROFILEPREFERENCES, "Failed to load the FireBug extension " + (char)34 + fireBugXPIFile + (char)34 + ".");
							this.logger.fatal(LOADFIREFOXPROFILEPREFERENCES, e.getLocalizedMessage());
							this.logger.fatal(LOADFIREFOXPROFILEPREFERENCES, e.getStackTrace());
						}
						this.loadFireFoxProfilePreferences();
					} else {
						this.logger.error(LOADFIREFOXPROFILEPREFERENCES, "FireFoxBug XPI file " + (char)34 + FIREBUG + (char)34 + " does not exist");
					}*/
				
			
			
	}

	private void loadFireFoxProfilePreferences() {
		Iterator<?> profilePreferenceIterator = this.profilePreferences.entrySet().iterator();
		while (profilePreferenceIterator.hasNext()) {
			Entry<?, ?> profileEntry = (Entry<?, ?>) profilePreferenceIterator.next();
			String key = profileEntry.getKey().toString();
			String value = profileEntry.getValue().toString();
			
				
			if (Converter.isBoolean(value)) {
				this.profile.setPreference(key, Boolean.parseBoolean(value));
				this.logger.info(LOADFIREFOXPROFILEPREFERENCES, "Loaded FireFox Profile Preference " + key + " = " + this.profile.getBooleanPreference(key, false));
			} else if (Converter.isNumeric(value)) {
				this.profile.setPreference(key, Converter.toInteger(value));
				this.logger.info(LOADFIREFOXPROFILEPREFERENCES, "Loaded FireFox Profile Preference " + key + " = " + this.profile.getIntegerPreference(key, 0));
			} else {
				this.profile.setPreference(key, value);
				this.logger.info(LOADFIREFOXPROFILEPREFERENCES, "Loaded FireFox Profile Preference " + key + " = " + this.profile.getStringPreference(key, ""));
			}	
		}
	}

}
