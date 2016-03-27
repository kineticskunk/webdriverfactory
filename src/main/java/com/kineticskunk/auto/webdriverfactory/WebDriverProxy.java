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

package com.kineticskunk.auto.webdriverfactory;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;

public class WebDriverProxy {

	private Proxy proxy;

	public WebDriverProxy() {
		this.proxy = new Proxy();
	}

	public void setHTTPProoxy(String httpProxy) {
		this.proxy.setHttpProxy(httpProxy);
	}

	public void setSSLProxy(String sslProxy) {
		this.proxy.setSslProxy(sslProxy);
	}

	public void setFTPProxy(String ftpProxy) {
		this.proxy.setFtpProxy(ftpProxy);
	}

	public Proxy getWebDriverProxy() {
		this.proxy.setProxyType(ProxyType.MANUAL);
		return this.proxy;
	}

	public ProxyType setProxyType(String proxyType) {
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
			return ProxyType.valueOf(proxyType);
		}
	}
}