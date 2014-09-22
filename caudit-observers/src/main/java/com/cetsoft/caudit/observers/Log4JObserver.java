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

import org.apache.log4j.Logger;

import com.cetsoft.caudit.observable.AuditEvent;
import com.cetsoft.caudit.observer.AuditObserver;

/**
 * An asynchronous update interface for receiving notifications
 * about log as the Log4J is constructed.
 */
public class Log4JObserver implements AuditObserver{
	
	/** The logger. */
	private Logger logger = Logger.getLogger(Log4JObserver.class);

	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.observer.AuditObserver#onAuditEvent(com.cetsoft.caudit.observable.AuditEvent)
	 */
	public void onAuditEvent(AuditEvent auditEvent) {
		logger.info(auditEvent.toString());
	}

}
