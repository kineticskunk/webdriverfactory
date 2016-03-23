package com.kineticskunk.auto.webdriverfactory;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kineticskunk.auto.logging.TestServiceLogging;

/**
 * 
 * @author yodaqua
 *
 */
public class FireFoxDriverConfig {

	private static final String DEFAULT_WIN_DOWNLOAD_DIRECTORY = System.getProperty("user.home") + "\\downloads\\";
	private static final String DEFAULT_UNIX_BASED_DOWNLOAD_DIRECTORY = System.getProperty("user.home") + "/downloads/";
	private static final String DEFAULT_BROWSER_HELPER_APPS_NEVERASK_SAVETODISK = "text/csv,application/excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,application/zip";
	private static final String NO_PARAMETERS = "no parameter values";
	private static final String SUCCESS_MESSAGE = "Success";
	private static final String FAILURE_MESSAGE = "Fail";

	private Hashtable<String, String> firefoxConfig;
	private TestServiceLogging tsl;
	private FirefoxProfile profile;
	private DesiredCapabilities dc;

	/**
	 * Constructor
	 */
	protected FireFoxDriverConfig(Logger logger, Marker marker, Hashtable <String, String> firefoxConfig) {
		this.firefoxConfig = firefoxConfig;
		tsl = new TestServiceLogging(logger, marker);
		profile = new FirefoxProfile();
		dc = new DesiredCapabilities();
	}

	/**
	 * Constructor
	 * @param firefoxConfigFile
	 * @throws IOException
	 */
	protected FireFoxDriverConfig(boolean loadFireBug)  throws IOException{
		
		this.loadFireFoxCustomProfile();
		if (this.loadExtension("FireBug", this.firefoxConfig.get("FIREBUG_EXTENSION_NAME"))) {
			this.loadFireBugPreferences();
		}
	}
	
	/**
	 * Load FireFox custom profile configuration
	 */
	private void loadFireFoxCustomProfile() {
		try {
			this.tsl.enterLogger("Loading FireFox profile preferences");
			this.setAcceptUntrustedCertificates(this.firefoxConfig.get("ACCEPT_UNTRUSTED_CERTIFICATES"));
			this.setEnableNativeEvents(this.firefoxConfig.get("ENABLE_NATIVE_EVENTS"));
			this.setFireBrowserDownLoad(this.firefoxConfig.get("BROWSER_DOWNLOAD_DIRECTORY"));
			this.setFireFoxProfileIntegerPreference("browser.download.folderList", this.firefoxConfig.get("BROWSER_DOWNLOAD_FOLDERLIST"), 2);
			this.setFireFoxProfileBooleanPreference("browser.cache.disk.enable", this.firefoxConfig.get("BROWSER_CACHE_DISK_ENABLE"), true);
			this.setFireFoxProfileBooleanPreference("browser.download.manager.showWhenStarting", this.firefoxConfig.get("BROWSER_DOWNLOAD_MANAGER_SHOWWHENSTARTING"), false);
			this.setFireFoxProfileBooleanPreference("browser.download.manager.alertOnEXEOpen", this.firefoxConfig.get("BROWSER_DOWNLOAD_MANAGER_ALERTONEXEOPEN"), false);
			this.setFireFoxProfileBooleanPreference("browser.download.manager.focusWhenStarting", this.firefoxConfig.get("BROWSER_DOWNLOAD_MANAGER_FOCUSWHENTARTING"), false);
			this.setFireFoxProfileBooleanPreference("browser.download.manager.useWindow", this.firefoxConfig.get("BROWSER_DOWNLOAD_MANAGER_USEWINDOW"), false);
			this.setFireFoxProfileBooleanPreference("browser.download.manager.showAlertOnComplete", this.firefoxConfig.get("BROWSER_DOWNLOAD_MANAGER_SHOWALERTSONCOMPLETE"), false);
			this.setFireFoxProfileBooleanPreference("browser.download.manager.closeWhenDone", this.firefoxConfig.get("BROWSER_DOWNLOAD_MANAGER_CLOSEWHENDONE"), false);
			this.setFireFoxProfileBooleanPreference("browser.helperApps.alwaysAsk.force", this.firefoxConfig.get("BROWSER_HELPER_APPS_ALWAYSASKFORCE"), false);
			this.setFireFoxProfileStringPreference("browser.helperApps.neverAsk.saveToDisk", this.firefoxConfig.get("BROWSER_HELPER_APPS_NEVERASK_SAVETODISK"), DEFAULT_BROWSER_HELPER_APPS_NEVERASK_SAVETODISK);
			this.tsl.exitLogger(SUCCESS_MESSAGE);
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger("Failed to load FireFox custom profile settings");
		}
	}

