package com.kineticskunk.driverfactory;

import java.io.IOException;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kineticskunk.driverutilities.DesiredCapabilityException;

public interface DriverSetup {

    WebDriver getWebDriverObject(DesiredCapabilities desiredCapabilities);

    DesiredCapabilities getDesiredCapabilities(String browserType, String desiredCapabilitiesConfigJSON) throws DesiredCapabilityException, IOException, Exception;
}
