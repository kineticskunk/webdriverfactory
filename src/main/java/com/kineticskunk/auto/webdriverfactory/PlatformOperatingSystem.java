package com.kineticskunk.auto.webdriverfactory;

import org.openqa.selenium.Platform;

/**
 * 
 * @author yodaqua
 *
 */

public class PlatformOperatingSystem {

	protected static String OS = System.getProperty("os.name").toLowerCase();

	protected static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	protected static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}

	protected static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	}

	protected static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}

	protected static String getOS(){
		if (isWindows()) {
			return "win";
		} else if (isMac()) {
			return "osx";
		} else if (isUnix()) {
			return "uni";
		} else if (isSolaris()) {
			return "sol";
		} else {
			return "err";
		}
	}

	protected static Platform getPlatform() {
		String os = getOS();
		if (os.contains("linux")) {
			return Platform.LINUX;
		}
		else if (os.contains("mac") || (os.contains("osx"))) {
			return Platform.MAC;
		}
		else if (os.contains("xp")) {
			return Platform.XP;
		}
		else if (os.contains("win7")) {
			return Platform.VISTA;
		}
		else if (os.contains("win8")) {
			return Platform.WIN8;
		}
		else if (os.contains("win")) {
			return Platform.WINDOWS;
		}
		else if (os.contains("android")) {
			return Platform.ANDROID;
		}
		else {
			return Platform.ANY;
		}
	}
}
