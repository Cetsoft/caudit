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
