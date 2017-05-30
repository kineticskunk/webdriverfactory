package com.kineticskunk.driverfactory;

import com.kineticskunk.driverutilities.PlatformOperatingSystem;

import java.io.File;
import com.sun.jna.Native;
import com.sun.jna.Library;

interface CLibrary extends Library {
	public int chmod(String path, int mode);
}

public class DriverExecutable {
	
	private static final String GECKODRIVERMAC64 = "geckodriver-v0.16.1-mac64";
	private static final String GECKODRIVERLINUX64 = "geckodriver-v0.16.1-linux64";
	private static final String GECKODRIVERWINDOWS64 = "geckodriver-v0.16.1-win64.exe";
	private static final String GECKODRIVERWINDOWS32 = "geckodriver-v0.16.1-win32.exe";
	
	private static final String CHROMEDRIVERMAC64 = "chromedriver-v2.29-mac64";
	private static final String CHROMEDRIVERLINUX64 = "chromedriver-v2.29-linux64";
	private static final String CHROMEDRIVERWINDOWS64 = "chromedriver-v2.29-win32.exe";
	private static final String CHROMEDRIVERWINDOWS32 = "chromedriver-v2.29-win32.exe";
	
	private static final String OPERADRIVERMAC64 = "operadriver-v2.27-mac64";
	private static final String OPERADRIVERLINUX64 = "operadriver-v2.27-linux64";
	private static final String OPERADRIVERLINUX32 = "operadriver-v2.27-linux32";
	private static final String OPERADRIVERWINDOWS64 = "operadriver-v2.27-win64.exe";
	private static final String OPERADRIVERWINDOWS32 = "operadriver-v2.27-win32.exe";
	
	private PlatformOperatingSystem pos = new PlatformOperatingSystem();
	
	private String browserType = null;
	private File driverExecutable = null;
	
	public DriverExecutable(String browserType) {
		this.browserType = browserType;
	}
	
	public void setDriverExecutable()  {
		switch (this.browserType.toLowerCase()) {
		case "firefox":
			if (this.pos.isMac() && System.getProperty("os.arch").contains("64")) {
				this.driverExecutable = new File(this.getClass().getClassLoader().getResource(GECKODRIVERMAC64).getPath());
			} else if (this.pos.isUnix() && System.getProperty("os.arch").contains("64")) {
				this.driverExecutable = new File(this.getClass().getClassLoader().getResource(GECKODRIVERLINUX64).getPath());
			} else if (this.pos.isWindows()) {
				if (System.getProperty("os.arch").contains("64")) {
					this.driverExecutable = new File(this.getClass().getClassLoader().getResource(GECKODRIVERWINDOWS64).getPath());
				} else if (System.getProperty("os.arch").contains("32")) {
					this.driverExecutable = new File(this.getClass().getClassLoader().getResource(GECKODRIVERWINDOWS32).getPath());
				}
			}
			System.setProperty("webdriver.gecko.driver", this.driverExecutable.getAbsolutePath());
			break;
		case "chrome":
			if (this.pos.isMac() && System.getProperty("os.arch").contains("64")) {
				driverExecutable = new File(this.getClass().getClassLoader().getResource(CHROMEDRIVERMAC64).getPath());
			} else if (this.pos.isUnix() && System.getProperty("os.arch").contains("64")) {
				this.driverExecutable = new File(this.getClass().getClassLoader().getResource(CHROMEDRIVERLINUX64).getPath());
			} else if (this.pos.isWindows()) {
				if (System.getProperty("os.arch").contains("64")) {
					this.driverExecutable = new File(this.getClass().getClassLoader().getResource(CHROMEDRIVERWINDOWS64).getPath());
				} else if (System.getProperty("os.arch").contains("32")) {
					this.driverExecutable = new File(this.getClass().getClassLoader().getResource(CHROMEDRIVERWINDOWS32).getPath());
				}
			}
			System.setProperty("webdriver.chrome.driver", this.driverExecutable.getAbsolutePath());
			break;
		case "opera":
			if (this.pos.isMac() && System.getProperty("os.arch").contains("64")) {
				driverExecutable = new File(this.getClass().getClassLoader().getResource(OPERADRIVERMAC64).getPath());
			} else if (this.pos.isUnix()) {
				if (System.getProperty("os.arch").contains("64")) {
					this.driverExecutable = new File(this.getClass().getClassLoader().getResource(OPERADRIVERLINUX64).getPath());
				} else if (System.getProperty("os.arch").contains("32")) {
					this.driverExecutable = new File(this.getClass().getClassLoader().getResource(OPERADRIVERLINUX32).getPath());
				}
			} if (this.pos.isWindows()) {
				if (System.getProperty("os.arch").contains("64")) {
					this.driverExecutable = new File(this.getClass().getClassLoader().getResource(OPERADRIVERWINDOWS64).getPath());
				} else if (System.getProperty("os.arch").contains("32")) {
					this.driverExecutable = new File(this.getClass().getClassLoader().getResource(OPERADRIVERWINDOWS32).getPath());
				}
			}
			System.setProperty("webdriver.opera.driver", this.driverExecutable.getAbsolutePath());
			break;
		}
		this.makeDriverExecutable(this.driverExecutable.getAbsolutePath());
	}
	
	private void makeDriverExecutable(String driverFile) {
		CLibrary libc = (CLibrary) Native.loadLibrary("c", CLibrary.class);
		libc.chmod(driverFile, 0755);
	}

}
