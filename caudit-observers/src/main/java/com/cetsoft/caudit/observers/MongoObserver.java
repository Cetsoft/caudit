/*
* Copyright (C) 2014 Cetsoft, http://www.cetsoft.com
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
* 
* Author : Yusuf Aytas
* Date   : Sep 23, 2014
*/
package com.cetsoft.caudit.observers;

import java.net.UnknownHostException;

import com.cetsoft.caudit.observable.AuditEvent;
import com.cetsoft.caudit.observer.AuditObserver;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

/**
 * An asynchronous update interface for receiving notifications
 * about log as the Mongo is constructed.
 */
public class MongoObserver implements AuditObserver{
	
	/** The collection. */
	private DBCollection collection;
	
	/** The Constant DEFAULT_PORT. */
	private final static String DEFAULT_PORT = "27017";
	
	/** The Constant DEFAULT_COLNAME. */
	private final static String DEFAULT_COLNAME = "caudit";
	
	/**
	 * This method is called when information about an Mongo
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param connectionString the connection string
	 * @param dbName the db name
	 */
	public MongoObserver(String connectionString, String dbName) {
		this(connectionString,dbName, DEFAULT_PORT);
	}
	
	/**
	 * This method is called when information about an Mongo
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param connectionString the connection string
	 * @param dbName the db name
	 * @param port the port
	 */
	public MongoObserver(String connectionString, String dbName, String port) {
		this(connectionString,dbName, port, DEFAULT_COLNAME);
	}
	
	/**
	 * This method is called when information about an Mongo
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param connectionString the connection string
	 * @param dbName the db name
	 * @param port the port
	 * @param collectionName the collection name
	 */
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

	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.observer.AuditObserver#onAuditEvent(com.cetsoft.caudit.observable.AuditEvent)
	 */
	public void onAuditEvent(AuditEvent auditEvent) {
		DBObject dbObject = (DBObject) JSON.parse(auditEvent.toJSON());
		collection.insert(dbObject);
	}

}
