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
* Date   : May 28, 2013
*/
package com.cetsoft.caudit.loader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.cetsoft.caudit.common.AuditStore;
import com.cetsoft.caudit.instance.ObjectInstanceCreater;
import com.cetsoft.caudit.observable.AuditObservable;
import com.cetsoft.caudit.observable.ScheduledAuditObservable;
import com.cetsoft.caudit.observer.AuditObserver;
import com.cetsoft.caudit.observer.ConsoleObserver;

/**
 * The Class AuditContextLoader.
 */
public class AuditContextLoader {

	/** The default filename. */
	private final String DEFAULT_FILENAME = "/caudit.xml";

	/** The observer tag. */
	private final String OBSERVER_TAG = "observer";
	
	/** The period tag. */
	private final String PERIOD_TAG = "period";
	
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
	 * Load the caudit configuration.
	 */
	public void load() {
		try {
			String fileName = System.getProperty("caudit.configuration.filePath");
			if (fileName == null) {
				loadConfigurationFromClasspath();
			} else {
				loadConfigurationFromFile(fileName);
			}
		} catch (Exception exception) {
			System.err.println("An exception occured while loading configuration file.");
			System.err.println("Loading caudit with default configuration, see the detailed exception.");
			exception.printStackTrace();
			loadWithDefaultConfiguration();
		}
	}

	/**
	 * Load with default configuration.
	 */
	protected void loadWithDefaultConfiguration() {
		AuditObserver observer = createDefaultAuditObserver();
		AuditObservable observable = createDefaultAuditObservable();
		observable.add(observer);
		observable.start();
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
		loadFromXmlDocument(inputStream);
	}

	/**
	 * Load configuration from file.
	 *
	 * @param fileName the file name
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the sAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void loadConfigurationFromFile(String fileName) throws ParserConfigurationException, SAXException, IOException {
		final InputStream inputStream = this.getClass().getResourceAsStream(fileName);
		loadFromXmlDocument(inputStream);
	}

	/**
	 * Load from xml document.
	 * 
	 * @param inputStream the input stream
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the sAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void loadFromXmlDocument(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
		if (inputStream == null) {
			loadWithDefaultConfiguration();
		} else {
			final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Document xmlDocument = documentBuilder.parse(bufferedInputStream);
			xmlDocument.getDocumentElement().normalize();
			AuditObservable auditObservable = createAuditObservable(xmlDocument);
			List<AuditObserver> auditObservers = createAuditObservers(xmlDocument);
			for (AuditObserver auditObserver : auditObservers) {
				auditObservable.add(auditObserver);
			}
			auditObservable.start();
		}
	}

	/**
	 * Creates the audit observers.
	 * 
	 * @param xmlDocument the xml document
	 * @return the list
	 */
	protected List<AuditObserver> createAuditObservers(Document xmlDocument) {
		NodeList nodeList = xmlDocument.getElementsByTagName(OBSERVER_TAG);
		List<AuditObserver> auditObservers = new ArrayList<AuditObserver>();
		if (nodeList == null || nodeList.getLength() == 0) {
			auditObservers = new ArrayList<AuditObserver>();
			auditObservers.add(createDefaultAuditObserver());
		} else {
			Node current = nodeList.item(0);
			while (current != null) {
				if (current.getNodeType() == Node.ELEMENT_NODE) {
					AuditObserver auditObserver = createObserver(current);
					auditObservers.add(auditObserver);
				}
				current = current.getNextSibling();
			}
		}
		return auditObservers;
	}

	/**
	 * Creates the observer.
	 *
	 * @param node the node
	 * @return the audit observer
	 */
	protected AuditObserver createObserver(Node node) {
		String classz = node.getAttributes().getNamedItem("class").getNodeValue();
		try {
			List<String> argList = new ArrayList<String>(3);
			for (int i=0; i<node.getChildNodes().getLength();i++) {
				Node childNode = node.getChildNodes().item(i);
				if(childNode.getNodeType() == Node.ELEMENT_NODE){
					argList.add(node.getChildNodes().item(i).getNodeValue());
				}
			}
			AuditObserver auditObserver = (AuditObserver) ObjectInstanceCreater.createObject(classz, argList.toArray());
			return auditObserver;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates the audit observable.
	 * 
	 * @param xmlDocument the xml document
	 * @return the audit observable
	 */
	protected AuditObservable createAuditObservable(Document xmlDocument) {
		NodeList nodeList = xmlDocument.getElementsByTagName(PERIOD_TAG);
		Node current = nodeList.item(0);
		while (current != null) {
			if (current.getNodeType() == Node.ELEMENT_NODE) {
				long period = Long.parseLong(current.getTextContent().trim());
				AuditObservable auditObservable = createAuditObservable(period);
				return auditObservable;
			}
			current = current.getNextSibling();
		}
		return createDefaultAuditObservable();
	}
	
	/**
	 * Creates the audit observable.
	 *
	 * @param period the period
	 * @return the scheduled audit observable
	 */
	protected ScheduledAuditObservable createAuditObservable(long period) {
		return new ScheduledAuditObservable(auditStore, period, TimeUnit.MILLISECONDS);
	}

	/**
	 * Creates the default audit observable.
	 * 
	 * @return the scheduled audit observable
	 */
	protected ScheduledAuditObservable createDefaultAuditObservable() {
		return new ScheduledAuditObservable(auditStore, 10, TimeUnit.SECONDS);
	}

	/**
	 * Creates the default audit observer.
	 * 
	 * @return the audit observer
	 */
	protected AuditObserver createDefaultAuditObserver() {
		return new ConsoleObserver();
	}

}