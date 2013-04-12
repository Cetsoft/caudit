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

import java.util.concurrent.atomic.AtomicLong;

import com.cetsoft.caudit.observable.AuditEvent;

/**
 * The Class LongQuantity.
 */
public class LongQuantity extends Quantity {

	/** The atomic long. */
	volatile AtomicLong atomicLong = new AtomicLong();
	
	/**
	 * Instantiates a new long quantity.
	 *
	 * @param auditId the audit id
	 * @param auditName the audit name
	 */
	public LongQuantity(int auditId, String auditName) {
		super(auditId, auditName);
	}

	/**
	 * Increment.
	 */
	public void increment(){
		atomicLong.incrementAndGet();
	}
	
	/**
	 * Increment by value.
	 *
	 * @param value the value
	 */
	public void increment(long value){
		atomicLong.addAndGet(value);
	}
	
	/**
	 * Decrement.
	 */
	public void decrement(){
		atomicLong.decrementAndGet();
	}
	
	/**
	 * Decrement by value.
	 *
	 * @param value the value
	 */
	public void decrement(long value){
		atomicLong.addAndGet(-value);
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the value
	 */
	public void set(long value){
		atomicLong.set(value);
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the long
	 */
	public long get(){
		return atomicLong.get();
	}

	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.common.Audit#reset()
	 */
	public void reset() {
		atomicLong.set(0);
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
