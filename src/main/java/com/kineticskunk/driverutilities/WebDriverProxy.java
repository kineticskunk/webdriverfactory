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

import java.util.Hashtable;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import com.kineticskunk.utilities.Converter;

public class WebDriverProxy {
	
	private static final String HTTP_PROXY = "httpProxy";
	private static final String SSL_PROXY = "sslProxy";
	private static final String FTP_PROXY = "ftpProxy";

	private Proxy proxy;
	private Hashtable<String, String> proxyConfig;
	
	public WebDriverProxy() {
		this.proxy = new Proxy();
	}
	
	public WebDriverProxy(Hashtable<String, String> proxyConfig) {
		this();
		this.proxyConfig = proxyConfig;
	}
	
	/**
	 * Set proxy auto detection to true or false
	 * @param autodetect
	 */
	public void setAutoDetect(boolean autodetect) {
		if (this.proxy.isAutodetect() != autodetect) {
			this.proxy.setAutodetect(autodetect);
		}
	}
	
	/**
	 * Set auto detection configuration URL
	 * @param url
	 */
	public void setAutoconfigUrl(String url) {
		this.proxy.setProxyAutoconfigUrl(Converter.toURL(url).toString());
	}
	
	/**
	 * 
	 */
	public void setHTTPProxy() {
		if (this.proxyConfig.containsKey(HTTP_PROXY)) {
			this.proxy.setHttpProxy(this.proxyConfig.get(HTTP_PROXY));
		} else {
			this.proxy.setHttpProxy("AUTODETECT");
		}
	}
	
	/**
	 * 
	 * @param sslProxy
	 */
	public void setSSLProxy(String sslProxy) {
		this.proxy.setSslProxy(this.proxyConfig.get(SSL_PROXY));
	}

	public void setFTPProxy(String ftpProxy) {
		this.proxy.setFtpProxy(this.proxyConfig.get(FTP_PROXY));
	}

	public Proxy getSSLProxy() {
		return this.proxy;
	}
	
	public Proxy getHTTPProxy() {
		return this.proxy;
	}

	public ProxyType getProxyType(String proxyType) {
		switch (proxyType.toUpperCase()) {
		case "AUTODETECT":
			return ProxyType.AUTODETECT;
		case "DIRECT":
			return ProxyType.DIRECT;
		case "MANUAL":
			return ProxyType.MANUAL;
		case "PAC":
			return ProxyType.PAC;
		case "RESERVED_1":
			return ProxyType.RESERVED_1;
		case "SYSTEM":
			return ProxyType.SYSTEM;
		case "UNSPECIFIED":
			return ProxyType.UNSPECIFIED;
		default:
			return ProxyType.UNSPECIFIED;
		}
	}
	
	private class WebDriverNetworkProxy {
		
		private Hashtable<String, String> networkProxyConfig;
		
		private WebDriverNetworkProxy(Hashtable<String, String> networkProxyConfig) {
			this.networkProxyConfig = networkProxyConfig;
		}
		
		
		
	}
	
}