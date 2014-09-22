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
* Date   : Apr 8, 2013
*/
package com.cetsoft.caudit.stopwatch;

import java.util.concurrent.atomic.AtomicLong;

import com.cetsoft.caudit.common.AbstractAudit;
import com.cetsoft.caudit.observable.AuditEvent;

/**
 * The Class Stopwatch.
 */
public class Stopwatch extends AbstractAudit {

	/** The elapsed time in millis. */
	volatile AtomicLong elapsedTimeInMillis = new AtomicLong(0);
	
	long startTime = 0;

	/**
	 * Instantiates a new stopwatch.
	 *
	 * @param auditId the audit id
	 * @param auditName the audit name
	 */
	public Stopwatch(int auditId, String auditName) {
		super(auditId, auditName);
	}

	/**
	 * Start.
	 */
	public void start() {
		startTime = System.currentTimeMillis();
	}

	/**
	 * Stop.
	 */
	public void stop() {
		long delta = System.currentTimeMillis() - startTime;
		elapsedTimeInMillis.addAndGet(delta);
	}

	/**
	 * Gets the elapsed time.
	 *
	 * @return the elapsed time
	 */
	public long getElapsedTime() {
		return elapsedTimeInMillis.get();
	}

	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.common.Audit#reset()
	 */
	public void reset() {
		elapsedTimeInMillis.set(0);
	}

	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.common.Audit#createEvent()
	 */
	public AuditEvent createEvent() {
		update();
		AuditEvent event = new AuditEvent();
		event.setAuditName(auditName);
		event.put("ElapsedTime", getElapsedTime());
		shouldReset();
		return event;
	}

}
