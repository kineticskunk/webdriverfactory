package com.kineticskunk.auto.webdriverfactory;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import java.io.IOException;
import java.util.Hashtable;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kineticskunk.auto.webdriverfactory.DesiredCapabilityException;

public class FireFoxIndividualDesiredCapabilitiesTestNG {
	
	private static final String USE_HOST_PLATFORM = "use_host_platform";
	private static final String USE_HOST_OS_VERSION = "use_host_os_version";
	
	private static DesiredCapabilities dc;
	private static FireFoxDesiredCapabilities ffdc;
	private static Hashtable<String, String> dcConfig;
	private static Utilties utils;
	
	public FireFoxIndividualDesiredCapabilitiesTestNG() {
		dc = null;
		ffdc = null;
		dcConfig = new Hashtable<String, String>();
	}
	
	@Parameters ( "desiredCapabilitiesConfigurationFile" )
	@BeforeClass
	public static void setupTestClass(String desiredCapabilitiesConfigurationFile) throws IOException, DesiredCapabilityException {
		utils = Utilties.getInstance();
		dcConfig = utils.readPropertyFileIntoHashtable(dcConfig, desiredCapabilitiesConfigurationFile);
	}
	
	@BeforeTest
	public void runTestSetup() {
		ffdc = new FireFoxDesiredCapabilities();
	}
	
	@AfterTest
	public void tearDownTest() {
		ffdc = null;
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifyAcceptSslCertsCapability() {
		ffdc.setAcceptSslCerts("true");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.ACCEPT_SSL_CERTS).equals(true));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifyBrowserNameCapability() {
		ffdc.setBrowserName("firefox");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.BROWSER_NAME).equals("firefox"));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifyEnrollmentBehaviourCapability() {
		ffdc.setEnrollmentBehaviour("1");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR).equals(1));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifyEnableProfilingCapability() {
		ffdc.setEnableProfilingCapability("true");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.ENABLE_PROFILING_CAPABILITY).equals(true));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifyHasNativeEventsCapability() {
		ffdc.setHasNativeEvents("true");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.HAS_NATIVE_EVENTS).equals(true));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifyHasTouchScreenCapability() {
		ffdc.setHasTouchScreen("true");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.HAS_TOUCHSCREEN).equals(true));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifyLoggingPrefsCapabilityServerCapability() {
		ffdc.setLoggingPrefs("ALL");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(ffdc.getLoggingPreferences("server").equalsIgnoreCase("ALL"));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifyOverlappingCheckDisabledCapability() {
		ffdc.setOverlappingCheckDisabled("true");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.OVERLAPPING_CHECK_DISABLED).equals(true));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifyPageLoadStrategyUnstableCapability() {
		ffdc.setPageLoadStrategy("unstable");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.PAGE_LOAD_STRATEGY).equals("unstable"));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifyPlaformCapability() {
		ffdc.setPlatform(USE_HOST_PLATFORM);
		dc = ffdc.getFireFoxDesiredCapabilities();
		PlatformOperatingSystem pos = new PlatformOperatingSystem();
		Assert.assertTrue(dc.getCapability(CapabilityType.PLATFORM).equals(pos.getHostPlatform()));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifyProxyAutoDetectCapability() {
		ffdc.setProxy("autodetect");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.PROXY).equals("autodetect"));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifyRotableCapability() {
		ffdc.setRotable("false");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.ROTATABLE).equals(false));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifySupportAlertsCapability() {
		ffdc.setSupportAlerts("true");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.SUPPORTS_ALERTS).equals(true));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifySupportsApplicationCacheCapability() {
		ffdc.setSupportsApplicationCache("true");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.SUPPORTS_APPLICATION_CACHE).equals(true));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifySupportsFindingByCSSCapability() {
		ffdc.setSupportsFindingByCSS("true");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS).equals(true));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifySupportsJavaScriptCapability() {
		ffdc.setSupportsJavaScript("true");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.SUPPORTS_JAVASCRIPT).equals(true));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifySupportsLocationContextCapability() {
		ffdc.setSupportsLocationContext("true");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.SUPPORTS_LOCATION_CONTEXT).equals(true));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifySupportsNetworkConnectionCapability() {
		ffdc.setSupportsNetworkConnection("true");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.SUPPORTS_NETWORK_CONNECTION).equals(true));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifySupportsSQLDatabaseCapability() {
		ffdc.setSupportsSQLDatabase("true");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.SUPPORTS_SQL_DATABASE).equals(true));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifySupportsWebStorageCapability() {
		ffdc.setSupportsWebStorage("true");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.SUPPORTS_WEB_STORAGE).equals(true));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifyTakesScreenShoteCapability() {
		ffdc.setTakesScreenShot("true");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.TAKES_SCREENSHOT).equals(true));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifyUnexpectedAlertBehaviourCapability() {
		ffdc.setUnexpectedAlertBehaviour("accept");
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR).equals("accept"));
	}
	
	@Test (priority = 0, groups = "desiredcapabilities")
	public void verifyVersionCapability() {
		ffdc.setVersion(USE_HOST_OS_VERSION);
		dc = ffdc.getFireFoxDesiredCapabilities();
		Assert.assertTrue(dc.getCapability(CapabilityType.VERSION).equals(System.getProperty("os.version")));
	}

}