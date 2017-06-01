package com.kineticskunk.driverutilities;

/*
	Copyright [2016] [KineticSkunk Information Technology Solutions]

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.json.simple.JSONObject;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import  org.openqa.selenium.WebDriverException;

public class WebDriverProxy {
	
	private final Logger logger = LogManager.getLogger(WebDriverProxy.class.getName());
	private final Marker WEBDRIVERPROXY = MarkerManager.getMarker("WEBDRIVERPROXY");
	
	private JSONObject proxyPreferences = new JSONObject();
	private Proxy proxy;
	
	public WebDriverProxy() {
		this.proxy = new Proxy();
	}
	
	public WebDriverProxy(JSONObject proxyPreferences) {
		this();
		this.proxyPreferences = proxyPreferences;
	}
	
	public Proxy getProxy() {
		return this.proxy;
	}
	
	/**
	 * Set proxy auto detection to true or false
	 * @param autodetect
	 */
	public void setAutoDetect(boolean autodetect) {
		this.logger.info(WEBDRIVERPROXY, "Setting autodetect to " + (char)34 + autodetect + (char)34 +".");
		try {
			this.proxy.setAutodetect(autodetect);
		} catch (WebDriverException wde) {
			this.logger.error(WEBDRIVERPROXY, "Failed to set autodetect to " + (char)34 + autodetect + (char)34 +".");
			this.logger.error(WEBDRIVERPROXY, "Localized message = " + wde.getLocalizedMessage());
			this.logger.error(WEBDRIVERPROXY, "Cause = " + wde.getCause().getMessage());
		}
	}
	
	/**
	 * Set auto detection configuration URL
	 * @param url
	 */
	public void setAutoconfigUrl(String url) {
		this.logger.info(WEBDRIVERPROXY, "Setting AutoconfigUrl to " + (char)34 + url + (char)34 +".");
		try {
			this.proxy.setProxyAutoconfigUrl(url);
		} catch (WebDriverException wde) {
			this.logger.error(WEBDRIVERPROXY, "Failed to set AutoconfigUrl to " + (char)34 + url + (char)34 +".");
			this.logger.error(WEBDRIVERPROXY, "Localized message = " + wde.getLocalizedMessage());
			this.logger.error(WEBDRIVERPROXY, "Cause = " + wde.getCause().getMessage());
		}
	}
	
	public void setProxyServer(String serverType, String server, String port) {
		this.logger.info(WEBDRIVERPROXY, "Setting " + (char)34 + serverType + (char)34 + " to " + (char)34 + server + ":" + port + (char)34 +".");
		String proxyURL = server + ":" + port;
		try {
			switch (serverType.toUpperCase()) {
			case "FTP": case "FTPPROXY":
				this.proxy.setFtpProxy(proxyURL);
				break;
			case "HTTP": case "HTTPPROXY":
				this.proxy.setHttpProxy(proxyURL);
				break;
			case "SSL":  case "SSLPROXY":
				this.proxy.setSslProxy(proxyURL);
				break;
			case "SOCKS": case "SOCKSPROXY":
				this.proxy.setSocksProxy(proxyURL);
				break;
			default:
				this.logger.error(WEBDRIVERPROXY, "Proxy server type " + (char)34 + serverType + (char)34 + " is not supported.");
				this.setAutoDetect(true);
				this.setProxyType("AUTODETECT");
				break;
			}
		} catch (WebDriverException wde) {
			this.logger.error(WEBDRIVERPROXY, "Failed to set " + (char)34 + serverType + (char)34 + " to " + (char)34 + proxyURL + (char)34 +".");
			this.logger.error(WEBDRIVERPROXY, "Localized message = " + wde.getLocalizedMessage());
			this.logger.error(WEBDRIVERPROXY, "Cause = " + wde.getCause().getMessage());
		}
		
	}
	
	public void logProxySettings() {
		this.logger.info(WEBDRIVERPROXY, "AutoconfigUrl has been set to " + (char)34 + this.proxy.getProxyAutoconfigUrl() + (char)34 +".");
		this.logger.info(WEBDRIVERPROXY, "NoProxy has been set to " + (char)34 + this.proxy.getNoProxy() + (char)34 +".");
		this.logger.info(WEBDRIVERPROXY, "ProxyType has been set to " + (char)34 + this.proxy.getProxyType() + (char)34 +".");
		this.logger.info(WEBDRIVERPROXY, "FtpProxy has been set to " + (char)34 + this.proxy.getFtpProxy() + (char)34 +".");
		this.logger.info(WEBDRIVERPROXY, "HttpProxy has been set to " + (char)34 + this.proxy.getHttpProxy() + (char)34 +".");
		this.logger.info(WEBDRIVERPROXY, "SslProxy has been set to " + (char)34 + this.proxy.getSslProxy() + (char)34 +".");
		this.logger.info(WEBDRIVERPROXY, "SocksProxy has been set to " + (char)34 + this.proxy.getSocksProxy() + (char)34 +".");
		this.logger.info(WEBDRIVERPROXY, "SocksPassword has been set to " + (char)34 + this.proxy.getSocksPassword() + (char)34 +".");
		this.logger.info(WEBDRIVERPROXY, "SocksUsername has been set to " + (char)34 + this.proxy.getSocksUsername() + (char)34 +".");
	}

	public void setProxyType(String proxyType) {
		this.logger.info(WEBDRIVERPROXY, "Setting proxy type to " + (char)34 + proxyType + (char)34 + ".");
		try {
			switch (proxyType.toUpperCase()) {
			case "AUTODETECT":
				this.proxy.setProxyType(ProxyType.AUTODETECT);
				break;
			case "DIRECT":
				this.proxy.setProxyType(ProxyType.DIRECT);
				break;
			case "MANUAL":
				this.proxy.setProxyType(ProxyType.MANUAL);
				break;
			case "PAC":
				this.proxy.setProxyType(ProxyType.PAC);
				break;
			case "RESERVED_1":
				this.proxy.setProxyType(ProxyType.RESERVED_1);
				break;
			case "SYSTEM":
				this.proxy.setProxyType(ProxyType.SYSTEM);
				break;
			case "UNSPECIFIED":
				this.proxy.setProxyType(ProxyType.UNSPECIFIED);
				break;
			default:
				this.logger.info(WEBDRIVERPROXY, "Proxy type " + (char)34 + proxyType + (char)34 + " is not supported. Defaulting to ProxyType.AUTODETECT");
				this.proxy.setProxyType(ProxyType.AUTODETECT);
				break;
			}
		} catch (WebDriverException wde) {
			this.logger.error(WEBDRIVERPROXY, wde.getLocalizedMessage());
		}
	}
	
}