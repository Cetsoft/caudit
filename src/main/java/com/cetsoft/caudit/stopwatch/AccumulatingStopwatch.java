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

/**
 * The Class AccumulatingStopwatch.
 */
public class AccumulatingStopwatch extends Stopwatch{

	/** The current elapsed time. */
	AtomicLong currentElapsedTime = new AtomicLong(0);
	
	/**
	 * Instantiates a new accumulating stopwatch.
	 *
	 * @param auditId the audit id
	 * @param auditName the audit name
	 */
	public AccumulatingStopwatch(int auditId, String auditName) {
		super(auditId, auditName);
	}
	
	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.stopwatch.Stopwatch#start()
	 */
	@Override
	public void start() {
		currentElapsedTime.set(System.currentTimeMillis());
	}

	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.stopwatch.Stopwatch#stop()
	 */
	@Override
	public void stop() {
		long delta = System.currentTimeMillis()-currentElapsedTime.get();
		elapsedTimeInMillis.addAndGet(delta);
	}
	
	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.stopwatch.Stopwatch#getElapsedTime()
	 */
	public long getElapsedTime(){
		return elapsedTimeInMillis.get();
	}
}
