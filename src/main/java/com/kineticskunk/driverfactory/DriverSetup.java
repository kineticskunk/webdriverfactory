package com.kineticskunk.driverfactory;

import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kineticskunk.driverutilities.DesiredCapabilityException;

public interface DriverSetup {

    WebDriver getWebDriverObject(DesiredCapabilities desiredCapabilities);

    DesiredCapabilities getDesiredCapabilities(HashMap<String, Object> params, Proxy proxySettings) throws DesiredCapabilityException, IOException, Exception;
}
