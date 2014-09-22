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
