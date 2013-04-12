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
