package com.kineticskunk.auto.webdriverfactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

public class Utilties {

	/**
	 * 
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c)) return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public boolean isBoolean(String value) {
		if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param configFileName
	 * @return
	 * @throws IOException
	 */
	private InputStream getResourceInputStream(String configFileName) throws IOException {
		return this.getClass().getClassLoader().getResourceAsStream(configFileName);
	}
	
	/**
	 * 
	 * @param testData
	 * @param propertiesFile
	 * @return
	 * @throws IOException
	 */
	public Hashtable<String, String> readPropertyFileIntoHashtable(Hashtable<String, String> testData, String propertiesFile)  throws IOException {
		Properties prop = new Properties();
		InputStream inputStream = getResourceInputStream(propertiesFile);
		Hashtable<String, String> propvals = new Hashtable<String, String>();
		try {
			prop.load(inputStream);
			Set<String> propertyNames = prop.stringPropertyNames();
			for (String Property : propertyNames) {
				if (prop.getProperty(Property).equalsIgnoreCase("")) {
					propvals.put(Property, "");
				} else if (prop.getProperty(Property).equals(null)) {
					propvals.put(Property, null);
				} else {
					propvals.put(Property, prop.getProperty(Property));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!testData.isEmpty()) {
			propvals.putAll(testData);
		}
		return propvals;
	}
}