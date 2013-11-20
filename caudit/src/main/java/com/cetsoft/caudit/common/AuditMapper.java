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
