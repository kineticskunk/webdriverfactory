package com.kineticskunk.auto.webdriverfactory;

import java.io.File;
import java.util.EnumSet;
import java.util.Hashtable;
import java.util.Set;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.kineticskunk.auto.logging.TestServiceLogging;

public class FireFoxProfile {

	private Hashtable<String, String> ffpConfig;
	private Utilties utils;
	private FirefoxProfile ffp;
	private TestServiceLogging tsl;

	public FireFoxProfile() {
		ffp = new FirefoxProfile();
		utils = new Utilties();
	}

	public FireFoxProfile(Hashtable<String, String> ffpConfig) {
		this();
		this.ffpConfig = ffpConfig;
	}

	public FireFoxProfile(Marker marker, Hashtable<String, String> dcCondfig) {
		this(dcCondfig);
		this.tsl = new TestServiceLogging(LogManager.getLogger(FireFoxProfile.class.getName()), marker, true);
	}
	
	/**
	 * 
	 * @author yodaqua
	 *
	 */
	public enum standardProfilePreferenceNames {
		acceptuntrustedcertificates,
		alwaysloadnofocuslib,
		assumeuntrustedcertificateissuer,
		enablenativeevents
	}

	/**
	 * 
	 * @return
	 */
	public FirefoxProfile getFirefoxProfiles() {
		Set<String> keys = this.ffpConfig.keySet();
		for (String key : keys) {
			if (EnumSet.allOf(standardProfilePreferenceNames.class).contains(key.replaceAll(".", ""))) {
				this.setStandardProfilePreference(key, this.ffpConfig.get(key));
			} else {
				if (this.utils.isNumeric(this.ffpConfig.get(key))) {
					this.ffp.setPreference(key, Integer.valueOf(this.ffpConfig.get(key)));
				} else if (this.utils.isBoolean(this.ffpConfig.get(key))) {
					this.ffp.setPreference(key, Boolean.valueOf(this.ffpConfig.get(key)));
				} else {
					this.ffp.setPreference(key, this.ffpConfig.get(key));
				}
			}
		}
		return this.ffp;
	}

	/**
	 * 
	 * @param standardProfilePrerenceName
	 * @param standardProfilePrerenceValue
	 */
	public void setStandardProfilePreference(String standardProfilePrerenceName, String standardProfilePrerenceValue) {
		this.tsl.enterLogger("In method setStandardProfilePreference", "standardProfilePrerenceName = '" + standardProfilePrerenceName + ", standardProfilePrerenceValue = '" + standardProfilePrerenceValue + "'");
		try {
			if (!this.utils.isBoolean(standardProfilePrerenceValue)) {
				this.tsl.logMessage(Level.WARN, "Standrd profile preference value '" + standardProfilePrerenceValue + "' for is NOT A BOOLEAN value. Setting the profile preference value to 'TRUE'");
				standardProfilePrerenceValue = "true";
			}
			switch (standardProfilePrerenceName.toLowerCase()) {
			case "accept.untrusted.certificates":
				this.ffp.setAcceptUntrustedCertificates(Boolean.valueOf(standardProfilePrerenceValue));
				break;
			case "always.load.no.focus.lib":
				this.ffp.setAlwaysLoadNoFocusLib(Boolean.valueOf(standardProfilePrerenceValue));
				break;
			case "assume.untrusted.certificate.issuer":
				this.ffp.setAssumeUntrustedCertificateIssuer(Boolean.valueOf(standardProfilePrerenceValue));
				break;
			case "enable.native.events":
				this.ffp.setEnableNativeEvents(Boolean.valueOf(standardProfilePrerenceValue));
				break;
			default:
				this.tsl.logMessage(Level.WARN, "Standrd profile preference '" + standardProfilePrerenceName + "' is NOT SUPPORTED.");
				this.tsl.exitLogger(false);
			}
			this.tsl.exitLogger(true);
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger(false);
		}
	}

