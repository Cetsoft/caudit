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
package com.cetsoft.caudit.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.cetsoft.caudit.observable.AuditEvent;

/**
 * The Class AuditStore.
 */
public class AuditStore {

	/** The factory. */
	AuditFactory factory;
	
	/** The lock. */
	Lock lock = new ReentrantLock();
	
	/** The audit map. */
	Map<Integer, Audit> auditMap = new HashMap<Integer, Audit>();

	/**
	 * Instantiates a new audit store.
	 *
	 * @param factory the factory
	 */
	public AuditStore(AuditFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * Gets the.
	 *
	 * @param <A> the generic type
	 * @param auditId the audit id
	 * @param type the type
	 * @return the a
	 */
	@SuppressWarnings("unchecked")
	public <A extends Audit> A get(int auditId, AuditType type) {
		A audit = (A) auditMap.get(auditId);
		if (audit == null) {
			lock.lock();
			try {
				audit = (A) auditMap.get(auditId);
				if (audit == null) {
					audit = (A) factory.create(type, auditId);
					if(audit!=null){
						auditMap.put(auditId, audit);
					}
					else{
						throw new UnmappedAuditException(auditId);
					}
				}
			} finally {
				lock.unlock();
			}
		}
		return audit;
	}
	
	/**
	 * Audits.
	 *
	 * @return the sets the
	 */
	public Set<Audit> audits(){
		lock.lock();
		try{
			return new HashSet<Audit>(auditMap.values());
		}finally{
			lock.unlock();
		}
	}

	/**
	 * Removes the.
	 *
	 * @param audit the audit
	 */
	public void remove(Audit audit) {
		lock.lock();
		try{
			AuditRemovalListener removalListener = audit.getRemovalListener();
			if(removalListener!=null){
				AuditEvent auditEvent = audit.createEvent();
				removalListener.onRemovalAuditEvent(auditEvent);
			}
			auditMap.remove(audit.getAuditId());
		}finally{
			lock.unlock();
		}
	}
}
