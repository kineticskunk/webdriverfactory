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

package com.kineticskunk.driverutilities;

import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;

import  org.openqa.selenium.WebDriverException;
import java.net.MalformedURLException;

public class WebDriverProxy {
	
	private final Logger logger = LogManager.getLogger(Thread.currentThread().getName());
	private final Marker WEBDRIVERPROXY = MarkerManager.getMarker("WEBDRIVERPROXY");

	private Proxy proxy;
	
	public WebDriverProxy() {
		this.proxy = new Proxy();
	}
	
	public Proxy getProxy() {
		return this.proxy;
	}
	
	/**
	 * Set proxy auto detection to true or false
	 * @param autodetect
	 */
	public void setAutoDetect(boolean autodetect) {
		try {
			if (this.proxy.isAutodetect() != autodetect) {
				this.proxy.setAutodetect(autodetect);
			}
		} catch (WebDriverException wde) {
			this.logger.error(WEBDRIVERPROXY, wde.getLocalizedMessage());
		}
	}
	
	/**
	 * Set auto detection configuration URL
	 * @param url
	 */
	public void setAutoconfigUrl(URL url) throws MalformedURLException {
		try {
			this.proxy.setProxyAutoconfigUrl(url.toString());
		} catch (WebDriverException wde) {
			this.logger.error(WEBDRIVERPROXY, wde.getLocalizedMessage());
		}
	}
	
	/**
	 * 
	 */
	public void setHTTPProxy(String httpProxy) {
		try {
			this.proxy.setHttpProxy(httpProxy);
		} catch (WebDriverException wde) {
			this.logger.error(WEBDRIVERPROXY, wde.getLocalizedMessage());
		}
	}
	
	/**
	 * 
	 * @param sslProxy
	 */
	public void setSSLProxy(String sslProxy) {
		try {
			this.proxy.setSslProxy(sslProxy);
		} catch (WebDriverException wde) {
			this.logger.error(WEBDRIVERPROXY, wde.getLocalizedMessage());
		}
	}

	public void setFTPProxy(String ftpProxy) {
		try {
			this.proxy.setFtpProxy(ftpProxy);
		} catch (WebDriverException wde) {
			this.logger.error(WEBDRIVERPROXY, wde.getLocalizedMessage());
		}
	}

	public void setProxyType(String proxyType) {
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
				this.logger.info(WEBDRIVERPROXY, "Proxy type " +  proxyType + " is not supported. Defaulting to ProxyType.AUTODETECT");
				this.proxy.setProxyType(ProxyType.AUTODETECT);
				break;
			}
		} catch (WebDriverException wde) {
			this.logger.error(WEBDRIVERPROXY, wde.getLocalizedMessage());
		}
	}
	
}