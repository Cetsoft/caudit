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
	private long period;
	
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
	public ScheduledAuditObservable(AuditStore auditStore, long period, TimeUnit timeUnit) {
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
