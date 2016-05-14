package com.kineticskunk.firefox;

import java.io.File;
import java.util.Hashtable;
import java.util.Set;

import org.apache.commons.lang3.EnumUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.openqa.selenium.firefox.FirefoxProfile;
import com.kineticskunk.utilities.Converter;
import com.kineticskunk.utilities.PlatformOperatingSystem;

public class FireFoxProfile {
	
	private static final Logger logger = LogManager.getLogger(FireFoxProfile.class.getName());
	private static final Marker FIREFOXPROFILE = MarkerManager.getMarker("FIREFOXPROFILE");
	
	private static final String DEFAULT_WIN_DOWNLOAD_DIRECTORY = System.getProperty("user.home") + "\\downloads\\";
	private static final String DEFAULT_UNIX_BASED_DOWNLOAD_DIRECTORY = System.getProperty("user.home") + "/downloads/";
	
	private Hashtable<String, Object> params;
	private FirefoxProfile ffp;
	private PlatformOperatingSystem pos;

	public FireFoxProfile() {
		ffp = new FirefoxProfile();
		pos = new PlatformOperatingSystem();
	}
	
	public FireFoxProfile(Hashtable<String, Object> params) {
		this();
		this.params = params;
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
	
	public FirefoxProfile getFirefoxProfile() {
		try {
			Set<String> keys = this.params.keySet();
			for (String key : keys) {
				if (EnumUtils.isValidEnum(standardProfilePreferenceNames.class, key.replaceAll(".", ""))) {
					this.setStandardProfilePreference(key, this.params.get(key).toString());
				} else if (key.contains("browser.download.dir")) {
					this.setFireBrowserDownLoad(this.params.get(key).toString());
				} else {
					logger.log(Level.INFO, "Preference name = '" + key + "'; Preferance value = '" + this.params.get(key) + "'");
					if (Converter.isNumeric(this.params.get(key).toString())) {
						this.ffp.setPreference(key, Integer.valueOf(this.params.get(key).toString()));
					} else if (Converter.isBoolean(this.params.get(key).toString())) {
						this.ffp.setPreference(key, Boolean.valueOf(this.params.get(key).toString()));
					} else {
						this.ffp.setPreference(key, this.params.get(key).toString());
					}
				}
			}
	
		} catch (Exception ex) {
			logger.catching(ex);
		}
		return this.ffp;
	}

	/**
	 * 
	 * @param standardProfilePrerenceName
	 * @param standardProfilePrerenceValue
	 */
	
	public void setStandardProfilePreference(String standardProfilePrerenceName, String standardProfilePrerenceValue) {
		logger.log(Level.INFO, FIREFOXPROFILE, "In method setStandardProfilePreference", "standardProfilePrerenceName = '" + standardProfilePrerenceName + ", standardProfilePrerenceValue = '" + standardProfilePrerenceValue + "'");
		try {
			if (!Converter.isBoolean(standardProfilePrerenceValue)) {
				logger.log(Level.WARN, FIREFOXPROFILE, "Standrd profile preference value '" + standardProfilePrerenceValue + "' for is NOT A BOOLEAN value. Setting the profile preference value to 'TRUE'");
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
				logger.log(Level.WARN, FIREFOXPROFILE, "Standrd profile preference '" + standardProfilePrerenceName + "' is NOT SUPPORTED.");
			}
		} catch (Exception ex) {
			logger.catching(ex);
		}
	}
	
	
	/**
	 * 
	 * @param downloodLocation
	 */
	private void setFireBrowserDownLoad(String downloodLocation) {
		try {
			File downloadlocation = new File(downloodLocation);
			if (downloadlocation.isDirectory()) {
				logger.log(Level.INFO, FIREFOXPROFILE, "Setting FireFox profile 'browser.download.dir' to '" + downloodLocation + "'");
				this.ffp.setPreference("browser.download.dir", downloodLocation);
			} else {
				if (pos.isWindows()) {
					logger.log(Level.WARN, "The provided download location '" + downloodLocation + "' is not a directory. Setting the download location to it's default value of '" + DEFAULT_WIN_DOWNLOAD_DIRECTORY + "'");
					this.ffp.setPreference("browser.download.dir", DEFAULT_WIN_DOWNLOAD_DIRECTORY);
				} else if (pos.isMac() || pos.isSolaris() || pos.isUnix()) {
					logger.log(Level.WARN, "The provided download location '" + downloodLocation + "' is not a directory. Setting the download location to it's default value of '" + DEFAULT_UNIX_BASED_DOWNLOAD_DIRECTORY + "'");
					this.ffp.setPreference("browser.download.dir", DEFAULT_UNIX_BASED_DOWNLOAD_DIRECTORY);
				}
			}
		} catch (Exception ex) {
			logger.catching(ex);
		}
	}
	
}