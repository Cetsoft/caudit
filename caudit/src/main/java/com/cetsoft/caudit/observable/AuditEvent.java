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
package com.cetsoft.caudit.observable;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The Class AuditEvent.
 */
public class AuditEvent {

	/** The audit name. */
	String auditName;
	
	/** The map. */
	Map<Object, Object> map = new HashMap<Object, Object>();
	
	/**
	 * Sets the audit name.
	 *
	 * @param auditName the new audit name
	 */
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}

	/**
	 * Put.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void put(Object key, Object value) {
		map.put(key, value);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (auditName != null) {
			StringBuilder builder = new StringBuilder();
			builder.append(auditName);
			builder.append(" : ");
			for (Entry<Object, Object> entry : map.entrySet()) {
				builder.append(entry.getKey());
				builder.append("[");
				builder.append(entry.getValue());
				builder.append("]");
				builder.append(" ");
			}
			return builder.toString();
		} else {
			return "";
		}
	}
	
	public String toJSON(){
		if (auditName != null) {
			StringBuilder builder = new StringBuilder();
			builder.append("{");
			builder.append("auditName:");
			builder.append(auditName);
			for (Entry<Object, Object> entry : map.entrySet()) {
				builder.append(",");
				builder.append(entry.getKey());
				builder.append(":");
				builder.append(entry.getValue());
			}
			builder.append("}");
			return builder.toString();
		} else {
			return "";
		}
	}

}
