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
package com.cetsoft.caudit.stopwatch;

import java.util.concurrent.atomic.AtomicLong;

import com.cetsoft.caudit.common.AbstractAudit;
import com.cetsoft.caudit.observable.AuditEvent;

/**
 * The Class Stopwatch.
 */
public class Stopwatch extends AbstractAudit {

	/** The elapsed time in millis. */
	AtomicLong elapsedTimeInMillis = new AtomicLong(0);

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
		elapsedTimeInMillis.set(System.currentTimeMillis());
	}

	/**
	 * Stop.
	 */
	public void stop() {
		long delta = System.currentTimeMillis() - elapsedTimeInMillis.get();
		elapsedTimeInMillis.set(delta);
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