	/**
	 * Set FireFox profile boolean value preferences
	 * @author yodaqua
	 * @category configuration
	 * @param preferenceName
	 * @param preferenceValue
	 * @param preferenceDefaultValue
	 */
	private void setFireFoxProfileBooleanPreference(String preferenceName, String preferenceValue, boolean preferenceDefaultValue) {
		tsl.enterLogger("In method setFireFoxProfileBooleanPreference", "Preference name = '" + preferenceName + "'; Preferance value = '" + preferenceValue + "; Preference Default Value = '" + preferenceDefaultValue);
		try {
			if (preferenceValue.equalsIgnoreCase("true") || preferenceValue.equalsIgnoreCase("false")) {
				this.tsl.logMessage(Level.INFO, "Setting FireFox profile '" + preferenceName + "' to '" + preferenceValue.toUpperCase() + "'");
				this.ffp.setPreference(preferenceName, Boolean.valueOf(preferenceValue));
				this.tsl.exitLogger(true);
			} else {
				this.tsl.logMessage(Level.WARN, "Non boolean value detected. Setting 'browserCacheDiskEnable' to it's default value of '" + String.valueOf(preferenceDefaultValue).toUpperCase() + "'");
				this.ffp.setPreference(preferenceName, Boolean.valueOf(preferenceValue));
				this.tsl.exitLogger(true);
			}
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger(false);
		}
	}

	/**
	 * Load FireFox custom profile configuration
	 */
	private void loadFireFoxCustomProfile() {
		try {
			this.tsl.enterLogger("Loading FireFox profile preferences");
			this.setAcceptUntrustedCertificates(this.ffpConfig.get("ACCEPT_UNTRUSTED_CERTIFICATES"));
			this.setEnableNativeEvents(this.ffpConfig.get("ENABLE_NATIVE_EVENTS"));
			this.setFireBrowserDownLoad(this.ffpConfig.get("BROWSER_DOWNLOAD_DIRECTORY"));
			this.setFireFoxProfileIntegerPreference("browser.download.folderList", this.ffpConfig.get("BROWSER_DOWNLOAD_FOLDERLIST"), 2);
			this.setFireFoxProfileBooleanPreference("browser.cache.disk.enable", this.ffpConfig.get("BROWSER_CACHE_DISK_ENABLE"), true);
			this.setFireFoxProfileBooleanPreference("browser.download.manager.showWhenStarting", this.ffpConfig.get("BROWSER_DOWNLOAD_MANAGER_SHOWWHENSTARTING"), false);
			this.setFireFoxProfileBooleanPreference("browser.download.manager.alertOnEXEOpen", this.ffpConfig.get("BROWSER_DOWNLOAD_MANAGER_ALERTONEXEOPEN"), false);
			this.setFireFoxProfileBooleanPreference("browser.download.manager.focusWhenStarting", this.ffpConfig.get("BROWSER_DOWNLOAD_MANAGER_FOCUSWHENTARTING"), false);
			this.setFireFoxProfileBooleanPreference("browser.download.manager.useWindow", this.ffpConfig.get("BROWSER_DOWNLOAD_MANAGER_USEWINDOW"), false);
			this.setFireFoxProfileBooleanPreference("browser.download.manager.showAlertOnComplete", this.ffpConfig.get("BROWSER_DOWNLOAD_MANAGER_SHOWALERTSONCOMPLETE"), false);
			this.setFireFoxProfileBooleanPreference("browser.download.manager.closeWhenDone", this.ffpConfig.get("BROWSER_DOWNLOAD_MANAGER_CLOSEWHENDONE"), false);
			this.setFireFoxProfileBooleanPreference("browser.helperApps.alwaysAsk.force", this.ffpConfig.get("BROWSER_HELPER_APPS_ALWAYSASKFORCE"), false);
			this.setFireFoxProfileStringPreference("browser.helperApps.neverAsk.saveToDisk", this.ffpConfig.get("BROWSER_HELPER_APPS_NEVERASK_SAVETODISK"), DEFAULT_BROWSER_HELPER_APPS_NEVERASK_SAVETODISK);
			this.tsl.exitLogger(true);
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.logMessage(Level.FATAL, "Failed to load FireFox custom profile settings");
			this.tsl.exitLogger(false);
		}
	}

