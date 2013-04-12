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
package com.cetsoft.caudit.complex;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.cetsoft.caudit.common.AbstractAudit;
import com.cetsoft.caudit.observable.AuditEvent;

/**
 * The Class ComplexAudit.
 */
public class ComplexAudit extends AbstractAudit{

	/** The map. */
	volatile Map<Object,Object> map = new ConcurrentHashMap<Object, Object>();
	
	/**
	 * Instantiates a new complex audit.
	 *
	 * @param auditId the audit id
	 * @param auditName the audit name
	 */
	public ComplexAudit(int auditId, String auditName) {
		super(auditId, auditName);
	}

	/**
	 * Put.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void put(Object key, Object value){
		map.put(key, value);
	}
	
	/**
	 * Gets the.
	 *
	 * @return the map
	 */
	public Map<Object, Object> get(){
		return map;
	}

	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.common.Audit#reset()
	 */
	public void reset() {
		map.clear();
	}

	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.common.Audit#createEvent()
	 */
	public AuditEvent createEvent() {
		update();
		AuditEvent event = new AuditEvent();
		event.setAuditName(auditName);
		for (Entry<Object,Object> entry : map.entrySet()) {
			event.put(entry.getKey(), entry.getValue());
		}
		shouldReset();
		return event;
	}

}