	/**
	 * Load FireFox extension
	 * @param extensionName
	 * @param extensionValue
	 * @return
	 */
	private boolean loadExtension(String extensionName, String extensionValue) {
		this.tsl.enterLogger("In method loadExtension", "Extenstion = '" + extensionName + ", with value = '" + extensionValue + "'");
		File extensionPath = null;
		try {
			extensionPath = new File(this.getClass().getClassLoader().getResource(extensionValue).getPath());
			if (extensionPath.isFile()) {
				this.tsl.logMessage(Level.INFO, "Setting '" + extensionName + "' path to '" + extensionPath.getAbsolutePath() + "'");
				this.profile.addExtension(extensionPath);
				this.tsl.exitLogger("Successfully loaded the '" + extensionValue + "'");
				return true;
			} else {
				this.tsl.logMessage(Level.WARN, "Failed to load the extension '" + extensionName + "' using location '" +  extensionPath.getPath() + "' as it is an invalid file");
				this.tsl.exitLogger("Failed to load the firebug extentsion");
				return false;
			}
		} catch (IOException ioe) {
			this.tsl.catchException(ioe);
			this.tsl.logMessage(Level.WARN, "Unable to load '" + extensionName + "' with absolute path '" + extensionPath.getAbsolutePath() + "'");
			this.tsl.exitLogger("Failed to load the firebug extentsion");
			return false;
		}
	}

	/**
	 * Load FireBug preferences
	 */
	private void loadFireBugPreferences() {
		try {
			this.tsl.logMessage(Level.INFO, "Loading firebug custom preferences");
			this.setFireBugExtensionPreferenceStringValue("extensions.firebug.currentVersion", this.firefoxConfig.get("FIREBUG_EXTENSION_VERSION"), "2.0.10b1");
			this.setFireFoxProfileBooleanPreference("extensions.firebug.console.enableSites", this.firefoxConfig.get("FIREBUG_EXTENSION_CONSOLE_ENABLESITES"), true);
			this.setFireFoxProfileBooleanPreference("extensions.firebug.script.enableSites", this.firefoxConfig.get("FIREBUG_EXTENSION_SCRIPT_ENABLESITES"), true);
			this.setFireBugExtensionPreferenceStringValue("extensions.firebug.defaultPanelName", this.firefoxConfig.get("FIREBUG_EXTENSION_DEFAULTPANELNAME"), "net");
			this.setFireFoxProfileBooleanPreference("extensions.firebug.net.enableSites", this.firefoxConfig.get("FIREBUG_EXTENSION_NET_ENABLESITES"), true);
			this.setFireBugExtensionPreferenceStringValue("extensions.firebug.allPagesActivation", this.firefoxConfig.get("FIREBUG_EXTENSION_ALL_PAGES_ACTIVATION"), "on");
			this.setFireFoxProfileBooleanPreference("extensions.firebug.cookies.enableSites", this.firefoxConfig.get("FIREBUG_EXTENSION_COOKIES_ENABLESITES"), true);
			this.tsl.logMessage(Level.INFO, "Done loading firebug custom preferences");
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger("Failed to load FireBug preferences");
		}
	}

	@Override
	public String toString() {
		return "FireFoxDriverProfile [ Driver name = " + this.dc.getBrowserName() + ", acceptUntrustedCertificates = " + this.profile.getBooleanPreference("AcceptUntrustedCertificates", true) + "]";
	}

