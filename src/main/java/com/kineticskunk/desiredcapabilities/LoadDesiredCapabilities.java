package com.kineticskunk.desiredcapabilities;



import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.remote.DesiredCapabilities;



public class LoadDesiredCapabilities {

	private static final Logger logger = LogManager.getLogger(Thread.currentThread().getName());
	private static final Marker LOADDESIREDCAPABILITIES = MarkerManager.getMarker("LOADDESIREDCAPABILITIES");

	private JSONParser parser = new JSONParser();
	private DesiredCapabilities dc;
	private String browserType = null;
	
	public LoadDesiredCapabilities() {
	}
	
	public LoadDesiredCapabilities(String browserType) {
		this.browserType = browserType;
	}


	public DesiredCapabilities createDesiredCapabilities(String browserType, String resourceFile) {
		dc = null;
		try {
			dc.setBrowserName(browserType);
			Object obj = this.parser.parse(new FileReader(new File(this.getClass().getClassLoader().getResource(resourceFile).getPath())));
			JSONObject jsonObject = (JSONObject) obj;
			if (jsonObject != null) { 
				Iterator<?> iterator = jsonObject.entrySet().iterator(); 
				while (iterator.hasNext()) { 
					Entry<?, ?> entry = (Entry<?, ?>) iterator.next(); 
					dc.setCapability(entry.getKey().toString(), entry.getValue()); 
				} 
			}
		} catch (Exception ex) {

		}
		return dc;
	}

}
