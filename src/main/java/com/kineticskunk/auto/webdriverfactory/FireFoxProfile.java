package com.kineticskunk.auto.webdriverfactory;

import java.io.File;
import java.util.Hashtable;
import java.util.Set;

import org.apache.commons.lang3.EnumUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.kineticskunk.auto.logging.TestServiceLogging;

public class FireFoxProfile {
	
	private static final String DEFAULT_WIN_DOWNLOAD_DIRECTORY = System.getProperty("user.home") + "\\downloads\\";
	private static final String DEFAULT_UNIX_BASED_DOWNLOAD_DIRECTORY = System.getProperty("user.home") + "/downloads/";
	
	private Hashtable<String, String> ffpConfig;
	private FirefoxProfile ffp;
	private Utilties utils;
	private PlatformOperatingSystem pos;
	private TestServiceLogging tsl;

	public FireFoxProfile() {
		ffp = new FirefoxProfile();
		utils = new Utilties();
		pos = new PlatformOperatingSystem();
	}

	public FireFoxProfile(Hashtable<String, String> ffpConfig) {
		this();
		this.ffpConfig = ffpConfig;
	}

	public FireFoxProfile(Hashtable<String, String> dcCondfig, boolean enableLogging) {
		this(dcCondfig);
		this.tsl = new TestServiceLogging(LogManager.getLogger(FireFoxProfile.class.getName()) , enableLogging);
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
	public FirefoxProfile setFirefoxProfile() {
		this.tsl.enterLogger("Configuring firefox profile");
		try {
			Set<String> keys = this.ffpConfig.keySet();
			for (String key : keys) {
				if (EnumUtils.isValidEnum(standardProfilePreferenceNames.class, key.replaceAll(".", ""))) {
					this.setStandardProfilePreference(key, this.ffpConfig.get(key));
				} else if (key.contains("browser.download.dir")) {
					this.setFireBrowserDownLoad(this.ffpConfig.get(key));
				} else {
					this.tsl.logMessage(Level.INFO, "Preference name = '" + key + "'; Preferance value = '" + this.ffpConfig.get(key) + "'");
					if (this.utils.isNumeric(this.ffpConfig.get(key))) {
						this.ffp.setPreference(key, Integer.valueOf(this.ffpConfig.get(key)));
					} else if (this.utils.isBoolean(this.ffpConfig.get(key))) {
						this.ffp.setPreference(key, Boolean.valueOf(this.ffpConfig.get(key)));
					} else {
						this.ffp.setPreference(key, this.ffpConfig.get(key));
					}
				}
			}
			this.tsl.exitLogger(true);
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger(false);
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
	 * 
	 * @param downloodLocation
	 */
	private void setFireBrowserDownLoad(String downloodLocation) {
		tsl.enterLogger("In method setFireBrowserDownLoad", downloodLocation);
		try {
			File downloadlocation = new File(downloodLocation);
			if (downloadlocation.isDirectory()) {
				this.tsl.logMessage(Level.INFO, "Setting FireFox profile 'browser.download.dir' to '" + downloodLocation + "'");
				this.ffp.setPreference("browser.download.dir", downloodLocation);
				this.tsl.exitLogger(true);
			} else {
				if (pos.isWindows()) {
					this.tsl.logMessage(Level.WARN, "The provided download location '" + downloodLocation + "' is not a directory. Setting the download location to it's default value of '" + DEFAULT_WIN_DOWNLOAD_DIRECTORY + "'");
					this.ffp.setPreference("browser.download.dir", DEFAULT_WIN_DOWNLOAD_DIRECTORY);
				} else if (pos.isMac() || pos.isSolaris() || pos.isUnix()) {
					this.tsl.logMessage(Level.WARN, "The provided download location '" + downloodLocation + "' is not a directory. Setting the download location to it's default value of '" + DEFAULT_UNIX_BASED_DOWNLOAD_DIRECTORY + "'");
					this.ffp.setPreference("browser.download.dir", DEFAULT_UNIX_BASED_DOWNLOAD_DIRECTORY);
				}
				this.tsl.exitLogger(true);
			}
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger(false);
		}
	}
	
}