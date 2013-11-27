package com.cetsoft.caudit.observers;

import java.net.UnknownHostException;

import com.cetsoft.caudit.observable.AuditEvent;
import com.cetsoft.caudit.observer.AuditObserver;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class MongoObserver implements AuditObserver{
	
	private DBCollection collection;
	private final static String DEFAULT_PORT = "27017";
	private final static String DEFAULT_COLNAME = "caudit";
	
	public MongoObserver(String connectionString, String dbName) {
		this(connectionString,dbName, DEFAULT_PORT);
	}
	
	public MongoObserver(String connectionString, String dbName, String port) {
		this(connectionString,dbName, port, DEFAULT_COLNAME);
	}
	
	public MongoObserver(String connectionString, String dbName, String port, String collectionName) {
		try {
			MongoClient mongo = new MongoClient(connectionString, Integer.parseInt(port));
			DB db = mongo.getDB(dbName);
			collection = db.getCollection(collectionName);
		} catch (NumberFormatException e) {
			throw new RuntimeException(e);
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}

	public void onAuditEvent(AuditEvent auditEvent) {
		DBObject dbObject = (DBObject) JSON.parse(auditEvent.toJSON());
		collection.insert(dbObject);
	}

}
