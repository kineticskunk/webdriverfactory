package com.kineticskunk.driverfactory;

import java.io.File;
import com.sun.jna.Native;
import com.kineticskunk.utilities.PlatformOperatingSystem;
import com.sun.jna.Library;

interface CLibrary extends Library {
	public int chmod(String path, int mode);
}

public class DriverExecutable {
	
	private PlatformOperatingSystem pos = new PlatformOperatingSystem();
	
	private String browserType = null;
	private File driverExecutable = null;
	
	public DriverExecutable(String browserType) {
		this.browserType = browserType;
	}
	
	public void setDriverExecutable()  {
		switch (this.browserType.toLowerCase()) {
		case "firefox":
			if (this.pos.isMac() && System.getProperty("os.arch").contains("64") && this.browserType.equalsIgnoreCase("firefox")) {
				driverExecutable = new File(this.getClass().getClassLoader().getResource("geckodrivermac64").getPath());
				System.setProperty("webdriver.gecko.driver", this.driverExecutable.getAbsolutePath());
				//System.setProperty("webdriver.firefox.bin","/Users/yodaqua/ks-test-automation/firefox-lib/Firefox_53.app/Contents/MacOS/firefox-bin");
			}
			break;
		case "chrome":
			if (pos.isMac() && System.getProperty("os.arch").contains("64") && this.browserType.equalsIgnoreCase("chrome")) {
				driverExecutable = new File(this.getClass().getClassLoader().getResource("chromedrivermac64").getPath());
				System.setProperty("webdriver.chrome.driver", this.driverExecutable.getAbsolutePath());
			}
			break;
		}
		this.makeDriverExecutable(this.driverExecutable.getAbsolutePath());
	}

	private void makeDriverExecutable(String driverFile) {
		CLibrary libc = (CLibrary) Native.loadLibrary("c", CLibrary.class);
		libc.chmod(driverFile, 0755);
	}

}
