/*
* Copyright (C) 2013 Cetsoft, http://www.cetsoft.com
*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Library General Public
* License as published by the Free Software Foundation; either
* version 2 of the License, or (at your option) any later version.
*
* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Library General Public License for more details.
*
* You should have received a copy of the GNU Library General Public
* License along with this library; if not, write to the Free
* Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
* 
* Author : Yusuf Aytas
* Date   : Apr 8, 2013
*/
package com.cetsoft.caudit.loader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.cetsoft.caudit.common.AuditStore;
import com.cetsoft.caudit.observable.ScheduledAuditObservable;
import com.cetsoft.caudit.observer.AuditObserver;
import com.cetsoft.caudit.observer.ConsoleObserver;

/**
 * The Class AuditContextLoader.
 */
public class AuditContextLoader {

	/** The default filename. */
	private String DEFAULT_FILENAME = "/caudit.xml";
	
	/** The audit store. */
	private AuditStore auditStore;

	/**
	 * Instantiates a new audit context loader.
	 *
	 * @param auditStore the audit store
	 */
	public AuditContextLoader(AuditStore auditStore) {
		this.auditStore = auditStore;
	}

	/**
	 * Load.
	 */
	public void load() {
		try {
			String fileName = System.getProperty("caudit.configuration.filePath");
			if (fileName == null) {
				loadConfigurationFromClasspath();
			} else {
				loadConfigurationFromFile();
			}
		} catch (Exception exception) {
			AuditObserver observer = new ConsoleObserver();
			ScheduledAuditObservable observable = new ScheduledAuditObservable(auditStore, 10, TimeUnit.SECONDS);
			observable.add(observer);
			observable.start();
		}
	}

	/**
	 * Load configuration from classpath.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SAXException the sAX exception
	 * @throws ParserConfigurationException the parser configuration exception
	 */
	protected void loadConfigurationFromClasspath() throws IOException, SAXException, ParserConfigurationException {
		final InputStream inputStream = this.getClass().getResourceAsStream(DEFAULT_FILENAME);
		final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		Document xmlDocument = documentBuilder.parse(bufferedInputStream);
		loadFromXmlDocument(xmlDocument);
	}

	/**
	 * Load configuration from file.
	 */
	protected void loadConfigurationFromFile() {

	}

	/**
	 * Load from xml document.
	 *
	 * @param xmlDocument the xml document
	 */
	protected void loadFromXmlDocument(Document xmlDocument) {
		throw new RuntimeException();
	}

}
