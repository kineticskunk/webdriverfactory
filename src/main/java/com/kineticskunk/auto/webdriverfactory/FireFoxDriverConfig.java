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
	private static final String NO_PARAMETERS = "NO_PARAMETERS";

	private Hashtable<String, String> firefoxConfig;
	private TestServiceLogging tsl;
	private FirefoxProfile profile;
	private DesiredCapabilities dc;

	/**
	 * Constructor
	 */
	protected FireFoxDriverConfig(Logger logger, Marker marker, Hashtable <String, String> firefoxConfig) {
		this.firefoxConfig = firefoxConfig;
		tsl = new TestServiceLogging(logger, marker, true);
		profile = new FirefoxProfile();
		dc = new DesiredCapabilities();
	}

	/**
	 * Constructor
	 * @param firefoxConfigFile
	 * @throws IOException
	 */
	protected FireFoxDriverConfig(boolean loadFireBug)  throws IOException{
		//this.loadFireFoxCustomProfile();
		if (this.loadExtension("FireBug", this.firefoxConfig.get("FIREBUG_EXTENSION_NAME"))) {
			this.loadFireBugPreferences();
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
				this.tsl.exitLogger(true);
				return true;
			} else {
				this.tsl.logMessage(Level.WARN, "Failed to load the extension '" + extensionName + "' using location '" +  extensionPath.getPath() + "' as it is an invalid file");
				this.tsl.exitLogger(false);
				return false;
			}
		} catch (IOException ioe) {
			this.tsl.catchException(ioe);
			this.tsl.logMessage(Level.WARN, "Unable to load '" + extensionName + "' with absolute path '" + extensionPath.getAbsolutePath() + "'");
			this.tsl.exitLogger(false);
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
			this.tsl.exitLogger(false);
		}
	}

	@Override
	public String toString() {
		return "FireFoxDriverProfile [ Driver name = " + this.dc.getBrowserName() + ", acceptUntrustedCertificates = " + this.profile.getBooleanPreference("AcceptUntrustedCertificates", true) + "]";
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
			this.tsl.exitLogger(true);
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger(false);
		}
	}

}