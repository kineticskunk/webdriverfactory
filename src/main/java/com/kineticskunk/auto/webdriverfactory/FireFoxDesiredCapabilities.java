package com.kineticskunk.auto.webdriverfactory;

import java.util.Hashtable;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kineticskunk.auto.logging.TestServiceLogging;

public class FireFoxDesiredCapabilities {
	
	private static final String SUCCESS_MESSAGE = "Success";
	private static final String FAILURE_MESSAGE = "Fail";

	private Hashtable<String, String> dcConfiguration;
	private DesiredCapabilities dc; 
	private TestServiceLogging tsl;
	
	public FireFoxDesiredCapabilities(Marker marker, Hashtable<String, String> dcConfiguration) {
		this.dcConfiguration = dcConfiguration;
		this.dc = new DesiredCapabilities();
		this.tsl = new TestServiceLogging(LogManager.getLogger(FireFoxDesiredCapabilities.class.getName()), marker);
	}

	public DesiredCapabilities loadDesiredCapabilities(Hashtable<String, String> desiredCapabilities) {
		this.setPlatform(PlatformOperatingSystem.getPlatform());
		this.setCapability("browser", this.dcConfiguration.get("BROWSER_NAME"), "FireFox");
		this.setCapability("acceptsslcertificates", this.dcConfiguration.get("ACCEPT_SSL_CERTS"), "accept");
		this.setEnableJavaScript(this.dcConfiguration.get("JAVASCIPT_ENABLED"));
		return this.dc;
	}

	/**
	 * Set the desired capabilities platform to local OS
	 */
	private void setPlatform(Platform pt) {
		tsl.enterLogger("In method setPlatform", "Operating system = '" + pt.name() + "'");
		try {
			this.dc.setCapability(CapabilityType.PLATFORM, pt);
			this.tsl.logMessage(Level.INFO, "Successfully set platform to '" + this.dc.getPlatform() + "'");
			this.tsl.exitLogger(SUCCESS_MESSAGE);
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger("Failed to set the 'platform' desired capability to '" + pt.name() + "'");
			this.tsl.exitLogger(FAILURE_MESSAGE);
		}
		this.tsl.exitLogger(SUCCESS_MESSAGE);
	}
	
	/**
	 * 
	 * @param capabilityName
	 * @param capabilityValue
	 */
	private void setCapability(String capabilityName, String capabilityValue, String defaultCapabilityValue) {
		tsl.enterLogger("In method setPlatform", "Operating system = '" + capabilityValue + "'");
		try {
			this.dc.setCapability(this.getCapabilityType(capabilityName), capabilityValue);
			this.tsl.logMessage(Level.INFO, "Successfully set platform to '" + capabilityValue + "'");
			this.tsl.exitLogger(SUCCESS_MESSAGE);
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger("Failed to set the 'platform' desired capability to '" + capabilityValue + "'");
			this.tsl.exitLogger(FAILURE_MESSAGE);
		}
		this.tsl.exitLogger(SUCCESS_MESSAGE);
	}
	
	/**
	 * 
	 * @param capabilityName
	 * @return
	 */
	private String getCapabilityType(String capabilityName) {
		switch (capabilityName.toLowerCase()) {
		case "accept_ssl_certificates":
			return CapabilityType.ACCEPT_SSL_CERTS;
		case "browser":
			return CapabilityType.BROWSER_NAME;
		case "element_scroll_behavior":
			return CapabilityType.ELEMENT_SCROLL_BEHAVIOR;
		case "platform":
			return CapabilityType.PLATFORM;
		}
		
		
		return null;
	}

	/**
	 * 
	 * @param browserName
	 */
	private void setBrowserName(String browserName) {
		tsl.enterLogger("In method setPlatform", "Operating system = '" + browserName + "'");
		try {
			this.dc.setCapability(CapabilityType.BROWSER_NAME, browserName);
			this.tsl.logMessage(Level.INFO, "Successfully set platform to '" + browserName + "'");
			this.tsl.exitLogger(SUCCESS_MESSAGE);
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger("Failed to set the 'platform' desired capability to '" + browserName + "'");
			this.tsl.exitLogger(FAILURE_MESSAGE);
		}
		this.tsl.exitLogger(SUCCESS_MESSAGE);
	}

	/**
	 * 
	 * @param javascriptEnabled
	 */
	private void setEnableJavaScript(String javascriptEnabled) {
		tsl.enterLogger("In method setEnableJavaScript", "javascriptEnabled = '" + javascriptEnabled + "'");
		try {
			this.dc.setJavascriptEnabled(Boolean.valueOf(javascriptEnabled));
			this.tsl.exitLogger("Successfully set 'javascriptEnabled' has been set '" + this.dc.getCapability("javascriptEnabled") + "'");
			this.tsl.exitLogger(SUCCESS_MESSAGE);
		} catch (Exception ex) {
			this.tsl.catchException(ex);
			this.tsl.exitLogger("Failed to set the 'javascriptEnabled' desired capability to '" + javascriptEnabled.toUpperCase() + "'");
			this.tsl.exitLogger(FAILURE_MESSAGE);
		}
		this.tsl.exitLogger(SUCCESS_MESSAGE);
	}



}
