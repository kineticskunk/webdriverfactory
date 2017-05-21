package com.kineticskunk.mongo;

import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;

public class WebDriverConfigData {
	
	private static Logger logger = LogManager.getLogger(Thread.currentThread().getName());

	BasicDBObject query = new BasicDBObject();
	BasicDBObject updateDBObject = new BasicDBObject();
	BasicDBObject personalDetails = new BasicDBObject();
	DBCursor cursor = null;
	private DBCollection collection = null;

	private final String localhost = "localhost";
	private final int port = 27017;
	private final String dbName = "WebDriverConfig";

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
	
	private WebDriverConfigData(String host, int port) throws UnknownHostException {
		mongoClient = new MongoClient(host, port);
		db = mongoClient.getDatabase(dbName);
		db
	}
	
	

}