	private void setFireBrowserDownLoad(String downloodLocation) {
		tsl.enterLogger("In method setFireBrowserDownLoad", downloodLocation);
		try {
			File downloadlocation = new File(downloodLocation);
			if (downloadlocation.isDirectory()) {
				this.tsl.logMessage(Level.INFO, "Setting FireFox profile 'browser.download.dir' to '" + downloodLocation + "'");
				this.profile.setPreference("browser.download.dir", downloodLocation);
				this.tsl.exitLogger(true);
			} else {
				if (PlatformOperatingSystem.isWindows()) {
					this.tsl.logMessage(Level.WARN, "The provided download location '" + downloodLocation + "' is not a directory. Setting the download location to it's default value of '" + DEFAULT_WIN_DOWNLOAD_DIRECTORY + "'");
					this.profile.setPreference("browser.download.dir", DEFAULT_WIN_DOWNLOAD_DIRECTORY);
				} else if (PlatformOperatingSystem.isMac() || PlatformOperatingSystem.isSolaris() || PlatformOperatingSystem.isUnix()) {
					this.tsl.logMessage(Level.WARN, "The provided download location '" + downloodLocation + "' is not a directory. Setting the download location to it's default value of '" + DEFAULT_UNIX_BASED_DOWNLOAD_DIRECTORY + "'");
					this.profile.setPreference("browser.download.dir", DEFAULT_UNIX_BASED_DOWNLOAD_DIRECTORY);
				}
				this.tsl.exitLogger(true);
			}
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger(false);
		}
	}

	/**
	 * 
	 * @param preferenceName
	 * @param preferenceValue
	 * @param preferenceDefaultValue
	 */
	private void setFireFoxProfileIntegerPreference(String preferenceName, String preferenceValue, int preferenceDefaultValue) {
		tsl.enterLogger("In method setFireFoxProfileStringPreference", "Preference name = '" + preferenceName + "'; Preferance value = '" + preferenceValue + "'; Preference Default Value = '" + preferenceDefaultValue + "'");
		try {
			if (preferenceName instanceof String) {
				this.tsl.logMessage(Level.INFO, "Setting FireFox profile '" + preferenceName + "' to '" + preferenceValue + "'");
				this.profile.setPreference(preferenceName, Integer.valueOf(preferenceValue));
			} else {
				this.tsl.logMessage(Level.WARN, "Non string value detected. Setting '" + preferenceName + "' to it's default value of '" + preferenceDefaultValue + "'");
				this.profile.setPreference(preferenceName, preferenceDefaultValue);
			}
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger(false);
		}
	}

	/**
	 * 
	 * @param preferenceName
	 * @param preferenceValue
	 * @param preferenceDefaultValue
	 */
	private void setFireFoxProfileStringPreference(String preferenceName, String preferenceValue, String preferenceDefaultValue) {
		tsl.enterLogger("In method setFireFoxProfileStringPreference", "Preference name = '" + preferenceName + "'; Preferance value = '" + preferenceValue + "'; Preference Default Value = '" + preferenceDefaultValue + "'");
		try {
			if (preferenceName instanceof String) {
				this.tsl.logMessage(Level.INFO, "Setting FireFox profile '" + preferenceName + "' to '" + preferenceValue.toUpperCase() + "'");
				this.profile.setPreference(preferenceName, Boolean.valueOf(preferenceValue));
			} else {
				this.tsl.logMessage(Level.WARN, "Non string value detected. Setting '" + preferenceName + "' to it's default value of '" + String.valueOf(preferenceDefaultValue).toUpperCase() + "'");
				this.profile.setPreference(preferenceName, preferenceDefaultValue);
			}
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger(false);
		}
	}

	/**
	 * Set firefoxprofile.enable-native-events to a boolean value
	 * @author yodaqua
	 * @category configuration
	 * @param enableNativeEvents
	 */
	private void setEnableNativeEvents(String enableNativeEvents) {
		tsl.enterLogger("In method setEnableNativeEvents", NO_PARAMETERS);
		try {
			if (enableNativeEvents.equalsIgnoreCase("true") || enableNativeEvents.equalsIgnoreCase("false")) {
				this.tsl.logMessage(Level.INFO, "Setting FireFox profile 'enableNativeEvents to '" + enableNativeEvents.toUpperCase() + "'");
				this.ffp.setEnableNativeEvents(Boolean.valueOf(enableNativeEvents));
				this.tsl.exitLogger(true);
			} else {
				this.tsl.logMessage(Level.WARN, "Non boolean value detected. Setting 'enableNativeEvents' to it's default value of '" + enableNativeEvents + "'");
				this.ffp.setEnableNativeEvents(Boolean.valueOf(true));
				this.tsl.exitLogger(true);
			}
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger(false);
		}

	}
}
