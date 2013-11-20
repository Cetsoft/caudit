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

}
