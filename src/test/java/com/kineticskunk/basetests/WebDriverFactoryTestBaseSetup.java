package com.kineticskunk.basetests;

/*
	Copyright [2016 - 2017] [KineticSkunk Information Technology Solutions]
	
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

import org.testng.annotations.AfterClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import com.kineticskunk.driverfactory.DriverFactory;
import com.kineticskunk.utilities.Converter;

public class WebDriverFactoryTestBaseSetup {
	
	private static int WAIT = 60;
	private Logger logger = LogManager.getLogger(WebDriverFactoryTestBaseSetup.class.getName());
	private Marker TESTBASESETUP = MarkerManager.getMarker("TESTBASESETUP");

	private DriverFactory df;
	private WebDriver wd;
	
	@BeforeClass
	@Parameters({ "browserType", "desiredCapabilitiesConfigJSON", "bringBrowserToFront", "resizeBrowser" })
	public void setDriver(String browserType, String desiredCapabilitiesConfigJSON, String bringBrowserToFront, String resizeBrowser) throws Exception {
		try {
			this.df =  new DriverFactory(browserType, desiredCapabilitiesConfigJSON);
			//this.df.setBringDriverToFront(Converter.toBoolean(bringBrowserToFront));
			//this.df.setResizeBrowser(Converter.toBoolean(resizeBrowser));
			this.wd = this.df.getDriver();
			this.wd.manage().timeouts().implicitlyWait(WAIT, TimeUnit.SECONDS);
		} catch (WebDriverException e) {
			this.logger.fatal(TESTBASESETUP, "Failed to load browser " + (char)34 + browserType + (char)34 + ".");
			this.logger.fatal(TESTBASESETUP, "Localized message = " + (char)34 + e.getLocalizedMessage() + (char)34 + ".");
			this.logger.debug(TESTBASESETUP, "Cause = " + (char)34 + e.getCause() + (char)34 + ".");
		}
	}

	public WebDriver getDriver() {
		return wd;
	}

	public void navigateToURL(String url) {
		this.wd.navigate().to(url);
	}

	@AfterClass
	public void quitDriver() {
		wd.quit();
	}

}