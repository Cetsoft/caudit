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
package com.cetsoft.caudit.quantity;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.cetsoft.caudit.observable.AuditEvent;

/**
 * The Class DoubleQuantity.
 */
public class DoubleQuantity extends Quantity {

	/** The atomic double. */
	volatile double atomicDouble = 0;
	
	/** The should reset. */
	boolean shouldReset = true;
	
	/** The lock. */
	Lock lock = new ReentrantLock();

	/**
	 * Instantiates a new double quantity.
	 *
	 * @param auditId the audit id
	 * @param auditName the audit name
	 */
	public DoubleQuantity(int auditId, String auditName) {
		super(auditId, auditName);
	}

	/**
	 * Sets the value.
	 *
	 * @param value the value
	 */
	public void set(double value) {
		lock.lock();
		try {
			atomicDouble = value;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Gets the value.
	 *
	 * @return the double
	 */
	public double get() {
		lock.lock();
		try {
			return atomicDouble;
		} finally {
			lock.unlock();
		}
	}

	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.common.Audit#reset()
	 */
	public void reset() {
		lock.lock();
		try {
			atomicDouble = 0;
		} finally {
			lock.unlock();
		}
	}

	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.common.Audit#createEvent()
	 */
	public AuditEvent createEvent() {
		update();
		AuditEvent event = new AuditEvent();
		event.setAuditName(auditName);
		event.put("Quantity", get());
		shouldReset();
		return event;
	}
}