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
package com.cetsoft.caudit.observable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.cetsoft.caudit.common.AuditStore;

/**
 * The Class ScheduledAuditObservable.
 */
public class ScheduledAuditObservable extends AbstractAuditObservable implements Runnable {

	/** The period. */
	private int period;
	
	/** The time unit. */
	private TimeUnit timeUnit;
	
	/** The service. */
	private ScheduledExecutorService service;

	/**
	 * Instantiates a new scheduled audit observable.
	 *
	 * @param auditStore the audit store
	 * @param period the period
	 * @param timeUnit the time unit
	 */
	public ScheduledAuditObservable(AuditStore auditStore, int period, TimeUnit timeUnit) {
		super(auditStore);
		this.period = period;
		this.timeUnit = timeUnit;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		createEvents();
	}

	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.observable.AuditObservable#start()
	 */
	public void start() {
		service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(this, 0, period, timeUnit);
	}

	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.observable.AuditObservable#stop()
	 */
	public void stop() {
		service.shutdown();
	}

}