	/**
	 * Set firefoxprofile.accept-untrusted-certificates to a boolean value
	 * @author yodaqua
	 * @category configuration
	 * @param acceptUntrustedCertificates
	 */
	public void setAcceptUntrustedCertificates(String acceptUntrustedCertificates) {
		tsl.enterLogger("In method setAcceptUntrustedCertificates", NO_PARAMETERS);
		if (acceptUntrustedCertificates.equalsIgnoreCase("true") || acceptUntrustedCertificates.equalsIgnoreCase("false")) {
			this.tsl.logMessage(Level.INFO, "Setting FireFox profile 'acceptUntrustedCertificates to '" + acceptUntrustedCertificates.toUpperCase() + "'");
			this.profile.setAcceptUntrustedCertificates(Boolean.valueOf(acceptUntrustedCertificates));
		} else {
			tsl.logMessage(Level.INFO, "Non boolean value detected. Setting 'acceptUntrustedCertificates' to it's default value of '" + acceptUntrustedCertificates + "'");
			this.profile.setAcceptUntrustedCertificates(Boolean.valueOf(true));
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
		if (enableNativeEvents.equalsIgnoreCase("true") || enableNativeEvents.equalsIgnoreCase("false")) {
			tsl.logMessage(Level.INFO, "Setting FireFox profile 'enableNativeEvents to '" + enableNativeEvents.toUpperCase() + "'");
			this.profile.setEnableNativeEvents(Boolean.valueOf(enableNativeEvents));
			tsl.exitLogger(SUCCESS_MESSAGE);
		} else {
			tsl.logMessage(Level.WARN, "Non boolean value detected. Setting 'enableNativeEvents' to it's default value of '" + enableNativeEvents + "'");
			this.profile.setEnableNativeEvents(Boolean.valueOf(true));
			tsl.exitLogger(SUCCESS_MESSAGE);
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
				this.profile.setPreference(preferenceName, Boolean.valueOf(preferenceValue));
				this.tsl.exitLogger(SUCCESS_MESSAGE);
			} else {
				this.tsl.logMessage(Level.WARN, "Non boolean value detected. Setting 'browserCacheDiskEnable' to it's default value of '" + String.valueOf(preferenceDefaultValue).toUpperCase() + "'");
				this.profile.setPreference(preferenceName, Boolean.valueOf(preferenceValue));
				this.tsl.exitLogger(SUCCESS_MESSAGE);
			}
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger(FAILURE_MESSAGE);
		}
	}

	/**
	 * Set the FireFox profile download directory
	 * @param downloodLocation
	 */
	private void setFireBrowserDownLoad(String downloodLocation) {
		tsl.enterLogger("In method setFireBrowserDownLoad", downloodLocation);
		try {
			File downloadlocation = new File(downloodLocation);
			if (downloadlocation.isDirectory()) {
				this.tsl.logMessage(Level.INFO, "Setting FireFox profile 'browser.download.dir' to '" + downloodLocation + "'");
				this.profile.setPreference("browser.download.dir", downloodLocation);
			} else {
				if (PlatformOperatingSystem.isWindows()) {
					this.tsl.logMessage(Level.WARN, "The provided download location '" + downloodLocation + "' is not a directory. Setting the download location to it's default value of '" + DEFAULT_WIN_DOWNLOAD_DIRECTORY + "'");
					this.profile.setPreference("browser.download.dir", DEFAULT_WIN_DOWNLOAD_DIRECTORY);
				} else if (PlatformOperatingSystem.isMac() || PlatformOperatingSystem.isSolaris() || PlatformOperatingSystem.isUnix()) {
					this.tsl.logMessage(Level.WARN, "The provided download location '" + downloodLocation + "' is not a directory. Setting the download location to it's default value of '" + DEFAULT_UNIX_BASED_DOWNLOAD_DIRECTORY + "'");
					this.profile.setPreference("browser.download.dir", DEFAULT_UNIX_BASED_DOWNLOAD_DIRECTORY);
				}
			}
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger(FAILURE_MESSAGE);
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
			this.tsl.exitLogger(FAILURE_MESSAGE);
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
			this.tsl.exitLogger(FAILURE_MESSAGE);
		}
	}

	/**
	 * 
	 * @param preferenceName
	 * @param preferenceValue
	 * @param defaultPanel
	 */
	protected void setFireBugExtensionPreferenceStringValue(String preferenceName, String preferenceValue, String defaultPreferenceValue) {
		this.tsl.enterLogger("In method setFireBugExtensionPreferenceStringValue", preferenceValue + ", " + defaultPreferenceValue);
		try {
			if (preferenceName instanceof String) {
				this.tsl.logMessage(Level.INFO, "Setting '" + preferenceName + "' to '" + preferenceValue + "'");
				this.profile.setPreference(preferenceName, preferenceValue);
			} else {
				this.tsl.logMessage(Level.WARN, "Non string value detected. Setting '" + preferenceName + "' to it's default value of '" + String.valueOf(defaultPreferenceValue).toUpperCase() + "'");
				this.profile.setPreference(preferenceName, defaultPreferenceValue);
			}
			this.tsl.exitLogger("Loaded firebug preference");
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger(FAILURE_MESSAGE);
		}
	}

}
