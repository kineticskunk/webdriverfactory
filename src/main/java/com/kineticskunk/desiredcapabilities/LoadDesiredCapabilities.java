package com.kineticskunk.desiredcapabilities;

import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import com.kineticskunk.mongo.WebDriverConfigData;

public class LoadDesiredCapabilities {
	
	private static final Logger logger = LogManager.getLogger(Thread.currentThread().getName());
	private static final Marker LOADDESIREDCAPABILITIES = MarkerManager.getMarker("LOADDESIREDCAPABILITIES");
	
	private WebDriverConfigData wdcd = new WebDriverConfigData();
	
	public LoadDesiredCapabilities(String host, int port) throws UnknownHostException {
		this.wdcd.setHost(host);
		this.wdcd.setPort(port);
		this.wdcd.setWebDriverConfigDBMongoClient();
		this.wdcd.setWebDriverConfigDBMongo();
	}
	
	

}
