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

import com.cetsoft.caudit.observable.AuditEvent;

/**
 * The Class CountingStopwatch.
 */
public class CountingStopwatch extends Stopwatch{

	/** The count. */
	volatile AtomicLong count = new AtomicLong();
	
	/** The start count. */
	long startCount = 0;
	
	/** The stop count. */
	long stopCount;
	
	/**
	 * Instantiates a new counting stopwatch.
	 *
	 * @param auditId the audit id
	 * @param auditName the audit name
	 */
	public CountingStopwatch(int auditId, String auditName) {
		super(auditId, auditName);
	}
	
	/**
	 * Start.
	 *
	 * @param startCount the start count
	 */
	public void start(long startCount){
		super.start();
		this.startCount = startCount;
	}
	
	/**
	 * Stop.
	 *
	 * @param stopCount the stop count
	 */
	public void stop(long stopCount){
		super.stop();
		this.count.addAndGet(stopCount-startCount);
	}
	
	/**
	 * Sets the count.
	 *
	 * @param count the new count
	 */
	public void setCount(long count){
		this.count.set(count);
	}
	
	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public long getCount(){
		return this.count.get();
	}
	
	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.stopwatch.Stopwatch#reset()
	 */
	@Override
	public void reset(){
		this.count.set(0);
		super.reset();
	}
	
	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.stopwatch.Stopwatch#createEvent()
	 */
	@Override
	public AuditEvent createEvent() {
		update();
		AuditEvent event = new AuditEvent();
		event.setAuditName(auditName);
		event.put("ElapsedTime", getElapsedTime());
		event.put("Count", getCount());
		shouldReset();
		return event;
	}

}
