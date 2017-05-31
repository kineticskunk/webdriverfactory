package com.kineticskunk.auto.webdriverfactory;

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
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.kineticskunk.basetests.TestBaseSetup;

public class DriverFactoryTestNG extends TestBaseSetup {
	
	private final Logger logger = LogManager.getLogger(DriverFactoryTestNG.class.getName());
	private final Marker LOADDESIREDCAPABILITIES = MarkerManager.getMarker("LOADDESIREDCAPABILITIES");
	
	@BeforeClass
	@Parameters({ "url" })
	public void beforeDriverFactoryTestNG(String url) {
		navigateToURL(url);
	}
	
	@Test(priority = 1, groups = "BrowserVerification")
	@Parameters( { "browserTitle" })
	public void verifyBrowserType(String browserTitle) {
		Assert.assertTrue(getDriver().getTitle().equalsIgnoreCase(browserTitle));
	}
	
	@AfterClass
	public void afterFireFoxDriverFactoryTestNG() {
		
	}
}
