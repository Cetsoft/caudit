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
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// TODO: Auto-generated Javadoc
/**
 * The Class AuditMapper.
 */
public class AuditMapper {

	/** The audit counter. */
	private int auditCounter;
	
	/** The lock. */
	private Lock lock = new ReentrantLock();
	
	/** The audit map. */
	private Map<String, Integer> auditMap = new HashMap<String, Integer>();
	
	/** The id map. */
	private Map<Integer, String> idMap = new HashMap<Integer, String>();

	/**
	 * Gets the audit id.
	 *
	 * @param auditName the audit name
	 * @return the audit id
	 */
	public int getAuditId(String auditName) {
		Integer id = auditMap.get(auditName);
		if (id == null) {
			lock.lock();
			try {
				id = auditMap.get(auditName);
				if (id == null) {
					id = auditCounter++;
					auditMap.put(auditName, id);
					idMap.put(id, auditName);
				}
			} finally {
				lock.unlock();
			}
		}
		return id;
	}

	/**
	 * Gets the audit name.
	 *
	 * @param id the id
	 * @return the audit name
	 */
	public String getAuditName(int id) {
		lock.lock();
		try {
			return idMap.get(id);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Removes the audit id.
	 *
	 * @param audit the audit
	 */
	public void removeAuditId(Audit audit) {
		lock.lock();
		try {
			auditMap.remove(audit.getAuditName());
		} finally {
			lock.unlock();
		}
	}

}
