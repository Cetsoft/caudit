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
