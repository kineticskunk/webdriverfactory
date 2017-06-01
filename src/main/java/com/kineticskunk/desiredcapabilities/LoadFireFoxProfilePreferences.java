package com.kineticskunk.desiredcapabilities;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.lang3.EnumUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.kineticskunk.utilities.Converter;

public class LoadFireFoxProfilePreferences {
	
	private final Logger logger = LogManager.getLogger(LoadFireFoxProfilePreferences.class.getName());
	private final Marker LOADFIREFOXPROFILEPREFERENCES = MarkerManager.getMarker("LOADFIREFOXPROFILEPREFERENCES");
	
	private static final String FIREFOXEXTENSIONS = "firefoxextensions";
	private static final String EXTENSION = "extension";
	private static final String PREFERENCES = "preferences";
	
	private JSONObject profilePreferences = new JSONObject();
	private FirefoxProfile profile = new FirefoxProfile();
	
	public LoadFireFoxProfilePreferences() {
	}
	
	public LoadFireFoxProfilePreferences(JSONObject profilePreferences) {
		this();
		this.profilePreferences = profilePreferences;
		this.loadPreferences(this.profilePreferences);
	}
	
	public void setAcceptUntrustedCertificates(boolean acceptUntrustedCertificates) {
		this.profile.setAcceptUntrustedCertificates(acceptUntrustedCertificates);
	}
	
	public void setAssumeUntrustedCertificateIssuer(boolean assumeUntrustedCertificateIssuer) {
		this.profile.setAssumeUntrustedCertificateIssuer(assumeUntrustedCertificateIssuer);
	}
	
	public void setAlwaysLoadNoFocusLib(boolean loadNoFocusLib) {
		this.profile.setAlwaysLoadNoFocusLib(loadNoFocusLib);
	}
	
	public void layoutOnDisk() {
		this.profile.layoutOnDisk();
	}
	
	public FirefoxProfile getFirefoxProfile() {
		return this.profile;
	}
	
	public void loadFireFoxExtensionsAndExtensionPreferences() throws IOException {
		JSONArray extensions = (JSONArray) this.profilePreferences.get(FIREFOXEXTENSIONS);
		for (Object extension : extensions) {
		    JSONObject jsonObject = (JSONObject) extension;
		    File extensionFile = new File(this.getClass().getClassLoader().getResource(jsonObject.get(EXTENSION).toString()).getPath());
		    this.profile.addExtension(extensionFile);
		    this.loadPreferences ((JSONObject) jsonObject.get(PREFERENCES));
		  }
	}
	
	public void loadPreferences(JSONObject prefs) {
		Iterator<?> prefsIterator = prefs.entrySet().iterator();
		while (prefsIterator.hasNext()) {
			Entry<?, ?> profileEntry = (Entry<?, ?>) prefsIterator.next();
			String key = profileEntry.getKey().toString();
			String value = profileEntry.getValue().toString();
			if (EnumUtils.isValidEnum(profileSetting.class, key.replace(".", "_"))) {
				if (!key.equalsIgnoreCase(FIREFOXEXTENSIONS)) {
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
			} else {
				this.logger.debug(LOADFIREFOXPROFILEPREFERENCES, "Profile preference " + (char)34 + key + (char)34 + " is not supported or is invalid");
			}
		}
	}
	
	private enum profileSetting {
		accept_untrusted_certificates,
		always_load_no_focus_lib,
		assume_untrusted_certificate_issuer,
		browser_cache_disk_enable,
		browser_download_dir,
		browser_download_folderList,
		browser_download_manager_alertOnEXEOpen,
		browser_download_manager_closeWhenDone,
		browser_download_manager_focusWhenStarting,
		browser_download_manager_showAlertOnComplete,
		browser_download_manager_showWhenStarting,
		browser_download_manager_useWindow,
		browser_helperApps_alwaysAsk_force,
		browser_helperApps_neverAsk_openFile,
		browser_helperApps_neverAsk_saveToDisk,
		enable_native_events,
		network_proxy_type,
		network_proxy_http,
		network_proxy_http_port,
		network_proxy_ssl,
		network_proxy_ssl_port
	}

}
