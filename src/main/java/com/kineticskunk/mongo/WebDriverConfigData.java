package com.kineticskunk.mongo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

public class WebDriverConfigData {

	private static Logger logger = LogManager.getLogger(Thread.currentThread().getName());

	BasicDBObject query = new BasicDBObject();
	BasicDBObject updateDBObject = new BasicDBObject();
	BasicDBObject personalDetails = new BasicDBObject();
	DBCursor cursor = null;
	private DBCollection collection = null;
	private static String collectionName = "webDriverCollection";

	private String host = "localhost";
	private int port = 27017;
	private String dbName = "WebDriverConfigDB";

	private MongoDatabase db = null;
	private MongoClient mongoClient = new MongoClient();
	private BasicDBObject searchQuery = new BasicDBObject();
	private DBCollection table = null;
	private BasicDBObject companymandateObject = new BasicDBObject();
	private BasicDBObject trustmandatetableObject = new BasicDBObject();
	private BasicDBObject pageverificationObject = new BasicDBObject();
	private BasicDBObject passwordtableObject = new BasicDBObject();
	private BasicDBObject afrifocusObject = new BasicDBObject();
	private BasicDBObject yeboyethuMinorMandateTable = new BasicDBObject();
	private BasicDBObject yeboyethuBackOfficeTable = new BasicDBObject();
	private BasicDBObject yeboyethuIndividualMandateTable = new BasicDBObject();

	public WebDriverConfigData() throws UnknownHostException {
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public void setWebDriverConfigDBMongoClient() {
		this.mongoClient = new MongoClient(this.host, this.port);
	}
	
	public void setWebDriverConfigDBMongo() {
		this.db = this.mongoClient.getDatabase(this.dbName);
	}

	public WebDriverConfigData(String host) throws UnknownHostException {
		this.mongoClient = new MongoClient(host, this.port);
		this.db = mongoClient.getDatabase(this.dbName);
	}

	public WebDriverConfigData(String host, int port, String dbName) throws UnknownHostException {
		this.mongoClient = new MongoClient(host, port);
		this.db = mongoClient.getDatabase(dbName);
		this.db.createCollection(WebDriverConfigData.collectionName);
	}
	
	public WebDriverConfigData(String host, int port, String dbName, String collectionName) throws UnknownHostException {
		this.mongoClient = new MongoClient(host, port);
		this.db = mongoClient.getDatabase(dbName);
		this.db.createCollection(collectionName);
	}

	public void loadWebDriverConfigDocument(String config, String collectionName, String resourceFile) {
		this.db.getCollection(collectionName);
		File f = new File(this.getClass().getClassLoader().getResource(resourceFile).getPath());
		DBObject obj = (DBObject) JSON.parse(f.getAbsolutePath());
		this.collection.insert(obj);
	}
	 
	

}
