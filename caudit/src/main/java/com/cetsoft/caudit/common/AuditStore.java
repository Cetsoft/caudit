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
				removalListener.onRemoval(auditEvent);
			}
			auditMap.remove(audit.getAuditId());
		}finally{
			lock.unlock();
		}
	}
}
